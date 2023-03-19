package dev.micalobia.bedrock_features.mixin.features;

import dev.micalobia.bedrock_features.entity.passive.SalmonSized;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.SalmonEntityRenderer;
import net.minecraft.client.render.entity.model.SalmonEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.*;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.passive.SalmonEntity;
import net.minecraft.entity.passive.SchoolingFishEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Objects;

public abstract class SalmonSizes {

    @Mixin(SalmonEntityRenderer.class)
    public static abstract class VisuallyChangeSize extends MobEntityRenderer<SalmonEntity, SalmonEntityModel<SalmonEntity>> {
        public VisuallyChangeSize(EntityRendererFactory.Context context, SalmonEntityModel<SalmonEntity> entityModel, float f) {
            super(context, entityModel, f);
        }

        @Inject(method = "setupTransforms(Lnet/minecraft/entity/passive/SalmonEntity;Lnet/minecraft/client/util/math/MatrixStack;FFF)V", at = @At("TAIL"))
        private void BedrockFeatures$changeSize(SalmonEntity salmonEntity, MatrixStack matrixStack, float f, float g, float h, CallbackInfo ci) {
            var size = ((SalmonSized) salmonEntity).BedrockFeatures$getSize();
            float scale = size == 1 ? 2f : size == -1 ? 0.5f : 1;
            matrixStack.scale(scale, scale, scale);
        }
    }

    @Mixin(SalmonEntity.class)
    public static abstract class ChangeSize extends SchoolingFishEntity implements SalmonSized {
        private static final TrackedData<Integer> SIZE = DataTracker.registerData(SalmonEntity.class, TrackedDataHandlerRegistry.INTEGER);

        public ChangeSize(EntityType<? extends SchoolingFishEntity> entityType, World world) {
            super(entityType, world);
        }

        public int BedrockFeatures$getSize() {
            return this.dataTracker.get(SIZE);
        }

        public void BedrockFeatures$setSize(int size) {
            this.dataTracker.set(SIZE, MathHelper.clamp(size, -1, 1));
        }

        private void BedrockFeatures$onSizeChanged() {
            this.calculateDimensions();
        }

        @Override
        protected void initDataTracker() {
            super.initDataTracker();
            this.dataTracker.startTracking(SIZE, 0);
        }

        @Override
        public void onTrackedDataSet(TrackedData<?> data) {
            if (SIZE.equals(data))
                this.BedrockFeatures$onSizeChanged();
            super.onTrackedDataSet(data);
        }

        @Nullable
        @Override
        public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, @Nullable EntityData entityData, @Nullable NbtCompound entityNbt) {
            // Might make it so school leaders are always large fish
            this.BedrockFeatures$setSize(world.getRandom().nextBetween(-1, 1));
            return super.initialize(world, difficulty, spawnReason, entityData, entityNbt);
        }

        @Override
        public void writeCustomDataToNbt(NbtCompound nbt) {
            super.writeCustomDataToNbt(nbt);
            nbt.putInt("Size", this.BedrockFeatures$getSize());
        }

        @Override
        public void readCustomDataFromNbt(NbtCompound nbt) {
            super.readCustomDataFromNbt(nbt);
            this.BedrockFeatures$setSize(nbt.getInt("Size"));
        }

        @Override
        public EntityDimensions getDimensions(EntityPose pose) {
            var dimensions = super.getDimensions(pose);
            var size = this.BedrockFeatures$getSize();
            var scale = size == 1 ? 2f : size == -1 ? 0.5f : 1;
            return dimensions.scaled(scale);
        }
    }
}
