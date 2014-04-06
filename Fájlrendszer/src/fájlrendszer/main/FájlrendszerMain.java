package fájlrendszer.main;

import javax.swing.SwingUtilities;
import javax.swing.tree.DefaultMutableTreeNode;

import fájlrendszer.gui.*;

/**
 * A fájlrendszer futtatható osztálya<br/><br/>
 * Ide kellene létrehozni a fájlrendszer-t kezelõ osztályt, amely mezõt valahogy (akár getteren keresztül) el lehessen érni.<br/><br/>
 * Mûködésvázlat:<br/>
 * 
 * A felhasználó a frame objektumon belül kommunikál, azaz ott hív meg egyes metódusokat. Ezek fogják meghívjni a fájlrendszer és fájl metódusait.
 * @author Kiss Dániel
 * @version 0.0.4
 *
 */
public class FájlrendszerMain {
	
	private static MainFrame frame; //Az ablakként funkcionáló frame, grafikus megjelenítés
	private static Fájlrendszer fájlrendszer;

	public static void main(String[] args) {
		
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				frame = new MainFrame("Fájlrendszer v0.0.4", fájlrendszer); //Frame létrehozása
				
				//Teszteleléshez meghívott függvény - Dani
				DaniTeszt();
				

			}
		});

	}
	
	public static void DaniTeszt(){
		
		DefaultMutableTreeNode filmek = new DefaultMutableTreeNode(new Könyvtár("Filmek")); //Filmek könyvtár
			frame.addTreeNode(filmek);
		DefaultMutableTreeNode játékok = new DefaultMutableTreeNode(new Könyvtár("Játékok"));
			frame.addTreeNode(játékok);		
	}

}
