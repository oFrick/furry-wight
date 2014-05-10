

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

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
 * @version 0.0.4
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
				DLLFunctions dll = new DLLFunctions(DLLFunctions.class.getProtectionDomain().getCodeSource().getLocation().getPath()+"filesystem.dll");
				dll.formatDisk(32);
			}
		});

	}
	
	public static void DaniTeszt(){
		
		/*DefaultMutableTreeNode filmek = new DefaultMutableTreeNode(new Könyvtár("Filmek")); //Filmek könyvtár
			frame.addTreeNode(filmek);
		DefaultMutableTreeNode játékok = new DefaultMutableTreeNode(new Könyvtár("Játékok"));
			frame.addTreeNode(játékok);		
		*/
		
		EgyszerûKépAlgoritmus algoritmus = new EgyszerûKépAlgoritmus(15);
		
		BufferedImage kép = null;
		
		try {
			kép = ImageIO.read(new File("kepem.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(kép!=null) {
			KépTitkosító titkos = new KépTitkosító(kép, algoritmus);
			System.out.println("\"szoveg\" titkosítva: "+titkos.titkosít("szoveg"));
			System.out.println(titkos.visszanyer(titkos.titkosít("szoveg")));
		}
		
		//ÜzenetKezelõ.popupÜzenet("Ez egy üzenet", "ablakom címe");
		
	}

}
