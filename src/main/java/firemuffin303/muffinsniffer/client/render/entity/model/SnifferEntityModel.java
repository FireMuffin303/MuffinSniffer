package firemuffin303.muffinsniffer.client.render.entity.model;

import com.google.common.collect.ImmutableList;
import firemuffin303.muffinsniffer.entity.SnifferEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.AnimalModel;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;

@Environment(EnvType.CLIENT)
public class SnifferEntityModel<T extends Entity> extends AnimalModel<T> {
    protected final ModelPart head;
    protected final ModelPart body;
    protected final ModelPart lowerMouth;
    protected final ModelPart rightMiddleLeg;
    protected final ModelPart rightFrontLeg;
    protected final ModelPart rightHindLeg;
    protected final ModelPart leftFrontLeg;
    protected final ModelPart leftMiddleLeg;
    protected final ModelPart leftHindLeg;
    float pitchModifier;

    public  SnifferEntityModel(ModelPart root) {
        super(true, 25.0F, 0.0F, 3.0F, 2.0F, 24);
        this.head = root.getChild("head");
        this.body = root.getChild("body");
        this.lowerMouth = head.getChild("lower_mouth");
        this.rightFrontLeg = root.getChild("right_front_leg");
        this.rightMiddleLeg = root.getChild("right_middle_leg");
        this.rightHindLeg = root.getChild("right_hind_leg");
        this.leftFrontLeg = root.getChild("left_front_leg");
        this.leftMiddleLeg = root.getChild("left_middle_leg");
        this.leftHindLeg = root.getChild("left_hind_leg");
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        ModelPartData head = modelPartData.addChild("head", ModelPartBuilder.create().uv(0, 48).cuboid(-5.0F, -7.0F, -8.0F, 10.0F, 12.0F, 8.0F, new Dilation(0.0F))
                .uv(36, 48).cuboid(-5.0F, -7.0F, -14.0F, 10.0F, 8.0F, 6.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 13.0F, -14.0F));
        head.addChild("right_ear", ModelPartBuilder.create().uv(58, 68).cuboid(-0.4914F, -0.5557F, -2.0F, 1.0F, 12.0F, 4.0F, new Dilation(0.0F)), ModelTransform.of(-4.749F, -6.4583F, -3.0F, 0.0F, 0.0F, 0.1745F));
        head.addChild("left_ear", ModelPartBuilder.create().uv(24, 68).cuboid(-0.5F, 0.1305F, -2.0F, 1.0F, 12.0F, 4.0F, new Dilation(0.0F)), ModelTransform.of(4.704F, -7.1445F, -3.0F, 0.0F, 0.0F, -0.1745F));

        head.addChild("lower_mouth", ModelPartBuilder.create().uv(30, 62).cuboid(-5.0F, -2.0F, -6.0F, 10.0F, 4.0F, 6.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 3.0F, -8.0F));
        modelPartData.addChild("body", ModelPartBuilder.create().uv(0, 0).cuboid(-9.0F, -10.0F, -14.0F, 18.0F, 20.0F, 28.0F, new Dilation(0.0F))
                .uv(0, 88).cuboid(-9.0F, -10.0F, -14.0F, 18.0F, 12.0F, 28.0F, new Dilation(0.25F)), ModelTransform.pivot(0.0F, 8.0F, 0.0F));
        modelPartData.addChild("left_front_leg", ModelPartBuilder.create().uv(64, 12).cuboid(-3.0F, 2.0F, -3.0F, 6.0F, 6.0F, 6.0F, new Dilation(0.0F)), ModelTransform.pivot(5.0F, 16.0F, -11.0F));
        modelPartData.addChild("left_middle_leg", ModelPartBuilder.create().uv(0, 68).cuboid(-3.0F, 2.0F, -3.0F, 6.0F, 6.0F, 6.0F, new Dilation(0.0F)), ModelTransform.pivot(5.0F, 16.0F, 0.0F));
        modelPartData.addChild("left_hind_leg", ModelPartBuilder.create().uv(64, 0).cuboid(-3.0F, 2.0F, -3.0F, 6.0F, 6.0F, 6.0F, new Dilation(0.0F)), ModelTransform.pivot(5.0F, 16.0F, 11.0F));
        modelPartData.addChild("right_hind_leg", ModelPartBuilder.create().uv(0, 0).cuboid(-3.0F, 2.0F, -3.0F, 6.0F, 6.0F, 6.0F, new Dilation(0.0F)), ModelTransform.pivot(-5.0F, 16.0F, 11.0F));
        modelPartData.addChild("right_middle_leg", ModelPartBuilder.create().uv(0, 12).cuboid(-3.0F, 2.0F, -3.0F, 6.0F, 6.0F, 6.0F, new Dilation(0.0F)), ModelTransform.pivot(-5.0F, 16.0F, 0.0F));
        modelPartData.addChild("right_front_leg", ModelPartBuilder.create().uv(62, 56).cuboid(-3.0F, 2.0F, -3.0F, 6.0F, 6.0F, 6.0F, new Dilation(0.0F)), ModelTransform.pivot(-5.0F, 16.0F, -11.0F));
        return TexturedModelData.of(modelData, 128, 128);
    }


    @Override
    protected Iterable<ModelPart> getHeadParts() {
        return ImmutableList.of(this.head);
    }

    @Override
    protected Iterable<ModelPart> getBodyParts() {
        return ImmutableList.of(this.body,this.leftFrontLeg,this.leftMiddleLeg,this.leftHindLeg,this.rightFrontLeg,this.rightMiddleLeg,this.rightHindLeg);
    }

    @Override
    public void animateModel(T entity, float limbAngle, float limbDistance, float tickDelta) {
        super.animateModel(entity, limbAngle, limbDistance, tickDelta);
        if(entity instanceof SnifferEntity snifferEntity) {
            this.pitchModifier = snifferEntity.getHeadAngle(tickDelta);
        }
    }

    @Override
    public void setAngles(T entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
                this.head.pitch = pitchModifier;
                this.head.yaw = headYaw * 0.017453292F;
                this.rightHindLeg.pitch = MathHelper.cos(limbAngle * 0.6662F + 3.1415927F) * 1.4F * limbDistance;
                this.leftHindLeg.pitch = MathHelper.cos(limbAngle * 0.6662F + 3.1415927F) * 1.4F * limbDistance;
                this.leftMiddleLeg.pitch = MathHelper.cos(limbAngle * 0.6662F + 3.1415927F/2) * 1.4F * limbDistance;
                this.rightMiddleLeg.pitch = MathHelper.cos(limbAngle * 0.6662F + 3.1415927F/2) * 1.4F * limbDistance;
                this.rightFrontLeg.pitch = MathHelper.cos(limbAngle * 0.6662F) * 1.4F * limbDistance;
                this.leftFrontLeg.pitch = MathHelper.cos(limbAngle * 0.6662F) * 1.4F * limbDistance;
    }
}
