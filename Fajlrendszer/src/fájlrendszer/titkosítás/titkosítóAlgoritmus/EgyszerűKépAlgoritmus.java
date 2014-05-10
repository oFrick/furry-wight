package f�jlrendszer.titkos�t�s.titkos�t�Algoritmus;

import java.awt.image.BufferedImage;

public class Egyszer�K�pAlgoritmus extends K�pTitkos�t�Algoritmus {
	
	private int n;
	private int red;
	private int i,j,k;
	private int[] gener�ltKulcs;
	
	public Egyszer�K�pAlgoritmus(int n){
		super();
		
		this.n=n;
		gener�ltKulcs = new int[n];
	}

	@Override
	public String k�dol(String mit, BufferedImage kulcs) {
		// TODO Auto-generated method stub
		int sz�nk�d;
		char[] mitChars = mit.toCharArray();
		k=0;
		i=0;
		j=0;
		
		while(k<n){
			sz�nk�d = kulcs.getRGB(i, j);
			if(i == kulcs.getWidth()-1){
				j++;
				i=0;
			}
			if(k < mit.length()) mitChars[k] += sz�nk�d;
			else break;
			k++;
		}
		
		return new String(mitChars);
	}

	@Override
	public String dek�dol(String mit, BufferedImage kulcs) {
		// TODO Auto-generated method stub
		int sz�nk�d;
		char[] mitChars = mit.toCharArray();
		k=0;
		i=0;
		j=0;
		
		while(k<n){
			sz�nk�d = kulcs.getRGB(i, j);
			if(i == kulcs.getWidth()-1){
				j++;
				i=0;
			}
			if(k < mit.length()) mitChars[k] -= sz�nk�d;
			else break;
			k++;
		}
		
		return new String(mitChars);
	}

}
