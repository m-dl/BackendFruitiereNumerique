package main;

import com.google.api.services.samples.drive.cmdline.DriveSample;

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

  public static void main(String[] args) {
	  
    FileManager FM = FileManager.getInstance();
    FM.Init();
    FM.InitChateau();
    FM.getChateauWorkspace().display();

    //DriveSample.launch();
    
    // zipmanager bug si le zip existe deja, et ne zip pas le dossier parent qu'on veut zipper et comment unzip ? 
    //ZipManager.zipDirectory("C:/Users/Maxime.PC/Pictures/toto", "C:/Users/Maxime.PC/Pictures/toto/t.zip");
  }

}
