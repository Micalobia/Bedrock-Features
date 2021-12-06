package dev.micalobia.bedrock_features.block;

import dev.micalobia.bedrock_features.BedrockFeatures;
import net.minecraft.stat.StatFormatter;
import net.minecraft.stat.Stats;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class BFStats {
	public static final Identifier DYE_ARMOR = register("dye_armor", StatFormatter.DEFAULT);
	public static final Identifier DYE_WATER = register("dye_water", StatFormatter.DEFAULT);
	public static final Identifier TIP_ARROWS = register("tip_arrows", StatFormatter.DEFAULT);

	public static Identifier register(String name, StatFormatter formatter) {
		Identifier identifier = BedrockFeatures.id(name);
		Registry.register(Registry.CUSTOM_STAT, identifier, identifier);
		Stats.CUSTOM.getOrCreateStat(identifier, formatter);
		return identifier;
	}
}
