package com.finder.algorithm;

import java.util.ArrayList;

/**
 * Created by Deep on 2017-03-16.
 * Population holds DNAs and makes them reach the target by crossover and mutation
 */
public class Population
{
    // global fields
    private DNA [] dnas;
    private String targetPhrase;
    private int dnaLength;
    private int popSize;
    private int mutRate;
    private Random r;
    private int strongIndex;

    // constructor initializes fields
    public Population(String targetPhrase, int popSize, int mutRate)
    {
        this.targetPhrase = targetPhrase;
        this.dnaLength = this.targetPhrase.length();
        this.popSize = popSize;
        this.mutRate = mutRate;
        this.strongIndex = -1;
        this.r = new Random();

        this.dnas = new DNA[popSize];
    }

    // createRandomPopulation() creates random DNAs
    public void createRandomPopulation()
    {
        for (int i = 0; i < dnas.length; i++)
           dnas[i] = new DNA(dnaLength, targetPhrase);
    }


    // createProbabilityPool() creates a probability pool that will be used
    // to pick random DNAs
    private ArrayList<DNA> createProbabilityPool()
    {
        double maxFit = 0;

        int [] scaledFitness = new int[popSize];

        ArrayList<DNA> newPool = new ArrayList<>();

        // find the maximum fitness from all the elements
        for (int i = 0; i < popSize; i++)
        {
            int currFitness = dnas[i].calcFitness();

            if (maxFit < currFitness)
                maxFit = currFitness;
        }


        // create an array of scaled fitness
        for (int i = 0; i < scaledFitness.length; i++)
            scaledFitness[i] = (int) ((dnas[i].calcFitness() / maxFit) * 100.0);


        // create a probability pool
        for (int i = 0; i < popSize; i++)
            for (int j = 0; j < scaledFitness[i]; j++)
                newPool.add(dnas[i]);

        return newPool;
    }

    // createNewDNA() creates a new DNA by applying crossover and mutation
    private DNA createNewDNA(ArrayList<DNA> probabilityPool)
    {
        DNA x = probabilityPool.get(r.produceInt(0, probabilityPool.size()-1));
        DNA y = probabilityPool.get(r.produceInt(0, probabilityPool.size()-1));
        DNA newDNA;

        // create a number between 1 and 100 that will represent mutation rate
        int randVal = r.produceInt(1, 100);

        // cross over x and y and find a new DNA
        newDNA = crossOver(x, y);

        // mutate if necessary
        if (randVal <= mutRate)
            newDNA = mutate(newDNA);

        return newDNA;
    }

    // update() creates new population by updating DNA using crossover and mutation
    public void update()
    {
        DNA [] newDNAs = new DNA[popSize];

        ArrayList<DNA> probabilityPool = createProbabilityPool();


        // create new DNA by picking random x and y from probabilityPool
        for (int i = 0; i < newDNAs.length; i++)
            newDNAs[i] = createNewDNA(probabilityPool);

        setDnas(newDNAs);
    }


    // crossOver() crosses over x DNA and y DNA and returns a DNA that has elements of both
    // but fitness maxed out as much as it can be
    public DNA crossOver(DNA x, DNA y)
    {
        DNA xy = new DNA(dnaLength, targetPhrase);
        DNA yx = new DNA(dnaLength, targetPhrase);
        DNA toggle = new DNA(dnaLength, targetPhrase);
        DNA rand = new DNA (dnaLength, targetPhrase);

        // new gene wil have half of x and half of y
        int splitPoint = x.getLength() / 2;


        //////////////// creates xy dna /////////////////

        // add the first half from x dna
        for (int i = 0; i < splitPoint; i++)
            xy.setGene(x.getGene(i), i);

        // add the second half from y dna
        for (int i = splitPoint; i < dnaLength; i++)
            xy.setGene(y.getGene(i), i);


        //////////////// creates yx dna /////////////////

        // add the first half from y dna
        for (int i = 0; i < splitPoint; i++)
            yx.setGene(y.getGene(i), i);

        // add the second half from x dna
        for (int i = splitPoint; i < dnaLength; i++)
            yx.setGene(x.getGene(i), i);


        //////////////// creates toggle dna /////////////////
        int picker = r.produceInt(0, 1); // initial picker val

        for (int i = 0; i < dnaLength; i++)
        {
            if (picker == 0)
                toggle.setGene(x.getGene(i), i);
            else
                toggle.setGene(y.getGene(i), i);

            // toggle the picker
            picker = 1 - picker;
        }


        //////////////// creates rand dna /////////////////

        for (int i = 0; i < dnaLength; i++)
        {
            // create a random value for picker between 0 and 1
            picker = r.produceInt(0, 1);

            if (picker == 0)
                rand.setGene(x.getGene(i), i);
            else
                rand.setGene(y.getGene(i), i);
        }

        return chooseBestXO (x, y, xy, yx, toggle, rand);
    }

    // chooseBestXO() chooses the best DNA from parents and various children
    private DNA chooseBestXO(DNA x, DNA y, DNA xy, DNA yx, DNA toggle, DNA rand)
    {
        int xFitness = x.calcFitness();
        int yFitness = y.calcFitness();
        int xyFitness = xy.calcFitness();
        int yxFitness = yx.calcFitness();
        int toggleFitness = toggle.calcFitness();
        int randFitness =  rand.calcFitness();

        // find the max fitness
        int maxChildFitness = Math.max(Math.max(xyFitness, yxFitness), (Math.max(toggleFitness, randFitness)));
        int maxParentFitness = Math.max(xFitness, yFitness);
        int maxFitness = Math.max(maxChildFitness, maxParentFitness);

        // return the DNA with the highest fitness

        // if xy has the highest fitness return it
        if (maxFitness == xyFitness)
            return xy;
            // if yx has the highest fitness return it
        else if (maxFitness == yxFitness)
            return yx;
            // if toggle has the highest fitness return it
        else if (maxFitness == toggleFitness)
            return toggle;
            // if rand has the highest fitness return it
        else if (maxFitness == randFitness)
            return rand;
            // if x has the highest fitness return it
        else if (maxFitness == xFitness)
            return x;
            // if y has the highest fitness return it
        else
            return y;
    }

    // mutate() mutates the DNA by replacing random gene with another random gene
    public DNA mutate(DNA dna)
    {
        int randIndex = r.produceInt(0, dna.getLength() - 1);
        char randChar  = Gene.randomChar();

        dna.setGene(randChar, randIndex);

        return dna;
    }

    // findStrongestDNA() calculates a dna that has the highest fitness
    public void findStrongestDNA()
    {
        int highestHealth = 0;
        int index = 0;

        for (int i = 0; i < dnaLength; i++)
        {
            int currFitness = dnas[i].calcFitness();

            if (highestHealth < currFitness)
            {
                highestHealth = currFitness;
                index = i;
            }
        }

        strongIndex = index;
    }

    // strongestDNA() returns the strongest DNA
    public DNA strongestDNA()
    {
        // update strong index
        findStrongestDNA();

        return getDna(strongIndex);
    }


    // getStrongIndex() finds the index of DNA with the highest fitness
    public int getStrongIndex()
    {
        // update strongIndex
        findStrongestDNA();

        return strongIndex;
    }

    // isTargetPhrase() returns true if DNA at the index is the target phrase
    public boolean isTargetPhrase(int i)
    {
        return dnas[i].isTargetPhrase();
    }

    // setDnas() sets current DNAs with provided DNAs
    public void setDnas(DNA[] dnas)
    {
        if (dnas.length == popSize)
            this.dnas = dnas;
    }

    // getDna() gets the dna at certain index
    public DNA getDna(int i)
    {
        return dnas[i];
    }

}
