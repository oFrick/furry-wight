package fájlrendszer.main;

import java.util.List;

import javax.swing.tree.DefaultMutableTreeNode;

/**
 * A fájlok/mappák tárolására, a merevlemez funkcióinak magas szinten történõ megvalósítására szolgáló osztály.
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
	 * @author Kiss Dániel
	 * @param rootNode - {@link DefaultMutableTreeNode}, a fa root grafikus eleme
	 */
	public Fájlrendszer(DefaultMutableTreeNode rootNode){
		this(rootNode, true);
	}
	
	/**
	 * Konstruktor, melyben eldönthetõ, hogy meglévõ, vagy új fájlrendszerrel szeretnénk dolgozni.<br/>
	 * Meglévõ esetében fájlból be kell tölteni a fájlrendszert
	 * @author Kiss Dániel
	 * @param rootNode - {@link DefaultMutableTreeNode}, a fa root grafikus eleme
	 * @param isÚjfájlrendszer - boolean, új fájlrendszert szeretnénk-e?
	 */
	public Fájlrendszer(DefaultMutableTreeNode rootNode, boolean isÚjfájlrendszer){
		if (isÚjfájlrendszer){
			root = new Könyvtár(rootNode);
			könyvtárLemezre(root);
		}else{
			root = könyvtárLemezrõl();
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

}
