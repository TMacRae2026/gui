/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.javaguiproject;

/**
 *
 * @author TMacRae2026
 */
public class Question {
    
    String prompt;
    String[] answerFields;
    boolean[] checkBoxes;
    
    public Question() {
        
    }
    
    public Question(String prompt, String[] answerFields) {
        this.prompt = prompt;
        this.answerFields = answerFields;
        this.checkBoxes = new boolean[4];
        
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
    
    public void setCheckBoxes(boolean[] cBoxes) {
        this.checkBoxes = cBoxes;
    }
    
}