package lab5;

import org.uncommons.maths.random.MersenneTwisterRNG;
import org.uncommons.maths.random.Probability;
import org.uncommons.watchmaker.framework.*;
import org.uncommons.watchmaker.framework.operators.EvolutionPipeline;
import org.uncommons.watchmaker.framework.selection.*;
import org.uncommons.watchmaker.framework.termination.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class QueensAlg {

    public static void main(String[] args) {

        int N = 11;
        int populationSize = 60;
        int generations = 2000;

        Random random = new MersenneTwisterRNG();

        CandidateFactory<QueensSolution> factory = new QueensFactory(N);
        QueensFitnessFunction fitness = new QueensFitnessFunction();

        List<EvolutionaryOperator<QueensSolution>> operators = new ArrayList<>();
        operators.add(new QueensCrossover()); // OX кроссовер
        operators.add(new QueensMutation(0.35));  // Swap мутация
        EvolutionPipeline<QueensSolution> pipeline = new EvolutionPipeline<>(operators);

        SelectionStrategy<Object> selection = new TournamentSelection(new Probability(0.95));

        EvolutionEngine<QueensSolution> engine =
                new GenerationalEvolutionEngine<>(factory, pipeline, fitness, selection, random);

        engine.addEvolutionObserver(populationData -> {
            System.out.println("Gen " + populationData.getGenerationNumber()
                    + " | Best fitness = " + populationData.getBestCandidateFitness()
                    + " | " + populationData.getBestCandidate());
        });

        TerminationCondition terminate = new GenerationCount(generations);
        QueensSolution best = engine.evolve(populationSize, 10, terminate);

        System.out.println("\nFinal solution:");
        System.out.println(best);

        QueensVisualizer.visualize(best.getPermutation());
    }
}
