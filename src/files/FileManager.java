package files;

import java.io.File;

import com.google.api.services.drive.cmdline.DriveTools;
import com.google.api.services.drive.cmdline.View;
import gui.Controller.GUIFormsController;

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
	final public static String _360_ = "360_";
    final public static String OVERVIEW_FOLDER = "visite-overview";
    final public static String INFO_FOLDER = "visite-info";
	final public static String NAME_EN = "name_en.txt";
	final public static String PRESENTATION_FR = "content_fr.txt";
    final public static String PRESENTATION_EN = "content_en.txt";
    final public static String LENGTH_FR = "duree_fr.txt";
    final public static String LENGTH_EN = "duree_en.txt";
    final public static String MARKER = "marker.txt";
    final public static String ROAD = "chemin.txt";
    private entities.chateau.Location chateauWorkspace;
    private entities.village.Location villageWorkspace;
    
    private static FileManager INSTANCE = new FileManager();
    
	// Singleton
	public static FileManager getInstance() {	
		return INSTANCE;
	}
	
    public FileManager() {
        this.setChateauWorkspace(new entities.chateau.Location());
        this.setVillageWorkspace(new entities.village.Location());
    }
    
    /**
     * @return the villageWorkspace
     */
    public entities.village.Location getVillageWorkspace() {
        return villageWorkspace;
    }

    /**
     * @param villageWorkspace the villageWorkspace to set
     */
    public void setVillageWorkspace(entities.village.Location villageWorkspace) {
        this.villageWorkspace = villageWorkspace;
    }

    /**
     * @return the chateauWorkspace
     */
    public entities.chateau.Location getChateauWorkspace() {
        return chateauWorkspace;
    }

    /**
     * @param chateauWorkspace the chateauWorkspace to set
     */
    public void setChateauWorkspace(entities.chateau.Location chateauWorkspace) {
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
        FileTools.ListVisitChateau(WORKSPACE + "/" + CHATEAU, chateauWorkspace);
    }
    
    // Init village media workspace
    public void InitVillage() {
        FileTools.CreateDirectory(WORKSPACE + "/" + VILLAGE);
        FileTools.ListVisitVillage(WORKSPACE + "/" + VILLAGE, villageWorkspace);
    }
    
    // Upload media to drive Chateau
    public void uploadToDriveChateau() {
        try {
        	// check if exist
        	if(FileTools.Exist(new File(WORKSPACE + "/" + CHATEAU))) {
        		// zip folder
	        	View.header1("Zipping file ...");
				ZipManager.zipFolder(WORKSPACE + "/" + CHATEAU, WORKSPACE + "/" + CHATEAU + ZIP_EXT);
				View.header1("Zipping file success !");
				// upload zip to drive
				DriveTools.upload(DriveTools.UPLOAD_FILE_PATH_CHATEAU, DriveTools.CHATEAU_MEDIAS);
				// delete zip
				FileTools.Delete(WORKSPACE + "/" + CHATEAU + ZIP_EXT);
        	}
		} catch (Exception exception) {
			exception.printStackTrace();
            GUIFormsController.getInstance().displayExceptionAlert(exception,"Erreur upload sur le drive chateau").showAndWait();
		}
    }
    
    // Download media from drive Chateau
    public void downloadFromDriveChateau() {
    	// download file
    	if(DriveTools.download(DriveTools.CHATEAU_MEDIAS)) {
	    	// delete old media folder
	    	if(FileTools.Exist(new File(WORKSPACE + "/" + CHATEAU))) {
		    	FileTools.Delete(WORKSPACE + "/" + CHATEAU);
	    	}		
	    	// unzip
	    	View.header1("Unzipping file ...");
	        ZipManager.unZip(WORKSPACE + "/" + CHATEAU + ZIP_EXT, WORKSPACE);
	        View.header1("Unzipping file success !");
	        // delete zip
	        FileTools.Delete(WORKSPACE + "/" + CHATEAU + ZIP_EXT);
    	}
    }
    
    // Upload media to drive Village
    public void uploadToDriveVillage() {
        try {
        	// check if exist
        	if(FileTools.Exist(new File(WORKSPACE + "/" + VILLAGE))) {
        		// zip folder
	        	View.header1("Zipping file ...");
				ZipManager.zipFolder(WORKSPACE + "/" + VILLAGE, WORKSPACE + "/" + VILLAGE + ZIP_EXT);
				View.header1("Zipping file success !");
				// upload zip to drive
				DriveTools.upload(DriveTools.UPLOAD_FILE_PATH_VILLAGE, DriveTools.VILLAGE_MEDIAS);
				// delete zip
				FileTools.Delete(WORKSPACE + "/" + VILLAGE + ZIP_EXT);
        	}
		} catch (Exception exception) {
			exception.printStackTrace();
            GUIFormsController.getInstance().displayExceptionAlert(exception,"Erreur upload sur le drive village").showAndWait();

        }
    }
    
    // Download media from drive Village
    public void downloadFromDriveVillage() {
    	// download file
    	if(DriveTools.download(DriveTools.VILLAGE_MEDIAS)) {
	    	// delete old media folder
	    	if(FileTools.Exist(new File(WORKSPACE + "/" + VILLAGE))) {
	    		FileTools.Delete(WORKSPACE + "/" + VILLAGE);
	    	}
	    	// unzip
	    	View.header1("Unzipping file ...");
	        ZipManager.unZip(WORKSPACE + "/" + VILLAGE + ZIP_EXT, WORKSPACE);
	        View.header1("Unzipping file success !");
	        // delete zip
	        FileTools.Delete(WORKSPACE + "/" + VILLAGE + ZIP_EXT);
    	}
    }
}
