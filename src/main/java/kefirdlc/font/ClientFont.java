package kefirdlc.font;

// coded by sitoku \\

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.stb.STBTTAlignedQuad;
import org.lwjgl.stb.STBTTBakedChar;
import org.lwjgl.stb.STBTruetype;
import org.lwjgl.system.MemoryStack;

public class ClientFont {
    private final int size;
    private final STBTTBakedChar.Buffer charData;
    private final int textureId;

    public ClientFont(String resourcePath, int size) {
        this.size = size;
        this.charData = STBTTBakedChar.malloc(96);
        ByteBuffer bitmap = BufferUtils.createByteBuffer(512 * 512);
        ByteBuffer ttf = this.readResource(resourcePath);
        STBTruetype.stbtt_BakeFontBitmap(ttf, (float)size, bitmap, 512, 512, 32, this.charData);
        this.textureId = GL11.glGenTextures();
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, this.textureId);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
        GL11.glPixelStorei(GL11.GL_UNPACK_ALIGNMENT, 1);
        GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_ALPHA, 512, 512, 0, GL11.GL_ALPHA, GL11.GL_UNSIGNED_BYTE, bitmap);
    }

    private ByteBuffer readResource(String resourcePath) {
        try (InputStream stream = ClientFont.class.getClassLoader().getResourceAsStream(resourcePath)) {
            if (stream == null) {
                throw new IllegalStateException("Font not found: " + resourcePath);
            }
            byte[] bytes = stream.readAllBytes();
            ByteBuffer buffer = BufferUtils.createByteBuffer(bytes.length);
            buffer.put(bytes);
            buffer.flip();
            return buffer;
        } catch (IOException e) {
            throw new IllegalStateException("Font read error: " + resourcePath, e);
        }
    }

    public int drawString(MatrixStack matrixStack, String text, float x, float y, int color) {
        float r = (float)(color >> 16 & 255) / 255.0F;
        float g = (float)(color >> 8 & 255) / 255.0F;
        float b = (float)(color & 255) / 255.0F;
        float a = (float)(color >> 24 & 255) / 255.0F;
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        GL11.glColor4f(r, g, b, a == 0.0F ? 1.0F : a);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, this.textureId);
        FloatBuffer xBuffer = BufferUtils.createFloatBuffer(1).put(0, x);
        FloatBuffer yBuffer = BufferUtils.createFloatBuffer(1).put(0, y);
        try (MemoryStack stack = MemoryStack.stackPush()) {
            STBTTAlignedQuad quad = STBTTAlignedQuad.mallocStack(stack);
            GL11.glBegin(GL11.GL_QUADS);
            for (int i = 0; i < text.length(); i++) {
                char ch = text.charAt(i);
                if (ch < 32 || ch > 126) {
                    ch = '?';
                }
                STBTruetype.stbtt_GetBakedQuad(this.charData, 512, 512, ch - 32, xBuffer, yBuffer, quad, true);
                GL11.glTexCoord2f(quad.s0(), quad.t0());
                GL11.glVertex2f(quad.x0(), quad.y0());
                GL11.glTexCoord2f(quad.s1(), quad.t0());
                GL11.glVertex2f(quad.x1(), quad.y0());
                GL11.glTexCoord2f(quad.s1(), quad.t1());
                GL11.glVertex2f(quad.x1(), quad.y1());
                GL11.glTexCoord2f(quad.s0(), quad.t1());
                GL11.glVertex2f(quad.x0(), quad.y1());
            }
            GL11.glEnd();
        }
        RenderSystem.disableBlend();
        return (int)xBuffer.get(0);
    }

    public int getWidth(String text) {
        float width = 0.0F;
        for (int i = 0; i < text.length(); i++) {
            char ch = text.charAt(i);
            if (ch < 32 || ch > 126) {
                ch = '?';
            }
            STBTTBakedChar glyph = this.charData.get(ch - 32);
            width += glyph.xadvance();
        }
        return (int)width;
    }

    public int getHeight() {
        return this.size;
    }

    public int getSize() {
        return this.size;
    }
}
