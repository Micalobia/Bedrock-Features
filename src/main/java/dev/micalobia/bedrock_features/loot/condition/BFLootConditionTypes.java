package dev.micalobia.bedrock_features.loot.condition;

import dev.micalobia.bedrock_features.BedrockFeatures;
import dev.micalobia.bedrock_features.config.BFConfig;
import net.minecraft.loot.condition.LootCondition;
import net.minecraft.loot.condition.LootConditionType;
import net.minecraft.util.JsonSerializer;
import net.minecraft.util.registry.Registry;

public class BFLootConditionTypes {
	public static LootConditionType CONFIG;

	private static LootConditionType register(String name, JsonSerializer<? extends LootCondition> serializer) {
		return Registry.register(Registry.LOOT_CONDITION_TYPE, BedrockFeatures.id(name), new LootConditionType(serializer));
	}

	public static void init() {
		CONFIG = register("config", new BFConfigLootCondition.Serializer());
		BFConfig.CHANGED.register(BFLootConditionTypes::onConfigChanged);
	}

	private static void onConfigChanged(BFConfig config) {
		Config.silkablePaths = config.areDirtPathsSilkable;
	}

	public static class Config {
		public static boolean silkablePaths;

		private Config() {
		}
	}
}
