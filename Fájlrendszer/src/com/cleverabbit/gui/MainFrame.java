package com.cleverabbit.gui;

import java.awt.FlowLayout;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;

/**
 * Az alkalmazás ablaka
 * @author Kiss Dániel
 *
 */
public class MainFrame extends JFrame {
	
	private JMenuBar menüsor;
	private JMenu fájlrendszerMenü;
	private JMenuItem újFáljrendszer;
	private JMenuItem betöltFájlrendszer;
	private JMenuItem mentFájlrendszer;
	private JMenuItem kilépés;
	
	private JPanel panel;
	
	private JTree explorer;
	
	/**
	 * Az ablakot megvalósító osztály. Tartalmazza az egyes menüket, azok elemeit, illetve az ezekhez tartozó eseménykezelõket.
	 * @author Kiss Dániel
	 * @param név - String, az ablak neve
	 */
	public MainFrame(String név){
		super(név);
		
		//Ablak inicializálása
		
		this.setSize(800, 600); //Ablak mérete
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //Ablakbezárás esemény kezelése
		
		loadMenus();
		loadContent();
		
		this.setVisible(true); //Láthatóva teszem
		
		//Eseménykezelõk
		kilépés.addActionListener(new ActionListener() { //Kilépés
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
				
			}
		});
	}
	
	private void loadContent(){
		FlowLayout layout = new FlowLayout(FlowLayout.CENTER);
		panel = new JPanel(layout);
		setContentPane(panel);
		
		DefaultMutableTreeNode top = new DefaultMutableTreeNode("Root könyvtár");
		explorer = new JTree(top);
		JScrollPane treeView = new JScrollPane(explorer);
		
		//panel.add(explorer);
		panel.add(treeView);
		
	}
	
	/**
	 * Az ablak menüinek létrehozása és betöltése
	 * @author Kiss Dániel
	 */
	private void loadMenus(){
		//Példányosítás
		menüsor = new JMenuBar();
		fájlrendszerMenü = new JMenu("Fájlrendszer");
		újFáljrendszer = new JMenuItem("Új");
		betöltFájlrendszer = new JMenuItem("Betölt");
		kilépés = new JMenuItem("Kilépés");
		
		//Objektumok "elhejezése"
		this.setJMenuBar(menüsor);
		menüsor.add(fájlrendszerMenü);
		fájlrendszerMenü.add(újFáljrendszer);
		fájlrendszerMenü.add(betöltFájlrendszer);
		fájlrendszerMenü.add(kilépés);
	}

}
