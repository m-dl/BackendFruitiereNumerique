package gui;

import files.FileManager;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class GUIWindow extends Application {

    public FileManager FM = FileManager.getInstance();
    private GUIControllerCastle guiControllerCastle;
    private Pane rootLayout;
    private Stage primaryStage;


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

            //Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(GUIWindow.class.getResource("sample.fxml"));

            fxmlLoader.setController(guiControllerCastle);

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

        this.guiControllerCastle = new GUIControllerCastle(this);

    }

    public void loadData() {
        guiControllerCastle.loadCastleData(FM.getChateauWorkspace().getV());
    }
}
