package main;

import files.*;


/**
 * Classe main utilisée pour les tests avec l'api Google Drive.
 * Executer GUIWindow du package gui pour le lancement de l'application complète.
 */
public class main {

    public static void main(String[] args) throws Exception {

        FileManager FM = FileManager.getInstance();
        FM.Init();
        FM.InitChateau();
        //FM.getChateauWorkspace().display();

        //FM.uploadToDriveChateau();
        FM.uploadToDriveVillage();
        //FM.downloadFromDriveChateau();
        //FM.downloadFromDriveVillage();
    }

}


