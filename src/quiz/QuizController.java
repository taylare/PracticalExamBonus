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
    
    //get the checkboxes/radio boxes to hide 
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
        
        // Check question type and adjust UI components visibility
            if ("radiobox".equals(q.getType())) {
                theView.showRadioButtons();
            } else if ("checkbox".equals(q.getType())) {
                theView.showCheckBoxes();
            } else {
            // If no question or invalid data, hide all options
            theView.hideAllOptions();
        }

    
        int currentQuestiontNum = theModel.getCurrentQuestionNum();
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
             
            if (theModel.getCurrentQuestionNum() == 0) {
                 return;
            }
            try {
                theModel.prevQuestion();
                
            } catch (Exception ex) {
                System.out.println(ex);
                theView.displayErrorMessage("Error: There is a problem setting a previous contract.");            
            }
            setUpDisplay(); //updates GUI with details of current contract
         }
     }

    
    class SubmitButtonListener implements ActionListener {
         
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
               /* theModel.nextQuestion();
                theView.displayQuestion(theModel.getTheQuestion());*/
               System.out.print("Submitted!");
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
            } catch (Exception ex) {
                System.out.println(ex);
                theView.displayErrorMessage("Error: There is a problem setting the next contract.");            
            }
            setUpDisplay();
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
