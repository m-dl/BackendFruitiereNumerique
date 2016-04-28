package gui.Controller.chateau;

import entities.Info;
import entities.Overview;
import entities.Visit;
import files.FileManager;
import files.FileTools;
import gui.Controller.GUIFormsController;
import gui.GUIUtilities;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import static gui.Controller.enums.PictureFormType.INFO;
import static gui.Controller.enums.PictureFormType.OVERVIEW;
import static gui.Controller.enums.VisitType.CHATEAU;

public class GUIControllerChateauVisitForm {

    private static GUIControllerChateauVisitForm INSTANCE = new GUIControllerChateauVisitForm();
    public TextField visitName;
    public TextArea visitPresTextFROv;
    public TextArea visitPresTextENOv;
    public TextArea visitLengthFROv;
    public TextArea visitLengthENOv;
    public TextArea visitPresTextFRInf;
    public TextArea visitPresTextENInf;
    private Stage stage;
    private boolean isNewVisit;

    private ArrayList<File> overviewImages = new ArrayList<>();
    private ArrayList<File> infoImages = new ArrayList<>();


    public static GUIControllerChateauVisitForm getInstance() {
        return INSTANCE;
    }


    public void displayForm(boolean isNewVisit, Visit selectedVisit) {

        this.isNewVisit = isNewVisit;

        try {

            ScrollPane root = (ScrollPane) GUIUtilities.loadLayout("view/chateau/visitForm.fxml", this);

            stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setOnCloseRequest(closeEvent -> GUIFormsController.getInstance().closeForm());


            if (!isNewVisit) {
                if (selectedVisit != null) {
                    overviewImages = selectedVisit.getOverview().getImagesContent();
                    infoImages = selectedVisit.getInfo().getPhotos();
                    this.fillInputs(selectedVisit);
                    stage.setTitle("Modification de la visite: " + selectedVisit.getName());
                    GUIFormsController.getInstance().displayForm(stage);
                    stage.show();
                }
            } else {
                overviewImages = new ArrayList<>();
                infoImages = new ArrayList<>();
                GUIFormsController.getInstance().displayForm(stage);
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
    public void addOverviewPictures() {
        GUIFormsController.getInstance().displayPhotoForm(CHATEAU, OVERVIEW, this.isNewVisit);
    }

    @FXML
    public void addInfoPictures() {
        GUIFormsController.getInstance().displayPhotoForm(CHATEAU, INFO, this.isNewVisit);
    }

    @FXML
    public void saveChanges() {

        if( validForm() ) {

            Overview visitOverview;
            Info visitInfos;

            if (isNewVisit) {

                String vName = this.visitName.getText();
                String visitPath = FileManager.WORKSPACE + "/" + FileManager.CHATEAU + "/" + vName;
                String visitOverviewPath = visitPath + "/" + "visite-overview";
                String visitInfosPath = visitPath + "/" + "visite-info";

                Visit v = new Visit(visitPath, vName);
                v.setIP(new ArrayList<>());

                visitOverview = new Overview(visitOverviewPath);

                visitOverview.writePresentation_FR(visitPresTextFROv.getText());
                visitOverview.writeLength_EN(visitPresTextENOv.getText());
                visitOverview.writeLength_FR(visitLengthFROv.getText());
                visitOverview.writeLength_EN(visitLengthENOv.getText());

                System.out.println(this.overviewImages.size());

                for (int i = 0; i < this.overviewImages.size(); i++) {
                    visitOverview.addImagesContent(this.overviewImages.get(i).getAbsolutePath(), visitOverviewPath, this.overviewImages.get(i).getName());
                }


                visitInfos = new Info(visitInfosPath);

                visitInfos.writeContent_EN(visitPresTextENInf.getText());
                visitInfos.writeContent_FR(visitPresTextFRInf.getText());

                for (int i = 0; i < this.infoImages.size(); i++) {
                    visitOverview.addImagesContent(this.infoImages.get(i).getAbsolutePath(), visitInfosPath, this.infoImages.get(i).getName());
                }

                v.setInfo(visitInfos);
                v.setOverview(visitOverview);
                FileManager.getInstance().getChateauWorkspace().addVisit(v);
                GUIControllerChateau.getInstance().visitListC.add(v);

            }
            else {

                Visit selectedVisit = GUIControllerChateau.getInstance().getSelectedVisit();
                int index = FileManager.getInstance().getChateauWorkspace().getV().indexOf(selectedVisit);

                String vName = this.visitName.getText();

                String visitPath = FileManager.WORKSPACE + "/" + FileManager.CHATEAU + "/" + vName;
                String visitOverviewPath = visitPath + "/" + "visite-overview";
                String visitInfosPath = visitPath + "/" + "visite-info";

                if (vName != selectedVisit.getName()) {
                    // TODO: 17/04/2016 bouger tout le dossier
                }

                // TODO: 28/04/2016 ça bug ici

                FileManager.getInstance().getChateauWorkspace().getV().get(index).getOverview().writePresentation_FR(visitPresTextFROv.getText());
                FileManager.getInstance().getChateauWorkspace().getV().get(index).getOverview().writeLength_EN(visitPresTextENOv.getText());
                FileManager.getInstance().getChateauWorkspace().getV().get(index).getOverview().writeLength_FR(visitLengthFROv.getText());
                FileManager.getInstance().getChateauWorkspace().getV().get(index).getOverview().writeLength_EN(visitLengthENOv.getText());


                System.out.println("fm:" + FileManager.getInstance().getChateauWorkspace().getV().get(index).getOverview().getImagesContent().toString());
                //System.out.println("overview"+ getOverviewImages().size());

                //FileManager.getInstance().getChateauWorkspace().getV().get(index).getOverview().removeAll();
//                System.out.println(FileManager.getInstance().getChateauWorkspace().getV().get(index).getOverview().getImagesContent().size());

                /*
                for (int i = 0; i < getOverviewImages().size(); i++) {
                    FileManager.getInstance().getChateauWorkspace().getV().get(index).getOverview().addImagesContent(getOverviewImages().get(i).getAbsolutePath(), visitOverviewPath, getOverviewImages().get(i).getName());
                }
                */

                /*
                FileManager.getInstance().getChateauWorkspace().getV().get(index).getOverview().removeAll();
                FileManager.getInstance().getChateauWorkspace().getV().get(index).getOverview().setImagesContent(getOverviewImages(),visitOverviewPath);


                FileManager.getInstance().getChateauWorkspace().getV().get(index).getInfo().writeContent_EN(visitPresTextENInf.getText());
                FileManager.getInstance().getChateauWorkspace().getV().get(index).getInfo().writeContent_FR(visitPresTextFRInf.getText());

                this.overviewImages =  FileManager.getInstance().getChateauWorkspace().getV().get(index).getOverview().getImagesContent();
                System.out.println(FileManager.getInstance().getChateauWorkspace().getV().get(index).getOverview().getImagesContent().size());
*/
            }

            GUIFormsController.getInstance().closeForm();
            stage.close();
        }
    }

    //pour véfirier les entrées
    private boolean validForm() {
        return true;
    }


    public void setInfoImages(ArrayList<File> infoImages) {
        System.out.println(infoImages.toString());
        this.infoImages = infoImages;
    }

    public void setOverviewImages(ArrayList<File> overviewImages) {
        System.out.println(overviewImages.toString());
        this.overviewImages = overviewImages;
    }

    public ArrayList<File> getOverviewImages() {
        return overviewImages;
    }

    public ArrayList<File> getInfoImages() {
        return infoImages;
    }
}
