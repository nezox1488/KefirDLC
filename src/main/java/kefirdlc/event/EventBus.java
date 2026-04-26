package kefirdlc.event;

// coded by sitoku \\

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class EventBus {
    private final List<EventListener> listeners = new CopyOnWriteArrayList<>();

    public void subscribe(EventListener listener) {
        if (!this.listeners.contains(listener)) {
            this.listeners.add(listener);
        }
    }

    public void unsubscribe(EventListener listener) {
        this.listeners.remove(listener);
    }

    public void post(Event event) {
        for (EventListener listener : this.listeners) {
            listener.onEvent(event);
        }
    }
}
