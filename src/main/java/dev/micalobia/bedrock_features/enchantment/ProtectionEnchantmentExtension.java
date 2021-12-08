package dev.micalobia.bedrock_features.enchantment;

public class ProtectionEnchantmentExtension {
	private static boolean doesMagicBypass;

	public static boolean doesMagicBypass() {
		return doesMagicBypass;
	}

	public static void setMagicBypass(boolean doesMagicBypass) {
		ProtectionEnchantmentExtension.doesMagicBypass = doesMagicBypass;
	}
}
