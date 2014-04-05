package fájlrendszer.main;

/**
 * A szabványos kiterjesztések megvalósítása.<br/><br/>
 * Tekintettel arra, hogy ezen megoldásban a szabványos kiterjesztések rögzítettek, így a nem szabványos, felhasználó által megadottak
 * nem kezelhetõek le, azokat úgy tekintjük, mint default (kiterjesztés nélküli).
 * @author Kiss Dániel
 *
 */
public enum Kiterjesztés {
	
	TXT (".txt"),
	JPG (".jpg"),
	EXE (".exe"),
	DIR (".dir"),
	DEFAULT ("");
	
	private final String jelölés;
	
	Kiterjesztés(String jelölés)
	{
		this.jelölés = jelölés;
	}
	
	public String getJelölés() { return jelölés;} //toString miatt ez nem fog kelleni
	public String toString(){ return jelölés;}
}
