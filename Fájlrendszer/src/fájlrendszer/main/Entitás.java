package fájlrendszer.main;

import java.util.Calendar;

public abstract class Entitás {
	
	private boolean isFuttatható;
	private boolean isÍrható;
	private boolean isRejtett;
	private boolean isTitkosított;
	private Calendar létrehozva;
	private Calendar módosítva;
	
	public Entitás(){
		this(false, false);
	}
	
	public Entitás(boolean isRejtett, boolean isTitkosított){
		this.isRejtett = isRejtett;
		this.isTitkosított = isTitkosított;
		this.isFuttatható=true;
		this.isÍrható=true;
		
		this.létrehozva = Calendar.getInstance();
		this.módosítva = Calendar.getInstance();
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
