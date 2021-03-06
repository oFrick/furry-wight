

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.StringTokenizer;

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
import javax.swing.tree.TreeSelectionModel;

import f�jlrendszer.gui.SajatTreeModell;
import f�jlrendszer.main.Entit�s;
import f�jlrendszer.main.F�jl;
import f�jlrendszer.main.K�nyvt�r;
import f�jlrendszer.titkos�t�s.K�pTitkos�t�;
import f�jlrendszer.titkos�t�s.Titkos�t�;
import f�jlrendszer.titkos�t�s.titkos�t�Algoritmus.Egyszer�K�pAlgoritmus;
import f�jlrendszer.titkos�t�s.titkos�t�Algoritmus.Eltol�Algoritmus;

/**
 * Az alkalmaz�s ablaka
 * @author Kiss D�niel
 *
 */
public class MainFrame extends JFrame {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2682758755048073588L;

	public boolean debug = true;
	
	//Men� mez�k
	private JMenuBar men�sor;
	private JMenu f�jlrendszerMen�;
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
	private JLabel kijel�lt;
	private JTextArea tartalom;
	private JButton ment;
	private JCheckBox �rhat�;
	private JCheckBox futtathat�;
	private JCheckBox titkos�tott;
	private JCheckBox rejtett;
	
	private JMenu egy�bMen�;
	private JMenuItem parancsMen�Elem;
	private JMenu titkos�t�s;
	private JMenu szimplaTitkos�t�;
		private JMenuItem szimplaK�dol�;
		private JMenuItem szimplaDek�dol�;
	private JMenu k�pTitkos�t�;
		private JMenuItem k�pK�dol�;
		private JMenuItem k�pDek�dol�;
	
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
		String path = DLLFunctions.class.getProtectionDomain().getCodeSource().getLocation().getPath()+"filesystem.dll";
		File tmp = new File("filesystem.dll");
		System.out.println(tmp.getAbsolutePath());
		dll = new DLLFunctions(tmp.getAbsolutePath());
		dll.formatDisk(64);
		
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
		
		//TODO �j f�jlrendszer l�trehoz�st implement�lni ehelyett
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
				if(ent instanceof F�jl) renameGui(ent.getOldName(), ent.getN�v(), true);
				else renameGui(ent.getOldName(), ent.getN�v(), false);
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
					}else if(rootElement == selected){
						selectedNode = selected;
						changeDirectory("root");
					}else if(selected == ((DefaultMutableTreeNode)selectedNode.getParent())){
						//selectedNode = selected;
						changeDirectory("..");
					}else if(selected != selectedNode){
						Seged.popup("Hib�s m�velet: nem lehet �gy mapp�t/f�jlt kiv�lasztani!", "Hib�s kijel�l�s", sajat);
					}
					tartalom.setText("");
					tartalom.setEnabled(false);
					ment.setEnabled(true);
					
				}else{
					
					if(!tartalmaz(selected)){
						Seged.popup("Hib�s m�velet: nem lehet �gy mapp�t/f�jlt kiv�lasztani!", "Hib�s kijel�l�s", sajat);
					}else{
						selectedNode = selected;
						
						String adat = dataGet();
						if(adat != null){
							tartalom.setEnabled(true);
							tartalom.setText(adat);
							ment.setEnabled(true);
						}else Seged.popup("HIBA. F�jlb�l olvas�skor gond van!", "F�jl megnyit�s sikertelen", sajat);
					}
				}
				
				//Jogosults�gok megjelen�t�se
				if(((Entit�s)selected.getUserObject()).isFuttathat�()) futtathat�.setSelected(true);
				else futtathat�.setSelected(false);
				if(((Entit�s)selected.getUserObject()).is�rhat�()) �rhat�.setSelected(true);
				else �rhat�.setSelected(false);
				if(((Entit�s)selected.getUserObject()).isTitkos�tott()) titkos�tott.setSelected(true);
				else titkos�tott.setSelected(false);
				if(((Entit�s)selected.getUserObject()).isRejtett()) rejtett.setSelected(true);
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
		
		k�sz�t�sIdeje = new JLabel("L�trehozva: ");
		constraint.weighty=0;
		constraint.gridx = 0;
		constraint.gridy = 2;
		panel.add(k�sz�t�sIdeje, constraint);
		
		kijel�lt = new JLabel("Kijel�lt elem: ");
		constraint.gridx = 1;
		panel.add(kijel�lt, constraint);
		
		tartalom = new JTextArea(10, 1);
		constraint.gridy = 3;
		constraint.gridx = 0;
		constraint.weighty = 1;
		constraint.gridwidth = 4;
		panel.add(tartalom, constraint);
		
		ment = new JButton("Ment�s");
		constraint.gridy = 4;
		constraint.gridwidth = 1;
		panel.add(ment, constraint);
		
		futtathat� = new JCheckBox();
		constraint.gridy = 5;
		constraint.gridx = 1;
		panel.add(futtathat�, constraint);
		
		�rhat� = new JCheckBox();
		constraint.gridx = 2;
		panel.add(�rhat�, constraint);
		
		titkos�tott = new JCheckBox();
		constraint.gridx = 3;
		panel.add(titkos�tott, constraint);
		
		rejtett = new JCheckBox();
		constraint.gridx = 4;
		panel.add(rejtett, constraint);
		
		JLabel jogok = new JLabel("Jogosults�gok(X-W-E-H)");	
		constraint.gridx = 5;
		panel.add(jogok, constraint);
		//Gomb esem�nykezel�k
		
		m�sol�sGomb.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				String innen = Seged.inputPopup("Adja meg a forr�st a m�sol�shoz!", "forr�s", "M�sol�s innen", sajat);
				String ezt = Seged.inputPopup("Adja meg a c�lt az m�sol�shoz!", "c�l", "M�sol�s ide", sajat);
				copy(innen, ezt);
			}
		});
		
		�thelyez�sGomb.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				String innen = Seged.inputPopup("Adja meg a forr�st az �thelyez�shez!", "forr�s", "�thelyez�s innen", sajat);
				String ezt = Seged.inputPopup("Adja meg a c�lt az �thelyez�shez!", "c�l", "�thelyez�s ide", sajat);
				replace(innen, ezt);
				
			}
		});
		
		ment.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				Entit�s ent = ((Entit�s)selectedNode.getUserObject());
				ent.setFuttathat�(futtathat�.isSelected());
				ent.set�rhat�(�rhat�.isSelected());
				ent.setTitkos�tott(titkos�tott.isSelected());
				ent.setRejtett(rejtett.isSelected());
				
				
				dataSet(ent);
			}
		});
		
		�jF�jlGomb.addActionListener(new ActionListener() {
			
			//-----F�jl l�trehoz�s esem�ny-----
			@Override
			public void actionPerformed(ActionEvent e) {
				createFile(�jElemPopup("f�jl"));
				
			}
		});
		
		�jMappaGomb.addActionListener(new ActionListener() {
			
			//-----K�nyvt�r l�trehoz�s esem�ny-----
			@Override
			public void actionPerformed(ActionEvent e) {
				
				createDirectory(�jElemPopup("k�nyvt�r"));
				
			}
		});
		
		t�rl�sGomb.addActionListener(new ActionListener() {
			
			//-----F�jl t�rl�s esem�ny-----
			@Override
			public void actionPerformed(ActionEvent arg0) {
				/*
				if(selectedNode != null) {
					Entit�s ent = (Entit�s)selectedNode.getUserObject();
					dll.deleteFile(ent.getN�v());
					DefaultMutableTreeNode t�rlend� = selectedNode;
					changeDirectory(".."); //T�rl�s ut�n vissza a sz�l�be!
					removeTreeNode(t�rlend�);
					
				}else Seged.popup("Nincs mit t�r�lni!", "T�rl�s sikertelen!", sajat);
				*/
				
				Entit�s ent = (Entit�s)selectedNode.getUserObject();
				if(ent instanceof F�jl) deleteGui(ent.getN�v(), true);
				else deleteGui(ent.getN�v(), false);
			}
		});
		
	}
	
	//--------------------------------------------------------------------------------------------------------------------
	
	private void �jF�jlrendszer(){
	//TODO implement�lni
		rootElement = new DefaultMutableTreeNode(new K�nyvt�r("root",0));
		selectedNode = rootElement; //Alap�rtelmez�s
		
		treemodel = new SajatTreeModell(rootElement);
		
		explorer = new JTree(treemodel);
	}
	
	/**
	 * A kiv�lasztott f�jl vagy mappa attrib�tumainak friss�t�se a grafikus fel�leten
	 * 
	 */
	private void updateAttributes(){
		Entit�s ent = (Entit�s)selectedNode.getUserObject();
		DateFormat d�tumForm�tum = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		if(ent != null){
			k�sz�t�sIdeje.setText("L�trehozva: "+d�tumForm�tum.format(ent.getL�trehozva().getTime()));
			kijel�lt.setText("Kijel�lt elem: "+((Entit�s)selectedNode.getUserObject()).getN�v());
		}
		else{
			k�sz�t�sIdeje.setText("L�trehozva: ");
			kijel�lt.setText("Kijel�lt elem: ");
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
		kil�p�s = new JMenuItem("Kil�p�s");
		
		//Objektumok "elhejez�se"
		this.setJMenuBar(men�sor);
		men�sor.add(f�jlrendszerMen�);
		f�jlrendszerMen�.add(kil�p�s);
		
		egy�bMen� = new JMenu("Egy�b");
		men�sor.add(egy�bMen�);
		parancsMen�Elem = new JMenuItem("Parancs�rtelmez�");
		egy�bMen�.add(parancsMen�Elem);
		
		titkos�t�s = new JMenu("Titkos�t�s");
		egy�bMen�.add(titkos�t�s);
		szimplaTitkos�t� = new JMenu("Egyszer� titkos�t�s");
		titkos�t�s.add(szimplaTitkos�t�);
		szimplaK�dol� = new JMenuItem("K�dol�s");
		szimplaTitkos�t�.add(szimplaK�dol�);
		szimplaDek�dol� = new JMenuItem("Dek�dol�s");
		szimplaTitkos�t�.add(szimplaDek�dol�);
		k�pTitkos�t� = new JMenu("K�p alap� k�dol�s");
		titkos�t�s.add(k�pTitkos�t�);
		k�pK�dol� = new JMenuItem("K�dol�s");
		k�pTitkos�t�.add(k�pK�dol�);
		k�pDek�dol� = new JMenuItem("Dek�dol�s");
		k�pTitkos�t�.add(k�pDek�dol�);
		
		
		parancsMen�Elem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				String parancs = Seged.inputPopup("Adja meg a parancsot:", "parancs", "Parancs�rtelmez�", sajat);
				Shell shell = new Shell(sajat);
				shell.bemenet(parancs);
								
			}
		});
		
		szimplaK�dol�.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				String kulcs = Seged.inputPopup("Titkos�t�s kulcsa:", "kulcs", "Egyszer� k�dol�s", sajat);
				Titkos�t� t;
				
				if(!kulcs.isEmpty()){
					t = new Titkos�t�(kulcs, new Eltol�Algoritmus());
					
					dataSet(((Entit�s)selectedNode.getUserObject()), t.titkos�t(dataGet()));
					

				}else Seged.popup("Meg kell adni egy kulcsot!",  "Sikertelen k�dol�s", sajat);
				
			}
		});
		
		szimplaDek�dol�.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				String kulcs = Seged.inputPopup("Titkos�t�s kulcsa:", "kulcs", "Egyszer� k�dol�s", sajat);
				Titkos�t� t;
				
				if(!kulcs.isEmpty()){
					t = new Titkos�t�(kulcs, new Eltol�Algoritmus());
					dataSet(((Entit�s)selectedNode.getUserObject()), t.visszanyer(dataGet()));
					
				}else Seged.popup("Meg kell adni egy kulcsot!",  "Sikertelen dek�dol�s", sajat);
				
			}
		});
		
		k�pK�dol�.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				String �tvonal = Seged.inputPopup("Titkos�t� k�p �tvonala:", "kulcs", "Egyszer� k�dol�s", sajat);
				int n = Integer.parseInt(Seged.inputPopup("K�ppontok darabsz�ma:", "50", "K�ppontsz�m", sajat));
				if(n>=0 && n<90000000){
					try {
						
						BufferedImage kulcs = ImageIO.read(new File(�tvonal));
						K�pTitkos�t� t = new K�pTitkos�t�(kulcs, new Egyszer�K�pAlgoritmus(15));
						
						dataSet(((Entit�s)selectedNode.getUserObject()), t.titkos�t(dataGet()));
						
					} catch (IOException e) {
						Seged.popup("Nincs ilyen f�jl!", "Sikertelen titkos�t�s!", sajat);
					}
				}else Seged.popup("pixelsz�m hib�s megad�s", "Sikertelen titkos�t�s", sajat);
			}
		});
		
		k�pDek�dol�.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				String �tvonal = Seged.inputPopup("Titkos�t� k�p �tvonala:", "kulcs", "Egyszer� k�dol�s", sajat);
				int n = Integer.parseInt(Seged.inputPopup("K�ppontok darabsz�ma:", "50", "K�ppontsz�m", sajat));
				if(n>=0 && n<90000000){
					try {
						
						BufferedImage kulcs = ImageIO.read(new File(�tvonal));
						K�pTitkos�t� t = new K�pTitkos�t�(kulcs, new Egyszer�K�pAlgoritmus(15));
						
						dataSet(((Entit�s)selectedNode.getUserObject()), t.visszanyer(dataGet()));
						
					} catch (IOException e) {
						Seged.popup("Nincs ilyen f�jl!", "Sikertelen titkos�t�s!", sajat);
					}
				}else Seged.popup("pixelsz�m hib�s megad�s", "Sikertelen titkos�t�s", sajat);
				
			}
		});
		
	}
	
	/**Be�ll�tja a fa rajzol�s�hoz sz�ks�ges param�tereket
	 */
	private void setupCellRenderer(){
		explorer.setCellRenderer(new DefaultTreeCellRenderer() {
			private static final long serialVersionUID = 6307850244018307488L;
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
	
	/**�j f�jl/mapp l�trehoz�s�ra haszn�latos felugr� ablakot hoz l�tre
	 * @param milyet
	 * @return String
	 */
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
	 * @return {@link DefaultMutableTreeNode}
	 */
	private DefaultMutableTreeNode getWorkingDirectory(){
		
		if (selectedNode.getUserObject() instanceof K�nyvt�r) {
			return selectedNode;
		}
		else{
			return (DefaultMutableTreeNode) selectedNode.getParent();
		}
		
	}
	
	/**Visszat�r a sztring nev� nodedal, ha az aktu�lis mappa tartalmazza azt a node-ot. Egy�bk�nt null a visz.�rt�k
	 * @param mit
	 * @return {@link DefaultMutableTreeNode}
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
	 * @return boolean
	 */
	private boolean tartalmaz(DefaultMutableTreeNode mit){
		if(tartalmaz(((Entit�s)mit.getUserObject()).getN�v()) != null) return true;
		else return false;
	}
	
	/**Megadott n�vvel l�trehoz egy f�jlt az aktu�lis k�nyvt�rban
	 * @param n�v
	 */
	public void createFile(String n�v){
		F�jl f�jl = new F�jl(n�v,0);
		
		if(dll.createFile(f�jl.getN�v())){
			if(selectedNode == null) addTreeNode(new DefaultMutableTreeNode(f�jl));
			else if(selectedNode.getUserObject() instanceof K�nyvt�r) addTreeNode(new DefaultMutableTreeNode(f�jl), selectedNode);
		}else Seged.popup("Hiba a l�trehoz�skor!", "Sikertelen f�jl l�trehoz�s", this);	
		
		int handle = dll.fileOpen(n�v);
		f�jl.setHandle(handle);
		dll.fileClose(handle);
	}
	
	/**Megadott n�vvel l�trehoz egy k�nyvt�rat az aktu�lis k�nyvt�rban
	 * @param n�v
	 */
	public void createDirectory(String n�v){
		K�nyvt�r k�nyvt�r = new K�nyvt�r(n�v,0);
		if(dll.createDirectory(n�v)){
			if(selectedNode == null) addTreeNode(new DefaultMutableTreeNode(k�nyvt�r));
			else if(selectedNode.getUserObject() instanceof K�nyvt�r) addTreeNode(new DefaultMutableTreeNode(k�nyvt�r), selectedNode);
		}else Seged.popup("Hiba a l�trehoz�skor!", "Sikertelen k�nyvt�r l�trehoz�s", this);		
		
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
			if(ent.getN�v().equals(mit) || ent.getOldName().equals(mit)){
				if(dll.renameFile(mit, mire)){
					if(!ent.getN�v().equals(mire)) ent.setN�v(mire);
					siker = true;
				}
				break;
			}
		}
		
		if(!siker) Seged.popup("Nincs ilyen k�nyt�r/f�jl: "+mit+"!", "�tnevez�s sikertelen!", this);
	}
	
	/**�tnevezi az els� param�terben kapott elemet a m�sodik param�terben kapott n�vre. A harmadik param�terben
	 * meg kell adni, hogy az �tnevezend� dolog f�jl-e. Ez fontos, ugyanis a met�dus nem foglalkozik azzal, hogy ez val�ban az-e.
	 * @param mit
	 * @param mire
	 * @param isF�jl
	 */
	public void renameGui(String mit, String mire, boolean isF�jl){
		if(!isF�jl) changeDirectory("..");
		rename(mit, mire);
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
				updateAttributes();
			}else if(nev.equals("..")){ //Sz�l� mapp�ba v�lt�s
				if(selectedNode != rootElement){
					selectedNode = (DefaultMutableTreeNode) selectedNode.getParent();
					dll.changeDirectory("..");
					updateAttributes();
				}else Seged.popup("A root-n�l nem lehet feljebb l�pni!", "Sikertelen k�nyvt�r v�lt�s", sajat);
			}else{ //Almapp�ba v�lt�s
				DefaultMutableTreeNode cel = tartalmaz(nev);
				if(cel != null){ //Ha van ilyen alk�nyvt�r
					if(cel.getUserObject() instanceof K�nyvt�r){ //Ha a c�l egy k�nyvt�r
						selectedNode = cel;
						dll.changeDirectory(nev);
						System.out.println("v�ltottam: "+nev+"!");
						updateAttributes();
					}else Seged.popup("Ez nem k�nyvt�r: "+nev+"!", "Nem k�nyvt�r", this);
				} else{
					Seged.popup("Nincs ilyen k�nyvt�r/alk�nyvt�r: "+nev+" itt: "+((Entit�s)selectedNode.getUserObject()).getN�v()+"!", "Sikertelen k�nyvt�rv�lt�s!", this);
					break;
				}
			}
		}
		
	}
	
	/**T�rli az aktu�lis k�nyvt�rban l�v� f�jlt/mapp�t
	 * @param mit
	 */
	public void delete(String mit){
		if(selectedNode != null) {
			DefaultMutableTreeNode node = tartalmaz(mit);
			if(node == null) node = getWorkingDirectory();
			if(node != null){
				if(dll.deleteFile(((Entit�s)node.getUserObject()).getN�v())){
					removeTreeNode(node);
					if(getWorkingDirectory() != rootElement) changeDirectory("..");
					node = null;
				}else Seged.popup("Sikertelen t�rl�s. A f�jlrendszerben ilyen f�jl/mappa nem tal�lhat�!", "Sikertelen t�rl�s", this);
				
			}else Seged.popup("Nincs milyen f�jl/mappa!", "Sikertelen t�rl�s", this);
		}
	}
	
	/**A t�rl�s grafikai �ton t�rt�n� megval�s�t�s�t v�gz� met�dus
	 * @param mit
	 * @param isF�jl
	 */
	public void deleteGui(String mit, boolean isF�jl){
		if(!isF�jl)changeDirectory("..");
		delete(mit);
	}
	
	/**�tm�solja az els� param�terben �tvonalk�nt megadott entit�st ({@link Entit�s}) a m�sodik param�terben megadott helyre, ami szint�n
	 * egy �tvonal.
	 * @param honnan String
	 * @param hova String
	 */
	public void copy(String honnan, String hova){
		/*
		DefaultMutableTreeNode mit = �tvonalFejt(honnan);
		DefaultMutableTreeNode mibe = �tvonalFejt(hova);
			
		System.out.println(((Entit�s)mit.getUserObject()).getN�v());
		System.out.println(((Entit�s)mibe.getUserObject()).getN�v());
		
		DefaultMutableTreeNode uj = (DefaultMutableTreeNode) mit.clone();
			
		addTreeNode(uj, mibe);
		*/
		
		DefaultMutableTreeNode mit = �tvonalFejt(honnan);
		
		if(mit != null){
			if(mit.getUserObject() instanceof K�nyvt�r){
				Seged.popup("Csak f�jlt lehet alacsony szinten m�solni!", "Sikertelen m�sol�s!", sajat);
				return;
			}
			
			int handle = dll.fileOpen(honnan);
			
			DefaultMutableTreeNode mibe;
			
			if(hova.equals("..") && selectedNode != rootElement){
				mibe = (DefaultMutableTreeNode)selectedNode.getParent();
			}else if(hova.equals("root")){
				mibe = rootElement;
			}else{
				mibe = �tvonalFejt(hova);
			}
			
			if(mibe != null){
				if(handle != 0){
					dll.fileCopy(handle);
					dll.fileClose(handle);
					
					DefaultMutableTreeNode uj = (DefaultMutableTreeNode) mit.clone();
					addTreeNode(uj, mibe);

				}else Seged.popup("M�sol�sn�l rossz handle megad�s!","Sikertelen m�sol�s!",sajat);
			}else if(handle != 0) dll.fileClose(handle);
		}
	}
	
	/**�thelyezi a f�jlt/mapp�t, amit az els� param�terben kap a m�sodik param�terben kapott helyre.
	 * @param honnan
	 * @param hova
	 */
	public void replace(String honnan, String hova){
		
		int handle = dll.fileOpen(honnan);
		if(handle != 0){
			DefaultMutableTreeNode wDir = getWorkingDirectory();
			DefaultMutableTreeNode mit = �tvonalFejt(honnan);
			DefaultMutableTreeNode mibe;
			
			if(wDir != getWorkingDirectory()) changeDirectory(".."); //Mert az el�z� �tvonalfejt�s bedobott a "honnan" mapp�ba
			
			if(hova.equals("..") && selectedNode != rootElement){
				mibe = (DefaultMutableTreeNode)selectedNode.getParent();
			}else if(hova.equals("root")){
				mibe = rootElement;
			}else{
				mibe = �tvonalFejt(hova);
			}
			
			
				
			
			if(handle != 0 && mit != null && mibe != null){
				dll.fileMove(handle);
				dll.fileClose(handle);
				addTreeNode(mit, mibe);

			}else if(handle != 0){
				dll.fileClose(handle);
			}else Seged.popup("Nem tal�lom a handle-t vagy helytelen forr�s/c�l megad�s!","Sikertelen �thelyez�s!",sajat);
		}
		
	}
	
	/**Visszat�r az adoutt �tvonal v�g�n l�v� f�jl/k�nyvt�rral. Az aktu�lis mapp�t a megfelel� helyre mozgatja, azaz ahol az �tvonalnak v�ge van
	 * @param �tvonal
	 * @return {@link DefaultMutableTreeNode}
	 */
	private DefaultMutableTreeNode �tvonalFejt(String �tvonal){
		
		String[] darabok = �tvonal.split("/");
		String cim = new String();
		
		for(int i=0; i<darabok.length-1; i++){
			cim+=darabok[i]+"/";
		}
		String utolso = darabok[darabok.length-1];
		changeDirectory(cim);
		
		DefaultMutableTreeNode node = tartalmaz(utolso);
		if(node != null){//Ha l�tezik az utols� elem
			if(node.getUserObject() instanceof K�nyvt�r) changeDirectory(utolso);
			return node;
		}
		
		return null;
	}
	
	/**A f�jl tartalm�t kiolvassa �s visszat�r vele
	 * @return String
	 */
	private String dataGet(){
		byte[] b;
		
		int handle = dll.fileOpen(((Entit�s)selectedNode.getUserObject()).getN�v());
		if(handle != 0){
			b = dll.fileGetData(handle);
			dll.fileClose(handle);
			
			try {
				return new String(b, "UTF8");
			} catch (UnsupportedEncodingException e) {
				System.err.println("Hiba a f�jlb�l olvas�skor a karakterk�dol�ssal!");
				System.err.println(e.getMessage());
			}
		}
		return null;
	}
	
	/**A f�jl tartalm�t a beviteli sz�vegdoboznak megfelel�en fel�l�rja. A teljes tartalom �jra�r�dik.
	 * @param ent - {@link Entit�s} A f�jl grafikus oszt�lya(�soszt�ly)
	 */
	private void dataSet(Entit�s ent){
		dataSet(ent, tartalom.getText());
	}
	
	/**Az els� param�terben k�pviselt f�jl tartalm�t a m�sodik param�ternek megfelel�en fel�l�rja.
	 * @param ent
	 * @param str
	 */
	private void dataSet(Entit�s ent, String str){
		if(ent instanceof F�jl){
			int handle = dll.fileOpen(ent.getN�v());
			if(handle != 0){
				try {
					dll.fileSetData(handle, str.getBytes("UTF8"));
				} catch (UnsupportedEncodingException e) {
					System.err.println("Hiba a f�jba �r�skor a karakterk�dol�ssal!");
					System.err.println(e.getMessage());
				}
				dll.fileSetReadPermission(handle, ent.isFuttathat�());
				dll.fileSetWritePermission(handle, ent.is�rhat�());
				dll.fileSetEncryption(handle, ent.isTitkos�tott());
				dll.fileSetEncryption(handle, ent.isRejtett());
				dll.fileClose(handle);
				
			}else Seged.popup("Nem tudok a f�jlba �rni! Nem tal�lom a handle-t.", "Sikertelen f�jlba �r�s!", sajat);
			
		}
	}
}
