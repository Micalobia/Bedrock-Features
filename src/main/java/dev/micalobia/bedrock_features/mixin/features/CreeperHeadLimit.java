package dev.micalobia.bedrock_features.mixin.features;

import dev.micalobia.bedrock_features.entity.BFEntities.Config;
import net.minecraft.entity.mob.CreeperEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(CreeperEntity.class)
public abstract class CreeperHeadLimit {
	@Shadow
	private int headsDropped;

	@Shadow
	public abstract boolean shouldRenderOverlay();

	@Inject(method = "shouldDropHead", at = @At("HEAD"), cancellable = true)
	public void increaseHeadLimitToInfinity(CallbackInfoReturnable<Boolean> cir) {
		boolean isUnderLimit = Config.creeperHeadDropLimit < 0 || this.headsDropped < Config.creeperHeadDropLimit;
		cir.setReturnValue(this.shouldRenderOverlay() && isUnderLimit);
	}
}
