package dev.micalobia.bedrock_features.block;

import dev.micalobia.bedrock_features.BedrockFeatures;
import dev.micalobia.bedrock_features.config.BFConfig;
import dev.micalobia.micalibria.block.entity.BlockUtility;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;

public class BFBlocks {
	public static DyeCauldronBlock DYE_CAULDRON;
	public static PotionCauldronBlock POTION_CAULDRON;

	public static void init() {
		DyeCauldronBlock.Behaviors.init();
		DYE_CAULDRON = register("dye_cauldron", new DyeCauldronBlock(FabricBlockSettings.copyOf(Blocks.CAULDRON)));
		PotionCauldronBlock.Behaviors.init();
		POTION_CAULDRON = register("potion_cauldron", new PotionCauldronBlock(FabricBlockSettings.copyOf(Blocks.CAULDRON)));
		BFConfig.CHANGED.register(BFBlocks::onConfigChanged);
	}

	private static void onConfigChanged(BFConfig config) {
		SugarCaneBlockProxy.setCanBeBonemealed(config.isSugarcaneBonemealable);
		AnvilBlockProxy.setPushable(config.areAnvilsPushable);
	}

	@Environment(EnvType.CLIENT)
	public static void clientInit() {
		ColorProviderRegistry.BLOCK.register(DyeCauldronBlock::colorProvider, DYE_CAULDRON);
		ColorProviderRegistry.BLOCK.register(PotionCauldronBlock::colorProvider, POTION_CAULDRON);
	}

	public static <T extends Block> T register(String name, T block) {
		return BlockUtility.register(BedrockFeatures.id(name), block);
	}
}
