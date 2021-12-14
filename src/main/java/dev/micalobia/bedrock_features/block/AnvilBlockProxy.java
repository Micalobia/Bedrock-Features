package dev.micalobia.bedrock_features.block;

public class AnvilBlockProxy {
	private static boolean pushable;

	public static boolean isPushable() {
		return pushable;
	}

	public static void setPushable(boolean pushable) {
		AnvilBlockProxy.pushable = pushable;
	}
}
