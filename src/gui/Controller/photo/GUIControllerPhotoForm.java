package gui.Controller.photo;

import entities.Overview;
import entities.Visit;
import files.FileManager;
import files.FileTools;
import gui.Controller.chateau.GUIControllerChateau;
import gui.Controller.enums.PictureFormType;
import gui.Controller.enums.VisitType;
import gui.Controller.village.GUIControllerVillage;
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
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.stream.Collectors;

class FilePath {

    protected String name;
    protected String path;

    FilePath(String n, String p) {
        this.name = n;
        this.path = p;
    }
}

// TODO: 11/04/2016 quand creer visite, form doit etre vide
public class GUIControllerPhotoForm {

    private static GUIControllerPhotoForm INSTANCE = new GUIControllerPhotoForm();
    private ObservableList<CheckBox> picturesCheckBoxList = FXCollections.observableArrayList();
    private ObservableList<AnchorPane> pictureContentContainer = FXCollections.observableArrayList();
    private VBox verticalContentDisplay;
    private Stage stage;
    private BorderPane pictureForm;

    private VisitType visitType;
    private PictureFormType pictureFormType;

    private ArrayList<FilePath> test = new ArrayList<>();
    private GUIControllerPhotoForm() {
        initializeWindow();
    }

    public static GUIControllerPhotoForm getInstance() {
        return INSTANCE;
    }

    private void initializeWindow() {
        try {
            pictureForm = (BorderPane) GUIUtilities.loadLayout("view/photos/photoFormView.fxml", this);
            stage = new Stage();
            stage.setScene(new Scene(pictureForm));
            //stage.setOnCloseRequest(closeEvent -> GUIFormsController.getInstance().closeStage());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void displayForm(VisitType visitType, PictureFormType pictureFormType) {
        this.visitType = visitType;
        this.pictureFormType = pictureFormType;
        pictureForm.setCenter(loadContent());
        stage.show();
    }

    private ScrollPane loadContent() {
        pictureContentContainer.clear();
        picturesCheckBoxList.clear();
        test.clear();

        verticalContentDisplay = new VBox();
        verticalContentDisplay.setPadding(new Insets(1, 1, 1, 1));

        pictureContentContainer.addAll(getArrayList().stream().map(this::createAnchorPane).collect(Collectors.toList()));

        verticalContentDisplay.getChildren().addAll(pictureContentContainer);
        ScrollPane sc = new ScrollPane();
        sc.setContent(verticalContentDisplay);
        return sc;
    }

    private ArrayList<File> getArrayList() {
        ArrayList<File> pictureList = new ArrayList<>();

        if (visitType == VisitType.CHATEAU) {
            switch (pictureFormType) {
                case OVERVIEW:
                    pictureList = GUIControllerChateau.getInstance().getSelectedVisit().getOverview().getImagesContent();
                    break;
                case INFO:
                    pictureList = GUIControllerChateau.getInstance().getSelectedVisit().getInfo().getPhotos();
                    break;
                case DESCRIPTIVE_PICTURE:
                    if (GUIControllerChateau.getInstance().getSelectedPoint().getPicture() != null)
                        pictureList.add(GUIControllerChateau.getInstance().getSelectedPoint().getPicture());
                    break;
                case INDOORS_PICTURES:
                    pictureList = GUIControllerChateau.getInstance().getSelectedPoint().getInterieur();
                    break;
                case PANORAMIC_PICTURES:
                    pictureList = GUIControllerChateau.getInstance().getSelectedPoint().get_360();
                    break;
                case PICTURES:
                    pictureList = GUIControllerChateau.getInstance().getSelectedPoint().getPhotos();
                    break;
                case VIDEOS:
                    pictureList = GUIControllerChateau.getInstance().getSelectedPoint().getVideos();
                    break;
            }
        } else if (visitType == VisitType.VILLAGE) {
            switch (pictureFormType) {
                case OVERVIEW:
                    pictureList = GUIControllerVillage.getInstance().getSelectedVisit().getOverview().getImagesContent();
                    break;
                case INFO:
                    pictureList = GUIControllerVillage.getInstance().getSelectedVisit().getInfo().getPhotos();
                    break;
                case DESCRIPTIVE_PICTURE:
                    if (GUIControllerVillage.getInstance().getSelectedPoint().getPicture() != null)
                        pictureList.add(GUIControllerVillage.getInstance().getSelectedPoint().getPicture());
                    break;
                case INDOORS_PICTURES:
                    pictureList = GUIControllerVillage.getInstance().getSelectedPoint().getInterieur();
                    break;
                case PANORAMIC_PICTURES:
                    pictureList = GUIControllerVillage.getInstance().getSelectedPoint().get_360();
                    break;
                case PICTURES:
                    pictureList = GUIControllerVillage.getInstance().getSelectedPoint().getPhotos();
                    break;
                case VIDEOS:
                    pictureList = GUIControllerVillage.getInstance().getSelectedPoint().getVideos();
                    break;
            }
        }
        return pictureList;
    }

    private Image createImage(final File imageFile) {

        try {
            FileInputStream f = new FileInputStream(imageFile);
            Image image = new Image(f, 75, 75, false, true);
            f.close();
            return image;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private AnchorPane createAnchorPane(File file) {

        AnchorPane ap = new AnchorPane();
        ap.prefWidth(400.0);
        ap.prefHeight(150.0);
        ap.setId(file.getName());

        ImageView i = new ImageView(createImage(file));
        i.setFitHeight(100.0);
        i.setFitWidth(100.0);
        i.setLayoutX(132.0);
        i.setLayoutY(29.0);

        CheckBox cb = new CheckBox();
        cb.setLayoutX(42.0);
        cb.setLayoutY(70.0);
        cb.setId(file.getName());
        picturesCheckBoxList.add(cb);

        Label l = new Label(file.getName());
        l.setLayoutX(249.0);
        l.setLayoutY(71.0);

        test.add(new FilePath(file.getName(), file.getPath()));
        ap.getChildren().addAll(i, cb, l);

        return ap;
    }

    @FXML
    public void deleteImages() {
        System.out.println("del");

        ArrayList<Integer> indexToDel = new ArrayList<>();

        for (int i = 0; i < picturesCheckBoxList.size(); i++) {
            if (picturesCheckBoxList.get(i).isSelected()) {
                indexToDel.add(i);
            }
        }

        for (int i = indexToDel.size(); i > 0; i--) {
            int index = indexToDel.get(i - 1);
            pictureContentContainer.remove(index);
            verticalContentDisplay.getChildren().remove(index);
            removeImagePos(index);
            picturesCheckBoxList.remove(index);
        }
    }

    // TODO: 13/04/2016 bug quand on ajoute puis on supprime derriere
    // TODO: 13/04/2016 aussi quand on supprime directement, il reste une trace fantome quelque part
    private void removeImagePos(int i) {

        if (visitType == VisitType.CHATEAU) {
            switch (pictureFormType) {
                case OVERVIEW:
                    GUIControllerChateau.getInstance().getSelectedVisit().getOverview().removeImagesContent(test.get(i).path, test.get(i).name);
                    break;
                case INFO:
                    GUIControllerChateau.getInstance().getSelectedVisit().getInfo().removePhotos(test.get(i).path, test.get(i).name);
                    break;
                case DESCRIPTIVE_PICTURE:
                    GUIControllerChateau.getInstance().getSelectedPoint().removePicture(test.get(i).path, test.get(i).name);
                    break;
                case INDOORS_PICTURES:
                    GUIControllerChateau.getInstance().getSelectedPoint().removeInterieur(test.get(i).path, test.get(i).name);
                    break;
                case PANORAMIC_PICTURES:
                    GUIControllerChateau.getInstance().getSelectedPoint().remove360(test.get(i).path, test.get(i).name);
                    break;
                case PICTURES:
                    GUIControllerChateau.getInstance().getSelectedPoint().removePhotos(test.get(i).path, test.get(i).name);
                    break;
                case VIDEOS:
                    GUIControllerChateau.getInstance().getSelectedPoint().removeVideo(test.get(i).path, test.get(i).name);
                    break;
            }
        } else if (visitType == VisitType.VILLAGE) {
            switch (pictureFormType) {
                case OVERVIEW:
                    GUIControllerVillage.getInstance().getSelectedVisit().getOverview().removeImagesContent(test.get(i).path, test.get(i).name);
                    break;
                case INFO:
                    GUIControllerVillage.getInstance().getSelectedVisit().getInfo().removePhotos(test.get(i).path, test.get(i).name);
                    break;
                case DESCRIPTIVE_PICTURE:
                    GUIControllerVillage.getInstance().getSelectedPoint().removePicture(test.get(i).path, test.get(i).name);
                    break;
                case INDOORS_PICTURES:
                    GUIControllerVillage.getInstance().getSelectedPoint().removeInterieur(test.get(i).path, test.get(i).name);
                    break;
                case PANORAMIC_PICTURES:
                    GUIControllerVillage.getInstance().getSelectedPoint().remove360(test.get(i).path, test.get(i).name);
                    break;
                case PICTURES:
                    GUIControllerVillage.getInstance().getSelectedPoint().removePhotos(test.get(i).path, test.get(i).name);
                    break;
                case VIDEOS:
                    GUIControllerVillage.getInstance().getSelectedPoint().removeVideo(test.get(i).path, test.get(i).name);
                    break;
            }
        }
        test.remove(i);
    }

    //// TODO: 06/04/2016 faire l'ajout d'images dans le workspace
    @FXML
    public void addImage() {
        /*
        System.out.println(GUIControllerChateau.getInstance().getSelectedVisit().getOverview().getImagesContent().size());
        ArrayList<File> f = FileTools.MultipleFileChooser(FileTools.IMAGES_FILE_FILTER);
        for (int i = 0; i < f.size(); i++) {
            //GUIControllerChateau.getInstance().getSelectedVisit().getOverview().addImagesContent()

        }*/


        ArrayList<File> selectedMedias = new ArrayList<>();
        String visitPath;


        if (visitType == VisitType.CHATEAU) {
            switch (pictureFormType) {
                case OVERVIEW:

                    selectedMedias = FileTools.MultipleFileChooser(FileTools.IMAGES_FILE_FILTER);
                    visitPath = FileManager.WORKSPACE + "/" + FileManager.CHATEAU + "/" + GUIControllerChateau.getInstance().getSelectedVisit().getName() + "/" + FileManager.OVERVIEW_FOLDER;

                    if (selectedMedias.size() > 3) {
                        // TODO: 13/04/2016 popup seulement 3 a selectionner
                    }
                    else {
                        for (int i = 0; i < selectedMedias.size(); i++) {
                            GUIControllerChateau.getInstance().getSelectedVisit().getOverview().addImagesContent(selectedMedias.get(i).getAbsolutePath(), visitPath, selectedMedias.get(i).getName());
                        }
                    }

                    break;
                case INFO:

                    selectedMedias = FileTools.MultipleFileChooser(FileTools.IMAGES_FILE_FILTER);
                    visitPath = FileManager.WORKSPACE + "/" + FileManager.CHATEAU + "/" + GUIControllerChateau.getInstance().getSelectedVisit().getName() + "/" + FileManager.INFO_FOLDER + "/" + FileManager.PHOTOS;


                    for (int i = 0; i < selectedMedias.size(); i++) {
                        GUIControllerChateau.getInstance().getSelectedVisit().getOverview().addImagesContent(selectedMedias.get(i).getAbsolutePath(), visitPath, selectedMedias.get(i).getName());
                    }


                    break;
                case DESCRIPTIVE_PICTURE:

                    /*
                    File f = FileTools.FileChooser(FileTools.IMAGES_FILE_FILTER);
                    visitPath = FileManager.WORKSPACE + "/" + FileManager.CHATEAU + "/" + GUIControllerChateau.getInstance().getSelectedVisit().getName() + "/" + FileManager.INFO_FOLDER + "/" + FileManager.PHOTOS;
                    GUIControllerChateau.getInstance().getSelectedVisit().getOverview().addImagesContent(selectedMedias.get(i).getAbsolutePath(), visitPath, selectedMedias.get(i).getName());
                    */

                    break;
                case INDOORS_PICTURES:


                    selectedMedias = FileTools.MultipleFileChooser(FileTools.IMAGES_FILE_FILTER);
                    visitPath = FileManager.WORKSPACE + "/" + FileManager.CHATEAU + "/" + GUIControllerChateau.getInstance().getSelectedPoint().getName() + "/" + FileManager.INTERIEUR;

                    for (int i = 0; i < selectedMedias.size(); i++) {
                        GUIControllerChateau.getInstance().getSelectedVisit().getOverview().addImagesContent(selectedMedias.get(i).getAbsolutePath(), visitPath, selectedMedias.get(i).getName());
                    }

                    break;
                case PANORAMIC_PICTURES:


                    selectedMedias = FileTools.MultipleFileChooser(FileTools.IMAGES_FILE_FILTER);
                    visitPath = FileManager.WORKSPACE + "/" + FileManager.CHATEAU + "/" + GUIControllerChateau.getInstance().getSelectedPoint().getName() + "/" + FileManager._360;

                    for (int i = 0; i < selectedMedias.size(); i++) {
                        GUIControllerChateau.getInstance().getSelectedVisit().getOverview().addImagesContent(selectedMedias.get(i).getAbsolutePath(), visitPath, selectedMedias.get(i).getName());
                    }
                    break;
                case PICTURES:


                    selectedMedias = FileTools.MultipleFileChooser(FileTools.IMAGES_FILE_FILTER);
                    visitPath = FileManager.WORKSPACE + "/" + FileManager.CHATEAU + "/" + GUIControllerChateau.getInstance().getSelectedPoint().getName() + "/" + FileManager.PHOTOS;

                    for (int i = 0; i < selectedMedias.size(); i++) {
                        GUIControllerChateau.getInstance().getSelectedVisit().getOverview().addImagesContent(selectedMedias.get(i).getAbsolutePath(), visitPath, selectedMedias.get(i).getName());
                    }
                    break;
                case VIDEOS:


                    selectedMedias = FileTools.MultipleFileChooser(FileTools.VIDEOS_FILE_FILTER);
                    visitPath = FileManager.WORKSPACE + "/" + FileManager.CHATEAU + "/" + GUIControllerChateau.getInstance().getSelectedPoint().getName() + "/" + FileManager.VIDEOS;

                    for (int i = 0; i < selectedMedias.size(); i++) {
                        GUIControllerChateau.getInstance().getSelectedVisit().getOverview().addImagesContent(selectedMedias.get(i).getAbsolutePath(), visitPath, selectedMedias.get(i).getName());
                    }
                    break;
            }
        } else if (visitType == VisitType.VILLAGE) {
            switch (pictureFormType) {
                case OVERVIEW:

                    selectedMedias = FileTools.MultipleFileChooser(FileTools.IMAGES_FILE_FILTER);
                    visitPath = FileManager.WORKSPACE + "/" + FileManager.CHATEAU + "/" + GUIControllerVillage.getInstance().getSelectedVisit().getName() + "/" + FileManager.OVERVIEW_FOLDER;

                    if (selectedMedias.size() > 3) {
                        // TODO: 13/04/2016 popup seulement 3 a selectionner
                    }
                    else {
                        for (int i = 0; i < selectedMedias.size(); i++) {
                            GUIControllerVillage.getInstance().getSelectedVisit().getOverview().addImagesContent(selectedMedias.get(i).getAbsolutePath(), visitPath, selectedMedias.get(i).getName());
                        }
                    }

                    break;
                case INFO:

                    selectedMedias = FileTools.MultipleFileChooser(FileTools.IMAGES_FILE_FILTER);
                    visitPath = FileManager.WORKSPACE + "/" + FileManager.CHATEAU + "/" + GUIControllerVillage.getInstance().getSelectedVisit().getName() + "/" + FileManager.INFO_FOLDER + "/" + FileManager.PHOTOS;


                    for (int i = 0; i < selectedMedias.size(); i++) {
                        GUIControllerVillage.getInstance().getSelectedVisit().getOverview().addImagesContent(selectedMedias.get(i).getAbsolutePath(), visitPath, selectedMedias.get(i).getName());
                    }


                    break;
                case DESCRIPTIVE_PICTURE:

                    /*
                    File f = FileTools.FileChooser(FileTools.IMAGES_FILE_FILTER);
                    visitPath = FileManager.WORKSPACE + "/" + FileManager.CHATEAU + "/" + GUIControllerVillage.getInstance().getSelectedVisit().getName() + "/" + FileManager.INFO_FOLDER + "/" + FileManager.PHOTOS;
                    GUIControllerVillage.getInstance().getSelectedVisit().getOverview().addImagesContent(selectedMedias.get(i).getAbsolutePath(), visitPath, selectedMedias.get(i).getName());
                    */

                    break;
                case INDOORS_PICTURES:


                    selectedMedias = FileTools.MultipleFileChooser(FileTools.IMAGES_FILE_FILTER);
                    visitPath = FileManager.WORKSPACE + "/" + FileManager.CHATEAU + "/" + GUIControllerVillage.getInstance().getSelectedPoint().getName() + "/" + FileManager.INTERIEUR;

                    for (int i = 0; i < selectedMedias.size(); i++) {
                        GUIControllerVillage.getInstance().getSelectedVisit().getOverview().addImagesContent(selectedMedias.get(i).getAbsolutePath(), visitPath, selectedMedias.get(i).getName());
                    }

                    break;
                case PANORAMIC_PICTURES:


                    selectedMedias = FileTools.MultipleFileChooser(FileTools.IMAGES_FILE_FILTER);
                    visitPath = FileManager.WORKSPACE + "/" + FileManager.CHATEAU + "/" + GUIControllerVillage.getInstance().getSelectedPoint().getName() + "/" + FileManager._360;

                    for (int i = 0; i < selectedMedias.size(); i++) {
                        GUIControllerVillage.getInstance().getSelectedVisit().getOverview().addImagesContent(selectedMedias.get(i).getAbsolutePath(), visitPath, selectedMedias.get(i).getName());
                    }
                    break;
                case PICTURES:


                    selectedMedias = FileTools.MultipleFileChooser(FileTools.IMAGES_FILE_FILTER);
                    visitPath = FileManager.WORKSPACE + "/" + FileManager.CHATEAU + "/" + GUIControllerVillage.getInstance().getSelectedPoint().getName() + "/" + FileManager.PHOTOS;

                    for (int i = 0; i < selectedMedias.size(); i++) {
                        GUIControllerVillage.getInstance().getSelectedVisit().getOverview().addImagesContent(selectedMedias.get(i).getAbsolutePath(), visitPath, selectedMedias.get(i).getName());
                    }
                    break;
                case VIDEOS:


                    selectedMedias = FileTools.MultipleFileChooser(FileTools.VIDEOS_FILE_FILTER);
                    visitPath = FileManager.WORKSPACE + "/" + FileManager.CHATEAU + "/" + GUIControllerVillage.getInstance().getSelectedPoint().getName() + "/" + FileManager.VIDEOS;

                    for (int i = 0; i < selectedMedias.size(); i++) {
                        GUIControllerVillage.getInstance().getSelectedVisit().getOverview().addImagesContent(selectedMedias.get(i).getAbsolutePath(), visitPath, selectedMedias.get(i).getName());
                    }
                    break;
            }
        }

        pictureForm.setCenter(loadContent());

    }


}
