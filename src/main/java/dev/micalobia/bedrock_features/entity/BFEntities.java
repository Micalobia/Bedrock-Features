package dev.micalobia.bedrock_features.entity;

import dev.micalobia.bedrock_features.config.BFConfig;
import dev.micalobia.bedrock_features.entity.mob.CreeperEntityProxy;
import dev.micalobia.bedrock_features.entity.mob.ShulkerEntityProxy;

public class BFEntities {
	public static void init() {
		BFConfig.CHANGED.register(BFEntities::onConfigChange);
	}

	private static void onConfigChange(BFConfig config) {
		CreeperEntityProxy.setHeadsDroppedLimit(config.chargedCreeperHeadLimit);
		ShulkerEntityProxy.setShulkersDyeable(config.areShulkersDyeable);
		ShulkerEntityProxy.setSurvivalDyeAllowed(config.areShulkersDyeableInSurvival);
	}
}
