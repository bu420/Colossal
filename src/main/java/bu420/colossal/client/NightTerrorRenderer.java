package bu420.colossal.client;

import bu420.colossal.Main;
import bu420.colossal.entity.NightTerror;
import bu420.colossal.entity.SeaSerpent;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class NightTerrorRenderer extends EntityRenderer<NightTerror> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(Main.MODID, "textures/entities/night_terror.png");
    private static final RenderType OUTER_TRANSLUCENT = RenderType.entityTranslucent(new ResourceLocation(Main.MODID, "textures/entities/night_terror_outer_translucent.png"));
    private static final RenderType SKELETON = RenderType.beaconBeam(new ResourceLocation(Main.MODID, "textures/entities/night_terror_skeleton.png"), true);
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(Main.MODID, "night_terror"), "main");

    private final ModelPart root;

    public NightTerrorRenderer(EntityRendererProvider.Context context) {
        super(context);
        root = context.bakeLayer(LAYER_LOCATION);
    }

    public static LayerDefinition createLayerDefinition() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition head = partdefinition.addOrReplaceChild("head", CubeListBuilder.create(), PartPose.offset(0.0F, 14.0F, 10.0F));

        PartDefinition lower = head.addOrReplaceChild("lower", CubeListBuilder.create().texOffs(53, 0).addBox(-10.0F, 0.0F, -30.0F, 20.0F, 10.0F, 10.0F, new CubeDeformation(0.0F))
                .texOffs(61, 21).addBox(-10.0F, 0.0F, -20.0F, 20.0F, 10.0F, 20.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition upper = head.addOrReplaceChild("upper", CubeListBuilder.create().texOffs(61, 62).addBox(-10.0F, -10.0F, -20.0F, 20.0F, 10.0F, 20.0F, new CubeDeformation(0.0F))
                .texOffs(0, 82).addBox(-10.0F, -6.0F, -30.0F, 20.0F, 6.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 41).addBox(-10.0F, -20.0F, -10.0F, 20.0F, 20.0F, 20.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition skeleton = body.addOrReplaceChild("skeleton", CubeListBuilder.create().texOffs(41, 93).addBox(-1.0F, -18.0F, -10.0F, 2.0F, 2.0F, 20.0F, new CubeDeformation(0.0F))
                .texOffs(61, 52).addBox(1.0F, -18.0F, -6.0F, 7.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(0, 22).addBox(-8.0F, -18.0F, -6.0F, 7.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(53, 21).addBox(1.0F, -18.0F, 4.0F, 7.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(0, 17).addBox(-8.0F, -18.0F, 4.0F, 7.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(0, 41).addBox(6.0F, -16.0F, -6.0F, 2.0F, 14.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(18, 0).addBox(6.0F, -16.0F, 4.0F, 2.0F, 14.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(9, 0).addBox(-8.0F, -16.0F, 4.0F, 2.0F, 14.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(0, 0).addBox(-8.0F, -16.0F, -6.0F, 2.0F, 14.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(9, 46).addBox(-6.0F, -4.0F, 4.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(9, 41).addBox(-6.0F, -4.0F, -6.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(19, 22).addBox(4.0F, -4.0F, -6.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(19, 17).addBox(4.0F, -4.0F, 4.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition tail = partdefinition.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(0, 0).addBox(-6.0F, -16.0F, -10.0F, 12.0F, 12.0F, 28.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 256, 256);
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@Nullable NightTerror p_114482_) {
        return TEXTURE;
    }

    @Override
    public void render(@NotNull NightTerror nightTerror, float p_115456_, float partialTicks, PoseStack stack, MultiBufferSource source, int p_115460_) {
        stack.pushPose();
        stack.scale(-1, -1, 1);
        stack.translate(0, -1.501, 0);

        int overlay = OverlayTexture.pack(0, OverlayTexture.v(nightTerror.hurtTime > 0 || nightTerror.deathTime > 0));

        if (Main.isDay()) {
            renderModel(nightTerror, partialTicks, stack, source.getBuffer(RenderType.entityCutoutNoCull(TEXTURE)), source, p_115460_, overlay);
        }
        else {
            renderModel(nightTerror, partialTicks, stack, source.getBuffer(SKELETON), source, p_115460_, overlay);
            renderModel(nightTerror, partialTicks, stack, source.getBuffer(OUTER_TRANSLUCENT), source, p_115460_, overlay);
        }

        stack.popPose();

        super.render(nightTerror, p_115456_, partialTicks, stack, source, p_115460_);
    }

    private void renderModel(NightTerror nightTerror, float partialTicks, PoseStack stack, VertexConsumer buffer, MultiBufferSource source, int p_115460_, int overlay) {
        var parts = nightTerror.getPartEntities();
        Vec3 prevRenderPos = Vec3.ZERO;

        for (int i = 0; i < parts.size(); i++) {
            var part = parts.get(i);

            Vec3 renderPos = part.getPrevTickPos().lerp(part.position(), partialTicks);

            if (i == 0) {
                Vec2 rot = nightTerror.getRotationVector();

                var upper = root.getChild("head").getChild("upper");
                var lower = root.getChild("head").getChild("lower");

                PartPose pose0 = upper.storePose();
                PartPose pose1 = upper.storePose();

                double time = part.level.getGameTime() + partialTicks;
                double mouthAnimation = Math.toRadians((Math.sin(time / 7) + 1) / 2 * 10);
                upper.xRot -= mouthAnimation;
                lower.xRot += mouthAnimation;

                renderModelPart(root.getChild("head"), nightTerror.position(), renderPos, rot, stack, buffer, p_115460_, overlay);

                upper.loadPose(pose0);
                lower.loadPose(pose1);
            }
            else {
                Vec2 rot = lookAt(renderPos, prevRenderPos);
                renderModelPart(root.getChild(part.getModelName()), nightTerror.position(), renderPos, rot, stack, buffer, p_115460_, overlay);
            }

            prevRenderPos = renderPos;
        }
    }

    private void renderModelPart(ModelPart model, Vec3 origin, Vec3 pos, Vec2 rot, PoseStack stack, VertexConsumer buffer, int p_115460_, int overlay) {
        PartPose pose = model.storePose();

        Vec3 modelPos = getModelSpacePos(origin, pos);
        model.x += modelPos.x;
        model.y += modelPos.y;
        model.z += modelPos.z;

        model.xRot += Math.toRadians(rot.x);
        model.yRot += Math.toRadians(rot.y + 180);

        model.render(stack, buffer, p_115460_, overlay);
        model.loadPose(pose);
    }

    private Vec2 lookAt(Vec3 eye, Vec3 center) {
        Vec3 diff = center.subtract(eye);
        return new Vec2((float) Mth.wrapDegrees(Math.atan2(diff.y, Math.hypot(diff.x, diff.z)) * -180 / Math.PI), (float)Mth.wrapDegrees(Math.atan2(diff.z, diff.x) * 180 / Math.PI - 90));
    }

    private Vec3 getModelSpacePos(Vec3 origin, Vec3 pos) {
        return origin.subtract(pos).multiply(16, 16, -16);
    }

    @Override
    protected boolean shouldShowName(NightTerror nightTerror) {
        return nightTerror.hasCustomName();
    }
}
