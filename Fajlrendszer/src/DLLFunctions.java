
/**
 *
 * @author B�lint
 */
public class DLLFunctions {
    
    
    //String loc = DLLFunctions.class.getProtectionDomain().getCodeSource().getLocation().getPath();
   
    DLLFunctions(String path) {
	System.load(path);
    }
    


    /**
     * IMPLEMENT�LVA & TESZTELVE
     * Leform�zza a lemezt.
     * @param sectors h�ny szektort foglalunk le?
     */
    native void formatDisk(int sectors);


    /**
     * IMPLEMENT�LVA & TESZTELVE
     * �tv�ltja az akt�v munkak�nyvt�rat egy �j mapp�ra. Ez lehet: "." (root), ".." (k�zvetlen sz�l� mappa) vagy
     * egy k�zvetlen almappa neve.
     * @param str az �j mappa neve, "." vagy ".."
     * @return true, ha sikeres volt a m�velet, egy�bk�nt false
     */
    native boolean changeDirectory(String str);
    
    /**
     * IMPLEMENT�LVA & TESZTELVE
     * Az aktu�lis DLL verzi� lek�r�s�re szolg�l.
     * @return a rendszer �ltal haszn�lt DLL verzi�inform�ci�ja
     */
    native String version();
    

    /**
     * IMPLEMENT�LVA & TESZTELVE
     * Kilist�zza az aktu�lis k�nyvt�rban tal�lhat� f�jlok �s mapp�k nev�t.
     * @return f�jlnevek
     */
    native String[] list();



    
    /**
     * IMPLEMENT�LVA & TESZTELVE
     * Megnyit egy f�jlt �r�sra/olvas�sra. A f�jlt a munka v�gezt�vel be kell z�rni (kiv�ve, ha sikertelen volt a megnyit�s).
     * @param name a f�jl neve, csak az akt�v mapp�ban l�v� f�jl nyithat� meg
     * @return handle a f�jlra; a tov�bbiakban ezzel azonos�that�, 0 ha nem siker�lt megnyitni
     */
    native int fileOpen(String name);
    



    /**
     * IMPLEMENT�LVA & TESZTELVE
     * A f�jlban l�v� jelenlegi adatot t�rli, �s �t�ll�tja az �jra.
     * @param handle f�jlazonos�t�
     * @param data adat
     * @see #fileOpen(String name)
     */
    native void fileSetData(int handle, byte[] data);
    


    /**
     * IMPLEMENT�LVA & TESZTELVE
     * Adat hozz�f�z�se a f�jlhoz.
     * @param handle f�jlazonos�t�
     * @param data adat
     * @see #fileOpen(String name)
     */
    native void fileAppendData(int handle, byte[] data);
    
    //adat besz�r�sa b�rhova
    //native void fileInsertData(int handle, byte[] data, long where);
    
    
    /**
     * IMPLEMENT�LVA & TESZTELVE
     * Adat t�rl�se a f�jlb�l.
     * @param handle f�jlazonos�t�
     * @param from hanyadik b�jtt�l t�r�lj�k az adatot?
     * @param size mennyit?
     * @see #fileOpen(String name)
     */
    native void fileEraseData(int handle, long from, long size);
    
    /**
     * IMPLEMENT�LVA & TESZTELVE
     * Adatlek�r�s. Fontos, hogy az adat csak a f�jl bez�rt�ig �rhet� el.
     * @param handle f�jlazonos�t�
     * @return a f�jlban l�v� adat b�jt t�mbben
     */
    native byte[] fileGetData(int handle);
    
    
    //attrib�tum accessorok
    //IMPLEMENT�LVA
    native void fileSetEncryption(int handle, boolean x);
    native void fileSetHidden(int handle, boolean x);
    native void fileSetReadPermission(int handle, boolean x);
    native void fileSetWritePermission(int handle, boolean x);
    native void fileSetExecPermission(int handle, boolean x);
    native void fileSetLastRead(int handle, long x);
    native void fileSetLastWritten(int handle, long x);
    native void fileSetLastExecuted(int handle, long x);
    
    native boolean fileIsEncrypted(int handle);
    native boolean fileIsHidden(int handle);
    native boolean fileIsReadable(int handle);
    native boolean fileIsWritable(int handle);
    native boolean fileIsExecutable(int handle);
    native long fileGetLastRead(int handle);
    native long fileGetLastWritten(int handle);
    native long fileGetLastExecuted(int handle);
    
    /**
     * IMPLEMENT�LVA & TESZTELVE
     * Lez�rja a f�jlt, innent�l nem lesz el�rhet�. A f�jl lemezre �r�dik, ha m�dosult.
     * @param handle f�jlazonos�t�
     */
    native void fileClose(int handle);
    
    /**
     * IMPLEMENT�LVA & TESZTELVE
     * �thelyezi a handle-vel azonos�tott f�jlt/mapp�t az �ppen akt�v mapp�ba.
     * @param handle f�jlazonos�t�
     * @return true, ha sikeres volt a m�velet, egy�bk�nt false
     */
    native boolean fileMove(int handle);
    
    
    /**
     * IMPLEMENT�LVA & TESZTELVE
     * �tm�solja a handle-vel azonos�tott f�jlt az �ppen akt�v mapp�ba. Az �sszes f�jl-attrib�tum is m�sol�sra ker�l.
     * A met�dus nem h�vhat� meg mapp�kra.
     * @param handle f�jlazonos�t�
     * @return true, ha sikeres volt a m�velet, egy�bk�nt false
     */
    native boolean fileCopy(int handle);
    
    
    /**
     * IMPLEMENT�LVA & TESZTELVE
     * �tnevezi a f�jlt/mapp�t.
     * @param what a f�jl/mappa jelenlegi neve
     * @param to a f�jl/mappa �j neve
     * @return true, ha sikeres volt a m�velet, egy�bk�nt false
     */
    native boolean renameFile(String what, String to);


    /**
     * IMPLEMENT�LVA & TESZTELVE
     * F�jl vagy mappa t�rl�se.
     * @param name n�v
     * @return true, ha sikeres volt a m�velet, egy�bk�nt false
     */
    native boolean deleteFile(String name);
    


    /**
     * IMPLEMENT�LVA & TESZTELVE
     * �j f�jl l�trehoz�sa.
     * @param name n�v
     * @return true, ha sikeres volt a m�velet, egy�bk�nt false
     */
    native boolean createFile(String name);
    



    /**
     * IMPLEMENT�LVA & TESZTELVE
     * �j mappa l�trehoz�sa.
     * @param name n�v
     * @return true, ha sikeres volt a m�velet, egy�bk�nt false
     */
    native boolean createDirectory(String name);
}
