package dev.micalobia.bedrock_features.mixin.features;

import dev.micalobia.bedrock_features.block.BFBlocks.Config;
import dev.micalobia.bedrock_features.state.property.BedrockProperties;
import net.minecraft.block.BellBlock;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BellBlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

public class BellRingState {
	@Mixin(BellBlockEntity.class)
	public static class StopRinging {
		@ModifyVariable(method = "tick", at = @At(value = "CONSTANT", args = "intValue=0", ordinal = 0), argsOnly = true, ordinal = 0, index = 0, name = "blockEntity")
		private static BellBlockEntity Bedrock$stopRingingState(BellBlockEntity self) {
			if(!Config.bellCanBeObserved) return self;
			var world = self.getWorld();
			if(world == null || world.isClient) return self;
			var pos = self.getPos();
			var state = world.getBlockState(pos);
			world.setBlockState(pos, state.with(BedrockProperties.RINGING, false), Block.NOTIFY_LISTENERS);
			return self;
		}
	}

	@Mixin(BellBlock.class)
	public static class StartRinging {
		@Inject(
				method = "ring(Lnet/minecraft/entity/Entity;Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/util/math/Direction;)Z",
				at = @At(value = "INVOKE", target = "Lnet/minecraft/block/entity/BellBlockEntity;activate(Lnet/minecraft/util/math/Direction;)V", ordinal = 0)
		)
		public void Bedrock$startRingingState(Entity entity, World world, BlockPos pos, Direction direction, CallbackInfoReturnable<Boolean> cir) {
			if(!Config.bellCanBeObserved) return;
			var state = world.getBlockState(pos);
			world.setBlockState(pos, state.with(BedrockProperties.RINGING, true), Block.NOTIFY_LISTENERS);
		}
	}
}
