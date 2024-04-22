/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package quiz;

import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;

/**
 *
 * @author Tayla Rechichi
 */
public class Quiz {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws ParserConfigurationException, SAXException {
        QuizModel theModel = new QuizModel();
        QuizView theView = new QuizView(theModel);
        QuizController theController;
        theController = new QuizController(theView, theModel);
        theView.setVisible(true);  
    }
    
}
