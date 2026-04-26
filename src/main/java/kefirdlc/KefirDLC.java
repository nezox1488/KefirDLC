package kefirdlc;

// coded by sitoku \\

import com.mojang.blaze3d.matrix.MatrixStack;
import kefirdlc.event.EventBus;
import kefirdlc.event.events.KeyInputEvent;
import kefirdlc.event.events.Render2DEvent;
import kefirdlc.event.events.Render3DEvent;
import kefirdlc.event.events.TickEvent;
import kefirdlc.font.FontManager;
import kefirdlc.manager.HudManager;
import kefirdlc.module.ModuleManager;
import kefirdlc.ui.clickgui.ClickGuiScreen;
import net.minecraft.client.Minecraft;

public class KefirDLC {
    private static final KefirDLC INSTANCE = new KefirDLC();
    private final EventBus eventBus = new EventBus();
    private final FontManager fontManager = new FontManager();
    private final ModuleManager moduleManager = new ModuleManager(this);
    private final HudManager hudManager = new HudManager(this);
    private boolean initialized;

    public static KefirDLC getInstance() {
        return INSTANCE;
    }

    public void init() {
        if (this.initialized) {
            return;
        }
        this.initialized = true;
        this.moduleManager.init();
        this.hudManager.init();
    }

    public void onClientTick() {
        this.init();
        this.eventBus.post(new TickEvent());
    }

    public void onRender2D(MatrixStack matrixStack, float partialTicks, int scaledWidth, int scaledHeight) {
        this.init();
        this.eventBus.post(new Render2DEvent(matrixStack, partialTicks, scaledWidth, scaledHeight));
    }

    public void onRender3D(MatrixStack matrixStack, float partialTicks) {
        this.init();
        this.eventBus.post(new Render3DEvent(matrixStack, partialTicks));
    }

    public void onKey(int key, int scanCode, int action, int modifiers) {
        this.init();
        this.eventBus.post(new KeyInputEvent(key, scanCode, action, modifiers));
        if (action == 1 && key == 344) {
            Minecraft minecraft = Minecraft.getInstance();
            minecraft.displayGuiScreen(new ClickGuiScreen(this));
        }
    }

    public EventBus getEventBus() {
        return this.eventBus;
    }

    public FontManager getFontManager() {
        return this.fontManager;
    }

    public ModuleManager getModuleManager() {
        return this.moduleManager;
    }

    public HudManager getHudManager() {
        return this.hudManager;
    }
}
