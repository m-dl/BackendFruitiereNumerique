package gui.Controller.chateau;

import entities.chateau.Info;
import entities.chateau.Overview;
import entities.chateau.Visit;
import files.FileManager;
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

    public ArrayList<File> overviewImages;
    public ArrayList<File> infoImages;

    private GUIControllerChateauVisitForm() {
        overviewImages = new ArrayList<>();
        infoImages = new ArrayList<>();
    }

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
                    //int index = FileManager.getInstance().getChateauWorkspace().getV().indexOf(GUIControllerChateau.getInstance().getSelectedVisit());
                   // overviewImages = FileManager.getInstance().getChateauWorkspace().getV().get(index).getOverview().getImagesContent();
                   // infoImages = FileManager.getInstance().getChateauWorkspace().getV().get(index).getInfo().getPhotos();
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

    @FXML void displayShit() {
        System.out.println("overview: " + getOverviewImages());
        System.out.println("infos: " + getInfoImages());

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

                for (int i = 0; i < this.overviewImages.size(); i++) {
                    visitOverview.addImagesContent(this.overviewImages.get(i).getAbsolutePath(), visitOverviewPath, this.overviewImages.get(i).getName());
                }

                visitInfos = new Info(visitInfosPath);

                visitInfos.writeContent_EN(visitPresTextENInf.getText());
                visitInfos.writeContent_FR(visitPresTextFRInf.getText());

                for (int i = 0; i < this.infoImages.size(); i++) {
                    visitInfos.addPhotos(this.infoImages.get(i).getAbsolutePath(), visitInfosPath, this.infoImages.get(i).getName());
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


                FileManager.getInstance().getChateauWorkspace().getV().get(index).getOverview().writePresentation_FR(visitPresTextFROv.getText());
                FileManager.getInstance().getChateauWorkspace().getV().get(index).getOverview().writeLength_EN(visitPresTextENOv.getText());
                FileManager.getInstance().getChateauWorkspace().getV().get(index).getOverview().writeLength_FR(visitLengthFROv.getText());
                FileManager.getInstance().getChateauWorkspace().getV().get(index).getOverview().writeLength_EN(visitLengthENOv.getText());


                //Suppression et ajout des images qui ont été supprimées ou ajoutées pour Overview.
                for (int i = 0; i < FileManager.getInstance().getChateauWorkspace().getV().get(index).getOverview().getImagesContent().size(); i++) {
                    if (! (getOverviewImages().contains(FileManager.getInstance().getChateauWorkspace().getV().get(index).getOverview().getImagesContent().get(i)))) {
                        File imageToDel = FileManager.getInstance().getChateauWorkspace().getV().get(index).getOverview().getImagesContent().get(i);
                        System.out.println("stuff to be del"+imageToDel.getName());
                        FileManager.getInstance().getChateauWorkspace().getV().get(index).getOverview().removeImagesContent(imageToDel.getPath(),imageToDel);
                    }
                }

                for (int i = 0; i < getOverviewImages().size(); i++) {
                    if (!FileManager.getInstance().getChateauWorkspace().getV().get(index).getOverview().getImagesContent().contains(getOverviewImages().get(i))) {
                        System.out.println("stuff to be add"+getOverviewImages().get(i).getName());

                        FileManager.getInstance().getChateauWorkspace().getV().get(index).getOverview().addImagesContent(getOverviewImages().get(i).getAbsolutePath(), visitOverviewPath, getOverviewImages().get(i).getName());
                    }
                }

                //Suppression et ajout des images qui ont été supprimées ou ajoutées pour Info.

                for (int i = 0; i < FileManager.getInstance().getChateauWorkspace().getV().get(index).getInfo().getPhotos().size(); i++) {
                    if (! (getInfoImages().contains(FileManager.getInstance().getChateauWorkspace().getV().get(index).getInfo().getPhotos().get(i)))) {
                        File imageToDel = FileManager.getInstance().getChateauWorkspace().getV().get(index).getInfo().getPhotos().get(i);
                        System.out.println("stuff to be del " + imageToDel.getName());
                        FileManager.getInstance().getChateauWorkspace().getV().get(index).getInfo().removePhotos(imageToDel.getAbsolutePath(),imageToDel);
                    }
                }

                for (int i = 0; i < getInfoImages().size(); i++) {
                    if (!FileManager.getInstance().getChateauWorkspace().getV().get(index).getInfo().getPhotos().contains(getInfoImages().get(i))) {
                        System.out.println("stuff to be add" + getInfoImages().get(i).getName());
                        FileManager.getInstance().getChateauWorkspace().getV().get(index).getInfo().addPhotos(getInfoImages().get(i).getAbsolutePath(), visitInfosPath, getInfoImages().get(i).getName());
                    }
                }

                FileManager.getInstance().getChateauWorkspace().getV().get(index).getInfo().writeContent_EN(visitPresTextENInf.getText());
                FileManager.getInstance().getChateauWorkspace().getV().get(index).getInfo().writeContent_FR(visitPresTextFRInf.getText());
            }

            GUIFormsController.getInstance().closeForm();
            stage.close();
        }

        //overviewImages.clear();
        //infoImages.clear();
    }

    private boolean validForm() {
        // TODO: 28/04/2016 vérif de form
        // TODO: 28/04/2016 afficher sous popup les champs qui ne respectent pas les conditions
        return true;
    }


    public void setInfoImages(ArrayList<File> selectedImages) {
        System.out.print("info: " + selectedImages.toString());
        this.infoImages = selectedImages;

    }

    public void setOverviewImages(ArrayList<File> selectedImages) {
        System.out.print("overview:" + selectedImages.size());
        this.overviewImages = selectedImages;
    }

    public ArrayList<File> getOverviewImages() {
        return overviewImages;
    }

    public ArrayList<File> getInfoImages() {
        return infoImages;
    }
}
