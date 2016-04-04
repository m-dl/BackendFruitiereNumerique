package gui.Controller.village;

import entities.InterestPoint;
import gui.Controller.GUIFormsController;
import gui.GUIUtilities;
import gui.GUIWindow;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class GUIControllerVillageIPForm {

    private static GUIControllerVillageIPForm INSTANCE = new GUIControllerVillageIPForm();
    private Stage stage;

    public TextField ipName;
    public TextArea ipPresTextFR;
    public TextArea ipPresTextEN;


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
            stage.setOnCloseRequest(closeEvent -> GUIFormsController.getInstance().closeStage());

            if (selectedPoint != null) {
                if (!isNewPoint) {
                    this.fillInputs(selectedPoint);
                    stage.setTitle("Modification du point: " + selectedPoint.getName());
                    GUIFormsController.getInstance().displayStage(stage);
                    stage.show();
                } else {
                    GUIFormsController.getInstance().displayStage(stage);
                    stage.setTitle("Ajout d'un nouveau point");
                    stage.show();
                }
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
