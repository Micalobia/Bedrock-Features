package dev.micalobia.bedrock_features.mixin.block;

import dev.micalobia.bedrock_features.block.BellBlockProxy;
import dev.micalobia.bedrock_features.state.property.BedrockProperties;
import net.minecraft.block.BellBlock;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BellBlock.class)
public class BellBlockMixin {
	@Inject(
			method = "ring(Lnet/minecraft/entity/Entity;Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/util/math/Direction;)Z",
			at = @At(value = "INVOKE", target = "Lnet/minecraft/block/entity/BellBlockEntity;activate(Lnet/minecraft/util/math/Direction;)V", ordinal = 0)
	)
	public void Bedrock$startRingingState(Entity entity, World world, BlockPos pos, Direction direction, CallbackInfoReturnable<Boolean> cir) {
		if(!BellBlockProxy.canBeObserved) return;
		var state = world.getBlockState(pos);
		world.setBlockState(pos, state.with(BedrockProperties.RINGING, true), Block.NOTIFY_LISTENERS);
	}
}
