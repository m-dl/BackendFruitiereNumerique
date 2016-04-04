package gui.Controller.chateau;

import entities.InterestPoint;
import entities.Visit;
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

public class GUIControllerChateau implements Initializable{

    private GUIWindow guiWindow;
    private GUIFormsController guiForms;
    private static GUIControllerChateau INSTANCE = new GUIControllerChateau();


    public ListView<Visit> visitListViewC;
    public ListView<InterestPoint> iPListViewC;
    public ObservableList<Visit> visitListC;
    public ObservableList<InterestPoint> iPListC;


    private GUIControllerChateau() {
        this.guiWindow = GUIWindow.getInstance();
        this.guiForms = new GUIFormsController();
        visitListC = FXCollections.observableArrayList();
        iPListC = FXCollections.observableArrayList();
    }

    public static GUIControllerChateau getInstance() {
        return INSTANCE;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {


        visitListViewC.setItems(visitListC);
        iPListViewC.setItems(iPListC);

        setFactories();
        setListeners();

    }

    public void setFactories() {

        visitListViewC.setCellFactory(lv -> {

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

        iPListViewC.setCellFactory(lv -> {

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

        visitListViewC.setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {
                iPListC.clear();
                loadIPData(visitListViewC.getSelectionModel().getSelectedItem());
            }
        });
    }


    @FXML
    void addVisitC() {
        System.out.println("add visit");
        guiForms.displayChateauVisitForm(true,this.getSelectedVisit());
    }

    @FXML
    void editVisitC() {
        System.out.println("edit visit : "+visitListViewC.getSelectionModel().getSelectedItem());
        guiForms.displayChateauVisitForm(false,this.getSelectedVisit());
    }

    @FXML
    void addPIC() {
        if(getSelectedVisit() != null) {
            System.out.println("edit visit : " + iPListViewC.getSelectionModel().getSelectedItem());
            guiForms.displayChateauIPForm(true, this.getSelectedPoint());
        }
        else {
            System.out.println("choisissez une visite");
        }
    }

    @FXML
    void editIPC() {
        if(getSelectedVisit() != null) {
            System.out.println("edit visit : " + iPListViewC.getSelectionModel().getSelectedItem());
            guiForms.displayChateauIPForm(false, this.getSelectedPoint());
        }
        else {
            System.out.println("choisissez une visite");
        }
    }

    @FXML
    void deleteVisitC() {
        String path = FileManager.getInstance().WORKSPACE + "/" + FileManager.getInstance().CHATEAU + "/" + visitListViewC.getSelectionModel().getSelectedItem().getName();
        guiWindow.FM.getChateauWorkspace().deleteVisit(visitListViewC.getSelectionModel().getSelectedItem(),path);
        visitListC.remove(visitListViewC.getSelectionModel().getSelectedItem());
    }

    @FXML
    void deleteIPC() {
        System.out.println("del point : "+iPListViewC.getSelectionModel().getSelectedItem());
        String path = FileManager.getInstance().WORKSPACE + "/" + FileManager.getInstance().CHATEAU + "/" + visitListViewC.getSelectionModel().getSelectedItem().getName() + "/" + iPListViewC.getSelectionModel().getSelectedItem().getName();
        ArrayList<Visit> visits = FileManager.getInstance().getChateauWorkspace().getV();
        visits.get(visits.indexOf(visitListViewC.getSelectionModel().getSelectedItem())).deleteInterestPoint(iPListViewC.getSelectionModel().getSelectedItem(),path);
        iPListC.remove(iPListViewC.getSelectionModel().getSelectedItem());
    }

    public Visit getSelectedVisit() {
        return visitListViewC.getSelectionModel().getSelectedItem();
    }

    public InterestPoint getSelectedPoint() {
        return iPListViewC.getSelectionModel().getSelectedItem();
    }

    public void loadCastleData(ArrayList<Visit> visits) {
        for (int i = 0; i < visits.size(); i++) {
            this.visitListC.add(visits.get(i));
        }
    }

    public void loadIPData(Visit visit) {

        for (int i = 0; i < visit.getIP().size(); i++) {
            this.iPListC.add(visit.getIP().get(i));
        }
    }

}
