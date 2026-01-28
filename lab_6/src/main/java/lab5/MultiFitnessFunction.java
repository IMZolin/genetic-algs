package lab5;

import org.uncommons.watchmaker.framework.FitnessEvaluator;

import java.util.List;
import java.util.Random;

public class MultiFitnessFunction implements FitnessEvaluator<double[]> {

    private int dimension;
    private int complexity;

    public MultiFitnessFunction(int dimension, int complexity) {
        this.dimension = dimension;
        this.complexity = complexity;
    }

    @Override
    public double getFitness(double[] solution, List<? extends double[]> list) {
        double result = 0.0;
        for (int i = 0; i < complexity; i++) {
            result += ackley(solution) / complexity;
        }
        return result;
    }

    private double ackley(double[] solution) {
        int n = dimension;
        double a = 10, b = 0.2, c = 2 * Math.PI;
        double s1 = 0.0, s2 = 0.0;

        Random noise = new Random(1);
        for (int i = 0; i < n; i++) {
            double v = solution[i] + noise.nextDouble();
            s1 += v * v;
            s2 += Math.cos(c * v);
        }

        double r =
                -a * Math.exp(-b * Math.sqrt(s1 / n)) -
                        Math.exp(s2 / n) + a + Math.E;

        return Math.abs(-r + a);
    }

    @Override
    public boolean isNatural() {
        return true;
    }
}
