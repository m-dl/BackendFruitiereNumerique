package gui.Controller;

import files.FileManager;
import gui.Controller.chateau.GUIControllerChateau;
import gui.Controller.village.GUIControllerVillage;
import javafx.concurrent.Service;
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


    boolean operationInprogress = false;

    public TabPane tabPane;

    public ImageView logoL;
    public Label operationIP;
    public ProgressIndicator progressIndicator;

    public String operation = "";
    private GUIMainViewController() {}

    public static GUIMainViewController getInstance() {
        return INSTANCE;
    }

    private static GUIMainViewController INSTANCE = new GUIMainViewController();

    Service<Void> fileSaveService = new Service<Void>(){
        @Override
        protected Task<Void> createTask() {
            return new Task<Void>(){

                @Override
                protected Void call() throws Exception {
                        operationInprogress = true;

                        if (operation.equals("upC")) {

                            updateMessage("Téléversement des médias du château en cours");
                            progressIndicator.setVisible(true);

                            FileManager.getInstance().uploadToDriveChateau();

                            updateMessage("Téléversement terminé");
                            progressIndicator.setVisible(false);

                        } else if (operation.equals("upV")) {

                            updateMessage("Téléversement des médias du village en cours");
                            progressIndicator.setVisible(true);

                            FileManager.getInstance().uploadToDriveVillage();

                            updateMessage("Téléversement terminé");
                            progressIndicator.setVisible(false);

                        } else if (operation.equals("downC")) {

                            updateMessage("Téléchargement des médias du château en cours");
                            progressIndicator.setVisible(true);

                            FileManager.getInstance().downloadFromDriveChateau();

                            updateMessage("Téléchargement terminé");
                            progressIndicator.setVisible(false);

                            GUIControllerChateau.getInstance().reloadCastleData();

                        } else if (operation.equals("downV")) {

                            updateMessage("Téléchargement des médias du village en cours");
                            progressIndicator.setVisible(true);

                            FileManager.getInstance().downloadFromDriveVillage();

                            updateMessage("Téléchargement terminé");
                            progressIndicator.setVisible(false);

                            GUIControllerVillage.getInstance().reloadVillageData();
                        }
                        operationInprogress = false;


                    return null;
                }




            };

        }

    };


    public void updateMessage(String message) {
        operationIP.setText(message);
    }

    protected void displayError() {
        String error = "Une opération est déjà en cours, veuillez attendre al finalisation de celle-ci";
        GUIFormsController.getInstance().displayWarningAlert("Opération en cours",error).showAndWait();
    }

    private void runThread() {
        if (!operationInprogress) {
            fileSaveService.restart();
        }
        else {
            displayError();
        }
    }

    @FXML
    void uploadMedia() {
        operationIP.textProperty().bind(fileSaveService.messageProperty());

        if (getTabPane().getSelectionModel().getSelectedIndex() == 0) {
            operation = "upC";
        } else if (getTabPane().getSelectionModel().getSelectedIndex() == 1) {
            operation = "upV";
        }
        runThread();

    }

    @FXML
    void downloadMedia() {
        operationIP.textProperty().bind(fileSaveService.messageProperty());

        if (getTabPane().getSelectionModel().getSelectedIndex() == 0) {
            operation = "downC";
        } else if (getTabPane().getSelectionModel().getSelectedIndex() == 1) {
            operation = "downV";
        }
        runThread();


    }

    public TabPane getTabPane() {
        return tabPane;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        logoL.setImage(new Image("logo.png"));
        progressIndicator.setVisible(false);
        updateMessage("En attente");
    }
}
