package lab3;

import org.uncommons.watchmaker.framework.EvolutionaryOperator;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

public class TspMutation implements EvolutionaryOperator<TspSolution> {

    private static final double MUTATION_RATE = 0.2;
    private static final double TWO_OPT_RATE = 0.3;
    private static final int TWO_OPT_ITERS = 100;

    private final TspFitnessFunction fitness;

    public TspMutation(TspFitnessFunction fitness) {
        this.fitness = fitness;
    }

    @Override
    public List<TspSolution> apply(List<TspSolution> population, Random random) {
        List<TspSolution> result = new ArrayList<>();

        for (TspSolution sol : population) {
            TspSolution copy = sol.copy();

            if (random.nextDouble() < MUTATION_RATE) {
                int i = random.nextInt(copy.size());
                int j = random.nextInt(copy.size());
                int[] t = copy.getTour();
                int tmp = t[i];
                t[i] = t[j];
                t[j] = tmp;
            }

            if (random.nextDouble() < TWO_OPT_RATE) {
                TwoOpt.improve(copy, fitness, TWO_OPT_ITERS);
            }

            result.add(copy);
        }
        return result;
    }
}
