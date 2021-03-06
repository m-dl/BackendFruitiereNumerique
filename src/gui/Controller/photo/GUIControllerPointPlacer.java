package gui.Controller.photo;

import gui.Controller.GUIFormsController;
import gui.Controller.chateau.GUIControllerChateauIPForm;
import gui.GUIUtilities;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

import static javafx.scene.paint.Color.BLACK;
import static javafx.scene.paint.Color.RED;

/**
 * Classe pour la fenêtre de placement du point château
 */
public class GUIControllerPointPlacer {

    private static GUIControllerPointPlacer INSTANCE = new GUIControllerPointPlacer();
    public ImageView displayedImage;
    public Pane imagePanel;
    private Stage displayedWindow;
    private Circle marker;
    private int currentFloor;
    private double coordX, coordY;

    private GUIControllerPointPlacer() {
    }

    public static GUIControllerPointPlacer getInstance() {
        return INSTANCE;
    }

    /**
     * Affichage de la fenêtre
     *
     * @param isNewPoint si c'est un point créé ou modifié
     */
    public void display(boolean isNewPoint) {

        try {

            BorderPane root = (BorderPane) GUIUtilities.loadLayout("view/photos/pointPlacerView.fxml", this);

            displayedWindow = new Stage();
            displayedWindow.setTitle("Choix des coordonnées");

            if (isNewPoint) {
                initContent();
            }
            else {
                loadContent();
            }

            displayedWindow.setScene(new Scene(root));
            displayedWindow.sizeToScene();
            displayedWindow.show();

            displayedWindow.setOnCloseRequest(closeEvent
                    -> GUIControllerChateauIPForm.getInstance().setCoords(currentFloor,calcX(),calcY()));

        } catch (IOException e) {
            e.printStackTrace();
            GUIFormsController.getInstance().displayExceptionAlert(e,"Erreur lors de l'affichage de la fenêtre pour le placement du point").showAndWait();

        }
    }

    /**
     * Charge le contenu de la fenêtre
     */
    private void loadContent() {
        currentFloor = GUIControllerChateauIPForm.getInstance().getFloor();
        loadFloor();

        coordX = GUIControllerChateauIPForm.getInstance().getCoordX()*displayedImage.getFitWidth();
        coordY = GUIControllerChateauIPForm.getInstance().getCoordY()*displayedImage.getFitHeight();
        marker = createCircle();
        displayContent();
    }

    /**
     * Initialise au premier étage
     */
    private void initContent() {
        currentFloor = 0;
        loadFloor();
    }

    /**
     * Calcule la coordonnée X par rapport à l'image cliquée
     * @return la coordonnée X
     */
    private double calcX() {
        if (displayedImage.getFitWidth() != 0)
            return coordX/displayedImage.getFitWidth();

        return 0;
    }

    /**
     * Calcule la coordonnée Y par rapport à l'image cliquée
     * @return la coordonnée Y
     */
    private double calcY() {
        if (displayedImage.getFitHeight() != 0)
            return coordY/displayedImage.getFitHeight();

        return 0;

    }

    /**
     * Charge les image des étages
     * Modifier ici pour ajouter de nouveaux étages
     */
    private void loadFloor() {
        File f;
        switch (currentFloor) {
            case 0:
                f = new File("res/etage0.jpg");

                if (f.exists() && !f.isDirectory())
                    displayedImage.setImage(new Image(new File("res/etage0.jpg").toURI().toString()));
                else {
                    System.out.println("pas de fichier, chargement par défaut");
                    displayedImage.setImage(new Image(new File("../../../etage0.jpg").toURI().toString()));
                }
                break;
            case 1:
                f = new File("res/etage1.jpg");

                if (f.exists() && !f.isDirectory())
                    displayedImage.setImage(new Image(new File("res/etage1.jpg").toURI().toString()));
                else {
                    System.out.println("aps de fichier charge par défaut");
                    displayedImage.setImage(new Image(new File("etage1.jpg").toURI().toString()));
                }

                break;
            case 2:
                f = new File("res/etage1.jpg");

                if (f.exists() && !f.isDirectory())
                    displayedImage.setImage(new Image(new File("res/etage2.jpg").toURI().toString()));
                else {
                    System.out.println("aps de fichier charge par défaut");
                    displayedImage.setImage(new Image(new File("etage2.jpg").toURI().toString()));
                }

                break;
        }

        displayedImage.setFitWidth(800);
        displayedImage.setFitHeight(600);

        displayedImage.setPreserveRatio(false);
        displayedImage.setSmooth(true);
        displayedImage.setCache(true);
        addListeners();
    }

    /**
     * Changement d'étage, appellée par JavaFX
     */
    public void increaseFloor() {
        if (currentFloor + 1 <= 2) {
            currentFloor++;
            loadFloor();
        }
    }

    /**
     * Changement d'étage, appellée par JavaFX
     */
    public void decreaseFloor() {
        if (currentFloor - 1 >= 0) {
            currentFloor--;
            loadFloor();
        }
    }


    /**
     * Ajout des listener sur les actions utilisateur
     */
    private void addListeners() {
        imagePanel.setOnMousePressed(event -> {
            coordX = event.getX();
            coordY = event.getY();

        });

        imagePanel.setOnMouseReleased(event -> {
            marker = createCircle();
            displayContent();
        });
    }


    /**
     * Création du cercle rouge à afficher
     * @return le cercle
     */
    public Circle createCircle()
    {
        Circle c = new Circle();

        c.setRadius(5);
        c.setCenterX(coordX);
        c.setCenterY(coordY);
        c.setFill(RED);
        c.setStroke(BLACK);

        return c;
    }

    /**
     * Affichage du contenu
     */
    public void displayContent()
    {
        imagePanel.getChildren().clear();
        imagePanel.getChildren().add(displayedImage);
        imagePanel.getChildren().add(marker);
    }

}


