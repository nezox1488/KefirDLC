package kefirdlc.render.core;

// coded by sitoku \\

import com.mojang.blaze3d.matrix.MatrixStack;
import java.util.ArrayDeque;
import kefirdlc.font.ClientFont;
import kefirdlc.render.backends.gl.GlBackend;

public final class Renderer2D {
    private static final Renderer2D INSTANCE = new Renderer2D();
    private final GlBackend backend = new GlBackend();
    private final ShapeBatcher batcher = new ShapeBatcher();
    private final TransformStack transformStack = new TransformStack();
    private final ArrayDeque<Float> alphaStack = new ArrayDeque<>();
    private boolean frameBegun;
    private MatrixStack matrixStack;

    private Renderer2D() {
        this.alphaStack.push(1.0F);
    }

    public static Renderer2D getInstance() {
        return INSTANCE;
    }

    public void begin(MatrixStack matrixStack) {
        if (this.frameBegun) {
            throw new IllegalStateException("Renderer2D frame already begun");
        }
        this.frameBegun = true;
        this.matrixStack = matrixStack;
        this.transformStack.clear();
        this.alphaStack.clear();
        this.alphaStack.push(1.0F);
    }

    public void end() {
        this.ensureFrame();
        this.flush();
        this.frameBegun = false;
        this.matrixStack = null;
    }

    public void flush() {
        this.ensureFrame();
        this.batcher.flush();
    }

    public void rect(float x, float y, float w, float h, int rgba) {
        this.rect(x, y, w, h, 0.0F, rgba);
    }

    public void rect(float x, float y, float w, float h, float rounding, int rgba) {
        this.ensureFrame();
        float[] transform = this.transformStack.current();
        float tx = transform[2] + x;
        float ty = transform[5] + y;
        int color = this.modulate(rgba);
        this.batcher.enqueue(() -> this.backend.drawRect(this.matrixStack, tx, ty, w, h, rounding, color));
    }

    public void text(ClientFont font, float x, float y, String text, int rgba) {
        this.ensureFrame();
        float[] transform = this.transformStack.current();
        float tx = transform[2] + x;
        float ty = transform[5] + y;
        int color = this.modulate(rgba);
        this.batcher.enqueue(() -> this.backend.drawText(this.matrixStack, font, tx, ty, text, color));
    }

    public void pushTranslation(float tx, float ty) {
        this.ensureFrame();
        this.transformStack.pushTranslation(tx, ty);
    }

    public void pushScale(float sx, float sy) {
        this.ensureFrame();
        this.transformStack.pushScale(sx, sy);
    }

    public void popTransform() {
        this.ensureFrame();
        this.transformStack.pop();
    }

    public void pushAlpha(float alpha) {
        this.ensureFrame();
        float clamped = Math.max(0.0F, Math.min(1.0F, alpha));
        this.alphaStack.push(this.alphaStack.peek() * clamped);
    }

    public void popAlpha() {
        this.ensureFrame();
        if (this.alphaStack.size() > 1) {
            this.alphaStack.pop();
        }
    }

    private int modulate(int color) {
        float factor = this.alphaStack.peek();
        int a = color >>> 24 & 255;
        int r = color >>> 16 & 255;
        int g = color >>> 8 & 255;
        int b = color & 255;
        int na = Math.min(255, Math.max(0, Math.round(a * factor)));
        int nr = Math.min(255, Math.max(0, Math.round(r * factor)));
        int ng = Math.min(255, Math.max(0, Math.round(g * factor)));
        int nb = Math.min(255, Math.max(0, Math.round(b * factor)));
        return na << 24 | nr << 16 | ng << 8 | nb;
    }

    private void ensureFrame() {
        if (!this.frameBegun) {
            throw new IllegalStateException("begin() must be called before draw commands");
        }
    }
}
