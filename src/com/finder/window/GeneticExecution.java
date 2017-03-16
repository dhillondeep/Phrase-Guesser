package com.finder.window;

import com.finder.algorithm.DNA;
import com.finder.algorithm.Population;

import javax.swing.*;
import java.util.List;

/**
 * Created by Deep6 on 2017-03-16.
 * GeneticExecution handles genetic algorithm used to guess phrase. It runs on
 * a separate thread
 */
public class GeneticExecution extends SwingWorker<Integer, int[]>{
    // global fields
    private final Window w;
    private final String targetPhrase;
    private final int numGenerations;
    private final int popSize;
    private final int mutationRate;
    private Population pop;

    private boolean cancel;
    private boolean isFinished;

    private final int SOLUTION_NOT_FOUND = -100;

    // constructor initializes global fields
    public GeneticExecution(final Window w, String targetPhrase, int numGenerations, int popSize, int mutationRate)
    {
        this.w = w;
        this.targetPhrase = targetPhrase;
        this.numGenerations = numGenerations;
        this.popSize = popSize;
        this.mutationRate = mutationRate;
    }

    // executes the generic algorithm
    @Override
    protected Integer doInBackground() throws Exception
    {
        // set isFinished to false
        isFinished = false;

        // make cancel false
        cancel = false;

        // create a population
        pop = new Population(targetPhrase, popSize, mutationRate);

        // randomly create population
        pop.createRandomPopulation();

        // counter while going through generations
        int generationCounter = 0;

        // boolean to check if solution is found
        boolean solutionFound = false;

        // show initial output
        publish(new int [] {pop.getStrongIndex(), generationCounter});


        // loop through generations
        while (generationCounter <= numGenerations)
        {
            // if canceled, end the loop and finish
            if (cancel)
                break;

            // if solution is found
            if (pop.isTargetPhrase(pop.getStrongIndex()))
            {
                // set solutionFound to true
                solutionFound = true;

                // output solution
                publish(new int [] {pop.getStrongIndex(), generationCounter});

                // come out of loop and finish
                break;
            }
            // if solution is not found
            else
            {
                // update the population
                pop.update();

                // add one to generation number
                generationCounter++;

                // show current output
                publish(new int [] {pop.getStrongIndex(), generationCounter});
            }
        }

        // if solution is not found and program was not canceled
        if(!solutionFound && !cancel)
        {
            // show solution not found message
            publish(new int [] {SOLUTION_NOT_FOUND, generationCounter});
        }

        // make isFinished true
        isFinished = true;

        return null;
    }

    @Override
    protected void process(final List<int[]> chunks)
    {
        // if the algorithm has finished, reset the "reset" button to reset
        if (isFinished)
            w.getResetButton().setText("Reset");

        // print all the publishes
        for (int i = 0; i < chunks.size(); i++)
        {
            // if solution is not found
            if (chunks.get(i)[0] == SOLUTION_NOT_FOUND)
                w.getAlgoOutputTextArea().setText("Solution not found :(");
                // otherwise
            else
            {
                DNA strongestDNA = pop.getDna(chunks.get(i)[0]);
                int generationCounter = chunks.get(i)[1];
                int fitness = strongestDNA.calcFitness();

                // set the best of current population based on fitness
                w.getAlgoOutputTextArea().setText(strongestDNA.toString());

                // set current fitness
                w.getShowFitnessTextArea().setText(Integer.toString(fitness));

                if (generationCounter > numGenerations)
                    generationCounter = numGenerations;

                // set generation number
                w.getShowGenerationTextArea().setText(Integer.toString(generationCounter));
            }

            // refresh the frame
            w.revalidate();
            w.repaint();
        }
    }

    // end() cancels the algorithm that is running
    public void end()
    {
        this.cancel = true;
    }
}
