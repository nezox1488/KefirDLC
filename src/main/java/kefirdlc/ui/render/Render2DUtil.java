package kefirdlc.ui.render;

// coded by sitoku \\

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.util.math.MathHelper;
import org.lwjgl.opengl.GL11;

public class Render2DUtil {
    public static void drawRoundedRect(MatrixStack matrixStack, int x, int y, int width, int height, int radius, int color) {
        int safeRadius = MathHelper.clamp(radius, 1, Math.min(width, height) / 2);
        drawRect(x + safeRadius, y, width - safeRadius * 2, height, color);
        drawRect(x, y + safeRadius, safeRadius, height - safeRadius * 2, color);
        drawRect(x + width - safeRadius, y + safeRadius, safeRadius, height - safeRadius * 2, color);
        drawCircle(x + safeRadius, y + safeRadius, safeRadius, 180, 270, color);
        drawCircle(x + width - safeRadius, y + safeRadius, safeRadius, 270, 360, color);
        drawCircle(x + width - safeRadius, y + height - safeRadius, safeRadius, 0, 90, color);
        drawCircle(x + safeRadius, y + height - safeRadius, safeRadius, 90, 180, color);
    }

    private static void drawRect(double x, double y, double width, double height, int color) {
        setup(color);
        GL11.glBegin(GL11.GL_QUADS);
        GL11.glVertex2d(x, y);
        GL11.glVertex2d(x + width, y);
        GL11.glVertex2d(x + width, y + height);
        GL11.glVertex2d(x, y + height);
        GL11.glEnd();
        teardown();
    }

    private static void drawCircle(double x, double y, double radius, int start, int end, int color) {
        setup(color);
        GL11.glBegin(GL11.GL_TRIANGLE_FAN);
        GL11.glVertex2d(x, y);
        for (int i = start; i <= end; i++) {
            double rad = Math.toRadians(i);
            GL11.glVertex2d(x + Math.sin(rad) * radius, y + Math.cos(rad) * radius);
        }
        GL11.glEnd();
        teardown();
    }

    private static void setup(int color) {
        float a = (float)(color >> 24 & 255) / 255.0F;
        float r = (float)(color >> 16 & 255) / 255.0F;
        float g = (float)(color >> 8 & 255) / 255.0F;
        float b = (float)(color & 255) / 255.0F;
        RenderSystem.disableTexture();
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        GL11.glColor4f(r, g, b, a);
        GL11.glEnable(GL11.GL_LINE_SMOOTH);
        GL11.glHint(GL11.GL_LINE_SMOOTH_HINT, GL11.GL_NICEST);
    }

    private static void teardown() {
        GL11.glDisable(GL11.GL_LINE_SMOOTH);
        RenderSystem.disableBlend();
        RenderSystem.enableTexture();
    }
}
