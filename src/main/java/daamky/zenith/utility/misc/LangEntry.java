package daamky.zenith.utility.misc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * Lightweight language-file reader used by Localization.
 * Ported to Daamky's package without the original Lombok dependency.
 */
public final class LangEntry {
    private final String name;
    private final Map<String, String> entries = new HashMap<>();

    public LangEntry(String name, InputStream input) throws IOException {
        this.name = name;
        read(input);
    }

    private void read(InputStream input) throws IOException {
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(input, StandardCharsets.UTF_8))) {
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty() || line.startsWith("#")) {
                    continue;
                }

                String[] pair = line.split("=", 2);
                if (pair.length == 2) {
                    entries.put(pair[0].trim(), pair[1].trim());
                }
            }
        }
    }

    /** Kept with its existing name because Localization already calls it. */
    public String method3304(String key) {
        String exact = entries.get(key);
        if (exact != null) {
            return exact;
        }

        int separator = key.lastIndexOf('.');
        if (separator >= 0 && separator + 1 < key.length()) {
            String wildcard = entries.get(key.substring(0, separator + 1) + "*");
            if (wildcard != null) {
                return wildcard.replace("{value}", key.substring(separator + 1));
            }
        }

        return key;
    }

    public String getName() {
        return name;
    }
}
