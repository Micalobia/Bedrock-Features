package dev.micalobia.bedrock_features.mixin.features;

import dev.micalobia.bedrock_features.entity.BFEntities.Config;
import net.minecraft.entity.JumpingMount;
import net.minecraft.entity.mob.SkeletonHorseEntity;
import net.minecraft.entity.passive.AbstractHorseEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

public abstract class SkeletonHorseSaddle {
	@Mixin(value = AbstractHorseEntity.class, priority = 1001)
	public static abstract class EnforceSaddleChanges implements JumpingMount {
		@Shadow
		public abstract boolean isSaddled();


		// TODO
//		@Redirect(method = "travel", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/passive/AbstractHorseEntity;isSaddled()Z"))
//		private boolean changeSaddleMovementBehavior(AbstractHorseEntity horse) {
//			return BedrockFeatures$isSaddled();
//		}

		@Redirect(method = "getControllingPassenger", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/passive/AbstractHorseEntity;isSaddled()Z"))
		private boolean changeSaddleControllingBehavior(AbstractHorseEntity instance) {
			return BedrockFeatures$isSaddled();
		}

		@Redirect(method = "setJumpStrength", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/passive/AbstractHorseEntity;isSaddled()Z"))
		private boolean changeSaddleJumpBehavior(AbstractHorseEntity horse) {
			return BedrockFeatures$isSaddled();
		}

		@Redirect(method = "isImmobile", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/passive/AbstractHorseEntity;isSaddled()Z"))
		private boolean changeSaddleImmobileBehavior(AbstractHorseEntity horse) {
			return BedrockFeatures$isSaddled();
		}

		protected boolean BedrockFeatures$isSaddled() {
			return isSaddled();
		}

		@Inject(method = "canJump", at = @At("HEAD"), cancellable = true)
		private void canJump(CallbackInfoReturnable<Boolean> cir) {
			cir.setReturnValue(BedrockFeatures$isSaddled());
		}
	}

	@Mixin(SkeletonHorseEntity.class)
	public static abstract class ChangeSaddleCheck extends EnforceSaddleChanges {
		@Override
		protected boolean BedrockFeatures$isSaddled() {
			return !Config.skeletonHorseNeedsSaddle || super.BedrockFeatures$isSaddled();
		}
	}
}
