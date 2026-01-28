package lab3;

import org.uncommons.watchmaker.framework.FitnessEvaluator;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class TspFitnessFunction implements FitnessEvaluator<TspSolution> {

    private static class City {
        double x, y;
        City(double x, double y) { this.x = x; this.y = y; }
    }

    private final List<City> cities = new ArrayList<>();

    public TspFitnessFunction(String problem) {
        load(problem + ".tsp");
    }

    private void load(String resourceName) {
        try {
            InputStream is = getClass()
                    .getClassLoader()
                    .getResourceAsStream(resourceName);

            if (is == null) {
                throw new RuntimeException("Resource not found: " + resourceName);
            }

            try (BufferedReader br = new BufferedReader(new InputStreamReader(is))) {
                String line;
                boolean readCoords = false;

                while ((line = br.readLine()) != null) {
                    line = line.trim();
                    if (line.isEmpty()) continue;

                    if (line.equalsIgnoreCase("NODE_COORD_SECTION")) {
                        readCoords = true;
                        continue;
                    }

                    if (line.equalsIgnoreCase("EOF")) {
                        break;
                    }

                    if (!readCoords) {
                        continue; // skip header
                    }

                    String[] p = line.split("\\s+");
                    if (p.length < 3) continue;

                    double x = Double.parseDouble(p[1]);
                    double y = Double.parseDouble(p[2]);

                    cities.add(new City(x, y));
                }
            }

            if (cities.isEmpty()) {
                throw new RuntimeException("No city coordinates loaded!");
            }

            System.out.println("Loaded cities: " + cities.size());

        } catch (Exception e) {
            throw new RuntimeException("Failed to load TSP resource", e);
        }
    }


    @Override
    public double getFitness(TspSolution solution, List<? extends TspSolution> list) {
        int[] tour = solution.getTour();
        double length = 0.0;

        for (int i = 0; i < tour.length - 1; i++) {
            length += dist(tour[i], tour[i + 1]);
        }
        length += dist(tour[tour.length - 1], tour[0]);
        return length;
    }

    public double dist(int a, int b) {
        City c1 = cities.get(a);
        City c2 = cities.get(b);
        double dx = c1.x - c2.x;
        double dy = c1.y - c2.y;
        return Math.sqrt(dx * dx + dy * dy);
    }


    @Override
    public boolean isNatural() {
        return false;
    }
}
