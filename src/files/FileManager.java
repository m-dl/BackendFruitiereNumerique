package files;

import entities.Location;

public class FileManager {
	
    final private static String WORKSPACE = "medias";
    final private static String CHATEAU = "/chateau";
    final private static String VILLAGE = "/village";
    private Location chateauWorkspace;
    private Location villageWorkspace;
    
    public FileManager() {
        this.setChateauWorkspace(new Location());
        this.setVillageWorkspace(new Location());
    }
    
    /**
     * @return the villageWorkspace
     */
    public Location getVillageWorkspace() {
        return villageWorkspace;
    }

    /**
     * @param villageWorkspace the villageWorkspace to set
     */
    public void setVillageWorkspace(Location villageWorkspace) {
        this.villageWorkspace = villageWorkspace;
    }

    /**
     * @return the chateauWorkspace
     */
    public Location getChateauWorkspace() {
        return chateauWorkspace;
    }

    /**
     * @param chateauWorkspace the chateauWorkspace to set
     */
    public void setChateauWorkspace(Location chateauWorkspace) {
        this.chateauWorkspace = chateauWorkspace;
    }
	
    // Init global media workspace
    public void Init() {
        FileTools.CreateDirectory(WORKSPACE);
    }
    
    // Init chateau media workspace
    public void InitChateau() {
        FileTools.CreateDirectory(WORKSPACE + CHATEAU);
        FileTools.ListDirectoryContent(WORKSPACE + CHATEAU, chateauWorkspace);
    }
    
    // Init village media workspace
    public void InitVillage() {
        FileTools.CreateDirectory(WORKSPACE + VILLAGE);
        FileTools.ListDirectoryContent(WORKSPACE + VILLAGE, villageWorkspace);
    }
}
