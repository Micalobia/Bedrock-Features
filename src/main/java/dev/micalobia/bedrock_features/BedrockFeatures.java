package dev.micalobia.bedrock_features;

import dev.micalobia.bedrock_features.block.BFBlocks;
import dev.micalobia.bedrock_features.block.entity.BFBlockEntities;
import dev.micalobia.bedrock_features.config.BFConfig;
import dev.micalobia.bedrock_features.enchantment.BFEnchantments;
import dev.micalobia.bedrock_features.entity.BFEntities;
import dev.micalobia.bedrock_features.item.BFItems;
import dev.micalobia.bedrock_features.loot.condition.BFLootConditionTypes;
import dev.micalobia.bedrock_features.stat.BFStats;
import net.fabricmc.api.ModInitializer;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BedrockFeatures implements ModInitializer {
	public static final String MODID = "bedrock_features";
	public static final Logger LOGGER = LoggerFactory.getLogger(MODID);

	public static Identifier id(String name) {
		return new Identifier(MODID, name);
	}

	@Override
	public void onInitialize() {
		BFConfig.init();
		BFBlocks.init();
		BFBlockEntities.init();
		BFItems.init();
		BFEnchantments.init();
		BFEntities.init();
		BFLootConditionTypes.init();
		BFStats.init();
		BFConfig.lateinit();
	}
}
