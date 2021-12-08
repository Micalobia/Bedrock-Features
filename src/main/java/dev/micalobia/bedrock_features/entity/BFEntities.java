package dev.micalobia.bedrock_features.entity;

import dev.micalobia.bedrock_features.config.BFConfig;
import dev.micalobia.bedrock_features.entity.mob.CreeperEntityExtension;

public class BFEntities {
	public static void init() {
		BFConfig.CHANGED.register(BFEntities::onConfigChange);
	}

	private static void onConfigChange(BFConfig config) {
		CreeperEntityExtension.setHeadsDroppedLimit(config.chargedCreeperHeadLimit);
	}
}
