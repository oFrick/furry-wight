package fájlrendszer.titkosítás;

public interface TitkosítóInterface<T> {
	
	 T  kulcs();
	 public T titkosít(T mit);
	 public T visszanyer(T mibõl);

}
