package fájlrendszer.main;

import java.util.List;

import javax.swing.tree.DefaultMutableTreeNode;

public class Könyvtár extends Entitás {
	
	private String név;
	
	public Könyvtár(String név){
		super();
		this.név = név;
	}
	
	/**A könyvtár betöltése a lemezrõl, azaz egy elmentett fájlrendszer fájlból.<br/><br/>
	 * 
	 * <b>Bálint! Ezt te tudnád itt implementálni a dll-ed alapján</b>
	 * 
	 */
	public void betöltLemezrõl(){
		
	}
	
	/**
	 * A könyvtár kiírása a lemezre<br/><br/>
	 * 
	 * <b>Bálint! Ezt te tudnád itt implementálni a dll-ed alapján</b>
	 * 
	 * 
	 */
	public void kiírLemezre(){
		
	}
	
	public String toString(){
		return név;
	}

}
