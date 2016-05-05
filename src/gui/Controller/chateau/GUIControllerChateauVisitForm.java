package gui.Controller.chateau;

import entities.chateau.Info;
import entities.chateau.InterestPoint;
import entities.chateau.Overview;
import entities.chateau.Visit;
import files.FileManager;
import gui.Controller.GUIFormsController;
import gui.GUIUtilities;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

import static gui.Controller.enums.PictureFormType.INFO;
import static gui.Controller.enums.PictureFormType.OVERVIEW;
import static gui.Controller.enums.VisitType.CHATEAU;

public class GUIControllerChateauVisitForm {

    private static GUIControllerChateauVisitForm INSTANCE = new GUIControllerChateauVisitForm();

    public Label overviewSizeText, infoSizeText;

    public TextField visitName;
    public TextField visitNameEN;
    public TextArea visitPresTextFROv;
    public TextArea visitPresTextENOv;
    public TextArea visitLengthFROv;
    public TextArea visitLengthENOv;
    public TextArea visitPresTextFRInf;
    public TextArea visitPresTextENInf;

    public ArrayList<File> overviewImages;
    public ArrayList<File> infoImages;

    private Stage stage;
    private boolean isNewVisit;
    private String errorList;


    private GUIControllerChateauVisitForm() {
        overviewImages = new ArrayList<>();
        infoImages = new ArrayList<>();
        errorList = "";
    }

    public static GUIControllerChateauVisitForm getInstance() {
        return INSTANCE;
    }


    public void displayForm(boolean isNewVisit, Visit selectedVisit) {

        this.isNewVisit = isNewVisit;

        try {

            ScrollPane root = (ScrollPane) GUIUtilities.loadLayout("view/chateau/visitForm.fxml", this);
            root.getStylesheets().add("errorStyle.css");

            stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setOnCloseRequest(closeEvent -> GUIFormsController.getInstance().closeForm());


            if (!isNewVisit) {
                if (selectedVisit != null) {
                    int index = FileManager.getInstance().getChateauWorkspace().getV().indexOf(GUIControllerChateau.getInstance().getSelectedVisit());
                    overviewImages = FileManager.getInstance().getChateauWorkspace().getV().get(index).getOverview().getImagesContent();
                    infoImages = FileManager.getInstance().getChateauWorkspace().getV().get(index).getInfo().getPhotos();
                    this.fillInputs(selectedVisit);
                    stage.setTitle("Modification de la visite: " + selectedVisit.getName());
                    GUIFormsController.getInstance().displayForm(stage);
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
        visitName.setText(v.getName());
        visitNameEN.setText(v.readName_EN());
        visitPresTextFROv.setText(v.getOverview().readPresentation_FR());
        visitPresTextENOv.setText(v.getOverview().readPresentation_EN());
        visitLengthFROv.setText(v.getOverview().readLength_FR());
        visitLengthENOv.setText(v.getOverview().readLength_EN());
        visitPresTextFRInf.setText(v.getInfo().readContent_FR());
        visitPresTextENInf.setText(v.getInfo().readContent_EN());
        overviewSizeText.setText(v.getOverview().getImagesContent().size() +" images sélectionnées");
        infoSizeText.setText(v.getInfo().getPhotos().size()+" images sélectionnées");
    }


    @FXML public void addOverviewPictures() {
        GUIFormsController.getInstance().displayPhotoForm(CHATEAU, OVERVIEW, this.isNewVisit);
    }

    @FXML public void addInfoPictures() {
        GUIFormsController.getInstance().displayPhotoForm(CHATEAU, INFO, this.isNewVisit);
    }

    @FXML public void saveChanges() {

        if(validForm()) {
            Overview visitOverview;
            Info visitInfos;

            if (isNewVisit) {

                String vName = this.visitName.getText();
                String vNameEN = this.visitNameEN.getText();

                String visitPath = FileManager.WORKSPACE + "/" + FileManager.CHATEAU + "/" + vName;
                String visitOverviewPath = visitPath + "/" + "visite-overview";
                String visitInfosPath = visitPath + "/" + "visite-info";

                Visit v = new Visit(visitPath, vName);
                v.setIP(new ArrayList<>());
                v.writeName_EN(vNameEN);

                visitOverview = new Overview(visitOverviewPath);

                visitOverview.writePresentation_FR(visitPresTextFROv.getText());
                visitOverview.writePresentation_EN(visitPresTextENOv.getText());
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
                String originalName = selectedVisit.getName();

                String visitPath = FileManager.WORKSPACE + "/" + FileManager.CHATEAU + "/" + originalName;
                String visitOverviewPath = visitPath + "/" + "visite-overview";
                String visitInfosPath = visitPath + "/" + "visite-info";

                if (!Objects.equals(vName, originalName)) {

                    visitPath = FileManager.WORKSPACE + "/" + FileManager.CHATEAU + "/";
                    renameVisit(selectedVisit,visitPath,vName);

                } else {

                    FileManager.getInstance().getChateauWorkspace().getV().get(index).writeName_EN(this.visitNameEN.getText());
                    FileManager.getInstance().getChateauWorkspace().getV().get(index).getOverview().writePresentation_FR(visitPresTextFROv.getText());
                    FileManager.getInstance().getChateauWorkspace().getV().get(index).getOverview().writePresentation_EN(visitPresTextENOv.getText());
                    FileManager.getInstance().getChateauWorkspace().getV().get(index).getOverview().writeLength_EN(visitPresTextENOv.getText());
                    FileManager.getInstance().getChateauWorkspace().getV().get(index).getOverview().writeLength_FR(visitLengthFROv.getText());
                    FileManager.getInstance().getChateauWorkspace().getV().get(index).getOverview().writeLength_EN(visitLengthENOv.getText());


                    //Suppression et ajout des images qui ont été supprimées ou ajoutées pour Overview.
                    for (int i = 0; i < FileManager.getInstance().getChateauWorkspace().getV().get(index).getOverview().getImagesContent().size(); i++) {
                        if (!(getOverviewImages().contains(FileManager.getInstance().getChateauWorkspace().getV().get(index).getOverview().getImagesContent().get(i)))) {
                            File imageToDel = FileManager.getInstance().getChateauWorkspace().getV().get(index).getOverview().getImagesContent().get(i);
                            System.out.println("stuff to be del" + imageToDel.getName());
                            FileManager.getInstance().getChateauWorkspace().getV().get(index).getOverview().removeImagesContent(imageToDel.getPath(), imageToDel);
                        }
                    }

                    for (int i = 0; i < getOverviewImages().size(); i++) {
                        if (!FileManager.getInstance().getChateauWorkspace().getV().get(index).getOverview().getImagesContent().contains(getOverviewImages().get(i))) {
                            System.out.println("stuff to be add" + getOverviewImages().get(i).getName());

                            FileManager.getInstance().getChateauWorkspace().getV().get(index).getOverview().addImagesContent(getOverviewImages().get(i).getAbsolutePath(), visitOverviewPath, getOverviewImages().get(i).getName());

                        }
                    }

                    //Suppression et ajout des images qui ont été supprimées ou ajoutées pour Info.

                    for (int i = 0; i < FileManager.getInstance().getChateauWorkspace().getV().get(index).getInfo().getPhotos().size(); i++) {
                        if (!(getInfoImages().contains(FileManager.getInstance().getChateauWorkspace().getV().get(index).getInfo().getPhotos().get(i)))) {
                            File imageToDel = FileManager.getInstance().getChateauWorkspace().getV().get(index).getInfo().getPhotos().get(i);
                            System.out.println("stuff to be del " + imageToDel.getName());
                            FileManager.getInstance().getChateauWorkspace().getV().get(index).getInfo().removePhotos(imageToDel.getAbsolutePath(), imageToDel);
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
            }

            GUIFormsController.getInstance().closeForm();
            stage.close();
        }
        else {
            GUIFormsController.getInstance().displayErrorAlert("Un ou plusieurs champs sont vides",
                    "Les champs manquants sont:",errorList).showAndWait();
            errorList = "";

        }
    }

    private void renameVisit(Visit oldVisit, String visitPath, String newName) {


        String newVisitPath = visitPath + "/" + newName;
        String visitOverviewPath = newVisitPath + "/" + "visite-overview";
        String visitInfosPath = newVisitPath + "/" + "visite-info";

        //on crée les dossiers
        Visit v = new Visit(newVisitPath, newName);
        v.writeName_EN(this.visitNameEN.getText());

        //on bouge les poi

        ArrayList<InterestPoint> ip = oldVisit.getIP();

        for (int i = 0; i < ip.size(); i++) {
            v.addInterestPoint(GUIControllerChateauIPForm.getInstance().renameIP(ip.get(i), newVisitPath, ip.get(i).getName(), true));
        }

        //on bouge overview
        Overview visitOverview = new Overview(visitOverviewPath);

        visitOverview.writePresentation_FR(oldVisit.getOverview().readLength_FR());
        visitOverview.writePresentation_EN(oldVisit.getOverview().readLength_EN());
        visitOverview.writeLength_FR(oldVisit.getOverview().readLength_FR());
        visitOverview.writeLength_EN(oldVisit.getOverview().readLength_EN());

        for (int i = 0; i < this.overviewImages.size(); i++) {
            visitOverview.addImagesContent(this.overviewImages.get(i).getAbsolutePath(), visitOverviewPath, this.overviewImages.get(i).getName());
        }

        //on bouge infos
        Info visitInfos = new Info(visitInfosPath);

        visitInfos.writeContent_FR(oldVisit.getInfo().readContent_FR());
        visitInfos.writeContent_EN(oldVisit.getInfo().readContent_EN());

        for (int i = 0; i < this.infoImages.size(); i++) {
            visitInfos.addPhotos(this.infoImages.get(i).getAbsolutePath(), visitInfosPath, this.infoImages.get(i).getName());
        }

        v.setInfo(visitInfos);
        v.setOverview(visitOverview);

        FileManager.getInstance().getChateauWorkspace().deleteVisit(oldVisit,visitPath + "/" + oldVisit.getName());
        FileManager.getInstance().getChateauWorkspace().addVisit(v);

        GUIControllerChateau.getInstance().visitListC.remove(oldVisit);
        GUIControllerChateau.getInstance().visitListC.add(v);

    }

    private boolean validForm() {
        boolean isValid = true;

        // TODO: 05/05/2016 taille image selecinnée
        System.out.println(visitPresTextENInf.getStyleClass());

        if (this.overviewImages.size() == 0) {
            errorList += "• Au moins une image 'Overview' doit être sélectionnée\n";
            isValid = false;
        }

        if (this.infoImages.size() == 0) {
            errorList += "• Au moins une image 'Info' doit être sélectionnée\n";
            isValid = false;
        }

        if (this.visitName.getText().equals("")) {
            errorList += "• Le case du nom est vide\n";
            visitName.getStyleClass().add("errorStyle");
            isValid = false;
        }
        else {
            visitName.getStyleClass().clear();
            visitName.getStyleClass().addAll("text-field", "text-input");
        }

        if (this.visitPresTextFROv.getText().equals("")) {
            errorList += "• Le case de présentation de la visite Overview en français est vide\n";
            visitPresTextFROv.getStyleClass().add("errorStyle");
            isValid = false;
        }
        else {
            visitPresTextFROv.getStyleClass().clear();
            visitPresTextFROv.getStyleClass().addAll("text-input","text-area");
        }

        if (this.visitPresTextENOv.getText().equals("")) {
            errorList += "• Le case de présentation de la visite Overview en anglais est vide\n";
            visitPresTextENOv.getStyleClass().add("errorStyle");
            isValid = false;
        }
        else {
            visitPresTextENOv.getStyleClass().clear();
            visitPresTextENOv.getStyleClass().addAll("text-input","text-area");
        }

        if (this.visitLengthFROv.getText().equals("")) {
            errorList += "• Le case de la durée de la visite en français est vide\n";
            visitLengthFROv.getStyleClass().add("errorStyle");
            isValid = false;
        }
        else {
            visitLengthFROv.getStyleClass().clear();
            visitLengthFROv.getStyleClass().addAll("text-input","text-area");
        }

        if (this.visitLengthENOv.getText().equals("")) {
            errorList += "• Le case de la durée de la visite en anglais est vide\n";
            visitLengthENOv.getStyleClass().add("errorStyle");
            isValid = false;
        }
        else {
            visitLengthENOv.getStyleClass().clear();
            visitLengthENOv.getStyleClass().addAll("text-input","text-area");
        }

        if (this.visitPresTextFRInf.getText().equals("")) {
            errorList += "• Le case de présentation de la visite Infos en français est vide\n";
            visitPresTextFRInf.getStyleClass().add("errorStyle");
            isValid = false;
        }
        else {
            visitPresTextFRInf.getStyleClass().clear();
            visitPresTextFRInf.getStyleClass().addAll("text-input","text-area");
        }

        if (this.visitPresTextENInf.getText().equals("")) {
            errorList += "• Le case de présentation de la visite Infos en anglais est vide\n";
            visitPresTextENInf.getStyleClass().add("errorStyle");
            isValid = false;
        }
        else {
            visitPresTextENInf.getStyleClass().clear();
            visitPresTextENInf.getStyleClass().addAll("text-input","text-area");
        }

        return isValid;
    }

    public ArrayList<File> getOverviewImages() {
        return overviewImages;
    }

    public void setOverviewImages(ArrayList<File> selectedImages) {
        overviewSizeText.setText(selectedImages.size()+" images sélectionnés");
        System.out.println("overview:" + selectedImages.toString());
        this.overviewImages = selectedImages;
    }

    public ArrayList<File> getInfoImages() {
        return infoImages;
    }

    public void setInfoImages(ArrayList<File> selectedImages) {
        infoSizeText.setText(selectedImages.size()+" images sélectionnées");
        System.out.println("info: " + selectedImages.toString());
        this.infoImages = selectedImages;

    }
}
