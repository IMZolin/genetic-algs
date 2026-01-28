package lab5;

import org.uncommons.watchmaker.framework.factories.AbstractCandidateFactory;

import java.util.Random;

public class MyFactory extends AbstractCandidateFactory<double[]> {

    private int dimension;

    public MyFactory(int dimension) {
        this.dimension = dimension;
    }

    @Override
    public double[] generateRandomCandidate(Random random) {
        double[] x = new double[dimension];
        for (int i = 0; i < dimension; i++) {
            x[i] = -5.0 + 10.0 * random.nextDouble();
        }
        return x;
    }
}
