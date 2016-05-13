package gui.Controller.village;

import entities.village.InterestPoint;
import entities.village.Visit;
import files.FileManager;
import gui.Controller.GUIFormsController;
import gui.GUIWindow;
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

    private GUIWindow guiWindow;
    private GUIFormsController guiForms;

    private static GUIControllerVillage INSTANCE = new GUIControllerVillage();
    public ListView<Visit> visitListViewV;
    public ListView<InterestPoint> iPListViewV;
    public ObservableList<Visit> visitListV;
    public ObservableList<InterestPoint> iPListV;


    private GUIControllerVillage() {
        this.guiWindow = GUIWindow.getInstance();
        this.guiForms = GUIFormsController.getInstance();
        visitListV = FXCollections.observableArrayList();
        iPListV = FXCollections.observableArrayList();
    }

    public static GUIControllerVillage getInstance() {
        return INSTANCE;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
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
        guiForms.displayVillageVisitForm(true, this.getSelectedVisit());
    }

    @FXML
    void editVisitV() {
        System.out.println("edit visit : "+ visitListViewV.getSelectionModel().getSelectedItem());
        if(this.getSelectedVisit() != null ) {
            guiForms.displayVillageVisitForm(false, this.getSelectedVisit());
        }
        else {
            String header = "Aucune visite sélectionnée";
            String error = "Pour modifier une visite, choisissez-en une dans la liste";
            guiForms.displayWarningAlert(header,error).showAndWait();
        }
    }

    @FXML
    void addPIV() {
        if(getSelectedVisit() != null) {
            System.out.println("add poi : " + iPListViewV.getSelectionModel().getSelectedItem());
            guiForms.displayVillageIPForm(true, this.getSelectedPoint());
        }
        else {
            String header = "Aucune visite sélectionnée";
            String error = "Pour ajouter un point d'intérêt, choisissez la visite dans laquelle vous voulez l'ajouter d'abord";
            guiForms.displayWarningAlert(header,error).showAndWait();
        }
    }

    @FXML
    void editIPV() {
        if(getSelectedVisit() != null) {
            System.out.println("edit visit : " + iPListViewV.getSelectionModel().getSelectedItem());
            guiForms.displayVillageIPForm(false, this.getSelectedPoint());
        }
        else {
            String header = "Aucune visite sélectionnée";
            String error = "Pour ajouter un point d'intérêt, choisissez la visite dans laquelle vous voulez l'ajouter d'abord";
            guiForms.displayWarningAlert(header,error).showAndWait();
        }
    }

    @FXML
    void deleteVisitV() {
        if(getSelectedVisit() != null) {
            System.out.println("del visit : " + visitListViewV.getSelectionModel().getSelectedItem());
            String path = FileManager.getInstance().WORKSPACE + "/" + FileManager.getInstance().VILLAGE + "/" + visitListViewV.getSelectionModel().getSelectedItem().getName();
            guiWindow.FM.getVillageWorkspace().deleteVisit(visitListViewV.getSelectionModel().getSelectedItem(), path);
            visitListV.remove(visitListViewV.getSelectionModel().getSelectedItem());
            visitListViewV.getParent().requestFocus();
        }
        else {
            String header = "Aucune visite sélectionnée";
            String error = "Pour supprimer une visite, choisissez-en une dans la liste";
            guiForms.displayWarningAlert(header,error).showAndWait();
        }
    }

    @FXML
    void deleteIPV() {
        if(getSelectedPoint() != null) {
            System.out.println("del point : " + iPListViewV.getSelectionModel().getSelectedItem());
            String path = FileManager.getInstance().WORKSPACE + "/" + FileManager.getInstance().VILLAGE + "/" + visitListViewV.getSelectionModel().getSelectedItem().getName() + "/" + iPListViewV.getSelectionModel().getSelectedItem().getName();
            ArrayList<Visit> visits = FileManager.getInstance().getVillageWorkspace().getV();
            visits.get(visits.indexOf(visitListViewV.getSelectionModel().getSelectedItem())).deleteInterestPoint(iPListViewV.getSelectionModel().getSelectedItem(), path);
            iPListV.remove(iPListViewV.getSelectionModel().getSelectedItem());
            iPListViewV.getParent().requestFocus();
        }
        else {
            String header = "Aucun point sélectionné";
            String error = "Pour supprimer une point d'intérêt, choisissez-en un dans la liste";
            guiForms.displayWarningAlert(header,error).showAndWait();
        }
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

    public void reloadVillageData() {

        visitListV.clear();
        iPListV.clear();
        ArrayList<Visit> visits = FileManager.getInstance().getVillageWorkspace().getV();

        for (int i = 0; i < visits.size(); i++) {
            this.visitListV.add(visits.get(i));

        }

        visitListViewV.setItems(visitListV);
        iPListViewV.setItems(iPListV);
    }

}
