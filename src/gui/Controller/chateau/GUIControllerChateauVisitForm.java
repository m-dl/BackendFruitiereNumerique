package gui.Controller.chateau;

import entities.Info;
import entities.Overview;
import entities.Visit;
import files.FileManager;
import gui.Controller.GUIFormsController;
import gui.GUIUtilities;
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
    public TextField visitName;
    public TextArea visitPresTextFROv;
    public TextArea visitPresTextENOv;
    public TextArea visitLengthFROv;
    public TextArea visitLengthENOv;
    public TextArea visitPresTextFRInf;
    public TextArea visitPresTextENInf;
    Info visitInfos;
    Overview visitOverview;
    private Stage stage;

    public static GUIControllerChateauVisitForm getInstance() {
        return INSTANCE;
    }


    public void displayForm(boolean isNewVisit, Visit selectedVisit) {

        try {

            ScrollPane root = (ScrollPane) GUIUtilities.loadLayout("view/chateau/visitForm.fxml", this);

            stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setOnCloseRequest(closeEvent -> GUIFormsController.getInstance().closeStage());


            if (!isNewVisit) {
                if (selectedVisit != null) {
                    this.fillInputs(selectedVisit);
                    stage.setTitle("Modification de la visite: " + selectedVisit.getName());
                    GUIFormsController.getInstance().displayStage(stage);
                    stage.show();
                }
            } else {
                GUIFormsController.getInstance().displayStage(stage);
                stage.setTitle("Ajout d'une nouvelle visite");
                stage.show();
            }

        } catch (IOException e) {
            e.printStackTrace();
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
    public void addInfoPictures() {
        // TODO: 06/04/2016 formulaire différent car info
        GUIFormsController.getInstance().displayPhotoForm();
    }

    @FXML
    public void addOverviewPictures() {
        // TODO: 06/04/2016  gérer plusieurs formulaires si one doit utiliser que trois images, si il faut que des vidéos ... etc
        GUIFormsController.getInstance().displayPhotoForm();
    }


    @FXML
    public void saveChanges() {

        if( validForm() ) {
            if(/*isNew*/true) {

                System.out.println("added");

                String vName = this.visitName.getText();
                String visitPath = FileManager.WORKSPACE + "/" + FileManager.CHATEAU + "/" + vName;

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

            GUIFormsController.getInstance().closeStage();
            stage.close();
        }



    }

    //pour véfirier les entrées
    private boolean validForm() {
        return true;
    }


}
