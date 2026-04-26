package kefirdlc.ui.hud;

// coded by sitoku \\

import com.mojang.blaze3d.matrix.MatrixStack;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import kefirdlc.KefirDLC;
import kefirdlc.event.events.Render2DEvent;
import kefirdlc.font.ClientFont;
import kefirdlc.module.Module;
import kefirdlc.render.core.Renderer2D;

public class HudRenderer {
    private final KefirDLC client;
    private static final String WATERMARK_TEXT = "KefirDLC";
    private static final String WATERMARK_FONT = "bold_20";
    private static final String LIST_FONT = "regular_14";

    public HudRenderer(KefirDLC client) {
        this.client = client;
    }

    public void render(Render2DEvent event) {
        MatrixStack matrixStack = event.getMatrixStack();
        Renderer2D renderer2D = Renderer2D.getInstance();
        renderer2D.begin(matrixStack);
        ClientFont titleFont = this.client.getFontManager().get(WATERMARK_FONT);
        ClientFont hudFont = this.client.getFontManager().get(LIST_FONT);
        int accent = new Color(111, 66, 255, 255).getRGB();
        int watermarkWidth = Math.max(110, titleFont.getWidth(WATERMARK_TEXT) + 20);
        renderer2D.rect(6.0F, 6.0F, (float)watermarkWidth, 20.0F, 6.0F, new Color(10, 10, 14, 170).getRGB());
        renderer2D.text(titleFont, 11.0F, 11.0F, WATERMARK_TEXT, accent);
        List<Module> enabled = new ArrayList<>();
        for (Module module : this.client.getModuleManager().getModules()) {
            if (module.isEnabled()) {
                enabled.add(module);
            }
        }
        enabled.sort(Comparator.comparingInt((Module module) -> hudFont.getWidth(module.getName())).reversed());
        int y = 10;
        for (Module module : enabled) {
            int width = hudFont.getWidth(module.getName());
            int startX = event.getScaledWidth() - width - 12;
            int boxWidth = event.getScaledWidth() - startX + 4;
            int boxHeight = hudFont.getHeight() + 4;
            renderer2D.rect((float)(startX - 4), (float)(y - 2), (float)boxWidth, (float)boxHeight, 4.0F, new Color(10, 10, 14, 165).getRGB());
            renderer2D.text(hudFont, (float)startX, (float)y, module.getName(), -1);
            y += hudFont.getHeight() + 3;
        }
        renderer2D.end();
    }
}
