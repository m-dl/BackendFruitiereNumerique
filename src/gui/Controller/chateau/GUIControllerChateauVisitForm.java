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

    private GUIWindow guiWindow;
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


    private GUIControllerChateauVisitForm()
    {}


    public static GUIControllerChateauVisitForm getInstance() {
        return INSTANCE;
    }

    public void setMainClass(GUIWindow guiWindow) {
        this.guiWindow = guiWindow;
    }


    public void displayForm(boolean isNewVisit, Visit selectedVisit) {

        this.isNew = isNewVisit;

        if (!isAlreadyDisplayed) {

            try {


                GUIUtilities utilities = new GUIUtilities();

                ScrollPane root = (ScrollPane) utilities.loadLayout("view/chateau/visitForm.fxml", this);

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
                }
                else if (isNewVisit){


                    stage.show();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
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

                visitOverview = new Overview(visitPath + "/" + "visite-overview");

                visitOverview.writePresentation_FR(visitPresTextFROv.getText());
                visitOverview.writeLength_EN(visitPresTextENOv.getText());
                visitOverview.writeLength_FR(visitLengthFROv.getText());
                visitOverview.writeLength_EN(visitLengthENOv.getText());

                visitInfos = new Info(visitPath + "/" + "visite-info");

                visitInfos.writeContent_EN(visitPresTextENInf.getText());
                visitInfos.writeContent_FR(visitPresTextFRInf.getText());




                Visit v = new Visit(visitPath, vName);
                v.setIP(new ArrayList<>());
                v.setInfo(visitInfos);
                v.setOverview(visitOverview);
                FileManager.getInstance().getChateauWorkspace().addVisit(v);

                GUIControllerChateau gc = GUIControllerChateau.getInstance();
                gc.visitListC.add(v);

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
