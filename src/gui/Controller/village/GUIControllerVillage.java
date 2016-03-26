package gui.Controller.village;

import entities.InterestPoint;
import entities.Visit;
import gui.GUIWindow;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class GUIControllerVillage implements Initializable{

    private GUIWindow guiWindow;
    public ListView<Visit> visitListViewC;
    public ListView<InterestPoint> iPListViewC;
    public ListView<String> infoListView;
    public ObservableList<Visit> visitList;
    public ObservableList<InterestPoint> iPList;
    public ObservableList<String> infoList;

    public GUIControllerVillage(GUIWindow guiWindow) {
        this.guiWindow = guiWindow;
        //visitList = FXCollections.observableArrayList();
       // iPList = FXCollections.observableArrayList();
        //infoList = FXCollections.observableArrayList();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        /*
        visitListView.setItems(visitList);
        iPListView.setItems(iPList);
        infoListView.setItems(infoList);

        setFactories();
        setListeners();
        */
    }
/*
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
    */

    @FXML
    void addVisitV() {
        System.out.println("add visit");
        //guiWindow.displayChateauForm(true);
    }

    @FXML
    void addPIV() {
        System.out.println("add pic");
    }

    @FXML
    void editVisitV() {
       // System.out.println("edit visit : "+visitListView.getSelectionModel().getSelectedItem());
        //guiWindow.displayChateauForm(false);
    }

    @FXML
    void deleteVisitV() {
        //System.out.println("edit visit : "+visitListView.getSelectionModel().getSelectedItem());
        //guiWindow.displayChateauForm(false);
    }

    @FXML
    void deleteIPV() {
        //System.out.println("edit visit : "+visitListView.getSelectionModel().getSelectedItem());
        //guiWindow.displayChateauForm(false);
    }

    @FXML
    void editIPV() {
        //System.out.println("edit IP : "+ iPListView.getSelectionModel().getSelectedItem());
    }


    public void getSelectedVisit() {
        //return visitListView.getSelectionModel().getSelectedItem();
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

}
