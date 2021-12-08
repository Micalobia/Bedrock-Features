package dev.micalobia.bedrock_features.entity.mob;

public class ShulkerEntityProxy {
	private static boolean areShulkersDyeable;
	private static boolean survivalDyeAllowed;


	public static boolean areShulkersDyeable() {
		return areShulkersDyeable;
	}

	public static void setShulkersDyeable(boolean areShulkersDyeable) {
		ShulkerEntityProxy.areShulkersDyeable = areShulkersDyeable;
	}

	public static boolean isSurvivalDyeAllowed() {
		return survivalDyeAllowed;
	}

	public static void setSurvivalDyeAllowed(boolean survivalDyeAllowed) {
		ShulkerEntityProxy.survivalDyeAllowed = survivalDyeAllowed;
	}
}
