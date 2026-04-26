package kefirdlc.module;

// coded by sitoku \\

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import kefirdlc.KefirDLC;
import kefirdlc.module.modules.render.PenisEspModule;

public class ModuleManager {
    private final KefirDLC client;
    private final List<Module> modules = new ArrayList<>();

    public ModuleManager(KefirDLC client) {
        this.client = client;
    }

    public void init() {
        if (!this.modules.isEmpty()) {
            return;
        }
        this.register(new BasicModule("ClickGui", ModuleCategory.HUD));
        this.register(new BasicModule("ArrayList", ModuleCategory.HUD));
        this.register(new BasicModule("Watermark", ModuleCategory.HUD));
        this.register(new PenisEspModule());
    }

    public void register(Module module) {
        this.modules.add(module);
        this.client.getEventBus().subscribe(module);
    }

    public List<Module> getModules() {
        return Collections.unmodifiableList(this.modules);
    }

    public Module getModuleByName(String name) {
        for (Module module : this.modules) {
            if (module.getName().equalsIgnoreCase(name)) {
                return module;
            }
        }
        return null;
    }
}
