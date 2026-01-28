package lab2;

import org.uncommons.watchmaker.framework.factories.AbstractCandidateFactory;

import java.util.Random;

public class MyFactory extends AbstractCandidateFactory<double[]> {

    private int dimension;
    private static final double MIN = -5.0;
    private static final double MAX = 5.0;

    public MyFactory(int dimension) {
        this.dimension = dimension;
    }

    @Override
    public double[] generateRandomCandidate(Random random) {
        double[] solution = new double[dimension];
        for (int i = 0; i < dimension; i++) {
            solution[i] = MIN + (MAX - MIN) * random.nextDouble();
        }
        return solution;
    }
}
