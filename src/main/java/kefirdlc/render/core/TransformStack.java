package kefirdlc.render.core;

// coded by sitoku \\

import java.util.ArrayDeque;

public class TransformStack {
    private final ArrayDeque<float[]> stack = new ArrayDeque<>();

    public TransformStack() {
        this.clear();
    }

    public void clear() {
        this.stack.clear();
        this.stack.push(new float[] {1.0F, 0.0F, 0.0F, 0.0F, 1.0F, 0.0F});
    }

    public float[] current() {
        return this.stack.peek();
    }

    public void pushTranslation(float tx, float ty) {
        float[] top = this.current();
        this.stack.push(new float[] {top[0], top[1], top[2] + tx, top[3], top[4], top[5] + ty});
    }

    public void pushScale(float sx, float sy) {
        float[] top = this.current();
        this.stack.push(new float[] {top[0] * sx, top[1], top[2], top[3], top[4] * sy, top[5]});
    }

    public void pop() {
        if (this.stack.size() > 1) {
            this.stack.pop();
        }
    }
}
