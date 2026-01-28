package lab3;

import java.util.Arrays;

public class TspSolution {

    private int[] tour; // permutation of city indices

    public TspSolution(int[] tour) {
        this.tour = tour;
    }

    public int[] getTour() {
        return tour;
    }

    public int size() {
        return tour.length;
    }

    public TspSolution copy() {
        return new TspSolution(Arrays.copyOf(tour, tour.length));
    }

    @Override
    public String toString() {
        return Arrays.toString(tour);
    }
}
