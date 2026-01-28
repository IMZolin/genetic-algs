package lab5;

import org.uncommons.maths.random.Probability;
import org.uncommons.watchmaker.framework.*;
import org.uncommons.watchmaker.framework.islands.*;
import org.uncommons.watchmaker.framework.operators.EvolutionPipeline;
import org.uncommons.watchmaker.framework.selection.RouletteWheelSelection;
import org.uncommons.watchmaker.framework.selection.TournamentSelection;
import org.uncommons.watchmaker.framework.termination.GenerationCount;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class IslandsAlg {

    public static void main(String[] args) {

        int dimension = 20;
        int complexity = 5;
        int populationSize = 100;
        int generations = 20;

        int islands = Runtime.getRuntime().availableProcessors();
        int epochLength = 10;
        int migrants = 2;

        Random random = new Random();

        CandidateFactory<double[]> factory = new MyFactory(dimension);

        ArrayList<EvolutionaryOperator<double[]>> operators = new ArrayList<>();
        operators.add(new MyCrossover());
        operators.add(new MyMutation());

        EvolutionPipeline<double[]> pipeline = new EvolutionPipeline<>(operators);
        SelectionStrategy<Object> selection = new TournamentSelection(new Probability(0.95));
        FitnessEvaluator<double[]> evaluator =
                new CachedFitnessEvaluator(
                        new MultiFitnessFunction(dimension, complexity)
                );


        Migration migration = new RingMigration();

        IslandEvolution<double[]> islandModel =
                new IslandEvolution<>(
                        islands,
                        migration,
                        factory,
                        pipeline,
                        evaluator,
                        selection,
                        random
                );

        islandModel.addEvolutionObserver(new IslandEvolutionObserver<double[]>() {

            @Override
            public void populationUpdate(PopulationData<? extends double[]> data) {
                System.out.println(
                        "Epoch " + data.getGenerationNumber() +
                                " best=" + data.getBestCandidateFitness()
                );
            }

            @Override
            public void islandPopulationUpdate(int island,
                                               PopulationData<? extends double[]> data) {
                System.out.println(
                        "Island " + island +
                                " gen=" + data.getGenerationNumber() +
                                " best=" + data.getBestCandidateFitness()
                );
                System.out.println(
                        java.util.Arrays.toString(data.getBestCandidate())
                );
            }
        });
        long start = System.currentTimeMillis();
        islandModel.evolve(
                populationSize,
                10,
                epochLength,
                migrants,
                new GenerationCount(generations)
        );
        long end = System.currentTimeMillis();
        float timeSeconds = (end - start) / 1000.0f;
        System.out.println("Time (s): " + timeSeconds);

    }
}
