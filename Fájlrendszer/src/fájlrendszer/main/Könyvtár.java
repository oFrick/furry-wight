package f�jlrendszer.main;

import java.util.List;

import javax.swing.tree.DefaultMutableTreeNode;

public class K�nyvt�r extends Entit�s {
	
	private String n�v;
	
	public K�nyvt�r(String n�v){
		super();
		this.n�v = n�v;
	}
	
	/**
	 * @return the n�v
	 */
	public String getN�v() {
		return n�v;
	}

	/**
	 * @param n�v the n�v to set
	 */
	public void setN�v(String n�v) {
		this.n�v = n�v;
	}

	/**A k�nyvt�r bet�lt�se a lemezr�l, azaz egy elmentett f�jlrendszer f�jlb�l.<br/><br/>
	 * 
	 * <b>B�lint! Ezt te tudn�d itt implement�lni a dll-ed alapj�n</b>
	 * 
	 */
	public void bet�ltLemezr�l(){
		
	}
	
	/**
	 * A k�nyvt�r ki�r�sa a lemezre<br/><br/>
	 * 
	 * <b>B�lint! Ezt te tudn�d itt implement�lni a dll-ed alapj�n</b>
	 * 
	 * 
	 */
	public void ki�rLemezre(){
		
	}
	
	public String toString(){
		return n�v;
	}

}
