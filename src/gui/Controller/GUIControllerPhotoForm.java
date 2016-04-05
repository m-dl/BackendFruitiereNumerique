package gui.Controller;

import gui.Controller.chateau.GUIControllerChateau;
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

public class GUIControllerPhotoForm {

    // TODO: 06/04/2016  Beaucoup de noms de variables qui correspondent pas, changer pour des noms plus explicites

    private static GUIControllerPhotoForm INSTANCE = new GUIControllerPhotoForm();
    // TODO: 06/04/2016  changer les noms ici
    ObservableList<CheckBox> tt = FXCollections.observableArrayList();
    ObservableList<AnchorPane> acc = FXCollections.observableArrayList();
    VBox box;

    private GUIControllerPhotoForm() {
    }

    public static GUIControllerPhotoForm getInstance() {
        return INSTANCE;
    }

    public void displayForm() {

        try {

            BorderPane root = (BorderPane) GUIUtilities.loadLayout("view/photos/photoFormView.fxml", this);

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            //stage.setOnCloseRequest(closeEvent -> GUIFormsController.getInstance().closeStage());

            root.setCenter(loadContent());

            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public ScrollPane loadContent() {
        acc.clear();
        tt.clear();

        box = new VBox();
        box.setPadding(new Insets(1, 1, 1, 1));

        for (final File file : GUIControllerChateau.getInstance().getSelectedVisit().getOverview().getImagesContent()) {
            acc.add(createAnchorPane(file));
        }

        box.getChildren().addAll(acc);
        ScrollPane sc = new ScrollPane();
        sc.setContent(box);
        return sc;
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
        tt.add(cb);

        Label l = new Label(file.getName());
        l.setLayoutX(249.0);
        l.setLayoutY(71.0);

        ap.getChildren().addAll(i, cb, l);

        return ap;
    }

    @FXML
    public void deleteImages() {
        System.out.println("del");

        for (int i = 0; i < tt.size(); i++) {
            if (tt.get(i).isSelected()) {
                acc.remove(i);
                box.getChildren().remove(i);
                //// TODO: 06/04/2016  chemin en dur: mettre le chemin selon l'image sélectionnée
                GUIControllerChateau.getInstance().getSelectedVisit().getOverview().removeImagesContent("medias/chateau/visite-bosco/visite-overview", tt.get(i).getId());
                tt.remove(i);
            }

        }

    }

    //// TODO: 06/04/2016 faire l'ajout d'images dans le workspace
    @FXML
    public void addImage() {
        System.out.println(GUIControllerChateau.getInstance().getSelectedVisit().getOverview().getImagesContent().size());

    }


}
