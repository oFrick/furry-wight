package com.cleverabbit.gui;

import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.SwingUtilities;
import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeModelListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeSelectionModel;

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
	private DefaultMutableTreeNode rootElement;
	private DefaultTreeModel treemodel;
	private JButton törlésGomb;
	private JButton létrehozGomb;
	private JButton másolásGomb;
	private JButton áthelyezésGomb;
	
	private GridBagConstraints constraint; //Elhelyezési "kényszer"
	
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
		
		this.setVisible(true); //Láthatóvá teszem
		
		//Eseménykezelõk
		kilépés.addActionListener(new ActionListener() { //Kilépés
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
				
			}
		});
	}
	
	/**
	 * Elhelyez egy - a grafikus megjelenítéshez használt - node-ot (ami lehet könyvtár vagy mappa) a fa struktúrában
	 * @author Kiss Dániel
	 * @param what - {@link DefaultMutableTreeNode}, amit be szeretnénk szúrni/elhelyezni
	 * @param at - {@link DefaultMutableTreeNode}, ahová el szeretnénk helyezni
	 */
	public void addTreeNode(DefaultMutableTreeNode what, DefaultMutableTreeNode at){
		//at.add(what); ne ezt használjuk!
		treemodel.insertNodeInto(what, at, at.getChildCount());
	}
	
	/**
	 * Elhelyez egy - a grafikus megjelenítéshez használt - node-ot a fa struktúra gyökerében
	 * @author Kiss Dániel
	 * @param what - {@link DefaultMutableTreeNode}, amit el szeretnénk helyezni
	 */
	public void addTreeNode(DefaultMutableTreeNode what){
		addTreeNode(what, rootElement);
		
	}
	
	/**
	 * Törli a fából a grafikus megjelenítéshez használt node-ot
	 * @author Kiss Dániel
	 * @param what - {@link DefaultMutableTreeNode}, amit törölni szeretnénk
	 */
	public void removeTreeNode(DefaultMutableTreeNode what){
		what.removeFromParent();
	}
	
	private void loadContent(){
		GridBagLayout layout = new GridBagLayout();
		constraint = new GridBagConstraints();
		constraint.fill = GridBagConstraints.HORIZONTAL; // Maximális szélesség, szükséges magasság
		panel = new JPanel(layout);
		setContentPane(panel);
		
		rootElement = new DefaultMutableTreeNode("Root könyvtár");
		explorer = new JTree(rootElement);
		JScrollPane treeView = new JScrollPane(explorer);
		explorer.setVisibleRowCount(15);
		
		//Fa eseménykezelõjének beállítása
		treemodel = new DefaultTreeModel(rootElement);
		treemodel.addTreeModelListener((new TreeModelListener() {
			
			@Override
			public void treeStructureChanged(TreeModelEvent e) {
				System.out.println("Fa struktúra változás!");
				
			}
			
			@Override
			public void treeNodesRemoved(TreeModelEvent e) {
				
			}
			
			@Override
			public void treeNodesInserted(TreeModelEvent e) {
				SwingUtilities.updateComponentTreeUI(explorer);
				System.out.println("Hozzáadva!");
			}
			
			@Override
			public void treeNodesChanged(TreeModelEvent e) {
				
			}
		}));
		explorer.setEditable(true);
		
		//Kiválasztásos eseménykezelés beállítása
		explorer.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);	
		explorer.addTreeSelectionListener(new TreeSelectionListener() {
			
			@Override
			public void valueChanged(TreeSelectionEvent arg0) {
				DefaultMutableTreeNode selected = (DefaultMutableTreeNode) explorer.getLastSelectedPathComponent(); //A kiválasztott TreeNode
				
				if(selected == null) return; //Ha nincs kiválasztva semmi, akkor kilépünk a függvénybõl
				
				//TODO Kiválaszott TreeNode kezelése: fájl adatainak megjelenítése, aktív(kiválasztott) TreeNode beállítása=> osztály privát adattagban
				
			}
		});
		
		
		constraint.gridwidth = 4; //Három oszlop széles
		constraint.gridx = 0; //0. sor
		constraint.gridy = 0; //0. oszlopában van
		constraint.weightx = 1;
		constraint.weighty = 1;
		panel.add(treeView, constraint);
		
		létrehozGomb = new JButton("Új elem");
		constraint.gridwidth = 1;
		constraint.weightx = 1;
		constraint.weighty = 0.5;
		constraint.gridx = 0;
		constraint.gridy = 1;
		panel.add(létrehozGomb, constraint);
		
		törlésGomb = new JButton("Törlés");
		constraint.gridx = 1;
		constraint.gridy = 1;
		panel.add(törlésGomb, constraint);
		
		másolásGomb = new JButton("Másolás");
		constraint.gridx = 2;
		constraint.gridy = 1;
		panel.add(másolásGomb, constraint);
		
		áthelyezésGomb = new JButton("Áthelyezés");
		constraint.gridx = 3;
		constraint.gridy = 1;
		panel.add(áthelyezésGomb, constraint);
		
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
		mentFájlrendszer = new JMenuItem("Mentés");
		kilépés = new JMenuItem("Kilépés");
		
		//Objektumok "elhejezése"
		this.setJMenuBar(menüsor);
		menüsor.add(fájlrendszerMenü);
		fájlrendszerMenü.add(újFáljrendszer);
		fájlrendszerMenü.add(betöltFájlrendszer);
		fájlrendszerMenü.add(mentFájlrendszer);
		fájlrendszerMenü.add(kilépés);
	}

}
