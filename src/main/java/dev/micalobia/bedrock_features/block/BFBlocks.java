package dev.micalobia.bedrock_features.block;

import dev.micalobia.bedrock_features.BedrockFeatures;
import dev.micalobia.bedrock_features.block.JukeboxBlockProxy.JukeboxStorage;
import dev.micalobia.bedrock_features.config.BFConfig;
import dev.micalobia.bedrock_features.config.JukeboxConfig;
import dev.micalobia.micalibria.block.BlockUtility;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.transfer.v1.item.ItemStorage;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.JukeboxBlock;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.state.property.Properties;

public class BFBlocks {
	public static DyeCauldronBlock DYE_CAULDRON;
	public static PotionCauldronBlock POTION_CAULDRON;

	public static void init() {
		DyeCauldronBlock.Behaviors.init();
		DYE_CAULDRON = register("dye_cauldron", new DyeCauldronBlock(FabricBlockSettings.copyOf(Blocks.CAULDRON)));
		PotionCauldronBlock.Behaviors.init();
		POTION_CAULDRON = register("potion_cauldron", new PotionCauldronBlock(FabricBlockSettings.copyOf(Blocks.CAULDRON)));
		BFConfig.CHANGED.register(BFBlocks::onConfigChanged);
		BlockUtility.injectBlockstateProperty(JukeboxBlock.class, Properties.POWERED, false);
		JukeboxConfig.init();
		//noinspection UnstableApiUsage
		ItemStorage.SIDED.registerForBlockEntity(((entity, direction) -> new JukeboxStorage(entity)), BlockEntityType.JUKEBOX);
	}

	private static void onConfigChanged(BFConfig config) {
		SugarCaneBlockProxy.setCanBeBonemealed(config.isSugarcaneBonemealable);
		AnvilBlockProxy.setPushable(config.areAnvilsPushable);
		JukeboxBlockProxy.setEmitsRedstone(config.jukeboxEmitRedstoneWhenPlaying);
		DyeCauldronBlock.obeysPrecipitation = config.doDyeCauldronsObeyPrecipitation;
		PotionCauldronBlock.obeysPrecipitation = config.doPotionCauldronsObeyPrecipitation;
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
