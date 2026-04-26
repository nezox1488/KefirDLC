package kefirdlc.font;

// coded by sitoku \\

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;

public class ClientFont {
    private final String name;
    private final int size;

    public ClientFont(String name, int size) {
        this.name = name;
        this.size = size;
    }

    public int drawString(MatrixStack matrixStack, String text, float x, float y, int color) {
        FontRenderer renderer = Minecraft.getInstance().fontRenderer;
        return renderer.drawString(matrixStack, text, x, y, color);
    }

    public int getWidth(String text) {
        return Minecraft.getInstance().fontRenderer.getStringWidth(text);
    }

    public int getHeight() {
        return this.size;
    }

    public String getName() {
        return this.name;
    }

    public int getSize() {
        return this.size;
    }
}
