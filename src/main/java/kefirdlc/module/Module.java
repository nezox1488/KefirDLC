package kefirdlc.module;

// coded by sitoku \\

import kefirdlc.event.Event;
import kefirdlc.event.EventListener;

public abstract class Module implements EventListener {
    private final String name;
    private final ModuleCategory category;
    private boolean enabled;

    public Module(String name, ModuleCategory category) {
        this.name = name;
        this.category = category;
    }

    public void toggle() {
        this.setEnabled(!this.enabled);
    }

    public void setEnabled(boolean enabled) {
        if (this.enabled == enabled) {
            return;
        }
        this.enabled = enabled;
        if (enabled) {
            this.onEnable();
        } else {
            this.onDisable();
        }
    }

    public String getName() {
        return this.name;
    }

    public ModuleCategory getCategory() {
        return this.category;
    }

    public boolean isEnabled() {
        return this.enabled;
    }

    protected void onEnable() {
    }

    protected void onDisable() {
    }

    @Override
    public void onEvent(Event event) {
    }
}
