package gui.Controller.chateau;

import entities.chateau.*;
import files.FileManager;
import gui.Controller.GUIFormsController;
import gui.GUIUtilities;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

import static gui.Controller.enums.PictureFormType.*;
import static gui.Controller.enums.VisitType.CHATEAU;

public class GUIControllerChateauIPForm {

    private static GUIControllerChateauIPForm INSTANCE = new GUIControllerChateauIPForm();

    public TextField ipName;
    public TextArea ipPresTextFR;
    public TextArea ipPresTextEN;

    public Button addDescImage;

    public Label descLabel;
    public Label mapPointLabel;
    public Label imageLabel;
    public Label videoLabel;
    public Label indoorLabel;
    public Label panoLabel;

    private double coordX = -1;
    private double coordY = -1;

    private Stage stage;
    private boolean isNewPoint;

    private File descPic;
    private ArrayList<File> photos, interieur, _360, videos;
    private String errorList;
    private int floor;

    private GUIControllerChateauIPForm()
    {
        photos = new ArrayList<>();
        interieur = new ArrayList<>();
        _360 = new ArrayList<>();
        videos = new ArrayList<>();
        errorList = "";
    }

    public static GUIControllerChateauIPForm getInstance() {
        return INSTANCE;
    }

    public void displayForm(boolean isNewPoint, InterestPoint selectedPoint) {

        this.isNewPoint = isNewPoint;

        try {

            ScrollPane root = (ScrollPane) GUIUtilities.loadLayout("view/chateau/iPForm.fxml", this);
            root.getStylesheets().add("errorStyle.css");

            stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setOnCloseRequest(closeEvent -> GUIFormsController.getInstance().closeForm());


                if(!isNewPoint) {
                    if (selectedPoint != null) {
                        int visitIndex = FileManager.getInstance().getChateauWorkspace().getV().indexOf(GUIControllerChateau.getInstance().getSelectedVisit());

                        if (selectedPoint.getFloor() == Location.FLOOR_ONE) {
                            int ipIndex = FileManager.getInstance().getChateauWorkspace().getV().get(visitIndex).getIP1().indexOf(GUIControllerChateau.getInstance().getSelectedPoint());

                            descPic = FileManager.getInstance().getChateauWorkspace().getV().get(visitIndex).getIP1().get(ipIndex).getPicture();
                            photos = FileManager.getInstance().getChateauWorkspace().getV().get(visitIndex).getIP1().get(ipIndex).getPhotos();
                            interieur = FileManager.getInstance().getChateauWorkspace().getV().get(visitIndex).getIP1().get(ipIndex).getInterieur();
                            videos = FileManager.getInstance().getChateauWorkspace().getV().get(visitIndex).getIP1().get(ipIndex).getVideos();
                            _360 = FileManager.getInstance().getChateauWorkspace().getV().get(visitIndex).getIP1().get(ipIndex).get_360();

                        }
                        else if (selectedPoint.getFloor() == Location.FLOOR_TWO) {
                            int ipIndex = FileManager.getInstance().getChateauWorkspace().getV().get(visitIndex).getIP2().indexOf(GUIControllerChateau.getInstance().getSelectedPoint());

                            descPic = FileManager.getInstance().getChateauWorkspace().getV().get(visitIndex).getIP2().get(ipIndex).getPicture();
                            photos = FileManager.getInstance().getChateauWorkspace().getV().get(visitIndex).getIP2().get(ipIndex).getPhotos();
                            interieur = FileManager.getInstance().getChateauWorkspace().getV().get(visitIndex).getIP2().get(ipIndex).getInterieur();
                            videos = FileManager.getInstance().getChateauWorkspace().getV().get(visitIndex).getIP2().get(ipIndex).getVideos();
                            _360 = FileManager.getInstance().getChateauWorkspace().getV().get(visitIndex).getIP2().get(ipIndex).get_360();


                        }
                        else if (selectedPoint.getFloor() == Location.FLOOR_THREE) {
                            int ipIndex = FileManager.getInstance().getChateauWorkspace().getV().get(visitIndex).getIP3().indexOf(GUIControllerChateau.getInstance().getSelectedPoint());

                            descPic = FileManager.getInstance().getChateauWorkspace().getV().get(visitIndex).getIP3().get(ipIndex).getPicture();
                            photos = FileManager.getInstance().getChateauWorkspace().getV().get(visitIndex).getIP3().get(ipIndex).getPhotos();
                            interieur = FileManager.getInstance().getChateauWorkspace().getV().get(visitIndex).getIP3().get(ipIndex).getInterieur();
                            videos = FileManager.getInstance().getChateauWorkspace().getV().get(visitIndex).getIP3().get(ipIndex).getVideos();
                            _360 = FileManager.getInstance().getChateauWorkspace().getV().get(visitIndex).getIP3().get(ipIndex).get_360();

                        }

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

        coordX = p.getCoordX();
        coordY = p.getCoordY();
        floor = p.getFloor();

        //opérateurs ternaires pour savoir si mettre le texte au singulier ou au pluriel

        mapPointLabel.setText("Veuillez placer le point sur la carte");
        descLabel.setText((p.getPicture() == null) ? "Aucune image sélectionnée" : "Une image sélectionnée");
        imageLabel.setText((p.getPhotos().size() <= 1) ? p.getPhotos().size() + " image sélectionnée" : p.getPhotos().size() + " images sélectionnées");
        videoLabel.setText((p.getVideos().size() <= 1) ? p.getVideos().size() + " vidéo sélectionnée" : p.getVideos().size() + " vidéos sélectionnées");
        indoorLabel.setText((p.getInterieur().size() <= 1) ? p.getInterieur().size() + " image sélectionnée" : p.getInterieur().size() + " images sélectionnées");
        panoLabel.setText((p.get_360().size() <= 1) ? p.get_360().size() + " image sélectionnée" : p.get_360().size() + " images sélectionnées");
    }

    // TODO: 09/05/2016  

    // TODO: 07/05/2016  rempir auto les coord
    @FXML
    public void saveChanges() {

        if( validForm() ) {
            if (isNewPoint) {

                String ipName = this.ipName.getText();

                String ipPath = FileManager.WORKSPACE + "/" + FileManager.CHATEAU + "/" +
                        GUIControllerChateau.getInstance().getSelectedVisit().getName() + "/" + ipName;

                InterestPoint ip = new InterestPoint(ipPath, ipName);

                ip.addPicture(this.descPic.getAbsolutePath(), ipPath, this.descPic.getName());

                ip.setCoordX(this.coordX);
                ip.setCoordY(this.coordY);
                ip.setFloor(this.floor);
                ip.writeMarker();

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

                if (this.floor == Location.FLOOR_ONE) {
                    FileManager.getInstance().getChateauWorkspace().getV().get(FileManager.getInstance()
                            .getChateauWorkspace().getV().indexOf(GUIControllerChateau.getInstance().getSelectedVisit()))
                            .addInterestPoint(ip, GUIControllerChateau.getInstance().getSelectedVisit().getIP1());

                }
                else if (this.floor == Location.FLOOR_TWO) {
                    FileManager.getInstance().getChateauWorkspace().getV().get(FileManager.getInstance()
                            .getChateauWorkspace().getV().indexOf(GUIControllerChateau.getInstance().getSelectedVisit()))
                            .addInterestPoint(ip, GUIControllerChateau.getInstance().getSelectedVisit().getIP2());
                }
                else if (this.floor == Location.FLOOR_THREE) {
                    FileManager.getInstance().getChateauWorkspace().getV().get(FileManager.getInstance()
                            .getChateauWorkspace().getV().indexOf(GUIControllerChateau.getInstance().getSelectedVisit()))
                            .addInterestPoint(ip, GUIControllerChateau.getInstance().getSelectedVisit().getIP3());
                }
                else {
                    try {
                        throw new Exception("can't find the floor while creating new IP");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                GUIControllerChateau.getInstance().iPListC.add(ip);

            }
            else {

                Visit selectedVisit = GUIControllerChateau.getInstance().getSelectedVisit();
                InterestPoint selectedPoint = GUIControllerChateau.getInstance().getSelectedPoint();


                if (selectedPoint.getFloor() == Location.FLOOR_ONE) {

                    int visitIndex = FileManager.getInstance().getChateauWorkspace().getV().indexOf(selectedVisit);
                    int ipIndex = FileManager.getInstance().getChateauWorkspace().getV().get(visitIndex).getIP1().indexOf(selectedPoint);

                    String ipNewName = this.ipName.getText();
                    String originalName = selectedPoint.getName();


                    String ipPath = FileManager.WORKSPACE + "/" + FileManager.CHATEAU + "/" +
                            GUIControllerChateau.getInstance().getSelectedVisit().getName() + "/" + originalName;

                    if (!Objects.equals(ipNewName, originalName)) {
                        ipPath = FileManager.WORKSPACE + "/" + FileManager.CHATEAU + "/" +
                                GUIControllerChateau.getInstance().getSelectedVisit().getName();

                        renameIP(selectedPoint, ipPath, ipNewName, false);

                    } else {

                        FileManager.getInstance().getChateauWorkspace().getV().get(visitIndex).getIP1().get(ipIndex).writePresentation_FR(ipPresTextFR.getText());
                        FileManager.getInstance().getChateauWorkspace().getV().get(visitIndex).getIP1().get(ipIndex).writePresentation_EN(ipPresTextEN.getText());

                        FileManager.getInstance().getChateauWorkspace().getV().get(visitIndex).getIP1().get(ipIndex).setCoordX(coordX);
                        FileManager.getInstance().getChateauWorkspace().getV().get(visitIndex).getIP1().get(ipIndex).setCoordY(coordY);
                        FileManager.getInstance().getChateauWorkspace().getV().get(visitIndex).getIP1().get(ipIndex).setFloor(floor);
                        FileManager.getInstance().getChateauWorkspace().getV().get(visitIndex).getIP1().get(ipIndex).writeMarker();


                        if (this.descPic != null && !descPic.equals(selectedPoint.getPicture())) {
                            if (selectedPoint.getPicture() != null) {
                                FileManager.getInstance().getChateauWorkspace().getV().get(visitIndex).getIP1().get(ipIndex).removePicture(selectedPoint.getPicture().getPath(), selectedPoint.getPicture());
                                FileManager.getInstance().getChateauWorkspace().getV().get(visitIndex).getIP1().get(ipIndex).addPicture(this.descPic.getAbsolutePath(), ipPath, this.descPic.getName());

                            }
                        }

                        for (int i = 0; i < FileManager.getInstance().getChateauWorkspace().getV().get(visitIndex).getIP1().get(ipIndex).getPhotos().size(); i++) {
                            if (!(photos.contains(FileManager.getInstance().getChateauWorkspace().getV().get(visitIndex).getIP1().get(ipIndex).getPhotos().get(i)))) {
                                File imageToDel = FileManager.getInstance().getChateauWorkspace().getV().get(visitIndex).getIP1().get(ipIndex).getPhotos().get(i);
                                FileManager.getInstance().getChateauWorkspace().getV().get(visitIndex).getIP1().get(ipIndex).removePhotos(imageToDel.getPath(), imageToDel);
                            }
                        }

                        for (int i = 0; i < photos.size(); i++) {
                            if (!FileManager.getInstance().getChateauWorkspace().getV().get(visitIndex).getIP1().get(ipIndex).getPhotos().contains(photos.get(i))) {
                                FileManager.getInstance().getChateauWorkspace().getV().get(visitIndex).getIP1().get(ipIndex).addPhotos(photos.get(i).getAbsolutePath(), ipPath, photos.get(i).getName());

                            }
                        }

                        for (int i = 0; i < FileManager.getInstance().getChateauWorkspace().getV().get(visitIndex).getIP1().get(ipIndex).getInterieur().size(); i++) {
                            if (!(interieur.contains(FileManager.getInstance().getChateauWorkspace().getV().get(visitIndex).getIP1().get(ipIndex).getInterieur().get(i)))) {
                                File imageToDel = FileManager.getInstance().getChateauWorkspace().getV().get(visitIndex).getIP1().get(ipIndex).getInterieur().get(i);
                                FileManager.getInstance().getChateauWorkspace().getV().get(visitIndex).getIP1().get(ipIndex).removeInterieur(imageToDel.getPath(), imageToDel);
                            }
                        }

                        for (int i = 0; i < interieur.size(); i++) {
                            if (!FileManager.getInstance().getChateauWorkspace().getV().get(visitIndex).getIP1().get(ipIndex).getInterieur().contains(interieur.get(i))) {
                                FileManager.getInstance().getChateauWorkspace().getV().get(visitIndex).getIP1().get(ipIndex).addInterieur(interieur.get(i).getAbsolutePath(), ipPath, interieur.get(i).getName());

                            }
                        }

                        for (int i = 0; i < FileManager.getInstance().getChateauWorkspace().getV().get(visitIndex).getIP1().get(ipIndex).get_360().size(); i++) {
                            if (!(_360.contains(FileManager.getInstance().getChateauWorkspace().getV().get(visitIndex).getIP1().get(ipIndex).get_360().get(i)))) {
                                File imageToDel = FileManager.getInstance().getChateauWorkspace().getV().get(visitIndex).getIP1().get(ipIndex).get_360().get(i);
                                FileManager.getInstance().getChateauWorkspace().getV().get(visitIndex).getIP1().get(ipIndex).remove360(imageToDel.getPath(), imageToDel);
                            }
                        }

                        for (int i = 0; i < _360.size(); i++) {
                            if (!FileManager.getInstance().getChateauWorkspace().getV().get(visitIndex).getIP1().get(ipIndex).get_360().contains(_360.get(i))) {
                                FileManager.getInstance().getChateauWorkspace().getV().get(visitIndex).getIP1().get(ipIndex).add360(_360.get(i).getAbsolutePath(), ipPath, _360.get(i).getName());

                            }
                        }

                        for (int i = 0; i < FileManager.getInstance().getChateauWorkspace().getV().get(visitIndex).getIP1().get(ipIndex).getVideos().size(); i++) {
                            if (!(videos.contains(FileManager.getInstance().getChateauWorkspace().getV().get(visitIndex).getIP1().get(ipIndex).getVideos().get(i)))) {
                                File imageToDel = FileManager.getInstance().getChateauWorkspace().getV().get(visitIndex).getIP1().get(ipIndex).getVideos().get(i);
                                FileManager.getInstance().getChateauWorkspace().getV().get(visitIndex).getIP1().get(ipIndex).removeVideo(imageToDel.getPath(), imageToDel);
                            }
                        }

                        for (int i = 0; i < videos.size(); i++) {
                            if (!FileManager.getInstance().getChateauWorkspace().getV().get(visitIndex).getIP1().get(ipIndex).getVideos().contains(videos.get(i))) {
                                FileManager.getInstance().getChateauWorkspace().getV().get(visitIndex).getIP1().get(ipIndex).addVideo(videos.get(i).getAbsolutePath(), ipPath, videos.get(i).getName());

                            }
                        }
                    }
                }
                else if(selectedPoint.getFloor() == Location.FLOOR_TWO) {
                    int visitIndex = FileManager.getInstance().getChateauWorkspace().getV().indexOf(selectedVisit);
                    int ipIndex = FileManager.getInstance().getChateauWorkspace().getV().get(visitIndex).getIP2().indexOf(selectedPoint);

                    String ipNewName = this.ipName.getText();
                    String originalName = selectedPoint.getName();


                    String ipPath = FileManager.WORKSPACE + "/" + FileManager.CHATEAU + "/" +
                            GUIControllerChateau.getInstance().getSelectedVisit().getName() + "/" + originalName;

                    if (!Objects.equals(ipNewName, originalName)) {
                        ipPath = FileManager.WORKSPACE + "/" + FileManager.CHATEAU + "/" +
                                GUIControllerChateau.getInstance().getSelectedVisit().getName();

                        renameIP(selectedPoint, ipPath, ipNewName, false);

                    } else {

                        FileManager.getInstance().getChateauWorkspace().getV().get(visitIndex).getIP2().get(ipIndex).writePresentation_FR(ipPresTextFR.getText());
                        FileManager.getInstance().getChateauWorkspace().getV().get(visitIndex).getIP2().get(ipIndex).writePresentation_EN(ipPresTextEN.getText());

                        FileManager.getInstance().getChateauWorkspace().getV().get(visitIndex).getIP2().get(ipIndex).setCoordX(coordX);
                        FileManager.getInstance().getChateauWorkspace().getV().get(visitIndex).getIP2().get(ipIndex).setCoordY(coordY);
                        FileManager.getInstance().getChateauWorkspace().getV().get(visitIndex).getIP2().get(ipIndex).setFloor(floor);
                        FileManager.getInstance().getChateauWorkspace().getV().get(visitIndex).getIP2().get(ipIndex).writeMarker();

                        if (descPic != null && !descPic.equals(selectedPoint.getPicture())) {
                            if (selectedPoint.getPicture() != null) {
                                FileManager.getInstance().getChateauWorkspace().getV().get(visitIndex).getIP2().get(ipIndex).removePicture(selectedPoint.getPicture().getPath(), selectedPoint.getPicture());
                            }

                            System.out.println(descPic.getAbsolutePath());

                            FileManager.getInstance().getChateauWorkspace().getV().get(visitIndex).getIP2().get(ipIndex).addPicture(this.descPic.getAbsolutePath(), ipPath, this.descPic.getName());

                        }

                        for (int i = 0; i < FileManager.getInstance().getChateauWorkspace().getV().get(visitIndex).getIP2().get(ipIndex).getPhotos().size(); i++) {
                            if (!(photos.contains(FileManager.getInstance().getChateauWorkspace().getV().get(visitIndex).getIP2().get(ipIndex).getPhotos().get(i)))) {
                                File imageToDel = FileManager.getInstance().getChateauWorkspace().getV().get(visitIndex).getIP2().get(ipIndex).getPhotos().get(i);
                                FileManager.getInstance().getChateauWorkspace().getV().get(visitIndex).getIP2().get(ipIndex).removePhotos(imageToDel.getPath(), imageToDel);
                            }
                        }

                        for (int i = 0; i < photos.size(); i++) {
                            if (!FileManager.getInstance().getChateauWorkspace().getV().get(visitIndex).getIP2().get(ipIndex).getPhotos().contains(photos.get(i))) {
                                FileManager.getInstance().getChateauWorkspace().getV().get(visitIndex).getIP2().get(ipIndex).addPhotos(photos.get(i).getAbsolutePath(), ipPath, photos.get(i).getName());

                            }
                        }

                        for (int i = 0; i < FileManager.getInstance().getChateauWorkspace().getV().get(visitIndex).getIP2().get(ipIndex).getInterieur().size(); i++) {
                            if (!(interieur.contains(FileManager.getInstance().getChateauWorkspace().getV().get(visitIndex).getIP2().get(ipIndex).getInterieur().get(i)))) {
                                File imageToDel = FileManager.getInstance().getChateauWorkspace().getV().get(visitIndex).getIP2().get(ipIndex).getInterieur().get(i);
                                FileManager.getInstance().getChateauWorkspace().getV().get(visitIndex).getIP2().get(ipIndex).removeInterieur(imageToDel.getPath(), imageToDel);
                            }
                        }

                        for (int i = 0; i < interieur.size(); i++) {
                            if (!FileManager.getInstance().getChateauWorkspace().getV().get(visitIndex).getIP2().get(ipIndex).getInterieur().contains(interieur.get(i))) {
                                FileManager.getInstance().getChateauWorkspace().getV().get(visitIndex).getIP2().get(ipIndex).addInterieur(interieur.get(i).getAbsolutePath(), ipPath, interieur.get(i).getName());

                            }
                        }

                        for (int i = 0; i < FileManager.getInstance().getChateauWorkspace().getV().get(visitIndex).getIP2().get(ipIndex).get_360().size(); i++) {
                            if (!(_360.contains(FileManager.getInstance().getChateauWorkspace().getV().get(visitIndex).getIP2().get(ipIndex).get_360().get(i)))) {
                                File imageToDel = FileManager.getInstance().getChateauWorkspace().getV().get(visitIndex).getIP2().get(ipIndex).get_360().get(i);
                                FileManager.getInstance().getChateauWorkspace().getV().get(visitIndex).getIP2().get(ipIndex).remove360(imageToDel.getPath(), imageToDel);
                            }
                        }

                        for (int i = 0; i < _360.size(); i++) {
                            if (!FileManager.getInstance().getChateauWorkspace().getV().get(visitIndex).getIP2().get(ipIndex).get_360().contains(_360.get(i))) {
                                FileManager.getInstance().getChateauWorkspace().getV().get(visitIndex).getIP2().get(ipIndex).add360(_360.get(i).getAbsolutePath(), ipPath, _360.get(i).getName());

                            }
                        }

                        for (int i = 0; i < FileManager.getInstance().getChateauWorkspace().getV().get(visitIndex).getIP2().get(ipIndex).getVideos().size(); i++) {
                            if (!(videos.contains(FileManager.getInstance().getChateauWorkspace().getV().get(visitIndex).getIP2().get(ipIndex).getVideos().get(i)))) {
                                File imageToDel = FileManager.getInstance().getChateauWorkspace().getV().get(visitIndex).getIP2().get(ipIndex).getVideos().get(i);
                                FileManager.getInstance().getChateauWorkspace().getV().get(visitIndex).getIP2().get(ipIndex).removeVideo(imageToDel.getPath(), imageToDel);
                            }
                        }

                        for (int i = 0; i < videos.size(); i++) {
                            if (!FileManager.getInstance().getChateauWorkspace().getV().get(visitIndex).getIP2().get(ipIndex).getVideos().contains(videos.get(i))) {
                                FileManager.getInstance().getChateauWorkspace().getV().get(visitIndex).getIP2().get(ipIndex).addVideo(videos.get(i).getAbsolutePath(), ipPath, videos.get(i).getName());

                            }
                        }
                    }

                }
                else if (selectedPoint.getFloor() == Location.FLOOR_THREE) {
                    int visitIndex = FileManager.getInstance().getChateauWorkspace().getV().indexOf(selectedVisit);
                    int ipIndex = FileManager.getInstance().getChateauWorkspace().getV().get(visitIndex).getIP3().indexOf(selectedPoint);

                    String ipNewName = this.ipName.getText();
                    String originalName = selectedPoint.getName();


                    String ipPath = FileManager.WORKSPACE + "/" + FileManager.CHATEAU + "/" +
                            GUIControllerChateau.getInstance().getSelectedVisit().getName() + "/" + originalName;

                    if (!Objects.equals(ipNewName, originalName)) {
                        ipPath = FileManager.WORKSPACE + "/" + FileManager.CHATEAU + "/" +
                                GUIControllerChateau.getInstance().getSelectedVisit().getName();

                        renameIP(selectedPoint, ipPath, ipNewName, false);

                    } else {

                        FileManager.getInstance().getChateauWorkspace().getV().get(visitIndex).getIP3().get(ipIndex).writePresentation_FR(ipPresTextFR.getText());
                        FileManager.getInstance().getChateauWorkspace().getV().get(visitIndex).getIP3().get(ipIndex).writePresentation_EN(ipPresTextEN.getText());

                        FileManager.getInstance().getChateauWorkspace().getV().get(visitIndex).getIP3().get(ipIndex).setCoordX(coordX);
                        FileManager.getInstance().getChateauWorkspace().getV().get(visitIndex).getIP3().get(ipIndex).setCoordY(coordY);
                        FileManager.getInstance().getChateauWorkspace().getV().get(visitIndex).getIP3().get(ipIndex).setFloor(floor);
                        FileManager.getInstance().getChateauWorkspace().getV().get(visitIndex).getIP3().get(ipIndex).writeMarker();


                        if (descPic != null && !descPic.equals(selectedPoint.getPicture())) {
                            if (selectedPoint.getPicture() != null) {
                                FileManager.getInstance().getChateauWorkspace().getV().get(visitIndex).getIP3().get(ipIndex).removePicture(selectedPoint.getPicture().getPath(), selectedPoint.getPicture());
                                FileManager.getInstance().getChateauWorkspace().getV().get(visitIndex).getIP3().get(ipIndex).addPicture(this.descPic.getAbsolutePath(), ipPath, this.descPic.getName());
                            }
                        }

                        for (int i = 0; i < FileManager.getInstance().getChateauWorkspace().getV().get(visitIndex).getIP3().get(ipIndex).getPhotos().size(); i++) {
                            if (!(photos.contains(FileManager.getInstance().getChateauWorkspace().getV().get(visitIndex).getIP3().get(ipIndex).getPhotos().get(i)))) {
                                File imageToDel = FileManager.getInstance().getChateauWorkspace().getV().get(visitIndex).getIP3().get(ipIndex).getPhotos().get(i);
                                FileManager.getInstance().getChateauWorkspace().getV().get(visitIndex).getIP3().get(ipIndex).removePhotos(imageToDel.getPath(), imageToDel);
                            }
                        }

                        for (int i = 0; i < photos.size(); i++) {
                            if (!FileManager.getInstance().getChateauWorkspace().getV().get(visitIndex).getIP3().get(ipIndex).getPhotos().contains(photos.get(i))) {
                                FileManager.getInstance().getChateauWorkspace().getV().get(visitIndex).getIP3().get(ipIndex).addPhotos(photos.get(i).getAbsolutePath(), ipPath, photos.get(i).getName());

                            }
                        }

                        for (int i = 0; i < FileManager.getInstance().getChateauWorkspace().getV().get(visitIndex).getIP3().get(ipIndex).getInterieur().size(); i++) {
                            if (!(interieur.contains(FileManager.getInstance().getChateauWorkspace().getV().get(visitIndex).getIP3().get(ipIndex).getInterieur().get(i)))) {
                                File imageToDel = FileManager.getInstance().getChateauWorkspace().getV().get(visitIndex).getIP3().get(ipIndex).getInterieur().get(i);
                                FileManager.getInstance().getChateauWorkspace().getV().get(visitIndex).getIP3().get(ipIndex).removeInterieur(imageToDel.getPath(), imageToDel);
                            }
                        }

                        for (int i = 0; i < interieur.size(); i++) {
                            if (!FileManager.getInstance().getChateauWorkspace().getV().get(visitIndex).getIP3().get(ipIndex).getInterieur().contains(interieur.get(i))) {
                                FileManager.getInstance().getChateauWorkspace().getV().get(visitIndex).getIP3().get(ipIndex).addInterieur(interieur.get(i).getAbsolutePath(), ipPath, interieur.get(i).getName());

                            }
                        }

                        for (int i = 0; i < FileManager.getInstance().getChateauWorkspace().getV().get(visitIndex).getIP3().get(ipIndex).get_360().size(); i++) {
                            if (!(_360.contains(FileManager.getInstance().getChateauWorkspace().getV().get(visitIndex).getIP3().get(ipIndex).get_360().get(i)))) {
                                File imageToDel = FileManager.getInstance().getChateauWorkspace().getV().get(visitIndex).getIP3().get(ipIndex).get_360().get(i);
                                FileManager.getInstance().getChateauWorkspace().getV().get(visitIndex).getIP3().get(ipIndex).remove360(imageToDel.getPath(), imageToDel);
                            }
                        }

                        for (int i = 0; i < _360.size(); i++) {
                            if (!FileManager.getInstance().getChateauWorkspace().getV().get(visitIndex).getIP3().get(ipIndex).get_360().contains(_360.get(i))) {
                                FileManager.getInstance().getChateauWorkspace().getV().get(visitIndex).getIP3().get(ipIndex).add360(_360.get(i).getAbsolutePath(), ipPath, _360.get(i).getName());

                            }
                        }

                        for (int i = 0; i < FileManager.getInstance().getChateauWorkspace().getV().get(visitIndex).getIP3().get(ipIndex).getVideos().size(); i++) {
                            if (!(videos.contains(FileManager.getInstance().getChateauWorkspace().getV().get(visitIndex).getIP3().get(ipIndex).getVideos().get(i)))) {
                                File imageToDel = FileManager.getInstance().getChateauWorkspace().getV().get(visitIndex).getIP3().get(ipIndex).getVideos().get(i);
                                FileManager.getInstance().getChateauWorkspace().getV().get(visitIndex).getIP3().get(ipIndex).removeVideo(imageToDel.getPath(), imageToDel);
                            }
                        }

                        for (int i = 0; i < videos.size(); i++) {
                            if (!FileManager.getInstance().getChateauWorkspace().getV().get(visitIndex).getIP3().get(ipIndex).getVideos().contains(videos.get(i))) {
                                FileManager.getInstance().getChateauWorkspace().getV().get(visitIndex).getIP3().get(ipIndex).addVideo(videos.get(i).getAbsolutePath(), ipPath, videos.get(i).getName());

                            }
                        }
                    }
                }

            }

            GUIFormsController.getInstance().closeForm();
            stage.close();
        }
        else {
            GUIFormsController.getInstance().displayErrorAlert("Un ou plusieurs champs sont vides",
                    "Les champs manquants sont:",errorList).showAndWait();
            errorList = "";
        }
    }

    public InterestPoint renameIP(InterestPoint oldIP, String newPath, String newName, boolean isFromVisit) {

        String oldPath = FileManager.WORKSPACE + "/" + FileManager.CHATEAU + "/" +
                GUIControllerChateau.getInstance().getSelectedVisit().getName() + "/" + oldIP.getName();

        String ipPath = newPath + "/" + newName;

        InterestPoint ip = new InterestPoint(ipPath, newName);

        if(isFromVisit) {

            if (oldIP.getPicture() != null)
                ip.addPicture(oldIP.getPicture().getAbsolutePath(), ipPath, oldIP.getPicture().getName());

            for (int i = 0; i < oldIP.getPhotos().size(); i++) {
                ip.addPhotos(oldIP.getPhotos().get(i).getAbsolutePath(), ipPath, oldIP.getPhotos().get(i).getName());
            }

            for (int i = 0; i < oldIP.getInterieur().size(); i++) {
                ip.addInterieur(oldIP.getInterieur().get(i).getAbsolutePath(), ipPath, oldIP.getInterieur().get(i).getName());
            }

            for (int i = 0; i < oldIP.get_360().size(); i++) {
                ip.add360(oldIP.get_360().get(i).getAbsolutePath(), ipPath, oldIP.get_360().get(i).getName());
            }

            for (int i = 0; i < oldIP.getVideos().size(); i++) {
                ip.addVideo(oldIP.getVideos().get(i).getAbsolutePath(), ipPath, oldIP.getVideos().get(i).getName());
            }


            ip.setCoordX(this.coordX);
            ip.setCoordY(this.coordY);
            ip.setFloor(this.floor);
            ip.writeMarker();
            ip.writePresentation_FR(oldIP.readPresentation_FR());
            ip.writePresentation_EN(oldIP.readPresentation_EN());

        }
        else {

            ip.addPicture(this.descPic.getAbsolutePath(), ipPath, this.descPic.getName());

            ip.setFloor(floor);
            ip.setCoordX(coordX);
            ip.setCoordY(coordY);
            ip.writeMarker();


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


            if (this.floor == Location.FLOOR_ONE) {
                FileManager.getInstance().getChateauWorkspace().getV().get(FileManager.getInstance()
                        .getChateauWorkspace().getV().indexOf(GUIControllerChateau.getInstance().getSelectedVisit()))
                        .deleteInterestPoint(oldIP,oldPath,GUIControllerChateau.getInstance().getSelectedVisit().getIP1());

                FileManager.getInstance().getChateauWorkspace().getV().get(FileManager.getInstance()
                        .getChateauWorkspace().getV().indexOf(GUIControllerChateau.getInstance().getSelectedVisit()))
                        .addInterestPoint(ip,GUIControllerChateau.getInstance().getSelectedVisit().getIP1());

                GUIControllerChateau.getInstance().iPListC.add(ip);
                GUIControllerChateau.getInstance().iPListC.remove(oldIP);

            }
            else if (this.floor == Location.FLOOR_TWO) {
                FileManager.getInstance().getChateauWorkspace().getV().get(FileManager.getInstance()
                        .getChateauWorkspace().getV().indexOf(GUIControllerChateau.getInstance().getSelectedVisit()))
                        .deleteInterestPoint(oldIP,oldPath,GUIControllerChateau.getInstance().getSelectedVisit().getIP2());

                FileManager.getInstance().getChateauWorkspace().getV().get(FileManager.getInstance()
                        .getChateauWorkspace().getV().indexOf(GUIControllerChateau.getInstance().getSelectedVisit()))
                        .addInterestPoint(ip,GUIControllerChateau.getInstance().getSelectedVisit().getIP2());

                GUIControllerChateau.getInstance().iPListC.add(ip);
                GUIControllerChateau.getInstance().iPListC.remove(oldIP);
            }
            else if (this.floor == Location.FLOOR_THREE) {
                FileManager.getInstance().getChateauWorkspace().getV().get(FileManager.getInstance()
                        .getChateauWorkspace().getV().indexOf(GUIControllerChateau.getInstance().getSelectedVisit()))
                        .deleteInterestPoint(oldIP,oldPath,GUIControllerChateau.getInstance().getSelectedVisit().getIP3());

                FileManager.getInstance().getChateauWorkspace().getV().get(FileManager.getInstance()
                        .getChateauWorkspace().getV().indexOf(GUIControllerChateau.getInstance().getSelectedVisit()))
                        .addInterestPoint(ip,GUIControllerChateau.getInstance().getSelectedVisit().getIP3());

                GUIControllerChateau.getInstance().iPListC.add(ip);
                GUIControllerChateau.getInstance().iPListC.remove(oldIP);
            }
            else {
                try {
                    throw new Exception("can't find the floor while deleting IP");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }


        }

        return ip;
    }

    private boolean validForm() {
        boolean isValid = true;

        if (this.ipName.getText().equals("")) {
            errorList += "• Le case du nom est vide\n";
            ipName.getStyleClass().add("errorStyle");
            isValid = false;
        }
        else {
            ipName.getStyleClass().clear();
            ipName.getStyleClass().addAll("text-field", "text-input");
        }

        if (this.ipPresTextFR.getText().equals("")) {
            errorList += "• Le case de présentation du point en français est vide\n";
            ipPresTextFR.getStyleClass().add("errorStyle");
            isValid = false;
        }
        else {
            ipPresTextFR.getStyleClass().clear();
            ipPresTextFR.getStyleClass().addAll("text-input","text-area");
        }

        if (this.ipPresTextEN.getText().equals("")) {
            errorList += "• Le case de présentation du point en anglais est vide\n";
            ipPresTextEN.getStyleClass().add("errorStyle");
            isValid = false;
        }
        else {
            ipPresTextEN.getStyleClass().clear();
            ipPresTextEN.getStyleClass().addAll("text-input","text-area");
        }

        if (this.descPic == null) {
            errorList += "• Au moins une image descriptive doit être sélectionnée\n";
            addDescImage.getStyleClass().addAll("buttonErrorStyle");
            isValid = false;
        }
        else {
            addDescImage.getStyleClass().clear();
            addDescImage.getStyleClass().addAll("button");
        }

        // TODO: 12/05/2016 ne pas valider form si pas de point placé
        return isValid;
    }

    public void placePoint() {
        GUIFormsController.getInstance().displayPointPlacerFrom(isNewPoint);
    }

    //@FXML
    public void addDescriptivePicture() {
        GUIFormsController.getInstance().displayPhotoForm(CHATEAU, DESCRIPTIVE_PICTURE, this.isNewPoint);
    }

    //@FXML
    public void addPictures() {

        GUIFormsController.getInstance().displayPhotoForm(CHATEAU, PICTURES, this.isNewPoint);
    }

    //@FXML
    public void addVideos() {
        GUIFormsController.getInstance().displayPhotoForm(CHATEAU, VIDEOS, this.isNewPoint);
    }

    //@FXML
    public void addPanoramic() {
        GUIFormsController.getInstance().displayPhotoForm(CHATEAU, PANORAMIC_PICTURES, this.isNewPoint);
    }

    //@FXML
    public void addIndoors() {
        GUIFormsController.getInstance().displayPhotoForm(CHATEAU, INDOORS_PICTURES, this.isNewPoint);
    }

    public ArrayList<File> getPhotos() {
        return photos;
    }

    public void setPhotos(ArrayList<File> photos) {
        imageLabel.setText((photos.size() <= 1) ? photos.size() + " image sélectionnée" : photos.size() + " images sélectionnées");
        this.photos = photos;
    }

    public ArrayList<File> getInterieur() {
        return interieur;
    }

    public void setInterieur(ArrayList<File> interieur) {
        indoorLabel.setText((interieur.size() <= 1) ? interieur + " image sélectionnée" : interieur.size() + " images sélectionnées");
        this.interieur = interieur;
    }

    public ArrayList<File> get_360() {
        return _360;
    }

    public void set_360(ArrayList<File> _360) {
        panoLabel.setText((_360.size() <= 1) ? _360.size() + " image sélectionnée" : _360.size() + " images sélectionnées");
        this._360 = _360;
    }

    public ArrayList<File> getVideos() {
        return videos;
    }

    public void setVideos(ArrayList<File> videos) {
        videoLabel.setText((videos.size() <= 1) ? videos.size() + " vidéo sélectionnée" : videos.size() + " vidéos sélectionnées");
        this.videos = videos;
    }

    public File getDescPic() {
        return descPic;
    }

    public void setDescPic(File descPic) {
        this.descPic = descPic;

        if (descPic == null) {
            descLabel.setText("Aucune image sélectionnée");
        }
        else {
            descLabel.setText("Une image sélectionnée");
        }
    }

    public void setCoords(int floor, double coordX, double coordY) {
        this.floor = floor+1;
        this.coordX = coordX;
        this.coordY = coordY;

        System.out.println(this.floor);
        mapPointLabel.setText("Coordonnées enregistrées");

    }

    public double getCoordY() {
        return coordY;
    }

    public double getCoordX() {
        return coordX;
    }

    public int getFloor() {
        return floor;
    }
}
