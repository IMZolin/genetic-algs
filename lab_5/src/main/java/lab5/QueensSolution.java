package lab5;

import java.util.Arrays;

public class QueensSolution {
    private final int[] permutation;

    public QueensSolution(int[] permutation) {
        this.permutation = permutation;
    }

    public int[] getPermutation() {
        return permutation;
    }

    public int size() {
        return permutation.length;
    }

    public QueensSolution copy() {
        return new QueensSolution(Arrays.copyOf(permutation, permutation.length));
    }

    @Override
    public String toString() {
        return Arrays.toString(permutation);
    }
}
