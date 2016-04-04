package gui;

import files.FileManager;
import gui.Controller.*;
import gui.Controller.chateau.GUIControllerChateau;
import gui.Controller.chateau.GUIControllerChateauIPForm;
import gui.Controller.chateau.GUIControllerChateauVisitForm;
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

    public FileManager FM = FileManager.getInstance();
    private GUIUtilities utilities;
    private static GUIWindow INSTANCE = new GUIWindow();


    private Pane rootLayout;
    private Stage primaryStage;

    private GUIMainViewController guiMainViewController;

    private GUIControllerChateau guiControllerChateau;
    private GUIControllerVillage guiControllerVillage;
    private GUIControllerPhotos guiControllerPhotos;

    private GUIControllerChateauVisitForm guiControllerChateauVisitForm;
    private GUIControllerChateauIPForm guiControllerChateauIPForm;


    private GUIFormsController guiFormsController;



    public static void main(String[] args) {
        launch(args);
    }

    public GUIWindow() {
        this.utilities = new GUIUtilities();
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
        loadControllers();

        initRootLayout();

    }

    public void initRootLayout() {

        try {
            rootLayout = (Pane) utilities.loadLayout("view/mainView.fxml", guiMainViewController);

            loadTabPane();
            loadChateauData();
            loadVillageData();

            // Show the scene containing the root layout.
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();


        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void loadTabPane() throws IOException {


        TabPane tabPane = guiMainViewController.getTabPane();

        AnchorPane chateauPane = (AnchorPane) utilities.loadLayout("view/chateau/ChateauView.fxml",guiControllerChateau);
        AnchorPane villagePane = (AnchorPane) utilities.loadLayout("view/village/VillageView.fxml",guiControllerVillage);
        AnchorPane photosPane = (AnchorPane) utilities.loadLayout("view/photos/photosView.fxml",guiControllerPhotos);



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

    public void loadControllers() {

        this.guiMainViewController = GUIMainViewController.getInstance();

        this.guiControllerChateau = GUIControllerChateau.getInstance();
        this.guiControllerVillage = GUIControllerVillage.getInstance();
        this.guiControllerPhotos = GUIControllerPhotos.getInstance();

        this.guiControllerChateauVisitForm = GUIControllerChateauVisitForm.getInstance();
        this.guiControllerChateauIPForm = GUIControllerChateauIPForm.getInstance();

        this.guiFormsController = new GUIFormsController(this);
    }

    public void test() {
        System.out.println("save stuff");
    }

    public void loadChateauData() {
        guiControllerChateau.loadCastleData(FM.getChateauWorkspace().getV());
    }

    public void loadVillageData() {
        guiControllerVillage.loadVisitData(FM.getVillageWorkspace().getV());
    }
}
