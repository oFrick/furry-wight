package fájlrendszer.gui;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.StringTokenizer;

import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.SwingUtilities;
import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeModelListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

import fájlrendszer.main.DLLFunctions;
import fájlrendszer.main.Entitás;
import fájlrendszer.main.Fájl;
import fájlrendszer.main.Fájlrendszer;
import fájlrendszer.main.Könyvtár;

/**
 * Az alkalmazás ablaka
 * @author Kiss Dániel
 *
 */
public class MainFrame extends JFrame {
	
	//Menü mezõk
	private JMenuBar menüsor;
	private JMenu fájlrendszerMenü;
	private JMenuItem újFáljrendszer;
	private JMenuItem betöltFájlrendszer;
	private JMenuItem mentFájlrendszer;
	private JMenuItem kilépés;
	
	//Panel mezõk
	private JPanel panel;
	private JTree explorer;
	private DefaultMutableTreeNode rootElement;
	private SajatTreeModell treemodel;
	private JButton törlésGomb;
	private JButton újFájlGomb;
	private JButton újMappaGomb;
	private JButton másolásGomb;
	private JButton áthelyezésGomb;
	private GridBagConstraints constraint; //Elhelyezési "kényszer"
	private JLabel készítésIdeje;
	private JLabel engedélyek; //rwx|rwx|rwx alakban
	
	//Fájlrendszer mezõk
	private DefaultMutableTreeNode selectedNode; //A fában éppen kiválasztott node
	private Fájlrendszer fájlrendszer;
	private DLLFunctions dll;
	
	/**
	 * Az ablakot megvalósító osztály. Tartalmazza az egyes menüket, azok elemeit, illetve az ezekhez tartozó eseménykezelõket.
	 * @param név - String, az ablak neve
	 */
	public MainFrame(String név, Fájlrendszer fájlrendszer){
		super(név);
		
		//Ablak inicializálása
		
		this.setSize(800, 600); //Ablak mérete
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //Ablakbezárás esemény kezelése
		
		this.fájlrendszer = fájlrendszer;
		
		loadMenus();
		loadContent();
		
		this.setVisible(true); //Láthatóvá teszem
		
		dll = new DLLFunctions("D:\\Munka\\Egyetem\\git\\Fájlrendszer-local\\Fájlrendszer\\filesystem2.dll");
		//dll.formatDisk(32);
		
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
	 * 
	 * @param what - {@link DefaultMutableTreeNode}, amit be szeretnénk szúrni/elhelyezni
	 * @param at - {@link DefaultMutableTreeNode}, ahová el szeretnénk helyezni
	 */
	public void addTreeNode(DefaultMutableTreeNode what, DefaultMutableTreeNode at){
		//at.add(what); ne ezt használjuk!
		treemodel.insertNodeInto(what, at, at.getChildCount());
	}
	
	/**
	 * Elhelyez egy - a grafikus megjelenítéshez használt - node-ot a fa struktúra gyökerében
	 * 
	 * @param what - {@link DefaultMutableTreeNode}, amit el szeretnénk helyezni
	 */
	public void addTreeNode(DefaultMutableTreeNode what){
		addTreeNode(what, rootElement);
		
	}
	
	/**
	 * Törli a fából a grafikus megjelenítéshez használt node-ot
	 * 
	 * @param what - {@link DefaultMutableTreeNode}, amit törölni szeretnénk
	 */
	public void removeTreeNode(DefaultMutableTreeNode what){
		treemodel.removeNodeFromParent(what);
	}
	
	/**
	 * A panel (azaz a grafikus felület) tartalmának betöltése, beállítása, az ottani események kezelése
	 */
	private void loadContent(){
		GridBagLayout layout = new GridBagLayout();
		constraint = new GridBagConstraints();
		constraint.fill = GridBagConstraints.HORIZONTAL; // Maximális szélesség, szükséges magasság
		
		panel = new JPanel(layout);
		setContentPane(panel);
		
		rootElement = new DefaultMutableTreeNode(new Könyvtár("Root könyvtáram"));
		selectedNode = rootElement; //Alapértelmezés
		//fájlrendszer = new Fájlrendszer(new DefaultMutableTreeNode("Root könyvtár"));
		
		treemodel = new SajatTreeModell(rootElement);
		
		explorer = new JTree(treemodel);
		setupCellRenderer();
		JScrollPane treeView = new JScrollPane(explorer);
		explorer.setVisibleRowCount(15);
		
		//Fa eseménykezelõjének beállítása
		//treemodel = new DefaultTreeModel(rootElement)
		treemodel.addTreeModelListener((new TreeModelListener() {
			
			@Override
			public void treeStructureChanged(TreeModelEvent e) {
				System.out.println("Fa struktúra változás!");
				
			}
			
			@Override
			public void treeNodesRemoved(TreeModelEvent e) {
				SwingUtilities.updateComponentTreeUI(explorer);
			}
			
			@Override
			public void treeNodesInserted(TreeModelEvent e) {
				SwingUtilities.updateComponentTreeUI(explorer);
			}
			
			@Override
			public void treeNodesChanged(TreeModelEvent e) {
				Entitás ent = (Entitás)selectedNode.getUserObject();
				//TODO Megvalósítani a dll-ben!!!!! dll.rename(ent.getNév());
			}
			
			
		}));
		
		treemodel.reload();
		
		explorer.setEditable(true);
		
		
		//Kiválasztásos eseménykezelés beállítása
		explorer.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);	
		explorer.addTreeSelectionListener(new TreeSelectionListener() {
			
			@Override
			public void valueChanged(TreeSelectionEvent arg0) {
				DefaultMutableTreeNode selected = (DefaultMutableTreeNode) explorer.getLastSelectedPathComponent(); //A kiválasztott TreeNode
				
				if(selected == null) return; //Ha nincs kiválasztva semmi, akkor kilépünk a függvénybõl
				
				//Kiválasztjuk a fájlt/mappát, amire kattintottunk
				selectedNode = selected;
				
				/*
				//Megnyitás
				if (selectedNode.getUserObject() instanceof Fájl){
					Fájl f = (Fájl)selectedNode.getUserObject();
					dll.fileOpen(f.getNév());
				}else{
					Könyvtár k = (Könyvtár)selectedNode.getUserObject();
					dll.changeDirectory(k.getNév());
				}
				*/
				
				Entitás ent = (Entitás)selectedNode.getUserObject();
				
				updateAttributes();
			}
			
		});
		
		constraint.gridwidth = 5; //Három oszlop széles
		constraint.gridx = 0; //0. sor
		constraint.gridy = 0; //0. oszlopában van
		constraint.weightx = 1;
		constraint.weighty = 1;
		panel.add(treeView, constraint);
		
		újFájlGomb = new JButton("Új Fájl");
		constraint.gridwidth = 1;
		constraint.weightx = 1;
		constraint.weighty = 0.5;
		constraint.gridx = 0;
		constraint.gridy = 1;
		panel.add(újFájlGomb, constraint);
		
		újMappaGomb = new JButton("Új Könyvtár");
		constraint.gridwidth = 1;
		constraint.gridx = 1;
		constraint.gridy = 1;
		panel.add(újMappaGomb, constraint);
		
		törlésGomb = new JButton("Törlés");
		constraint.gridx = 2;
		constraint.gridy = 1;
		panel.add(törlésGomb, constraint);
		
		másolásGomb = new JButton("Másolás");
		constraint.gridx = 3;
		constraint.gridy = 1;
		panel.add(másolásGomb, constraint);
		
		áthelyezésGomb = new JButton("Áthelyezés");
		constraint.gridx = 4;
		constraint.gridy = 1;
		panel.add(áthelyezésGomb, constraint);
		
		engedélyek = new JLabel("Engedélyek: ");
		//constraint.gridwidth = 5;
		constraint.weighty=0;
		constraint.gridx = 0;
		constraint.gridy = 2;
		panel.add(engedélyek, constraint);
		
		készítésIdeje = new JLabel("Létrehozva: ");
		constraint.weighty=0;
		constraint.gridx = 0;
		constraint.gridy = 3;
		panel.add(készítésIdeje, constraint);
		
		//Gomb eseménykezelõk
		
		újFájlGomb.addActionListener(new ActionListener() {
			
			//-----Fájl létrehozás esemény-----
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Új fájl létrehozása - fájl objektum példányosítása
				createFile(újElemPopup("fájl"));
				
			}
		});
		
		újMappaGomb.addActionListener(new ActionListener() {
			
			//-----Könyvtár létrehozás esemény-----
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Új könyvtár létrehozása - könyvtár objektum példányosítása
				
				createDirectory(újElemPopup("könyvtár"));
				
			}
		});
		
		törlésGomb.addActionListener(new ActionListener() {
			
			//-----Fájl törlés esemény-----
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Fájl törlés esemény implementálása a Fájl objektummal
				if(selectedNode != null) {
					Entitás ent = (Entitás)selectedNode.getUserObject();
					dll.deleteFile(ent.getNév());
					removeTreeNode(selectedNode);
					selectedNode = null; //Törlés után állítsuk null-ra a kiválasztott TreeNode-ot, különben BUG-ot kapunk!
				}
			}
		});
		
	}
	
	//--------------------------------------------------------------------------------------------------------------------
	
	/**
	 * A kiválasztott fájl vagy mappa attribútumainak frissítése a grafikus felületen
	 * 
	 */
	private void updateAttributes(){
		Entitás ent = (Entitás)selectedNode.getUserObject();
		String jog = new String("Jogosultságok: ");
		DateFormat dátumFormátum = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Calendar c = Calendar.getInstance();
		if(ent != null){
			if(ent.isFuttatható()) jog+="r";
			else jog+="-";
			if(ent.isÍrható()) jog+="w";
			else jog+="-";
			engedélyek.setText(jog);
			készítésIdeje.setText("Létrehozva: "+dátumFormátum.format(ent.getLétrehozva().getTime()));
		}
		else{
			engedélyek.setText("Jogosultságok: ");
			készítésIdeje.setText("Létrehozva: ");
		}
		
	}
	
	/**
	 * Az ablak menüinek létrehozása és betöltése
	 * 
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
	
	private void setupCellRenderer(){
		explorer.setCellRenderer(new DefaultTreeCellRenderer() {
            private Icon fájlIcon = this.getDefaultLeafIcon();
            private Icon mappaIcon = this.getDefaultClosedIcon();
            @Override
            public Component getTreeCellRendererComponent(JTree tree,
                    Object value, boolean selected, boolean expanded,
                    boolean isLeaf, int row, boolean focused) {
                Component c = super.getTreeCellRendererComponent(tree, value,
                        selected, expanded, isLeaf, row, focused);
                
                DefaultMutableTreeNode node = (DefaultMutableTreeNode) value;
                
                if (node.getUserObject() instanceof Fájl)
                    setIcon(fájlIcon);
                else
                    setIcon(mappaIcon);
                return c;
            }
            
        });
		
	}
	
	private String újElemPopup(String milyet){
		TextField szövegmezõ = new TextField("új elem");
		Object[] válaszLehetõség = {"Adja meg a "+milyet+" nevét", szövegmezõ}; 
		Object[] válaszGomb = {"Létrehozás", "Mégsem"};
		
		JOptionPane optionpane = new JOptionPane(válaszLehetõség, JOptionPane.QUESTION_MESSAGE, JOptionPane.YES_NO_OPTION, null, válaszGomb, válaszGomb[0]);
		JDialog dialog = optionpane.createDialog(this, "Új "+milyet+" megadás");
		dialog.show();
		
		return szövegmezõ.getText();
		
	}
	/**
	 * Megadja, hogy melyik az épp aktuális könyvtár, amiben vagyunk. Ha épp egy fájl van
	 * kijelölve, akkor a fájlt tartalmazó könyvtárral tér vissza, egyébként a kiválasztott könyvtárral.
	 * @return
	 */
	private DefaultMutableTreeNode getWorkingDirectory(){
		if (selectedNode.getUserObject() instanceof Könyvtár) return selectedNode;
		else return (DefaultMutableTreeNode) selectedNode.getParent();
	}
	
	/**Visszatér a sztring nevû nodedal, ha az aktuális mappa tartalmazza azt a node-ot. Egyébként null a visz.érték
	 * @author Kiss Dániel
	 * @param mit
	 * @return
	 */
	private DefaultMutableTreeNode tartalmaz(String mit){
		DefaultMutableTreeNode wDir = getWorkingDirectory();
		
		for(int i=0; i<wDir.getChildCount(); i++){
			Entitás ent = (Entitás) ((DefaultMutableTreeNode) wDir.getChildAt(i)).getUserObject();
			if(ent.getNév().equals(mit)) return (DefaultMutableTreeNode) wDir.getChildAt(i);
		}
		return null;
	}
	
	public void createFile(String név){
		Fájl fájl = new Fájl(név);
		//dll.createFile(fájl.getNév());
		
		if(selectedNode == null) addTreeNode(new DefaultMutableTreeNode(fájl));
		else if(selectedNode.getUserObject() instanceof Könyvtár) addTreeNode(new DefaultMutableTreeNode(fájl), selectedNode);
	}
	public void createDirectory(String név){
		Könyvtár könyvtár = new Könyvtár(név);
		//dll.createDirectory(könyvtár.getNév());
		
		if(selectedNode == null) addTreeNode(new DefaultMutableTreeNode(könyvtár));
		else if(selectedNode.getUserObject() instanceof Könyvtár) addTreeNode(new DefaultMutableTreeNode(könyvtár), selectedNode);
	}
	
	/**A jelenlegi könyvtárban lévõ, adott nevû elemet átnevezi. Csak parancsértelemzõvel kell ezt használni.
	 * @param mit
	 * @param mire
	 */
	public void rename(String mit, String mire){
		
		DefaultMutableTreeNode wDir = getWorkingDirectory(); //Aktuális munkakönyvtár
		boolean siker = false;
			
		for(int i=0; i<wDir.getChildCount(); i++){
			Entitás ent = (Entitás) ((DefaultMutableTreeNode) wDir.getChildAt(i)).getUserObject();
			if(ent.getNév() == mit){
				ent.setNév(mire);
				siker = true;
				break;
			}
		}
		
		if(!siker) Seged.popup("Nincs ilyen könytár/fájl: "+mit+"!", "Átnevezés sikertelen!", this);
	}
	
	/**Átváltja az aktuális könyvtárat a megadott útvonalnak megfelelõ könyvtárba.
	 * @param hova
	 */
	public void changeDirectory(String hova){
		StringTokenizer st = new StringTokenizer(hova,"/");
		while(st.hasMoreTokens()){
			String nev = st.nextToken();
			if(nev.equals("root")){ //Gyökérbe váltás
				selectedNode = rootElement;
				//dll.changeDirectory(".");
			}else if(nev.equals("..")){ //Szülõ mappába váltás
				selectedNode = (DefaultMutableTreeNode) selectedNode.getParent();
				//dll.changeDirectory("..");
			}else{ //Almappába váltás
				DefaultMutableTreeNode cel = tartalmaz(nev);
				if(cel != null){ //Ha van ilyen alkönyvtár
					if(cel.getUserObject() instanceof Könyvtár){ //Ha a cél egy könyvtár
						selectedNode = cel;
						//dll.changeDirectory(nev);
					}else Seged.popup("Ez nem könyvtár: "+nev+"!", "Nem könyvtár", this);
				} else{
					Seged.popup("Nincs ilyen könyvtár/alkönyvtár: "+nev+" itt: "+((Entitás)selectedNode.getUserObject()).getNév()+"!", "Sikertelen könyvtárváltás!", this);
					break;
				}
			}
		}
		
	}
}
