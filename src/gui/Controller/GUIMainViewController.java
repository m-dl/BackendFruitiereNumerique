package gui.Controller;

import files.FileManager;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.ResourceBundle;

public class GUIMainViewController implements Initializable {

    @FXML
    public ImageView logoL;
    @FXML
    public Label test;

    private static GUIMainViewController INSTANCE = new GUIMainViewController();
    //threads
    Task<Void> uploadChateauMedia = new Task<Void>() {
        @Override
        protected Void call() throws Exception {
            FileManager.getInstance().uploadToDriveChateau();
            return null;
        }
    };
    Task<Void> uploadVillageMedia = new Task<Void>() {
        @Override
        protected Void call() throws Exception {
            FileManager.getInstance().uploadToDriveVillage();
            return null;
        }
    };
    Task<Void> downloadChateauMedia = new Task<Void>() {
        @Override
        protected Void call() throws Exception {
            FileManager.getInstance().downloadFromDriveChateau();
            return null;
        }
    };
    Task<Void> downloadVillageMedia = new Task<Void>() {
        @Override
        protected Void call() throws Exception {
            FileManager.getInstance().downloadFromDriveVillage();
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

    @FXML
    void uploadMedia() {
        if (getTabPane().getSelectionModel().getSelectedIndex() == 0) {
            System.out.println("uploadMedia chateau");
            new Thread(uploadChateauMedia).start();
        } else if (getTabPane().getSelectionModel().getSelectedIndex() == 1) {
            System.out.println("uploadMedia village");
            FileManager.getInstance().uploadToDriveVillage();
            new Thread(uploadVillageMedia).start();
        }
    }

    @FXML
    void downloadMedia() {
        if (getTabPane().getSelectionModel().getSelectedIndex() == 0) {
            System.out.println("downloadMedia chateau");
            new Thread(downloadChateauMedia).start();
        } else if (getTabPane().getSelectionModel().getSelectedIndex() == 1) {
            System.out.println("downloadMedia village");
            new Thread(downloadVillageMedia).start();
        }
    }

    public TabPane getTabPane() {
        return tabPane;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        logoL.setImage(new Image("logo.png"));
    }
}
