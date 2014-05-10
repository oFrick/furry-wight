

import javax.swing.JDialog;
import javax.swing.JOptionPane;

public class Seged {
	public static void popup(String uzenet, String cim, MainFrame frame){
		JOptionPane optionpane = new JOptionPane(uzenet, JOptionPane.INFORMATION_MESSAGE);
		JDialog dialog = optionpane.createDialog(frame, cim);
		dialog.setVisible(true);
	}
}
