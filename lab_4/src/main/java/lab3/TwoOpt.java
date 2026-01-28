package lab3;

public class TwoOpt {

    public static void improve(TspSolution sol, TspFitnessFunction fit) {
        int[] tour = sol.getTour();
        int n = tour.length;
        boolean improved = true;

        while (improved) {
            improved = false;
            for (int i = 0; i < n - 1; i++) {
                for (int j = i + 2; j < n; j++) {
                    double delta = gain(tour, i, j, fit);
                    if (delta < -1e-6) {
                        reverse(tour, i + 1, j);
                        improved = true;
                    }
                }
            }
        }
    }

    private static double gain(int[] t, int i, int j, TspFitnessFunction f) {
        int a = t[i];
        int b = t[(i + 1) % t.length];
        int c = t[j];
        int d = t[(j + 1) % t.length];

        return f.dist(a, c) + f.dist(b, d)
                - f.dist(a, b) - f.dist(c, d);
    }

    private static void reverse(int[] t, int i, int j) {
        while (i < j) {
            int tmp = t[i];
            t[i] = t[j];
            t[j] = tmp;
            i++; j--;
        }
    }

    public static void improve(TspSolution sol,
                               TspFitnessFunction fit,
                               int maxIter) {

        int[] tour = sol.getTour();
        int n = tour.length;

        for (int iter = 0; iter < maxIter; iter++) {
            boolean improved = false;

            for (int i = 0; i < n - 1; i++) {
                for (int j = i + 2; j < n; j++) {
                    if (gain(tour, i, j, fit) < -1e-6) {
                        reverse(tour, i + 1, j);
                        improved = true;
                        break;
                    }
                }
                if (improved) break;
            }
            if (!improved) break;
        }
    }
}
