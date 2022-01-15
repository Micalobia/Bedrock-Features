package dev.micalobia.bedrock_features.mixin.block;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Optional;

@Mixin(AbstractBlock.class)
public class AbstractBlockMixin {
	@Inject(method = "getPistonBehavior", at = @At("HEAD"), cancellable = true)
	public final void Bedrock$pistonBehaviorHook(BlockState state, CallbackInfoReturnable<PistonBehavior> cir) {
		Bedrock$changePistonBehavior(state).ifPresent(cir::setReturnValue);
	}

	@Inject(method = "emitsRedstonePower", at = @At("HEAD"), cancellable = true)
	public final void Bedrock$emitsRedstoneHook(BlockState state, CallbackInfoReturnable<Boolean> cir) {
		Bedrock$changeEmitsRedstonePower(state).ifPresent(cir::setReturnValue);
	}

	@Inject(method = "getWeakRedstonePower", at = @At("HEAD"), cancellable = true)
	public final void Bedrock$weakRedstonePowerHook(BlockState state, BlockView world, BlockPos pos, Direction direction, CallbackInfoReturnable<Integer> cir) {
		Bedrock$changeWeakRedstonePower(state, world, pos, direction).ifPresent(cir::setReturnValue);
	}

	public Optional<PistonBehavior> Bedrock$changePistonBehavior(BlockState state) {
		return Optional.empty();
	}

	public Optional<Boolean> Bedrock$changeEmitsRedstonePower(BlockState state) {
		return Optional.empty();
	}

	public Optional<Integer> Bedrock$changeWeakRedstonePower(BlockState state, BlockView world, BlockPos pos, Direction direction) {
		return Optional.empty();
	}
}
