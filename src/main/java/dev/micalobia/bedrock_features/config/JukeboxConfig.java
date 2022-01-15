package dev.micalobia.bedrock_features.config;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import dev.micalobia.bedrock_features.BedrockFeatures;
import net.fabricmc.fabric.api.resource.IdentifiableResourceReloadListener;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.resource.JsonDataLoader;
import net.minecraft.resource.ResourceManager;
import net.minecraft.resource.ResourceType;
import net.minecraft.util.Identifier;
import net.minecraft.util.profiler.Profiler;
import net.minecraft.util.registry.Registry;

import java.util.HashMap;
import java.util.Map;

public class JukeboxConfig {
	private static final HashMap<Identifier, Integer> tickCounts = new HashMap<>();

	public static void init() {
		ResourceManagerHelper.get(ResourceType.SERVER_DATA).registerReloadListener(new Listener());
	}

	public static int getTickCount(ItemStack record) {
		return tickCounts.getOrDefault(Registry.ITEM.getId(record.getItem()), Integer.MAX_VALUE);
	}

	private static class Listener extends JsonDataLoader implements IdentifiableResourceReloadListener {
		public Listener() {
			super(new GsonBuilder().create(), "jukebox");
		}

		@Override
		protected void apply(Map<Identifier, JsonElement> prepared, ResourceManager manager, Profiler profiler) {
			tickCounts.clear();
			for(var files : prepared.keySet()) {
				try {
					var element = prepared.get(files);
					var obj = element.getAsJsonObject();
					for(var discId : obj.keySet()) {
						Identifier id = new Identifier(discId);
						int value = obj.get(discId).getAsInt();
						tickCounts.put(id, value);
					}
				} catch(Exception e) {
					e.printStackTrace();
				}
			}
		}

		@Override
		public Identifier getFabricId() {
			return BedrockFeatures.id("jukebox");
		}
	}
}
