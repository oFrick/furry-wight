package f�jlrendszer.main;

import java.util.Calendar;

public abstract class Entit�s {
	
	private boolean isFuttathat�;
	private boolean is�rhat�;
	private boolean isRejtett;
	private boolean isTitkos�tott;
	private Calendar l�trehozva;
	private Calendar m�dos�tva;
	
	private String n�v;
	
	/**
	 * @return the n�v
	 */
	public String getN�v() {
		return n�v;
	}

	/**
	 * @param n�v the n�v to set
	 */
	public void setN�v(String n�v) {
		this.n�v = n�v;
	}

	public Entit�s(String n�v){
		this(n�v, false, false, Calendar.getInstance());
	}
	
	public Entit�s(String n�v, boolean isRejtett, boolean isTitkos�tott, Calendar l�trehozva){
		this.isRejtett = isRejtett;
		this.isTitkos�tott = isTitkos�tott;
		this.isFuttathat�=true;
		this.is�rhat�=true;
		
		this.m�dos�tva = Calendar.getInstance();
		this.l�trehozva = l�trehozva;
		
		this.n�v = n�v;
	}

	public boolean isFuttathat�() {
		return isFuttathat�;
	}

	public boolean is�rhat�() {
		return is�rhat�;
	}

	public boolean isRejtett() {
		return isRejtett;
	}

	public boolean isTitkos�tott() {
		return isTitkos�tott;
	}
	
	public Calendar getM�dos�tva(){
		return m�dos�tva;
	}
	
	public Calendar getL�trehozva(){
		return l�trehozva;
	}

	public void setFuttathat�(boolean isFuttathat�) {
		this.isFuttathat� = isFuttathat�;
	}

	public void set�rhat�(boolean is�rhat�) {
		this.is�rhat� = is�rhat�;
	}

	public void setRejtett(boolean isRejtett) {
		this.isRejtett = isRejtett;
	}

	public void setTitkos�tott(boolean isTitkos�tott) {
		this.isTitkos�tott = isTitkos�tott;
	}
	
	public void setM�dos�tva(Calendar m�dos�t�sIdeje){
		m�dos�tva = m�dos�t�sIdeje;
	}
	
	

}
