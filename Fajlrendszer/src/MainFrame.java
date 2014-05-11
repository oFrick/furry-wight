

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

/**
 * Az alkalmazás ablaka
 * @author Kiss Dániel
 *
 */
public class MainFrame extends JFrame {
	
	public boolean debug = false;
	
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
	private JTextArea tartalom;
	private JButton ment;
	
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
		
		dll = new DLLFunctions(DLLFunctions.class.getProtectionDomain().getCodeSource().getLocation().getPath()+"filesystem.dll");
		dll.formatDisk(1024);
		
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
		
		/*
		dll.createFile("valami");
		dll.createDirectory("Ide");
		
		int handle = dll.fileOpen("valami");
		if(handle != 0){
			dll.changeDirectory("Ide");
			dll.fileCopy(1);
			System.out.println("Sikerült! "+handle);
		}else{
			System.out.println("Nem sikerült!");
		}
		*/
		
		//createDirectory("elso");
		//createFile("fileom");
		//createDirectory("dani");
		//changeDirectory("elsõ");
		//createFile("valami.txt");
		//changeDirectory("root/elsõ");
		//útvonalFejt("root/elsõ/valami.txt");
		//replace("root/fileom.txt","root/elsõ");
		
		//copy("root/fileom.txt","root/elsõ");
		createFile("dani");
		createDirectory("ide");
		replace("root/dani", "root/ide");
		
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
				if(selectedNode == null){
					selectedNode = rootElement;
					Seged.popup("Automatikus visszalépés a root könyvtárba!", "Vissza a root-ba!", sajat);
					return;
				}
				
				if(selected.getUserObject() instanceof Könyvtár){
					if(tartalmaz(selected)){
						changeDirectory(((Entitás)selected.getUserObject()).getNév());
						selectedNode = selected;
						System.out.println("Aktuális hely: "+((Entitás)getWorkingDirectory().getUserObject()).getNév());
					}else if(rootElement == selected){
						selectedNode = selected;
						changeDirectory("root");
						System.out.println("Vissza a gyökérbe!");
						System.out.println("Aktuális hely: "+((Entitás)getWorkingDirectory().getUserObject()).getNév());
					}else if(selected == ((DefaultMutableTreeNode)selectedNode.getParent())){
						//selectedNode = selected;
						System.out.println("Vissza egy szinttel!");
						changeDirectory("..");
						System.out.println("Aktuális hely: "+((Entitás)getWorkingDirectory().getUserObject()).getNév());
					}else if(selected != selectedNode){
						Seged.popup("Hibás mûvelet: nem lehet így mappát/fájlt kiválasztani!", "Hibás kijelölés", sajat);
						System.out.println("Aktuális hely: "+((Entitás)getWorkingDirectory().getUserObject()).getNév());
					}
					tartalom.setText("");
					tartalom.setEnabled(false);
					ment.setEnabled(false);
					
				}else{
					selectedNode = selected;
					byte[] b;
					if(debug){
						
						if(dll.fileOpen(((Entitás)selectedNode.getUserObject()).getNév()) != 0){
							
							b = dll.fileGetData(((Entitás)selectedNode.getUserObject()).getHandle());
							dll.fileClose(((Entitás)selectedNode.getUserObject()).getHandle());
							
							tartalom.setEnabled(true);
							tartalom.setText(new String(b));
							ment.setEnabled(true);
						}
						
					}
					
				}
				
				//selectedNode = selected;
				//changeDirectory(((Entitás)selectedNode.getUserObject()).getNév());
				
				
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
		
		tartalom = new JTextArea(10, 1);
		constraint.gridy = 4;
		constraint.weighty = 1;
		constraint.gridwidth = 4;
		panel.add(tartalom, constraint);
		
		ment = new JButton("Mentés");
		constraint.gridy = 5;
		constraint.gridwidth = 1;
		panel.add(ment, constraint);
		
		//Gomb eseménykezelõk
		
		ment.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(selectedNode.getUserObject() instanceof Fájl){
					dll.fileOpen(((Entitás)selectedNode.getUserObject()).getNév());
					dll.fileSetData(((Entitás)selectedNode.getUserObject()).getHandle(), tartalom.getText().getBytes());
					dll.fileClose(((Entitás)selectedNode.getUserObject()).getHandle());
				}
			}
		});
		
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
					//dll.deleteFile(ent.getNév());
					DefaultMutableTreeNode törlendõ = selectedNode;
					changeDirectory(".."); //Törlés után vissza a szülõbe!
					removeTreeNode(törlendõ);
					
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
		}	
		
		int handle = dll.fileOpen(név);
		System.out.println(név+" handle-je: "+handle+"!");
		fájl.setHandle(handle);
		dll.fileClose(handle);
	}
	
	public void createDirectory(String név){
		Könyvtár könyvtár = new Könyvtár(név,0);
		if(dll.createDirectory(név)){
			if(selectedNode == null) addTreeNode(new DefaultMutableTreeNode(könyvtár));
			else if(selectedNode.getUserObject() instanceof Könyvtár) addTreeNode(new DefaultMutableTreeNode(könyvtár), selectedNode);
		}		
		
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
	 * @param utvonal
	 */
	public void changeDirectory(String utvonal){
		StringTokenizer st = new StringTokenizer(utvonal,"/");
		while(st.hasMoreTokens()){
			String nev = st.nextToken();
			if(nev.equals("root")){ //Gyökérbe váltás
				selectedNode = rootElement;
				dll.changeDirectory(".");
			}else if(nev.equals("..")){ //Szülõ mappába váltás
				selectedNode = (DefaultMutableTreeNode) selectedNode.getParent();
				dll.changeDirectory("..");
			}else{ //Almappába váltás
				DefaultMutableTreeNode cel = tartalmaz(nev);
				if(cel != null){ //Ha van ilyen alkönyvtár
					if(cel.getUserObject() instanceof Könyvtár){ //Ha a cél egy könyvtár
						selectedNode = cel;
						dll.changeDirectory(nev);
						System.out.println("váltottam: "+nev+"!");
					}else Seged.popup("Ez nem könyvtár: "+nev+"!", "Nem könyvtár", this);
				} else{
					Seged.popup("Nincs ilyen könyvtár/alkönyvtár: "+nev+" itt: "+((Entitás)selectedNode.getUserObject()).getNév()+"!", "Sikertelen könyvtárváltás!", this);
					break;
				}
			}
		}
		
	}
	
	public void delete(){
		if(selectedNode != null) {
			Entitás ent = (Entitás)selectedNode.getUserObject();
			dll.deleteFile(ent.getNév());
			removeTreeNode(selectedNode);
			selectedNode = null; //Törlés után állítsuk null-ra a kiválasztott TreeNode-ot, különben BUG-ot kapunk!
		}
	}
	
	/**Átmásolja az elsõ paraméterben útvonalként megadott entitást ({@link Entitás}) a második paraméterben megadott helyre, ami szintén
	 * egy útvonal.
	 * @param honnan String
	 * @param hova String
	 */
	public void copy(String honnan, String hova){
		DefaultMutableTreeNode mit = útvonalFejt(honnan);
		DefaultMutableTreeNode mibe = útvonalFejt(hova);
			
		System.out.println(((Entitás)mit.getUserObject()).getNév());
		System.out.println(((Entitás)mibe.getUserObject()).getNév());
		
		DefaultMutableTreeNode uj = (DefaultMutableTreeNode) mit.clone();
			
		addTreeNode(uj, mibe);
	}
	
	public void replace(String honnan, String hova){
		DefaultMutableTreeNode mit = útvonalFejt(honnan);
		int handle = dll.fileOpen("dani");
		
		DefaultMutableTreeNode mibe = útvonalFejt(hova);
			
		System.out.println(((Entitás)mit.getUserObject()).getNév());
		System.out.println(((Entitás)mibe.getUserObject()).getNév());
		
		if(handle != 0){
			dll.fileMove(handle);
			dll.fileClose(handle);
			addTreeNode(mit, mibe);

		}else Seged.popup("Átnevezésnél rossz handle megadás!","Sikertelen átnevezés!",sajat);
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
}
