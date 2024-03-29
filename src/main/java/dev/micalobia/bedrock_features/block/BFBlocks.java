package dev.micalobia.bedrock_features.block;

import dev.micalobia.bedrock_features.BedrockFeatures;
import dev.micalobia.bedrock_features.config.BFConfig;
import dev.micalobia.bedrock_features.state.property.BedrockProperties;
import dev.micalobia.micalibria.block.BlockUtility;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.transfer.v1.item.ItemStorage;
import net.minecraft.block.BellBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.JukeboxBlock;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.state.property.Properties;

public class BFBlocks {
	public static DyeCauldronBlock DYE_CAULDRON;
	public static PotionCauldronBlock POTION_CAULDRON;

	@SuppressWarnings("UnstableApiUsage")
	public static void init() {
		DyeCauldronBlock.Behaviors.init();
		DYE_CAULDRON = register("dye_cauldron", new DyeCauldronBlock(FabricBlockSettings.copyOf(Blocks.CAULDRON)));
		PotionCauldronBlock.Behaviors.init();
		POTION_CAULDRON = register("potion_cauldron", new PotionCauldronBlock(FabricBlockSettings.copyOf(Blocks.CAULDRON)));
		BFConfig.CHANGED.register(BFBlocks::onConfigChanged);
		BlockUtility.injectBlockstateProperty(BellBlock.class, BedrockProperties.RINGING, false);
	}

	private static void onConfigChanged(BFConfig config) {
		Config.sugarcaneCanBeBonemealed = config.isSugarcaneBonemealable;
		Config.anvilCanBePushed = config.areAnvilsPushable;
		Config.dyeCauldronObeysPrecipition = config.doDyeCauldronsObeyPrecipitation;
		Config.potionCauldronObeysPrecipition = config.doPotionCauldronsObeyPrecipitation;
		Config.bellCanBeObserved = config.bellsCanBeObserved;
		Config.flowerCanBeBonemealed = config.areFlowersBonemealable;
	}

	@Environment(EnvType.CLIENT)
	public static void clientInit() {
		ColorProviderRegistry.BLOCK.register(DyeCauldronBlock::colorProvider, DYE_CAULDRON);
		ColorProviderRegistry.BLOCK.register(PotionCauldronBlock::colorProvider, POTION_CAULDRON);
	}

	public static <T extends Block> T register(String name, T block) {
		return BlockUtility.register(BedrockFeatures.id(name), block);
	}

	public static final class Config {
		public static boolean sugarcaneCanBeBonemealed;
		public static boolean anvilCanBePushed;
		public static boolean jukeboxEmitsRedstone;
		public static boolean dyeCauldronObeysPrecipition;
		public static boolean potionCauldronObeysPrecipition;
		public static boolean bellCanBeObserved;
		public static boolean flowerCanBeBonemealed;

		private Config() {
		}
	}
}
