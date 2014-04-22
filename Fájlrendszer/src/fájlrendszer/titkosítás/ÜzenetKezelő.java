package f�jlrendszer.titkos�t�s;

import javax.swing.JDialog;
import javax.swing.JOptionPane;

/**
 * Feladata a GUI-n az egyes modulok �ltal produk�lt felhaszn�l� sz�m�ra fontos �zenetek megjelen�t�se
 * @author Kiss D�niel
 *
 */
public class �zenetKezel� {
	
	/**
	 * Egy felugr� ablakot nyit meg a param�terk�nt �tadott ablak c�m�vel �s az �zenet tartalm�val. A egy OK-gombra kattinthat, ez�ltal
	 * az ablak bez�r�di.
	 * 
	 * @param �zenet String
	 * @param ablakC�m String
	 */
	public static void popup�zenet(String �zenet, String ablakC�m){
		JOptionPane optionpane = new JOptionPane(�zenet, JOptionPane.INFORMATION_MESSAGE);
		JDialog dialog = optionpane.createDialog(ablakC�m);
		dialog.setVisible(true);
	}
	
	/**Egy param�terben feltett sz�veges k�rd�sre a felhaszn�l� felugr� ablakban igen/nem-el v�laszolhat.
	 * Ennek megfelel�en a visszat�r�si �rt�k logikai (boolean)
	 * 
	 * @param k�rd�s String
	 * @return boolean
	 */
	public static boolean popupIgenNem(String k�rd�s){
		return false;
	}

}
