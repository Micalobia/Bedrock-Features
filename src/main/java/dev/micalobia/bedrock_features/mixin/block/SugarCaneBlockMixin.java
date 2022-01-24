package dev.micalobia.bedrock_features.mixin.block;

import dev.micalobia.bedrock_features.block.BFBlocks.Config;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Fertilizable;
import net.minecraft.block.SugarCaneBlock;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import java.util.Random;

@Mixin(SugarCaneBlock.class)
public class SugarCaneBlockMixin extends Block implements Fertilizable {
	@Shadow
	@Final
	public static IntProperty AGE;

	public SugarCaneBlockMixin(Settings settings) {
		super(settings);
	}

	@Override
	public boolean isFertilizable(BlockView world, BlockPos pos, BlockState state, boolean isClient) {
		if(!Config.sugarcaneCanBeBonemealed) return false;
		BlockPos top = Bedrock$getTop(world, pos);
		int height = Bedrock$getHeight(world, top);
		if(height == 3) return false;
		for(int i = 1; i <= 3 - height; ++i)
			if(!world.getBlockState(top.up(i)).isAir())
				return false;
		return true;
	}

	@Override
	public boolean canGrow(World world, Random random, BlockPos pos, BlockState state) {
		return true;
	}

	@Override
	public void grow(ServerWorld world, Random random, BlockPos pos, BlockState state) {
		BlockPos top = Bedrock$getTop(world, pos);
		world.setBlockState(pos, state.with(AGE, 0), Block.NO_REDRAW);
		int height = Bedrock$getHeight(world, top);
		for(int i = 1; i <= 3 - height; ++i)
			world.setBlockState(top.up(i), getDefaultState());
	}

	public int Bedrock$getHeight(BlockView world, BlockPos pos, int max) {
		BlockState above = world.getBlockState(pos.up());
		BlockPos top = above.isOf(this) ? Bedrock$getTop(world, pos) : pos;
		int height = 1;
		while(height < max && world.getBlockState(top.down(height)).isOf(this)) ++height;
		return height;
	}

	public int Bedrock$getHeight(BlockView world, BlockPos pos) {
		return Bedrock$getHeight(world, pos, 3);
	}

	public BlockPos Bedrock$getTop(BlockView world, BlockPos pos) {
		BlockPos top = pos;
		while(world.getBlockState(top).isOf(this)) top = top.up();
		return top.down();
	}
}
