package main;

import com.google.api.services.drive.cmdline.DriveTools;

import files.*;


/*
 * Project > Properties > Project References > cocher la case du DriveAPI Puis aller sur la
 * fonction DriveSample.test() plus bas ici, clic droit > fix project setup > cocher et valider le
 * lien Et ca marche ...
 */
/**
 * @author Maxime
 */
public class main {

  public static void main(String[] args) throws Exception {
	//TODO: supprimer visite ou PI, une fonction dans location et une dans visite ou PI  
    FileManager FM = FileManager.getInstance();
    FM.Init();
    FM.InitChateau();
    //FM.getChateauWorkspace().display();
    
    //ZipManager.zipFolder(FileManager.WORKSPACE + "/" + FileManager.CHATEAU, FileManager.WORKSPACE + "/" + FileManager.CHATEAU + FileManager.ZIP_EXT);
    //DriveTools.download(DriveTools.VILLAGE_MEDIAS);
    //ZipManager.unZip("medias/VisiteTablette.zip","C:/Users/Maxime.PC/Desktop");

  }

}


