/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.javaguiproject;

import java.awt.Panel;
import javax.swing.JFrame;

/**
 *
 * @author TMacRae2026
 */
public class TestGrader extends JFrame {
    
    public TestGrader() {
        setTitle("Test Grader - File Picker");
        setSize(500, 250);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        Panel panel = new Panel();
        
        add(panel);
        setVisible(true);
    }
    
}