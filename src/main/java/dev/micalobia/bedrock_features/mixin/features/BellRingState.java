package dev.micalobia.bedrock_features.mixin.features;

import dev.micalobia.bedrock_features.block.BFBlocks.Config;
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

public class BellRingState {
	@Mixin(BellBlock.class)
	public static class StartRinging {
		@Inject(
				method = "ring(Lnet/minecraft/entity/Entity;Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/util/math/Direction;)Z",
				at = @At(value = "RETURN", ordinal = 0)
		)
		public void Bedrock$startRingingState(Entity entity, World world, BlockPos pos, Direction direction, CallbackInfoReturnable<Boolean> cir) {
			if(!Config.bellCanBeObserved) return;
			var state = world.getBlockState(pos);
			var prev = state.get(BedrockProperties.RINGING);
			world.setBlockState(pos, state.with(BedrockProperties.RINGING, !prev), Block.NOTIFY_LISTENERS);
		}
	}
}
