package dev.micalobia.bedrock_features.block.entity;

import dev.micalobia.micalibria.block.entity.MBlockEntity;
import dev.micalobia.micalibria.util.Dyeable;
import net.minecraft.block.BlockState;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.BlockPos;

public class DyeCauldronBlockEntity extends MBlockEntity implements Dyeable {
	int color;

	public DyeCauldronBlockEntity(BlockPos pos, BlockState state) {
		super(BFBlockEntities.DYE_CAULDRON, pos, state);
		color = -1;
	}

	@Override
	public boolean hasColor() {
		return color >= 0;
	}

	@Override
	public int getColor() {
		return hasColor() ? color : 0x385DC6; // Default color of water
	}

	@Override
	public void setColor(int color) {
		this.color = color & 0xffffff;
	}

	@Override
	public void removeColor() {
		this.color = -1;
	}

	@Override
	public void writeToNbt(NbtCompound nbt) {
		nbt.putInt("color", color);
	}

	@Override
	public void readFromNbt(NbtCompound nbt) {
		color = nbt.getInt("color");
	}

	@Override
	public void writeToClientNbt(NbtCompound nbt) {
		nbt.putInt("color", color);
	}

	@Override
	public void readFromClientNbt(NbtCompound nbt) {
		color = nbt.getInt("color");
		remesh();
	}
}
