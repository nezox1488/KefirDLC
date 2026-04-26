package kefirdlc.manager;

// coded by sitoku \\

import kefirdlc.KefirDLC;
import kefirdlc.event.Event;
import kefirdlc.event.EventListener;
import kefirdlc.event.events.Render2DEvent;
import kefirdlc.ui.hud.HudRenderer;

public class HudManager implements EventListener {
    private final KefirDLC client;
    private final HudRenderer renderer;

    public HudManager(KefirDLC client) {
        this.client = client;
        this.renderer = new HudRenderer(client);
    }

    public void init() {
        this.client.getEventBus().subscribe(this);
    }

    @Override
    public void onEvent(Event event) {
        if (event instanceof Render2DEvent) {
            this.renderer.render((Render2DEvent)event);
        }
    }
}
