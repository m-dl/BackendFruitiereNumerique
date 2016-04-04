package gui.Controller.chateau;

import entities.Info;
import entities.Overview;
import entities.Visit;
import files.FileManager;
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

public class GUIControllerChateauVisitForm {

    private static GUIControllerChateauVisitForm INSTANCE = new GUIControllerChateauVisitForm();
    private Stage stage;

    Info visitInfos;
    Overview visitOverview;

    public boolean isAlreadyDisplayed = false;
    boolean isNew = false;
    public TextField visitName;
    public TextArea visitPresTextFROv;
    public TextArea visitPresTextENOv;
    public TextArea visitLengthFROv;
    public TextArea visitLengthENOv;
    public TextArea visitPresTextFRInf;
    public TextArea visitPresTextENInf;


    public static GUIControllerChateauVisitForm getInstance() {
        return INSTANCE;
    }


    public void displayForm(boolean isNewVisit, Visit selectedVisit) {

        this.isNew = isNewVisit;
        System.out.println(isAlreadyDisplayed);

        if (!isAlreadyDisplayed) {

            try {

                GUIUtilities utilities = new GUIUtilities();

                ScrollPane root = (ScrollPane) utilities.loadLayout("view/village/visitForm.fxml", this);

                stage = new Stage();
                stage.setTitle("Formulaire de visite");
                stage.setScene(new Scene(root));

                stage.setOnCloseRequest(closeEvent -> {
                    System.out.println("Stage is closing");
                    isAlreadyDisplayed = false;
                });


                if (!isNewVisit && selectedVisit != null) {

                    this.fillInputs(selectedVisit);

                    stage.show();
                    isAlreadyDisplayed = true;
                }
                else if (isNewVisit){
                    stage.show();
                    isAlreadyDisplayed = true;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else {
            stage.toFront();
        }
    }


    public void fillInputs(Visit v) {
            visitName.setText(v.getName());
            visitPresTextFROv.setText(v.getOverview().readPresentation_FR());
            visitPresTextENOv.setText(v.getOverview().readPresentation_EN());
            visitLengthFROv.setText(v.getOverview().readLength_FR());
            visitLengthENOv.setText(v.getOverview().readLength_EN());
            visitPresTextFRInf.setText(v.getInfo().readContent_FR());
            visitPresTextENInf.setText(v.getInfo().readContent_EN());
    }


    @FXML
    public void saveChanges() {

        if( validForm() ) {
            if(/*isNew*/true) {

                System.out.println("added");

                String vName = this.visitName.getText();
                String visitPath = FileManager.getInstance().WORKSPACE + "/" + FileManager.getInstance().CHATEAU + "/" + vName;

                Visit v = new Visit(visitPath, vName);
                v.setIP(new ArrayList<>());

                visitOverview = new Overview(visitPath + "/" + "visite-overview");

                visitOverview.writePresentation_FR(visitPresTextFROv.getText());
                visitOverview.writeLength_EN(visitPresTextENOv.getText());
                visitOverview.writeLength_FR(visitLengthFROv.getText());
                visitOverview.writeLength_EN(visitLengthENOv.getText());

                visitInfos = new Info(visitPath + "/" + "visite-info");

                visitInfos.writeContent_EN(visitPresTextENInf.getText());
                visitInfos.writeContent_FR(visitPresTextFRInf.getText());


                v.setInfo(visitInfos);
                v.setOverview(visitOverview);
                FileManager.getInstance().getChateauWorkspace().addVisit(v);

                GUIControllerChateau.getInstance().visitListC.add(v);

            }
            else {
                //si visite est modifiée
            }

            isNew = false;
            stage.close();
        }



    }

    //pour véfirier les entrées
    private boolean validForm() {
        return true;
    }


}
