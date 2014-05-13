

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

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
 *
 */
public class F�jlrendszerMain {
	
	private static MainFrame frame; //Az ablakk�nt funkcion�l� frame, grafikus megjelen�t�s

	public static void main(String[] args) {
		
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				
				frame = new MainFrame("F�jlrendszer v1.1.1"); //Frame l�trehoz�sa
				
			}
		});

	}

}
