

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

import f�jlrendszer.gui.SajatTreeModell;
import f�jlrendszer.main.Entit�s;
import f�jlrendszer.main.F�jl;
import f�jlrendszer.main.K�nyvt�r;

/**
 * Az alkalmaz�s ablaka
 * @author Kiss D�niel
 *
 */
public class MainFrame extends JFrame {
	
	public boolean debug;
	
	//Men� mez�k
	private JMenuBar men�sor;
	private JMenu f�jlrendszerMen�;
	private JMenuItem �jF�ljrendszer;
	private JMenuItem bet�ltF�jlrendszer;
	private JMenuItem mentF�jlrendszer;
	private JMenuItem kil�p�s;
	
	//Panel mez�k
	private JPanel panel;
	private JTree explorer;
	private DefaultMutableTreeNode rootElement;
	private SajatTreeModell treemodel;
	private JButton t�rl�sGomb;
	private JButton �jF�jlGomb;
	private JButton �jMappaGomb;
	private JButton m�sol�sGomb;
	private JButton �thelyez�sGomb;
	private GridBagConstraints constraint; //Elhelyez�si "k�nyszer"
	private JLabel k�sz�t�sIdeje;
	private JLabel enged�lyek; //rwx|rwx|rwx alakban
	private JTextArea tartalom;
	private JButton ment;
	
	//F�jlrendszer mez�k
	private DefaultMutableTreeNode selectedNode; //A f�ban �ppen kiv�lasztott node
	
	private DLLFunctions dll;
	private MainFrame sajat;
	
	/**
	 * Az ablakot megval�s�t� oszt�ly. Tartalmazza az egyes men�ket, azok elemeit, illetve az ezekhez tartoz� esem�nykezel�ket.
	 * @param n�v - String, az ablak neve
	 */
	public MainFrame(String n�v){
		super(n�v);
		sajat = this;
		
		//Ablak inicializ�l�sa
		
		this.setSize(800, 600); //Ablak m�rete
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //Ablakbez�r�s esem�ny kezel�se
		
		dll = new DLLFunctions(DLLFunctions.class.getProtectionDomain().getCodeSource().getLocation().getPath()+"filesystem.dll");
		dll.formatDisk(32);
		
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
		/*
		createDirectory("els�");
		createFile("fileom.txt");
		changeDirectory("els�");
		createFile("valami.txt");
		*/
		
	}
	
	/**
	 * Elhelyez egy - a grafikus megjelen�t�shez haszn�lt - node-ot (ami lehet k�nyvt�r vagy mappa) a fa strukt�r�ban
	 * 
	 * @param what - {@link DefaultMutableTreeNode}, amit be szeretn�nk sz�rni/elhelyezni
	 * @param at - {@link DefaultMutableTreeNode}, ahov� el szeretn�nk helyezni
	 */
	public void addTreeNode(DefaultMutableTreeNode what, DefaultMutableTreeNode at){
		//at.add(what); ne ezt haszn�ljuk!
		treemodel.insertNodeInto(what, at, at.getChildCount());
	}
	
	/**
	 * Elhelyez egy - a grafikus megjelen�t�shez haszn�lt - node-ot a fa strukt�ra gy�ker�ben
	 * 
	 * @param what - {@link DefaultMutableTreeNode}, amit el szeretn�nk helyezni
	 */
	public void addTreeNode(DefaultMutableTreeNode what){
		addTreeNode(what, rootElement);
		
	}
	
	/**
	 * T�rli a f�b�l a grafikus megjelen�t�shez haszn�lt node-ot
	 * 
	 * @param what - {@link DefaultMutableTreeNode}, amit t�r�lni szeretn�nk
	 */
	public void removeTreeNode(DefaultMutableTreeNode what){
		treemodel.removeNodeFromParent(what);
	}
	
	/**
	 * A panel (azaz a grafikus fel�let) tartalm�nak bet�lt�se, be�ll�t�sa, az ottani esem�nyek kezel�se
	 */
	private void loadContent(){
		GridBagLayout layout = new GridBagLayout();
		constraint = new GridBagConstraints();
		constraint.fill = GridBagConstraints.HORIZONTAL; // Maxim�lis sz�less�g, sz�ks�ges magass�g
		
		panel = new JPanel(layout);
		setContentPane(panel);
		
		rootElement = new DefaultMutableTreeNode(new K�nyvt�r("root",0));
		selectedNode = rootElement; //Alap�rtelmez�s
		
		treemodel = new SajatTreeModell(rootElement);
		
		explorer = new JTree(treemodel);
		setupCellRenderer();
		JScrollPane treeView = new JScrollPane(explorer);
		explorer.setVisibleRowCount(15);
		
		//Fa esem�nykezel�j�nek be�ll�t�sa
		treemodel.addTreeModelListener((new TreeModelListener() {
			
			@Override
			public void treeStructureChanged(TreeModelEvent e) {
				System.out.println("Fa strukt�ra v�ltoz�s!");
				
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
				Entit�s ent = (Entit�s)selectedNode.getUserObject();
				//TODO Megval�s�tani a dll-ben!!!!! dll.rename(ent.getN�v());
			}
			
			
		}));
		
		treemodel.reload();
		
		explorer.setEditable(true);
		
		
		//Kiv�laszt�sos esem�nykezel�s be�ll�t�sa
		explorer.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);	
		explorer.addTreeSelectionListener(new TreeSelectionListener() {
			
			@Override
			public void valueChanged(TreeSelectionEvent arg0) {
				DefaultMutableTreeNode selected = (DefaultMutableTreeNode) explorer.getLastSelectedPathComponent(); //A kiv�lasztott TreeNode
				
				if(selected == null) return; //Ha nincs kiv�lasztva semmi, akkor kil�p�nk a f�ggv�nyb�l
				if(selectedNode == null){
					selectedNode = rootElement;
					Seged.popup("Automatikus visszal�p�s a root k�nyvt�rba!", "Vissza a root-ba!", sajat);
					return;
				}
				
				if(selected.getUserObject() instanceof K�nyvt�r){
					if(tartalmaz(selected)){
						changeDirectory(((Entit�s)selected.getUserObject()).getN�v());
						selectedNode = selected;
						System.out.println("Aktu�lis hely: "+((Entit�s)getWorkingDirectory().getUserObject()).getN�v());
					}else if(rootElement == selected){
						selectedNode = selected;
						changeDirectory("root");
						System.out.println("Vissza a gy�k�rbe!");
						System.out.println("Aktu�lis hely: "+((Entit�s)getWorkingDirectory().getUserObject()).getN�v());
					}else if(selected == ((DefaultMutableTreeNode)selectedNode.getParent())){
						//selectedNode = selected;
						System.out.println("Vissza egy szinttel!");
						changeDirectory("..");
						System.out.println("Aktu�lis hely: "+((Entit�s)getWorkingDirectory().getUserObject()).getN�v());
					}else if(selected != selectedNode){
						Seged.popup("Hib�s m�velet: nem lehet �gy mapp�t/f�jlt kiv�lasztani!", "Hib�s kijel�l�s", sajat);
						System.out.println("Aktu�lis hely: "+((Entit�s)getWorkingDirectory().getUserObject()).getN�v());
					}
					tartalom.setText("");
					tartalom.setEnabled(false);
					ment.setEnabled(false);
					
				}else{
					selectedNode = selected;
					byte[] b;
					if(debug){
						b = dll.fileGetData(((Entit�s)selectedNode.getUserObject()).getHandle());
						tartalom.setEnabled(true);
						tartalom.setText(new String(b));
						ment.setEnabled(true);
					}
					
				}
				
				//selectedNode = selected;
				//changeDirectory(((Entit�s)selectedNode.getUserObject()).getN�v());
				
				
				updateAttributes();
			}
			
		});
		
		constraint.gridwidth = 5; //H�rom oszlop sz�les
		constraint.gridx = 0; //0. sor
		constraint.gridy = 0; //0. oszlop�ban van
		constraint.weightx = 1;
		constraint.weighty = 1;
		panel.add(treeView, constraint);
		
		�jF�jlGomb = new JButton("�j F�jl");
		constraint.gridwidth = 1;
		constraint.weightx = 1;
		constraint.weighty = 0.5;
		constraint.gridx = 0;
		constraint.gridy = 1;
		panel.add(�jF�jlGomb, constraint);
		
		�jMappaGomb = new JButton("�j K�nyvt�r");
		constraint.gridwidth = 1;
		constraint.gridx = 1;
		constraint.gridy = 1;
		panel.add(�jMappaGomb, constraint);
		
		t�rl�sGomb = new JButton("T�rl�s");
		constraint.gridx = 2;
		constraint.gridy = 1;
		panel.add(t�rl�sGomb, constraint);
		
		m�sol�sGomb = new JButton("M�sol�s");
		constraint.gridx = 3;
		constraint.gridy = 1;
		panel.add(m�sol�sGomb, constraint);
		
		�thelyez�sGomb = new JButton("�thelyez�s");
		constraint.gridx = 4;
		constraint.gridy = 1;
		panel.add(�thelyez�sGomb, constraint);
		
		enged�lyek = new JLabel("Enged�lyek: ");
		//constraint.gridwidth = 5;
		constraint.weighty=0;
		constraint.gridx = 0;
		constraint.gridy = 2;
		panel.add(enged�lyek, constraint);
		
		k�sz�t�sIdeje = new JLabel("L�trehozva: ");
		constraint.weighty=0;
		constraint.gridx = 0;
		constraint.gridy = 3;
		panel.add(k�sz�t�sIdeje, constraint);
		
		tartalom = new JTextArea(10, 1);
		constraint.gridy = 4;
		constraint.weighty = 1;
		constraint.gridwidth = 4;
		panel.add(tartalom, constraint);
		
		ment = new JButton("Ment�s");
		constraint.gridy = 5;
		constraint.gridwidth = 1;
		panel.add(ment, constraint);
		
		//Gomb esem�nykezel�k
		
		ment.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(selectedNode.getUserObject() instanceof F�jl) dll.fileSetData(((Entit�s)selectedNode.getUserObject()).getHandle(), tartalom.getText().getBytes());
			}
		});
		
		�jF�jlGomb.addActionListener(new ActionListener() {
			
			//-----F�jl l�trehoz�s esem�ny-----
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO �j f�jl l�trehoz�sa - f�jl objektum p�ld�nyos�t�sa
				createFile(�jElemPopup("f�jl"));
				
			}
		});
		
		�jMappaGomb.addActionListener(new ActionListener() {
			
			//-----K�nyvt�r l�trehoz�s esem�ny-----
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO �j k�nyvt�r l�trehoz�sa - k�nyvt�r objektum p�ld�nyos�t�sa
				
				createDirectory(�jElemPopup("k�nyvt�r"));
				
			}
		});
		
		t�rl�sGomb.addActionListener(new ActionListener() {
			
			//-----F�jl t�rl�s esem�ny-----
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO F�jl t�rl�s esem�ny implement�l�sa a F�jl objektummal
				if(selectedNode != null) {
					Entit�s ent = (Entit�s)selectedNode.getUserObject();
					//dll.deleteFile(ent.getN�v());
					DefaultMutableTreeNode t�rlend� = selectedNode;
					changeDirectory(".."); //T�rl�s ut�n vissza a sz�l�be!
					removeTreeNode(t�rlend�);
					
				}
			}
		});
		
	}
	
	//--------------------------------------------------------------------------------------------------------------------
	
	/**
	 * A kiv�lasztott f�jl vagy mappa attrib�tumainak friss�t�se a grafikus fel�leten
	 * 
	 */
	private void updateAttributes(){
		Entit�s ent = (Entit�s)selectedNode.getUserObject();
		String jog = new String("Jogosults�gok: ");
		DateFormat d�tumForm�tum = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Calendar c = Calendar.getInstance();
		if(ent != null){
			if(ent.isFuttathat�()) jog+="r";
			else jog+="-";
			if(ent.is�rhat�()) jog+="w";
			else jog+="-";
			enged�lyek.setText(jog);
			k�sz�t�sIdeje.setText("L�trehozva: "+d�tumForm�tum.format(ent.getL�trehozva().getTime()));
		}
		else{
			enged�lyek.setText("Jogosults�gok: ");
			k�sz�t�sIdeje.setText("L�trehozva: ");
		}
		
	}
	
	/**
	 * Az ablak men�inek l�trehoz�sa �s bet�lt�se
	 * 
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
	
	private void setupCellRenderer(){
		explorer.setCellRenderer(new DefaultTreeCellRenderer() {
            private Icon f�jlIcon = this.getDefaultLeafIcon();
            private Icon mappaIcon = this.getDefaultClosedIcon();
            @Override
            public Component getTreeCellRendererComponent(JTree tree,
                    Object value, boolean selected, boolean expanded,
                    boolean isLeaf, int row, boolean focused) {
                Component c = super.getTreeCellRendererComponent(tree, value,
                        selected, expanded, isLeaf, row, focused);
                
                DefaultMutableTreeNode node = (DefaultMutableTreeNode) value;
                
                if (node.getUserObject() instanceof F�jl)
                    setIcon(f�jlIcon);
                else
                    setIcon(mappaIcon);
                return c;
            }
            
        });
		
	}
	
	private String �jElemPopup(String milyet){
		TextField sz�vegmez� = new TextField("�j elem");
		Object[] v�laszLehet�s�g = {"Adja meg a "+milyet+" nev�t", sz�vegmez�}; 
		Object[] v�laszGomb = {"L�trehoz�s", "M�gsem"};
		
		JOptionPane optionpane = new JOptionPane(v�laszLehet�s�g, JOptionPane.QUESTION_MESSAGE, JOptionPane.YES_NO_OPTION, null, v�laszGomb, v�laszGomb[0]);
		JDialog dialog = optionpane.createDialog(this, "�j "+milyet+" megad�s");
		dialog.show();
		
		return sz�vegmez�.getText();
		
	}
	/**
	 * Megadja, hogy melyik az �pp aktu�lis k�nyvt�r, amiben vagyunk. Ha �pp egy f�jl van
	 * kijel�lve, akkor a f�jlt tartalmaz� k�nyvt�rral t�r vissza, egy�bk�nt a kiv�lasztott k�nyvt�rral.
	 * @return
	 */
	private DefaultMutableTreeNode getWorkingDirectory(){
		
		if (selectedNode.getUserObject() instanceof K�nyvt�r) {
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
	
	/**Visszat�r a sztring nev� nodedal, ha az aktu�lis mappa tartalmazza azt a node-ot. Egy�bk�nt null a visz.�rt�k
	 * @param mit
	 * @return
	 */
	private DefaultMutableTreeNode tartalmaz(String mit){
		DefaultMutableTreeNode wDir = getWorkingDirectory();
		
		for(int i=0; i<wDir.getChildCount(); i++){
			Entit�s ent = (Entit�s) ((DefaultMutableTreeNode) wDir.getChildAt(i)).getUserObject();
			if(ent.getN�v().equals(mit)) return (DefaultMutableTreeNode) wDir.getChildAt(i);
		}
		return null;
	}
	
	/**Leellen�rzi, hogy a megadott node-ot tartalmazza-e az aktu�lis k�nyvt�r. Az aktu�lis k�nyvt�r az a k�nyvt�r grafikailag,
	 * ahol az utolj�ra kiv�lasztott node van. Ha ez a node k�nyvt�r, akkor ez az aktu�lis.
	 * @param mit
	 * @return
	 */
	private boolean tartalmaz(DefaultMutableTreeNode mit){
		if(tartalmaz(((Entit�s)mit.getUserObject()).getN�v()) != null) return true;
		else return false;
	}
	
	public void createFile(String n�v){
		F�jl f�jl = new F�jl(n�v,0);
		
		if(dll.createFile(f�jl.getN�v())){
			if(selectedNode == null) addTreeNode(new DefaultMutableTreeNode(f�jl));
			else if(selectedNode.getUserObject() instanceof K�nyvt�r) addTreeNode(new DefaultMutableTreeNode(f�jl), selectedNode);
		}	
		
		int handle = dll.fileOpen(n�v);
		f�jl.setHandle(handle);
		dll.fileClose(handle);
	}
	
	public void createDirectory(String n�v){
		K�nyvt�r k�nyvt�r = new K�nyvt�r(n�v,0);
		if(dll.createDirectory(k�nyvt�r.getN�v())){
			if(selectedNode == null) addTreeNode(new DefaultMutableTreeNode(k�nyvt�r));
			else if(selectedNode.getUserObject() instanceof K�nyvt�r) addTreeNode(new DefaultMutableTreeNode(k�nyvt�r), selectedNode);
		}		
		
	}
	
	/**A jelenlegi k�nyvt�rban l�v�, adott nev� elemet �tnevezi. Csak parancs�rtelemz�vel kell ezt haszn�lni.
	 * @param mit
	 * @param mire
	 */
	public void rename(String mit, String mire){
		
		DefaultMutableTreeNode wDir = getWorkingDirectory(); //Aktu�lis munkak�nyvt�r
		boolean siker = false;
			
		for(int i=0; i<wDir.getChildCount(); i++){
			Entit�s ent = (Entit�s) ((DefaultMutableTreeNode) wDir.getChildAt(i)).getUserObject();
			if(ent.getN�v() == mit){
				ent.setN�v(mire);
				siker = true;
				break;
			}
		}
		
		if(!siker) Seged.popup("Nincs ilyen k�nyt�r/f�jl: "+mit+"!", "�tnevez�s sikertelen!", this);
	}
	
	/**�tv�ltja az aktu�lis k�nyvt�rat a megadott �tvonalnak megfelel� k�nyvt�rba.
	 * @param utvonal
	 */
	public void changeDirectory(String utvonal){
		StringTokenizer st = new StringTokenizer(utvonal,"/");
		while(st.hasMoreTokens()){
			String nev = st.nextToken();
			if(nev.equals("root")){ //Gy�k�rbe v�lt�s
				selectedNode = rootElement;
				dll.changeDirectory(".");
			}else if(nev.equals("..")){ //Sz�l� mapp�ba v�lt�s
				selectedNode = (DefaultMutableTreeNode) selectedNode.getParent();
				dll.changeDirectory("..");
			}else{ //Almapp�ba v�lt�s
				DefaultMutableTreeNode cel = tartalmaz(nev);
				if(cel != null){ //Ha van ilyen alk�nyvt�r
					if(cel.getUserObject() instanceof K�nyvt�r){ //Ha a c�l egy k�nyvt�r
						selectedNode = cel;
						dll.changeDirectory(nev);
						System.out.println("v�ltottam: "+nev+"!");
					}else Seged.popup("Ez nem k�nyvt�r: "+nev+"!", "Nem k�nyvt�r", this);
				} else{
					Seged.popup("Nincs ilyen k�nyvt�r/alk�nyvt�r: "+nev+" itt: "+((Entit�s)selectedNode.getUserObject()).getN�v()+"!", "Sikertelen k�nyvt�rv�lt�s!", this);
					break;
				}
			}
		}
		
	}
	
	public void delete(){
		if(selectedNode != null) {
			Entit�s ent = (Entit�s)selectedNode.getUserObject();
			dll.deleteFile(ent.getN�v());
			removeTreeNode(selectedNode);
			selectedNode = null; //T�rl�s ut�n �ll�tsuk null-ra a kiv�lasztott TreeNode-ot, k�l�nben BUG-ot kapunk!
		}
	}
}