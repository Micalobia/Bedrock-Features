package dev.micalobia.bedrock_features.block;

import dev.micalobia.bedrock_features.BedrockFeatures;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.util.registry.Registry;

public class BFBlocks {
	public static DyeCauldronBlock DYE_CAULDRON;

	public static void init() {
		DyeCauldronBlock.Behaviors.register();
		DYE_CAULDRON = register("dye_cauldron", new DyeCauldronBlock(FabricBlockSettings.copyOf(Blocks.CAULDRON)));
	}

	@Environment(EnvType.CLIENT)
	public static void clientInit() {
		ColorProviderRegistry.BLOCK.register(DyeCauldronBlock::colorProvider, DYE_CAULDRON);
	}

	public static <T extends Block> T register(String name, T block) {
		return Registry.register(Registry.BLOCK, BedrockFeatures.id(name), block);
	}
}
