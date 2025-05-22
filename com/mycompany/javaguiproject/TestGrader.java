/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.javaguiproject;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.*;

/**
 *
 * @author TMacRae2026
 */
public class TestGrader extends JFrame {
    
    private File keyFile;
    private File answersFile;
    
    ArrayList<Question> key;
    ArrayList<Question> studentAns;
    
    public TestGrader() {
        setTitle("Test Grader - File Picker");
        setSize(500, 250);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(2, 3, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel keyLabel = new JLabel("Select Test Key:");
        JTextField keyPath = new JTextField();
        keyPath.setEditable(false);
        JButton keyButton = new JButton("Browse...");

        JLabel answersLabel = new JLabel("Select Test Answers:");
        JTextField answersPath = new JTextField();
        answersPath.setEditable(false);
        JButton answersButton = new JButton("Browse...");

        keyButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            int result = fileChooser.showOpenDialog(this);
            if (result == JFileChooser.APPROVE_OPTION) {
                keyFile = fileChooser.getSelectedFile();
                keyPath.setText(keyFile.getAbsolutePath());
            }
        });

        answersButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            int result = fileChooser.showOpenDialog(this);
            if (result == JFileChooser.APPROVE_OPTION) {
                answersFile = fileChooser.getSelectedFile();
                answersPath.setText(answersFile.getAbsolutePath());
            }
        });
        
        JButton gradeButton = new JButton("Grade");
        gradeButton.setFont(new Font("Arial", Font.BOLD, 16));
        gradeButton.setPreferredSize(new Dimension(100, 40));
        
        gradeButton.addActionListener(e -> {
            
        });
        
        JPanel bottomPanel = new JPanel();
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 20, 20));
        bottomPanel.add(gradeButton);
        
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
    
    void gradeTest() {
        
        readKeyFromCSV(keyFile);
        
        
    }
    
    ArrayList<Question> readKeyFromCSV(File file) {
        ArrayList<Question> questionList = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                // Assume CSV format: prompt,ans1,ans2,ans3,ans4,check1,check2,check3,check4
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
    
}