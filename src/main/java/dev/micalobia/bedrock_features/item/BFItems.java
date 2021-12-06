package dev.micalobia.bedrock_features.item;

import dev.micalobia.bedrock_features.block.BFBlocks;
import net.minecraft.item.Item;
import net.minecraft.item.Items;

public class BFItems {
	public static void init() {
		Item.BLOCK_ITEMS.put(BFBlocks.DYE_CAULDRON, Items.CAULDRON);
		Item.BLOCK_ITEMS.put(BFBlocks.POTION_CAULDRON, Items.CAULDRON);
	}
}
