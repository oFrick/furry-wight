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
 *
 */
public class FájlrendszerMain {
	
	private static MainFrame frame; //Az ablakként funkcionáló frame, grafikus megjelenítés

	public static void main(String[] args) {
		
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				frame = new MainFrame("Fájlrendszer v0.0.4"); //Frame létrehozása
				
				//Teszteleléshez meghívott függvény - Dani
				DaniTeszt();
				

			}
		});

	}
	
	public static void DaniTeszt(){
		DefaultMutableTreeNode jegyzetek = new DefaultMutableTreeNode("Jegyzetek"); //mappa vagy fájl: jegyzetek
		DefaultMutableTreeNode játékok = new DefaultMutableTreeNode("Játékok"); //mappa vagy fájl: játékok
		DefaultMutableTreeNode opr1 = new DefaultMutableTreeNode("opr_Rodek.pdf"); //mappa vagy fájl: ...Rodek.pdf
		DefaultMutableTreeNode opr2 = new DefaultMutableTreeNode("operációs rendszerek puska.txt"); //mappa vagy fájl: puska.txt
		
		frame.addTreeNode(jegyzetek); //adjuk hozzá a jegyzetek-nevû elemet (ami egyébként mappa lesz) a megjelenítési fa rootjához
		frame.addTreeNode(opr1, jegyzetek); //A Rodek féle segédletet a jegyzetek nevû mappába rakjuk
		frame.addTreeNode(opr2, jegyzetek);
		frame.addTreeNode(játékok);
	}

}
