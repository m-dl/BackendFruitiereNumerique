package gui.Controller.chateau;

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

public class GUIControllerChateauIPForm {

    private GUIWindow guiWindow;
    private static GUIControllerChateauIPForm INSTANCE = new GUIControllerChateauIPForm();
    private Stage stage;

    public boolean isAlreadyDisplayed = false;
    public TextField visitName;
    public TextArea visitPresTextFR;
    public TextArea visitPresTextEN;
    public TextArea visitLengthFR;
    public TextArea visitLengthEN;


    private GUIControllerChateauIPForm()
    {}


    public static GUIControllerChateauIPForm getInstance() {
        return INSTANCE;
    }

    public void setMainClass(GUIWindow guiWindow) {
        this.guiWindow = guiWindow;
    }


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

                    //this.fillInputs(selectedPoint);
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


    public void fillInputs(InterestPoint p) {
        /*
            visitName.setText(v.getName());
            visitPresTextFR.setText(v.getOverview().readPresentation_FR());
            visitPresTextEN.setText(v.getOverview().readPresentation_EN());
            visitLengthFR.setText(v.getOverview().readLength_FR());
            visitLengthEN.setText(v.getOverview().readLength_EN());
*/
    }


    @FXML
    public void saveChanges() {
        guiWindow.test();
        stage.close();

    }


}
