/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package quiz;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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

   public QuizModel() throws ParserConfigurationException, SAXException {
       questions = new ArrayList<>();
       currentQuestionIndex = 0;
 
        
        //reading data from the text file:
        try {
            File xmlFile = new File(System.getProperty("user.dir") + "\\src\\quiz\\quiz.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(xmlFile);
            // Check if the document is null
            if (doc != null) {

                doc.getDocumentElement().normalize();
                NodeList nList = doc.getElementsByTagName("Question"); //root element

                for (int i = 0; i < nList.getLength(); i++) {
                     Element eElement = (Element) nList.item(i);
                String type = eElement.getAttribute("type");
                String text = eElement.getElementsByTagName("Text").item(0).getTextContent();

                Element aElement = (Element) eElement.getElementsByTagName("a").item(0);
                String a = aElement.getTextContent();
                boolean aCorrect = "true".equals(aElement.getAttribute("correct"));

                Element bElement = (Element) eElement.getElementsByTagName("b").item(0);
                String b = bElement.getTextContent();
                boolean bCorrect = "true".equals(bElement.getAttribute("correct"));

                Element cElement = (Element) eElement.getElementsByTagName("c").item(0);
                String c = cElement.getTextContent();
                boolean cCorrect = "true".equals(cElement.getAttribute("correct"));

                Element dElement = (Element) eElement.getElementsByTagName("d").item(0);
                String d = dElement.getTextContent();
                boolean dCorrect = "true".equals(dElement.getAttribute("correct"));

                Question question = new Question(text, type, a, b, c, d, aCorrect, bCorrect, cCorrect, dCorrect);
                questions.add(question);
                } 

            } else {
            System.out.println("Error: Document is null!");
        }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        } catch (SAXException ex) {
            System.out.println("SAXException: " + ex.getMessage());
        } catch (ParserConfigurationException ex) {
            System.out.println("ParserConfigurationException: " + ex.getMessage());
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
    
    public boolean checkAnswer(String userAnswers) {
        Question currentQuestion = getTheQuestion();
        List<String> correctAnswers = currentQuestion.getCorrectAnswers(); 
        String[] userAnswersArray = userAnswers.split("");  
        return correctAnswers.containsAll(Arrays.asList(userAnswersArray)) && userAnswersArray.length == correctAnswers.size();
    }


public boolean isLastQuestion() {
    return currentQuestionIndex == questions.size() - 1;
}


}
   
