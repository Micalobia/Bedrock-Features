package dev.micalobia.bedrock_features.mixin.block;

import dev.micalobia.bedrock_features.block.JukeboxBlockProxy;
import dev.micalobia.bedrock_features.block.entity.JukeboxBlockEntityExtension;
import dev.micalobia.bedrock_features.config.JukeboxConfig;
import net.minecraft.block.Block;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockState;
import net.minecraft.block.JukeboxBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.entity.JukeboxBlockEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import java.util.Optional;

@Mixin(JukeboxBlock.class)
public abstract class JukeboxBlockMixin extends AbstractBlockMixin implements BlockEntityProvider {
	@ModifyVariable(method = "onUse", at = @At(value = "STORE", target = "Lnet/minecraft/block/BlockState;with(Lnet/minecraft/state/property/Property;Ljava/lang/Comparable;)Ljava/lang/Object;"), argsOnly = true)
	private BlockState injectPoweredStateOnUse(BlockState state) {
		return state.with(Properties.POWERED, false);
	}

	@ModifyArg(method = "onUse", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;setBlockState(Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/BlockState;I)Z"), index = 2)
	private int injectNotificationOnUse(int value) {
		return value | Block.NOTIFY_NEIGHBORS;
	}

	@ModifyArg(method = "setRecord", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/WorldAccess;setBlockState(Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/BlockState;I)Z"), index = 1)
	private BlockState injectPoweredStateSetRecord(BlockState state) {
		return state.with(Properties.POWERED, true);
	}

	@ModifyArg(method = "setRecord", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/WorldAccess;setBlockState(Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/BlockState;I)Z"), index = 2)
	private int injectNotificationSetRecord(int value) {
		return value | Block.NOTIFY_NEIGHBORS;
	}

	@Nullable
	@Override
	public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World _world, BlockState _state, BlockEntityType<T> _type) {
		if(_world.isClient) return null;
		return (world, pos, state, blockEntity) -> {
			if(!state.get(Properties.POWERED)) return;
			var jentity = (JukeboxBlockEntity) blockEntity;
			ItemStack record = jentity.getRecord();
			if(record.isEmpty()) return;
			var entity = (JukeboxBlockEntityExtension) blockEntity;
			entity.Bedrock$addTicksPlaying(1);
			int ticksPlaying = entity.Bedrock$getTicksPlaying();
			if(ticksPlaying > JukeboxConfig.getTickCount(record))
				world.setBlockState(pos, state.with(Properties.POWERED, false), Block.NOTIFY_ALL);
		};
	}

	@Override
	public Optional<Boolean> Bedrock$changeEmitsRedstonePower(BlockState state) {
		if(!JukeboxBlockProxy.getEmitsRedstone()) return super.Bedrock$changeEmitsRedstonePower(state);
		else return Optional.of(true);
	}

	@Override
	public Optional<Integer> Bedrock$changeWeakRedstonePower(BlockState state, BlockView world, BlockPos pos, Direction direction) {
		return Optional.of(state.get(Properties.POWERED) ? 15 : 0);
	}
}