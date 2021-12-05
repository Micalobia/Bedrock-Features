package dev.micalobia.bedrock_features;

import dev.micalobia.bedrock_features.block.BFBlocks;
import dev.micalobia.bedrock_features.block.entity.BFBlockEntities;
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
		BFBlocks.init();
		BFBlockEntities.init();
	}
}
