package f�jlrendszer.titkos�t�s;

import f�jlrendszer.titkos�t�s.titkos�t�Algoritmus.Titkos�t�Algoritmus;

/**
 * Titkos�t� objektum, amely a hozz�rendelt tetsz�leges titkos�t�si algoritmus alapj�n tetsz�leges karaktersorozatot titkos�t �s fejt meg.
 * @author Kiss D�niel
 *
 */
public class Titkos�t� {
	
	private final String kulcs;
	private final Titkos�t�Algoritmus algoritmus;
	
	public Titkos�t�(String kulcs, Titkos�t�Algoritmus algoritmus){
		super();
		this.kulcs = kulcs;		
		this.algoritmus = algoritmus;		
	}
	
	public final String titkos�t(String mit){
		return algoritmus.k�dol(mit, kulcs);	
	}
	
	public final String visszanyer(String mib�l){
		return algoritmus.dek�dol(mib�l, kulcs);
	}
	
}
