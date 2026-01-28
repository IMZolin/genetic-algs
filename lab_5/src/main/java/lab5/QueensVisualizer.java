package lab5;

public class QueensVisualizer {

    public static void visualize(int[] solution) {
        int N = solution.length;
        for (int row = 0; row < N; row++) {
            for (int i : solution) {
                if (i == row) {
                    System.out.print("Q ");
                } else {
                    System.out.print(". ");
                }
            }
            System.out.println();
        }
        System.out.println();
    }

    public static void main(String[] args) {
        int[] solution = {2, 5, 7, 0, 3, 6, 4, 1};
        System.out.println("N-Queens solution visualization:");
        visualize(solution);
    }
}
