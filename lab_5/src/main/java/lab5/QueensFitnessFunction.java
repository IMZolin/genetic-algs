package lab5;

import org.uncommons.watchmaker.framework.FitnessEvaluator;

import java.util.List;

public class QueensFitnessFunction implements FitnessEvaluator<QueensSolution> {

    @Override
    public double getFitness(QueensSolution candidate,
                             List<? extends QueensSolution> population) {

        int[] q = candidate.getPermutation();
        int conflicts = 0;

        for (int i = 0; i < q.length; i++) {
            for (int j = i + 1; j < q.length; j++) {
                if (Math.abs(q[i] - q[j]) == Math.abs(i - j)) {
                    conflicts++;
                }
            }
        }
        return conflicts;
    }

    @Override
    public boolean isNatural() {
        return false; // минимизация
    }
}

