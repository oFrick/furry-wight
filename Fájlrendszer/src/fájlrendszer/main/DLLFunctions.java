package f�jlrendszer.main;

/**
 *
 * @author B�lint
 */
public class DLLFunctions {
    
    
    //String loc = DLLFunctions.class.getProtectionDomain().getCodeSource().getLocation().getPath();
   
    public DLLFunctions(String path) {
	System.load(path);
    }
    


    /**
     * Leform�zza a lemezt.
     * @param sectors h�ny szektort foglalunk le?
     */
    public native void formatDisk(int sectors);


    /**
     * IMPLEMENT�LVA & TESZTELVE
     * �tv�ltja az akt�v munkak�nyvt�rat egy �j mapp�ra. Ez lehet: "." (root), ".." (k�zvetlen sz�l� mappa) vagy
     * egy k�zvetlen almappa neve.
     * @param str az �j mappa neve, "." vagy ".."
     */
    public native void changeDirectory(String str);
    

    /**
     * IMPLEMENT�LVA & TESZTELVE
     * Kilist�zza az aktu�lis k�nyvt�rban tal�lhat� f�jlok �s mapp�k nev�t.
     * @return f�jlnevek
     */
    native String[] list();



    
    /**
     * IMPLEMENT�LVA & TESZTELVE
     * Megnyit egy f�jlt �r�sra/olvas�sra.
     * @param name a f�jl neve, csak az akt�v mapp�ban l�v� f�jl nyithat� meg
     * @return handle a f�jlra; a tov�bbiakban ezzel azonos�that�, 0 ha nem siker�lt megnyitni
     */
    public native int fileOpen(String name);
    



    /**
     * A f�jlban l�v� jelenlegi adatot t�rli, �s �t�ll�tja az �jra.
     * @param handle f�jlazonos�t�
     * @param data adat
     * @see fileOpen(String name)
     */
    native void fileSetData(int handle, byte[] data);
    


    /**
     * Adat hozz�f�z�se a f�jlhoz.
     * @param handle f�jlazonos�t�
     * @param data adat
     * @see fileOpen(String name)
     */
    native void fileAppendData(int handle, byte[] data);
    
    //adat besz�r�sa b�rhova
    //native void fileInsertData(int handle, byte[] data, long where);
    
    
    /**
     * Adat t�rl�se a f�jlb�l.
     * @param handle f�jlazonos�t�
     * @param from hanyadik b�jtt�l t�r�lj�k az adatot?
     * @param size mennyit?
     * @see fileOpen(String name)
     */
    native void fileEraseData(int handle, long from, long size);
    
    /**
     * Adatlek�r�s.
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
    public native void fileClose(int handle);
    
    /**
     * IMPLEMENT�LVA
     * �thelyezi a handle-vel azonos�tott f�jlt/mapp�t az �ppen akt�v mapp�ba.
     * @param handle f�jlazonos�t�
     */
    native void fileMove(int handle);
    
    
    /**
     * IMPLEMENT�LVA
     * �tm�solja a handle-vel azonos�tott f�jlt az �ppen akt�v mapp�ba. Az �sszes f�jl-attrib�tum is m�sol�sra ker�l.
     * A met�dus nem h�vhat� meg mapp�kra.
     * @param handle f�jlazonos�t�
     */
    native void fileCopy(int handle);
    
    
    /**
     * IMPLEMENT�LVA
     * �tnevezi a f�jlt/mapp�t.
     * @param to a f�jl/mappa �j neve
     */
    public native void renameFile(String to);


    /**
     * IMPLEMENT�LVA & TESZTELVE
     * F�jl vagy mappa t�rl�se.
     * @param name n�v
     */
    native void deleteFile(String name);


    /**
     * IMPLEMENT�LVA & TESZTELVE
     * �j f�jl l�trehoz�sa.
     * @param name n�v
     */
    native void createFile(String name);
    



    /**
     * IMPLEMENT�LVA & TESZTELVE
     * �j mappa l�trehoz�sa.
     * @param name n�v
     */
    native void createDirectory(String name);
}
