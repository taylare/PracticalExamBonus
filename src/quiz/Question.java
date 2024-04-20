/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package quiz;

import java.util.List;



/**
 *
 * @author tayre
 */
class Question {
    private String text;
    private String type;
    private String a;
    private String b;
    private String c;
    private String d;
    
    public Question(String type, String a, String b, String c, String d){
        this.type = type;
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
    }
    
    public Question (String text){
        this.text = text;
    }
    

    public String getA(){
        return a;
    }
    public String getType(){
        return a;
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
    
    
    
}


