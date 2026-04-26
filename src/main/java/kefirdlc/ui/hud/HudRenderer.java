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
import kefirdlc.ui.render.Render2DUtil;

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
        ClientFont titleFont = this.client.getFontManager().get(WATERMARK_FONT);
        ClientFont hudFont = this.client.getFontManager().get(LIST_FONT);
        int accent = new Color(111, 66, 255, 255).getRGB();
        int watermarkWidth = Math.max(110, titleFont.getWidth(WATERMARK_TEXT) + 20);
        Render2DUtil.drawRoundedRect(matrixStack, 6, 6, watermarkWidth, 20, 6, new Color(10, 10, 14, 170).getRGB());
        titleFont.drawString(matrixStack, WATERMARK_TEXT, 11.0F, 11.0F, accent);
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
            Render2DUtil.drawRoundedRect(matrixStack, startX - 4, y - 2, boxWidth, boxHeight, 4, new Color(10, 10, 14, 165).getRGB());
            hudFont.drawString(matrixStack, module.getName(), (float)startX, (float)y, -1);
            y += hudFont.getHeight() + 3;
        }
    }
}
