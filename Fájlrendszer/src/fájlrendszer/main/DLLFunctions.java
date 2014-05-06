package fájlrendszer.main;

/**
 *
 * @author Bálint
 */
public class DLLFunctions {
    
    
    //String loc = DLLFunctions.class.getProtectionDomain().getCodeSource().getLocation().getPath();
   
    public DLLFunctions(String path) {
	System.load(path);
    }
    


    /**
     * Leformázza a lemezt.
     * @param sectors hány szektort foglalunk le?
     */
    public native void formatDisk(int sectors);


    /**
     * IMPLEMENTÁLVA & TESZTELVE
     * Átváltja az aktív munkakönyvtárat egy új mappára. Ez lehet: "." (root), ".." (közvetlen szülõ mappa) vagy
     * egy közvetlen almappa neve.
     * @param str az új mappa neve, "." vagy ".."
     */
    public native void changeDirectory(String str);
    

    /**
     * IMPLEMENTÁLVA & TESZTELVE
     * Kilistázza az aktuális könyvtárban található fájlok és mappák nevét.
     * @return fájlnevek
     */
    native String[] list();



    
    /**
     * IMPLEMENTÁLVA & TESZTELVE
     * Megnyit egy fájlt írásra/olvasásra.
     * @param name a fájl neve, csak az aktív mappában lévõ fájl nyitható meg
     * @return handle a fájlra; a továbbiakban ezzel azonosítható, 0 ha nem sikerült megnyitni
     */
    public native int fileOpen(String name);
    



    /**
     * A fájlban lévõ jelenlegi adatot törli, és átállítja az újra.
     * @param handle fájlazonosító
     * @param data adat
     * @see fileOpen(String name)
     */
    native void fileSetData(int handle, byte[] data);
    


    /**
     * Adat hozzáfûzése a fájlhoz.
     * @param handle fájlazonosító
     * @param data adat
     * @see fileOpen(String name)
     */
    native void fileAppendData(int handle, byte[] data);
    
    //adat beszúrása bárhova
    //native void fileInsertData(int handle, byte[] data, long where);
    
    
    /**
     * Adat törlése a fájlból.
     * @param handle fájlazonosító
     * @param from hanyadik bájttól töröljük az adatot?
     * @param size mennyit?
     * @see fileOpen(String name)
     */
    native void fileEraseData(int handle, long from, long size);
    
    /**
     * Adatlekérés.
     * @param handle fájlazonosító
     * @return a fájlban lévõ adat bájt tömbben
     */
    native byte[] fileGetData(int handle);
    
    
    //attribútum accessorok
    //IMPLEMENTÁLVA
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
     * IMPLEMENTÁLVA & TESZTELVE
     * Lezárja a fájlt, innentõl nem lesz elérhetõ. A fájl lemezre íródik, ha módosult.
     * @param handle fájlazonosító
     */
    public native void fileClose(int handle);
    
    /**
     * IMPLEMENTÁLVA
     * Áthelyezi a handle-vel azonosított fájlt/mappát az éppen aktív mappába.
     * @param handle fájlazonosító
     */
    native void fileMove(int handle);
    
    
    /**
     * IMPLEMENTÁLVA
     * Átmásolja a handle-vel azonosított fájlt az éppen aktív mappába. Az összes fájl-attribútum is másolásra kerül.
     * A metódus nem hívható meg mappákra.
     * @param handle fájlazonosító
     */
    native void fileCopy(int handle);
    
    
    /**
     * IMPLEMENTÁLVA
     * Átnevezi a fájlt/mappát.
     * @param to a fájl/mappa új neve
     */
    public native void renameFile(String to);


    /**
     * IMPLEMENTÁLVA & TESZTELVE
     * Fájl vagy mappa törlése.
     * @param name név
     */
    native void deleteFile(String name);


    /**
     * IMPLEMENTÁLVA & TESZTELVE
     * Új fájl létrehozása.
     * @param name név
     */
    native void createFile(String name);
    



    /**
     * IMPLEMENTÁLVA & TESZTELVE
     * Új mappa létrehozása.
     * @param name név
     */
    native void createDirectory(String name);
}
