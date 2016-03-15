package gui;

import files.FileManager;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class GUIWindow extends Application {

    public FileManager FM = FileManager.getInstance();
    private GUIControllerChateau guiControllerChateau;
    private Pane rootLayout;
    private Stage primaryStage;
    private GUIControllerChateauVisitFrom guiControllerChateauVisitForm;


    public static void main(String[] args) {
        launch(args);
    }


    @Override
    public void start(Stage primaryStage) throws Exception{

        FM.Init();
        FM.InitChateau();

        this.primaryStage = primaryStage;
        primaryStage.setTitle("Ajout de fichiers");
        loadControllers();

        initRootLayout();

    }

    public void initRootLayout() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(GUIWindow.class.getResource("view/mainView.fxml"));

            fxmlLoader.setController(guiControllerChateau);

            rootLayout = fxmlLoader.load();

            loadData();

            // Show the scene containing the root layout.
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadControllers() {

        this.guiControllerChateau = new GUIControllerChateau(this);
        this.guiControllerChateauVisitForm = new GUIControllerChateauVisitFrom(this);

    }

    public void displayChateauForm(boolean isNewVisit) {
        try {

            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getClassLoader().getResource("gui/view/chateau/visitForm.fxml"));
            fxmlLoader.setController(guiControllerChateauVisitForm);

            ScrollPane root = fxmlLoader.load();

            if (!isNewVisit) {
                guiControllerChateauVisitForm.fillInputs(guiControllerChateau.getSelectedVisit());
            }

            Stage stage = new Stage();
            stage.setTitle("Formulaire de visite");
            stage.setScene(new Scene(root));
            stage.show();


        } catch (IOException e) {
            e.printStackTrace();
        }



    }

    public void loadData() {
        guiControllerChateau.loadCastleData(FM.getChateauWorkspace().getV());
    }
}
