package gui.Controller.village;

import entities.InterestPoint;
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

    private GUIWindow guiWindow;
    private static GUIControllerVillageIPForm INSTANCE = new GUIControllerVillageIPForm();
    private Stage stage;

    public boolean isAlreadyDisplayed = false;
    public TextField ipName;
    public TextArea ipPresTextFR;
    public TextArea ipPresTextEN;


    private GUIControllerVillageIPForm()
    {}


    public static GUIControllerVillageIPForm getInstance() {
        return INSTANCE;
    }

    public void setMainClass(GUIWindow guiWindow) {
        this.guiWindow = guiWindow;
    }

/*
    public void displayForm(boolean isNewVisit, InterestPoint selectedPoint) {

        if (!isAlreadyDisplayed) {

            try {


                GUIUtilities utilities = new GUIUtilities();

                ScrollPane root = (ScrollPane) utilities.loadLayout("view/chateau/iPForm.fxml", this);

                stage = new Stage();

                stage.setScene(new Scene(root));

                stage.setOnCloseRequest(closeEvent -> {
                    System.out.println("Stage is closing");
                    isAlreadyDisplayed = false;
                });


                if (!isNewVisit && selectedPoint != null) {

                    this.fillInputs(selectedPoint);
                    stage.setTitle("Modification d'un point d'intérêt");
                    stage.show();
                }
                else if (isNewVisit){

                    stage.setTitle("Ajout d'un point d'intérêt");
                    stage.show();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
*/

    public void fillInputs(InterestPoint p) {

            ipName.setText(p.getName());
            ipPresTextFR.setText(p.readPresentation_FR());
            ipPresTextEN.setText(p.readPresentation_EN());
    }


    @FXML
    public void saveChanges() {
        guiWindow.test();
        stage.close();

    }


}
