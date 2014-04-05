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
	
	private F�jlt�pus f�jlt�pus; //A f�jl kiterjeszt�se
	private String tartalom; //A f�jl tartalma
	private DefaultMutableTreeNode grafikusNode;
	private DefaultMutableTreeNode sz�l�Node;
	
	private int m�ret; //A f�jl m�rete byte-ban
	private static final int defaultM�ret=1; //A f�jl tartalm�nak egy elemi r�szegys�g�hez (pl sz�vegben bet�, k�pben pixel) tartoz� byte-ban megadott m�retsz�ks�glet
	
	public F�jl(DefaultMutableTreeNode grafikusNode, DefaultMutableTreeNode sz�l�Node){
		this.grafikusNode = grafikusNode;
		this.sz�l�Node = sz�l�Node;
		
		this.f�jlt�pus = F�jlt�pus.DEFAULT;
		
		System.out.println("F�jl l�trehozva: "+grafikusNode.getUserObject()+", itt: "+sz�l�Node.getUserObject()); //Debug
		
	}
	
	public int getM�ret() {return m�ret;}
	public String getN�v() {return (String)grafikusNode.getUserObject();}
	public String getTartalom() {return tartalom;}
	
	public void setN�v(String n�v){
		grafikusNode.setUserObject(n�v);
	}
	public void setTartalom(String tartalom){
		this.tartalom = tartalom;
	}
	
	/**
	 * A f�jl m�ret�nek megv�ltoztat�sa.<br/><br/>
	 * A f�jl tartalm�nak megfelel�en v�ltoztatja a m�retet. Akkor lesz megh�vva ez a met�dus, ha v�ltozik a f�jl tartalma.<br/>
	 * A tartalom mez�ben l�v� minden egyes karakterrel n�vekszik/cs�kken a m�ret 1 byte-tal.
	 * @author Kiss D�niel
	 */
	private void setM�ret(){
		m�ret = tartalom.length()*defaultM�ret;
	}

}
