package f�jlrendszer.main;

import javax.swing.SwingUtilities;
import javax.swing.tree.DefaultMutableTreeNode;

import f�jlrendszer.gui.*;
import f�jlrendszer.titkos�t�s.Titkos�t�;
import f�jlrendszer.titkos�t�s.titkos�t�Algoritmus.Eltol�Algoritmus;

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
	private static F�jlrendszer f�jlrendszer;

	public static void main(String[] args) {
		
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				frame = new MainFrame("F�jlrendszer v0.0.4", f�jlrendszer); //Frame l�trehoz�sa
				
				//Tesztelel�shez megh�vott f�ggv�ny - Dani
				DaniTeszt();
				

			}
		});

	}
	
	public static void DaniTeszt(){
		
		DefaultMutableTreeNode filmek = new DefaultMutableTreeNode(new K�nyvt�r("Filmek")); //Filmek k�nyvt�r
			frame.addTreeNode(filmek);
		DefaultMutableTreeNode j�t�kok = new DefaultMutableTreeNode(new K�nyvt�r("J�t�kok"));
			frame.addTreeNode(j�t�kok);		
		
		Eltol�Algoritmus algoritmus = new Eltol�Algoritmus();
		Titkos�t� titkos = new Titkos�t�("al", algoritmus);
		System.out.println("\"sz�veg\" titkos�tva: "+titkos.titkos�t("sz�veg"));
		System.out.println("visszafejtve: "+titkos.visszanyer(titkos.titkos�t("sz�veg")));
		
	}

}
