package lab2;

import org.uncommons.watchmaker.framework.operators.AbstractCrossover;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MyCrossover extends AbstractCrossover<double[]> {

    private static final double MIN = -5.0;
    private static final double MAX = 5.0;

    public MyCrossover() {
        super(1);
    }

    @Override
    protected List<double[]> mate(double[] p1, double[] p2, int i, Random random) {
        List<double[]> children = new ArrayList<>(2);

        int dim = p1.length;
        double[] c1 = new double[dim];
        double[] c2 = new double[dim];

        double alpha = random.nextDouble();
        for (int d = 0; d < dim; d++) {
            c1[d] = alpha * p1[d] + (1 - alpha) * p2[d];
            c2[d] = alpha * p2[d] + (1 - alpha) * p1[d];

            c1[d] = clamp(c1[d]);
            c2[d] = clamp(c2[d]);
        }
        children.add(c1);
        children.add(c2);
        return children;
    }

    private double clamp(double x) {
        return Math.max(MIN, Math.min(MAX, x));
    }
}
