package dev.micalobia.bedrock_features.block.entity;

import dev.micalobia.bedrock_features.block.BFBlocks;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.Block;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.registry.Registry;

public class BFBlockEntities {
	public static BlockEntityType<DyeCauldronBlockEntity> DYE_CAULDRON;

	public static void init() {
		DYE_CAULDRON = register(BFBlocks.DYE_CAULDRON);
	}

	@SuppressWarnings("unchecked")
	public static <E extends BlockEntity, B extends Block & BlockEntityProvider> BlockEntityType<E> register(B block) {
		return Registry.register(
				Registry.BLOCK_ENTITY_TYPE,
				Registry.BLOCK.getId(block),
				FabricBlockEntityTypeBuilder.create(
						(pos, state) -> (E) block.createBlockEntity(pos, state), block
				).build()
		);
	}
}
