package dev.micalobia.bedrock_features.mixin.features;

import dev.micalobia.bedrock_features.block.BFBlocks.Config;
import net.minecraft.block.AnvilBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.piston.PistonBehavior;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(AnvilBlock.class)
public class AnvilPushable extends Block {
	public AnvilPushable(Settings settings) {
		super(settings);
	}

	@Override
	public PistonBehavior getPistonBehavior(BlockState state) {
		return Config.anvilCanBePushed ? PistonBehavior.NORMAL : super.getPistonBehavior(state);
	}
}
