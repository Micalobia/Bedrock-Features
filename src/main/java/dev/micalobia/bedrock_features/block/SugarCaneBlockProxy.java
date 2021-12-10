package dev.micalobia.bedrock_features.block;

public class SugarCaneBlockProxy {
	private static boolean canBeBonemealed;

	public static boolean canBeBonemealed() {
		return canBeBonemealed;
	}

	public static void setCanBeBonemealed(boolean canBeBonemealed) {
		SugarCaneBlockProxy.canBeBonemealed = canBeBonemealed;
	}
}
