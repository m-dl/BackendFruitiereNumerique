package gui.Controller;

import entities.InterestPoint;
import entities.Visit;
import gui.Controller.chateau.GUIControllerChateauIPForm;
import gui.Controller.chateau.GUIControllerChateauVisitForm;
import gui.Controller.village.GUIControllerVillageIPForm;
import gui.Controller.village.GUIControllerVillageVisitForm;
import javafx.stage.Stage;

public class GUIFormsController {

    private static GUIFormsController INSTANCE = new GUIFormsController();
    private Stage stage;
    private boolean isFormDisplayed = false;

    private GUIFormsController() {
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

    // TODO: 06/04/2016 plusieurs types de formulaires ici selon si overview, infos, images etc..
    // TODO: 06/04/2016 avec un parametre ou plusieurs fonctions?
    // TODO: 06/04/2016 est-ce qu'on peut afficher plusieurs fenetres d'ajout d'images?
    public void displayPhotoForm() {
        GUIControllerPhotoForm.getInstance().displayForm();
    }

}
