package gui.Controller.village;

import entities.village.InterestPoint;
import entities.village.Visit;
import gui.Controller.GUIFormsController;
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

public class GUIControllerVillage implements Initializable{

    private static GUIControllerVillage INSTANCE = new GUIControllerVillage();
    public ListView<Visit> visitListViewV;
    public ListView<InterestPoint> iPListViewV;
    public ObservableList<Visit> visitListV;
    public ObservableList<InterestPoint> iPListV;
    private GUIFormsController guiForms;


    private GUIControllerVillage()
    {}

    public static GUIControllerVillage getInstance() {
        return INSTANCE;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        this.guiForms = GUIFormsController.getInstance();
        visitListV = FXCollections.observableArrayList();
        iPListV = FXCollections.observableArrayList();

        visitListViewV.setItems(visitListV);
        iPListViewV.setItems(iPListV);

        setFactories();
        setListeners();

    }

    public void setFactories() {

        visitListViewV.setCellFactory(lv -> {

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

        iPListViewV.setCellFactory(lv -> {

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

        visitListViewV.setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {
                iPListV.clear();
                loadIPData(visitListViewV.getSelectionModel().getSelectedItem());
            }
        });
    }


    @FXML
    void addVisitV() {
        System.out.println("add visit");
        guiForms.displayVillageVisitForm(true,this.getSelectedVisit());
    }

    @FXML
    void editVisitV() {
        System.out.println("edit visit : "+ visitListViewV.getSelectionModel().getSelectedItem());
        guiForms.displayVillageVisitForm(false,this.getSelectedVisit());
    }

    @FXML
    void addPIV() {
        if(getSelectedVisit() != null) {
            System.out.println("edit visit : " + iPListViewV.getSelectionModel().getSelectedItem());
            guiForms.displayVillageIPForm(true, this.getSelectedPoint());
        }
        else {
            System.out.println("choisissez une visite");
        }
    }

    @FXML
    void editIPV() {
        if(getSelectedVisit() != null) {
            System.out.println("edit visit : " + iPListViewV.getSelectionModel().getSelectedItem());
            guiForms.displayVillageIPForm(false, this.getSelectedPoint());
        }
        else {
            System.out.println("choisissez une visite");
        }
    }

    @FXML
    void deleteVisitV() {
        System.out.println("del visit : "+ visitListViewV.getSelectionModel().getSelectedItem());
        //guiWindow.displayChateauForm(false);
    }

    @FXML
    void deleteIPV() {
        System.out.println("del point : "+ iPListViewV.getSelectionModel().getSelectedItem());
       // guiWindow.displayChateauForm(false);
    }

    public Visit getSelectedVisit() {
        return visitListViewV.getSelectionModel().getSelectedItem();
    }

    public InterestPoint getSelectedPoint() {
        return iPListViewV.getSelectionModel().getSelectedItem();
    }

    public void loadVisitData(ArrayList<Visit> visits) {
        for (int i = 0; i < visits.size(); i++) {
            this.visitListV.add(visits.get(i));
        }
    }

    public void loadIPData(Visit visit) {

        for (int i = 0; i < visit.getIP().size(); i++) {
            this.iPListV.add(visit.getIP().get(i));
        }
    }

}
