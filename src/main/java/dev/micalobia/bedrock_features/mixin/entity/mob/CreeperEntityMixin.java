package dev.micalobia.bedrock_features.mixin.entity.mob;

import net.minecraft.entity.mob.CreeperEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(CreeperEntity.class)
public abstract class CreeperEntityMixin {
	@Shadow
	public abstract boolean shouldRenderOverlay();

	@Inject(method = "shouldDropHead", at = @At("HEAD"), cancellable = true)
	public void increaseHeadLimitToInfinity(CallbackInfoReturnable<Boolean> cir) {
		cir.setReturnValue(this.shouldRenderOverlay());
	}
}
