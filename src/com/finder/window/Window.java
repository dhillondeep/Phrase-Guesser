package com.finder.window;

import javax.swing.*;

/**
 * Created by Deep on 2017-03-04.
 * Window creates a frame and adds various components to it
 */
public class Window extends JFrame
{
    // global fields containing various window components
    private JPanel mainPanel;
    private JTextField userInputTextField;
    private JTextArea algoOutputTextArea;
    private JButton runButton;
    private JTextArea showFitnessTextArea;
    private JTextArea showGenerationTextArea;
    private JPanel showPanel;
    private JPanel showTextPanel;
    private JLabel emptyLabelShowPanel;
    private JPanel showAlgoPanel;
    private JLabel fitnessLabel;
    private JLabel generationLabel;
    private JTextField rateInputTextField;
    private JButton resetButton;
    private JPanel userChoosePanel;
    private JPanel typePanel;
    private JPanel choosePanel;
    private JLabel mutationLabel;

    // constructor initializes JFrame super constructor and provides a title
    public Window(String title)
    {
        super(title);

        // set the look and feel
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // add main panel to the frame
        add(mainPanel);
    }

    // getUserInputTextField() returns the JTextField object used to get user input
    // for the target phrase
    public JTextField getUserInputTextField()
    {
        return userInputTextField;
    }

    // getRateInputTextField() returns the JTextField object used to get user input
    // for the mutation rate
    public JTextField getRateInputTextField()
    {
        return rateInputTextField;
    }

    // getRunButton() returns the JButton object used to run the program
    public JButton getRunButton()
    {
        return runButton;
    }

    // getAlgoOutputTextArea() returns the JTextArea object used to show the output
    // as algorithm is running
    public JTextArea getAlgoOutputTextArea()
    {
        return algoOutputTextArea;
    }

    // getShowFitnessTextArea() returns the JTextArea object used to show the fitness
    // as algorithm is running
    public JTextArea getShowFitnessTextArea()
    {
        return showFitnessTextArea;
    }

    // getShowGenerationTextArea() returns the JTextArea object used to show the
    // generation number as algorithm is running
    public JTextArea getShowGenerationTextArea()
    {
        return showGenerationTextArea;
    }

    // getResetButton() returns the JButton object used to reset the program
    public JButton getResetButton()
    {
        return resetButton;
    }
}
