package gui;

import com.google.api.client.auth.oauth2.BearerToken;
import com.google.api.client.http.HttpRequest;
import com.google.api.services.drive.cmdline.DriveTools;
import files.FileManager;
import gui.Controller.*;
import gui.Controller.chateau.GUIControllerChateau;
import gui.Controller.chateau.GUIControllerChateauIPForm;
import gui.Controller.chateau.GUIControllerChateauVisitForm;
import gui.Controller.photo.GUIControllerPhotos;
import gui.Controller.village.GUIControllerVillage;
import javafx.application.Application;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebEvent;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

import java.io.Console;
import java.io.IOException;
import java.io.PrintStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class GUIWindow extends Application {

    static final String APP_ID = "...";
    static final String REDIRECT_URL = "https://login.live.com/oauth20_desktop.srf";
    static final String RESPONSE_TYPE = "token";
    static final String SCOPE = "wl.signin%20wl.offline_access";

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


        Tab photosTab = new Tab();
        photosTab.setDisable(true);
        photosTab.setText("Stockage Photos");
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
