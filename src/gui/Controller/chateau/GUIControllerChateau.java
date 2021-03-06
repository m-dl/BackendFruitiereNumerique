package gui.Controller.chateau;

import entities.chateau.InterestPoint;
import entities.chateau.Location;
import entities.chateau.Visit;
import files.FileManager;
import gui.Controller.GUIFormsController;
import gui.GUIWindow;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.cell.TextFieldListCell;
import javafx.util.StringConverter;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * Class GUIControllerChateau se charge de l'onglet chateau du gui
 * Classe Singleton
 */
public class GUIControllerChateau implements Initializable{


    private static GUIControllerChateau INSTANCE = new GUIControllerChateau();
    public ListView<Visit> visitListViewC;
    public ListView<InterestPoint> iPListViewC;
    public ObservableList<Visit> visitListC;
    public ObservableList<InterestPoint> iPListC;
    private GUIWindow guiWindow;
    private GUIFormsController guiForms;


    /**
     * Constructor pour initialiser les listes des visites et points
     */
    private GUIControllerChateau() {
        this.guiWindow = GUIWindow.getInstance();
        this.guiForms = GUIFormsController.getInstance();
        visitListC = FXCollections.observableArrayList();
        iPListC = FXCollections.observableArrayList();
    }

    /**
     * Method getInstance retourne l'instance de la classe
     *
     * @return l'instance de la classe
     */
    public static GUIControllerChateau getInstance() {
        return INSTANCE;
    }

    /**
     * Initialise le contenu des listes aisni que les listeners
     *
     * @param location of type URL
     * @param resources of type ResourceBundle
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        visitListViewC.setItems(visitListC);
        iPListViewC.setItems(iPListC);

        setFactories();
        setListeners();
    }

    /**
     * Method pour afficher les noms correspondant aux visites et points dans les listes.
     */
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

    /**
     * Method pour initialiser les actions à effectuer lors d'un clic sur les items de la liste
     */
    public void setListeners() {

        visitListViewC.setOnMouseClicked(event -> {
            iPListC.clear();
            loadIPData(visitListViewC.getSelectionModel().getSelectedItem());
        });
    }


    /**
     * Method pour l'action d'ajout d'une visite
     */
    @FXML
    void addVisitC() {
        System.out.println("add visit");
        guiForms.displayChateauVisitForm(true, this.getSelectedVisit());

    }

    /**
     * Methode pour la modification d'une visite
     */
    @FXML
    void editVisitC() {
        System.out.println("edit visit : "+visitListViewC.getSelectionModel().getSelectedItem().getName());

        if(this.getSelectedVisit() != null ) {
            guiForms.displayChateauVisitForm(false, this.getSelectedVisit());
        }
        else {
            String header = "Aucune visite sélectionnée";
            String error = "Pour modifier une visite, choisissez-en une dans la liste";
            guiForms.displayWarningAlert(header,error).showAndWait();
        }
    }

    /**
     * Methode pour l'ajout d'un point
     */
    @FXML
    void addPIC() {
        if(getSelectedVisit() != null) {
            System.out.println("add poi : " + iPListViewC.getSelectionModel().getSelectedItem());
            guiForms.displayChateauIPForm(true, this.getSelectedPoint());
        }
        else {
            String header = "Aucune visite sélectionnée";
            String error = "Pour ajouter un point d'intérêt, choisissez la visite dans laquelle vous voulez l'ajouter d'abord";
            guiForms.displayWarningAlert(header,error).showAndWait();
        }
    }

    /**
     * Methode pour modifier un point d'intéret
     */
    @FXML
    void editIPC() {
        if(getSelectedVisit() != null) {
            System.out.println("edit visit : " + iPListViewC.getSelectionModel().getSelectedItem());
            guiForms.displayChateauIPForm(false, this.getSelectedPoint());
        }
        else {
            String header = "Aucune visite sélectionnée";
            String error = "Pour ajouter un point d'intérêt, choisissez la visite dans laquelle vous voulez l'ajouter d'abord";
            guiForms.displayWarningAlert(header,error).showAndWait();
        }
    }

    /**
     * Method pour la suppresion d'une visite
     */
    @FXML
    void deleteVisitC() {
        if(getSelectedVisit() != null) {
            String path = FileManager.WORKSPACE + "/" + FileManager.CHATEAU + "/" + visitListViewC.getSelectionModel().getSelectedItem().getName();
            FileManager.getInstance().getChateauWorkspace().deleteVisit(visitListViewC.getSelectionModel().getSelectedItem(), path);
            visitListC.remove(visitListViewC.getSelectionModel().getSelectedItem());
            iPListViewC.getItems().clear();
            visitListViewC.getParent().requestFocus();
            iPListViewC.getParent().requestFocus();
        }
        else {
            String header = "Aucune visite sélectionnée";
            String error = "Pour supprimer une visite, choisissez-en une dans la liste";
            guiForms.displayWarningAlert(header,error).showAndWait();
        }

    }

    /**
     * Method pour la suppression d'un point
     */
    @FXML
    void deleteIPC() {
        if(getSelectedPoint() != null) {
            System.out.println("del point : " + iPListViewC.getSelectionModel().getSelectedItem());
            String path = FileManager.WORKSPACE + "/" + FileManager.CHATEAU + "/" + visitListViewC.getSelectionModel().getSelectedItem().getName() + "/" + iPListViewC.getSelectionModel().getSelectedItem().getName();
            ArrayList<Visit> visits = FileManager.getInstance().getChateauWorkspace().getV();

            if (getSelectedPoint().getFloor() == Location.FLOOR_ONE) {
                visits.get(visits.indexOf(visitListViewC.getSelectionModel().getSelectedItem())).deleteInterestPoint(iPListViewC.getSelectionModel().getSelectedItem(), path
                        , visits.get(visits.indexOf(visitListViewC.getSelectionModel().getSelectedItem())).getIP1());
            }
            else if(getSelectedPoint().getFloor() == Location.FLOOR_TWO) {
                visits.get(visits.indexOf(visitListViewC.getSelectionModel().getSelectedItem())).deleteInterestPoint(iPListViewC.getSelectionModel().getSelectedItem(), path
                        , visits.get(visits.indexOf(visitListViewC.getSelectionModel().getSelectedItem())).getIP2());
            }
            else if (getSelectedPoint().getFloor() == Location.FLOOR_THREE) {
                visits.get(visits.indexOf(visitListViewC.getSelectionModel().getSelectedItem())).deleteInterestPoint(iPListViewC.getSelectionModel().getSelectedItem(), path
                        , visits.get(visits.indexOf(visitListViewC.getSelectionModel().getSelectedItem())).getIP3());
            }
            iPListC.remove(iPListViewC.getSelectionModel().getSelectedItem());
            iPListViewC.getParent().requestFocus();
            visitListViewC.getParent().requestFocus();
        }
        else {
            String header = "Aucun point sélectionné";
            String error = "Pour supprimer une point d'intérêt, choisissez-en un dans la liste";
            guiForms.displayWarningAlert(header,error).showAndWait();
        }
    }

    /**
     * Method pour récuperer la visite sélctionnée
     *
     *
     *
     * @return la visite sélectionnée
     */
    public Visit getSelectedVisit() {
        return visitListViewC.getSelectionModel().getSelectedItem();
    }

    /**
     * Method pour récupérer le point sélectionné
     *
     *
     *
     * @return le point sélectionné
     */
    public InterestPoint getSelectedPoint() {
        return iPListViewC.getSelectionModel().getSelectedItem();
    }

    /**
     * Charge les visites dans la liste
     *
     * @param visits la liste des visites a charger
     */
    public void loadCastleData(ArrayList<Visit> visits) {
        for (int i = 0; i < visits.size(); i++) {
            this.visitListC.add(visits.get(i));
        }
    }

    /**
     * Charge les points dans la liste
     *
     * @param visit la visite depuis laquelle il faut charger les points
     */
    public void loadIPData(Visit visit) {
        if(visit != null) {
            for (int i = 0; i < visit.getIP1().size(); i++) {
                this.iPListC.add(visit.getIP1().get(i));
            }
            for (int i = 0; i < visit.getIP2().size(); i++) {
                this.iPListC.add(visit.getIP2().get(i));
            }
            for (int i = 0; i < visit.getIP3().size(); i++) {
                this.iPListC.add(visit.getIP3().get(i));
            }
            iPListViewC.refresh();
        }
    }

    /**
     * Permet de recharger les données des listes
     */
    public void reloadCastleData() {

        Platform.runLater(() -> {
            FileManager.getInstance().getChateauWorkspace().getV().clear();
            visitListC.clear();
            visitListViewC.getItems().clear();

            FileManager.getInstance().InitChateau();

            for (int i = 0; i < FileManager.getInstance().getChateauWorkspace().getV().size(); i++) {
                visitListC.add(FileManager.getInstance().getChateauWorkspace().getV().get(i));
            }

            visitListViewC.refresh();
        });
    }

}
