package gui.Controller.photo;

import files.FileManager;
import gui.Controller.GUIFormsController;
import gui.Controller.chateau.GUIControllerChateau;
import gui.Controller.chateau.GUIControllerChateauIPForm;
import gui.GUIUtilities;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ToolBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import java.io.IOException;

import static javafx.scene.paint.Color.BLACK;
import static javafx.scene.paint.Color.RED;

public class GUIControllerPointPlacer {

    private static GUIControllerPointPlacer INSTANCE = new GUIControllerPointPlacer();
    public static GUIControllerPointPlacer getInstance() {
        return INSTANCE;
    }
    private GUIControllerPointPlacer() {}

    private Stage displayedWindow;
    public ImageView displayedImage;
    public Pane imagePanel;
    private Circle marker;

    private int currentFloor;
    private double coordX, coordY;


    public void displayModCoord(double x, double y) {
        coordX = x;
        coordY = y;
        marker = createCircle();
    }

    public void display() {
        try {

            BorderPane root = (BorderPane) GUIUtilities.loadLayout("view/photos/pointPlacerView.fxml", this);

            displayedWindow = new Stage();
            displayedWindow.setTitle("Choix des coordonnées");

            initContent();

            displayedWindow.setScene(new Scene(root));
            displayedWindow.sizeToScene();
            displayedWindow.show();

            displayedWindow.setOnCloseRequest(closeEvent
                    -> GUIControllerChateauIPForm.getInstance().setCoords(calcX(),calcY()));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initContent() {
        currentFloor = 0;
        loadFloor();
    }

    private double calcX() {
        if (displayedImage.getImage().getWidth() != 0)
            return coordX/displayedImage.getImage().getWidth();

        return 0;
    }
    private double calcY() {
        if (displayedImage.getImage().getHeight() != 0)
            return coordY/displayedImage.getImage().getHeight();

        return 0;

    }
    private void loadFloor() {
        switch (currentFloor) {
            case 0:
                displayedImage.setImage(new Image("plans.JPG"));
                break;
            case 1:
                displayedImage.setImage(new Image("0_1.jpg"));
                break;
            case 2:
                displayedImage.setImage(new Image("1_0.jpg"));
                break;
            case 3:
                displayedImage.setImage(new Image("1_1.jpg"));
                break;
        }
        displayedWindow.setWidth(displayedImage.getImage().getWidth() > 300
                ? displayedImage.getImage().getWidth()
                : 300 );
        displayedWindow.setHeight(displayedImage.getImage().getHeight());

        addListeners();
    }

    public void increaseFloor() {
        if (currentFloor + 1 <= 3) {
            currentFloor++;
            loadFloor();
        }
    }

    public void decreaseFloor() {
        if (currentFloor - 1 >= 0) {
            currentFloor--;
            loadFloor();
        }
    }

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

    public void displayContent()
    {
        imagePanel.getChildren().clear();
        imagePanel.getChildren().add(displayedImage);
        imagePanel.getChildren().add(marker);
    }

}


