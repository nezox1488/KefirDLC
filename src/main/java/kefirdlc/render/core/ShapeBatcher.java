package kefirdlc.render.core;

// coded by sitoku \\

import java.util.ArrayList;
import java.util.List;

public class ShapeBatcher {
    private final List<Runnable> commands = new ArrayList<>();

    public void enqueue(Runnable command) {
        this.commands.add(command);
    }

    public void flush() {
        for (Runnable command : this.commands) {
            command.run();
        }
        this.commands.clear();
    }
}
