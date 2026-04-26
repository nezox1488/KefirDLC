package kefirdlc.ui.render;

// coded by sitoku \\

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.util.math.MathHelper;

public class Render2DUtil {
    public static void drawRoundedRect(MatrixStack matrixStack, int x, int y, int width, int height, int radius, int color) {
        int safeRadius = MathHelper.clamp(radius, 1, Math.min(width, height) / 2);
        AbstractGui.fill(matrixStack, x + safeRadius, y, x + width - safeRadius, y + height, color);
        AbstractGui.fill(matrixStack, x, y + safeRadius, x + safeRadius, y + height - safeRadius, color);
        AbstractGui.fill(matrixStack, x + width - safeRadius, y + safeRadius, x + width, y + height - safeRadius, color);
        for (int ix = 0; ix < safeRadius; ix++) {
            for (int iy = 0; iy < safeRadius; iy++) {
                double dx = safeRadius - ix;
                double dy = safeRadius - iy;
                if (dx * dx + dy * dy <= (double)(safeRadius * safeRadius)) {
                    int px = x + ix;
                    int py = y + iy;
                    AbstractGui.fill(matrixStack, px, py, px + 1, py + 1, color);
                    int px2 = x + width - 1 - ix;
                    AbstractGui.fill(matrixStack, px2, py, px2 + 1, py + 1, color);
                    int py2 = y + height - 1 - iy;
                    AbstractGui.fill(matrixStack, px, py2, px + 1, py2 + 1, color);
                    AbstractGui.fill(matrixStack, px2, py2, px2 + 1, py2 + 1, color);
                }
            }
        }
    }
}
