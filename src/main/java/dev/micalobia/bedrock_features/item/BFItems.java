package dev.micalobia.bedrock_features.item;

import dev.micalobia.bedrock_features.block.BFBlocks;
import dev.micalobia.bedrock_features.config.BFConfig;
import dev.micalobia.bedrock_features.mixin.item.ItemAccessor;
import net.minecraft.item.Item;
import net.minecraft.item.Items;

public class BFItems {
	public static void init() {
		Item.BLOCK_ITEMS.put(BFBlocks.DYE_CAULDRON, Items.CAULDRON);
		Item.BLOCK_ITEMS.put(BFBlocks.POTION_CAULDRON, Items.CAULDRON);
		BFConfig.CHANGED.register(BFItems::onConfigChange);
	}

	private static void onConfigChange(BFConfig config) {
		changeStackSize(Items.CAKE, config.areCakesStackable ? 64 : 1);
	}

	public static void changeStackSize(Item item, int count) {
		((ItemAccessor) item).setMaxCount(count);
	}
}
