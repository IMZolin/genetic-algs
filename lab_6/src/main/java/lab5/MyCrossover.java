package lab5;

import org.uncommons.watchmaker.framework.operators.AbstractCrossover;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MyCrossover extends AbstractCrossover<double[]> {

    public MyCrossover() {
        super(1);
    }

    @Override
    protected List<double[]> mate(double[] p1, double[] p2, int i, Random random) {

        int n = p1.length;
        double alpha = random.nextDouble();

        double[] c1 = new double[n];
        double[] c2 = new double[n];

        for (int k = 0; k < n; k++) {
            c1[k] = alpha * p1[k] + (1 - alpha) * p2[k];
            c2[k] = (1 - alpha) * p1[k] + alpha * p2[k];
        }

        List<double[]> children = new ArrayList<>(2);
        children.add(c1);
        children.add(c2);
        return children;
    }
}
