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
		
		this.setVisible(true); //L�that�va teszem
		
		//Esem�nykezel�k
		kil�p�s.addActionListener(new ActionListener() { //Kil�p�s
			
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
		
		DefaultMutableTreeNode top = new DefaultMutableTreeNode("Root k�nyvt�r");
		explorer = new JTree(top);
		JScrollPane treeView = new JScrollPane(explorer);
		
		//panel.add(explorer);
		panel.add(treeView);
		
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
		kil�p�s = new JMenuItem("Kil�p�s");
		
		//Objektumok "elhejez�se"
		this.setJMenuBar(men�sor);
		men�sor.add(f�jlrendszerMen�);
		f�jlrendszerMen�.add(�jF�ljrendszer);
		f�jlrendszerMen�.add(bet�ltF�jlrendszer);
		f�jlrendszerMen�.add(kil�p�s);
	}

}
