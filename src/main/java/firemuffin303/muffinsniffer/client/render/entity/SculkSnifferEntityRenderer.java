package firemuffin303.muffinsniffer.client.render.entity;

import firemuffin303.muffinsniffer.MuffinSniffer;
import firemuffin303.muffinsniffer.client.render.entity.model.SnifferEntityModel;
import firemuffin303.muffinsniffer.entity.SculkSnifferEntity;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.util.Identifier;

public class SculkSnifferEntityRenderer extends MobEntityRenderer<SculkSnifferEntity, SnifferEntityModel<SculkSnifferEntity>> {
    public static final EntityModelLayer SCULK_SNIFFER = new EntityModelLayer(new Identifier(MuffinSniffer.MODID,"sculk_sniffer"),"main");
    private static final Identifier TEXTURE = new Identifier(MuffinSniffer.MODID,"textures/entity/sniffer/sniffer.png");

    public SculkSnifferEntityRenderer(EntityRendererFactory.Context context) {
        super(context, new SnifferEntityModel<>(context.getPart(SCULK_SNIFFER)), 0.8f);
    }

    @Override
    public Identifier getTexture(SculkSnifferEntity entity) {
        return TEXTURE;
    }
}
