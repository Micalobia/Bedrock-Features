package dev.micalobia.bedrock_features.block;

import dev.micalobia.bedrock_features.block.entity.DyeCauldronBlockEntity;
import net.fabricmc.fabric.api.event.registry.RegistryEntryAddedCallback;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.LeveledCauldronBlock;
import net.minecraft.block.cauldron.CauldronBehavior;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.DyeItem;
import net.minecraft.item.DyeableItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.Objects;

public class DyeCauldronBlock extends LeveledCauldronBlock implements BlockEntityProvider {
	public DyeCauldronBlock(Settings settings) {
		super(settings, null, Behaviors.MAP);
	}

	public static int colorProvider(BlockState state, BlockView view, BlockPos pos, int tintIndex) {
		DyeCauldronBlockEntity entity = Objects.requireNonNull((DyeCauldronBlockEntity) view.getBlockEntity(pos));
		return entity.getColor();
	}

	@Nullable
	@Override
	public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
		return new DyeCauldronBlockEntity(pos, state);
	}

	public static class Behaviors {
		private static final Map<Item, CauldronBehavior> MAP;

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

		public static void register() {
			for(var item : Registry.ITEM) registerItem(item);
			RegistryEntryAddedCallback.event(Registry.ITEM).register(((rawId, id, item) -> registerItem(item)));
		}

		private static ActionResult dyeDyableItem(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, ItemStack stack) {
			Item item = stack.getItem();
			if(!(item instanceof DyeableItem dyeableItem))
				return ActionResult.PASS;
			if(!world.isClient) {
				DyeCauldronBlockEntity entity = Objects.requireNonNull((DyeCauldronBlockEntity) world.getBlockEntity(pos));
				dyeableItem.setColor(stack, entity.getColor());
				LeveledCauldronBlock.decrementFluidLevel(state, world, pos);
			}
			return ActionResult.success(world.isClient);
		}

		private static ActionResult dyeWater(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, ItemStack stack) {
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
				world.playSound(null, pos, SoundEvents.ITEM_DYE_USE, SoundCategory.BLOCKS, 1f, 1f);
			}
			return ActionResult.success(world.isClient);
		}
	}
}
