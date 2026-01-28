package lab5;

import org.uncommons.watchmaker.framework.operators.AbstractCrossover;

import java.util.*;

public class QueensCrossover extends AbstractCrossover<QueensSolution> {

    public QueensCrossover() {
        super(1);
    }

    @Override
    protected List<QueensSolution> mate(QueensSolution p1,
                                        QueensSolution p2,
                                        int points,
                                        Random random) {

        int n = p1.size();
        int[] child = new int[n];
        Arrays.fill(child, -1);

        int start = random.nextInt(n);
        int end = random.nextInt(n);

        if (start > end) {
            int tmp = start;
            start = end;
            end = tmp;
        }

        int[] a = p1.getPermutation();
        int[] b = p2.getPermutation();

        for (int i = start; i <= end; i++) {
            child[i] = a[i];
        }

        int idx = (end + 1) % n;
        for (int i = 0; i < n; i++) {
            int gene = b[(end + 1 + i) % n];
            if (!contains(child, gene)) {
                child[idx] = gene;
                idx = (idx + 1) % n;
            }
        }

        return List.of(new QueensSolution(child));
    }

    private boolean contains(int[] arr, int v) {
        for (int x : arr) {
            if (x == v) return true;
        }
        return false;
    }
}
