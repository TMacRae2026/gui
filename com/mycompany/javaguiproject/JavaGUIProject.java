
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.javaguiproject;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;

/**
 *
 * @author TMacRae2026
 */
public class JavaGUIProject {
    
    static ArrayList<Question> questions = new ArrayList<>();
    
    //static String IP = "http://localhost:8000/";
    
    public static void main(String[] args) {
        // Create a new JFrame object
        JFrame frame = new JFrame("Tests");

        // Set the size of the window
        frame.setSize(400, 300);

        // Set the window to close when the user clicks the "X" button
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create a label to display text
        JLabel label = new JLabel("Choose an option:", JLabel.CENTER);

        // Create buttons
        JButton takeTest = new JButton("Take test");
        JButton makeTest = new JButton("Make test");
        JButton gradeTest = new JButton("Grade test");
        
        
        // Create a JPanel to hold the buttons
        JPanel panel = new JPanel();

        // Set the layout for the panel
        panel.setLayout(new FlowLayout());

        // Add buttons to the panel
        panel.add(takeTest);
        panel.add(makeTest);
        panel.add(gradeTest);
        
        
        // Add the label and panel to the frame
        frame.setLayout(new FlowLayout());  // Optional: to arrange components in a flow
        frame.add(label);
        frame.add(panel);
        
        
        // Add action listener to the "Make test" button
        makeTest.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
                // Hide the current frame
                frame.setVisible(false);
                
                // Create and show the new MakeQuestionGUI window
                new TestEditorDisplay();
                
                
                System.out.println("Make test button clicked");
                // You can add further logic here (e.g., open a new window for test creation)
            }
        });
        
        takeTest.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.setVisible(false);
                
                new TestTakerChooserDisplay();
            }
        });
        
        gradeTest.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.setVisible(false);
                
                new TestGrader();
            }
        });
        
        // Make the window visible
        frame.setVisible(true);
    }
    
}

