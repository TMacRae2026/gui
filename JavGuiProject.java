
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
                new TestEditorDisplay();
                
                
                System.out.println("Make test button clicked");
                // You can add further logic here (e.g., open a new window for test creation)
            }
        });
        

        // Make the window visible
        frame.setVisible(true);
    }
}

class Question {
    
    String prompt;
    String[] answerFields;
    boolean[] checkBoxes;
    
    public Question() {
        
    }
    
    public Question(String prompt, String[] answerFields) {
        this.prompt = prompt;
        this.answerFields = answerFields;
        this.checkBoxes = null;
    }
    
    public Question(String prompt, String[] answerFields, boolean[] checkBoxes) {
        this.prompt = prompt;
        this.answerFields = answerFields;
        this.checkBoxes = checkBoxes;
    }

    public String getPrompt() {
        return prompt;
    }

    public String[] getAnswerFields() {
        return answerFields;
    }

    public boolean[] getCheckBoxes() {
        return checkBoxes;
    }
    
    
    
    
}

class TestEditorDisplay extends JFrame {
    
    int questionNumber = 1;
    
    JLabel questionLabel;
    JTextField questionField;
    
    JCheckBox[] checkBoxes;
    JTextField[] answerFields;
    
    Button previous;
    
    public TestEditorDisplay() {
        // Set up the JFrame
        setTitle("Test Editor");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(6, 2)); // Grid layout for organized display

        // Question prompt
        questionLabel = new JLabel("Question " + this.questionNumber + ": ");
        questionField = new JTextField(20);
        add(questionLabel);
        add(questionField);

        // Answer options with checkboxes
        checkBoxes = new JCheckBox[4];
        answerFields = new JTextField[4];
        
        for (int i = 0; i < 4; i++) {
            checkBoxes[i] = new JCheckBox("");
            
            
            answerFields[i] = new JTextField(20);
            add(answerFields[i]);
            add(checkBoxes[i]);
        }
        
        
        previous = new Button("Previous Question");
        previous.setEnabled(false);
        
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
                questionNumber++;
                
                loadQuestion();
            }
        });
        previous.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                questionNumber--;
                loadQuestion();
            }
        });
        
        // Make the frame visible
        setVisible(true);
    }
    
    void loadQuestion() {
        //System.out.println(JavaGUIProject.questions.size());
        if(JavaGUIProject.questions.size() > this.questionNumber) {
            if(this.questionNumber == 1) {
                previous.setEnabled(false);
            }
            
            Question question = JavaGUIProject.questions.get(this.questionNumber - 1);
            
            questionLabel.setText("Question " + this.questionNumber + ": ");
            questionField.setText(question.getPrompt());
            
            //checkBoxes = new JCheckBox[4];
            int count = 0;
            for(boolean b:question.getCheckBoxes()) {
                checkBoxes[count].setSelected(b);
                count++;
            }
            
            count = 0;
            for(String a : question.getAnswerFields()) {
                answerFields[count].setText(a);
                count++;
            }
            
        } else {
            questionLabel.setText("Question " + this.questionNumber + ": ");
            questionField.setText("");
            
            //checkBoxes = new JCheckBox[4];
            for(JCheckBox c:checkBoxes) {
                c.setSelected(false);
            }
            
            for(JTextField a : answerFields) {
                a.setText("");
            }
            previous.setEnabled(true);
        }
    }
    
    public void saveQuestion(String prompt, String[] answerFields, boolean[] checkBoxes) {
        int index = this.questionNumber - 1;
        // Ensure the list is large enough to hold the index
        while (JavaGUIProject.questions.size() <= index) {
            JavaGUIProject.questions.add(null);  // Add placeholder elements if necessary
        }
        
        // Save the question at the specified index
        JavaGUIProject.questions.set(index, new Question(prompt, answerFields, checkBoxes));
    }

    
    
}

/*class MakeQuestionGUI extends JFrame {
    
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
    
    
    
    
    
}*/
