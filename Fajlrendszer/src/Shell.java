/**
*@author Patrick Hecker
*@version 1.0
*/
import java.util.StringTokenizer; //Az input string tordelesehez kell
import java.util.Arrays; //Tombkezeleshez kell

public class Shell {

	/* Teszt
	*public static void main(String[] args) {
	*	bemenet("exit");
	*}
	*/
	
	private MainFrame frame;
	
	public Shell(MainFrame frame){
		this.frame = frame;
	}
	
	public void bemenet(String cmd) {
		String par1, par2 = null;
		StringTokenizer st = new StringTokenizer(cmd); //Az inputot tokenekre bontjuk a szokozok menten
		while (st.hasMoreTokens()) { //Amig van token...
			switch (st.nextToken()) { //Az elso tokennek megfelelo parancs fuggvenyet hivjuk meg a megadott parameterekkel
				case "help": par1 = st.nextToken(); help(par1); break;
				case "cp": par1 = st.nextToken(); par2 = st.nextToken(); cp(par1, par2); break;
				case "copy": par1 = st.nextToken(); par2 = st.nextToken(); cp(par1, par2); break;
				case "mv": par1 = st.nextToken(); par2 = st.nextToken(); mv(par1, par2); break;
				case "move": par1 = st.nextToken(); par2 = st.nextToken(); mv(par1, par2); break;
				case "del": par1 = st.nextToken(); del(par1); break;
				case "delete": par1 = st.nextToken(); del(par1); break;
				case "rn": par1 = st.nextToken(); rn(par1, par2); break;
				case "rename": par1 = st.nextToken(); rn(par1, par2); break;
				case "mkdir": par1 = st.nextToken(); mkdir(par1); break;
				case "rmdir": par1 = st.nextToken(); rmdir(par1); break;
				case "crypt": par1 = st.nextToken(); crypt(par1); break;
				case "decrypt": par1 = st.nextToken(); decrypt(par1); break;
				case "cd": par1 = st.nextToken(); cd(par1); break;
				case "hide": par1 = st.nextToken(); hide(par1); break;
				case "unhide": par1 = st.nextToken(); unhide(par1); break;
				default: Seged.popup("Nem talalhato ilyen parancs!", "Hiba!", frame); break;
			}
		}
	}
public void helper(String par1) { //A megfelelo szoveg kiiratasa a help parancs eseten
	switch(par1) {
		case "help": Seged.popup("help: Ez a parancs kilistazza az osszes elerheto parancsot. Ha egy parametert is megadunk, akkor arrol a parancsrol kapunk informaciokat.", "Help üzenet", frame); break;
		case "cp": Seged.popup("cp: Ez a parancs az elso parameterben megadott fajlrol keszit egy masolatot, amit a masodik parameterben megadott helyre ment el.", "Help üzenet", frame); break;
		case "copy": Seged.popup("copy: Ez a parancs az elso parameterben megadott fajlrol keszit egy masolatot, amit a masodik parameterben megadott helyre ment el.", "Help üzenet", frame); break;
		case "mv": Seged.popup("mv: Ez a parancs az elso parameterben megadott fajlt athelyezi a masodik parameterben megadott helyre.", "Help üzenet", frame); break;
		case "move": Seged.popup("move: Ez a parancs az elso parameterben megadott fajlt athelyezi a masodik parameterben megadott helyre.", "Help üzenet", frame); break;
		case "del": Seged.popup("del: Ez a parancs torli az elso parameterben megadott fajlt.", "Help üzenet", frame); break;
		case "delete": Seged.popup("delete: Ez a parancs torli az elso parameterben megadott fajlt.", "Help üzenet", frame); break;
		case "rn": Seged.popup("rn: Ez a parancs atnevezi az elso parameterben megadott fajlt, vagy mappat a masodik parameterben megadottra.", "Help üzenet", frame); break;
		case "rename": Seged.popup("rename: Ez a parancs atnevezi az elso parameterben megadott fajlt, vagy mappat a masodik parameterben megadottra.", "Help üzenet", frame); break;
		case "mkdir": Seged.popup("mkdir: Ez a parancs letrehoz egy mappat az elso parameterben megadott neven.", "Help üzenet", frame); break;
		case "rmdir": Seged.popup("rmdir: Ez a parancs torli a parameterkent megadott ures mappat.", "Help üzenet", frame); break;
		case "crypt": Seged.popup("crypt: Ez a parancs titkositja az elso parameterben megadott nem titkositott fajlt.", "Help üzenet", frame); break;
		case "decrypt": Seged.popup("decrypt: Ez a parancs ujra olvashatova teszi az elso parameterben megadott titkositott fajlt.", "Help üzenet", frame); break;
		case "cd": Seged.popup("cd: Ez a parancs megvaltoztatja az aktualis munkakonyvtarat az elso parameterben megadottra.", "Help üzenet", frame); break;
		case "hide": Seged.popup("hide: Ez a parancs rejtette teszi az elso parameterben megadott fajlt.", "Help üzenet", frame); break;
		case "unhide": Seged.popup("unhide: Ez a parancs lathatova teszi az elso parameterben megadott rejtett fajlt.", "Help üzenet", frame); break;
		default: Seged.popup("Nincsen " + par1 + " nevu parancs. Az elerheto parancsok listajahoz hasznald a help parancsot.", "Help üzenet", frame); break;
	}
}




/**
*Ezekben a metodusokban fog tortenni a megfelelo fuggveny meghivasa, a parameterek atadasa, szintaktikai hibaellenorzes
*/
public void cp(String par1, String par2) {
	if ((par1 == "") || (par2 == "")) {
		Seged.popup("Hiba! Hiányzó paraméter(ek)!", "Hiba!", frame);
		return;
	}
	frame.copy(par1, par2);
	return;
}

public void mv(String par1, String par2) {
	if ((par1 == "") || (par2 == "")) {
		Seged.popup("Hiba! Hiányzó paraméter(ek)!", "Hiba!", frame);
		return;
	}
	frame.replace(par1, par2);
	return;
}
public void del(String par1) {
	if (par1 == "") {
		Seged.popup("Hiba! Hiányzó paraméter!", "Hiba!", frame);
		return;
	}
	frame.delete(par1);
	return;
}
public void rn(String par1, String par2) {
	if (par1 == "") {
		Seged.popup("Hiba! Hiányzó paraméter(ek)!", "Hiba!", frame);
		return;
	}
	frame.rename(par1, par2);
	return;
}
public void mkdir(String par1) {
	if (par1 == "") {
		Seged.popup("Hiba! Hiányzó paraméter!", "Hiba!", frame);
		return;
	}
	return;
}
public void rmdir(String par1) {
	if (par1 == "") {
		Seged.popup("Hiba! Hiányzó paraméter!", "Hiba!", frame);
		return;
	}
	return;
}
public void crypt(String par1) {
	if (par1 == "") {
		Seged.popup("Hiba! Hiányzó paraméter!", "Hiba!", frame);
		return;
	}
	return;
}
public void decrypt(String par1) {
	if (par1 == "") {
		Seged.popup("Hiba! Hiányzó paraméter!", "Hiba!", frame);
		return;
	}
	return;
}
public void cd(String par1) {
	if (par1 == "") {
		Seged.popup("Hiba! Hiányzó paraméter!", "Hiba!", frame);
		return;
	}
	return;
}
public void hide(String par1) {
	if (par1 == "") {
		Seged.popup("Hiba! Hiányzó paraméter!", "Hiba!", frame);
		return;
	}
	return;
}
public void unhide(String par1) {
	if (par1 == "") {
		Seged.popup("Hiba! Hiányzó paraméter!", "Hiba!", frame);
		return;
	}
	return;
}
public void help(String par1) {
	if (par1 == "") {
		String commandlist = "help, cp, copy, mv, rename, move, del, delete, rn, mkdir, rmdir, cd";
		Seged.popup("Lehetséges parancsok: "+commandlist, "Elérhetõ parancsok", frame);
	}
	else {
		helper(par1);
	}
}



}