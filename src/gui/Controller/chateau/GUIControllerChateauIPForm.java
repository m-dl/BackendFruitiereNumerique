package gui.Controller.chateau;

import entities.InterestPoint;
import entities.Visit;
import files.FileManager;
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
import java.util.ArrayList;

public class GUIControllerChateauIPForm {

    private static GUIControllerChateauIPForm INSTANCE = new GUIControllerChateauIPForm();
    private Stage stage;

    public TextField ipName;
    public TextArea ipPresTextFR;
    public TextArea ipPresTextEN;


    private GUIControllerChateauIPForm()
    {}


    public static GUIControllerChateauIPForm getInstance() {
        return INSTANCE;
    }

    public void displayForm(boolean isNewPoint, InterestPoint selectedPoint) {

        try {

            ScrollPane root = (ScrollPane) GUIUtilities.loadLayout("view/chateau/iPForm.fxml", this);

            stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setOnCloseRequest(closeEvent -> GUIFormsController.getInstance().closeStage());


                if(!isNewPoint) {
                    if (selectedPoint != null) {
                        this.fillInputs(selectedPoint);
                        stage.setTitle("Modification du point: " + selectedPoint.getName());
                        GUIFormsController.getInstance().displayStage(stage);
                        stage.show();
                    }
                }
                else {
                    GUIFormsController.getInstance().displayStage(stage);
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
        if( validForm() ) {
            if(/*isNew*/true) {

                System.out.println("added");

                String ipName = this.ipName.getText();

                String ipPath = FileManager.getInstance().WORKSPACE + "/" + FileManager.getInstance().CHATEAU + "/" +
                        GUIControllerChateau.getInstance().getSelectedVisit().getName() + "/" + ipName;

                InterestPoint ip = new InterestPoint(ipPath, ipName);

                ip.writePresentation_FR(ipPresTextFR.getText());
                ip.writePresentation_EN(ipPresTextEN.getText());

                ArrayList<Visit> v = FileManager.getInstance().getChateauWorkspace().getV();
                v.get(v.indexOf(GUIControllerChateau.getInstance().getSelectedVisit())).addInterestPoint(ip);

                GUIControllerChateau.getInstance().iPListC.add(ip);

            }
            else {
                //si visite est modifiée
            }

            //isNew = false;
            stage.close();
        }

    }

    private boolean validForm() {
        return true;
    }


}
