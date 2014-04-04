package f�jlrendszer.titkos�t�s.titkos�t�Algoritmus;

public final class Eltol�Algoritmus extends Titkos�t�Algoritmus {
	
	public Eltol�Algoritmus(){
		super();
	}

	@Override
	public String k�dol(String mit, String kulcs) {
		char[] mitArray = mit.toCharArray();
		char[] kulcsArray = mit.toCharArray();
		for(int i=0; i<mitArray.length; i++){
			mitArray[i] += kulcsArray[i];
		}
		return mitArray.toString();
	}

	@Override
	public String dek�dol(String mit, String kulcs) {
		char[] mitArray = mit.toCharArray();
		char[] kulcsArray = mit.toCharArray();
		for(int i=0; i<mitArray.length; i++){
			mitArray[i] -= kulcsArray[i];
		}
		return mitArray.toString();
	}

}
