/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.javaguiproject;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

/**
 *
 * @author TMacRae2026
 */
public class TestTakerChooserDisplay extends JFrame {
    
    
    public TestTakerChooserDisplay() {
        setTitle("Test Taker - File Picker");
        setSize(500, 250);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Main panel with padding and layout
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Padding between components
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;

        JLabel titleLabel = new JLabel("Choose a Test File");
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 18));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(titleLabel, gbc);

        // File picker button
        gbc.gridy++;
        gbc.gridwidth = 1;
        JButton pickFileButton = new JButton("Choose File");
        panel.add(pickFileButton, gbc);

        // Label to show selected file
        gbc.gridx = 1;
        JLabel selectedFileLabel = new JLabel("No file selected");
        selectedFileLabel.setFont(new Font("SansSerif", Font.PLAIN, 12));
        panel.add(selectedFileLabel, gbc);

        // Start test button
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        JButton startTest = new JButton("Start Test");
        startTest.setEnabled(false);
        panel.add(startTest, gbc);
        
        JFileChooser fileChooser = new JFileChooser();
        // File picker logic
        pickFileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
                int option = fileChooser.showOpenDialog(TestTakerChooserDisplay.this);
                if (option == JFileChooser.APPROVE_OPTION) {
                    selectedFileLabel.setText("Selected: " + fileChooser.getSelectedFile().getName());
                    startTest.setEnabled(true);
                } else {
                    selectedFileLabel.setText("No file selected");
                    startTest.setEnabled(false);
                }
            }
        });
        
        startTest.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new TestTakerDisplay(fileChooser.getSelectedFile());
                setVisible(false);
                
            }
        });
        
        add(panel);
        setVisible(true);
    }
    
}
