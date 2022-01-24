package dev.micalobia.bedrock_features.mixin.block.entity;

import dev.micalobia.bedrock_features.block.BellBlockProxy;
import dev.micalobia.bedrock_features.state.property.BedrockProperties;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BellBlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(BellBlockEntity.class)
public class BellBlockEntityMixin {
	@ModifyVariable(method = "tick", at = @At(value = "CONSTANT", args = "intValue=0", ordinal = 0), argsOnly = true, ordinal = 0, index = 0, name="blockEntity")
	private static BellBlockEntity Bedrock$stopRingingState(BellBlockEntity self) {
		if(!BellBlockProxy.canBeObserved) return self;
		var world = self.getWorld();
		if(world == null || world.isClient) return self;
		var pos = self.getPos();
		var state = world.getBlockState(pos);
		world.setBlockState(pos, state.with(BedrockProperties.RINGING, false), Block.NOTIFY_LISTENERS);
		return self;
	}
}
