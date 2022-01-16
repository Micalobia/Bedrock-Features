package dev.micalobia.bedrock_features.mixin.entity.mob;

import dev.micalobia.bedrock_features.entity.mob.CreeperEntityProxy;
import net.minecraft.entity.mob.CreeperEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(CreeperEntity.class)
public abstract class CreeperEntityMixin {
	@Shadow
	private int headsDropped;

	@Shadow
	public abstract boolean shouldRenderOverlay();

	@Inject(method = "shouldDropHead", at = @At("HEAD"), cancellable = true)
	public void increaseHeadLimitToInfinity(CallbackInfoReturnable<Boolean> cir) {
		boolean isUnderLimit = CreeperEntityProxy.getHeadsDroppedLimit() < 0 || this.headsDropped < CreeperEntityProxy.getHeadsDroppedLimit();
		cir.setReturnValue(this.shouldRenderOverlay() && isUnderLimit);
	}
}
