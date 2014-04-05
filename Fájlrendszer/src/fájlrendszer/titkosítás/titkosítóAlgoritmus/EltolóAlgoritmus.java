package fájlrendszer.titkosítás.titkosítóAlgoritmus;

public final class EltolóAlgoritmus extends TitkosítóAlgoritmus {
	
	public EltolóAlgoritmus(){
		super();
	}

	@Override
	public String kódol(String mit, String kulcs) {
		char[] mitArray = mit.toCharArray();
		char[] kulcsArray = kulcs.toCharArray();
		for(int i=0; i<mitArray.length; i++){
			mitArray[i] += kulcsArray[i];
		}
		return new String(mitArray);
	}

	@Override
	public String dekódol(String mit, String kulcs) {
		char[] mitArray = mit.toCharArray();
		char[] kulcsArray = kulcs.toCharArray();
		for(int i=0; i<mitArray.length; i++){
			mitArray[i] -= kulcsArray[i];
		}
		return new String(mitArray);
	}

}
