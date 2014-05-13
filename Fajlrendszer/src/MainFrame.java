

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.StringTokenizer;

import javafx.stage.Popup;

import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
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

import fájlrendszer.gui.SajatTreeModell;
import fájlrendszer.main.Entitás;
import fájlrendszer.main.Fájl;
import fájlrendszer.main.Könyvtár;
import fájlrendszer.titkosítás.KépTitkosító;
import fájlrendszer.titkosítás.Titkosító;
import fájlrendszer.titkosítás.titkosítóAlgoritmus.EgyszerûKépAlgoritmus;
import fájlrendszer.titkosítás.titkosítóAlgoritmus.EltolóAlgoritmus;

/**
 * Az alkalmazás ablaka
 * @author Kiss Dániel
 *
 */
public class MainFrame extends JFrame {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2682758755048073588L;

	public boolean debug = true;
	
	//Menü mezõk
	private JMenuBar menüsor;
	private JMenu fájlrendszerMenü;
	private JMenuItem újFáljrendszer;
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
	private JLabel kijelölt;
	private JTextArea tartalom;
	private JButton ment;
	private JCheckBox írható;
	private JCheckBox futtatható;
	private JCheckBox titkosított;
	private JCheckBox rejtett;
	
	private JMenu egyébMenü;
	private JMenuItem parancsMenüElem;
	private JMenu titkosítás;
	private JMenu szimplaTitkosító;
		private JMenuItem szimplaKódoló;
		private JMenuItem szimplaDekódoló;
	private JMenu képTitkosító;
		private JMenuItem képKódoló;
		private JMenuItem képDekódoló;
	
	//TODO kitörölni
	private String teszt;
	
	//Fájlrendszer mezõk
	private DefaultMutableTreeNode selectedNode; //A fában éppen kiválasztott node
	
	private DLLFunctions dll;
	private MainFrame sajat;
	
	/**
	 * Az ablakot megvalósító osztály. Tartalmazza az egyes menüket, azok elemeit, illetve az ezekhez tartozó eseménykezelõket.
	 * @param név - String, az ablak neve
	 */
	public MainFrame(String név){
		super(név);
		sajat = this;
		
		//Ablak inicializálása
		
		this.setSize(800, 600); //Ablak mérete
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //Ablakbezárás esemény kezelése
		String path = DLLFunctions.class.getProtectionDomain().getCodeSource().getLocation().getPath()+"filesystem.dll";
		File tmp = new File("filesystem.dll");
		System.out.println(tmp.getAbsolutePath());
		dll = new DLLFunctions(tmp.getAbsolutePath());
		dll.formatDisk(64);
		
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
		
		createDirectory("Danika");
		rename("Danika", "Gábor");
		
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
		
		//TODO eltávolítani a felesleges részt
		rootElement = new DefaultMutableTreeNode(new Könyvtár("root",0));
		selectedNode = rootElement; //Alapértelmezés
		
		treemodel = new SajatTreeModell(rootElement);
		
		explorer = new JTree(treemodel);
		setupCellRenderer();
		JScrollPane treeView = new JScrollPane(explorer);
		explorer.setVisibleRowCount(15);
		
		//Fa eseménykezelõjének beállítása
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
				if(ent instanceof Fájl) renameGui(ent.getOldName(), ent.getNév(), true);
				else renameGui(ent.getOldName(), ent.getNév(), false);
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
				if(selectedNode == null){
					selectedNode = rootElement;
					Seged.popup("Automatikus visszalépés a root könyvtárba!", "Vissza a root-ba!", sajat);
					return;
				}
				
				if(selected.getUserObject() instanceof Könyvtár){
					if(tartalmaz(selected)){
						changeDirectory(((Entitás)selected.getUserObject()).getNév());
						selectedNode = selected;
					}else if(rootElement == selected){
						selectedNode = selected;
						changeDirectory("root");
					}else if(selected == ((DefaultMutableTreeNode)selectedNode.getParent())){
						//selectedNode = selected;
						changeDirectory("..");
					}else if(selected != selectedNode){
						Seged.popup("Hibás mûvelet: nem lehet így mappát/fájlt kiválasztani!", "Hibás kijelölés", sajat);
					}
					tartalom.setText("");
					tartalom.setEnabled(false);
					ment.setEnabled(true);
					
				}else{
					
					if(!tartalmaz(selected)){
						Seged.popup("Hibás mûvelet: nem lehet így mappát/fájlt kiválasztani!", "Hibás kijelölés", sajat);
					}else{
						selectedNode = selected;
						
						String adat = dataGet();
						if(adat != null){
							tartalom.setEnabled(true);
							tartalom.setText(adat);
							ment.setEnabled(true);
						}
					}
				}
				
				//Jogosultságok megjelenítése
				if(((Entitás)selected.getUserObject()).isFuttatható()) futtatható.setSelected(true);
				else futtatható.setSelected(false);
				if(((Entitás)selected.getUserObject()).isÍrható()) írható.setSelected(true);
				else írható.setSelected(false);
				if(((Entitás)selected.getUserObject()).isTitkosított()) titkosított.setSelected(true);
				else titkosított.setSelected(false);
				if(((Entitás)selected.getUserObject()).isRejtett()) rejtett.setSelected(true);
				else rejtett.setSelected(false);
				
				String[] l = dll.list();
				if (debug) System.out.print("A mappa tartalma: ");
				for(String str : l){
					System.out.print(str+", ");
				}
				System.out.println();
				
				
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
		
		készítésIdeje = new JLabel("Létrehozva: ");
		constraint.weighty=0;
		constraint.gridx = 0;
		constraint.gridy = 2;
		panel.add(készítésIdeje, constraint);
		
		kijelölt = new JLabel("Kijelölt elem: ");
		constraint.gridx = 1;
		panel.add(kijelölt, constraint);
		
		tartalom = new JTextArea(10, 1);
		constraint.gridy = 3;
		constraint.gridx = 0;
		constraint.weighty = 1;
		constraint.gridwidth = 4;
		panel.add(tartalom, constraint);
		
		ment = new JButton("Mentés");
		constraint.gridy = 4;
		constraint.gridwidth = 1;
		panel.add(ment, constraint);
		
		futtatható = new JCheckBox();
		constraint.gridy = 5;
		constraint.gridx = 1;
		panel.add(futtatható, constraint);
		
		írható = new JCheckBox();
		constraint.gridx = 2;
		panel.add(írható, constraint);
		
		titkosított = new JCheckBox();
		constraint.gridx = 3;
		panel.add(titkosított, constraint);
		
		rejtett = new JCheckBox();
		constraint.gridx = 4;
		panel.add(rejtett, constraint);
		
		JLabel jogok = new JLabel("Jogosultságok(X-W-E-H)");	
		constraint.gridx = 5;
		panel.add(jogok, constraint);
		//Gomb eseménykezelõk
		
		másolásGomb.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				String innen = Seged.inputPopup("Adja meg a forrást a másoláshoz!", "forrás", "Másolás innen", sajat);
				String ezt = Seged.inputPopup("Adja meg a célt az másoláshoz!", "cél", "Másolás ide", sajat);
				copy(innen, ezt);
			}
		});
		
		áthelyezésGomb.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				String innen = Seged.inputPopup("Adja meg a forrást az áthelyezéshez!", "forrás", "Áthelyezés innen", sajat);
				String ezt = Seged.inputPopup("Adja meg a célt az áthelyezéshez!", "cél", "Áthelyezés ide", sajat);
				replace(innen, ezt);
				
			}
		});
		
		ment.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				Entitás ent = ((Entitás)selectedNode.getUserObject());
				ent.setFuttatható(futtatható.isSelected());
				ent.setÍrható(írható.isSelected());
				ent.setTitkosított(titkosított.isSelected());
				ent.setRejtett(rejtett.isSelected());
				
				
				dataSet(ent);
			}
		});
		
		újFájlGomb.addActionListener(new ActionListener() {
			
			//-----Fájl létrehozás esemény-----
			@Override
			public void actionPerformed(ActionEvent e) {
				createFile(újElemPopup("fájl"));
				
			}
		});
		
		újMappaGomb.addActionListener(new ActionListener() {
			
			//-----Könyvtár létrehozás esemény-----
			@Override
			public void actionPerformed(ActionEvent e) {
				
				createDirectory(újElemPopup("könyvtár"));
				
			}
		});
		
		törlésGomb.addActionListener(new ActionListener() {
			
			//-----Fájl törlés esemény-----
			@Override
			public void actionPerformed(ActionEvent arg0) {
				/*
				if(selectedNode != null) {
					Entitás ent = (Entitás)selectedNode.getUserObject();
					dll.deleteFile(ent.getNév());
					DefaultMutableTreeNode törlendõ = selectedNode;
					changeDirectory(".."); //Törlés után vissza a szülõbe!
					removeTreeNode(törlendõ);
					
				}else Seged.popup("Nincs mit törölni!", "Törlés sikertelen!", sajat);
				*/
				
				Entitás ent = (Entitás)selectedNode.getUserObject();
				if(ent instanceof Fájl) deleteGui(ent.getNév(), true);
				else deleteGui(ent.getNév(), false);
			}
		});
		
	}
	
	//--------------------------------------------------------------------------------------------------------------------
	
	private void újFájlrendszer(){
	//TODO implementálni
		rootElement = new DefaultMutableTreeNode(new Könyvtár("root",0));
		selectedNode = rootElement; //Alapértelmezés
		
		treemodel = new SajatTreeModell(rootElement);
		
		explorer = new JTree(treemodel);
	}
	
	/**
	 * A kiválasztott fájl vagy mappa attribútumainak frissítése a grafikus felületen
	 * 
	 */
	private void updateAttributes(){
		Entitás ent = (Entitás)selectedNode.getUserObject();
		DateFormat dátumFormátum = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		if(ent != null){
			készítésIdeje.setText("Létrehozva: "+dátumFormátum.format(ent.getLétrehozva().getTime()));
			kijelölt.setText("Kijelölt elem: "+((Entitás)selectedNode.getUserObject()).getNév());
		}
		else{
			készítésIdeje.setText("Létrehozva: ");
			kijelölt.setText("Kijelölt elem: ");
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
		kilépés = new JMenuItem("Kilépés");
		
		//Objektumok "elhejezése"
		this.setJMenuBar(menüsor);
		menüsor.add(fájlrendszerMenü);
		fájlrendszerMenü.add(újFáljrendszer);
		fájlrendszerMenü.add(kilépés);
		
		egyébMenü = new JMenu("Egyéb");
		menüsor.add(egyébMenü);
		parancsMenüElem = new JMenuItem("Parancsértelmezõ");
		egyébMenü.add(parancsMenüElem);
		
		titkosítás = new JMenu("Titkosítás");
		egyébMenü.add(titkosítás);
		szimplaTitkosító = new JMenu("Egyszerû titkosítás");
		titkosítás.add(szimplaTitkosító);
		szimplaKódoló = new JMenuItem("Kódolás");
		szimplaTitkosító.add(szimplaKódoló);
		szimplaDekódoló = new JMenuItem("Dekódolás");
		szimplaTitkosító.add(szimplaDekódoló);
		képTitkosító = new JMenu("Kép alapú kódolás");
		titkosítás.add(képTitkosító);
		képKódoló = new JMenuItem("Kódolás");
		képTitkosító.add(képKódoló);
		képDekódoló = new JMenuItem("Dekódolás");
		képTitkosító.add(képDekódoló);
		
		
		parancsMenüElem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				String parancs = Seged.inputPopup("Adja meg a parancsot:", "parancs", "Parancsértelmezõ", sajat);
				Shell shell = new Shell(sajat);
				shell.bemenet(parancs);
								
			}
		});
		
		szimplaKódoló.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				String kulcs = Seged.inputPopup("Titkosítás kulcsa:", "kulcs", "Egyszerû kódolás", sajat);
				Titkosító t;
				
				if(!kulcs.isEmpty()){
					t = new Titkosító(kulcs, new EltolóAlgoritmus());
					
					//DEBUG
					teszt = dataGet();
					System.out.println("Kódolandó: "+teszt);
					teszt = t.titkosít(teszt);
					System.out.println("Kódolva: "+teszt);
					
					dataSet(((Entitás)selectedNode.getUserObject()), t.titkosít(dataGet()));
					

				}else Seged.popup("Meg kell adni egy kulcsot!",  "Sikertelen kódolás", sajat);
				
			}
		});
		
		szimplaDekódoló.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				String kulcs = Seged.inputPopup("Titkosítás kulcsa:", "kulcs", "Egyszerû kódolás", sajat);
				Titkosító t;
				
				if(!kulcs.isEmpty()){
					t = new Titkosító(kulcs, new EltolóAlgoritmus());
					dataSet(((Entitás)selectedNode.getUserObject()), t.visszanyer(dataGet()));
					
					//DEBUG
					teszt = t.visszanyer(teszt);
					System.out.println("Visszafejtve: "+teszt);
					
				}else Seged.popup("Meg kell adni egy kulcsot!",  "Sikertelen dekódolás", sajat);
				
			}
		});
		
		képKódoló.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				String útvonal = Seged.inputPopup("Titkosító kép útvonala:", "kulcs", "Egyszerû kódolás", sajat);
				int n = Integer.parseInt(Seged.inputPopup("Képpontok darabszáma:", "50", "Képpontszám", sajat));
				if(n>=0 && n<90000000){
					try {
						
						BufferedImage kulcs = ImageIO.read(new File(útvonal));
						KépTitkosító t = new KépTitkosító(kulcs, new EgyszerûKépAlgoritmus(15));
						
						//DEBUG
						teszt = dataGet();
						System.out.println("Kódolandó: "+teszt);
						teszt = t.titkosít(teszt);
						System.out.println("Kódolva: "+teszt);
						
						dataSet(((Entitás)selectedNode.getUserObject()), t.titkosít(dataGet()));
						
					} catch (IOException e) {
						Seged.popup("Nincs ilyen fájl!", "Sikertelen titkosítás!", sajat);
					}
				}else Seged.popup("pixelszám hibás megadás", "Sikertelen titkosítás", sajat);
			}
		});
		
		képDekódoló.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				String útvonal = Seged.inputPopup("Titkosító kép útvonala:", "kulcs", "Egyszerû kódolás", sajat);
				int n = Integer.parseInt(Seged.inputPopup("Képpontok darabszáma:", "50", "Képpontszám", sajat));
				if(n>=0 && n<90000000){
					try {
						
						BufferedImage kulcs = ImageIO.read(new File(útvonal));
						KépTitkosító t = new KépTitkosító(kulcs, new EgyszerûKépAlgoritmus(15));
						
						dataSet(((Entitás)selectedNode.getUserObject()), t.visszanyer(dataGet()));
						
						//DEBUG
						teszt = t.visszanyer(teszt);
						System.out.println("Visszafejtve: "+teszt);
						
					} catch (IOException e) {
						Seged.popup("Nincs ilyen fájl!", "Sikertelen titkosítás!", sajat);
					}
				}else Seged.popup("pixelszám hibás megadás", "Sikertelen titkosítás", sajat);
				
			}
		});
		
	}
	
	private void setupCellRenderer(){
		explorer.setCellRenderer(new DefaultTreeCellRenderer() {
			private static final long serialVersionUID = 6307850244018307488L;
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
		
		if (selectedNode.getUserObject() instanceof Könyvtár) {
			return selectedNode;
		}
		else{
			return (DefaultMutableTreeNode) selectedNode.getParent();
		}
		
		/*
		if(selectedNode == rootElement) return rootElement;
		else return (DefaultMutableTreeNode) selectedNode.getParent();
		*/
	}
	
	/**Visszatér a sztring nevû nodedal, ha az aktuális mappa tartalmazza azt a node-ot. Egyébként null a visz.érték
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
	
	/**Leellenõrzi, hogy a megadott node-ot tartalmazza-e az aktuális könyvtár. Az aktuális könyvtár az a könyvtár grafikailag,
	 * ahol az utoljára kiválasztott node van. Ha ez a node könyvtár, akkor ez az aktuális.
	 * @param mit
	 * @return
	 */
	private boolean tartalmaz(DefaultMutableTreeNode mit){
		if(tartalmaz(((Entitás)mit.getUserObject()).getNév()) != null) return true;
		else return false;
	}
	
	public void createFile(String név){
		Fájl fájl = new Fájl(név,0);
		
		if(dll.createFile(fájl.getNév())){
			if(selectedNode == null) addTreeNode(new DefaultMutableTreeNode(fájl));
			else if(selectedNode.getUserObject() instanceof Könyvtár) addTreeNode(new DefaultMutableTreeNode(fájl), selectedNode);
		}else Seged.popup("Hiba a létrehozáskor!", "Sikertelen fájl létrehozás", this);	
		
		int handle = dll.fileOpen(név);
		fájl.setHandle(handle);
		dll.fileClose(handle);
	}
	
	public void createDirectory(String név){
		Könyvtár könyvtár = new Könyvtár(név,0);
		if(dll.createDirectory(név)){
			if(selectedNode == null) addTreeNode(new DefaultMutableTreeNode(könyvtár));
			else if(selectedNode.getUserObject() instanceof Könyvtár) addTreeNode(new DefaultMutableTreeNode(könyvtár), selectedNode);
		}else Seged.popup("Hiba a létrehozáskor!", "Sikertelen könyvtár létrehozás", this);		
		
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
			if(ent.getNév().equals(mit) || ent.getOldName().equals(mit)){
				if(dll.renameFile(mit, mire)){
					if(!ent.getNév().equals(mire)) ent.setNév(mire);
					siker = true;
				}
				break;
			}
		}
		
		if(!siker) Seged.popup("Nincs ilyen könytár/fájl: "+mit+"!", "Átnevezés sikertelen!", this);
	}
	
	public void renameGui(String mit, String mire, boolean isFájl){
		if(!isFájl) changeDirectory("..");
		rename(mit, mire);
	}
	
	/**Átváltja az aktuális könyvtárat a megadott útvonalnak megfelelõ könyvtárba.
	 * @param utvonal
	 */
	public void changeDirectory(String utvonal){
		StringTokenizer st = new StringTokenizer(utvonal,"/");
		while(st.hasMoreTokens()){
			String nev = st.nextToken();
			if(nev.equals("root")){ //Gyökérbe váltás
				selectedNode = rootElement;
				dll.changeDirectory(".");
				updateAttributes();
			}else if(nev.equals("..")){ //Szülõ mappába váltás
				if(selectedNode != rootElement){
					selectedNode = (DefaultMutableTreeNode) selectedNode.getParent();
					dll.changeDirectory("..");
					updateAttributes();
				}else Seged.popup("A root-nál nem lehet feljebb lépni!", "Sikertelen könyvtár váltás", sajat);
			}else{ //Almappába váltás
				DefaultMutableTreeNode cel = tartalmaz(nev);
				if(cel != null){ //Ha van ilyen alkönyvtár
					if(cel.getUserObject() instanceof Könyvtár){ //Ha a cél egy könyvtár
						selectedNode = cel;
						dll.changeDirectory(nev);
						System.out.println("váltottam: "+nev+"!");
						updateAttributes();
					}else Seged.popup("Ez nem könyvtár: "+nev+"!", "Nem könyvtár", this);
				} else{
					Seged.popup("Nincs ilyen könyvtár/alkönyvtár: "+nev+" itt: "+((Entitás)selectedNode.getUserObject()).getNév()+"!", "Sikertelen könyvtárváltás!", this);
					break;
				}
			}
		}
		
	}
	
	/**Törli az aktuális könyvtárban lévõ fájlt/mappát
	 * @author Kiss Dániel
	 * @param mit
	 */
	public void delete(String mit){
		if(selectedNode != null) {
			DefaultMutableTreeNode node = tartalmaz(mit);
			if(node == null) node = getWorkingDirectory();
			if(node != null){
				if(dll.deleteFile(((Entitás)node.getUserObject()).getNév())){
					removeTreeNode(node);
					if(getWorkingDirectory() != rootElement) changeDirectory("..");
					node = null;
				}else Seged.popup("Sikertelen törlés. A fájlrendszerben ilyen fájl/mappa nem található!", "Sikertelen törlés", this);
				
			}else Seged.popup("Nincs milyen fájl/mappa!", "Sikertelen törlés", this);
		}
	}
	
	public void deleteGui(String mit, boolean isFájl){
		if(!isFájl)changeDirectory("..");
		delete(mit);
	}
	
	/**Átmásolja az elsõ paraméterben útvonalként megadott entitást ({@link Entitás}) a második paraméterben megadott helyre, ami szintén
	 * egy útvonal.
	 * @param honnan String
	 * @param hova String
	 */
	public void copy(String honnan, String hova){
		/*
		DefaultMutableTreeNode mit = útvonalFejt(honnan);
		DefaultMutableTreeNode mibe = útvonalFejt(hova);
			
		System.out.println(((Entitás)mit.getUserObject()).getNév());
		System.out.println(((Entitás)mibe.getUserObject()).getNév());
		
		DefaultMutableTreeNode uj = (DefaultMutableTreeNode) mit.clone();
			
		addTreeNode(uj, mibe);
		*/
		
		DefaultMutableTreeNode mit = útvonalFejt(honnan);
		int handle = dll.fileOpen("dani");
		
		if(mit.getUserObject() instanceof Könyvtár){
			Seged.popup("Csak fájlt lehet alacsony szinten másolni!", "Sikertelen másolás!", sajat);
			return;
		}
		
		DefaultMutableTreeNode mibe;
		
		if(hova.equals("..") && selectedNode != rootElement){
			mibe = (DefaultMutableTreeNode)selectedNode.getParent();
		}else if(hova.equals("root")){
			mibe = rootElement;
		}else{
			mibe = útvonalFejt(hova);
		}
			
		System.out.println(((Entitás)mit.getUserObject()).getNév());
		System.out.println(((Entitás)mibe.getUserObject()).getNév());
		
		if(handle != 0){
			dll.fileCopy(handle);
			dll.fileClose(handle);
			
			DefaultMutableTreeNode uj = (DefaultMutableTreeNode) mit.clone();
			addTreeNode(uj, mibe);

		}else Seged.popup("Másolásnál rossz handle megadás!","Sikertelen másolás!",sajat);
	}
	
	public void replace(String honnan, String hova){
		
		int handle = dll.fileOpen(honnan);
		if(handle != 0){
			DefaultMutableTreeNode wDir = getWorkingDirectory();
			DefaultMutableTreeNode mit = útvonalFejt(honnan);
			DefaultMutableTreeNode mibe;
			
			if(wDir != getWorkingDirectory()) changeDirectory(".."); //Mert az elõzõ útvonalfejtés bedobott a "honnan" mappába
			
			if(hova.equals("..") && selectedNode != rootElement){
				mibe = (DefaultMutableTreeNode)selectedNode.getParent();
			}else if(hova.equals("root")){
				mibe = rootElement;
			}else{
				mibe = útvonalFejt(hova);
			}
			
			
				
			
			if(handle != 0 && mit != null && mibe != null){
				dll.fileMove(handle);
				dll.fileClose(handle);
				addTreeNode(mit, mibe);

			}else Seged.popup("Nem találom a handle-t vagy helytelen forrás/cél megadás!","Sikertelen áthelyezés!",sajat);
		}
		
	}
	
	/**Visszatér az adoutt útvonal végén lévõ fájl/könyvtárral. Az aktuális mappát a megfelelõ helyre mozgatja, azaz ahol az útvonalnak vége van
	 * @param útvonal
	 * @return
	 */
	private DefaultMutableTreeNode útvonalFejt(String útvonal){
		StringTokenizer st = new StringTokenizer(útvonal,"/");
		
		String[] darabok = útvonal.split("/");
		String cim = new String();
		
		for(int i=0; i<darabok.length-1; i++){
			cim+=darabok[i]+"/";
		}
		String utolso = darabok[darabok.length-1];
		changeDirectory(cim);
		
		DefaultMutableTreeNode node = tartalmaz(utolso);
		if(node != null){//Ha létezik az utolsó elem
			if(node.getUserObject() instanceof Könyvtár) changeDirectory(utolso);
			return node;
		}
		
		return null;
	}
	
	private String dataGet(){
		byte[] b;
		
		int handle = dll.fileOpen(((Entitás)selectedNode.getUserObject()).getNév());
		if(handle != 0){
			b = dll.fileGetData(handle);
			dll.fileClose(handle);
			
			return new String(b);
		}
		return null;
	}
	
	private void dataSet(Entitás ent){
		dataSet(ent, tartalom.getText());
	}
	
	private void dataSet(Entitás ent, String str){
		if(ent instanceof Fájl){
			int handle = dll.fileOpen(ent.getNév());
			if(handle != 0){
				dll.fileSetData(handle, str.getBytes());
				dll.fileSetReadPermission(handle, ent.isFuttatható());
				dll.fileSetWritePermission(handle, ent.isÍrható());
				dll.fileSetEncryption(handle, ent.isTitkosított());
				dll.fileSetEncryption(handle, ent.isRejtett());
				dll.fileClose(handle);
			}
			
		}
	}
}
