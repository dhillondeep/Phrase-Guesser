package com.finder.window;

import com.finder.algorithm.Gene;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Deep on 2017-03-04.
 * This creates the window and add functionality to it so that user can use it
 */
public class PhraseGuesser {
    // global fields
    private final Window w;
    private final Dimension windowDimension;
    private GeneticExecution ge;
    private boolean geMade;         // keeps track of creating of GeneticExecution object

    // constructor creating a window and initializing fields
    public PhraseGuesser(){
        this.w = new Window("Guess Phrase");

        // initialize all the characters in Gene
        Gene.initialize();

        // create window dimension
        this.windowDimension = getDimension();

        // set attributes of the window
        w.setSize(windowDimension);
        w.setResizable(false);
        w.setLocationRelativeTo(null);
        w.setVisible(true);
    }

    // getDimension() returns the dimension to be used for window
    // it has a fixed height but width is decided based on monitor's width
    public Dimension getDimension() {
        int height = 360;
        int width;

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        double screenWidth = screenSize.getWidth();
        double windowWidth = screenWidth / 1.8;

        width = (int) windowWidth;

        return new Dimension(width, height);
    }

    // control() handles on screen key and mouse events and makes the
    // on screen logic flow
    public void control(){
        // when run button is clicked
        w.getRunButton().addActionListener(event->{
            // variables to get user inputs
            boolean rateCorrect;                // checks if mutation rate is correct
            boolean targetCorrect;              // checks if target phrase is correct
            int mutationRate;                   // stores the mutation rate
            String targetPhrase;                // stores the targetPhrase
            int numGenerations = 10000;         // stores number of generations
            int popSize;                        // stores the population size


            // get mutation rate and check if it is valid
            try
            {
                // stores mutation rate and it has to be integer
                mutationRate = Integer.parseInt(w.getRateInputTextField().getText());

                // if mutation rate is between 0 and 100 rate is correct otherwise not
                if ((mutationRate < 0) || (mutationRate > 100))
                    rateCorrect = false;
                else
                    rateCorrect = true;
            }
            catch (NumberFormatException g)
            {
                // give a random value to mutation rate because it is invalid anyways
                mutationRate = 325461;

                // invalid rate
                rateCorrect = false;
            }


            // get target phrase as input from the user
            targetPhrase = w.getUserInputTextField().getText();

            // there has to be at least 1 character
            if (targetPhrase.length() == 0)
                targetCorrect = false;
                // check if characters typed are valid
            else
            {
                // a list of all the valid characters
                char[] charsAllowed = Gene.getAllChars();

                targetCorrect = true;

                // go through each char of targetPhrase and check if it is valid
                for (int i = 0; i < targetPhrase.length(); i++)
                {
                    char currChar = targetPhrase.charAt(i);

                    boolean isCharPresent = false;

                    for (int j = 0; j < charsAllowed.length; j++)
                    {
                        if (currChar == charsAllowed[j])
                            isCharPresent = true;
                    }

                    if (!isCharPresent)
                    {
                        targetCorrect = false;

                        // end the loop
                        i = targetPhrase.length();
                    }
                }
            }

            // based on what is invalid, show various error messages
            if (!targetCorrect && !rateCorrect)
                JOptionPane.showMessageDialog(null, "ERROR: Invalid Target Phrase and Mutation Rate");
            else if (!targetCorrect)
                JOptionPane.showMessageDialog(null, "ERROR: Some Invalid Characters in Target Phrase");
            else if (!rateCorrect)
                JOptionPane.showMessageDialog(null, "ERROR: Rate has to be >= 0 and <= 100");
            else
            {
                // everything is valid if this is reached

                // calculate the population size based on the length of target phrase
                popSize = targetPhrase.length() * 20;

                // create an object of Run Algorithm to run the algorithm
                ge = new GeneticExecution(w, targetPhrase, numGenerations, popSize, mutationRate);

                // RunAlgorithm object is created
                geMade = true;

                // make user input field uneditable
                w.getUserInputTextField().setEditable(false);

                // make mutation rate button uneditable
                w.getRateInputTextField().setEditable(false);

                // make run button un clickable
                w.getRunButton().setEnabled(false);

                // make the reset button "end" button
                w.getResetButton().setText("End");

                // run the algorithm
                ge.execute();
            }
        });

        // when reset button is clicked
        w.getResetButton().addActionListener(event ->{
            System.out.println("Reset clicked");
        });
    }

    // main method runs the program
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() ->{
           new PhraseGuesser().control();
        });
    }
}
