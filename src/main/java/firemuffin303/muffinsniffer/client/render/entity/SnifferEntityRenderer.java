package firemuffin303.muffinsniffer.client.render.entity;

import firemuffin303.muffinsniffer.MuffinSniffer;
import firemuffin303.muffinsniffer.client.render.entity.model.SnifferEntityModel;
import firemuffin303.muffinsniffer.entity.SnifferEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.feature.SaddleFeatureRenderer;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class SnifferEntityRenderer extends MobEntityRenderer<SnifferEntity, SnifferEntityModel<SnifferEntity>> {
    public static final EntityModelLayer SNIFFER = new EntityModelLayer(new Identifier(MuffinSniffer.MODID,"sniffer"),"main");
    public static final EntityModelLayer SNIFFER_SADDLE = new EntityModelLayer(new Identifier(MuffinSniffer.MODID,"sniffer"),"saddle");
    private static final Identifier TEXTURE = new Identifier(MuffinSniffer.MODID,"textures/entity/sniffer/sniffer.png");
    public SnifferEntityRenderer(EntityRendererFactory.Context ctx) {
        super(ctx,new SnifferEntityModel(ctx.getPart(SNIFFER)), 0.8F);
        this.addFeature(new SaddleFeatureRenderer(this,new SnifferEntityModel(ctx.getPart(SNIFFER_SADDLE)), new Identifier(MuffinSniffer.MODID,"textures/entity/sniffer/sniffer_saddle.png")));
    }

    @Override
    public Identifier getTexture(SnifferEntity entity) {
        return TEXTURE;
    }
}
