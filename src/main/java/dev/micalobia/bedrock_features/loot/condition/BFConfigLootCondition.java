package dev.micalobia.bedrock_features.loot.condition;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import dev.micalobia.bedrock_features.loot.condition.BFLootConditionTypes.Config;
import net.minecraft.loot.condition.LootCondition;
import net.minecraft.loot.condition.LootConditionType;
import net.minecraft.loot.context.LootContext;
import net.minecraft.util.JsonSerializer;

import java.util.Arrays;
import java.util.Optional;
import java.util.function.BooleanSupplier;

public class BFConfigLootCondition implements LootCondition {
	private final Condition check;

	private BFConfigLootCondition(Condition check) {
		this.check = check;
	}

	@Override
	public LootConditionType getType() {
		return BFLootConditionTypes.CONFIG;
	}

	@Override
	public boolean test(LootContext lootContext) {
		return check.test();
	}

	private enum Condition {
		SILK_PATH("silk_path", () -> Config.silkablePaths),
		NEVER("never", () -> false);

		public final String name;
		private final BooleanSupplier test;

		Condition(String name, BooleanSupplier test) {
			this.name = name;
			this.test = test;
		}

		public static Condition fromName(String name) {
			return Arrays.stream(values()).filter(x -> x.name.equals(name)).findFirst().orElse(Condition.NEVER);
		}

		private static boolean never() {
			return false;
		}

		public boolean test() {
			return test.getAsBoolean();
		}
	}

	public static class Serializer implements JsonSerializer<BFConfigLootCondition> {
		@Override
		public void toJson(JsonObject json, BFConfigLootCondition object, JsonSerializationContext context) {
			json.addProperty("check", object.check.name);
		}

		@Override
		public BFConfigLootCondition fromJson(JsonObject json, JsonDeserializationContext context) {
			return new BFConfigLootCondition(Condition.fromName(Optional.ofNullable(json.get("check")).map(JsonElement::getAsString).orElse("never")));
		}
	}
}
