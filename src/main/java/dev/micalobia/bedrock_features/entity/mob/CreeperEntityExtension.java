package dev.micalobia.bedrock_features.entity.mob;

public class CreeperEntityExtension {
	private static int headsDroppedLimit = -1;

	public static int getHeadsDroppedLimit() {
		return headsDroppedLimit;
	}

	public static void setHeadsDroppedLimit(int headsDroppedLimit) {
		CreeperEntityExtension.headsDroppedLimit = headsDroppedLimit;
	}
}
