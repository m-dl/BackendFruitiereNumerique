package gui.Controller.chateau;

import entities.chateau.Info;
import entities.chateau.InterestPoint;
import entities.chateau.Overview;
import entities.chateau.Visit;
import files.FileManager;
import gui.Controller.GUIFormsController;
import gui.Controller.photo.GUIControllerPhotoForm;
import gui.GUIUtilities;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

import static gui.Controller.enums.PictureFormType.*;
import static gui.Controller.enums.VisitType.CHATEAU;

/**
 * Controlleur du form d'ajout et de modification de visite du château
 */
public class GUIControllerChateauVisitForm {

    private static GUIControllerChateauVisitForm INSTANCE = new GUIControllerChateauVisitForm();

    public TextField visitName;
    public TextField visitNameEN;
    public TextArea visitPresTextFROv;
    public TextArea visitPresTextENOv;
    public TextArea visitLengthFROv;
    public TextArea visitLengthENOv;
    public TextArea visitPresTextFRInf;
    public TextArea visitPresTextENInf;

    public Label infoDescLabel;
    public Label overviewSizeText, infoSizeText;
    public Button addPicOverview, addPicInf, addInfoDescB;

    public File infoDesctiptive;
    public ArrayList<File> overviewImages;
    public ArrayList<File> infoImages;

    private Stage stage;
    private boolean isNewVisit;
    private String errorList;


    /**
     * Constructeur initialisant les listes de média
     */
    private GUIControllerChateauVisitForm() {
        overviewImages = new ArrayList<>();
        infoImages = new ArrayList<>();
        errorList = "";
    }

    /**
     * Classe singleton, retourne l'instance de la classe
     *
     * @return l'instance de la classe
     */
    public static GUIControllerChateauVisitForm getInstance() {
        return INSTANCE;
    }

    /**
     * Affichage de la fenêtre du formulaire
     * @param isNewVisit si c'est une nouvelle visite ou existante
     * @param selectedVisit la visite sélectionnée
     */
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

                    infoDesctiptive = FileManager.getInstance().getChateauWorkspace().getV().get(index).getInfo().getPicture();
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
            GUIFormsController.getInstance().displayExceptionAlert(e,"Erreur lors de l'affichage du formulaire d'ajout de visite").showAndWait();

        }
    }


    /**
     * Pré-remplissage des champs en cas de modification
     * @param v la visite concernée
     */
    public void fillInputs(Visit v) {
        visitName.setText(v.getName());
        visitNameEN.setText(v.readName_EN());

        visitPresTextFROv.setText(v.getOverview().readPresentation_FR());
        visitPresTextENOv.setText(v.getOverview().readPresentation_EN());
        visitLengthFROv.setText(v.getOverview().readLength_FR());
        visitLengthENOv.setText(v.getOverview().readLength_EN());

        visitPresTextFRInf.setText(v.getInfo().readContent_FR());
        visitPresTextENInf.setText(v.getInfo().readContent_EN());

        infoDescLabel.setText((v.getInfo().getPicture() == null) ? "Aucune image sélectionnée" : "Une image sélectionnée");


        //opérateurs ternaires pour savoir si mettre le texte au singulier ou au pluriel
        overviewSizeText.setText((v.getOverview().getImagesContent().size() <= 1)
                ? v.getOverview().getImagesContent().size() + " image sélectionnée"
                : v.getOverview().getImagesContent().size() + " images sélectionnées");

        infoSizeText.setText((v.getInfo().getPhotos().size() <= 1)
                ? v.getInfo().getPhotos().size() + " image sélectionnée"
                : v.getInfo().getPhotos().size() + " images sélectionnées");

    }

    /**
     * Affichage du form d'ajout d'images overview
     */
    @FXML public void addOverviewPictures() {
        GUIFormsController.getInstance().displayPhotoForm(CHATEAU, OVERVIEW, this.isNewVisit);
    }

    /**
     * Affichage du form d'ajout d'images info
     */
    @FXML public void addInfoPictures() {
        GUIFormsController.getInstance().displayPhotoForm(CHATEAU, INFO, this.isNewVisit);
    }

    /**
     * Affichage du form d'ajout de l'image descriptive
     */
    @FXML
    public void addInfoDescPic() {
        GUIFormsController.getInstance().displayPhotoForm(CHATEAU, INFO_DESC, this.isNewVisit);
    }

    /**
     * Enregistrement des données du formulaire
     */
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
                v.setIP1(new ArrayList<>());
                v.setIP2(new ArrayList<>());
                v.setIP3(new ArrayList<>());
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

                visitInfos.addPicture(this.infoDesctiptive.getAbsolutePath(), visitInfosPath, this.infoDesctiptive.getName());


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

                    if (this.infoDesctiptive != null && !infoDesctiptive.equals(selectedVisit.getInfo().getPicture())) {
                        if (selectedVisit.getInfo().getPicture() != null) {
                            FileManager.getInstance().getChateauWorkspace().getV().get(index).getInfo().removePicture(selectedVisit.getInfo().getPicture().getPath(), selectedVisit.getInfo().getPicture());
                            FileManager.getInstance().getChateauWorkspace().getV().get(index).getInfo().addPicture(this.infoDesctiptive.getAbsolutePath(), visitInfosPath, this.infoDesctiptive.getName());
                        } else {
                            FileManager.getInstance().getChateauWorkspace().getV().get(index).getInfo().addPicture(this.infoDesctiptive.getAbsolutePath(), visitInfosPath, this.infoDesctiptive.getName());
                        }
                    }

                    FileManager.getInstance().getChateauWorkspace().getV().get(index).writeName_EN(this.visitNameEN.getText());
                    FileManager.getInstance().getChateauWorkspace().getV().get(index).getOverview().writePresentation_FR(visitPresTextFROv.getText());
                    FileManager.getInstance().getChateauWorkspace().getV().get(index).getOverview().writePresentation_EN(visitPresTextENOv.getText());
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

            wipeData();
            GUIFormsController.getInstance().closeForm();
            stage.close();
        }
        else {
            GUIFormsController.getInstance().displayErrorAlert("Un ou plusieurs champs sont vides",
                    "Les champs manquants sont:",errorList).showAndWait();
            errorList = "";

        }
    }

    /**
     * Remise à zéro des données
     */
    private void wipeData() {
        infoDesctiptive = null;
        overviewImages.clear();
        infoImages.clear();
        GUIControllerPhotoForm.getInstance().wipePictures();
    }

    /**
     * Renommage d'une visite, déplacement du dossier
     * @param oldVisit visite avant le renommage
     * @param visitPath chemin de destination de la nouvelle visite
     * @param newName nouveau nom de la visite
     */
    private void renameVisit(Visit oldVisit, String visitPath, String newName) {


        String newVisitPath = visitPath + "/" + newName;
        String visitOverviewPath = newVisitPath + "/" + "visite-overview";
        String visitInfosPath = newVisitPath + "/" + "visite-info";

        //on crée les dossiers
        Visit v = new Visit(newVisitPath, newName);
        v.writeName_EN(this.visitNameEN.getText());

        //on bouge les poi

        ArrayList<InterestPoint> ip1 = oldVisit.getIP1();
        ArrayList<InterestPoint> ip2 = oldVisit.getIP2();
        ArrayList<InterestPoint> ip3 = oldVisit.getIP3();

        for (int i = 0; i < ip1.size(); i++) {
            v.addInterestPoint(GUIControllerChateauIPForm.getInstance().renameIP(ip1.get(i), newVisitPath, ip1.get(i).getName(), true),v.getIP1());
        }

        for (int i = 0; i < ip2.size(); i++) {
            v.addInterestPoint(GUIControllerChateauIPForm.getInstance().renameIP(ip2.get(i), newVisitPath, ip2.get(i).getName(), true),v.getIP2());
        }

        for (int i = 0; i < ip3.size(); i++) {
            v.addInterestPoint(GUIControllerChateauIPForm.getInstance().renameIP(ip3.get(i), newVisitPath, ip3.get(i).getName(), true),v.getIP3());
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

        visitInfos.addPicture(this.infoDesctiptive.getAbsolutePath(), visitInfosPath, this.infoDesctiptive.getName());

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

    /**
     * Vérifie la validité du formulaire
     * @return si le form est bien rempli
     */
    private boolean validForm() {
        boolean isValid = true;

        if (this.visitName.getText().equals("")) {
            errorList += "• Le case du nom est vide\n";
            visitName.getStyleClass().add("errorStyle");
            isValid = false;
        }
        else {
            visitName.getStyleClass().clear();
            visitName.getStyleClass().addAll("text-field", "text-input");
        }

        if (this.visitNameEN.getText().equals("")) {
            errorList += "• Le case du nom en anglais est vide\n";
            visitNameEN.getStyleClass().add("errorStyle");
            isValid = false;
        }
        else {
            visitNameEN.getStyleClass().clear();
            visitNameEN.getStyleClass().addAll("text-field", "text-input");
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

        if (this.overviewImages.size() < 3) {
            errorList += "• Au moins trois images 'Overview' doivent être sélectionnées\n";
            addPicOverview.getStyleClass().addAll("buttonErrorStyle");
            isValid = false;
        }
        else {
            addPicOverview.getStyleClass().clear();
            addPicOverview.getStyleClass().addAll("button");
        }

        if (this.infoDesctiptive == null) {
            errorList += "• Une image descriptive doit être sélectionnée\n";
            addInfoDescB.getStyleClass().addAll("buttonErrorStyle");
            isValid = false;
        } else {
            addInfoDescB.getStyleClass().clear();
            addInfoDescB.getStyleClass().addAll("button");
        }

        if (this.infoImages.size() == 0) {
            errorList += "• Au moins une image 'Info' doit être sélectionnée\n";
            addPicInf.getStyleClass().addAll("buttonErrorStyle");
            isValid = false;
        }
        else {
            addPicInf.getStyleClass().clear();
            addPicInf.getStyleClass().addAll("button");
        }
        return isValid;
    }

    /**
     * Getteur des photos overview
     * @return les photos overview
     */
    public ArrayList<File> getOverviewImages() {
        return overviewImages;
    }

    /**
     * setteur des photos overview
     * @param selectedImages les photos overview à enregister
     */
    public void setOverviewImages(ArrayList<File> selectedImages) {

        this.overviewImages = selectedImages;
        overviewSizeText.setText((selectedImages.size() <= 1)
                ? selectedImages.size() + " image sélectionnée"
                : selectedImages.size() + " images sélectionnées");
    }

    /**
     * Getteur des photos info
     * @return les photos info
     */
    public ArrayList<File> getInfoImages() {
        return infoImages;
    }

    /**
     * setteur des photos info
     * @param selectedImages les photos info à enregister
     */
    public void setInfoImages(ArrayList<File> selectedImages) {

        this.infoImages = selectedImages;
        infoSizeText.setText((selectedImages.size() <= 1)
                ? selectedImages.size() + " image sélectionnée"
                : selectedImages.size() + " images sélectionnées");
    }

    /**
     * Getteur de l'image descriptive  de la visite
     * @return l'image descriptive du point
     */
    public File getInfoDesctiptive() {
        return this.infoDesctiptive;
    }

    /**
     * setteur de l'image descriptive
     * @param descPic l'image descriptive à enregister
     */
    public void setInfoDesctiptive(File descPic) {
        this.infoDesctiptive = descPic;

        if (descPic == null) {
            infoDescLabel.setText("Aucune image sélectionnée");
        } else {
            infoDescLabel.setText("Une image sélectionnée");
        }
    }
}
