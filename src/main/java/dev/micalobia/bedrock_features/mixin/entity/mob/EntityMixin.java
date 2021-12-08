package dev.micalobia.bedrock_features.mixin.entity.mob;

import net.minecraft.entity.Entity;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Entity.class)
public abstract class EntityMixin {
	@Shadow
	public abstract DataTracker getDataTracker();

	@Inject(method = "interact", at = @At("HEAD"), cancellable = true)
	public void changeInteraction(PlayerEntity player, Hand hand, CallbackInfoReturnable<ActionResult> cir) {
	}
}
