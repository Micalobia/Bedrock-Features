package dev.micalobia.bedrock_features.mixin.block;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.piston.PistonBehavior;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Optional;

@Mixin(AbstractBlock.class)
public class AbstractBlockMixin {
	@Inject(method = "getPistonBehavior", at = @At("HEAD"), cancellable = true)
	public final void Bedrock$pistonBehaviorHook(BlockState state, CallbackInfoReturnable<PistonBehavior> cir) {
		Bedrock$changePistonBehavior().ifPresent(cir::setReturnValue);
	}

	public Optional<PistonBehavior> Bedrock$changePistonBehavior() {
		return Optional.empty();
	}
}
