package com.mycompany.javaguiproject;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.*;

public class TestGrader extends JFrame {

    private File keyFile;        // File for the answer key
    private File answersFile;    // File for student answers

    public TestGrader() {
        // Set up the main window
        setTitle("Test Grader - File Picker");
        setSize(500, 250);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(2, 3, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // UI components for key file selection
        JLabel keyLabel = new JLabel("Select Test Key:");
        JTextField keyPath = new JTextField();
        keyPath.setEditable(false);
        JButton keyButton = new JButton("Browse...");

        // UI components for answers file selection
        JLabel answersLabel = new JLabel("Select Test Answers:");
        JTextField answersPath = new JTextField();
        answersPath.setEditable(false);
        JButton answersButton = new JButton("Browse...");

        // File chooser for key file
        keyButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            int result = fileChooser.showOpenDialog(this);
            if (result == JFileChooser.APPROVE_OPTION) {
                keyFile = fileChooser.getSelectedFile();
                keyPath.setText(keyFile.getAbsolutePath());
            }
        });

        // File chooser for answers file
        answersButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            int result = fileChooser.showOpenDialog(this);
            if (result == JFileChooser.APPROVE_OPTION) {
                answersFile = fileChooser.getSelectedFile();
                answersPath.setText(answersFile.getAbsolutePath());
            }
        });

        // Grade button to initiate grading
        JButton gradeButton = new JButton("Grade");
        gradeButton.setFont(new Font("Arial", Font.BOLD, 16));
        gradeButton.setPreferredSize(new Dimension(100, 40));

        gradeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gradeTest(); // Perform grading
                JavaGUIProject.main(new String[0]); // Go back to main screen
                setVisible(false); // Hide current frame
            }
        });

        JPanel bottomPanel = new JPanel();
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 20, 20));
        bottomPanel.add(gradeButton);

        // Assemble components into frame
        getContentPane().setLayout(new BorderLayout());
        add(panel, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);

        panel.add(keyLabel);
        panel.add(keyPath);
        panel.add(keyButton);
        panel.add(answersLabel);
        panel.add(answersPath);
        panel.add(answersButton);

        add(panel);
        setVisible(true);
    }

    // Grades the test by comparing student answers with the key
    void gradeTest() {
        ArrayList<Question> key = readKeyFromCSV(keyFile);
        ArrayList<Question> studentAns = readAnswersFromCSV(answersFile);

        int score = 0;

        for (int i = 0; i < key.size(); i++) {
            Question k = key.get(i);
            Question a = studentAns.get(i);

            // Check if prompts match
            if (!k.getPrompt().equals(a.getPrompt())) {
                System.out.println("WRONG KEY FOR THE WRONG TEST!");
                JOptionPane.showMessageDialog(null, "WRONG KEY FOR THE WRONG TEST!");
                return;
            }

            boolean[] kcb = k.getCheckBoxes();
            boolean[] acb = a.getCheckBoxes();
            boolean isCorrect = true;

            // Compare answer checkboxes
            for (int j = 0; j < 4; j++) {
                if (!kcb[j] == acb[j]) isCorrect = false;
            }

            if (isCorrect) {
                score++;
            }
        }

        // Display score
        System.out.println("Score: " + score + "/" + key.size());
        JOptionPane.showMessageDialog(null, "Score: " + score + "/" + key.size());
    }

    // Reads student answers from CSV file
    ArrayList<Question> readAnswersFromCSV(File file) {
        ArrayList<Question> questionList = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",", -1);
                if (parts.length != 5) {
                    continue;
                }
                String prompt = parts[0];
                boolean[] checks = new boolean[4];
                for (int i = 0; i < 4; i++) {
                    checks[i] = Boolean.parseBoolean(parts[i + 1]);
                }
                questionList.add(new Question(prompt, checks));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return questionList;
    }

    // Reads answer key from CSV file
    ArrayList<Question> readKeyFromCSV(File file) {
        ArrayList<Question> questionList = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                // Format: prompt,ans1,ans2,ans3,ans4,check1,check2,check3,check4
                String[] parts = line.split(",", -1);
                if (parts.length < 5) continue;

                String prompt = parts[0];
                boolean[] checks = new boolean[4];
                for (int i = 0; i < 4; i++) {
                    checks[i] = Boolean.parseBoolean(parts[i + 5]);
                }

                questionList.add(new Question(prompt, checks));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return questionList;
    }
}
