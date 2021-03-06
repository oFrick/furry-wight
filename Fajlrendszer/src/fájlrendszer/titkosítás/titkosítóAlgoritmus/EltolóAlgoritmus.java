package f�jlrendszer.titkos�t�s.titkos�t�Algoritmus;

public final class Eltol�Algoritmus extends Titkos�t�Algoritmus {
	
	public Eltol�Algoritmus(){
		super();
	}

	@Override
	public String k�dol(String mit, String kulcs) {
		char[] mitArray = mit.toCharArray();
		char[] kulcsArray = kulcs.toCharArray();
		int j=0;
		
		for(int i=0; i<mitArray.length; i++){
			mitArray[i] += kulcsArray[j];
			if(j == kulcs.length()-1) j=0;
			else j++;
		}
		return new String(mitArray);
	}

	@Override
	public String dek�dol(String mit, String kulcs) {
		char[] mitArray = mit.toCharArray();
		char[] kulcsArray = kulcs.toCharArray();
		int j=0;
		
		for(int i=0; i<mitArray.length; i++){
			mitArray[i] -= kulcsArray[j];
			if(j == kulcs.length()-1) j=0;
			else j++;
		}
		return new String(mitArray);
	}

}
