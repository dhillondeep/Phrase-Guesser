package com.finder.algorithm;

/**
 * Created by Deep on 2017-03-16.
 * DNA holds all the genes which are just characters
 */
public class DNA
{
    // global fields
    private int length;
    private Gene[] genes;
    private String targetPhrase;

    // constructor creates a DNA given the length of it
    public DNA(int length, String targetPhrase)
    {
        // if length of dna is different that the length of targetPhrase then modify it
        if ((length < targetPhrase.length()) && (length > targetPhrase.length()))
            this.length = targetPhrase.length();
        else
            this.length = length;

        this.genes = new Gene[length];
        this.targetPhrase = targetPhrase;

        // create all the genes inside the DNA
        for (int i = 0; i < this.genes.length; i++)
            this.genes[i] = new Gene();
    }

    // calcFitness() finds the fitness level of the DNA by comparing it
    // to the targetPhrase
    public int calcFitness()
    {
        int fitness = 0;
        int percentage = 0;

        for (int i = 0; i < genes.length; i++)
        {
           char targetPhraseCurrChar = targetPhrase.charAt(i);
           char geneChar = genes[i].getChar();

           if (geneChar == targetPhraseCurrChar)
               fitness++;
        }

        percentage = (int) ((fitness / (double) length) * 100.0);

        if (percentage <= 0)
            return 1;
        else
            return percentage;
    }

    // getLength() returns the length of DNA
    public int getLength()
    {
        return length;
    }

    // getGene() returns a gene at provided index
    public char getGene(int index)
    {
        if ((index >= 0) && (index < getLength()))
            return genes[index].getChar();
        else
            return '1';
    }

    // setGene() sets the gene to the index provided
    public void setGene(char gene, int index)
    {
        // check if index is correct
        if ((index >= 0) && (index < getLength()))
            this.genes[index].setChar(gene);
    }

    // setGenes() sets the new genes to the DNA
    public void setGenes(char[] genes)
    {
        // check if new genes are same length as old ones
        if (genes.length == this.genes.length)
            for (int i = 0; i < this.genes.length; i++)
                setGene(genes[i], i);
    }

    // getTargetPhrase() returns the target phrase
    public String getTargetPhrase()
    {
        return targetPhrase;
    }

    // isTargetPhrase() returns true or false based on if DNA is equal to the target phrase
    public boolean isTargetPhrase()
    {
        if (getTargetPhrase().equals(toString()))
            return true;
        else
            return false;
    }

    // toString returns the String representation of DNA
    @Override
    public String toString()
    {
        char[] chars = new char[genes.length];

        for (int i = 0; i < chars.length; i++)
            chars[i] = genes[i].getChar();

        return String.valueOf(chars);
    }
}