package gui.Controller.photo;

import javafx.fxml.FXML;

/**
 * Classe pour la récupération de photos de la tablette
 * Non utilisée
 */
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
