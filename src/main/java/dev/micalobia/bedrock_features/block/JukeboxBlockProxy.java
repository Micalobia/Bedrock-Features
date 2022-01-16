package dev.micalobia.bedrock_features.block;

import net.fabricmc.fabric.api.transfer.v1.item.ItemVariant;
import net.fabricmc.fabric.api.transfer.v1.storage.base.SingleSlotStorage;
import net.fabricmc.fabric.api.transfer.v1.transaction.TransactionContext;
import net.minecraft.block.Block;
import net.minecraft.block.JukeboxBlock;
import net.minecraft.block.entity.JukeboxBlockEntity;
import net.minecraft.item.Item;
import net.minecraft.item.MusicDiscItem;
import net.minecraft.state.property.Properties;
import net.minecraft.world.WorldEvents;

public class JukeboxBlockProxy {
	private static boolean emitsRedstone;

	public static boolean getEmitsRedstone() {
		return emitsRedstone;
	}

	public static void setEmitsRedstone(boolean value) {
		emitsRedstone = value;
	}

	@SuppressWarnings("UnstableApiUsage")
	public static class JukeboxStorage implements SingleSlotStorage<ItemVariant> {
		private final JukeboxBlockEntity entity;
		private final ItemVariant record;

		public JukeboxStorage(JukeboxBlockEntity entity) {
			this.entity = entity;
			this.record = ItemVariant.of(entity.getRecord());
		}

		@Override
		public long insert(ItemVariant resource, long maxAmount, TransactionContext transaction) {
			if(maxAmount == 0 || !isResourceBlank()) return 0;
			if(!(resource.getItem() instanceof MusicDiscItem)) return 0;
			var world = entity.getWorld();
			if(world == null) return 0;
			var pos = entity.getPos();
			var state = world.getBlockState(pos);
			var block = state.getBlock();
			if(!(block instanceof JukeboxBlock)) return 0;
			transaction.addCloseCallback(((ctx, result) -> {
				entity.setRecord(resource.toStack());
				world.syncWorldEvent(null, WorldEvents.MUSIC_DISC_PLAYED, pos, Item.getRawId(resource.getItem()));
				world.setBlockState(pos, state.with(JukeboxBlock.HAS_RECORD, true).with(Properties.POWERED, true), Block.NOTIFY_ALL);
			}));
			return 1;
		}

		@Override
		public long extract(ItemVariant resource, long maxAmount, TransactionContext transaction) {
			if(maxAmount == 0 || isResourceBlank()) return 0;
			if(!(resource.equals(getResource()))) return 0;
			var world = entity.getWorld();
			if(world == null || world.isClient) return 0;
			var pos = entity.getPos();
			var state = world.getBlockState(pos);
			var block = state.getBlock();
			if(!(block instanceof JukeboxBlock)) return 0;
			transaction.addCloseCallback((ctx, result) -> {
				if(result.wasAborted()) return;
				entity.clear();
				world.syncWorldEvent(null, WorldEvents.MUSIC_DISC_PLAYED, pos, 0);
				world.setBlockState(pos, state.with(JukeboxBlock.HAS_RECORD, false).with(Properties.POWERED, false), Block.NOTIFY_ALL);
			});
			return 1;
		}

		@Override
		public boolean isResourceBlank() {
			return getResource().isBlank();
		}

		@Override
		public ItemVariant getResource() {
			return record;
		}

		@Override
		public long getAmount() {
			return record.isBlank() ? 0 : 1;
		}

		@Override
		public long getCapacity() {
			return 1;
		}
	}
}
