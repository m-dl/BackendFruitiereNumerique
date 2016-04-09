package gui.Controller;

import files.FileManager;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TabPane;

public class GUIMainViewController {

    private static GUIMainViewController INSTANCE = new GUIMainViewController();
    Task<Void> downloadMedia = new Task<Void>() {
        @Override
        protected Void call() throws Exception {
            //FileManager.getInstance().uploadToDriveChateau();
            FileManager.getInstance().uploadToDriveVillage();
            return null;
        }
    };
    @FXML
    private TabPane tabPane;
    @FXML
    private ProgressIndicator progressIndicator; // TODO: 09/04/2016 trouver un moyen de set progress pendant dl

    private GUIMainViewController()
    {}

    public static GUIMainViewController getInstance() {
        return INSTANCE;
    }

    // TODO: 08/04/2016 Faut des threads (ça bloque l'affichage javafx aussi )
    @FXML
    void uploadMedia() {
        if (getTabPane().getSelectionModel().getSelectedIndex() == 0) {
            System.out.println("uploadMedia chateau");
            new Thread(downloadMedia).start();
        } else if (getTabPane().getSelectionModel().getSelectedIndex() == 1) {
            System.out.println("uploadMedia village");
            FileManager.getInstance().uploadToDriveVillage();
        }
    }

    // TODO: 08/04/2016 Faut des threads ici aussi
    @FXML
    void downloadMedia() {
        if (getTabPane().getSelectionModel().getSelectedIndex() == 0) {
            System.out.println("downloadMedia chateau");
            FileManager.getInstance().downloadFromDriveChateau();
        } else if (getTabPane().getSelectionModel().getSelectedIndex() == 1) {
            System.out.println("downloadMedia village");
            FileManager.getInstance().downloadFromDriveVillage();
        }
    }

    public TabPane getTabPane() {
        return tabPane;
    }
}
