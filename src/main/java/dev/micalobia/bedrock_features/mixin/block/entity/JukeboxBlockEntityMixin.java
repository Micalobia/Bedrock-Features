package dev.micalobia.bedrock_features.mixin.block.entity;

import dev.micalobia.bedrock_features.block.entity.JukeboxBlockEntityExtension;
import net.minecraft.block.entity.JukeboxBlockEntity;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(JukeboxBlockEntity.class)
public class JukeboxBlockEntityMixin implements JukeboxBlockEntityExtension {
	private int Bedrock$ticksPlaying = 0;

	@Inject(method = "setRecord", at = @At("HEAD"))
	private void resetTimer(ItemStack stack, CallbackInfo ci) {
		Bedrock$setTicksPlaying(0);
	}

	@Override
	public int Bedrock$getTicksPlaying() {
		return Bedrock$ticksPlaying;
	}

	@Override
	public void Bedrock$setTicksPlaying(int value) {
		Bedrock$ticksPlaying = value;
	}

	@Override
	public void Bedrock$addTicksPlaying(int value) {
		Bedrock$ticksPlaying += value;
	}
}
