package com.cleverabbit.gui;

import javax.swing.JFrame;

/**
 * @author Kiss Dániel
 *
 */
public class MainFrame extends JFrame {
	
	public MainFrame(String név){
		super(név);
		
		//Ablak inicializálása
		
		this.setSize(800, 600); //Ablak mérete
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //Ablakbezárás esemény kezelése
		
		this.setVisible(true); //Láthatóva teszem
	}

}
