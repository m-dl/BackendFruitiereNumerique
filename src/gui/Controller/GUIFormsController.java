package gui.Controller;

import entities.InterestPoint;
import entities.Visit;
import gui.Controller.chateau.GUIControllerChateauIPForm;
import gui.Controller.chateau.GUIControllerChateauVisitForm;
import gui.Controller.enums.PictureFormType;
import gui.Controller.enums.VisitType;
import gui.Controller.photo.GUIControllerPhotoForm;
import gui.Controller.village.GUIControllerVillageIPForm;
import gui.Controller.village.GUIControllerVillageVisitForm;
import javafx.stage.Stage;

public class GUIFormsController {

    private static GUIFormsController INSTANCE = new GUIFormsController();
    private Stage formStage;
    private Stage pictureFormStage;
    private boolean isFormDisplayed = false;
    private boolean isPictureFormDisplayed = false;

    private GUIFormsController() {
    }
    public static GUIFormsController getInstance() {
        return INSTANCE;
    }


    public void displayForm(Stage s) {
        formStage = s;
        isFormDisplayed = true;
    }

    public void closeForm() {
        isFormDisplayed = false;
    }

    public void formToFront() {
        formStage.toFront();
    }

    public void displayPictureForm(Stage s) {
        pictureFormStage = s;
        isPictureFormDisplayed = true;
    }

    public void closePictureForm() {
        isPictureFormDisplayed = false;
    }

    public void pictureFormToFront() {
        pictureFormStage.toFront();
    }


    public void displayChateauVisitForm(boolean isNewVisit, Visit selectedVisit) {

        if(!isFormDisplayed)
            GUIControllerChateauVisitForm.getInstance().displayForm(isNewVisit, selectedVisit);
        else
            formToFront();
    }

    public void displayChateauIPForm(boolean isNewVisit, InterestPoint selectedPoint) {

        if(!isFormDisplayed)
            GUIControllerChateauIPForm.getInstance().displayForm(isNewVisit, selectedPoint);
        else
            formToFront();
    }


    public void displayVillageVisitForm(boolean isNewVisit, Visit selectedVisit) {

        if(!isFormDisplayed)
            GUIControllerVillageVisitForm.getInstance().displayForm(isNewVisit, selectedVisit);
        else
            formToFront();
    }

    public void displayVillageIPForm(boolean isNewVisit, InterestPoint selectedPoint) {
        if(!isFormDisplayed)
            GUIControllerVillageIPForm.getInstance().displayForm(isNewVisit, selectedPoint);
        else
            formToFront();
    }

    public void displayPhotoForm(VisitType visitType, PictureFormType pictureFormType) {

        if (!isPictureFormDisplayed)
            GUIControllerPhotoForm.getInstance().displayForm(visitType, pictureFormType);
        else
            pictureFormToFront();
    }

}
