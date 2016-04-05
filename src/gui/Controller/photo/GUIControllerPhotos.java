package gui.Controller.photo;

import javafx.fxml.FXML;

public class GUIControllerPhotos {

    private static GUIControllerPhotos INSTANCE = new GUIControllerPhotos();



    private GUIControllerPhotos()
    {}

    public static GUIControllerPhotos getInstance() {
        return INSTANCE;
    }

    @FXML
    void delOld() {
        System.out.println("delOld");
    }

}
