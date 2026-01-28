package lab3;

import org.uncommons.maths.random.Probability;
import org.uncommons.watchmaker.framework.*;
import org.uncommons.watchmaker.framework.operators.EvolutionPipeline;
import org.uncommons.watchmaker.framework.selection.TournamentSelection;
import org.uncommons.watchmaker.framework.termination.GenerationCount;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class TspAlg {

    public static void main(String[] args) {
        String problem = "PKA379"; // name of problem or path to input file XQF131

        int populationSize = 400; // size of population
        int generations = 5000; // number of generations

        Random random = new Random(); // random

        CandidateFactory<TspSolution> factory = new TspFactory(379); // generation of solutions
        TspFitnessFunction evaluator = new TspFitnessFunction(problem); // Fitness function

        ArrayList<EvolutionaryOperator<TspSolution>> operators = new ArrayList<EvolutionaryOperator<TspSolution>>();
        operators.add(new TspCrossoverOX()); // Crossover
        operators.add(new TspMutation(evaluator)); // Mutation
        EvolutionPipeline<TspSolution> pipeline = new EvolutionPipeline<TspSolution>(operators);

        SelectionStrategy<Object> selection =
                new TournamentSelection(new Probability(0.95));
        // Selection operator
        EvolutionEngine<TspSolution> algorithm =
                new GenerationalEvolutionEngine<>(
                        factory, pipeline, evaluator, selection, random
                );

        algorithm.addEvolutionObserver(new EvolutionObserver() {
            public void populationUpdate(PopulationData populationData) {
                double bestFit = populationData.getBestCandidateFitness();
                System.out.println("Generation " + populationData.getGenerationNumber() + ": " + bestFit);
                TspSolution best = (TspSolution)populationData.getBestCandidate();
                System.out.println("\tBest solution = " + best.toString());
            }
        });

        algorithm.evolve(populationSize, 10, new GenerationCount(generations));
    }
}
