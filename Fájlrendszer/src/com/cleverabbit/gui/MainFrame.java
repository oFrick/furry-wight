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
import javax.swing.SwingUtilities;
import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeModelListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

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
				//System.exit(0);
				addTreeNode(new DefaultMutableTreeNode("Dani"));
				
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
		at.add(what);
		//treemodel.insertNodeInto(what, at, at.getChildCount());
		SwingUtilities.updateComponentTreeUI(explorer);
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
		FlowLayout layout = new FlowLayout(FlowLayout.CENTER);
		panel = new JPanel(layout);
		setContentPane(panel);
		
		rootElement = new DefaultMutableTreeNode("Root könyvtár");
		explorer = new JTree(rootElement);
		JScrollPane treeView = new JScrollPane(explorer);
		explorer.setVisibleRowCount(15);
		
		treemodel = new DefaultTreeModel(rootElement);
		treemodel.addTreeModelListener((new TreeModelListener() {
			
			@Override
			public void treeStructureChanged(TreeModelEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void treeNodesRemoved(TreeModelEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void treeNodesInserted(TreeModelEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void treeNodesChanged(TreeModelEvent e) {
				// TODO Auto-generated method stub
				
			}
		}));
		explorer.setEditable(true);
		
		
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
