package com.finder.window;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Deep on 2017-03-04.
 * This creates the window and add control to it so that user can use it
 */
public class PhraseGuesser {
    // global fields
    private final Window w;
    private final Dimension windowDimension;

    // constructor creating a window and initializing fields
    public PhraseGuesser(){
        this.w = new Window("Guess Phrase");

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
            System.out.println("Run clicked");
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
