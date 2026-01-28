package lab5;

import org.uncommons.watchmaker.framework.EvolutionaryOperator;

import java.util.*;

public class QueensMutation implements EvolutionaryOperator<QueensSolution> {

    private final double probability;

    public QueensMutation(double probability) {
        this.probability = probability;
    }

    @Override
    public List<QueensSolution> apply(List<QueensSolution> population, Random random) {
        List<QueensSolution> result = new ArrayList<>();

        for (QueensSolution sol : population) {
            QueensSolution copy = sol.copy();

            if (random.nextDouble() < probability) {
                int n = copy.size();
                int i = random.nextInt(n);
                int j = random.nextInt(n);

                int[] p = copy.getPermutation();
                int tmp = p[i];
                p[i] = p[j];
                p[j] = tmp;
            }
            result.add(copy);
        }
        return result;
    }
}

