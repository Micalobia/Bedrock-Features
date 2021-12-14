package dev.micalobia.bedrock_features.item;

import dev.micalobia.bedrock_features.block.BFBlocks;
import dev.micalobia.bedrock_features.config.BFConfig;
import dev.micalobia.micalibria.item.ItemUtility;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Items;

public class BFItems {
	public static void init() {
		ItemUtility.register(BFBlocks.DYE_CAULDRON, (BlockItem) Items.CAULDRON);
		ItemUtility.register(BFBlocks.POTION_CAULDRON, (BlockItem) Items.CAULDRON);
		BFConfig.CHANGED.register(BFItems::onConfigChange);
	}

	private static void onConfigChange(BFConfig config) {
		ItemUtility.setMaxStackSize(Items.CAKE, config.areCakesStackable ? 64 : 1);
	}
}
