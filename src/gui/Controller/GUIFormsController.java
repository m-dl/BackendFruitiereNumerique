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

        stage = s;
        isFormDisplayed = true;
    }

    public void closeStage() {
        isFormDisplayed = false;

    }

    public void toFront() {
        stage.toFront();
    }


    public void displayChateauVisitForm(boolean isNewVisit, Visit selectedVisit) {

        if(!isFormDisplayed)
            GUIControllerChateauVisitForm.getInstance().displayForm(isNewVisit, selectedVisit);
        else
            toFront();
    }

    public void displayChateauIPForm(boolean isNewVisit, InterestPoint selectedPoint) {

        if(!isFormDisplayed)
            GUIControllerChateauIPForm.getInstance().displayForm(isNewVisit, selectedPoint);
        else
            toFront();
    }


    public void displayVillageVisitForm(boolean isNewVisit, Visit selectedVisit) {

        if(!isFormDisplayed)
            GUIControllerVillageVisitForm.getInstance().displayForm(isNewVisit, selectedVisit);
        else
            toFront();
    }

    public void displayVillageIPForm(boolean isNewVisit, InterestPoint selectedPoint) {


        if(!isFormDisplayed)
            GUIControllerVillageIPForm.getInstance().displayForm(isNewVisit, selectedPoint);
        else
            toFront();
    }

}
