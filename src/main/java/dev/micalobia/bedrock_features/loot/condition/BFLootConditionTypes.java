package dev.micalobia.bedrock_features.loot.condition;

import dev.micalobia.bedrock_features.BedrockFeatures;
import dev.micalobia.bedrock_features.config.BFConfig;
import net.minecraft.loot.condition.LootCondition;
import net.minecraft.loot.condition.LootConditionType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.JsonSerializer;

public class BFLootConditionTypes {
	public static LootConditionType CONFIG;

	private static LootConditionType register(String name, JsonSerializer<? extends LootCondition> serializer) {
		return Registry.register(Registries.LOOT_CONDITION_TYPE, BedrockFeatures.id(name), new LootConditionType(serializer));
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
