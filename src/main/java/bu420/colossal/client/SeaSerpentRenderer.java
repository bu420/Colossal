package bu420.colossal.client;

import bu420.colossal.Main;
import bu420.colossal.entity.SeaSerpent;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
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

public class SeaSerpentRenderer extends EntityRenderer<SeaSerpent> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(Main.MODID, "textures/entities/sea_serpent.png");
    private static final RenderType OPAQUE = RenderType.entityCutoutNoCull(TEXTURE);
    private static final RenderType EYES = RenderType.eyes(new ResourceLocation(Main.MODID, "textures/entities/sea_serpent_eyes.png"));
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(Main.MODID, "sea_serpent"), "main");

    private final ModelPart root;

    public SeaSerpentRenderer(EntityRendererProvider.Context context) {
        super(context);
        root = context.bakeLayer(LAYER_LOCATION);
    }

    public static LayerDefinition createLayerDefinition() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition head = partdefinition.addOrReplaceChild("head", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition upper = head.addOrReplaceChild("upper", CubeListBuilder.create().texOffs(80, 0).addBox(-4.0F, -6.0F, -16.0F, 8.0F, 6.0F, 16.0F, new CubeDeformation(0.0F))
                .texOffs(32, 0).addBox(-4.0F, -4.0F, -22.0F, 8.0F, 4.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(16, 12).addBox(0.0F, -10.0F, -7.0F, 0.0F, 4.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(29, 15).addBox(0.0F, -10.0F, -13.0F, 0.0F, 4.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -4.0F, 8.0F));

        upper.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(72, 67).addBox(-4.0F, 0.0F, 0.0F, 8.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, -22.0F, -0.0873F, 0.0F, 0.0F));

        upper.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(84, 27).addBox(0.0F, 0.0F, -11.0F, 0.0F, 1.0F, 22.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(4.0F, 0.0F, -11.0F, 0.0F, 0.0F, -0.0873F));

        upper.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(84, 27).addBox(0.0F, 0.0F, -11.0F, 0.0F, 1.0F, 22.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-4.0F, 0.0F, -11.0F, 0.0F, 0.0F, 0.0873F));

        PartDefinition lower = head.addOrReplaceChild("lower", CubeListBuilder.create().texOffs(0, 35).addBox(-4.0F, 0.0F, -22.0F, 8.0F, 4.0F, 22.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -4.0F, 8.0F));

        lower.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(0, 63).addBox(-4.0F, -1.0F, 0.0F, 8.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, -22.0F, 0.0873F, 0.0F, 0.0F));

        lower.addOrReplaceChild("cube_r5", CubeListBuilder.create().texOffs(68, 38).addBox(0.0F, -1.0F, -11.0F, 0.0F, 1.0F, 22.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(4.0F, 0.0F, -11.0F, 0.0F, 0.0F, 0.0873F));

        lower.addOrReplaceChild("cube_r6", CubeListBuilder.create().texOffs(68, 38).addBox(0.0F, -1.0F, -11.0F, 0.0F, 1.0F, 22.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-4.0F, 0.0F, -11.0F, 0.0F, 0.0F, -0.0873F));

        partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(16, 12).addBox(0.0F, -14.0F, -3.0F, 0.0F, 4.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(0, 0).addBox(-4.0F, -10.0F, -4.0F, 8.0F, 10.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

        partdefinition.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(0, 18).addBox(-2.0F, -9.0F, -9.0F, 4.0F, 8.0F, 8.0F, new CubeDeformation(0.0F))
                .texOffs(50, 71).addBox(0.0F, -14.0F, -1.0F, 0.0F, 18.0F, 39.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 128, 128);
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@Nullable SeaSerpent p_114482_) {
        return TEXTURE;
    }

    @Override
    public void render(@NotNull SeaSerpent seaSerpent, float p_115456_, float partialTicks, PoseStack stack, MultiBufferSource source, int p_115460_) {
        stack.pushPose();
        stack.scale(-1, -1, 1);
        stack.translate(0, -1.501, 0);

        int overlay = OverlayTexture.pack(0, OverlayTexture.v(seaSerpent.hurtTime > 0 || seaSerpent.deathTime > 0));

        renderModel(seaSerpent, partialTicks, stack, source.getBuffer(OPAQUE), p_115460_, overlay);
        renderModel(seaSerpent, partialTicks, stack, source.getBuffer(EYES), p_115460_, overlay);

        stack.popPose();

        super.render(seaSerpent, p_115456_, partialTicks, stack, source, p_115460_);
    }

    private void renderModel(SeaSerpent seaSerpent, float partialTicks, PoseStack stack, VertexConsumer buffer, int p_115460_, int overlay) {
        var parts = seaSerpent.getPartEntities();
        Vec3 prevRenderPos = Vec3.ZERO;

        for (int i = 0; i < parts.size(); i++) {
            var part = parts.get(i);

            Vec3 renderPos = part.getPrevTickPos().lerp(part.position(), partialTicks);

            if (i == 0) {
                Vec2 rot = seaSerpent.getRotationVector();

                var upper = root.getChild("head").getChild("upper");
                var lower = root.getChild("head").getChild("lower");

                PartPose pose0 = upper.storePose();
                PartPose pose1 = upper.storePose();

                double time = part.level.getGameTime() + partialTicks;
                double mouthAnimation = Math.toRadians((Math.sin(time / 7) + 1) / 2 * 10);
                upper.xRot -= mouthAnimation;
                lower.xRot += mouthAnimation;

                renderModelPart(root.getChild("head"), seaSerpent.position(), renderPos, rot, stack, buffer, p_115460_, overlay);

                upper.loadPose(pose0);
                lower.loadPose(pose1);
            }
            else {
                Vec2 rot = lookAt(renderPos, prevRenderPos);
                renderModelPart(root.getChild(part.getModelName()), seaSerpent.position(), renderPos, rot, stack, buffer, p_115460_, overlay);
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
    protected boolean shouldShowName(SeaSerpent seaSerpent) {
        return seaSerpent.hasCustomName();
    }

    /*private Vec3 calcSidewaysMovement(SeaSerpentPart part, int i, float partialTick) {
        double time = part.level.getGameTime() + partialTick;
        Vec3 right = part.getForward().cross(part.getUpVector(1));
        double amount = Math.sin((time / 10) + i / 2D) / 0.75;
        Vec3 dm = part.getDeltaMovement();
        double speed = Math.max(dm.x, Math.max(dm.y, dm.z));
        double result = amount * speed * 5;
        return right.multiply(result, result, result);
    }*/
}
