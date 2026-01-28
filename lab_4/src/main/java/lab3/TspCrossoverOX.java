package lab3;

import org.uncommons.watchmaker.framework.operators.AbstractCrossover;

import java.util.*;

public class TspCrossoverOX extends AbstractCrossover<TspSolution> {

    public TspCrossoverOX() {
        super(1); // один кроссовер на пару родителей
    }

    @Override
    protected List<TspSolution> mate(TspSolution p1, TspSolution p2, int points, Random random) {
        int size = p1.size();
        int[] parent1 = p1.getTour();
        int[] parent2 = p2.getTour();

        int[] child = new int[size];
        Arrays.fill(child, -1);

        // Выбираем случайный сегмент
        int start = random.nextInt(size);
        int end = random.nextInt(size);
        if (start > end) {
            int t = start;
            start = end;
            end = t;
        }

        // Копируем сегмент из первого родителя
        for (int i = start; i <= end; i++) {
            child[i] = parent1[i];
        }

        // Заполняем оставшиеся позиции порядком из второго родителя
        int idx = (end + 1) % size;
        for (int i = 0; i < size; i++) {
            int gene = parent2[(end + 1 + i) % size];
            if (!contains(child, gene)) {
                child[idx] = gene;
                idx = (idx + 1) % size;
            }
        }

        return List.of(new TspSolution(child));
    }

    private boolean contains(int[] arr, int val) {
        for (int x : arr) if (x == val) return true;
        return false;
    }
}
