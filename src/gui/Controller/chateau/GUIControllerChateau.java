package gui.Controller.chateau;

import entities.InterestPoint;
import entities.Visit;
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

    public ListView<Visit> visitListViewC;
    public ListView<InterestPoint> iPListViewC;
    public ObservableList<Visit> visitListC;
    public ObservableList<InterestPoint> iPListC;

    public GUIControllerChateau(GUIWindow guiWindow) {
        this.guiWindow = guiWindow;
        this.guiForms = new GUIFormsController();
        visitListC = FXCollections.observableArrayList();
        iPListC = FXCollections.observableArrayList();
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
        System.out.println("add pic");
    }

    @FXML
    void editIPC() {
        //System.out.println("edit IP : "+ iPListView.getSelectionModel().getSelectedItem());
    }

    @FXML
    void deleteVisitC() {
        System.out.println("edit visit : "+visitListViewC.getSelectionModel().getSelectedItem());
        //guiWindow.displayChateauForm(false);
    }

    @FXML
    void deleteIPC() {
        System.out.println("edit visit : "+visitListViewC.getSelectionModel().getSelectedItem());
       // guiWindow.displayChateauForm(false);
    }

    public Visit getSelectedVisit() {
        return visitListViewC.getSelectionModel().getSelectedItem();
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
