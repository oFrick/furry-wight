package fájlrendszer.titkosítás;

import java.awt.image.BufferedImage;

import fájlrendszer.titkosítás.titkosítóAlgoritmus.KépTitkosítóAlgoritmus;
import fájlrendszer.titkosítás.titkosítóAlgoritmus.TitkosítóAlgoritmus;

public class KépTitkosító{
	
	private final BufferedImage kulcs;
	private final KépTitkosítóAlgoritmus algoritmus;
	
	public KépTitkosító(BufferedImage kulcs, KépTitkosítóAlgoritmus algoritmus){
		super();
		this.kulcs = kulcs;	
		this.algoritmus = algoritmus;	
		
	}
	
	public final String titkosít(String mit){
		return algoritmus.kódol(mit, kulcs);	
	}
	
	public final String visszanyer(String mibõl){
		return algoritmus.dekódol(mibõl, kulcs);
	}
}
