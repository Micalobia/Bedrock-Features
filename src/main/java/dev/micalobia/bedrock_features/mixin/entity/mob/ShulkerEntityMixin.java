package dev.micalobia.bedrock_features.mixin.entity.mob;

import dev.micalobia.bedrock_features.entity.mob.ShulkerEntityProxy;
import dev.micalobia.bedrock_features.mixin.entity.EntityMixin;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.mob.ShulkerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.DyeItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ShulkerEntity.class)
public abstract class ShulkerEntityMixin extends EntityMixin {
	@Shadow
	@Final
	protected static TrackedData<Byte> COLOR;

	@Override
	public void changeInteraction(PlayerEntity player, Hand hand, CallbackInfoReturnable<ActionResult> cir) {
		if(!ShulkerEntityProxy.areShulkersDyeable()) return;
		if(!ShulkerEntityProxy.isSurvivalDyeAllowed() && !player.getAbilities().creativeMode) return;
		ItemStack handStack = player.getStackInHand(hand);
		Item item = handStack.getItem();
		if(!(item instanceof DyeItem dyeItem)) return;
		if(player.world.isClient) {
			cir.setReturnValue(ActionResult.SUCCESS);
			return;
		}
		getDataTracker().set(COLOR, (byte) dyeItem.getColor().ordinal());
		cir.setReturnValue(ActionResult.CONSUME);
	}
}
