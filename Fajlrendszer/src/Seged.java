

import java.awt.TextField;

import javax.swing.JDialog;
import javax.swing.JOptionPane;

public class Seged {
	public static void popup(String uzenet, String cim, MainFrame frame){
		JOptionPane optionpane = new JOptionPane(uzenet, JOptionPane.INFORMATION_MESSAGE);
		JDialog dialog = optionpane.createDialog(frame, cim);
		dialog.setVisible(true);
	}
	
	public static String inputPopup(String �zenet, String minta, String c�m,  MainFrame frame){
		TextField sz�vegmez� = new TextField(minta);
		Object[] v�laszLehet�s�g = {�zenet, sz�vegmez�}; 
		Object[] v�laszGomb = {"OK", "M�gsem"};
		
		JOptionPane optionpane = new JOptionPane(v�laszLehet�s�g, JOptionPane.QUESTION_MESSAGE, JOptionPane.YES_NO_OPTION, null, v�laszGomb, v�laszGomb[0]);
		JDialog dialog = optionpane.createDialog(frame, c�m);
		dialog.setVisible(true);
		
		return sz�vegmez�.getText();
		
	}
}
