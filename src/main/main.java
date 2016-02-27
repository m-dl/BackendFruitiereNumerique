package main;

import com.google.api.services.samples.drive.cmdline.DriveSample;

import files.FileManager;

import files.*;

/*
 * Project > Properties > Project References > cocher la case du DriveAPI Puis aller sur la
 * fonction DriveSample.test() plus bas ici, clic droit > fix project setup > cocher et valider le
 * lien Et ca marche ...
 */
public class main {

  public static void main(String[] args) {
    String dir1 = "totot";
    String dir = dir1 + "/totot/";
    String file1 = dir1 + "/" + "totott.txt";
    String file2 = dir + "tt.txt";
    String dir2 = "totorasse.txt";
    
    FileManager FM = new FileManager();
    FM.Init();
    FM.InitChateau();
    FM.getChateauWorkspace().display();

    // FileFactory.CreateDirectory(dir1);
    // FileFactory.ListDirectoryContent(dir1);
    /*
     * FileFactory.CreateDirectory(dir); FileFactory.CreateFile(file1);
     * FileFactory.CreateFile(file2); FileFactory.Write(file1, "salut rasse"); String output =
     * FileFactory.Read(file1); FileFactory.Move(file1, dir2); FileFactory.Delete(dir1);
     * System.out.println(output);
     */

    //DriveSample.launch();
    
    // zipmanager bug si le zip existe deja, et ne zip pas le dossier parent qu'on veut zipper et comment unzip ? 
    //ZipManager.zipDirectory("C:/Users/Maxime.PC/Pictures/toto", "C:/Users/Maxime.PC/Pictures/toto/t.zip");
  }

}
