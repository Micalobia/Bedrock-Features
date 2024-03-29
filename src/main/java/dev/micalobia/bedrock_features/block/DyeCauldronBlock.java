package dev.micalobia.bedrock_features.block;

import dev.micalobia.bedrock_features.block.BFBlocks.Config;
import dev.micalobia.bedrock_features.block.entity.DyeCauldronBlockEntity;
import dev.micalobia.bedrock_features.config.BFConfig;
import dev.micalobia.bedrock_features.stat.BFStats;
import net.fabricmc.fabric.api.event.registry.RegistryEntryAddedCallback;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.LeveledCauldronBlock;
import net.minecraft.block.cauldron.CauldronBehavior;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome.Precipitation;
import net.minecraft.world.event.GameEvent;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.Objects;

public class DyeCauldronBlock extends LeveledCauldronBlock implements BlockEntityProvider {
	public DyeCauldronBlock(Settings settings) {
		super(settings, RAIN_PREDICATE, Behaviors.MAP);
	}

	public static int colorProvider(BlockState state, BlockView view, BlockPos pos, int tintIndex) {
		DyeCauldronBlockEntity entity = (DyeCauldronBlockEntity) view.getBlockEntity(pos);
		if(entity == null)
			return switch(tintIndex) {
				case 0 -> colorProvider(state, view, pos.offset(Direction.DOWN), 1);
				case 6 -> 0x385DC6;
				default -> {
					Direction[] values = Direction.values();
					BlockPos _pos = pos
							.offset(values[tintIndex - 1].getOpposite())
							.offset(values[tintIndex]);
					yield colorProvider(state, view, _pos, tintIndex + 1);
				}
			};
		return entity.getColor();
	}

	@Nullable
	@Override
	public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
		return new DyeCauldronBlockEntity(pos, state);
	}

	@Override
	protected boolean canBeFilledByDripstone(Fluid fluid) {
		return Config.dyeCauldronObeysPrecipition && super.canBeFilledByDripstone(fluid);
	}

	@Override
	public void precipitationTick(BlockState state, World world, BlockPos pos, Precipitation precipitation) {
		if(!Config.dyeCauldronObeysPrecipition) return;
		super.precipitationTick(state, world, pos, precipitation);
	}

	public static class Behaviors {
		public static final Map<Item, CauldronBehavior> MAP;
		private static boolean enabled;

		static {
			MAP = CauldronBehavior.createMap();
		}

		private static void registerItem(Item item) {
			if(item instanceof DyeableItem) MAP.put(item, Behaviors::dyeDyableItem);
			if(item instanceof DyeItem) {
				MAP.put(item, Behaviors::dyeWater);
				CauldronBehavior.WATER_CAULDRON_BEHAVIOR.put(item, Behaviors::dyeWater);
			}
		}

		public static void init() {
			BFConfig.CHANGED.register(Behaviors::onConfigChanged);
			for(var item : Registries.ITEM) registerItem(item);
			RegistryEntryAddedCallback.event(Registries.ITEM).register(((rawId, id, item) -> registerItem(item)));
			MAP.put(Items.WATER_BUCKET, CauldronBehavior.FILL_WITH_WATER);
			MAP.put(Items.LAVA_BUCKET, CauldronBehavior.FILL_WITH_LAVA);
			MAP.put(Items.POWDER_SNOW_BUCKET, CauldronBehavior.FILL_WITH_POWDER_SNOW);
		}

		private static void onConfigChanged(BFConfig config) {
			enabled = config.areDyeCauldronsEnabled;
		}

		private static ActionResult dyeDyableItem(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, ItemStack stack) {
			Item item = stack.getItem();
			if(!(item instanceof DyeableItem dyeableItem))
				return ActionResult.PASS;
			if(!world.isClient) {
				DyeCauldronBlockEntity entity = Objects.requireNonNull((DyeCauldronBlockEntity) world.getBlockEntity(pos));
				dyeableItem.setColor(stack, entity.getColor());
				player.incrementStat(BFStats.DYE_ARMOR);
				LeveledCauldronBlock.decrementFluidLevel(state, world, pos);
				world.emitGameEvent(null, GameEvent.FLUID_PICKUP, pos);
				world.playSound(null, pos, SoundEvents.ITEM_DYE_USE, SoundCategory.BLOCKS, 1f, 1f);
			}
			return ActionResult.success(world.isClient);
		}

		private static ActionResult dyeWater(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, ItemStack stack) {
			if(!enabled)
				return ActionResult.PASS;
			Item item = stack.getItem();
			if(!(item instanceof DyeItem dyeItem))
				return ActionResult.PASS;
			if(!world.isClient) {
				if(!player.getAbilities().creativeMode)
					stack.decrement(1);
				DyeColor dyeColor = dyeItem.getColor();
				if(state.isOf(Blocks.WATER_CAULDRON))
					world.setBlockState(pos, BFBlocks.DYE_CAULDRON.getDefaultState().with(LeveledCauldronBlock.LEVEL, state.get(LeveledCauldronBlock.LEVEL)));
				DyeCauldronBlockEntity entity = Objects.requireNonNull((DyeCauldronBlockEntity) world.getBlockEntity(pos));
				entity.blendAndSetColor(dyeColor);
				entity.sync();
				player.incrementStat(BFStats.DYE_WATER);
				world.playSound(null, pos, SoundEvents.ITEM_DYE_USE, SoundCategory.BLOCKS, 1f, 1f);
			}
			return ActionResult.success(world.isClient);
		}
	}
}
