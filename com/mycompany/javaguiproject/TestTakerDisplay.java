/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.javaguiproject;

import java.awt.Button;
import java.awt.GridLayout;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;

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

        Button next = new Button("Next Question");
        JButton finishButton = new JButton("Finish and Submit");

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
                // Assume CSV format: prompt,ans1,ans2,ans3,ans4,chk1,chk2,chk3,chk4
                String[] parts = line.split(",", -1);
                if (parts.length < 9) continue;

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
        if (questions.size() >= this.questionNumber) {
            if (this.questionNumber == 1) {
                previous.setEnabled(false);
            }

            Question question = questions.get(this.questionNumber - 1);
            questionLabel.setText("Question " + this.questionNumber + ": ");
            questionField.setText(question.getPrompt());

            for (int i = 0; i < 4; i++) {
                answerLabels[i].setText(question.getAnswerFields()[i]);
                checkBoxes[i].setSelected(question.getCheckBoxes()[i]);
            }
        } else {
            questionLabel.setText("Question " + this.questionNumber + ": ");
            questionField.setText("");

            for (JCheckBox c : checkBoxes) {
                c.setSelected(false);
            }

            for (JLabel a : answerLabels) {
                a.setText("");
            }
            previous.setEnabled(true);
        }
    }
}