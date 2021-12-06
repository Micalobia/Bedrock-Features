package dev.micalobia.bedrock_features.item;

import com.mojang.datafixers.util.Pair;
import dev.micalobia.bedrock_features.mixin.item.FoodComponentAccessor;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.FoodComponent;
import net.minecraft.item.FoodComponents;

import java.util.List;

public class BFFoodComponents {
	public static void init() {
		changeStatusEffect(FoodComponents.PUFFERFISH, 0, new StatusEffectInstance(StatusEffects.POISON, 1200, 3));
		changeStatusEffect(FoodComponents.PUFFERFISH, 2, new StatusEffectInstance(StatusEffects.NAUSEA, 300, 1));
	}

	public static void changeStatusEffect(FoodComponent component, int index, StatusEffectInstance effect) {
		List<Pair<StatusEffectInstance, Float>> list = ((FoodComponentAccessor) component).getStatusEffects();
		Pair<StatusEffectInstance, Float> pair = list.get(index);
		list.set(index, Pair.of(effect, pair.getSecond()));
	}
}
