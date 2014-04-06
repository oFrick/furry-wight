package f�jlrendszer.main;

import java.util.List;

import javax.swing.tree.DefaultMutableTreeNode;

/**
 * A f�jlok/mapp�k t�rol�s�ra, a merevlemez funkci�inak magas szinten t�rt�n� megval�s�t�s�ra szolg�l� oszt�ly.
 * @author Kiss D�niel
 *
 */
public class F�jlrendszer {
	
	private List<F�jl> f�jlok;
	private List<K�nyvt�r> mapp�k;
	private K�nyvt�r root;
	
	/**
	 * Konstruktor.<br/><br/>
	 * 
	 * Egy teljesen �j f�jlrendszert k�sz�t
	 * @author Kiss D�niel
	 * @param rootNode - {@link DefaultMutableTreeNode}, a fa root grafikus eleme
	 */
	public F�jlrendszer(DefaultMutableTreeNode rootNode){
		this(rootNode, true);
	}
	
	/**
	 * Konstruktor, melyben eld�nthet�, hogy megl�v�, vagy �j f�jlrendszerrel szeretn�nk dolgozni.<br/>
	 * Megl�v� eset�ben f�jlb�l be kell t�lteni a f�jlrendszert
	 * @author Kiss D�niel
	 * @param rootNode - {@link DefaultMutableTreeNode}, a fa root grafikus eleme
	 * @param is�jf�jlrendszer - boolean, �j f�jlrendszert szeretn�nk-e?
	 */
	public F�jlrendszer(DefaultMutableTreeNode rootNode, boolean is�jf�jlrendszer){
		if (is�jf�jlrendszer){
			root = new K�nyvt�r(rootNode);
			k�nyvt�rLemezre(root);
		}else{
			root = k�nyvt�rLemezr�l();
		}
	}
	
	public K�nyvt�r k�nyvt�rLemezr�l(){
		return null;
	}
	
	public void k�nyvt�rLemezre(K�nyvt�r k�nyvt�r){
		
	}
	
	public F�jl f�jlLemezr�l(){
		return null;
	}
	
	public void f�jlLemezre(F�jl f�jl){
		
	}

}
