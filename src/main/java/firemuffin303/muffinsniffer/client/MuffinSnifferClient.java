package firemuffin303.muffinsniffer.client;

import firemuffin303.muffinsniffer.block.ModBlocks;
import firemuffin303.muffinsniffer.client.render.entity.SnifferEntityRenderer;
import firemuffin303.muffinsniffer.client.render.entity.model.SnifferEntityModel;
import firemuffin303.muffinsniffer.entity.ModEntities;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.render.RenderLayer;

@Environment(EnvType.CLIENT)
public class MuffinSnifferClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        EntityRendererRegistry.register(ModEntities.SNIFFER, SnifferEntityRenderer::new);
        EntityModelLayerRegistry.registerModelLayer(SnifferEntityRenderer.SNIFFER, SnifferEntityModel::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(SnifferEntityRenderer.SNIFFER_SADDLE, SnifferEntityModel::getTexturedModelData);


        //EntityRendererRegistry.register(ModEntities.SCULK_SNIFFER,SnifferEntityRenderer::new);
        //EntityModelLayerRegistry.registerModelLayer(SculkSnifferEntityRenderer.SCULK_SNIFFER,SnifferEntityModel::getTexturedModelData);

        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.ANCIENT_GRASS, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.TALL_ANCIENT_GRASS, RenderLayer.getCutout());
    }
}
