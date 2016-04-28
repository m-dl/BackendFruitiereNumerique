package gui.Controller.village;

import entities.village.InterestPoint;
import gui.Controller.GUIFormsController;
import gui.GUIUtilities;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class GUIControllerVillageIPForm {

    private static GUIControllerVillageIPForm INSTANCE = new GUIControllerVillageIPForm();
    public TextField ipName;
    public TextArea ipPresTextFR;
    public TextArea ipPresTextEN;
    private Stage stage;


    private GUIControllerVillageIPForm() {
    }


    public static GUIControllerVillageIPForm getInstance() {
        return INSTANCE;
    }


    public void displayForm(boolean isNewPoint, InterestPoint selectedPoint) {

        try {

            ScrollPane root = (ScrollPane) GUIUtilities.loadLayout("view/village/iPForm.fxml", this);
            stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setOnCloseRequest(closeEvent -> GUIFormsController.getInstance().closeForm());


                if (!isNewPoint) {
                    if (selectedPoint != null) {
                        this.fillInputs(selectedPoint);
                        stage.setTitle("Modification du point: " + selectedPoint.getName());
                        GUIFormsController.getInstance().displayForm(stage);
                        stage.show();
                    }
                } else {
                    GUIFormsController.getInstance().displayForm(stage);
                    stage.setTitle("Ajout d'un nouveau point");
                    stage.show();
                }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public void fillInputs(InterestPoint p) {

        ipName.setText(p.getName());
        ipPresTextFR.setText(p.readPresentation_FR());
        ipPresTextEN.setText(p.readPresentation_EN());
    }


    @FXML
    public void saveChanges() {
        stage.close();

    }


}
