package fájlrendszer.titkosítás.titkosítóAlgoritmus;

import java.awt.image.BufferedImage;

public class EgyszerûKépAlgoritmus extends KépTitkosítóAlgoritmus {
	
	private int n;
	private int red;
	private int i,j,k;
	private int[] generáltKulcs;
	
	public EgyszerûKépAlgoritmus(int n){
		super();
		
		this.n=n;
		generáltKulcs = new int[n];
	}

	@Override
	public String kódol(String mit, BufferedImage kulcs) {
		// TODO Auto-generated method stub
		int színkód;
		char[] mitChars = mit.toCharArray();
		k=0;
		i=0;
		j=0;
		
		while(k<n){
			színkód = kulcs.getRGB(i, j);
			if(i == kulcs.getWidth()-1){
				j++;
				i=0;
			}
			if(k < mit.length()) mitChars[k] += színkód;
			else break;
			k++;
		}
		
		return new String(mitChars);
	}

	@Override
	public String dekódol(String mit, BufferedImage kulcs) {
		// TODO Auto-generated method stub
		int színkód;
		char[] mitChars = mit.toCharArray();
		k=0;
		i=0;
		j=0;
		
		while(k<n){
			színkód = kulcs.getRGB(i, j);
			if(i == kulcs.getWidth()-1){
				j++;
				i=0;
			}
			if(k < mit.length()) mitChars[k] -= színkód;
			else break;
			k++;
		}
		
		return new String(mitChars);
	}

}
