package gui;

import files.FileManager;
import gui.Controller.*;
import gui.Controller.chateau.GUIControllerChateau;
import gui.Controller.chateau.GUIControllerChateauIPForm;
import gui.Controller.chateau.GUIControllerChateauVisitForm;
import gui.Controller.photo.GUIControllerPhotos;
import gui.Controller.village.GUIControllerVillage;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class GUIWindow extends Application {

    public FileManager FM = FileManager.getInstance();
    private static GUIWindow INSTANCE = new GUIWindow();


    private Pane rootLayout;
    private Stage primaryStage;


    public static void main(String[] args) {
        launch(args);
    }

    public GUIWindow() {
    }

    public static GUIWindow getInstance() {
        return INSTANCE;
    }


    @Override
    public void start(Stage primaryStage) throws Exception{

        FM.Init();
        FM.InitChateau();
        FM.InitVillage();

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


        Tab photosTab = new Tab();
        photosTab.setText("Stockage P");
        photosTab.setContent(photosPane);


        tabPane.getTabs().clear();
        tabPane.getTabs().addAll(chateauTab,villageTab,photosTab);

    }

    public void loadChateauData() {
        GUIControllerChateau.getInstance().loadCastleData(FM.getChateauWorkspace().getV());
    }

    public void loadVillageData() {
        GUIControllerVillage.getInstance().loadVisitData(FM.getVillageWorkspace().getV());
    }
}
