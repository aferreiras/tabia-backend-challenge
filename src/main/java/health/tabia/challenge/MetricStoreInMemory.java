package health.tabia.challenge;

import org.jetbrains.annotations.*;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class MetricStoreInMemory implements MetricStore{
	@NotNull
	private final Map<String, List<Metric>> metricMap = new ConcurrentHashMap<>();

	@Override
	public void insert(Metric metric) {
		metricMap.computeIfAbsent(metric.getName(), metricName -> new ArrayList<>()).add(metric);
	}

	@Override
	public void removeAll(@NotNull String name) {
		metricMap.remove(name);
	}

	@Override
	@NotNull
	public MetricIteratorImpl query(@NotNull String name, long from, long to) {
		List<Metric> metrics = metricMap.getOrDefault(name, Collections.emptyList());

		if (metrics.isEmpty()) {
			return new MetricIteratorImpl(metrics);
		}

		List<Metric> filteredMetrics = metrics.stream().filter(metric -> metric.getTimestamp() >= from && metric.getTimestamp() < to).toList();

		return new MetricIteratorImpl(filteredMetrics);
	}
}
