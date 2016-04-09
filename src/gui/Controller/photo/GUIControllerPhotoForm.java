package gui.Controller.photo;

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

public class GUIControllerPhotoForm {


    private static GUIControllerPhotoForm INSTANCE = new GUIControllerPhotoForm();
    private ObservableList<CheckBox> picturesCheckBoxList = FXCollections.observableArrayList();
    private ObservableList<AnchorPane> pictureContentContainer = FXCollections.observableArrayList();
    private VBox verticalContentDisplay;
    private Stage stage;
    private BorderPane pictureForm;

    private VisitType visitType;
    private PictureFormType pictureFormType;

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
        cb.setId(ap.getId());
        cb.setLayoutX(42.0);
        cb.setLayoutY(70.0);
        cb.setId(file.getName());
        picturesCheckBoxList.add(cb);

        Label l = new Label(file.getName());
        l.setLayoutX(249.0);
        l.setLayoutY(71.0);

        ap.getChildren().addAll(i, cb, l);

        return ap;
    }

    @FXML
    public void deleteImages() {
        System.out.println("del");

        for (int i = 0; i < picturesCheckBoxList.size(); i++) {
            if (picturesCheckBoxList.get(i).isSelected()) {
                pictureContentContainer.remove(i);
                verticalContentDisplay.getChildren().remove(i);
                //// TODO: 06/04/2016  chemin en dur: mettre le chemin selon l'image sélectionnée
                GUIControllerChateau.getInstance().getSelectedVisit().getOverview().removeImagesContent("medias/chateau/visite-bosco/visite-overview", picturesCheckBoxList.get(i).getId());
                picturesCheckBoxList.remove(i);
            }

        }

    }

    //// TODO: 06/04/2016 faire l'ajout d'images dans le workspace
    @FXML
    public void addImage() {
        System.out.println(GUIControllerChateau.getInstance().getSelectedVisit().getOverview().getImagesContent().size());

    }


}
