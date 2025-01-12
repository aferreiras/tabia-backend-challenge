package health.tabia.challenge;

import org.jetbrains.annotations.*;
import java.util.ArrayList;
import java.util.List;


public class MetricIteratorImpl implements MetricIterator {

	@NotNull
	private final List<Metric> metrics;
	@Nullable
	private Metric metric;
	private int index = -1;
	private boolean moveNextReturnedFalse = false;

	public MetricIteratorImpl(@NotNull List<Metric> metrics){
			this.metrics = new ArrayList<>(metrics);
	}

	@Override
	public boolean moveNext(){
		if (metrics.isEmpty()|| index >= metrics.size()) {
			moveNextReturnedFalse = true;
			return false;
		}

		if (index + 1 < metrics.size()) {
			index++;
			metric = metrics.get(index);
			moveNextReturnedFalse = false;

			return true;
		}

		moveNextReturnedFalse = true;
		return false;
	}

	@Nullable
	@Override
	public Metric current() {
		checkMoveNext(index, moveNextReturnedFalse);

		return metric;
	}

	public void remove() {
		checkMoveNext(index, moveNextReturnedFalse);

		metrics.remove(index);

        if (index != 0) {
            index--;
        }
        this.metric = metrics.get(index);

    }

	@Override
	public void close() {
		metrics.clear();
	}

	private void checkMoveNext(int index, boolean returnedFalse) {
		if (index < 0 || returnedFalse ) {
			throw new  IllegalStateException("moveNext was never called or the last call resulted false");
		}
	}
}
