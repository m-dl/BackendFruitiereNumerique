package files;

import entities.Location;

/**
 * @author Maxime
 */
public class FileManager {
	
    public final static String WORKSPACE = "medias";
    public final static String CHATEAU = "chateau";
    final private static String VILLAGE = "village";
	final public static String PHOTOS = "photo";
	final public static String INTERIEUR = "interieur";
	final public static String VIDEOS = "video";
	final public static String _360 = "360";
    final public static String OVERVIEW_FOLDER = "visite-overview";
    final public static String INFO_FOLDER = "visite-info";
	final public static String PRESENTATION_FR = "content_fr.txt";
    final public static String PRESENTATION_EN = "content_en.txt";
    final public static String LENGTH_FR = "duree_fr.txt";
    final public static String LENGTH_EN = "duree_en.txt";
    final public static String MARKER = "marker.txt";
    final public static String ROAD = "chemin.txt";
    private Location chateauWorkspace;
    private Location villageWorkspace;
    
    private static FileManager INSTANCE = new FileManager();
    
	// Singleton
	public static FileManager getInstance() {	
		return INSTANCE;
	}
	
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
        FileTools.CreateDirectory(WORKSPACE + "/" + CHATEAU);
        FileTools.ListVisit(WORKSPACE + "/" + CHATEAU, chateauWorkspace);
    }
    
    // Init village media workspace
    public void InitVillage() {
        FileTools.CreateDirectory(WORKSPACE + "/" + VILLAGE);
        FileTools.ListVisit(WORKSPACE + "/" + VILLAGE, villageWorkspace);
    }
}
