package health.tabia.challenge;

import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class MetricStoreInMemoryTest {

    @NotNull
    private MetricStoreInMemory store;
    @NotNull
    private final List<Metric> metrics = new ArrayList<>();

    @BeforeEach
    public void setUp() {
        store = new MetricStoreInMemory();
        metrics.add(new Metric("temperature",1735745252));
        metrics.add(new Metric("glucose",1735745252));
        metrics.add(new Metric("pressure",1735745252));
    }
    @Test
    public void insertAndQueryTest() {
        store.insert(metrics.getFirst());
        store.insert(metrics.get(1));

        try (MetricIteratorImpl iterator = store.query("temperature", 1735745250, 1735745255)){
            iterator.moveNext();
            assertEquals(iterator.current(), metrics.getFirst());
        }

        try (MetricIteratorImpl iterator = store.query("glucose", 1735745250, 1735745255)){
            iterator.moveNext();
            assertNotEquals(iterator.current(), metrics.getFirst());
            assertEquals(iterator.current(), metrics.get(1));
        }

        try (MetricIteratorImpl iterator = store.query("pressure", 1735745250, 1735745255)){
            boolean moved = iterator.moveNext();
            assertFalse(moved);
        }

    }

    @Test
    public void removeAllTest() {
        store.insert(metrics.getFirst());
        store.insert(metrics.get(1));
        Metric repeatedNameMetric = new Metric("glucose",1735745253);
        store.insert(repeatedNameMetric);

        try (MetricIteratorImpl iterator = store.query("glucose", 1735745250, 1735745255)){
            iterator.moveNext();
            assertEquals(iterator.current(), metrics.get(1));
            iterator.moveNext();
            assertEquals(iterator.current(), repeatedNameMetric);
        }

        store.removeAll("glucose");

        try (MetricIteratorImpl iterator = store.query("glucose", 1735745250, 1735745255)){
            boolean moved = iterator.moveNext();
            assertFalse(moved);
        }

    }
}
