package com.cleverabbit.gui;

import javax.swing.JFrame;

/**
 * @author Kiss D�niel
 *
 */
public class MainFrame extends JFrame {
	
	public MainFrame(String n�v){
		super(n�v);
		
		//Ablak inicializ�l�sa
		
		this.setSize(800, 600); //Ablak m�rete
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //Ablakbez�r�s esem�ny kezel�se
		
		this.setVisible(true); //L�that�va teszem
	}

}
