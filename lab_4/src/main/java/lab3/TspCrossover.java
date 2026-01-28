package lab3;

import org.uncommons.watchmaker.framework.operators.AbstractCrossover;

import java.util.*;

public class TspCrossover extends AbstractCrossover<TspSolution> {

    public TspCrossover() {
        super(1);
    }

    @Override
    protected List<TspSolution> mate(TspSolution p1, TspSolution p2, int points, Random random) {
        int size = p1.size();
        int a = random.nextInt(size);
        int b = random.nextInt(size);
        if (a > b) { int t = a; a = b; b = t; }

        int[] child = new int[size];
        Arrays.fill(child, -1);

        // copy segment
        for (int i = a; i <= b; i++) {
            child[i] = p1.getTour()[i];
        }

        // fill remaining from p2
        int idx = (b + 1) % size;
        for (int gene : p2.getTour()) {
            if (!contains(child, gene)) {
                child[idx] = gene;
                idx = (idx + 1) % size;
            }
        }

        return List.of(new TspSolution(child));
    }

    private boolean contains(int[] arr, int v) {
        for (int x : arr) if (x == v) return true;
        return false;
    }
}
