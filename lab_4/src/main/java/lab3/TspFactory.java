package lab3;

import org.uncommons.watchmaker.framework.factories.AbstractCandidateFactory;

import java.util.Random;

public class TspFactory extends AbstractCandidateFactory<TspSolution> {

    private final int cityCount;

    public TspFactory(int cityCount) {
        this.cityCount = cityCount;
    }

    @Override
    public TspSolution generateRandomCandidate(Random random) {
        int[] tour = new int[cityCount];
        for (int i = 0; i < cityCount; i++) {
            tour[i] = i;
        }

        // Перемешивание (Fisher–Yates shuffle)
        for (int i = cityCount - 1; i > 0; i--) {
            int j = random.nextInt(i + 1);
            int tmp = tour[i];
            tour[i] = tour[j];
            tour[j] = tmp;
        }

        return new TspSolution(tour);
    }
}
