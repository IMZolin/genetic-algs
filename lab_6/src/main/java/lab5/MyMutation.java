package lab5;

import org.uncommons.watchmaker.framework.EvolutionaryOperator;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MyMutation implements EvolutionaryOperator<double[]> {

    private static final double P = 0.2;
    private static final double SIGMA = 0.3;

    @Override
    public List<double[]> apply(List<double[]> population, Random random) {

        List<double[]> result = new ArrayList<>();

        for (double[] ind : population) {
            double[] m = ind.clone();

            for (int i = 0; i < m.length; i++) {
                if (random.nextDouble() < P) {
                    m[i] += random.nextGaussian() * SIGMA;
                    m[i] = Math.max(-5.0, Math.min(5.0, m[i]));
                }
            }
            result.add(m);
        }
        return result;
    }
}
