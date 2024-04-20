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

    
    QuizController(QuizView1 theView, QuizModel1 theModel) {
        this.theView = theView;
        this.theModel = theModel;
        
        this.theView.addPrevListener(new PrevButtonListener());
        this.theView.addNextListener(new NextButtonListener());
        this.theView.addXMLQuestionListener(new ViewXMLQuestionListener());
        this.theView.addSubmitListener(new SubmitButtonListener());
        
        //theModel.refreshResults();
    }
    
    class PrevButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (theModel.getCurrentQuestionNum() > 0) {
                theModel.prevQuestion();
                theView.displayQuestion(theModel.getTheQuestion());
                setUpDisplay();
            }
        }
    }

    
    class NextButtonListener implements ActionListener {
         
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                theModel.nextQuestion();
                theView.displayQuestion(theModel.getTheQuestion());
            } catch (Exception ex) {
                System.out.println(ex);
                theView.displayErrorMessage("Error: There is a problem setting the next question.");            
            }
            setUpDisplay();
        }
    }
    
    class NextButtonListener implements ActionListener {
         
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                theModel.nextQuestion();
                theView.displayQuestion(theModel.getTheQuestion());
            } catch (Exception ex) {
                System.out.println(ex);
                theView.displayErrorMessage("Error: There is a problem setting the next question.");            
            }
            setUpDisplay();
        }
    }
    
    class ViewXMLQuestionListener implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            List<String> selectedAnswers = theView.getSelectedAnswers();
            Question currentQuestion = theModel.getTheQuestion();
            if (selectedAnswers.isEmpty()) {
                theView.setFeedback("Please select an answer.");
                return;
            }

            if (selectedAnswers.contains(currentQuestion.getCorrectAnswers()) && selectedAnswers.size() == 1) {
                theView.setFeedback("Correct!");
                theModel.incrementScore();  // Increment score for correct answer
            } else {
                theView.setFeedback("Incorrect! The correct answer is: " + currentQuestion.getCorrectAnswer());
            }

            // Check if it was the last question
            if (theModel.getCurrentQuestionNum() == theModel.getQuizCount() - 1) {
                showCompletionPopup();  
            } 
        } catch (Exception ex) {
            ex.printStackTrace();
            theView.displayErrorMessage("Error: There was an issue processing your submission.");
        }
    }

    private void showCompletionPopup() {
        String name = JOptionPane.showInputDialog(theView, "You got: " + theModel.getScore() + "! Enter your name to save score:");
        if (name != null && !name.isEmpty()) {
            saveScore(name, theModel.getScore());
        }
    }
    }
}
