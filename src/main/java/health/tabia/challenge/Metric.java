package health.tabia.challenge;

import org.jetbrains.annotations.NotNull;

/**
 * Basic implementation of the Metric abstraction
 */
public class Metric {

    @NotNull
    private final String name;
    private final long timestamp;

    public Metric(String name, long timestamp) {
        this.name = name;
        this.timestamp = timestamp;
    }

    @NotNull
    public String getName() {
        return name;
    }

    public long getTimestamp() {
        return timestamp;
    }

    @NotNull
    public String toString() {
        return "{name='" + name + "', timestamp=" + timestamp + "}";
    }
}
