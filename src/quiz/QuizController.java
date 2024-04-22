/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package quiz;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javax.swing.Timer;
import javax.swing.JOptionPane;

/**
 *
 * @author tayre
 */
class QuizController {
    protected QuizView theView; //reference to GUI
    protected QuizModel theModel; //reference to data model
    private int currentQuestionNum;
    private int score;
    private int questionsSubmitted;
    private Timer quizTimer;
    private long startTime;
    private long elapsedSeconds;
    
    QuizController(QuizView theView, QuizModel theModel) {
        this.theView = theView;
        this.theModel = theModel;
        this.score = 0;
        this.questionsSubmitted = 0;
        
        
        this.theView.addPrevListener(new PrevButtonListener());
        this.theView.addNextListener(new NextButtonListener());
        this.theView.addXmlListener(new ViewXMLQuestionListener());
        this.theView.addSubmitListener(new SubmitButtonListener());
        this.theView.addJsonListener(new ViewJSONQuestionListener());
        this.theView.addQuitListener(new QuitButtonListener());
        this.theView.addViewScoresListener(new ViewScoresButtonListener());

        theModel.refreshResults();
    }
    
    public void startQuizTimer(){
        startTime = System.currentTimeMillis();
        quizTimer = new Timer (1000, (ActionEvent e) -> updateTimer()); //lambda expression used to handle actions triggered by the timer
        quizTimer.start();
    }
    
    private void updateTimer() {
        long elapsedMillis = System.currentTimeMillis() - startTime;
        elapsedSeconds = elapsedMillis / 1000;
        long seconds = elapsedSeconds % 60;
        long minutes = (elapsedSeconds / 60) % 60;
        long hours = elapsedSeconds / 3600;
        String timeString = String.format("%02d:%02d:%02d", hours, minutes, seconds);
        theView.setTimerText(timeString);
    }
    
    public void stopTimer(){
        if(quizTimer != null){
            quizTimer.stop();
        }
    }
    
    public void resetTimer(){
        stopTimer();
        theView.setTimerText("00:00:00");
    }
    
    private void setUpDisplay() {
        try {
            theView.resetRadioButtons(); 
            theView.setBackgroundColor(new Color(205, 180, 219));  //lavender background
            Question q = theModel.getTheQuestion(); 
            if (q.isAnswered()){
                theView.disableAnswerControls();
            }else {
                theView.enableAnswerControls();
            }
            theView.showButtons();
            if (q != null) {
                theView.setQuestion(q.getText());
                theView.setA(q.getA());
                theView.setB(q.getB());
                theView.setC(q.getC());
                theView.setD(q.getD());

            } else { //placeholders
                theView.setA("???");
                theView.setB("???");
                theView.setC("???");
                theView.setD("???");
                theView.setQuestion("???");
            }
            if ("radiobox".equals(q.getType())) {
                theView.showRadioButtons();
            } else if ("checkbox".equals(q.getType())) {
                theView.showCheckBoxes();
            } 

            int currentQuestionNum = theModel.getCurrentQuestionNum();
            int totalQuestions = theModel.getQuizCount();
            theView.updateQuizViewPanel(currentQuestionNum, totalQuestions);
            // Enable or disable prev and next buttons based on contract position
            if (currentQuestionNum == 0) {
                theView.disablePrevButton();
            } else {
                theView.enablePrevButton();
            }
            if (currentQuestionNum == totalQuestions - 1) {
                theView.disableNextButton();
            } else {
                theView.enableNextButton();
            }
        }catch (Error ex) {
            System.out.println(ex);
            theView.displayErrorMessage("Error: There was a problem setting the question." );
        }
    }
    
    private void setUpDisplayJSON() {
        try {
            theView.resetRadioButtons(); // Resets selections
            theView.setBackgroundColor(new Color(123, 223, 242));  
            // fetch and display the JSON question
            Question jsonQ = theModel.getTheQuestion(); 
            if (jsonQ.isAnswered()){
                theView.disableAnswerControls();
            }else {
                theView.enableAnswerControls();
            }
            if (jsonQ != null) {
                theView.setQuestion(jsonQ.getText());
                theView.setA(jsonQ.getA());
                theView.setB(jsonQ.getB());
                theView.setC(jsonQ.getC());
                theView.setD(jsonQ.getD());
                // setting the visibility based on question type
                if ("radiobox".equals(jsonQ.getType())) {
                    theView.showRadioButtons();
                } else if ("checkbox".equals(jsonQ.getType())) {
                    theView.showCheckBoxes();
                }
            } 
            int currentQuestionNum = theModel.getCurrentQuestionNum();
            int totalQuestions = theModel.getQuizCount();
            theView.updateQuizViewPanel(currentQuestionNum, totalQuestions);

            // Enable or disable prev and next buttons based on contract position
            if (currentQuestionNum == 0) {
                theView.disablePrevButton();
            } else {
                theView.enablePrevButton();
            }

            if (currentQuestionNum == totalQuestions - 1) {
                theView.disableNextButton();
            } else {
                theView.enableNextButton();
            }

        } catch (Exception ex) {
            System.err.println("Error in setUpDisplayJSON: " + ex.getMessage());
            theView.displayErrorMessage("Error: There was a problem setting the JSON question.");
        }
    }
 

    class QuitButtonListener extends MouseAdapter {

        @Override
        public void mouseClicked (MouseEvent e) {  
            System.exit(0);
        }  
    }
    
    class PrevButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (theModel.getCurrentQuestionNum() > 0) {
                theModel.prevQuestion();
                // Decide which display setup to use based on the current source type
                if (theModel.getCurrentSourceType() == QuizModel.SourceType.XML) {
                    setUpDisplay();
            } else if (theModel.getCurrentSourceType() == QuizModel.SourceType.JSON) {
                setUpDisplayJSON();
            }
                theView.setFeedback("");
            }
        }
    }
    
    class ViewScoresButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try{    
                    theModel.refreshResults();
                    ViewScores vs = new ViewScores (theView, true, theModel);
                    vs.setLocationRelativeTo(null);
                    vs.setVisible(true);
            }catch (Exception ex) {
                    System.out.println(ex);
                    theView.displayErrorMessage("Error, there is a problem opening view bids.");
            }
        }
    }

    class NextButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (theModel.getCurrentQuestionNum() < theModel.getQuizCount() - 1) {
            theModel.nextQuestion();
            // decide which display setup to use based on the current source type
            if (theModel.getCurrentSourceType() == QuizModel.SourceType.XML) {
                setUpDisplay();
            } else if (theModel.getCurrentSourceType() == QuizModel.SourceType.JSON) {
                setUpDisplayJSON();
            }
                theView.setFeedback("");
            }
        }
    }

   class SubmitButtonListener implements ActionListener {
         
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                Question currentQuestion = theModel.getTheQuestion();
                if(currentQuestion.isAnswered()){
                    theView.displayErrorMessage("This question has already been answered");
                }
                    
                String userAnswers = theView.getCurrentAnswer();
                if (userAnswers.isEmpty()) {
                    theView.displayErrorMessage("Please select an answer.");
                    return;  // Exit the method if no answers are selected
                }
                questionsSubmitted++;
                theView.clearAllSelections();
                boolean isCorrect = theModel.checkAnswer(userAnswers);
                if (isCorrect) {
                    theView.setFeedback("Correct!");
                    if(!(currentQuestion.isAnswered())){
                      score++;
                    }
                    
                } else {
                    String correctAnswers = theModel.getTheQuestion().getCorrectAnswersAsString(); 
                    theView.setFeedback("Incorrect! Correct answers: " + correctAnswers);
                }
                
                currentQuestion.setAnswered(true);
                theView.disableAnswerControls();

                if (questionsSubmitted == 10) {
                    stopTimer();
                    theView.displayMessage("Finished test! You scored " + getScore() + "!");
                    String name = JOptionPane.showInputDialog(theView, "Enter your name to save the score:");
                    theModel.refreshResults();

                    if (name != null && !name.isEmpty()) {
                        saveScore(name);
                    }
                    resetQuiz();
                }
            } catch (Exception ex) {
                System.out.println(ex);
                theView.displayErrorMessage("Error: There is a problem processing your submission.");
            }
        }
    }

    public int getScore() {
        return score;
    }
    
    private void resetQuiz() {
        score = 0;
        questionsSubmitted = 0;
    }

    public void saveScore(String name) {
        String filename = "C:\\Users\\tayre\\Documents\\Quiz\\src\\quiz\\scores.txt";
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDateTime = now.format(formatter); 
        String timerFormatted = String.format("%02d:%02d:%02d",
                                          elapsedSeconds / 3600,
                                          (elapsedSeconds % 3600) / 60,
                                          elapsedSeconds % 60);
        try (PrintWriter out = new PrintWriter(new FileWriter(filename, true))) { // true to append to the file rather than overwrite
            out.println(name + "," + getScore() + "," + formattedDateTime + "," + timerFormatted );
        } catch (IOException ex) {
            System.err.println("Error writing to score file: " + ex.getMessage());
        }
    }
    
    class ViewXMLQuestionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
               resetQuiz();
               theModel.clearQuestions();
               theModel.loadXMLQuiz();
               theView.clearAllSelections();
               resetTimer();
               setUpDisplay();
               startQuizTimer();

            } catch (Exception ex) {
                ex.printStackTrace();
                theView.displayErrorMessage("Error: There was an issue processing your submission.");
            }
        }
    }
    
    class ViewJSONQuestionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                resetQuiz();
                theModel.clearQuestions();
                theModel.loadJsonQuiz();
                theView.clearAllSelections();
                resetTimer();
                setUpDisplayJSON();
                startQuizTimer();
                theView.showButtons();   

            } catch (Exception ex) {
                ex.printStackTrace();
                theView.displayErrorMessage("Error: There was an issue processing your submission.");
            }
        }
    } 
  /*  
    class PrevButtonScoresListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            theScores.previousScore();
        }
            
    }
    
    class NextButtonScoresListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            theScores.previousScore();
        }
    }*/
    
}
  
