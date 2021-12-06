package dev.micalobia.bedrock_features.block.entity;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.BlockState;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionUtil;
import net.minecraft.potion.Potions;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;

import java.util.Collection;
import java.util.List;

public class PotionCauldronBlockEntity extends BFBlockEntity {
	protected Potion potion;
	protected List<StatusEffectInstance> customEffects;
	protected int customColor;

	public PotionCauldronBlockEntity(BlockPos pos, BlockState state) {
		super(BFBlockEntities.POTION_CAULDRON, pos, state);
		potion = Potions.EMPTY;
		customEffects = List.of();
		customColor = -1;
	}

	public Potion getPotion() {
		return this.potion;
	}

	public void setPotion(Potion potion) {
		this.potion = potion == null ? Potions.EMPTY : potion;
	}

	public List<StatusEffectInstance> getCustomEffects() {
		return this.customEffects;
	}

	public void setCustomEffects(Collection<StatusEffectInstance> customEffects) {
		this.customEffects = List.copyOf(customEffects);
	}

	public int getCustomColor() {
		return this.customColor < 0 ? 0xF800F8 : this.customColor;
	}

	public void setCustomColor(int customColor) {
		this.customColor = customColor & 0xffffff;
	}

	public void removeCustomColor() {
		this.customColor = -1;
	}

	public boolean hasCustomColor() {
		return customColor >= 0;
	}

	@Override
	public void writeToNbt(NbtCompound nbt) {
		nbt.putString("Potion", Registry.POTION.getId(this.potion).toString());
		nbt.putInt("Color", this.customColor);
		NbtList list = new NbtList();
		for(StatusEffectInstance effect : this.customEffects) list.add(effect.writeNbt(new NbtCompound()));
		nbt.put("CustomPotionEffects", list);
	}

	@Override
	public void readFromNbt(NbtCompound nbt) {
		this.setPotion(PotionUtil.getPotion(nbt));
		this.setCustomEffects(PotionUtil.getCustomPotionEffects(nbt));
		this.customColor = nbt.getInt("Color");
	}

	@Override
	public void writeToClientNbt(NbtCompound nbt) {
		nbt.putInt("Color", this.hasCustomColor() ? this.getCustomColor() : PotionUtil.getColor(this.potion));
	}

	@Override
	public void readFromClientNbt(NbtCompound nbt) {
		customColor = nbt.getInt("Color");
		remesh();
	}

	@Environment(EnvType.CLIENT)
	public int getRenderColor() {
		return customColor;
	}
}
