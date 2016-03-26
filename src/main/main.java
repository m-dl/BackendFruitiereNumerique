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

  public static void main(String[] args) throws Exception {
	  
    FileManager FM = FileManager.getInstance();
    FM.Init();
    FM.InitChateau();
    //FM.getChateauWorkspace().display();

    //DriveSample.launch();
    
    ZipManager.zipFolder("C:/Users/Maxime.PC/Desktop/CERI COURS/eclipse/JAVA/BackendFruitiereNumerique/medias","C:/Users/Maxime.PC/Desktop/t.zip");
    ZipManager.unZipIt("C:/Users/Maxime.PC/Desktop/t.zip","C:/Users/Maxime.PC/Desktop");

  }

}


