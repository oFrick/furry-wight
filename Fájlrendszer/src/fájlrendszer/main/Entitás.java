package fájlrendszer.main;

import java.util.Calendar;

public abstract class Entitás {
	
	private boolean isFuttatható;
	private boolean isÍrható;
	private boolean isRejtett;
	private boolean isTitkosított;
	private Calendar létrehozva;
	private Calendar módosítva;
	
	private String név;
	
	/**
	 * @return the név
	 */
	public String getNév() {
		return név;
	}

	/**
	 * @param név the név to set
	 */
	public void setNév(String név) {
		this.név = név;
	}

	public Entitás(String név){
		this(név, false, false, Calendar.getInstance());
	}
	
	public Entitás(String név, boolean isRejtett, boolean isTitkosított, Calendar létrehozva){
		this.isRejtett = isRejtett;
		this.isTitkosított = isTitkosított;
		this.isFuttatható=true;
		this.isÍrható=true;
		
		this.módosítva = Calendar.getInstance();
		this.létrehozva = létrehozva;
		
		this.név = név;
	}

	public boolean isFuttatható() {
		return isFuttatható;
	}

	public boolean isÍrható() {
		return isÍrható;
	}

	public boolean isRejtett() {
		return isRejtett;
	}

	public boolean isTitkosított() {
		return isTitkosított;
	}
	
	public Calendar getMódosítva(){
		return módosítva;
	}
	
	public Calendar getLétrehozva(){
		return létrehozva;
	}

	public void setFuttatható(boolean isFuttatható) {
		this.isFuttatható = isFuttatható;
	}

	public void setÍrható(boolean isÍrható) {
		this.isÍrható = isÍrható;
	}

	public void setRejtett(boolean isRejtett) {
		this.isRejtett = isRejtett;
	}

	public void setTitkosított(boolean isTitkosított) {
		this.isTitkosított = isTitkosított;
	}
	
	public void setMódosítva(Calendar módosításIdeje){
		módosítva = módosításIdeje;
	}
	
	

}
