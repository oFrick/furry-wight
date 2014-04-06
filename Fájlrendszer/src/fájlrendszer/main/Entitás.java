package f�jlrendszer.main;

public abstract class Entit�s {
	
	private boolean isFuttathat�;
	private boolean is�rhat�;
	private boolean isRejtett;
	private boolean isTitkos�tott;
	
	public Entit�s(){
		this.isRejtett=false;
		this.isFuttathat�=true;
		this.is�rhat�=true;
		this.isTitkos�tott = false;
	}
	
	public Entit�s(boolean isRejtett, boolean isTitkos�tott){
		this.isRejtett = isRejtett;
		this.isTitkos�tott = isTitkos�tott;
		this.isFuttathat�=true;
		this.is�rhat�=true;		
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
	
	

}
