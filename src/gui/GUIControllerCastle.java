package gui;

import entities.InterestPoint;
import entities.Visit;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.cell.TextFieldListCell;
import javafx.scene.input.MouseEvent;
import javafx.util.StringConverter;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class GUIControllerCastle implements Initializable{

    private GUIWindow guiWindow;
    public ListView<Visit> visitListView;
    public ListView<InterestPoint> iPListView;
    public ListView<String> infoListView;
    public ObservableList<Visit> visitList;
    public ObservableList<InterestPoint> iPList;
    public ObservableList<String> infoList;

    public GUIControllerCastle(GUIWindow guiWindow) {
        this.guiWindow = guiWindow;
        visitList = FXCollections.observableArrayList();
        iPList = FXCollections.observableArrayList();
        infoList = FXCollections.observableArrayList();
    }


    @FXML
    void editVisit() {
        System.out.println("edit visit : "+visitListView.getSelectionModel().getSelectedItem());
    }

    @FXML
    void editIP() {
        System.out.println("edit IP : "+ iPListView.getSelectionModel().getSelectedItem());
    }

    // le contenu du poi
    void editContent() {

    }

    @FXML
    void uploadMedia() {
        System.out.println("uploadMedia");
    }

    @FXML
    void downloadMedia() {
        System.out.println("downloadMedia");
    }

    @FXML
    void delOld() {
        System.out.println("delOld");
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        visitListView.setItems(visitList);
        iPListView.setItems(iPList);
        infoListView.setItems(infoList);

        setFactories();
        setListeners();
    }

    public void loadCastleData(ArrayList<Visit> visits) {
        for (int i = 0; i < visits.size(); i++) {
            this.visitList.add(visits.get(i));
        }
    }

    public void loadIPData(Visit visit) {

        for (int i = 0; i < visit.getIP().size(); i++) {
            this.iPList.add(visit.getIP().get(i));
        }
    }

    public void loadInfoData(InterestPoint ip) {

        for (int i = 0; i < ip.getPhotos().size(); i++) {
            this.infoList.add(ip.getPhotos().get(i).getName());
        }
    }

    public void setFactories() {

        visitListView.setCellFactory(lv -> {

            StringConverter<Visit> converter = new StringConverter<Visit>() {
                @Override
                public String toString(Visit object) {
                    return object.getName();
                }

                @Override
                public Visit fromString(String string) {
                    return null;
                }
            };

            ListCell<Visit> cell = new TextFieldListCell<>(converter);

            return cell;
        });

        iPListView.setCellFactory(lv -> {

            StringConverter<InterestPoint> converter = new StringConverter<InterestPoint>() {
                @Override
                public String toString(InterestPoint object) {
                    return object.getName();
                }

                @Override
                public InterestPoint fromString(String string) {
                    return null;
                }
            };

            ListCell<InterestPoint> cell = new TextFieldListCell<>(converter);

            return cell;
        });
    }

    public void setListeners() {

        visitListView.setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {
                iPList.clear();
                infoList.clear();
                loadIPData(visitListView.getSelectionModel().getSelectedItem());
            }
        });

        iPListView.setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {
                infoList.clear();
                loadInfoData(iPListView.getSelectionModel().getSelectedItem());
            }
        });

    }

}
