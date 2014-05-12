

import java.awt.TextField;

import javax.swing.JDialog;
import javax.swing.JOptionPane;

public class Seged {
	public static void popup(String uzenet, String cim, MainFrame frame){
		JOptionPane optionpane = new JOptionPane(uzenet, JOptionPane.INFORMATION_MESSAGE);
		JDialog dialog = optionpane.createDialog(frame, cim);
		dialog.setVisible(true);
	}
	
	public static String inputPopup(String üzenet, String minta, String cím,  MainFrame frame){
		TextField szövegmezõ = new TextField(minta);
		Object[] válaszLehetõség = {üzenet, szövegmezõ}; 
		Object[] válaszGomb = {"OK", "Mégsem"};
		
		JOptionPane optionpane = new JOptionPane(válaszLehetõség, JOptionPane.QUESTION_MESSAGE, JOptionPane.YES_NO_OPTION, null, válaszGomb, válaszGomb[0]);
		JDialog dialog = optionpane.createDialog(frame, cím);
		dialog.setVisible(true);
		
		return szövegmezõ.getText();
		
	}
}
