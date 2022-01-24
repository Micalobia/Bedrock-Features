package dev.micalobia.bedrock_features.enchantment;

import dev.micalobia.bedrock_features.config.BFConfig;

public class BFEnchantments {
	public static void init() {
		BFConfig.CHANGED.register(BFEnchantments::onConfigChanged);
	}

	private static void onConfigChanged(BFConfig config) {
		Config.sharpnessBuffed = config.isSharpnessBuffed;
		Config.protectionMagicBypass = config.doesMagicBypassProtection;
	}

	public static final class Config {
		public static boolean sharpnessBuffed;
		public static boolean protectionMagicBypass;

		private Config() {
		}
	}
}
