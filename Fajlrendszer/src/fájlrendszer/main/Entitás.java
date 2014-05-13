package fájlrendszer.main;

import java.util.Calendar;

/**
 * A fájlrendszer magas szintû megjelenítési objektumainak õsosztálya.
 * Ebbõl származik a {@link Fájl} és a {@link Könyvtár}
 * @author Kiss Dániel
 *
 */
public abstract class Entitás {
	
	private boolean isFuttatható;
	private boolean isÍrható;
	private boolean isRejtett;
	private boolean isTitkosított;
	private Calendar létrehozva;
	private Calendar módosítva;
	
	private String név;
	private String oldName;
	private int handle;
	
	/**
	 * @return the handle
	 */
	public int getHandle() {
		return handle;
	}

	/**
	 * @param handle the handle to set
	 */
	public void setHandle(int handle) {
		this.handle = handle;
	}

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
		this.oldName = this.név;
		this.név = név;
	}

	public String getOldName() {
		return oldName;
	}

	public Entitás(String név, int handle){
		this(név, false, false, Calendar.getInstance());
		this.handle = handle;
	}
	
	public Entitás(String név, boolean isRejtett, boolean isTitkosított, Calendar létrehozva){
		this.isRejtett = isRejtett;
		this.isTitkosított = isTitkosított;
		this.isFuttatható=true;
		this.isÍrható=true;
		
		this.módosítva = Calendar.getInstance();
		this.létrehozva = létrehozva;
		
		this.név = név;
		this.oldName = név;
		this.handle = 0;
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
