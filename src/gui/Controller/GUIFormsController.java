package gui.Controller;

import gui.Controller.chateau.GUIControllerChateauIPForm;
import gui.Controller.chateau.GUIControllerChateauVisitForm;
import gui.Controller.enums.PictureFormType;
import gui.Controller.enums.VisitType;
import gui.Controller.photo.GUIControllerPhotoForm;
import gui.Controller.photo.GUIControllerPointPlacer;
import gui.Controller.village.GUIControllerVillageIPForm;
import gui.Controller.village.GUIControllerVillageVisitForm;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
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


    public void displayChateauVisitForm(boolean isNewVisit, entities.chateau.Visit selectedVisit) {

        if(!isFormDisplayed)
            GUIControllerChateauVisitForm.getInstance().displayForm(isNewVisit, selectedVisit);
        else
            formToFront();
    }

    public void displayChateauIPForm(boolean isNewVisit, entities.chateau.InterestPoint selectedPoint) {

        if(!isFormDisplayed)
            GUIControllerChateauIPForm.getInstance().displayForm(isNewVisit, selectedPoint);
        else
            formToFront();
    }


    public void displayVillageVisitForm(boolean isNewVisit, entities.village.Visit selectedVisit) {

        if(!isFormDisplayed)
            GUIControllerVillageVisitForm.getInstance().displayForm(isNewVisit, selectedVisit);
        else
            formToFront();
    }

    public void displayVillageIPForm(boolean isNewVisit, entities.village.InterestPoint selectedPoint) {
        if(!isFormDisplayed)
            GUIControllerVillageIPForm.getInstance().displayForm(isNewVisit, selectedPoint);
        else
            formToFront();
    }

    public void displayPhotoForm(VisitType visitType, PictureFormType pictureFormType, boolean isNew) {
            GUIControllerPhotoForm.getInstance().displayForm(visitType, pictureFormType);
    }

    public void displayPointPlacerFrom(boolean isNewPoint) {
        GUIControllerPointPlacer.getInstance().display(isNewPoint);
    }

    public Alert displayErrorAlert(String content, String labelText, String error) {

        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText(content);

        Label label = new Label(labelText);
        TextArea textArea = new TextArea(error);
        textArea.setEditable(false);
        textArea.setWrapText(true);
        textArea.setMaxWidth(Double.MAX_VALUE);
        textArea.setMaxHeight(Double.MAX_VALUE);
        GridPane.setVgrow(textArea, Priority.ALWAYS);
        GridPane.setHgrow(textArea, Priority.ALWAYS);

        GridPane expContent = new GridPane();
        expContent.setMaxWidth(Double.MAX_VALUE);
        expContent.add(label, 0, 0);
        expContent.add(textArea, 0, 1);

        alert.getDialogPane().setExpandableContent(expContent);

        return alert;

    }

    public Alert displayWarningAlert(String headerText, String errorText) {

        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Attention");
        alert.setHeaderText(headerText);
        alert.setContentText(errorText);

        return alert;

    }

}
