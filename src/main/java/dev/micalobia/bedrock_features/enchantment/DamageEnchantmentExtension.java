package dev.micalobia.bedrock_features.enchantment;

public class DamageEnchantmentExtension {
	private static boolean sharpnessBuffed;

	public static boolean isSharpnessBuffed() {
		return sharpnessBuffed;
	}

	public static void setSharpnessBuffed(boolean sharpnessBuffed) {
		DamageEnchantmentExtension.sharpnessBuffed = sharpnessBuffed;
	}
}
