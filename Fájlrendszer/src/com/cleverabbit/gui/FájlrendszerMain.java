package com.cleverabbit.gui;

import javax.swing.SwingUtilities;

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
				MainFrame fram = new MainFrame("F�jlrendszer v0.0.1"); //Frame l�trehoz�sa
			}
		});

	}

}
