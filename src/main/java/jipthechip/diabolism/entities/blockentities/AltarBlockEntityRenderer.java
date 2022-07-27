package jipthechip.diabolism.entities.blockentities;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.math.Vec3f;

public class AltarBlockEntityRenderer implements BlockEntityRenderer<AltarBlockEntity> {

    public AltarBlockEntityRenderer(BlockEntityRendererFactory.Context ctx){}

    @Override
    public void render(AltarBlockEntity entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        matrices.push();
        if (entity.getStoredItem() != Items.AIR) {

            // Calculate the current offset in the y value
            double offset = Math.sin((entity.getWorld().getTime() + tickDelta) / 8.0) / 4.0;
            // Move the item
            matrices.translate(0.5, 1.25 + offset, 0.5);

            // Rotate the item
            matrices.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion((entity.getWorld().getTime() + tickDelta) * 4));

            int lightAbove = WorldRenderer.getLightmapCoordinates(entity.getWorld(), entity.getPos().up());
            MinecraftClient.getInstance().getItemRenderer().renderItem(new ItemStack(entity.getStoredItem(), 1), ModelTransformation.Mode.GROUND, lightAbove, OverlayTexture.DEFAULT_UV, matrices, vertexConsumers, 0);

        }
        matrices.pop();
    }
}
