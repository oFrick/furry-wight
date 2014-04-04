package f�jlrendszer.main;

import javax.swing.SwingUtilities;
import javax.swing.tree.DefaultMutableTreeNode;

import f�jlrendszer.gui.*;

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
				// TODO Auto-generated method stub
				frame = new MainFrame("F�jlrendszer v0.0.4"); //Frame l�trehoz�sa
				
				//Tesztelel�shez megh�vott f�ggv�ny - Dani
				DaniTeszt();
				

			}
		});

	}
	
	public static void DaniTeszt(){
		DefaultMutableTreeNode jegyzetek = new DefaultMutableTreeNode("Jegyzetek"); //mappa vagy f�jl: jegyzetek
		DefaultMutableTreeNode j�t�kok = new DefaultMutableTreeNode("J�t�kok"); //mappa vagy f�jl: j�t�kok
		DefaultMutableTreeNode opr1 = new DefaultMutableTreeNode("opr_Rodek.pdf"); //mappa vagy f�jl: ...Rodek.pdf
		DefaultMutableTreeNode opr2 = new DefaultMutableTreeNode("oper�ci�s rendszerek puska.txt"); //mappa vagy f�jl: puska.txt
		
		frame.addTreeNode(jegyzetek); //adjuk hozz� a jegyzetek-nev� elemet (ami egy�bk�nt mappa lesz) a megjelen�t�si fa rootj�hoz
		frame.addTreeNode(opr1, jegyzetek); //A Rodek f�le seg�dletet a jegyzetek nev� mapp�ba rakjuk
		frame.addTreeNode(opr2, jegyzetek);
		frame.addTreeNode(j�t�kok);
	}

}
