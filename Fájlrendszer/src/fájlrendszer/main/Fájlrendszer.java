package fájlrendszer.main;

import java.util.ArrayList;
import java.util.List;

import javax.swing.tree.DefaultMutableTreeNode;

/**
 * A fájlok/mappák tárolására, a merevlemez funkcióinak magas szinten történõ megvalósítására szolgáló osztály.<br>
 * A fájlokat/mappákat csak tároljuk, elérésük a {@link DefaultMutableTreeNode} típusú adattagjukon keresztül történik közvetlenül
 * a GUI-t megvalósító osztályokból
 * @author Kiss Dániel
 *
 */
public class Fájlrendszer {
	
	private List<Fájl> fájlok;
	private List<Könyvtár> mappák;
	private Könyvtár root;
	
	/**
	 * Konstruktor.<br/><br/>
	 * 
	 * Egy teljesen új fájlrendszert készít
	 * 
	 * @param rootNode - {@link DefaultMutableTreeNode}, a fa root grafikus eleme
	 */
	public Fájlrendszer(DefaultMutableTreeNode rootNode){
		this(rootNode, true);
	}
	
	/**
	 * Konstruktor, melyben eldönthetõ, hogy meglévõ, vagy új fájlrendszerrel szeretnénk dolgozni.<br/>
	 * Meglévõ esetében fájlból be kell tölteni a fájlrendszert
	 * 
	 * @param rootNode - {@link DefaultMutableTreeNode}, a fa root grafikus eleme
	 * @param isÚjfájlrendszer - boolean, új fájlrendszert szeretnénk-e?
	 */
	public Fájlrendszer(DefaultMutableTreeNode rootNode, boolean isÚjfájlrendszer){
		fájlok = new ArrayList<Fájl>();
		mappák = new ArrayList<Könyvtár>();
		
		if (isÚjfájlrendszer){
			root = new Könyvtár(rootNode.getUserObject().toString());
			mappák.add(root);
			könyvtárLemezre(root);
		}else{
			root = könyvtárLemezrõl();
			mappák.add(root);
		}
	}
	
	public Könyvtár könyvtárLemezrõl(){
		return null;
	}
	
	public void könyvtárLemezre(Könyvtár könyvtár){
		
	}
	
	public Fájl fájlLemezrõl(){
		return null;
	}
	
	public void fájlLemezre(Fájl fájl){
		
	}
	
	public void add(Entitás elem){
		if(elem instanceof Fájl) fájlok.add((Fájl)elem);
		else mappák.add((Könyvtár)elem);
	}
	
	public void remove(Entitás elem){
		if(elem instanceof Fájl) fájlok.remove((Fájl)elem);
		else mappák.remove((Könyvtár)elem);
	}
	
	public Könyvtár getRoot(){
		return root;
	}

}
