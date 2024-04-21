/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package quiz;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
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
    
    QuizController(QuizView theView, QuizModel theModel) {
        this.theView = theView;
        this.theModel = theModel;
        this.score = 0;
        
        this.theView.addPrevListener(new PrevButtonListener());
        this.theView.addNextListener(new NextButtonListener());
        this.theView.addXmlListener(new ViewXMLQuestionListener());
        this.theView.addSubmitListener(new SubmitButtonListener());
        
        //theModel.refreshResults();
    }
    
    private void setUpDisplay() {
    try {
        theView.resetRadioButtons(); 
        theView.setBackgroundColor(new Color(205, 180, 219));  //lavender background
        Question q = theModel.getTheQuestion(); 
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
            } else {
            // if no question or invalid data, hide all options
            theView.hideAllOptions();
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
    
        class PrevButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (theModel.getCurrentQuestionNum() > 0) {
                theModel.prevQuestion();
                setUpDisplay(); 
                theView.setFeedback("");
            }
        }
    }

    class NextButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (theModel.getCurrentQuestionNum() < theModel.getQuizCount() - 1) {
                theModel.nextQuestion();
                setUpDisplay(); 
                theView.setFeedback("");
            }
        }
    }

   class SubmitButtonListener implements ActionListener {
         
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                String userAnswers = theView.getCurrentAnswer();
                if (userAnswers.isEmpty()) {
                    theView.displayErrorMessage("Please select an answer.");
                    return;  // Exit the method if no answers are selected
                }

                boolean isCorrect = theModel.checkAnswer(userAnswers);
                if (isCorrect) {
                    theView.setFeedback("Correct!");
                    score++;
                } else {
                    String correctAnswers = theModel.getTheQuestion().getCorrectAnswersAsString(); 
                    theView.setFeedback("Incorrect! Correct answers: " + correctAnswers);
                }

                if (theModel.isLastQuestion()) {
                    theView.displayMessage("Finished test! You scored " + getScore() + "!");
                    String name = JOptionPane.showInputDialog(theView, "Enter your name to save the score:");

                    if (name != null && !name.isEmpty()) {
                        saveScore(name);
                    }
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

        public void saveScore(String name) {
            String filename = "C:\\Users\\tayre\\Documents\\Quiz\\src\\quiz\\scores.txt";
            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String formattedDateTime = now.format(formatter); 
            try (PrintWriter out = new PrintWriter(new FileWriter(filename, true))) { // true to append to the file rather than overwrite
                out.println(name + ": " + getScore() + " - completed on " + formattedDateTime);
            } catch (IOException ex) {
                System.err.println("Error writing to score file: " + ex.getMessage());
            }
        }
    
    class ViewXMLQuestionListener implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        try {
           setUpDisplay();
        
        } catch (Exception ex) {
            ex.printStackTrace();
            theView.displayErrorMessage("Error: There was an issue processing your submission.");
        }
    }

    /*private void showCompletionPopup() {
        String name = JOptionPane.showInputDialog(theView, "You got: " + theModel.getScore() + "! Enter your name to save score:");
        if (name != null && !name.isEmpty()) {
            saveScore(name, theModel.getScore());
        }
    }*/
    }
}
