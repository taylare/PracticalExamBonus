/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package quiz;

/**
 *
 * @author tayre
 */
public class Quiz {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        QuizModel theModel = new QuizModel();
        QuizView theView = new QuizView(null, true, theModel);
        QuizController theController;
        theController = new QuizController(theView, theModel);
        theView.setVisible(true);  
    }
    
}
