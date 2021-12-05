package dev.micalobia.bedrock_features.util;

import net.minecraft.util.DyeColor;

import java.util.Arrays;
import java.util.Collection;

public interface Dyeable {
	boolean hasColor();

	int getColor();

	void setColor(int color);

	void removeColor();

	default void setRGB(int red, int green, int blue) {
		int color = (red & 255) << 16 | (green & 255) << 8 | (blue & 255);
		setColor(color);
	}

	default void blendAndSetColor(DyeColor... colors) {
		blendAndSetColor(Arrays.asList(colors));
	}

	default void blendAndSetColor(Collection<DyeColor> colors) {
		int[] RGB = new int[3];
		int value = 0;
		int colorCount = 0;
		if(hasColor()) {
			int color = 0;
			color = getColor();
			int red = color >> 16 & 255;
			int green = color >> 8 & 255;
			int blue = color & 255;
			value = Math.max(red, Math.max(green, blue));
			RGB[0] = red;
			RGB[1] = green;
			RGB[2] = blue;
			++colorCount;
		}

		for(DyeColor color : colors) {
			float[] DyeRGB = color.getColorComponents();
			int red = (int) (DyeRGB[0] * 255f);
			int green = (int) (DyeRGB[1] * 255f);
			int blue = (int) (DyeRGB[2] * 255f);
			value += Math.max(red, Math.max(green, blue));
			RGB[0] += red;
			RGB[1] += green;
			RGB[2] += blue;
			++colorCount;
		}
		int avgRed = RGB[0] / colorCount;
		int avgGreen = RGB[1] / colorCount;
		int avgBlue = RGB[2] / colorCount;
		int avgValue = value / colorCount;

		int maxAvg = Math.max(avgRed, Math.max(avgGreen, avgBlue));
		float gainFactor = (float) avgValue / (float) maxAvg;
		int endRed = (int) (avgRed * gainFactor);
		int endGreen = (int) (avgGreen * gainFactor);
		int endBlue = (int) (avgBlue * gainFactor);
		setRGB(endRed, endGreen, endBlue);
	}

}
