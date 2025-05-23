/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.javaguiproject;

import java.awt.Button;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

/**
 *
 * @author TMacRae2026
 */
public class TestTakerDisplay extends JFrame {

    int questionNumber = 1;
    JLabel questionLabel;
    JLabel questionField;
    JCheckBox[] checkBoxes;
    JLabel[] answerLabels;
    Button next;
    Button previous;
    ArrayList<Question> questions;

    public TestTakerDisplay(File CSVTest) {
        setTitle("Test");
        setSize(400, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(7, 2));

        questionLabel = new JLabel("Question " + this.questionNumber + ": ");
        questionField = new JLabel();
        add(questionLabel);
        add(questionField);

        checkBoxes = new JCheckBox[4];
        answerLabels = new JLabel[4];

        for (int i = 0; i < 4; i++) {
            answerLabels[i] = new JLabel();
            checkBoxes[i] = new JCheckBox("");
            add(answerLabels[i]);
            add(checkBoxes[i]);
        }

        previous = new Button("Previous Question");
        previous.setEnabled(false);

        next = new Button("Next Question");
        JButton finishButton = new JButton("Finish and Submit");
        
        next.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveAnswer(questionNumber, jboxToBoolArr(checkBoxes));
                questionNumber++;
                loadQuestion();
            }
        });
        
        previous.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveAnswer(questionNumber, jboxToBoolArr(checkBoxes));
                questionNumber--;
                loadQuestion();
            }
        });
        
        finishButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int result = JOptionPane.showConfirmDialog(
                    null,
                    "Are you sure?",
                    "Confirmation",
                    JOptionPane.YES_NO_OPTION
                );

                if (result == JOptionPane.YES_OPTION) {
                    saveAnswer(questionNumber, jboxToBoolArr(checkBoxes));
                    saveAnswers();
                }
            }
        });

        
        add(previous);
        add(next);
        add(finishButton);

        questions = readQuestionsFromCSV(CSVTest);

        loadQuestion();

        setVisible(true);
    }

    ArrayList<Question> readQuestionsFromCSV(File file) {
        ArrayList<Question> questionList = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                // Assume CSV format: prompt,ans1,ans2,ans3,ans4
                String[] parts = line.split(",", -1);
                if (parts.length < 5) continue;

                String prompt = parts[0];
                String[] answers = new String[4];
                //boolean[] checks = new boolean[4];

                for (int i = 0; i < 4; i++) {
                    answers[i] = parts[i + 1];
                    //checks[i] = Boolean.parseBoolean(parts[i + 5]);
                }

                questionList.add(new Question(prompt, answers));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return questionList;
    }

    void loadQuestion() {
        if(questionNumber == 1) {
            previous.setEnabled(false);
        } else {
            previous.setEnabled(true);
        }
        if(questionNumber == questions.size()) {
            next.setEnabled(false);
        } else {
            next.setEnabled(true);
        }
        Question question = questions.get(this.questionNumber - 1);
        questionLabel.setText("Question " + this.questionNumber + ": ");
        questionField.setText(question.getPrompt());

        for (int i = 0; i < 4; i++) {
            answerLabels[i].setText(question.getAnswerFields()[i]);
            checkBoxes[i].setSelected(question.getCheckBoxes()[i]);
        }
    }
    
    boolean[] jboxToBoolArr(JCheckBox[] jboxes) {
        boolean[] result = new boolean[jboxes.length];
        for (int i = 0; i < jboxes.length; i++) {
            result[i] = jboxes[i].isSelected();
        }
        return result;
    }

    
    void saveAnswer(int question, boolean[] checkBoxes) {
        questions.get(question - 1).setCheckBoxes(checkBoxes);
    }
    
    void saveAnswers() {
        try {
            FileWriter writer = new FileWriter("Answers.csv");
            System.out.println("qnumb:" + questions.size());
            for(Question question:questions) {
                //We don't neccicarily NEED the prompt but I'll use it to prevent the wrong key being used to grade the students answers
                writer.append(question.getPrompt()).append(",");
                
                //add all the check boxes in the same line as the prompt.
                boolean[] checkBoxes = question.getCheckBoxes();
                for (int i = 0; i < checkBoxes.length; i++) {
                    writer.append(Boolean.toString(checkBoxes[i]));
                    if (i < checkBoxes.length - 1) writer.append(",");
                }
                //Make a new line
                writer.append("\n");
            }
            //For some reason this line of code is super duper important
            writer.close();
        } catch (IOException ex) {
            Logger.getLogger(TestTakerDisplay.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
        }
        
    }
}