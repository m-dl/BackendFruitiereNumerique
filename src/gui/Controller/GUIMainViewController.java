package gui.Controller;

import gui.GUIWindow;
import javafx.fxml.FXML;
import javafx.scene.control.TabPane;

public class GUIMainViewController {

    private static GUIMainViewController INSTANCE = new GUIMainViewController();

    @FXML
    private TabPane tabPane;

    private GUIMainViewController()
    {}

    public static GUIMainViewController getInstance() {
        return INSTANCE;
    }

    @FXML
    void uploadMedia() {
        System.out.println("uploadMedia");
    }

    @FXML
    void downloadMedia() {
        System.out.println("downloadMedia");
    }


    public TabPane getTabPane() {
        return tabPane;
    }
}
