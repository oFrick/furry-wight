package fájlrendszer.titkosítás.titkosítóAlgoritmus;

public final class EltolóAlgoritmus extends TitkosítóAlgoritmus {
	
	public EltolóAlgoritmus(){
		super();
	}

	@Override
	public String kódol(String mit, String kulcs) {
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
	public String dekódol(String mit, String kulcs) {
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
