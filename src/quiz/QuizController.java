/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package quiz;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
    
    QuizController(QuizView theView, QuizModel theModel) {
        this.theView = theView;
        this.theModel = theModel;
        
        this.theView.addPrevListener(new PrevButtonListener());
        this.theView.addNextListener(new NextButtonListener());
        this.theView.addXmlListener(new ViewXMLQuestionListener());
        this.theView.addSubmitListener(new SubmitButtonListener());
        
        //theModel.refreshResults();
    }
    
    private void setUpDisplay() {
    try {
        Question q = theModel.getTheQuestion(); 
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
            }
        }
    }

    class NextButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (theModel.getCurrentQuestionNum() < theModel.getQuizCount() - 1) {
                theModel.nextQuestion();
                setUpDisplay(); 
            }
        }
    }


    
   class SubmitButtonListener implements ActionListener {
         
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                String userAnswer = theView.getCurrentAnswer();
                if (userAnswer.isEmpty()) {
                    theView.setFeedback("Please select an answer.");
                    return; // stop further processing if no answer is selected
                }

                boolean isCorrect = theModel.checkAnswer(userAnswer);
                if (isCorrect) {
                    theView.setFeedback("Correct!");
                } else {
                    String correctAnswers = theModel.getTheQuestion().getCorrectAnswersAsString();
                    theView.setFeedback("Incorrect! Correct answer(s): " + correctAnswers);
                }

                if (theModel.isLastQuestion()) {
                    int totalScore = theModel.getScore();
                    theView.displayMessage("Finished test! You scored " + totalScore + "!");
                    String name = JOptionPane.showInputDialog(theView, "Enter your name to save the score:");
                    if (name != null && !name.isEmpty()) {
                        theModel.saveScore(name);
                    }
                  
                } 
            } catch (Exception ex) {
                System.out.println(ex);
                theView.displayErrorMessage("Error: There is a problem processing your submission.");
            }
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
