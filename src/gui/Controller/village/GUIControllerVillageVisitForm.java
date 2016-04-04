package gui.Controller.village;

import entities.Info;
import entities.Overview;
import entities.Visit;
import files.FileManager;
import gui.Controller.GUIFormsController;
import gui.Controller.chateau.GUIControllerChateau;
import gui.GUIUtilities;
import gui.GUIWindow;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class GUIControllerVillageVisitForm {

    private static GUIControllerVillageVisitForm INSTANCE = new GUIControllerVillageVisitForm();
    private Stage stage;

    Info visitInfos;
    Overview visitOverview;

    boolean isNew = false;
    @FXML
    public TextField visitNameVillage;
    public TextArea visitPresTextFrOverviewVillage;
    public TextArea visitPresTextEnOverviewVillage;
    public TextArea visitLengthFrOverviewVillage;
    public TextArea visitLengthEnOverviewVillage;
    public TextArea visitPresTextFfInfoVillage;
    public TextArea visitPresTextEnInfoVillage;
    public Button addPicturesOverview;

    public GUIControllerVillageVisitForm() {
    }


    @FXML
    public void addPicturesOverview() {

    }



    public static GUIControllerVillageVisitForm getInstance() {
        return INSTANCE;
    }


    public void displayForm(boolean isNewVisit, Visit selectedVisit) {

        try {

            ScrollPane root = (ScrollPane) GUIUtilities.loadLayout("view/village/visitForm.fxml", this);

            stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setOnCloseRequest(closeEvent -> GUIFormsController.getInstance().closeStage());

            if (selectedVisit != null) {
                if(!isNewVisit) {
                    this.fillInputs(selectedVisit);
                    GUIFormsController.getInstance().displayStage(stage);
                    stage.setTitle("Modification de la visite: " + selectedVisit.getName());
                    stage.show();
                }
                else {
                    GUIFormsController.getInstance().displayStage(stage);
                    stage.setTitle("Ajout d'une nouvelle visite");
                    stage.show();
                }
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

        if( validForm() ) {
            if(/*isNew*/true) {

                System.out.println("added");

                String vName = this.visitNameVillage.getText();
                String visitPath = FileManager.getInstance().WORKSPACE + "/" + FileManager.getInstance().CHATEAU + "/" + vName;

                Visit v = new Visit(visitPath, vName);
                v.setIP(new ArrayList<>());

                visitOverview = new Overview(visitPath + "/" + "visite-overview");

                visitOverview.writePresentation_FR(visitPresTextFrOverviewVillage.getText());
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
