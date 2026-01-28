package lab2;

import org.uncommons.watchmaker.framework.EvolutionaryOperator;

import java.util.List;
import java.util.Random;

public class MyMutation implements EvolutionaryOperator<double[]> {

    private static final double MIN = -5.0;
    private static final double MAX = 5.0;

    private double mutationRate = 0.4;   // вероятность мутации гена
    private double sigma = 0.5;           // стандартное отклонение

    // вероятность того, что особь вообще будет мутироваться
    private double individualMutationProb = 0.75;

    @Override
    public List<double[]> apply(List<double[]> population, Random random) {

        // предполагаем, что лучший индивид находится на позиции 0
        for (int i = 1; i < population.size(); i++) {
            if (random.nextDouble() > individualMutationProb) {
                continue;
            }

            double[] ind = population.get(i);
            double[] mutated = ind.clone();

            for (int d = 0; d < mutated.length; d++) {
                if (random.nextDouble() < mutationRate) {
                    mutated[d] += random.nextGaussian() * sigma;
                    mutated[d] = clamp(mutated[d]);
                }
            }

            population.set(i, mutated);
        }
        return population;
    }

    private double clamp(double x) {
        return Math.max(MIN, Math.min(MAX, x));
    }
}
