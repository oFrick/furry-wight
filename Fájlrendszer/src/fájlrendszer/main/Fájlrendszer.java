package f�jlrendszer.main;

import java.util.ArrayList;
import java.util.List;

import javax.swing.tree.DefaultMutableTreeNode;

/**
 * A f�jlok/mapp�k t�rol�s�ra, a merevlemez funkci�inak magas szinten t�rt�n� megval�s�t�s�ra szolg�l� oszt�ly.<br>
 * A f�jlokat/mapp�kat csak t�roljuk, el�r�s�k a {@link DefaultMutableTreeNode} t�pus� adattagjukon kereszt�l t�rt�nik k�zvetlen�l
 * a GUI-t megval�s�t� oszt�lyokb�l
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
	 * 
	 * @param rootNode - {@link DefaultMutableTreeNode}, a fa root grafikus eleme
	 */
	public F�jlrendszer(DefaultMutableTreeNode rootNode){
		this(rootNode, true);
	}
	
	/**
	 * Konstruktor, melyben eld�nthet�, hogy megl�v�, vagy �j f�jlrendszerrel szeretn�nk dolgozni.<br/>
	 * Megl�v� eset�ben f�jlb�l be kell t�lteni a f�jlrendszert
	 * 
	 * @param rootNode - {@link DefaultMutableTreeNode}, a fa root grafikus eleme
	 * @param is�jf�jlrendszer - boolean, �j f�jlrendszert szeretn�nk-e?
	 */
	public F�jlrendszer(DefaultMutableTreeNode rootNode, boolean is�jf�jlrendszer){
		f�jlok = new ArrayList<F�jl>();
		mapp�k = new ArrayList<K�nyvt�r>();
		
		if (is�jf�jlrendszer){
			root = new K�nyvt�r(rootNode.getUserObject().toString());
			mapp�k.add(root);
			k�nyvt�rLemezre(root);
		}else{
			root = k�nyvt�rLemezr�l();
			mapp�k.add(root);
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
	
	public void add(Entit�s elem){
		if(elem instanceof F�jl) f�jlok.add((F�jl)elem);
		else mapp�k.add((K�nyvt�r)elem);
	}
	
	public void remove(Entit�s elem){
		if(elem instanceof F�jl) f�jlok.remove((F�jl)elem);
		else mapp�k.remove((K�nyvt�r)elem);
	}
	
	public K�nyvt�r getRoot(){
		return root;
	}

}
