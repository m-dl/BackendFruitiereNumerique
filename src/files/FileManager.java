package files;

import java.io.File;

import com.google.api.services.drive.cmdline.DriveTools;

import entities.Location;

/**
 * @author Maxime
 */
public class FileManager {
	
    final public static String CLIENT_DRIVE_JSON_CONTENT = "{\"installed\":{\"client_id\":\"\",\""
    		+ "project_id\":\"\",\"auth_uri\":\"\",\"token_uri\":\"\",\""
    		+ "auth_provider_x509_cert_url\":\"\",\"client_secret\":\"\",\"redirect_uris\":[]}}";
	final public static String WORKSPACE = "medias";
    final public static String RES = "res";
    final public static String CLIENT_DRIVE_JSON = "client_secrets.json";
    final public static String ZIP_EXT = ".zip";
    final public static String CHATEAU = "VisiteChateau";
    final public static String VILLAGE = "VisiteTablette";
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
        FileTools.CreateDirectory(RES);
        File clientDriveJSON = new File(RES + "/" + CLIENT_DRIVE_JSON);
        if(!FileTools.Exist(clientDriveJSON)) {
        	FileTools.CreateFile(RES + "/" + CLIENT_DRIVE_JSON);
	        FileTools.Write(new File(RES + "/" + CLIENT_DRIVE_JSON), CLIENT_DRIVE_JSON_CONTENT);
        }
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
    
    // Upload media to drive Chateau
    public void uploadToDriveChateau() {
        try {
			ZipManager.zipFolder(FileManager.WORKSPACE + "/" + FileManager.CHATEAU, FileManager.WORKSPACE + "/" + FileManager.CHATEAU + FileManager.ZIP_EXT);
			DriveTools.upload(DriveTools.UPLOAD_FILE_PATH_CHATEAU, DriveTools.CHATEAU_MEDIAS);
		} catch (Exception exception) {
			// TODO Auto-generated catch block
			exception.printStackTrace();
		}
    }
    
    // Download media from drive Chateau
    public void downloadFromDriveChateau() {
        
    }
}
