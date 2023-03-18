package dev.micalobia.bedrock_features.mixin.features;

import dev.micalobia.bedrock_features.entity.BFEntities.Config;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.vehicle.CommandBlockMinecartEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(CommandBlockMinecartEntity.class)
public class RepeatingCommandBlockMinecart {
    @Inject(method = "getDefaultContainedBlock", at = @At("HEAD"), cancellable = true)
    private void replaceWithRepeatingCommandBlock(CallbackInfoReturnable<BlockState> cir) {
        if(Config.commandBlockMinecartIsRepeating)
            cir.setReturnValue(Blocks.REPEATING_COMMAND_BLOCK.getDefaultState());
    }
}