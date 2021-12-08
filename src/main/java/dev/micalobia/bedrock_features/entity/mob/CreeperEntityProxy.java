package dev.micalobia.bedrock_features.entity.mob;

public class CreeperEntityProxy {
	private static int headsDroppedLimit = -1;

	public static int getHeadsDroppedLimit() {
		return headsDroppedLimit;
	}

	public static void setHeadsDroppedLimit(int headsDroppedLimit) {
		CreeperEntityProxy.headsDroppedLimit = headsDroppedLimit;
	}
}
