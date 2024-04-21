/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package quiz;

import java.util.ArrayList;
import java.util.List;



/**
 *
 * @author tayre
 */
class Question {
    private String text;
    private String type;
    private String a, b, c, d;
    private boolean aCorrect, bCorrect, cCorrect, dCorrect;
    private boolean answered;
    public static final int NUMBER_OF_SCORES_ATTRIBUTES = 4;
    public static final int INDEX_OF_NAME = 0;
    public static final int INDEX_OF_SCORE = 1;
    public static final int INDEX_OF_DATE = 2;
    public static final int INDEX_OF_TIMER = 3;
    private String name;
    private String score;
    private String date;
    private String timer;
       
  
    
    public Question( String text, String type, String a, String b, String c, String d, boolean aCorrect, boolean bCorrect, boolean cCorrect, boolean dCorrect){
      
        this.type = type;
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
        this.text = text;
        this.aCorrect = aCorrect;
        this.bCorrect = bCorrect;
        this.cCorrect = cCorrect;
        this.dCorrect = dCorrect;
    }
    
    public Question(String name, String score, String date, String timer) {
        this.name = name;
        this.score = score;
        this.date = date;
        this.timer = timer;
    }
  
    public String getA(){
        return a;
    }
    public String getType(){
        return type;
    }
    
    public String getText(){
        return text;
    }
    
    public String getB(){
        return b;
    }
    
    public String getC(){
        return c;
    }
    
    public String getD(){
        return d;
    }

    public void setA(String a){
        this.a = a;
    }
    
    public void setB(String b){
        this.b = b;
    }
    
    public void setC(){
        this.c = c;
    }
    
    public void setD(){
        this.d = d;
    }
    
    public void setText(){
       this.text = text;
    }

     public void setType(){
       this.type = type;
    }

    public boolean isACorrect() { 
        return aCorrect; 
    }
    
    public boolean isBCorrect() { 
        return bCorrect; 
    }
    public boolean isCCorrect() { 
        return cCorrect; 
    }
    public boolean isDCorrect() { 
        return dCorrect; 
    }
    
     public List<String> getCorrectAnswers() {
        List<String> correctAnswers = new ArrayList<>();
        if (aCorrect) correctAnswers.add("a");
        if (bCorrect) correctAnswers.add("b");
        if (cCorrect) correctAnswers.add("c");
        if (dCorrect) correctAnswers.add("d");
        return correctAnswers;
    }

    public String getCorrectAnswersAsString() {
        List<String> correctAnswers = getCorrectAnswers();
        return String.join(", ", correctAnswers);
    }

    public boolean isAnswered(){
        return answered;
    }
    
    public void setAnswered(boolean answered){
        this.answered = answered;
    }
    
    
    
}


