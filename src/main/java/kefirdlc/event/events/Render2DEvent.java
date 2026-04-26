package kefirdlc.event.events;

// coded by sitoku \\

import com.mojang.blaze3d.matrix.MatrixStack;
import kefirdlc.event.Event;

public class Render2DEvent extends Event {
    private final MatrixStack matrixStack;
    private final float partialTicks;
    private final int scaledWidth;
    private final int scaledHeight;

    public Render2DEvent(MatrixStack matrixStack, float partialTicks, int scaledWidth, int scaledHeight) {
        this.matrixStack = matrixStack;
        this.partialTicks = partialTicks;
        this.scaledWidth = scaledWidth;
        this.scaledHeight = scaledHeight;
    }

    public MatrixStack getMatrixStack() {
        return this.matrixStack;
    }

    public float getPartialTicks() {
        return this.partialTicks;
    }

    public int getScaledWidth() {
        return this.scaledWidth;
    }

    public int getScaledHeight() {
        return this.scaledHeight;
    }
}
