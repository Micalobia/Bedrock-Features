package dev.micalobia.bedrock_features.entity;

import dev.micalobia.bedrock_features.config.BFConfig;

public class BFEntities {
	public static void init() {
		BFConfig.CHANGED.register(BFEntities::onConfigChange);
	}

	private static void onConfigChange(BFConfig config) {
		Config.creeperHeadDropLimit = config.chargedCreeperHeadLimit;
		Config.shulkerDyeable = config.areShulkersDyeable;
		Config.shulkerSurvivalDyeable = config.areShulkersDyeableInSurvival;
		Config.skeletonHorseNeedsSaddle = config.doSkeletonHorsesNeedSaddle;
		Config.commandBlockMinecartIsRepeating = config.areCommandBlockMinecartsRepeating;
	}

	public static final class Config {
		public static int creeperHeadDropLimit;
		public static boolean shulkerDyeable;
		public static boolean shulkerSurvivalDyeable;
		public static boolean skeletonHorseNeedsSaddle;
		public static boolean commandBlockMinecartIsRepeating;

		private Config() {
		}
	}
}
