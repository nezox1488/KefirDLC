package kefirdlc.ui.clickgui;

// coded by sitoku \\

import com.mojang.blaze3d.matrix.MatrixStack;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import kefirdlc.KefirDLC;
import kefirdlc.font.ClientFont;
import kefirdlc.module.Module;
import kefirdlc.render.core.Renderer2D;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.util.text.StringTextComponent;

public class ClickGuiScreen extends Screen {
    private final KefirDLC client;
    private final List<Module> visibleModules = new ArrayList<>();

    public ClickGuiScreen(KefirDLC client) {
        super(new StringTextComponent("KefirDLC"));
        this.client = client;
    }

    @Override
    protected void init() {
        this.visibleModules.clear();
        this.visibleModules.addAll(this.client.getModuleManager().getModules());
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(matrixStack);
        Renderer2D renderer2D = Renderer2D.getInstance();
        renderer2D.begin(matrixStack);
        int left = this.width / 2 - 120;
        int top = this.height / 2 - 90;
        int right = this.width / 2 + 120;
        int bottom = this.height / 2 + 90;
        renderer2D.rect((float)left, (float)top, (float)(right - left), (float)(bottom - top), 7.0F, new Color(12, 12, 16, 220).getRGB());
        ClientFont titleFont = this.client.getFontManager().get("bold_20");
        ClientFont textFont = this.client.getFontManager().get("regular_18");
        renderer2D.text(titleFont, (float)(left + 10), (float)(top + 10), "KefirDLC Menu", new Color(111, 66, 255, 255).getRGB());
        int y = top + 34;
        for (Module module : this.visibleModules) {
            String state = module.isEnabled() ? "ON" : "OFF";
            renderer2D.text(textFont, (float)(left + 12), (float)y, module.getName() + " : " + state, -1);
            y += 14;
        }
        renderer2D.end();
        super.render(matrixStack, mouseX, mouseY, partialTicks);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        int left = this.width / 2 - 120;
        int top = this.height / 2 - 90;
        int y = top + 34;
        for (Module module : this.visibleModules) {
            int rowTop = y - 2;
            int rowBottom = y + 11;
            if (mouseX >= left + 8 && mouseX <= left + 170 && mouseY >= rowTop && mouseY <= rowBottom && button == 0) {
                module.toggle();
                return true;
            }
            y += 14;
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }
}
