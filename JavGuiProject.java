/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.javaguiproject;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 *
 * @author TMacRae2026
 */
public class JavaGUIProject {

    public static void main(String[] args) {
        // Create a new JFrame object
        JFrame frame = new JFrame("The test maker 3000");

        // Set the size of the window
        frame.setSize(400, 300);

        // Set the window to close when the user clicks the "X" button
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create a label to display text
        JLabel label = new JLabel("Choose an option:", JLabel.CENTER);

        // Create buttons
        JButton takeTest = new JButton("Take test");
        JButton makeTest = new JButton("Make test");

        // Create a JPanel to hold the buttons
        JPanel panel = new JPanel();

        // Set the layout for the panel
        panel.setLayout(new FlowLayout());

        // Add buttons to the panel
        panel.add(takeTest);
        panel.add(makeTest);

        // Add the label and panel to the frame
        frame.setLayout(new FlowLayout());  // Optional: to arrange components in a flow
        frame.add(label);
        frame.add(panel);
        
        // Add action listener to the "Take test" button
        takeTest.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Handle button click for "Take test"
                System.out.println("Take test button clicked");
                // You can add further logic here (e.g., open a new window for the test)
            }
        });

        // Add action listener to the "Make test" button
        makeTest.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Handle button click for "Make test"
                System.out.println("Make test button clicked");
                
                // Hide the current frame
                frame.setVisible(false);
                
                // Create and show the new MakeQuestionGUI window
                new MakeQuestionGUI(1);
                
                
                System.out.println("Make test button clicked");
                // You can add further logic here (e.g., open a new window for test creation)
            }
        });
        

        // Make the window visible
        frame.setVisible(true);
    }
}

class Question {
    
    
    
}

class MakeQuestionGUI extends JFrame {
    
    int questionNumber;
    
    public MakeQuestionGUI(int questionNumber) {
        this.questionNumber = questionNumber;

        // Set up the JFrame
        setTitle("Test Editor");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(6, 2)); // Grid layout for organized display

        // Question prompt
        JLabel questionLabel = new JLabel("Question " + this.questionNumber + ": ");
        JTextField questionField = new JTextField(20);
        add(questionLabel);
        add(questionField);

        // Answer options with checkboxes
        JCheckBox[] checkBoxes = new JCheckBox[4];
        JTextField[] answerFields = new JTextField[4];
        
        for (int i = 0; i < 4; i++) {
            checkBoxes[i] = new JCheckBox("");
            answerFields[i] = new JTextField(20);
            add(answerFields[i]);
            add(checkBoxes[i]);
        }
        
        
        Button previous = new Button("Previous Question");
        if(this.questionNumber == 1) {
            previous.setEnabled(false);
        }
        
        Button next = new Button("Next Question");
        
        add(previous);
        add(next);
        
        next.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String[] answers = new String[4];
                int count = 0;
                for(JTextField a : answerFields) {
                    answers[count] = a.getText();
                    count++;
                }
                
                boolean[] checks = new boolean[4];
                count = 0;
                for (JCheckBox c : checkBoxes) {
                    checks[count] = c.isSelected();
                    count++;
                }
                
                saveQuestion(questionField.getText(), answers, checks);
                
                // Hide the current frame
                setVisible(false);
                
                // Create and show the new MakeQuestionGUI window
                new MakeQuestionGUI(questionNumber + 1);
            }
        });
        
        
        // Make the frame visible
        setVisible(true);
    }
    
    public void saveQuestion(String prompt, String[] answerFields, boolean[] checkBoxes) {
        
    }
    
}
