package dev.micalobia.bedrock_features.mixin.features;

import dev.micalobia.bedrock_features.enchantment.BFEnchantments.Config;
import net.minecraft.enchantment.ProtectionEnchantment;
import net.minecraft.entity.damage.DamageSource;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ProtectionEnchantment.class)
public class ProtectionMagicBypass {
	@Inject(method = "getProtectionAmount", at = @At(value = "RETURN", ordinal = 1), cancellable = true)
	public void magicBypass(int level, DamageSource source, CallbackInfoReturnable<Integer> cir) {
		if(!Config.protectionMagicBypass) return;
		if(!source.isMagic()) return;
		cir.setReturnValue(0);
	}
}
