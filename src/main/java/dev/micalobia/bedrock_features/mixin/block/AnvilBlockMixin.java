package dev.micalobia.bedrock_features.mixin.block;

import dev.micalobia.bedrock_features.block.AnvilBlockProxy;
import net.minecraft.block.AnvilBlock;
import net.minecraft.block.piston.PistonBehavior;
import org.spongepowered.asm.mixin.Mixin;

import java.util.Optional;

@Mixin(AnvilBlock.class)
public class AnvilBlockMixin extends AbstractBlockMixin {
	@Override
	public Optional<PistonBehavior> Bedrock$changePistonBehavior() {
		return AnvilBlockProxy.isPushable() ? Optional.of(PistonBehavior.NORMAL) : super.Bedrock$changePistonBehavior();
	}
}
