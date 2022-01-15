package dev.micalobia.bedrock_features.block;

public class JukeboxBlockProxy {
	private static boolean emitsRedstone;

	public static boolean getEmitsRedstone() {
		return emitsRedstone;
	}

	public static void setEmitsRedstone(boolean value) {
		emitsRedstone = value;
	}
}
