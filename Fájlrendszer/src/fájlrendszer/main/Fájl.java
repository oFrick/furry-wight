package fájlrendszer.main;

import javax.swing.tree.DefaultMutableTreeNode;

/**
 * A fájlrendszerben egy fájl reprezentálása.<br/><br/>
 * 
 * Minden fájlt a grafikus megjelenítéshez használt {@link DefaultMutableTreeNode} mezõje alapján azonosítunk.<br/>
 * Emiatt a fájlnak nincs név mezõje, a nevet az elõbb megadott DefaultMutableTreeNode tartalmazza.<br/><br/>
 * 
 * A fájl kiterjesztésének jelentése (kezelése) leginikább a UNIX rendszerekhez hasonlít, ahol a kiterjesztés és a fájl típusa két különbözõ fogalom<br/>
 * <b>Ezen osztály az egyes fájltípus osztályok õsosztálya.</b>
 * @author Kiss Dániel
 *
 */
public class Fájl {
	
	private Kiterjesztés kiterjesztés; //A fájl kiterjesztése
	private int méret; //A fájl mérete
	private String tartalom; //A fájl tartalma
	private DefaultMutableTreeNode grafikusNode;
	private DefaultMutableTreeNode szülõNode;
	
	public Fájl(DefaultMutableTreeNode grafikusNode, DefaultMutableTreeNode szülõNode){
		this.grafikusNode = grafikusNode;
		this.szülõNode = szülõNode;
		
		this.kiterjesztés = Kiterjesztés.DEFAULT;
	}
	
	public int getMéret() {return méret;}
	public String getNév() {return (String)grafikusNode.getUserObject();}
	public String getTartalom() {return tartalom;}
	
	public void setNév(String név){
		grafikusNode.setUserObject(név);
	}
	
	private void setMéret(String tartalom){
		
	}

}
