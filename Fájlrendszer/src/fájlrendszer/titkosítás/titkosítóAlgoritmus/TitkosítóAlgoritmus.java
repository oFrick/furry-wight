package f�jlrendszer.titkos�t�s.titkos�t�Algoritmus;

/**
 * A k�dol�st �s dek�dol�st megval�s�t� algoritmust tartalmaz� oszt�ly.<br/><br/>
 * <b>Ezen oszt�ly lesz�rmazottainak k�telez�en meghat�rozhat�nak kell lenni a konstruktoruk alapj�n, azaz k�s�bbi be�ll�t�suk
 * setter met�dusokon kereszt�l nem lehets�ges!</b><br/>
 * Ennek oka, hogy a Titkos�t� oszt�lyban az algoritmus mez� t�pusa ez az �soszt�ly (upcast), �gy ha ennek sz�rmaztatottja be�ll�t�sainak m�dj�t
 * (pl setteren kereszt�l megadni egy titkos�t�si param�tert), akkor mag�t a titkos�t� oszt�lyt is m�dos�tani kellene ennek megfelel�en, ami az
 * �jrafelhaszn�lhat�s�got rontja.
 * 
 * @author Kiss D�niel
 *
 */
public abstract class Titkos�t�Algoritmus {
	
	public abstract String k�dol(String mit, String kulcs);
	public abstract String dek�dol(String mit, String kulcs);

}
