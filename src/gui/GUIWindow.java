package gui;

import com.google.api.services.drive.cmdline.DriveTools;
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

/**
 * Classe mère de la partie GUI
 * Intialise toutes les parties du GUI et affiche la fenêtre principale
 */
public class GUIWindow extends Application {


    /**
     * Field INSTANCE pour classe singleton
     */
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


    /**
     * Method start qui init tout les dossier de visite et la fenêtre
     *
     * @param primaryStage le stage JavaFX
     * @throws Exception lors d'un problème de chargement
     */
    @Override
    public void start(Stage primaryStage) throws Exception{

        FileManager.getInstance().Init();
        FileManager.getInstance().InitChateau();
        FileManager.getInstance().InitVillage();

        DriveTools.auth();

        this.primaryStage = primaryStage;
        primaryStage.setTitle("Backend Fuitière Numérique");

        initRootLayout();

    }

    /**
     * Charge la vue principale ainsi que les données à afficher
     */
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


    /**
     * Charge les vues associés aux onglets
     * @throws IOException
     */
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

    /**
     * Initialise la partie chateau du backend
     */
    public void loadChateauData() {
        GUIControllerChateau.getInstance().loadCastleData(FileManager.getInstance().getChateauWorkspace().getV());
    }

    /**
     * Initialis la partie village du backend
     */
    public void loadVillageData() {
        GUIControllerVillage.getInstance().loadVisitData(FileManager.getInstance().getVillageWorkspace().getV());
    }
}
