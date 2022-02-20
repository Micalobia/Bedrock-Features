package dev.micalobia.bedrock_features.mixin.features;

import dev.micalobia.bedrock_features.block.BFBlocks.Config;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.CampfireBlock;
import net.minecraft.entity.Entity;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(CampfireBlock.class)
public class CampfireLitByBurningEntity {
	@Shadow
	@Final
	public static BooleanProperty LIT;

	@Inject(method = "onEntityCollision", at = @At("HEAD"))
	private void watchForBurningEntity(BlockState state, World world, BlockPos pos, Entity entity, CallbackInfo ci) {
		if(Config.campfireCanBeLitByBurningEntity && entity.isOnFire() && !state.get(LIT))
			world.setBlockState(pos, state.with(LIT, true), Block.NOTIFY_LISTENERS);
	}
}
