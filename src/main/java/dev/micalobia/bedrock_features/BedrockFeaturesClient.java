package dev.micalobia.bedrock_features;

import dev.micalobia.bedrock_features.block.BFBlocks;
import net.fabricmc.api.ClientModInitializer;

public class BedrockFeaturesClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		BFBlocks.clientInit();
	}
}
