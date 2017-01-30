/**
 *
 * @author Wood
 */

import javax.swing.JFrame;

public class ContactInfoInput {
    public static void main(String[] args) {
       ContactInputForm mainFrame = new ContactInputForm(); 
       mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       mainFrame.setSize( 400, 400 );//set frame size
       mainFrame.setResizable(false);//keeps the user from resizing the window
       mainFrame.setVisible ( true );//display frame       
    }//end main    
}//end class
