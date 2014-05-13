

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.imageio.ImageIO;
import javax.swing.SwingUtilities;
import javax.swing.tree.DefaultMutableTreeNode;

import fájlrendszer.gui.*;
import fájlrendszer.titkosítás.KépTitkosító;
import fájlrendszer.titkosítás.Titkosító;
import fájlrendszer.titkosítás.ÜzenetKezelõ;
import fájlrendszer.titkosítás.titkosítóAlgoritmus.EgyszerûKépAlgoritmus;
import fájlrendszer.titkosítás.titkosítóAlgoritmus.EltolóAlgoritmus;
import fájlrendszer.titkosítás.titkosítóAlgoritmus.KépTitkosítóAlgoritmus;

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
				
				frame = new MainFrame("Fájlrendszer v1.1.1"); //Frame létrehozása
				
			}
		});

	}

}
