package f�jlrendszer.main;

/**
 * A szabv�nyos kiterjeszt�sek megval�s�t�sa.<br/><br/>
 * Tekintettel arra, hogy ezen megold�sban a szabv�nyos kiterjeszt�sek r�gz�tettek, �gy a nem szabv�nyos, felhaszn�l� �ltal megadottak
 * nem kezelhet�ek le, azokat �gy tekintj�k, mint default (kiterjeszt�s n�lk�li).
 * @author Kiss D�niel
 *
 */
public enum Kiterjeszt�s {
	
	TXT (".txt"),
	JPG (".jpg"),
	EXE (".exe"),
	DIR (".dir"),
	DEFAULT ("");
	
	private final String jel�l�s;
	
	Kiterjeszt�s(String jel�l�s)
	{
		this.jel�l�s = jel�l�s;
	}
	
	public String getJel�l�s() { return jel�l�s;} //toString miatt ez nem fog kelleni
	public String toString(){ return jel�l�s;}
}
