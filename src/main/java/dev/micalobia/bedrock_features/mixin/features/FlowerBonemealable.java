package dev.micalobia.bedrock_features.mixin.features;

import dev.micalobia.bedrock_features.block.BFBlocks.Config;
import dev.micalobia.bedrock_features.world.gen.feature.BFFeatures;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.Fertilizable;
import net.minecraft.block.FlowerBlock;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;

import java.util.Random;

@Mixin(FlowerBlock.class)
public class FlowerBonemealable implements Fertilizable {
	private static BlockPos Bedrock$wander(BlockPos pos, Random random) {
		int x = random.nextInt(3) - 1;
		int y = (random.nextInt(3) - 1) * (random.nextInt(3) / 2);
		int z = random.nextInt(3) - 1;
		return pos.add(x, y, z);
	}

	private FlowerBlock self() {
		return (FlowerBlock) (Object) this;
	}

	@Override
	public boolean isFertilizable(BlockView world, BlockPos pos, BlockState state, boolean isClient) {
		return Config.flowerCanBeBonemealed;
	}

	@Override
	public boolean canGrow(World world, Random random, BlockPos pos, BlockState state) {
		return true;
	}

	@Override
	public void grow(ServerWorld world, Random random, BlockPos pos, BlockState state) {
		var feature = BFFeatures.getFlowerFeature(self()).getConfig().feature().get();
		parent:
		for(int i = 2; i < 16; ++i) {
			var wandered = pos;
			for(int j = 0; j < i / 2; ++j) {
				wandered = Bedrock$wander(wandered, random);
				if(!world.getBlockState(wandered.down()).isOf(Blocks.GRASS_BLOCK)) continue parent;
				if(world.getBlockState(wandered).isFullCube(world, wandered)) continue parent;
			}
			var curState = world.getBlockState(wandered);
			if(!curState.isAir()) continue;
			feature.generateUnregistered(world, world.getChunkManager().getChunkGenerator(), random, wandered);
		}
	}
}
