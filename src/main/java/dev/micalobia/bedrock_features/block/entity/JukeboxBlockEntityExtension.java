package dev.micalobia.bedrock_features.block.entity;

public interface JukeboxBlockEntityExtension {
	int Bedrock$getTicksPlaying();

	void Bedrock$setTicksPlaying(int value);

	void Bedrock$addTicksPlaying(int value);
}
