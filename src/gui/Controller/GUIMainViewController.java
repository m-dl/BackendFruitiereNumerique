package gui.Controller;

import gui.GUIWindow;
import javafx.fxml.FXML;
import javafx.scene.control.TabPane;

public class GUIMainViewController {

    private GUIWindow guiWindow;

    @FXML
    private TabPane tabPane;

    public GUIMainViewController(GUIWindow guiWindow) {
        this.guiWindow = guiWindow;
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
