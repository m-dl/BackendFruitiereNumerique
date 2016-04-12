package gui.Controller.village;

import entities.Info;
import entities.Overview;
import entities.Visit;
import files.FileManager;
import gui.Controller.GUIFormsController;
import gui.Controller.chateau.GUIControllerChateau;
import gui.GUIUtilities;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class GUIControllerVillageVisitForm {

    private static GUIControllerVillageVisitForm INSTANCE = new GUIControllerVillageVisitForm();

    public TextField visitNameVillage;
    public TextArea visitPresTextFrOverviewVillage;
    public TextArea visitPresTextEnOverviewVillage;
    public TextArea visitLengthFrOverviewVillage;
    public TextArea visitLengthEnOverviewVillage;
    public TextArea visitPresTextFfInfoVillage;
    public TextArea visitPresTextEnInfoVillage;

    Info visitInfos;
    Overview visitOverview;

    boolean isNew = true;
    private Stage stage;

    public GUIControllerVillageVisitForm() {
    }

    public static GUIControllerVillageVisitForm getInstance() {
        return INSTANCE;
    }

    @FXML
    public void addPicturesOverview() {

    }

    public void displayForm(boolean isNewVisit, Visit selectedVisit) {

        try {

            ScrollPane root = (ScrollPane) GUIUtilities.loadLayout("view/village/visitForm.fxml", this);

            stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setOnCloseRequest(closeEvent -> GUIFormsController.getInstance().closeForm());


            if (!isNewVisit) {
                if (selectedVisit != null) {
                    this.fillInputs(selectedVisit);
                    GUIFormsController.getInstance().displayForm(stage);
                    stage.setTitle("Modification de la visite: " + selectedVisit.getName());
                    stage.show();
                }
            } else {
                GUIFormsController.getInstance().displayForm(stage);
                stage.setTitle("Ajout d'une nouvelle visite");
                stage.show();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public void fillInputs(Visit v) {

        this.visitNameVillage.setText(v.getName());
        this.visitPresTextFrOverviewVillage.setText(v.getOverview().readPresentation_FR());
        this.visitPresTextEnOverviewVillage.setText(v.getOverview().readPresentation_EN());
        this.visitLengthFrOverviewVillage.setText(v.getOverview().readLength_FR());
        this.visitLengthEnOverviewVillage.setText(v.getOverview().readLength_EN());
        this.visitPresTextFfInfoVillage.setText(v.getInfo().readContent_FR());
        this.visitPresTextEnInfoVillage.setText(v.getInfo().readContent_EN());
    }


    @FXML
    public void saveChanges() {

        if (validForm()) {
            if (isNew) {

                System.out.println("added");

                String vName = this.visitNameVillage.getText();
                String visitPath = FileManager.WORKSPACE + "/" + FileManager.CHATEAU + "/" + vName;

                Visit v = new Visit(visitPath, vName);
                v.setIP(new ArrayList<>());

                visitOverview = new Overview(visitPath + "/" + "visite-overview");

                visitOverview.writePresentation_FR(visitPresTextFrOverviewVillage.getText());
                visitOverview.writePresentation_EN(visitPresTextEnOverviewVillage.getText());
                visitOverview.writeLength_EN(visitPresTextEnOverviewVillage.getText());
                visitOverview.writeLength_FR(visitLengthFrOverviewVillage.getText());
                visitOverview.writeLength_EN(visitLengthEnOverviewVillage.getText());

                visitInfos = new Info(visitPath + "/" + "visite-info");

                visitInfos.writeContent_EN(visitPresTextFfInfoVillage.getText());
                visitInfos.writeContent_FR(visitPresTextEnInfoVillage.getText());


                v.setInfo(visitInfos);
                v.setOverview(visitOverview);
                FileManager.getInstance().getChateauWorkspace().addVisit(v);

                GUIControllerChateau.getInstance().visitListC.add(v);

            } else {
                //si visite est modifiée
            }

            GUIFormsController.getInstance().closeForm();
            stage.close();
        }


    }

    //pour véfirier les entrées
    private boolean validForm() {
        return true;
    }


}
