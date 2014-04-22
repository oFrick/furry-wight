package fájlrendszer.titkosítás;

import javax.swing.JDialog;
import javax.swing.JOptionPane;

/**
 * Feladata a GUI-n az egyes modulok által produkált felhasználó számára fontos üzenetek megjelenítése
 * @author Kiss Dániel
 *
 */
public class ÜzenetKezelõ {
	
	/**
	 * Egy felugró ablakot nyit meg a paraméterként átadott ablak címével és az üzenet tartalmával. A egy OK-gombra kattinthat, ezáltal
	 * az ablak bezáródi.
	 * 
	 * @param üzenet String
	 * @param ablakCím String
	 */
	public static void popupÜzenet(String üzenet, String ablakCím){
		JOptionPane optionpane = new JOptionPane(üzenet, JOptionPane.INFORMATION_MESSAGE);
		JDialog dialog = optionpane.createDialog(ablakCím);
		dialog.setVisible(true);
	}
	
	/**Egy paraméterben feltett szöveges kérdésre a felhasználó felugró ablakban igen/nem-el válaszolhat.
	 * Ennek megfelelõen a visszatérési érték logikai (boolean)
	 * 
	 * @param kérdés String
	 * @return boolean
	 */
	public static boolean popupIgenNem(String kérdés){
		return false;
	}

}
