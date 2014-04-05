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
	
	private Fájltípus fájltípus; //A fájl kiterjesztése
	private String tartalom; //A fájl tartalma
	private DefaultMutableTreeNode grafikusNode;
	private DefaultMutableTreeNode szülõNode;
	
	private int méret; //A fájl mérete byte-ban
	private static final int defaultMéret=1; //A fájl tartalmának egy elemi részegységéhez (pl szövegben betû, képben pixel) tartozó byte-ban megadott méretszükséglet
	
	public Fájl(DefaultMutableTreeNode grafikusNode, DefaultMutableTreeNode szülõNode){
		this.grafikusNode = grafikusNode;
		this.szülõNode = szülõNode;
		
		this.fájltípus = Fájltípus.DEFAULT;
		
		System.out.println("Fájl létrehozva: "+grafikusNode.getUserObject()+", itt: "+szülõNode.getUserObject()); //Debug
		
	}
	
	public int getMéret() {return méret;}
	public String getNév() {return (String)grafikusNode.getUserObject();}
	public String getTartalom() {return tartalom;}
	
	public void setNév(String név){
		grafikusNode.setUserObject(név);
	}
	public void setTartalom(String tartalom){
		this.tartalom = tartalom;
	}
	
	/**
	 * A fájl méretének megváltoztatása.<br/><br/>
	 * A fájl tartalmának megfelelõen változtatja a méretet. Akkor lesz meghívva ez a metódus, ha változik a fájl tartalma.<br/>
	 * A tartalom mezõben lévõ minden egyes karakterrel növekszik/csökken a méret 1 byte-tal.
	 * @author Kiss Dániel
	 */
	private void setMéret(){
		méret = tartalom.length()*defaultMéret;
	}

}
