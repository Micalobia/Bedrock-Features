package dev.micalobia.bedrock_features.block;

import dev.micalobia.bedrock_features.block.entity.PotionCauldronBlockEntity;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.LeveledCauldronBlock;
import net.minecraft.block.cauldron.CauldronBehavior;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsage;
import net.minecraft.item.Items;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionUtil;
import net.minecraft.potion.Potions;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome.Precipitation;
import net.minecraft.world.event.GameEvent;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Random;

public class PotionCauldronBlock extends LeveledCauldronBlock implements BlockEntityProvider {
	public PotionCauldronBlock(Settings settings) {
		super(settings, null, Behaviors.MAP);
	}

	public static int colorProvider(BlockState state, BlockView view, BlockPos pos, int tintIndex) {
		PotionCauldronBlockEntity entity = Objects.requireNonNull((PotionCauldronBlockEntity) view.getBlockEntity(pos));
		return entity.getRenderColor();
	}

	@Override
	public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
		return new PotionCauldronBlockEntity(pos, state);
	}

	@Override
	public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
	}

	@Override
	protected void onFireCollision(BlockState state, World world, BlockPos pos) {
	}

	@Override
	public void precipitationTick(BlockState state, World world, BlockPos pos, Precipitation precipitation) {
	}

	@Override
	protected void fillFromDripstone(BlockState state, World world, BlockPos pos, Fluid fluid) {
	}

	@Override
	public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
	}

	@Override
	public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
		if(random.nextInt(state.get(LEVEL) + 4) < 2) {
			int j = random.nextInt(2) * 2 - 1;
			int k = random.nextInt(2) * 2 - 1;
			float d = pos.getX() + 0.5f + j * .375f * random.nextFloat();
			float e = (float) (pos.getY() + getFluidHeight(state));
			float f = pos.getZ() + 0.5f + k * .375f * random.nextFloat();
			PotionCauldronBlockEntity entity = Objects.requireNonNull((PotionCauldronBlockEntity) world.getBlockEntity(pos));
			int color = entity.getRenderColor();
			double r = (color >> 16 & 255) / 255.0D;
			double g = (color >> 8 & 255) / 255.0D;
			double b = (color & 255) / 255.0D;
			world.addParticle(state.get(LEVEL) == 3 ? ParticleTypes.ENTITY_EFFECT : ParticleTypes.AMBIENT_ENTITY_EFFECT, d, e, f, r, g, b);
		}
	}

	public static class Behaviors {
		private static final Map<Item, CauldronBehavior> MAP;

		static {
			MAP = CauldronBehavior.createMap();
		}

		private static ActionResult addPotionToPotion(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, ItemStack stack) {
			if(state.get(PotionCauldronBlock.LEVEL) == 3) return ActionResult.PASS;
			Potion potion = PotionUtil.getPotion(stack);
			if(potion == Potions.WATER) return ActionResult.PASS;
			PotionCauldronBlockEntity entity = Objects.requireNonNull((PotionCauldronBlockEntity) world.getBlockEntity(pos));
			if(!world.isClient) {
				if(entity.getPotion() != potion) return ActionResult.PASS;
				boolean hasCustomColor = entity.hasCustomColor();
				boolean stackCustomColor = hasCustomColor(stack);
				if(hasCustomColor != stackCustomColor) return ActionResult.PASS;
				if(hasCustomColor && PotionUtil.getColor(stack) != entity.getCustomColor())
					return ActionResult.PASS;
				List<StatusEffectInstance> effects = PotionUtil.getCustomPotionEffects(stack);
				boolean sameCustom = entity.getCustomEffects().containsAll(effects) && effects.containsAll(entity.getCustomEffects());
				if(!sameCustom)
					return ActionResult.PASS;
				player.setStackInHand(hand, ItemUsage.exchangeStack(stack, player, new ItemStack(Items.GLASS_BOTTLE)));
				player.incrementStat(Stats.USE_CAULDRON);
				player.incrementStat(Stats.USED.getOrCreateStat(stack.getItem()));
				world.setBlockState(pos, state.cycle(LEVEL));
				world.playSound(null, pos, SoundEvents.ITEM_BOTTLE_EMPTY, SoundCategory.BLOCKS, 1f, 1f);
				entity.sync();
			} else {
				if(entity.getRenderColor() != PotionUtil.getColor(stack)) return ActionResult.PASS;
			}
			return ActionResult.success(world.isClient);
		}

		private static ActionResult addPotionToEmpty(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, ItemStack stack) {
			Potion potion = PotionUtil.getPotion(stack);
			if(!world.isClient) {
				Item item = stack.getItem();
				player.setStackInHand(hand, ItemUsage.exchangeStack(stack, player, new ItemStack(Items.GLASS_BOTTLE)));
				player.incrementStat(Stats.USE_CAULDRON);
				player.incrementStat(Stats.USED.getOrCreateStat(item));
				if(potion == Potions.WATER)
					world.setBlockState(pos, Blocks.WATER_CAULDRON.getDefaultState());
				else {
					BlockState newState = BFBlocks.POTION_CAULDRON.getDefaultState();
					world.setBlockState(pos, newState);
					PotionCauldronBlockEntity entity = Objects.requireNonNull((PotionCauldronBlockEntity) world.getBlockEntity(pos));
					entity.setPotion(PotionUtil.getPotion(stack));
					entity.setCustomEffects(PotionUtil.getCustomPotionEffects(stack));
					if(hasCustomColor(stack))
						entity.setCustomColor(PotionUtil.getColor(stack));
					else
						entity.removeCustomColor();
					entity.sync();
				}
				world.playSound(null, pos, SoundEvents.ITEM_BOTTLE_EMPTY, SoundCategory.BLOCKS, 1.0F, 1.0F);
				world.emitGameEvent(null, GameEvent.FLUID_PLACE, pos);
			}
			return ActionResult.success(world.isClient);
		}

		private static ActionResult tipArrows(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, ItemStack stack) {
			if(!world.isClient) {
				ItemStack newStack = new ItemStack(Items.TIPPED_ARROW);
				PotionCauldronBlockEntity entity = Objects.requireNonNull((PotionCauldronBlockEntity) world.getBlockEntity(pos));
				PotionUtil.setPotion(newStack, entity.getPotion());
				PotionUtil.setCustomPotionEffects(newStack, entity.getCustomEffects());
				if(entity.hasCustomColor())
					Objects.requireNonNull(newStack.getNbt()).putInt("Color", entity.getCustomColor());
				int maxCount = switch(state.get(LEVEL)) {
					case 1 -> 16;
					case 2 -> 32;
					case 3 -> 64;
					default -> throw new IllegalStateException();
				};
				int count = Math.min(maxCount, stack.getCount());
				if(!player.getAbilities().creativeMode)
					stack.decrement(count);
				else
					count = maxCount;
				newStack.setCount(count);
				player.setStackInHand(hand, ItemUsage.exchangeStack(stack, player, newStack));
				world.setBlockState(pos, Blocks.CAULDRON.getDefaultState());
			}
			return ActionResult.success(world.isClient);
		}

		private static ActionResult takePotion(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, ItemStack stack) {
			if(!world.isClient) {
				Item item = stack.getItem();
				ItemStack newStack = new ItemStack(Items.POTION);
				PotionCauldronBlockEntity entity = Objects.requireNonNull((PotionCauldronBlockEntity) world.getBlockEntity(pos));
				PotionUtil.setPotion(newStack, entity.getPotion());
				PotionUtil.setCustomPotionEffects(newStack, entity.getCustomEffects());
				if(entity.hasCustomColor()) setCustomColor(newStack, entity.getCustomColor());
				player.setStackInHand(hand, ItemUsage.exchangeStack(stack, player, newStack));
				player.incrementStat(Stats.USE_CAULDRON);
				player.incrementStat(Stats.USED.getOrCreateStat(item));
				LeveledCauldronBlock.decrementFluidLevel(state, world, pos);
				world.playSound(null, pos, SoundEvents.ITEM_BOTTLE_FILL, SoundCategory.BLOCKS, 1f, 1f);
				world.emitGameEvent(null, GameEvent.FLUID_PLACE, pos);
			}
			return ActionResult.success(world.isClient);
		}

		private static void setCustomColor(ItemStack stack, int color) {
			Objects.requireNonNull(stack.getNbt()).putInt(PotionUtil.CUSTOM_POTION_COLOR_KEY, color);
		}

		private static boolean hasCustomColor(ItemStack stack) {
			return Objects.requireNonNull(stack.getNbt()).contains(PotionUtil.CUSTOM_POTION_COLOR_KEY);
		}

		public static void register() {
			MAP.put(Items.POTION, Behaviors::addPotionToPotion);
			MAP.put(Items.GLASS_BOTTLE, Behaviors::takePotion);
			MAP.put(Items.ARROW, Behaviors::tipArrows);
			// NOTE: This overrides vanilla behavior!
			CauldronBehavior.EMPTY_CAULDRON_BEHAVIOR.put(Items.POTION, Behaviors::addPotionToEmpty);
		}
	}
}
