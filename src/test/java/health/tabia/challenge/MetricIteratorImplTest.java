package health.tabia.challenge;

import org.jetbrains.annotations.*;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import java.util.List;

public class MetricIteratorImplTest {
	@NotNull
	private final List<Metric> metrics = new ArrayList<>();

	@BeforeEach
	void setup(){
		metrics.add(new Metric("temperature",1735745252));
		metrics.add(new Metric("glucose",1735745252));
		metrics.add(new Metric("pressure",1735745252));
	}

	@Test
	public void moveNextTest() {
        try (MetricIteratorImpl iterator = new MetricIteratorImpl(metrics)) {

            boolean moved = iterator.moveNext();
			assertTrue(moved);

			moved = finishIterations(iterator);

			assertFalse(moved);
        }

	}

	@Test
	public void currentTest() {
		try (MetricIteratorImpl iterator = new MetricIteratorImpl(metrics)) {
            assertThrows(IllegalStateException.class, iterator::current);

			boolean moved = iterator.moveNext();
			assertEquals(iterator.current(),metrics.getFirst());

			moved = finishIterations(iterator);

			assertFalse(moved);
			assertThrows(IllegalStateException.class, iterator::current);
		}

	}

	@Test
	public void removeTest() {
		try (MetricIteratorImpl iterator = new MetricIteratorImpl(metrics)) {
			assertThrows(IllegalStateException.class, iterator::remove);

			iterator.moveNext();
			assertEquals(iterator.current(), metrics.getFirst());
			iterator.remove();
			assertEquals(iterator.current(), metrics.get(1));
			finishIterations(iterator);

			assertThrows(IllegalStateException.class, iterator::current);
		}
	}

	private boolean finishIterations(@NotNull MetricIteratorImpl iterator) {
		boolean moved = true;
		for (Metric metric : metrics) {
			moved = iterator.moveNext();
		}

		return moved;
	}
}
