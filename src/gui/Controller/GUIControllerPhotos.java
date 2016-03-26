package gui.Controller;

import gui.GUIWindow;
import javafx.fxml.FXML;

public class GUIControllerPhotos {

    private GUIWindow guiWindow;

    public GUIControllerPhotos(GUIWindow guiWindow) {
        this.guiWindow = guiWindow;
    }

    @FXML
    void delOld() {
        System.out.println("delOld");
    }

}
