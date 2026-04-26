package kefirdlc.render.backends.gl;

// coded by sitoku \\

import com.mojang.blaze3d.matrix.MatrixStack;
import kefirdlc.font.ClientFont;
import kefirdlc.ui.render.Render2DUtil;

public class GlBackend {
    public void drawRect(MatrixStack matrixStack, float x, float y, float w, float h, float rounding, int color) {
        Render2DUtil.drawRoundedRect(matrixStack, (int)x, (int)y, (int)w, (int)h, (int)rounding, color);
    }

    public void drawText(MatrixStack matrixStack, ClientFont font, float x, float y, String text, int color) {
        font.drawString(matrixStack, text, x, y, color);
    }
}
