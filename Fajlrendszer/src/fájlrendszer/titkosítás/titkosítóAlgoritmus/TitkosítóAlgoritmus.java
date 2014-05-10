package fájlrendszer.titkosítás.titkosítóAlgoritmus;

/**
 * A kódolást és dekódolást megvalósító algoritmust tartalmazó osztály.<br/><br/>
 * <b>Ezen osztály leszármazottainak kötelezõen meghatározhatónak kell lenni a konstruktoruk alapján, azaz késõbbi beállításuk
 * setter metódusokon keresztül nem lehetséges!</b><br/>
 * Ennek oka, hogy a Titkosító osztályban az algoritmus mezõ típusa ez az õsosztály (upcast), így ha ennek származtatottja beállításainak módját
 * (pl setteren keresztül megadni egy titkosítási paramétert), akkor magát a titkosító osztályt is módosítani kellene ennek megfelelõen, ami az
 * újrafelhasználhatóságot rontja.
 * 
 * @author Kiss Dániel
 *
 */
public abstract class TitkosítóAlgoritmus {
	
	public abstract String kódol(String mit, String kulcs);
	public abstract String dekódol(String mit, String kulcs);

}
