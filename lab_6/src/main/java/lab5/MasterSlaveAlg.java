package lab5;

import org.uncommons.maths.random.Probability;
import org.uncommons.watchmaker.framework.*;
import org.uncommons.watchmaker.framework.operators.EvolutionPipeline;
import org.uncommons.watchmaker.framework.selection.RouletteWheelSelection;
import org.uncommons.watchmaker.framework.selection.TournamentSelection;
import org.uncommons.watchmaker.framework.termination.GenerationCount;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class MasterSlaveAlg {

    public static void main(String[] args) {

        int dimension = 20;
        int complexity = 1;
        int populationSize = 100;
        int generations = 3000;

        Random random = new Random();

        CandidateFactory<double[]> factory = new MyFactory(dimension);

        ArrayList<EvolutionaryOperator<double[]>> operators = new ArrayList<>();
        operators.add(new MyCrossover());
        operators.add(new MyMutation());

        EvolutionPipeline<double[]> pipeline = new EvolutionPipeline<>(operators);
        SelectionStrategy<Object> selection = new TournamentSelection(new Probability(0.95));
        FitnessEvaluator<double[]> evaluator = new MultiFitnessFunction(dimension, complexity);

        AbstractEvolutionEngine<double[]> engine =
                new SteadyStateEvolutionEngine<>(
                        factory, pipeline, evaluator,
                        selection, populationSize, false, random
                );

        engine.setSingleThreaded(true); // master–slave

        engine.addEvolutionObserver(data -> {
            System.out.println(
                    "Gen " + data.getGenerationNumber() +
                            " best=" + data.getBestCandidateFitness()
            );
            System.out.println(
                    Arrays.toString((double[]) data.getBestCandidate())
            );
        });
        long start = System.currentTimeMillis();
        engine.evolve(populationSize, 5, new GenerationCount(generations));
        long end = System.currentTimeMillis();
        float timeSeconds = (end - start) / 1000.0f;
        System.out.println("Time (s): " + timeSeconds);
    }
}
