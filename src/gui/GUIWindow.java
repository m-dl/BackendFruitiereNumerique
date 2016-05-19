package gui;

import com.google.api.services.drive.cmdline.DriveTools;
import com.google.api.services.drive.model.File;
import files.FileManager;
import gui.Controller.GUIFormsController;
import gui.Controller.GUIMainViewController;
import gui.Controller.chateau.GUIControllerChateau;
import gui.Controller.photo.GUIControllerPhotos;
import gui.Controller.village.GUIControllerVillage;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class GUIWindow extends Application {


    private static GUIWindow INSTANCE = new GUIWindow();
    private Pane rootLayout;
    private Stage primaryStage;


    public GUIWindow() {
    }

    public static void main(String[] args) {
        launch(args);
    }

    public static GUIWindow getInstance() {
        return INSTANCE;
    }


    @Override
    public void start(Stage primaryStage) throws Exception{

        FileManager.getInstance().Init();
        FileManager.getInstance().InitChateau();
        FileManager.getInstance().InitVillage();

        DriveTools.auth();

        this.primaryStage = primaryStage;
        primaryStage.setTitle("Ajout de fichiers");

        initRootLayout();

    }

    public void initRootLayout() {

        try {
            rootLayout = (Pane) GUIUtilities.loadLayout("view/mainView.fxml", GUIMainViewController.getInstance());

            loadTabPane();
            loadChateauData();
            loadVillageData();


            // Show the scene containing the root layout.
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
            primaryStage.setResizable(false);


        } catch (IOException e) {
            e.printStackTrace();
            GUIFormsController.getInstance().displayExceptionAlert(e,"Erreur lors du chargement de la fenêtre").showAndWait();

        }
    }



    public void loadTabPane() throws IOException {


        TabPane tabPane = GUIMainViewController.getInstance().getTabPane();

        AnchorPane chateauPane = (AnchorPane) GUIUtilities.loadLayout("view/chateau/ChateauView.fxml", GUIControllerChateau.getInstance());
        AnchorPane villagePane = (AnchorPane) GUIUtilities.loadLayout("view/village/VillageView.fxml", GUIControllerVillage.getInstance());
        AnchorPane photosPane = (AnchorPane) GUIUtilities.loadLayout("view/photos/photosView.fxml", GUIControllerPhotos.getInstance());



        Tab chateauTab = new Tab();
        chateauTab.setText("Visite Château");
        chateauTab.setContent(chateauPane);


        Tab villageTab = new Tab();
        villageTab.setText("Visite Village");
        villageTab.setContent(villagePane);

        /*
         onglet non utilisé

        Pour utilisation, modifier le fichier view/photos/photosView.fxml avec SceneBuilder
        et décommenter ces blocs
        Tab photosTab = new Tab();
        photosTab.setDisable(true);
        photosTab.setText("Stockage Photos");
        photosTab.setContent(photosPane);
        */

        tabPane.getTabs().clear();
        tabPane.getTabs().addAll(chateauTab,villageTab);
        //tabPane.getTabs().addAll(chateauTab,villageTab,photosTab);

    }

    public void loadChateauData() {
        GUIControllerChateau.getInstance().loadCastleData(FileManager.getInstance().getChateauWorkspace().getV());
    }

    public void loadVillageData() {
        GUIControllerVillage.getInstance().loadVisitData(FileManager.getInstance().getVillageWorkspace().getV());
    }
}
