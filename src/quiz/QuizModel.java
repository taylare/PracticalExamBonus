/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package quiz;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * @author tayre
 */
 public class QuizModel {
   private ArrayList<Question> questions;
   private int currentQuestionIndex;
   private int correctAnswer;
   private ArrayList<Question> questionTitle;

   public QuizModel() throws ParserConfigurationException, SAXException {
       questions = new ArrayList<>();
       currentQuestionIndex = 0;
       correctAnswer = 0;
       questionTitle = new ArrayList<>();
       
        //loadBids();
        
        //reading data from the text file:
        try {
            File xmlFile = new File(System.getProperty("user.dir") + "\\src\\practicalexambonus\\questions.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(xmlFile);
                
            doc.getDocumentElement().normalize();
            NodeList nList = doc.getElementsByTagName("Question"); //root element
            
            for (int i = 0; i < nList.getLength(); i++) {
                Element eElement = (Element) nList.item(i);
                String type = eElement.getAttribute("type");
                String title = eElement.getElementsByTagName("text").item(0).getTextContent();
                String a = eElement.getElementsByTagName("a").item(0).getTextContent();
                String b = eElement.getElementsByTagName("a").item(0).getTextContent();
                String c = eElement.getElementsByTagName("c").item(0).getTextContent();
                String d = eElement.getElementsByTagName("d").item(0).getTextContent();
                
                Question questionOptions = new Question (title, type, a, b, c, d);
                questions.add(questionOptions);
            } 
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
       }
   }
  
    public int getQuizCount() { 
        return questions.size();
    }
    
    public boolean foundQuestions() {
        return !questions.isEmpty();
    }
    
    public int getCurrentQuestionNum() { 
        return currentQuestionIndex;
    } 
    
     public void nextQuestion() {
        if (currentQuestionIndex < questions.size() - 1) {
            currentQuestionIndex++;
        }
    }
    
    public Question getTheQuestion() {
    if (currentQuestionIndex >= 0 && currentQuestionIndex < questions.size()) {
        return questions.get(currentQuestionIndex);
    } else {
        throw new IllegalStateException("No questions are available to display.");
    }
}
    
    public void prevQuestion() {
        if (currentQuestionIndex > 0) {
            currentQuestionIndex--;
        }
    }
}
   
