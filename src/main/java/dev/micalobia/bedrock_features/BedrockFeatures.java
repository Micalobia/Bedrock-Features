package dev.micalobia.bedrock_features;

import dev.micalobia.bedrock_features.block.BFBlocks;
import dev.micalobia.bedrock_features.block.entity.BFBlockEntities;
import dev.micalobia.bedrock_features.config.BFConfig;
import dev.micalobia.bedrock_features.enchantment.BFEnchantments;
import dev.micalobia.bedrock_features.entity.BFEntities;
import dev.micalobia.bedrock_features.item.BFItems;
import net.fabricmc.api.ModInitializer;
import net.minecraft.util.Identifier;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class BedrockFeatures implements ModInitializer {
	public static final String MODID = "bedrock_features";
	public static final Logger LOGGER = LogManager.getLogger(MODID);

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
		BFConfig.lateinit();
	}
}
