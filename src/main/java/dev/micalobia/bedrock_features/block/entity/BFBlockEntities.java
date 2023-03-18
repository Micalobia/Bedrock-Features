package dev.micalobia.bedrock_features.block.entity;

import dev.micalobia.bedrock_features.block.BFBlocks;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.Block;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class BFBlockEntities {
	public static BlockEntityType<DyeCauldronBlockEntity> DYE_CAULDRON;
	public static BlockEntityType<PotionCauldronBlockEntity> POTION_CAULDRON;

	public static void init() {
		DYE_CAULDRON = register(BFBlocks.DYE_CAULDRON);
		POTION_CAULDRON = register(BFBlocks.POTION_CAULDRON);
	}

	@SuppressWarnings("unchecked")
	public static <E extends BlockEntity, B extends Block & BlockEntityProvider> BlockEntityType<E> register(B block) {
		return Registry.register(
				Registries.BLOCK_ENTITY_TYPE,
				Registries.BLOCK.getId(block),
				FabricBlockEntityTypeBuilder.create(
						(pos, state) -> (E) block.createBlockEntity(pos, state), block
				).build()
		);
	}
}
