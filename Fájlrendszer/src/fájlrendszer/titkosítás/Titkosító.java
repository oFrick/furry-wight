package fájlrendszer.titkosítás;

import fájlrendszer.titkosítás.titkosítóAlgoritmus.TitkosítóAlgoritmus;

/**
 * Titkosító objektum, amely a hozzárendelt tetszõleges titkosítási algoritmus alapján tetszõleges karaktersorozatot titkosít és fejt meg.
 * @author Kiss Dániel
 *
 */
public class Titkosító {
	
	private final String kulcs;
	private final TitkosítóAlgoritmus algoritmus;
	
	public Titkosító(String kulcs, TitkosítóAlgoritmus algoritmus){
		super();
		this.kulcs = kulcs;		
		this.algoritmus = algoritmus;		
	}
	
	/**
	 * A megadott titkosító algoritmus alapján titkosítja a sztringet
	 * @author Kiss Dániel
	 * @param mit- String
	 * @return String
	 */
	public final String titkosít(String mit){
		return algoritmus.kódol(mit, kulcs);	
	}
	
	/**
	 * Ezen objektum által titkosított adatot (String) visszafejti a titkosítás elõtti állapotra
	 * @author Kiss Dániel
	 * @param mibõl - String
	 * @return String
	 */
	public final String visszanyer(String mibõl){
		return algoritmus.dekódol(mibõl, kulcs);
	}
	
}
