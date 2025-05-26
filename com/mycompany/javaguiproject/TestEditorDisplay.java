/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.javaguiproject;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;
import javax.swing.*;

/**
 *
 * @author TMacRae2026
 */
public class TestEditorDisplay extends JFrame {
    
    int questionNumber = 1;
    
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
        setLayout(new GridLayout(7, 2)); // Grid layout for organized display

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
        
        JButton finishButton = new JButton("Finish and Save");
        
        add(previous);
        add(next);
        add(finishButton);
        
        
        
        next.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
                saveCurQuestion();
                questionNumber++;
                loadQuestion();
            }
        });
        previous.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveCurQuestion();
                questionNumber--;
                loadQuestion();
            }
        });
        finishButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Save the last question
                saveCurQuestion();
                
                // Call the method to save the questions to CSV
                saveQuestionsToCSV(JavaGUIProject.questions, "test.csv", "key.csv");
                
                //uploadFileToServer("key.csv", JavaGUIProject.IP); (This would have been awesome if I got it to work)
                //uploadFileToServer("test.csv", JavaGUIProject.IP);
                //System.out.println(JavaGUIProject.IP);
                
                JOptionPane.showMessageDialog(null, "Test saved to CSV files!");
                setVisible(false);
                JavaGUIProject.main(new String[0]);
            }
        });
        
        
        // Make the frame visible
        setVisible(true);
    }
    
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
    
    void loadQuestion() {
        //System.out.println(JavaGUIProject.questions.size());
        if(JavaGUIProject.questions.size() >= this.questionNumber) {
            
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

    public void saveQuestionsToCSV(ArrayList<Question> questions, String testPath, String keyPath) {
        try (
            FileWriter writerWithoutCheckboxes = new FileWriter(testPath);
            FileWriter writerWithCheckboxes = new FileWriter(keyPath)
        ) {
            for (Question question : questions) {
                // Prepare data for the CSV without checkboxes
                writerWithoutCheckboxes.append(question.getPrompt()).append(",");
                String[] answerFields = question.getAnswerFields();
                for (int i = 0; i < answerFields.length; i++) {
                    writerWithoutCheckboxes.append(answerFields[i]);
                    if (i < answerFields.length - 1) writerWithoutCheckboxes.append(",");
                }
                writerWithoutCheckboxes.append("\n");

                // Prepare data for the CSV with checkboxes if they exist
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
    
    /*private void uploadFileToServer(String filePath, String serverURL) {
        String boundary = "----WebKitFormBoundary";
        try {
            File file = new File(filePath);
            HttpURLConnection connection = (HttpURLConnection) new URL(serverURL).openConnection();
            connection.setDoOutput(true);
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);

            try (DataOutputStream out = new DataOutputStream(connection.getOutputStream())) {
                // Write file data
                out.writeBytes("--" + boundary + "\r\n");
                out.writeBytes("Content-Disposition: form-data; name=\"file\"; filename=\"" + file.getName() + "\"\r\n");
                out.writeBytes("Content-Type: text/csv\r\n\r\n");

                FileInputStream fileInputStream = new FileInputStream(file);
                byte[] buffer = new byte[4096];
                int bytesRead;
                while ((bytesRead = fileInputStream.read(buffer)) != -1) {
                    out.write(buffer, 0, bytesRead);
                }
                fileInputStream.close();

                out.writeBytes("\r\n--" + boundary + "--\r\n");
                out.flush();
            }

            // Get response from the server
            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                System.out.println("File uploaded successfully: " + filePath);
            } else {
                System.err.println("Failed to upload file: " + filePath);
            }

            connection.disconnect();

        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error uploading file: " + filePath + " - " + e.getMessage(), "Upload error", JOptionPane.ERROR_MESSAGE);
        }
    }*/
    
    
}