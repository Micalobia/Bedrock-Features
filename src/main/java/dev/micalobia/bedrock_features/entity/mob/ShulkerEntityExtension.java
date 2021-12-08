package dev.micalobia.bedrock_features.entity.mob;

public class ShulkerEntityExtension {
	private static boolean areShulkersDyeable;
	private static boolean survivalDyeAllowed;


	public static boolean areShulkersDyeable() {
		return areShulkersDyeable;
	}

	public static void setShulkersDyeable(boolean areShulkersDyeable) {
		ShulkerEntityExtension.areShulkersDyeable = areShulkersDyeable;
	}

	public static boolean isSurvivalDyeAllowed() {
		return survivalDyeAllowed;
	}

	public static void setSurvivalDyeAllowed(boolean survivalDyeAllowed) {
		ShulkerEntityExtension.survivalDyeAllowed = survivalDyeAllowed;
	}
}
