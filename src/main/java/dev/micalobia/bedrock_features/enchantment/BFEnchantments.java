package dev.micalobia.bedrock_features.enchantment;

import dev.micalobia.bedrock_features.config.BFConfig;

public class BFEnchantments {
	public static void init() {
		BFConfig.CHANGED.register(BFEnchantments::onConfigChanged);
	}

	private static void onConfigChanged(BFConfig config) {
		DamageEnchantmentExtension.setSharpnessBuffed(config.isSharpnessBuffed);
		ProtectionEnchantmentExtension.setMagicBypass(config.doesMagicBypassProtection);
	}
}
