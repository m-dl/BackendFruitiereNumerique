package main;

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
    
    //FM.uploadToDriveChateau();
    //FM.uploadToDriveVillage();
    //FM.downloadFromDriveChateau();
    //FM.downloadFromDriveVillage();
  }

}


