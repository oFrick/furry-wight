package fájlrendszer.main;

import java.util.List;

import javax.swing.tree.DefaultMutableTreeNode;

public class Könyvtár extends Entitás {
	
	private DefaultMutableTreeNode grafikusNode;
	
	public Könyvtár(DefaultMutableTreeNode grafikusNode){
		super();
		this.grafikusNode = grafikusNode;
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

}
