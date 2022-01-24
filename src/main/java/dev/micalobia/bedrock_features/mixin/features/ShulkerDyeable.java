package dev.micalobia.bedrock_features.mixin.features;

import dev.micalobia.bedrock_features.entity.BFEntities.Config;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.mob.Monster;
import net.minecraft.entity.mob.ShulkerEntity;
import net.minecraft.entity.passive.GolemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.DyeItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(ShulkerEntity.class)
public abstract class ShulkerDyeable extends GolemEntity implements Monster {
	@Shadow
	@Final
	protected static TrackedData<Byte> COLOR;

	protected ShulkerDyeable(EntityType<? extends GolemEntity> entityType, World world) {
		super(entityType, world);
	}

	@Override
	public ActionResult interactMob(PlayerEntity player, Hand hand) {
		if(!Config.shulkerDyeable) return super.interactMob(player, hand);
		if(!Config.shulkerSurvivalDyeable && !player.getAbilities().creativeMode)
			return super.interactMob(player, hand);
		ItemStack handStack = player.getStackInHand(hand);
		Item item = handStack.getItem();
		if(!(item instanceof DyeItem dyeItem)) return super.interactMob(player, hand);
		if(player.world.isClient) return ActionResult.SUCCESS;
		getDataTracker().set(COLOR, (byte) dyeItem.getColor().ordinal());
		return ActionResult.CONSUME;
	}
}
