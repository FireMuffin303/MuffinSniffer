package firemuffin303.muffinsniffer.client;

import firemuffin303.muffinsniffer.block.ModBlocks;
import firemuffin303.muffinsniffer.client.render.entity.SnifferEntityRenderer;
import firemuffin303.muffinsniffer.client.render.entity.model.SnifferEntityModel;
import firemuffin303.muffinsniffer.entity.ModEntities;
import firemuffin303.muffinsniffer.item.ModItems;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.block.BlockState;
import net.minecraft.client.color.block.BlockColors;
import net.minecraft.client.color.world.BiomeColors;
import net.minecraft.client.color.world.FoliageColors;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.item.BlockItem;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockRenderView;

@Environment(EnvType.CLIENT)
public class MuffinSnifferClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        EntityRendererRegistry.register(ModEntities.SNIFFER, SnifferEntityRenderer::new);
        EntityModelLayerRegistry.registerModelLayer(SnifferEntityRenderer.SNIFFER, SnifferEntityModel::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(SnifferEntityRenderer.SNIFFER_SADDLE, SnifferEntityModel::getTexturedModelData);

        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.ANCIENT_GRASS, RenderLayer.getCutoutMipped());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.TALL_ANCIENT_GRASS, RenderLayer.getCutoutMipped());

    }
}
