/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package quiz;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * @author Tayla Rechichi
 */
 public class QuizModel {
     
    public enum SourceType { //will be used to differenciate between for the next, prev, submit buttons
        XML, JSON
    }
    private SourceType currentSourceType;
    private ArrayList<Question> questions;
    private List<Scores> theResults;
    private int currentScoreIndex = 0;
    private int currentQuestionIndex;
  
    public QuizModel() throws ParserConfigurationException, SAXException {
       questions = new ArrayList<>();
       currentQuestionIndex = 0;
       theResults = new ArrayList<>();
       
       try {
            String fileName = "C:\\Users\\tayre\\Documents\\Quiz\\src\\quiz\\scores.txt";
            try (FileReader fileReader = new FileReader(fileName)) {
                BufferedReader bufferedReader = new BufferedReader(fileReader);
                
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    String[] tokens = line.split(",", Scores.NUMBER_OF_SCORES_ATTRIBUTES);
                    
                    String name = tokens[Scores.INDEX_OF_NAME];
                    String score = tokens[Scores.INDEX_OF_SCORE];
                    String date = tokens[Scores.INDEX_OF_DATE];
                    String timer = tokens[Scores.INDEX_OF_TIMER];
                    
                    Scores dataScores = new Scores(name,
                            score,
                            date,
                            timer);
                    theResults.add(dataScores);
                }
            }
 
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
       }
    }
    
    public void refreshResults(){
        theResults.clear();
        try{
            String fileName = "C:\\Users\\tayre\\Documents\\Quiz\\src\\quiz\\scores.txt";
            try (FileReader fileReader = new FileReader(fileName)) {
                BufferedReader bufferedReader = new BufferedReader(fileReader);
                
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    String[] tokens = line.split(",", Scores.NUMBER_OF_SCORES_ATTRIBUTES);
                    
                    String name = tokens[Scores.INDEX_OF_NAME];
                    String score = tokens[Scores.INDEX_OF_SCORE];
                    String date = tokens[Scores.INDEX_OF_DATE];
                    String timer = tokens[Scores.INDEX_OF_TIMER];
                    
                    Scores dataScores = new Scores(name,
                            score,
                            date,
                            timer);
                    theResults.add(dataScores);
                }
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }    
    }
   
    public void loadXMLQuiz() throws ParserConfigurationException, SAXException, IOException{
        File xmlFile = new File(System.getProperty("user.dir") + "\\src\\quiz\\quiz.xml");
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(xmlFile);
        setCurrentSourceType(SourceType.XML);
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
    }
   
   public void loadJsonQuiz() throws IOException, ParseException, org.json.simple.parser.ParseException {
        JSONParser parser = new JSONParser();
        File jsonFile = new File("C:\\Users\\tayre\\Documents\\Quiz\\src\\quiz\\Question.json");
        FileReader reader = new FileReader(jsonFile);
        JSONObject jsonObject = (JSONObject) parser.parse(reader);
        setCurrentSourceType(SourceType.JSON);
        
        JSONArray quizArray = (JSONArray) jsonObject.get("Quiz"); // Extract the quiz array
        if (quizArray == null){
            System.out.println("No quiz found");
        }

        for (Object o : quizArray) {
            JSONObject qObj = (JSONObject) o;

            String text = (String) qObj.get("q");
            String type = (String) qObj.get("type");
            JSONObject options = (JSONObject) qObj.get("Options");

            // Extract options and their correctness
            JSONObject optionA = (JSONObject) options.get("a");
            String aText = (String) optionA.get("q"); 
            boolean aCorrect = optionA.containsKey("correct") && (boolean) optionA.get("correct");

            JSONObject optionB = (JSONObject) options.get("b");
            String bText = (String) optionB.get("q"); 
            boolean bCorrect = optionB.containsKey("correct") && (boolean) optionB.get("correct");

            JSONObject optionC = (JSONObject) options.get("c");
            String cText = (String) optionC.get("q"); 
            boolean cCorrect = optionC.containsKey("correct") && (boolean) optionC.get("correct");

            JSONObject optionD = (JSONObject) options.get("d");
            String dText = (String) optionD.get("q"); 
            boolean dCorrect = optionD.containsKey("correct") && (boolean) optionD.get("correct");

            Question jsonQuestion = new Question(
                text, type, aText, bText, cText, dText, aCorrect, bCorrect, cCorrect, dCorrect
            );
            questions.add(jsonQuestion);
        }
        if (questions.isEmpty()) {
            System.out.println("No questions loaded into jsonQuestions list");
        } else {
            System.out.println("Loaded " + questions.size() + " JSON questions");
        }
    }
   
    public void setCurrentSourceType(SourceType type) {
        this.currentSourceType = type;
    }

    public SourceType getCurrentSourceType() {
        return this.currentSourceType;
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
    
    public Question getTheQuestion() {
        if (currentQuestionIndex >= 0 && currentQuestionIndex < questions.size()) {
            return questions.get(currentQuestionIndex);
        } else {
            throw new IllegalStateException("No questions are available to display.");
        }
    }

    public void nextQuestion() {
        if (currentQuestionIndex < questions.size() - 1) {
            currentQuestionIndex++;
        }
    }

    public void prevQuestion() {
        if (currentQuestionIndex > 0) {
            currentQuestionIndex--;
        }
    }
    
    public void nextScore() {
        if (currentScoreIndex < theResults.size() - 1) {
            currentScoreIndex++;
        }
    }

    public void previousScore() {
        if (currentScoreIndex > 0) {
            currentScoreIndex--;

        }
    }
    
    public boolean checkAnswer(String userAnswers) {
        Question currentQuestion = getTheQuestion();
        List<String> correctAnswers = currentQuestion.getCorrectAnswers(); 
        String[] userAnswersArray = userAnswers.split("");  
        return correctAnswers.containsAll(Arrays.asList(userAnswersArray)) && userAnswersArray.length == correctAnswers.size();
    }
    
   
    public void clearQuestions() {
        questions.clear();
        currentQuestionIndex = 0;
    }
    
    
    public List<Scores> getAllScores() {
        return theResults;
    }

    // Add scores for testing or from file reading
    public void addScoreRecord(Scores score) {
        theResults.add(score);
    }
    
    public boolean foundScores(){
        return !theResults.isEmpty();
    }
    
    public Scores getTheScore() {
        return theResults.get(currentScoreIndex);
    }
    
    public int getCurrentScoreNum(){
        return currentScoreIndex;
    }
    
    public int getScoreCount() {
        return theResults.size();
    }

 }
