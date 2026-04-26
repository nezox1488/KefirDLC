package kefirdlc.module.modules.render;

// coded by sitoku \\

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import kefirdlc.event.Event;
import kefirdlc.event.events.Render3DEvent;
import kefirdlc.module.Module;
import kefirdlc.module.ModuleCategory;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;

public class PenisEspModule extends Module {
    public PenisEspModule() {
        super("PenisESP", ModuleCategory.RENDER);
    }

    @Override
    public void onEvent(Event event) {
        if (!this.isEnabled() || !(event instanceof Render3DEvent)) {
            return;
        }
        this.render((Render3DEvent)event);
    }

    private void render(Render3DEvent event) {
        Minecraft mc = Minecraft.getInstance();
        if (mc.world == null || mc.player == null) {
            return;
        }
        ClientPlayerEntity self = mc.player;
        MatrixStack matrixStack = event.getMatrixStack();
        float partial = event.getPartialTicks();
        Vector3d cam = mc.gameRenderer.getActiveRenderInfo().getProjectedView();
        IRenderTypeBuffer.Impl buffer = mc.getRenderTypeBuffers().getBufferSource();
        IVertexBuilder lines = buffer.getBuffer(RenderType.getLines());
        matrixStack.push();
        matrixStack.translate(-cam.x, -cam.y, -cam.z);
        for (PlayerEntity target : mc.world.getPlayers()) {
            if (target == self || target.isInvisible() || !target.isAlive()) {
                continue;
            }
            double x = MathHelper.lerp((double)partial, target.prevPosX, target.getPosX());
            double y = MathHelper.lerp((double)partial, target.prevPosY, target.getPosY());
            double z = MathHelper.lerp((double)partial, target.prevPosZ, target.getPosZ());
            this.draw(matrixStack, lines, x, y, z);
        }
        matrixStack.pop();
        buffer.finish(RenderType.getLines());
    }

    private void draw(MatrixStack matrixStack, IVertexBuilder lines, double x, double y, double z) {
        AxisAlignedBB shaft = new AxisAlignedBB(x - 0.03D, y + 0.9D, z - 0.03D, x + 0.03D, y + 1.35D, z + 0.03D);
        AxisAlignedBB left = new AxisAlignedBB(x - 0.11D, y + 0.82D, z - 0.05D, x - 0.02D, y + 0.92D, z + 0.05D);
        AxisAlignedBB right = new AxisAlignedBB(x + 0.02D, y + 0.82D, z - 0.05D, x + 0.11D, y + 0.92D, z + 0.05D);
        WorldRenderer.drawBoundingBox(matrixStack, lines, shaft, 1.0F, 0.35F, 0.82F, 1.0F);
        WorldRenderer.drawBoundingBox(matrixStack, lines, left, 1.0F, 0.35F, 0.82F, 1.0F);
        WorldRenderer.drawBoundingBox(matrixStack, lines, right, 1.0F, 0.35F, 0.82F, 1.0F);
    }
}
