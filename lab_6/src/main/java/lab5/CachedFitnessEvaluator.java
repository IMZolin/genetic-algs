package lab5;

import org.uncommons.watchmaker.framework.FitnessEvaluator;

import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class CachedFitnessEvaluator implements FitnessEvaluator<double[]> {

    private final FitnessEvaluator<double[]> delegate;
    private final Map<ArrayKey, Double> cache = new ConcurrentHashMap<>();

    public CachedFitnessEvaluator(FitnessEvaluator<double[]> delegate) {
        this.delegate = delegate;
    }

    @Override
    public double getFitness(double[] candidate, java.util.List<? extends double[]> population) {
        ArrayKey key = new ArrayKey(candidate);
        return cache.computeIfAbsent(key,
                k -> delegate.getFitness(candidate, population));
    }

    @Override
    public boolean isNatural() {
        return delegate.isNatural();
    }

    /** Immutable array key */
    private static final class ArrayKey {
        private final double[] data;
        private final int hash;

        ArrayKey(double[] src) {
            this.data = Arrays.copyOf(src, src.length);
            this.hash = Arrays.hashCode(this.data);
        }

        @Override
        public boolean equals(Object o) {
            return o instanceof ArrayKey &&
                    Arrays.equals(data, ((ArrayKey) o).data);
        }

        @Override
        public int hashCode() {
            return hash;
        }
    }
}
