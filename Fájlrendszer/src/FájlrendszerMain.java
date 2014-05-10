

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.SwingUtilities;
import javax.swing.tree.DefaultMutableTreeNode;

import f�jlrendszer.gui.*;
import f�jlrendszer.titkos�t�s.K�pTitkos�t�;
import f�jlrendszer.titkos�t�s.Titkos�t�;
import f�jlrendszer.titkos�t�s.�zenetKezel�;
import f�jlrendszer.titkos�t�s.titkos�t�Algoritmus.Egyszer�K�pAlgoritmus;
import f�jlrendszer.titkos�t�s.titkos�t�Algoritmus.Eltol�Algoritmus;
import f�jlrendszer.titkos�t�s.titkos�t�Algoritmus.K�pTitkos�t�Algoritmus;

/**
 * A f�jlrendszer futtathat� oszt�lya<br/><br/>
 * Ide kellene l�trehozni a f�jlrendszer-t kezel� oszt�lyt, amely mez�t valahogy (ak�r getteren kereszt�l) el lehessen �rni.<br/><br/>
 * M�k�d�sv�zlat:<br/>
 * 
 * A felhaszn�l� a frame objektumon bel�l kommunik�l, azaz ott h�v meg egyes met�dusokat. Ezek fogj�k megh�vjni a f�jlrendszer �s f�jl met�dusait.
 * @author Kiss D�niel
 * @version 0.0.4
 *
 */
public class F�jlrendszerMain {
	
	private static MainFrame frame; //Az ablakk�nt funkcion�l� frame, grafikus megjelen�t�s

	public static void main(String[] args) {
		
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				frame = new MainFrame("F�jlrendszer v0.0.4"); //Frame l�trehoz�sa
				
				//Tesztelel�shez megh�vott f�ggv�ny - Dani
				DaniTeszt();
				DLLFunctions dll = new DLLFunctions(DLLFunctions.class.getProtectionDomain().getCodeSource().getLocation().getPath()+"filesystem.dll");
				dll.formatDisk(32);
			}
		});

	}
	
	public static void DaniTeszt(){
		
		/*DefaultMutableTreeNode filmek = new DefaultMutableTreeNode(new K�nyvt�r("Filmek")); //Filmek k�nyvt�r
			frame.addTreeNode(filmek);
		DefaultMutableTreeNode j�t�kok = new DefaultMutableTreeNode(new K�nyvt�r("J�t�kok"));
			frame.addTreeNode(j�t�kok);		
		*/
		
		Egyszer�K�pAlgoritmus algoritmus = new Egyszer�K�pAlgoritmus(15);
		
		BufferedImage k�p = null;
		
		try {
			k�p = ImageIO.read(new File("kepem.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(k�p!=null) {
			K�pTitkos�t� titkos = new K�pTitkos�t�(k�p, algoritmus);
			System.out.println("\"szoveg\" titkos�tva: "+titkos.titkos�t("szoveg"));
			System.out.println(titkos.visszanyer(titkos.titkos�t("szoveg")));
		}
		
		//�zenetKezel�.popup�zenet("Ez egy �zenet", "ablakom c�me");
		
	}

}
