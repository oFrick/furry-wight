package f�jlrendszer.main;

import javax.swing.tree.DefaultMutableTreeNode;

/**
 * A f�jlrendszerben egy f�jl reprezent�l�sa.<br/><br/>
 * 
 * Minden f�jlt a grafikus megjelen�t�shez haszn�lt {@link DefaultMutableTreeNode} mez�je alapj�n azonos�tunk.<br/>
 * Emiatt a f�jlnak nincs n�v mez�je, a nevet az el�bb megadott DefaultMutableTreeNode tartalmazza.<br/><br/>
 * 
 * A f�jl kiterjeszt�s�nek jelent�se (kezel�se) leginik�bb a UNIX rendszerekhez hasonl�t, ahol a kiterjeszt�s �s a f�jl t�pusa k�t k�l�nb�z� fogalom<br/>
 * <b>Ezen oszt�ly az egyes f�jlt�pus oszt�lyok �soszt�lya.</b>
 * @author Kiss D�niel
 *
 */
public class F�jl {
	
	private Kiterjeszt�s kiterjeszt�s; //A f�jl kiterjeszt�se
	private int m�ret; //A f�jl m�rete
	private String tartalom; //A f�jl tartalma
	private DefaultMutableTreeNode grafikusNode;
	private DefaultMutableTreeNode sz�l�Node;
	
	public F�jl(DefaultMutableTreeNode grafikusNode, DefaultMutableTreeNode sz�l�Node){
		this.grafikusNode = grafikusNode;
		this.sz�l�Node = sz�l�Node;
		
		this.kiterjeszt�s = Kiterjeszt�s.DEFAULT;
	}
	
	public int getM�ret() {return m�ret;}
	public String getN�v() {return (String)grafikusNode.getUserObject();}
	public String getTartalom() {return tartalom;}
	
	public void setN�v(String n�v){
		grafikusNode.setUserObject(n�v);
	}
	
	private void setM�ret(String tartalom){
		
	}

}
