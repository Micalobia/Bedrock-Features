package dev.micalobia.bedrock_features.enchantment;

public class ProtectionEnchantmentProxy {
	private static boolean doesMagicBypass;

	public static boolean doesMagicBypass() {
		return doesMagicBypass;
	}

	public static void setMagicBypass(boolean doesMagicBypass) {
		ProtectionEnchantmentProxy.doesMagicBypass = doesMagicBypass;
	}
}
