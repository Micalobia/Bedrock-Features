package dev.micalobia.bedrock_features.world.gen.feature;

import dev.micalobia.bedrock_features.BedrockFeatures;
import net.fabricmc.fabric.api.event.registry.RegistryEntryAddedCallback;
import net.minecraft.block.Block;
import net.minecraft.block.FlowerBlock;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.stateprovider.BlockStateProvider;

import java.util.HashMap;
import java.util.Map;

public class BFFeatures {
	private static final Map<Identifier, ConfiguredFeature<RandomPatchFeatureConfig, ?>> FLOWER_FEATURES = new HashMap<>();

	public static ConfiguredFeature<RandomPatchFeatureConfig, ?> getFlowerFeature(FlowerBlock block) {
		return FLOWER_FEATURES.get(Registry.BLOCK.getId(block));
	}

	private static <FC extends FeatureConfig> ConfiguredFeature<FC, ?> register(String name, ConfiguredFeature<FC, ?> configuredFeature) {
		var id = BedrockFeatures.id(name);
		BedrockFeatures.LOGGER.info(id);
		return Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, id, configuredFeature);
	}

	private static ConfiguredFeature<SimpleBlockFeatureConfig, ?> simple(Block block) {
		return Feature.SIMPLE_BLOCK.configure(new SimpleBlockFeatureConfig(BlockStateProvider.of(block)));
	}

	private static ConfiguredFeature<RandomPatchFeatureConfig, ?> random(Block block) {
		return Feature.RANDOM_PATCH.configure(ConfiguredFeatures.createRandomPatchFeatureConfig(simple(block)));
	}

	private static void registerIfFlower(int raw, Identifier id, Block block) {
		registerIfFlower(block);
	}

	private static void registerIfFlower(Block block) {
		if(!(block instanceof FlowerBlock)) return;
		Identifier id = Registry.BLOCK.getId(block);
		FLOWER_FEATURES.put(id, register(id.getNamespace() + "/" + id.getPath(), random(block)));
	}

	public static void init() {
		Registry.BLOCK.forEach(BFFeatures::registerIfFlower);
		RegistryEntryAddedCallback.event(Registry.BLOCK).register(BFFeatures::registerIfFlower);
	}
}
