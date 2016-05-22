package gui.Controller.photo;

import files.FileTools;
import gui.Controller.GUIFormsController;
import gui.Controller.chateau.GUIControllerChateauIPForm;
import gui.Controller.chateau.GUIControllerChateauVisitForm;
import gui.Controller.enums.PictureFormType;
import gui.Controller.enums.VisitType;
import gui.Controller.village.GUIControllerVillageIPForm;
import gui.Controller.village.GUIControllerVillageVisitForm;
import gui.GUIUtilities;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.stream.Collectors;

/**
 * Controlleur pour l'ajout des photos et vidéos
 * Classe Singleton
 */
public class GUIControllerPhotoForm {

    private static GUIControllerPhotoForm INSTANCE = new GUIControllerPhotoForm(); // instance singleton
    private ObservableList<CheckBox> picturesCheckBoxList = FXCollections.observableArrayList(); //liste des cases a cocher
    private ObservableList<AnchorPane> pictureContentContainer = FXCollections.observableArrayList();
    private VBox verticalContentDisplay;
    private Stage stage;
    private BorderPane pictureForm;

    private VisitType visitType;
    private PictureFormType pictureFormType;
    private ArrayList<File> workingImageList;
    private File selectedDescImage;


    private GUIControllerPhotoForm() {
        initializeWindow();
        selectedDescImage = null;
        workingImageList = new ArrayList<>();
    }

    /**
     * Retourne l'instance de la classe singleton
     *
     * @return l'instance de classe
     */
    public static GUIControllerPhotoForm getInstance() {
        return INSTANCE;
    }

    /**
     * Charge la view fxml
     */
    private void initializeWindow() {
        try {
            pictureForm = (BorderPane) GUIUtilities.loadLayout("view/photos/photoFormView.fxml", this);
            stage = new Stage();
            stage.setScene(new Scene(pictureForm));

            stage.setOnCloseRequest(event -> {
                saveChanges();
            });

        } catch (IOException e) {
            e.printStackTrace();
            GUIFormsController.getInstance().displayExceptionAlert(e,"Erreur lors de l'affichage du formulaire de modification des images").showAndWait();

        }
    }

    /**
     * fonction qui set les images séléctionnés selon le form qui a appelé la classe
     */
    @FXML
    public void saveChanges() {

        if (visitType == VisitType.CHATEAU) {
            switch (pictureFormType) {

                case OVERVIEW:
                    GUIControllerChateauVisitForm.getInstance().setOverviewImages(workingImageList);
                    break;
                case INFO:
                    GUIControllerChateauVisitForm.getInstance().setInfoImages(workingImageList);
                    break;

                case INFO_DESC:
                    GUIControllerChateauVisitForm.getInstance().setInfoDesctiptive(selectedDescImage);
                    break;

                case DESCRIPTIVE_PICTURE:
                    GUIControllerChateauIPForm.getInstance().setDescPic(selectedDescImage);
                    break;

                case INDOORS_PICTURES:
                    GUIControllerChateauIPForm.getInstance().setInterieur(workingImageList);
                    break;

                case PANORAMIC_PICTURES:
                    GUIControllerChateauIPForm.getInstance().set_360(workingImageList);
                    break;

                case PICTURES:
                    GUIControllerChateauIPForm.getInstance().setPhotos(workingImageList);
                    break;

                case VIDEOS:
                    GUIControllerChateauIPForm.getInstance().setVideos(workingImageList);
                    break;
            }
        }
        else if (visitType == VisitType.VILLAGE) {
            switch (pictureFormType) {

                case OVERVIEW:
                    GUIControllerVillageVisitForm.getInstance().setOverviewImages(workingImageList);
                    break;

                case INFO:
                    GUIControllerVillageVisitForm.getInstance().setInfoImages(workingImageList);
                    break;

                case INFO_DESC:
                    GUIControllerVillageVisitForm.getInstance().setInfoDesctiptive(selectedDescImage);
                    break;

                case DESCRIPTIVE_PICTURE:
                    GUIControllerVillageIPForm.getInstance().setDescPic(selectedDescImage);
                    break;

                case INDOORS_PICTURES:
                    GUIControllerVillageIPForm.getInstance().setInterieur(workingImageList);
                    break;

                case PANORAMIC_PICTURES:
                    GUIControllerVillageIPForm.getInstance().set_360(workingImageList);
                    break;

                case PICTURES:
                    GUIControllerVillageIPForm.getInstance().setPhotos(workingImageList);
                    break;

                case VIDEOS:
                    GUIControllerVillageIPForm.getInstance().setVideos(workingImageList);
                    break;
            }
        }

        stage.close();
    }

    /**
     * Clean les listes contenant les médias pour remettre à zero
     */
    public void wipePictures() {
        workingImageList.clear();
        selectedDescImage = null;
    }

    /**
     * Affichage du formulaire
     * @param visitType type de visite appelante
     * @param pictureFormType type de donnée nécessaire
     */
    public void displayForm(VisitType visitType, PictureFormType pictureFormType) {
        this.visitType = visitType;
        this.pictureFormType = pictureFormType;
        pictureForm.setCenter(loadContent());
        stage.show();
    }

    /**
     * Charge les images existantes pour affichage
     * Initialise la fenêtre
     * @return le conteneur des données
     */
    private ScrollPane loadContent() {
        pictureContentContainer.clear();
        picturesCheckBoxList.clear();
        verticalContentDisplay = new VBox();
        verticalContentDisplay.setPadding(new Insets(1, 1, 1, 1));
        initWorkingList();

        if (pictureFormType == PictureFormType.DESCRIPTIVE_PICTURE || pictureFormType == PictureFormType.INFO_DESC) {
            pictureContentContainer.add(createAnchorPane(selectedDescImage));
        }
        else {
            pictureContentContainer.addAll(workingImageList.stream().map(this::createAnchorPane).collect(Collectors.toList()));
        }

        verticalContentDisplay.getChildren().addAll(pictureContentContainer);
        ScrollPane sc = new ScrollPane();
        sc.setContent(verticalContentDisplay);
        return sc;
    }

    /**
     * Recharge le contenu de la fenêtre
     * @return le conteneur des données
     */
    private ScrollPane reloadContent() {
        pictureContentContainer.clear();
        picturesCheckBoxList.clear();

        verticalContentDisplay = new VBox();
        verticalContentDisplay.setPadding(new Insets(1, 1, 1, 1));

        if (pictureFormType == PictureFormType.DESCRIPTIVE_PICTURE || pictureFormType == PictureFormType.INFO_DESC) {
            pictureContentContainer.addAll(createAnchorPane(selectedDescImage));
        }
        else {
            pictureContentContainer.addAll(workingImageList.stream().map(this::createAnchorPane).collect(Collectors.toList()));
        }

        verticalContentDisplay.getChildren().addAll(pictureContentContainer);
        ScrollPane sc = new ScrollPane();
        sc.setContent(verticalContentDisplay);
        return sc;
    }

    /**
     * Récupère les données existantes pour modification
     */
    private void initWorkingList() {

        if (visitType == VisitType.CHATEAU) {
            switch (pictureFormType) {
                case OVERVIEW:
                    workingImageList = (ArrayList<File>) GUIControllerChateauVisitForm.getInstance().getOverviewImages().clone();
                    break;
                case INFO:
                    workingImageList = (ArrayList<File>) GUIControllerChateauVisitForm.getInstance().getInfoImages().clone();
                    break;
                case INFO_DESC:
                    selectedDescImage = GUIControllerChateauVisitForm.getInstance().getInfoDesctiptive();
                    break;
                case DESCRIPTIVE_PICTURE:
                    selectedDescImage = GUIControllerChateauIPForm.getInstance().getDescPic();
                    break;
                case INDOORS_PICTURES:
                    workingImageList = (ArrayList<File>) GUIControllerChateauIPForm.getInstance().getInterieur().clone();
                    break;
                case PANORAMIC_PICTURES:
                    workingImageList = (ArrayList<File>) GUIControllerChateauIPForm.getInstance().get_360().clone();
                    break;
                case PICTURES:
                    workingImageList = (ArrayList<File>) GUIControllerChateauIPForm.getInstance().getPhotos().clone();
                    break;
                case VIDEOS:
                    workingImageList = (ArrayList<File>) GUIControllerChateauIPForm.getInstance().getVideos().clone();
                    break;
            }
        } else if (visitType == VisitType.VILLAGE) {
            switch (pictureFormType) {
                case OVERVIEW:
                    workingImageList = (ArrayList<File>) GUIControllerVillageVisitForm.getInstance().getOverviewImages().clone();
                    break;
                case INFO:
                    workingImageList = (ArrayList<File>) GUIControllerVillageVisitForm.getInstance().getInfoImages().clone();
                    break;
                case INFO_DESC:
                    selectedDescImage = GUIControllerVillageVisitForm.getInstance().getInfoDesctiptive();
                    break;
                case DESCRIPTIVE_PICTURE:
                    selectedDescImage = GUIControllerVillageIPForm.getInstance().getDescPic();
                    break;
                case INDOORS_PICTURES:
                    workingImageList = (ArrayList<File>) GUIControllerVillageIPForm.getInstance().getInterieur().clone();
                    break;
                case PANORAMIC_PICTURES:
                    workingImageList = (ArrayList<File>) GUIControllerVillageIPForm.getInstance().get_360().clone();
                    break;
                case PICTURES:
                    workingImageList = (ArrayList<File>) GUIControllerVillageIPForm.getInstance().getPhotos().clone();
                    break;
                case VIDEOS:
                    workingImageList = (ArrayList<File>) GUIControllerVillageIPForm.getInstance().getVideos().clone();
                    break;
            }
        }
    }

    /**
     * Creation de miniature des images
     * @param imageFile l'image à afficher
     * @return la miniature créée
     */
    private Image createImage(final File imageFile) {

        try {
            FileInputStream f = new FileInputStream(imageFile);
            Image image = new Image(f, 75, 75, false, true);
            f.close();
            return image;
        } catch (IOException e) {
            e.printStackTrace();
            GUIFormsController.getInstance().displayExceptionAlert(e,"Erreur lors de la création des miniatures d'images").showAndWait();

        }
        return null;
    }

    /**
     * Conteneur avec une case à cocher, la miniature et le nom pour chaque image
     * @param file le fichier sur lequel se baser
     * @return le conteneur avec les données initialisées
     */
    private AnchorPane createAnchorPane(File file) {

        AnchorPane ap = new AnchorPane();
        ap.prefWidth(400.0);
        ap.prefHeight(150.0);

        ImageView i;
        CheckBox cb;
        Label l;

        if (file != null) {
            ap.setId(file.getName());

            i = new ImageView(createImage(file));
            i.setFitHeight(100.0);
            i.setFitWidth(100.0);
            i.setLayoutX(132.0);
            i.setLayoutY(29.0);

            cb = new CheckBox();
            cb.setLayoutX(42.0);
            cb.setLayoutY(70.0);
            cb.setId(file.getName());
            picturesCheckBoxList.add(cb);

            l = new Label(file.getName());
            l.setLayoutX(249.0);
            l.setLayoutY(71.0);

            ap.getChildren().addAll(i, cb, l);

        }
        return ap;
    }

    /**
     * Suppression des avec les cases cochées
     */
    @FXML
    public void deleteImages() {
        System.out.println("del");

        ArrayList<Integer> indexToDel = new ArrayList<>();

        for (int i = 0; i < picturesCheckBoxList.size(); i++) {
            if (picturesCheckBoxList.get(i).isSelected()) {
                indexToDel.add(i);
            }
        }

        for (int i = indexToDel.size() - 1; i >= 0; i--) {
            int index = indexToDel.get(i);
            pictureContentContainer.remove(index);
            verticalContentDisplay.getChildren().remove(index);
            if (pictureFormType != PictureFormType.DESCRIPTIVE_PICTURE) {
                workingImageList.remove(i);
            }
            else {
                selectedDescImage = null;
            }
            picturesCheckBoxList.remove(index);
        }
    }


    /**
     * Ajout d'un média grâce au sélecteur systeme
     * Diffère selon le type de fichier
     */
    @FXML
    public void addImage() {

            switch (pictureFormType) {
                case OVERVIEW:
                    workingImageList.addAll(FileTools.MultipleFileChooser(FileTools.IMAGES_FILE_FILTER));
                    break;

                case INFO:
                    workingImageList.addAll(FileTools.MultipleFileChooser(FileTools.IMAGES_FILE_FILTER));
                    break;

                case INFO_DESC:
                    selectedDescImage = FileTools.FileChooser(FileTools.IMAGES_FILE_FILTER);
                    break;
                
                case DESCRIPTIVE_PICTURE:
                    selectedDescImage = FileTools.FileChooser(FileTools.IMAGES_FILE_FILTER);
                    break;

                case INDOORS_PICTURES:
                    workingImageList.addAll(FileTools.MultipleFileChooser(FileTools.IMAGES_FILE_FILTER));
                    break;

                case PANORAMIC_PICTURES:
                    workingImageList.addAll(FileTools.MultipleFileChooser(FileTools.IMAGES_FILE_FILTER));
                    break;

                case PICTURES:
                    workingImageList.addAll(FileTools.MultipleFileChooser(FileTools.IMAGES_FILE_FILTER));
                    break;

                case VIDEOS:
                    workingImageList.addAll(FileTools.MultipleFileChooser(FileTools.VIDEOS_FILE_FILTER));
                    break;
            }
        pictureForm.setCenter(reloadContent());
    }

}