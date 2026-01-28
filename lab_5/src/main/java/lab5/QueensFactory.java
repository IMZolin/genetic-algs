package lab5;

import org.uncommons.watchmaker.framework.factories.AbstractCandidateFactory;

import java.util.Random;

public class QueensFactory extends AbstractCandidateFactory<QueensSolution> {

    private final int n;

    public QueensFactory(int n) {
        this.n = n;
    }

    @Override
    public QueensSolution generateRandomCandidate(Random random) {
        int[] perm = new int[n];
        for (int i = 0; i < n; i++) {
            perm[i] = i;
        }

        // Fisher–Yates shuffle
        for (int i = n - 1; i > 0; i--) {
            int j = random.nextInt(i + 1);
            int tmp = perm[i];
            perm[i] = perm[j];
            perm[j] = tmp;
        }

        return new QueensSolution(perm);
    }
}
