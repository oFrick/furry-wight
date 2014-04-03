package com.cleverabbit.gui;

import javax.swing.SwingUtilities;
import javax.swing.tree.DefaultMutableTreeNode;

/**
 * @author Kiss D�niel
 *
 */
public class F�jlrendszerMain {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				MainFrame frame = new MainFrame("F�jlrendszer v0.0.1"); //Frame l�trehoz�sa
				
				//Tesztel� k�d innent�l!!!!!!
				DefaultMutableTreeNode jegyzetek = new DefaultMutableTreeNode("Jegyzetek"); //mappa vagy f�jl: jegyzetek
				DefaultMutableTreeNode j�t�kok = new DefaultMutableTreeNode("J�t�kok"); //mappa vagy f�jl: j�t�kok
				DefaultMutableTreeNode opr1 = new DefaultMutableTreeNode("opr_Rodek.pdf"); //mappa vagy f�jl: ...Rodek.pdf
				DefaultMutableTreeNode opr2 = new DefaultMutableTreeNode("oper�ci�s rendszerek puska.txt"); //mappa vagy f�jl: puska.txt
				
				frame.addTreeNode(jegyzetek); //adjuk hozz� a jegyzetek-nev� elemet (ami egy�bk�nt mappa lesz) a megjelen�t�si fa rootj�hoz
				frame.addTreeNode(opr1, jegyzetek); //A Rodek f�le seg�dletet a jegyzetek nev� mapp�ba rakjuk
				frame.addTreeNode(opr2, jegyzetek);
				frame.addTreeNode(j�t�kok);

			}
		});

	}

}
