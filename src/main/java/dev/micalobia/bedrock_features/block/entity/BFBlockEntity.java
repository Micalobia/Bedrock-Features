package dev.micalobia.bedrock_features.block.entity;

import com.google.common.base.Preconditions;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.Packet;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;

public abstract class BFBlockEntity extends BlockEntity {
	public BFBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
		super(type, pos, state);
	}

	public abstract void writeToNbt(NbtCompound nbt);

	public abstract void readFromNbt(NbtCompound nbt);

	public abstract void writeToClientNbt(NbtCompound nbt);

	public abstract void readFromClientNbt(NbtCompound nbt);

	@Override
	public final void readNbt(NbtCompound nbt) {
		if(nbt.contains("#c")) readFromClientNbt(nbt);
		else readFromNbt(nbt);
	}

	@Override
	protected final void writeNbt(NbtCompound nbt) {
		writeToNbt(nbt);
	}

	@Override
	public final Packet<ClientPlayPacketListener> toUpdatePacket() {
		return BlockEntityUpdateS2CPacket.create(this);
	}

	@Override
	public final NbtCompound toInitialChunkDataNbt() {
		NbtCompound nbt = super.toInitialChunkDataNbt();
		writeToClientNbt(nbt);
		nbt.putBoolean("#c", true);
		return nbt;
	}

	public final void remesh() {
		remesh(false);
	}

	public final void remesh(boolean important) {
		Preconditions.checkNotNull(world);
		if(!world.isClient)
			throw new IllegalStateException("Cannot call remesh() on the server!");
		world.updateListeners(pos, null, null, important ? 8 : 0);
	}

	public final void sync() {
		Preconditions.checkNotNull(world);
		if(world.isClient)
			throw new IllegalStateException("Cannot call sync() on the logical client!");
		((ServerWorld) world).getChunkManager().markForUpdate(pos);
	}
}
