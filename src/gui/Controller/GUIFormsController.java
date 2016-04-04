package gui.Controller;

import entities.Info;
import entities.InterestPoint;
import entities.Overview;
import entities.Visit;
import gui.Controller.chateau.GUIControllerChateauIPForm;
import gui.Controller.chateau.GUIControllerChateauVisitForm;
import gui.Controller.village.GUIControllerVillageIPForm;
import gui.Controller.village.GUIControllerVillageVisitForm;
import gui.GUIUtilities;
import gui.GUIWindow;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class GUIFormsController {

    private static GUIFormsController INSTANCE = new GUIFormsController();
    private GUIUtilities utilities;
    private Stage stage;
    private boolean isFormDisplayed = false;

    private GUIFormsController() {
        this.utilities = new GUIUtilities();
    }


    public static GUIFormsController getInstance() {
        return INSTANCE;
    }


    public void displayStage(Stage s) {
        if (!isFormDisplayed) {
            this.stage = s;
            stage.show();
            isFormDisplayed = true;
        }
        else {
            this.stage.toFront();
        }

    }

    public void closeStage() {
        stage.close();
        isFormDisplayed = false;
    }


    public void displayChateauVisitForm(boolean isNewVisit, Visit selectedVisit) {

        GUIControllerChateauVisitForm.getInstance().displayForm(isNewVisit, selectedVisit);
    }

    public void displayChateauIPForm(boolean isNewVisit, InterestPoint selectedPoint) {

        GUIControllerChateauIPForm.getInstance().displayForm(isNewVisit, selectedPoint);
    }


    public void displayVillageVisitForm(boolean isNewVisit, Visit selectedVisit) {

        GUIControllerVillageVisitForm.getInstance().displayForm(isNewVisit, selectedVisit);
    }

    public void displayVillageIPForm(boolean isNewVisit, InterestPoint selectedPoint) {

        //villageIPForm.getInstance().displayForm(isNewVisit, selectedPoint);
    }

}
