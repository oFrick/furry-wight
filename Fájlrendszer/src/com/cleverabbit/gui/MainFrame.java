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
 * Az alkalmaz�s ablaka
 * @author Kiss D�niel
 *
 */
public class MainFrame extends JFrame {
	
	private JMenuBar men�sor;
	private JMenu f�jlrendszerMen�;
	private JMenuItem �jF�ljrendszer;
	private JMenuItem bet�ltF�jlrendszer;
	private JMenuItem mentF�jlrendszer;
	private JMenuItem kil�p�s;
	
	private JPanel panel;
	
	private JTree explorer;
	private DefaultMutableTreeNode rootElement;
	private DefaultTreeModel treemodel;
	private JButton t�rl�sGomb;
	private JButton l�trehozGomb;
	private JButton m�sol�sGomb;
	private JButton �thelyez�sGomb;
	
	private GridBagConstraints constraint; //Elhelyez�si "k�nyszer"
	
	/**
	 * Az ablakot megval�s�t� oszt�ly. Tartalmazza az egyes men�ket, azok elemeit, illetve az ezekhez tartoz� esem�nykezel�ket.
	 * @author Kiss D�niel
	 * @param n�v - String, az ablak neve
	 */
	public MainFrame(String n�v){
		super(n�v);
		
		//Ablak inicializ�l�sa
		
		this.setSize(800, 600); //Ablak m�rete
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //Ablakbez�r�s esem�ny kezel�se
		
		loadMenus();
		loadContent();
		
		this.setVisible(true); //L�that�v� teszem
		
		//Esem�nykezel�k
		kil�p�s.addActionListener(new ActionListener() { //Kil�p�s
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
				
			}
		});
	}
	
	/**
	 * Elhelyez egy - a grafikus megjelen�t�shez haszn�lt - node-ot (ami lehet k�nyvt�r vagy mappa) a fa strukt�r�ban
	 * @author Kiss D�niel
	 * @param what - {@link DefaultMutableTreeNode}, amit be szeretn�nk sz�rni/elhelyezni
	 * @param at - {@link DefaultMutableTreeNode}, ahov� el szeretn�nk helyezni
	 */
	public void addTreeNode(DefaultMutableTreeNode what, DefaultMutableTreeNode at){
		//at.add(what); ne ezt haszn�ljuk!
		treemodel.insertNodeInto(what, at, at.getChildCount());
	}
	
	/**
	 * Elhelyez egy - a grafikus megjelen�t�shez haszn�lt - node-ot a fa strukt�ra gy�ker�ben
	 * @author Kiss D�niel
	 * @param what - {@link DefaultMutableTreeNode}, amit el szeretn�nk helyezni
	 */
	public void addTreeNode(DefaultMutableTreeNode what){
		addTreeNode(what, rootElement);
		
	}
	
	/**
	 * T�rli a f�b�l a grafikus megjelen�t�shez haszn�lt node-ot
	 * @author Kiss D�niel
	 * @param what - {@link DefaultMutableTreeNode}, amit t�r�lni szeretn�nk
	 */
	public void removeTreeNode(DefaultMutableTreeNode what){
		what.removeFromParent();
	}
	
	private void loadContent(){
		GridBagLayout layout = new GridBagLayout();
		constraint = new GridBagConstraints();
		constraint.fill = GridBagConstraints.HORIZONTAL; // Maxim�lis sz�less�g, sz�ks�ges magass�g
		panel = new JPanel(layout);
		setContentPane(panel);
		
		rootElement = new DefaultMutableTreeNode("Root k�nyvt�r");
		explorer = new JTree(rootElement);
		JScrollPane treeView = new JScrollPane(explorer);
		explorer.setVisibleRowCount(15);
		
		//Fa esem�nykezel�j�nek be�ll�t�sa
		treemodel = new DefaultTreeModel(rootElement);
		treemodel.addTreeModelListener((new TreeModelListener() {
			
			@Override
			public void treeStructureChanged(TreeModelEvent e) {
				System.out.println("Fa strukt�ra v�ltoz�s!");
				
			}
			
			@Override
			public void treeNodesRemoved(TreeModelEvent e) {
				
			}
			
			@Override
			public void treeNodesInserted(TreeModelEvent e) {
				SwingUtilities.updateComponentTreeUI(explorer);
				System.out.println("Hozz�adva!");
			}
			
			@Override
			public void treeNodesChanged(TreeModelEvent e) {
				
			}
		}));
		explorer.setEditable(true);
		
		//Kiv�laszt�sos esem�nykezel�s be�ll�t�sa
		explorer.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);	
		explorer.addTreeSelectionListener(new TreeSelectionListener() {
			
			@Override
			public void valueChanged(TreeSelectionEvent arg0) {
				DefaultMutableTreeNode selected = (DefaultMutableTreeNode) explorer.getLastSelectedPathComponent(); //A kiv�lasztott TreeNode
				
				if(selected == null) return; //Ha nincs kiv�lasztva semmi, akkor kil�p�nk a f�ggv�nyb�l
				
				//TODO Kiv�laszott TreeNode kezel�se: f�jl adatainak megjelen�t�se, akt�v(kiv�lasztott) TreeNode be�ll�t�sa=> oszt�ly priv�t adattagban
				
			}
		});
		
		
		constraint.gridwidth = 4; //H�rom oszlop sz�les
		constraint.gridx = 0; //0. sor
		constraint.gridy = 0; //0. oszlop�ban van
		constraint.weightx = 1;
		constraint.weighty = 1;
		panel.add(treeView, constraint);
		
		l�trehozGomb = new JButton("�j elem");
		constraint.gridwidth = 1;
		constraint.weightx = 1;
		constraint.weighty = 0.5;
		constraint.gridx = 0;
		constraint.gridy = 1;
		panel.add(l�trehozGomb, constraint);
		
		t�rl�sGomb = new JButton("T�rl�s");
		constraint.gridx = 1;
		constraint.gridy = 1;
		panel.add(t�rl�sGomb, constraint);
		
		m�sol�sGomb = new JButton("M�sol�s");
		constraint.gridx = 2;
		constraint.gridy = 1;
		panel.add(m�sol�sGomb, constraint);
		
		�thelyez�sGomb = new JButton("�thelyez�s");
		constraint.gridx = 3;
		constraint.gridy = 1;
		panel.add(�thelyez�sGomb, constraint);
		
	}
	
	/**
	 * Az ablak men�inek l�trehoz�sa �s bet�lt�se
	 * @author Kiss D�niel
	 */
	private void loadMenus(){
		//P�ld�nyos�t�s
		men�sor = new JMenuBar();
		f�jlrendszerMen� = new JMenu("F�jlrendszer");
		�jF�ljrendszer = new JMenuItem("�j");
		bet�ltF�jlrendszer = new JMenuItem("Bet�lt");
		mentF�jlrendszer = new JMenuItem("Ment�s");
		kil�p�s = new JMenuItem("Kil�p�s");
		
		//Objektumok "elhejez�se"
		this.setJMenuBar(men�sor);
		men�sor.add(f�jlrendszerMen�);
		f�jlrendszerMen�.add(�jF�ljrendszer);
		f�jlrendszerMen�.add(bet�ltF�jlrendszer);
		f�jlrendszerMen�.add(mentF�jlrendszer);
		f�jlrendszerMen�.add(kil�p�s);
	}

}
