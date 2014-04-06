package fájlrendszer.main;

public abstract class Entitás {
	
	private boolean isFuttatható;
	private boolean isÍrható;
	private boolean isRejtett;
	private boolean isTitkosított;
	
	public Entitás(){
		this.isRejtett=false;
		this.isFuttatható=true;
		this.isÍrható=true;
		this.isTitkosított = false;
	}
	
	public Entitás(boolean isRejtett, boolean isTitkosított){
		this.isRejtett = isRejtett;
		this.isTitkosított = isTitkosított;
		this.isFuttatható=true;
		this.isÍrható=true;		
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
	
	

}
