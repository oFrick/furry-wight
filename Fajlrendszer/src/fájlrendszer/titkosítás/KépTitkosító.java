package f�jlrendszer.titkos�t�s;

import java.awt.image.BufferedImage;

import f�jlrendszer.titkos�t�s.titkos�t�Algoritmus.K�pTitkos�t�Algoritmus;
import f�jlrendszer.titkos�t�s.titkos�t�Algoritmus.Titkos�t�Algoritmus;

public class K�pTitkos�t�{
	
	private final BufferedImage kulcs;
	private final K�pTitkos�t�Algoritmus algoritmus;
	
	public K�pTitkos�t�(BufferedImage kulcs, K�pTitkos�t�Algoritmus algoritmus){
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
