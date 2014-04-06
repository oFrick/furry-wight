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
	 * @author Kiss Dániel
	 */
	public void betöltLemezrõl(){
		
	}
	
	/**
	 * A könyvtár kiírása a lemezre<br/><br/>
	 * 
	 * <b>Bálint! Ezt te tudnád itt implementálni a dll-ed alapján</b>
	 * @author Kiss Dániel
	 * 
	 */
	public void kiírLemezre(){
		
	}
	
	public String toString(){
		return név;
	}

}
