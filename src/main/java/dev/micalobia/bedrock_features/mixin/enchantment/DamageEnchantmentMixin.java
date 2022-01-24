package dev.micalobia.bedrock_features.mixin.enchantment;

import dev.micalobia.bedrock_features.enchantment.BFEnchantments.Config;
import net.minecraft.enchantment.DamageEnchantment;
import net.minecraft.entity.EntityGroup;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(DamageEnchantment.class)
public class DamageEnchantmentMixin {
	@Inject(method = "getAttackDamage", at = @At(value = "RETURN", ordinal = 0), cancellable = true)
	public void useBedrockDamage(int level, EntityGroup group, CallbackInfoReturnable<Float> cir) {
		if(Config.sharpnessBuffed)
			cir.setReturnValue(1.25f * level);
	}
}
