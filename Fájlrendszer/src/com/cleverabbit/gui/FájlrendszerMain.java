package com.cleverabbit.gui;

import javax.swing.SwingUtilities;

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
				MainFrame fram = new MainFrame("Fájlrendszer v0.0.1"); //Frame létrehozása
			}
		});

	}

}
