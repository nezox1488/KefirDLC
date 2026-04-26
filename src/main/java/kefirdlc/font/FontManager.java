package kefirdlc.font;

// coded by sitoku \\

import java.util.HashMap;
import java.util.Map;

public class FontManager {
    private final Map<String, ClientFont> fonts = new HashMap<>();

    public FontManager() {
        this.register("regular_14", new ClientFont("regular", 14));
        this.register("regular_18", new ClientFont("regular", 18));
        this.register("bold_20", new ClientFont("bold", 20));
    }

    public void register(String key, ClientFont font) {
        this.fonts.put(key, font);
    }

    public ClientFont get(String key) {
        return this.fonts.getOrDefault(key, this.fonts.get("regular_14"));
    }
}
