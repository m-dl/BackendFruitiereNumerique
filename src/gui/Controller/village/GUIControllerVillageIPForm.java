package gui.Controller.village;

import entities.village.InterestPoint;
import entities.village.Visit;
import files.FileManager;
import gui.Controller.GUIFormsController;
import gui.GUIUtilities;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import static gui.Controller.enums.PictureFormType.*;
import static gui.Controller.enums.VisitType.VILLAGE;

public class GUIControllerVillageIPForm {

    private static GUIControllerVillageIPForm INSTANCE = new GUIControllerVillageIPForm();

    public TextField ipName;
    public TextArea ipPresTextFR;
    public TextArea ipPresTextEN;

    private Stage stage;
    private boolean isNewPoint;

    private ArrayList<File> photos, interieur, _360, videos;
    private String errorList;


    private GUIControllerVillageIPForm() {
        photos = new ArrayList<>();
        interieur = new ArrayList<>();
        _360 = new ArrayList<>();
        videos = new ArrayList<>();
        errorList = "";
    }


    public static GUIControllerVillageIPForm getInstance() {
        return INSTANCE;
    }


    public void displayForm(boolean isNewPoint, InterestPoint selectedPoint) {

        this.isNewPoint = isNewPoint;

        try {

            ScrollPane root = (ScrollPane) GUIUtilities.loadLayout("view/village/iPForm.fxml", this);

            stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setOnCloseRequest(closeEvent -> GUIFormsController.getInstance().closeForm());


            if(!isNewPoint) {
                if (selectedPoint != null) {
                    int visitIndex = FileManager.getInstance().getVillageWorkspace().getV().indexOf(GUIControllerVillage.getInstance().getSelectedVisit());
                    int ipIndex = FileManager.getInstance().getVillageWorkspace().getV().get(visitIndex).getIP().indexOf(GUIControllerVillage.getInstance().getSelectedPoint());

                    photos = FileManager.getInstance().getVillageWorkspace().getV().get(visitIndex).getIP().get(ipIndex).getPhotos();
                    interieur = FileManager.getInstance().getVillageWorkspace().getV().get(visitIndex).getIP().get(ipIndex).getInterieur();
                    videos = FileManager.getInstance().getVillageWorkspace().getV().get(visitIndex).getIP().get(ipIndex).getVideos();
                    _360 = FileManager.getInstance().getVillageWorkspace().getV().get(visitIndex).getIP().get(ipIndex).get_360();

                    this.fillInputs(selectedPoint);
                    stage.setTitle("Modification du point: " + selectedPoint.getName());
                    GUIFormsController.getInstance().displayForm(stage);
                    stage.show();
                }
            }
            else {
                GUIFormsController.getInstance().displayForm(stage);
                stage.setTitle("Ajout d'un nouveau point");
                stage.show();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public void fillInputs(InterestPoint p) {
        ipName.setText(p.getName());
        ipPresTextFR.setText(p.readPresentation_FR());
        ipPresTextEN.setText(p.readPresentation_EN());
    }


    @FXML
    public void saveChanges() {

        if( validForm() ) {
            if (isNewPoint) {

                String ipName = this.ipName.getText();

                String ipPath = FileManager.WORKSPACE + "/" + FileManager.VILLAGE + "/" +
                        GUIControllerVillage.getInstance().getSelectedVisit().getName() + "/" + ipName;

                InterestPoint ip = new InterestPoint(ipPath, ipName);

                for (int i = 0; i < this.photos.size(); i++) {
                    ip.addPhotos(this.photos.get(i).getAbsolutePath(), ipPath, this.photos.get(i).getName());
                }

                for (int i = 0; i < this.interieur.size(); i++) {
                    ip.addInterieur(this.interieur.get(i).getAbsolutePath(), ipPath, this.interieur.get(i).getName());
                }

                for (int i = 0; i < this._360.size(); i++) {
                    ip.add360(this._360.get(i).getAbsolutePath(), ipPath, this._360.get(i).getName());
                }

                for (int i = 0; i < this.videos.size(); i++) {
                    ip.addVideo(this.videos.get(i).getAbsolutePath(), ipPath, this.videos.get(i).getName());
                }

                ip.writePresentation_FR(ipPresTextFR.getText());
                ip.writePresentation_EN(ipPresTextEN.getText());

                FileManager.getInstance().getVillageWorkspace().getV().get(FileManager.getInstance()
                        .getVillageWorkspace().getV().indexOf(GUIControllerVillage.getInstance().getSelectedVisit())).addInterestPoint(ip);

                GUIControllerVillage.getInstance().iPListV.add(ip);

            }
            else {

                Visit selectedVisit = GUIControllerVillage.getInstance().getSelectedVisit();
                InterestPoint selectedPoint = GUIControllerVillage.getInstance().getSelectedPoint();
                int visitIndex = FileManager.getInstance().getVillageWorkspace().getV().indexOf(selectedVisit);
                int ipIndex = FileManager.getInstance().getVillageWorkspace().getV().get(visitIndex).getIP().indexOf(selectedPoint);

                String ipName = this.ipName.getText();

                String ipPath = FileManager.WORKSPACE + "/" + FileManager.VILLAGE + "/" +
                        GUIControllerVillage.getInstance().getSelectedVisit().getName() + "/" + ipName;

                if (ipName != selectedPoint.getName()) {
                    // TODO: 17/04/2016 bouger tout le dossier
                }


                FileManager.getInstance().getVillageWorkspace().getV().get(visitIndex).getIP().get(ipIndex).writePresentation_FR(ipPresTextFR.getText());
                FileManager.getInstance().getVillageWorkspace().getV().get(visitIndex).getIP().get(ipIndex).writePresentation_EN(ipPresTextEN.getText());

                for (int i = 0; i < FileManager.getInstance().getVillageWorkspace().getV().get(visitIndex).getIP().get(ipIndex).getPhotos().size(); i++) {
                    if (! (photos.contains(FileManager.getInstance().getVillageWorkspace().getV().get(visitIndex).getIP().get(ipIndex).getPhotos().get(i)))) {
                        File imageToDel = FileManager.getInstance().getVillageWorkspace().getV().get(visitIndex).getIP().get(ipIndex).getPhotos().get(i);
                        System.out.println("stuff to be del" + imageToDel.getName());
                        FileManager.getInstance().getVillageWorkspace().getV().get(visitIndex).getIP().get(ipIndex).removePhotos(imageToDel.getPath(),imageToDel);
                    }
                }

                for (int i = 0; i < photos.size(); i++) {
                    if (!FileManager.getInstance().getVillageWorkspace().getV().get(visitIndex).getIP().get(ipIndex).getPhotos().contains(photos.get(i))) {
                        System.out.println("stuff to be add"+ photos.get(i).getName());
                        FileManager.getInstance().getVillageWorkspace().getV().get(visitIndex).getIP().get(ipIndex).addPhotos(photos.get(i).getAbsolutePath(), ipPath, photos.get(i).getName());

                    }
                }

                for (int i = 0; i < FileManager.getInstance().getVillageWorkspace().getV().get(visitIndex).getIP().get(ipIndex).getInterieur().size(); i++) {
                    if (! (interieur.contains(FileManager.getInstance().getVillageWorkspace().getV().get(visitIndex).getIP().get(ipIndex).getInterieur().get(i)))) {
                        File imageToDel = FileManager.getInstance().getVillageWorkspace().getV().get(visitIndex).getIP().get(ipIndex).getInterieur().get(i);
                        System.out.println("stuff to be del" + imageToDel.getName());
                        FileManager.getInstance().getVillageWorkspace().getV().get(visitIndex).getIP().get(ipIndex).removeInterieur(imageToDel.getPath(),imageToDel);
                    }
                }

                for (int i = 0; i < interieur.size(); i++) {
                    if (!FileManager.getInstance().getVillageWorkspace().getV().get(visitIndex).getIP().get(ipIndex).getInterieur().contains(interieur.get(i))) {
                        System.out.println("stuff to be add"+ interieur.get(i).getName());
                        FileManager.getInstance().getVillageWorkspace().getV().get(visitIndex).getIP().get(ipIndex).addInterieur(interieur.get(i).getAbsolutePath(), ipPath, interieur.get(i).getName());

                    }
                }

                for (int i = 0; i < FileManager.getInstance().getVillageWorkspace().getV().get(visitIndex).getIP().get(ipIndex).get_360().size(); i++) {
                    if (! (_360.contains(FileManager.getInstance().getVillageWorkspace().getV().get(visitIndex).getIP().get(ipIndex).get_360().get(i)))) {
                        File imageToDel = FileManager.getInstance().getVillageWorkspace().getV().get(visitIndex).getIP().get(ipIndex).get_360().get(i);
                        System.out.println("stuff to be del" + imageToDel.getName());
                        FileManager.getInstance().getVillageWorkspace().getV().get(visitIndex).getIP().get(ipIndex).remove360(imageToDel.getPath(),imageToDel);
                    }
                }

                for (int i = 0; i < _360.size(); i++) {
                    if (!FileManager.getInstance().getVillageWorkspace().getV().get(visitIndex).getIP().get(ipIndex).get_360().contains(_360.get(i))) {
                        System.out.println("stuff to be add"+ _360.get(i).getName());
                        FileManager.getInstance().getVillageWorkspace().getV().get(visitIndex).getIP().get(ipIndex).add360(_360.get(i).getAbsolutePath(), ipPath, _360.get(i).getName());

                    }
                }

                for (int i = 0; i < FileManager.getInstance().getVillageWorkspace().getV().get(visitIndex).getIP().get(ipIndex).getVideos().size(); i++) {
                    if (! (videos.contains(FileManager.getInstance().getVillageWorkspace().getV().get(visitIndex).getIP().get(ipIndex).getVideos().get(i)))) {
                        File imageToDel = FileManager.getInstance().getVillageWorkspace().getV().get(visitIndex).getIP().get(ipIndex).getVideos().get(i);
                        System.out.println("stuff to be del" + imageToDel.getName());
                        FileManager.getInstance().getVillageWorkspace().getV().get(visitIndex).getIP().get(ipIndex).removeVideo(imageToDel.getPath(),imageToDel);
                    }
                }

                for (int i = 0; i < videos.size(); i++) {
                    if (!FileManager.getInstance().getVillageWorkspace().getV().get(visitIndex).getIP().get(ipIndex).getVideos().contains(videos.get(i))) {
                        System.out.println("stuff to be add"+ videos.get(i).getName());
                        FileManager.getInstance().getVillageWorkspace().getV().get(visitIndex).getIP().get(ipIndex).addVideo(videos.get(i).getAbsolutePath(), ipPath, videos.get(i).getName());

                    }
                }

            }

            GUIFormsController.getInstance().closeForm();
            stage.close();
        }
    }

    private boolean validForm() {
        // TODO: 01/05/2016 valid form
        return true;
    }

    @FXML public void addDescriptivePicture() {
        GUIFormsController.getInstance().displayPhotoForm(VILLAGE, DESCRIPTIVE_PICTURE, this.isNewPoint);
    }

    @FXML public void addPictures() {

        GUIFormsController.getInstance().displayPhotoForm(VILLAGE, PICTURES, this.isNewPoint);
    }

    @FXML public void addVideos() {
        GUIFormsController.getInstance().displayPhotoForm(VILLAGE, VIDEOS, this.isNewPoint);
    }

    @FXML public void addPanoramic() {
        GUIFormsController.getInstance().displayPhotoForm(VILLAGE, PANORAMIC_PICTURES, this.isNewPoint);
    }

    @FXML public void addIndoors() {
        GUIFormsController.getInstance().displayPhotoForm(VILLAGE, INDOORS_PICTURES, this.isNewPoint);
    }

    public ArrayList<File> getPhotos() {
        return photos;
    }

    public void setPhotos(ArrayList<File> photos) {
        this.photos = photos;
    }

    public ArrayList<File> getInterieur() {
        return interieur;
    }

    public void setInterieur(ArrayList<File> interieur) {
        this.interieur = interieur;
    }

    public ArrayList<File> get_360() {
        return _360;
    }

    public void set_360(ArrayList<File> _360) {
        this._360 = _360;
    }

    public ArrayList<File> getVideos() {
        return videos;
    }

    public void setVideos(ArrayList<File> videos) {
        this.videos = videos;
    }


}
