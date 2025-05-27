package com.mycompany.javaguiproject;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;
import javax.swing.*;

/**
 * GUI for editing a test, adding questions, answers, and correct checkboxes.
 */
public class TestEditorDisplay extends JFrame {
    
    int questionNumber = 1; // Tracks current question number
    
    JLabel questionLabel;
    JTextField questionField;
    
    JCheckBox[] checkBoxes;
    JTextField[] answerFields;
    
    Button previous;
    
    public TestEditorDisplay() {
        // Set up the JFrame
        setTitle("Test Editor");
        setSize(400, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(7, 2)); // Grid layout with 7 rows, 2 columns

        // UI for question prompt
        questionLabel = new JLabel("Question " + this.questionNumber + ": ");
        questionField = new JTextField(20);
        add(questionLabel);
        add(questionField);

        // UI for answer options and checkboxes
        checkBoxes = new JCheckBox[4];
        answerFields = new JTextField[4];
        
        for (int i = 0; i < 4; i++) {
            checkBoxes[i] = new JCheckBox("");
            answerFields[i] = new JTextField(20);
            add(answerFields[i]);
            add(checkBoxes[i]);
        }

        // Navigation and finish buttons
        previous = new Button("Previous Question");
        previous.setEnabled(false);
        
        Button next = new Button("Next Question");
        JButton finishButton = new JButton("Finish and Save");
        
        add(previous);
        add(next);
        add(finishButton);
        
        // Handle "Next Question" button click
        next.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveCurQuestion();
                questionNumber++;
                loadQuestion();
            }
        });

        // Handle "Previous Question" button click
        previous.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveCurQuestion();
                questionNumber--;
                loadQuestion();
            }
        });

        // Handle "Finish and Save" button click
        finishButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveCurQuestion();
                saveQuestionsToCSV(JavaGUIProject.questions, "test.csv", "key.csv");
                JOptionPane.showMessageDialog(null, "Test saved to CSV files!");
                setVisible(false);
                JavaGUIProject.main(new String[0]);
            }
        });

        setVisible(true); // Display the GUI
    }

    // Save current question inputs to memory
    void saveCurQuestion() {
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
    }

    // Load question data into the UI fields
    void loadQuestion() {
        if(JavaGUIProject.questions.size() >= this.questionNumber) {
            if(this.questionNumber == 1) {
                previous.setEnabled(false);
            }

            Question question = JavaGUIProject.questions.get(this.questionNumber - 1);
            questionLabel.setText("Question " + this.questionNumber + ": ");
            questionField.setText(question.getPrompt());

            int count = 0;
            for(boolean b : question.getCheckBoxes()) {
                checkBoxes[count].setSelected(b);
                count++;
            }

            count = 0;
            for(String a : question.getAnswerFields()) {
                answerFields[count].setText(a);
                count++;
            }
        } else {
            // Load empty fields for a new question
            questionLabel.setText("Question " + this.questionNumber + ": ");
            questionField.setText("");

            for(JCheckBox c : checkBoxes) {
                c.setSelected(false);
            }

            for(JTextField a : answerFields) {
                a.setText("");
            }

            previous.setEnabled(true);
        }
    }

    // Save a single question to the question list
    public void saveQuestion(String prompt, String[] answerFields, boolean[] checkBoxes) {
        int index = this.questionNumber - 1;
        while (JavaGUIProject.questions.size() <= index) {
            JavaGUIProject.questions.add(null);
        }
        JavaGUIProject.questions.set(index, new Question(prompt, answerFields, checkBoxes));
    }

    // Save questions to CSV files (one with checkboxes, one without)
    public void saveQuestionsToCSV(ArrayList<Question> questions, String testPath, String keyPath) {
        try (
            FileWriter writerWithoutCheckboxes = new FileWriter(testPath);
            FileWriter writerWithCheckboxes = new FileWriter(keyPath)
        ) {
            for (Question question : questions) {
                // Write to CSV without checkboxes
                writerWithoutCheckboxes.append(question.getPrompt()).append(",");
                String[] answerFields = question.getAnswerFields();
                for (int i = 0; i < answerFields.length; i++) {
                    writerWithoutCheckboxes.append(answerFields[i]);
                    if (i < answerFields.length - 1) writerWithoutCheckboxes.append(",");
                }
                writerWithoutCheckboxes.append("\n");

                // Write to CSV with checkboxes
                boolean[] checkBoxes = question.getCheckBoxes();
                if (checkBoxes != null) {
                    writerWithCheckboxes.append(question.getPrompt()).append(",");
                    for (int i = 0; i < answerFields.length; i++) {
                        writerWithCheckboxes.append(answerFields[i]);
                        if (i < answerFields.length - 1) writerWithCheckboxes.append(",");
                    }
                    writerWithCheckboxes.append(",");
                    for (int i = 0; i < checkBoxes.length; i++) {
                        writerWithCheckboxes.append(Boolean.toString(checkBoxes[i]));
                        if (i < checkBoxes.length - 1) writerWithCheckboxes.append(",");
                    }
                    writerWithCheckboxes.append("\n");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
