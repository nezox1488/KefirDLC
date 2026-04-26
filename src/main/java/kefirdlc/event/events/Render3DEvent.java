package kefirdlc.event.events;

// coded by sitoku \\

import com.mojang.blaze3d.matrix.MatrixStack;
import kefirdlc.event.Event;

public class Render3DEvent extends Event {
    private final MatrixStack matrixStack;
    private final float partialTicks;

    public Render3DEvent(MatrixStack matrixStack, float partialTicks) {
        this.matrixStack = matrixStack;
        this.partialTicks = partialTicks;
    }

    public MatrixStack getMatrixStack() {
        return this.matrixStack;
    }

    public float getPartialTicks() {
        return this.partialTicks;
    }
}
