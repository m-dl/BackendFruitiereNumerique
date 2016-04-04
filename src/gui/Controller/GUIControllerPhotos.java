package gui.Controller;

import entities.InterestPoint;
import entities.Visit;
import gui.GUIWindow;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;

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
