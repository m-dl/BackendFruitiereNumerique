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

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * Classe gérant l'affichage des formulaires
 */
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


    /**
     * Fonction qui affichera le formulaire
     *
     * @param s le conteneur du formulaire
     */
    public void displayForm(Stage s) {
        formStage = s;
        isFormDisplayed = true;
    }

    /**
     * Ferme le formulaire courant
     */
    public void closeForm() {
        isFormDisplayed = false;
        GUIControllerPhotoForm.getInstance().wipePictures();
    }

    /**
     * Afficher le formualire au premier plan si il est déjà affiché
     */
    public void formToFront() {
        formStage.toFront();
    }

    /**
     * Affichage du formulaire d'ajout d'images
     * @param s le formulaire
     */
    public void displayPictureForm(Stage s) {
        pictureFormStage = s;
        isPictureFormDisplayed = true;
    }

    /**
     * Ferme le formulaire d'ajout d'images
     */
    public void closePictureForm() {
        isPictureFormDisplayed = false;
    }

    /**
     * Afficher le formulaire au premier plan
     */
    public void pictureFormToFront() {
        pictureFormStage.toFront();
    }

    /**
     * Affichage du formulaire correspondant à la visite chateau
     * @param isNewVisit si nouvelle visite ou visite modifiée
     * @param selectedVisit la visite sélectionnée si modification
     */
    public void displayChateauVisitForm(boolean isNewVisit, entities.chateau.Visit selectedVisit) {

        if(!isFormDisplayed)
            GUIControllerChateauVisitForm.getInstance().displayForm(isNewVisit, selectedVisit);
        else
            formToFront();
    }

    /**
     * Affichage du formulaire correspondant au point d'intérêt chateau
     * @param isNewPoint si nouveau point ou point modifié
     * @param selectedPoint le point sélectionné si modification
     */
    public void displayChateauIPForm(boolean isNewPoint, entities.chateau.InterestPoint selectedPoint) {

        if(!isFormDisplayed)
            GUIControllerChateauIPForm.getInstance().displayForm(isNewPoint, selectedPoint);
        else
            formToFront();
    }

    /**
     * Affichage du formulaire correspondant à la visite village
     * @param isNewVisit si nouvelle visite ou visite modifiée
     * @param selectedVisit la visite sélectionnée si modification
     */
    public void displayVillageVisitForm(boolean isNewVisit, entities.village.Visit selectedVisit) {

        if(!isFormDisplayed)
            GUIControllerVillageVisitForm.getInstance().displayForm(isNewVisit, selectedVisit);
        else
            formToFront();
    }

    /**
     * Affichage du formulaire correspondant au point d'intérêt chateau
     * @param isNewPoint si nouveau point ou point modifié
     * @param selectedPoint le point sélectionné si modification
     */
    public void displayVillageIPForm(boolean isNewPoint, entities.village.InterestPoint selectedPoint) {
        if(!isFormDisplayed)
            GUIControllerVillageIPForm.getInstance().displayForm(isNewPoint, selectedPoint);
        else
            formToFront();
    }

    /**
     * Affichage du formulaire correspondant d'ajout de photos
     * @param visitType type de visite appelant
     * @param pictureFormType type de médias à ajouter
     * @param isNew si l'objet appelant est nouveau ou modifié
     */
    public void displayPhotoForm(VisitType visitType, PictureFormType pictureFormType, boolean isNew) {
            GUIControllerPhotoForm.getInstance().displayForm(visitType, pictureFormType);
    }

    /**
     * Affichage de la fene^re pour placer le point d'intérêt dans le château
     * @param isNewPoint si c'est un nouveau point d'intérêt ou non
     */
    public void displayPointPlacerFrom(boolean isNewPoint) {
        GUIControllerPointPlacer.getInstance().display(isNewPoint);
    }

    /**
     * Affichage d'une boite de dialogue d'erreur
     * @param content le message du milieu de fenêtre
     * @param labelText le message concerant l'erreur
     * @param error le message d'erreur du ScrollPane
     * @return la boite de dialogue
     */
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

    /**
     * Affichage d'une boite de dialogue pour les exceptions
     * @param ex l'exception à afficher
     * @param error le message d'erreur accompagnant l'erreur
     * @return la boite de dialogue
     */
    public Alert displayExceptionAlert(Exception ex, String error) {

        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Une erreur s'est produite");
        alert.setHeaderText("Une erreur s'est produite");
        alert.setContentText(error);

        // Create expandable Exception.
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        ex.printStackTrace(pw);
        String exceptionText = sw.toString();

        Label label = new Label("Contenu de l'exception:");

        TextArea textArea = new TextArea(exceptionText);
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

    /**
     * Affichage d'une boite de dialogue d'alerte
     * @param headerText le message intermediaire à afficher
     * @param errorText le message d'erreur
     * @return la boite de dialogue
     */
    public Alert displayWarningAlert(String headerText, String errorText) {

        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Attention");
        alert.setHeaderText(headerText);
        alert.setContentText(errorText);

        return alert;

    }

}
