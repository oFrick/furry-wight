package com.cleverabbit.gui;

import javax.swing.SwingUtilities;
import javax.swing.tree.DefaultMutableTreeNode;

/**
 * @author Kiss Dániel
 *
 */
public class FájlrendszerMain {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				MainFrame frame = new MainFrame("Fájlrendszer v0.0.1"); //Frame létrehozása
				
				//Tesztelõ kód innentõl!!!!!!
				DefaultMutableTreeNode jegyzetek = new DefaultMutableTreeNode("Jegyzetek"); //mappa vagy fájl: jegyzetek
				DefaultMutableTreeNode játékok = new DefaultMutableTreeNode("Játékok"); //mappa vagy fájl: játékok
				DefaultMutableTreeNode opr1 = new DefaultMutableTreeNode("opr_Rodek.pdf"); //mappa vagy fájl: ...Rodek.pdf
				DefaultMutableTreeNode opr2 = new DefaultMutableTreeNode("operációs rendszerek puska.txt"); //mappa vagy fájl: puska.txt
				
				frame.addTreeNode(jegyzetek); //adjuk hozzá a jegyzetek-nevû elemet (ami egyébként mappa lesz) a megjelenítési fa rootjához
				frame.addTreeNode(opr1, jegyzetek); //A Rodek féle segédletet a jegyzetek nevû mappába rakjuk
				frame.addTreeNode(opr2, jegyzetek);
				frame.addTreeNode(játékok);

			}
		});

	}

}
