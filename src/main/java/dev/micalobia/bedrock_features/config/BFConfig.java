package dev.micalobia.bedrock_features.config;

import dev.micalobia.bedrock_features.BedrockFeatures;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;
import me.shedaniel.autoconfig.serializer.Toml4jConfigSerializer;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.util.ActionResult;

@Config(name = BedrockFeatures.MODID)
public class BFConfig implements ConfigData {
	@ConfigEntry.Gui.Excluded
	public static transient Event<ChangedConfig> CHANGED = EventFactory.createArrayBacked(ChangedConfig.class,
			(listeners) -> (bfConfig) -> {
				for(var listener : listeners) listener.changed(bfConfig);
			});
	public boolean areDyeCauldronsEnabled = true;
	public boolean arePotionCauldronsEnabled = true;
	public boolean areCakesStackable = true;
	public boolean arePufferfishBuffed = true;
	public boolean isSharpnessBuffed = true;
	public boolean areShulkersDyeable = true;
	public boolean areShulkersDyeableInSurvival = false;
	public boolean doesMagicBypassProtection = false;
	public boolean isSugarcaneBonemealable = true;
	public boolean areAnvilsPushable = true;
	public boolean jukeboxEmitRedstoneWhenPlaying = true;
	public int chargedCreeperHeadLimit = -1;

	private BFConfig() {
	}

	public static void init() {
		AutoConfig.register(BFConfig.class, Toml4jConfigSerializer::new);
		AutoConfig.getConfigHolder(BFConfig.class).registerSaveListener((configHolder, bfConfig) -> {
			CHANGED.invoker().changed(bfConfig);
			return ActionResult.PASS;
		});
	}

	public static void lateinit() {
		CHANGED.invoker().changed(getConfig());
	}

	private static BFConfig getConfig() {
		return AutoConfig.getConfigHolder(BFConfig.class).getConfig();
	}

	public interface ChangedConfig {
		void changed(BFConfig config);
	}
}
