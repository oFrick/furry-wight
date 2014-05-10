package fájlrendszer.titkosítás.titkosítóAlgoritmus;

import java.awt.image.BufferedImage;

public abstract class KépTitkosítóAlgoritmus {
	
	
	public abstract String kódol(String mit, BufferedImage kulcs);
	public abstract String dekódol(String mit, BufferedImage kulcs);
	
}
