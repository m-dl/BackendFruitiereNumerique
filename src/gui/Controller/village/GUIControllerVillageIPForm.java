package gui.Controller.village;

//import com.lynden.gmapsfx.GMap;
import com.lynden.gmapsfx.GMap;
import com.lynden.gmapsfx.GPSContainer;
import entities.village.InterestPoint;
import entities.village.Visit;
import files.FileManager;
import gui.Controller.GUIFormsController;
import gui.Controller.photo.GUIControllerPhotoForm;
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
import static gui.Controller.enums.VisitType.VILLAGE;

public class GUIControllerVillageIPForm {

    private static GUIControllerVillageIPForm INSTANCE = new GUIControllerVillageIPForm();

    public TextField ipName, coordLat, coordLong;
    public TextArea ipPresTextFR;
    public TextArea ipPresTextEN;

    public Button addDescImage;
    public Button preFillButton;

    public Label descLabel;
    public Label imageLabel;
    public Label videoLabel;
    public Label indoorLabel;
    public Label panoLabel;
    public GPSContainer coords;
    private Stage stage;
    private boolean isNewPoint;
    private File descPic;
    private ArrayList<File> photos, interieur, _360, videos;
    private String errorList;


    private GUIControllerVillageIPForm() {
        photos = new ArrayList<>();
        interieur = new ArrayList<>();
        _360 = new ArrayList<>();
        videos = new ArrayList<>();
        errorList = "";
        coords = new GPSContainer();
    }


    public static GUIControllerVillageIPForm getInstance() {
        return INSTANCE;
    }


    public void displayForm(boolean isNewPoint, InterestPoint selectedPoint) {

        this.isNewPoint = isNewPoint;
        photos.clear();
        interieur.clear();
        videos.clear();
        _360.clear();

        try {

            ScrollPane root = (ScrollPane) GUIUtilities.loadLayout("view/village/iPForm.fxml", this);
            root.getStylesheets().add("errorStyle.css");

            stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setOnCloseRequest(closeEvent -> GUIFormsController.getInstance().closeForm());


            if(!isNewPoint) {
                if (selectedPoint != null) {
                    int visitIndex = FileManager.getInstance().getVillageWorkspace().getV().indexOf(GUIControllerVillage.getInstance().getSelectedVisit());
                    int ipIndex = FileManager.getInstance().getVillageWorkspace().getV().get(visitIndex).getIP().indexOf(GUIControllerVillage.getInstance().getSelectedPoint());

                    descPic = FileManager.getInstance().getVillageWorkspace().getV().get(visitIndex).getIP().get(ipIndex).getPicture();
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
            GUIFormsController.getInstance().displayExceptionAlert(e,"Erreur lors de l'affichage du formulaire d'ajout de point").showAndWait();

        }

    }


    public void fillInputs(InterestPoint p) {
        ipName.setText(p.getName());
        ipPresTextFR.setText(p.readPresentation_FR());
        ipPresTextEN.setText(p.readPresentation_EN());


        coordLat.setText(p.readMarker().split(",")[0]);
        coordLong.setText(p.readMarker().split(",")[1]);

        //opérateurs ternaires pour savoir si mettre le texte au singulier ou au pluriel

        descLabel.setText((p.getPicture() == null) ? "Aucune image sélectionnée" : "Une image sélectionnée");
        imageLabel.setText((p.getPhotos().size() <= 1) ? p.getPhotos().size() + " image sélectionnée" : p.getPhotos().size() + " images sélectionnées");
        videoLabel.setText((p.getVideos().size() <= 1) ? p.getPhotos().size() + " vidéo sélectionnée" : p.getPhotos().size() + " vidéos sélectionnées");
        indoorLabel.setText((p.getInterieur().size() <= 1) ? p.getPhotos().size() + " image sélectionnée" : p.getPhotos().size() + " images sélectionnées");
        panoLabel.setText((p.get_360().size() <= 1) ? p.getPhotos().size() + " image sélectionnée" : p.getPhotos().size() + " images sélectionnées");
    }


    @FXML
    public void saveChanges() {

        if( validForm() ) {
            if (isNewPoint) {

                String ipName = this.ipName.getText();

                String ipPath = FileManager.WORKSPACE + "/" + FileManager.VILLAGE + "/" +
                        GUIControllerVillage.getInstance().getSelectedVisit().getName() + "/" + ipName;

                InterestPoint ip = new InterestPoint(ipPath, ipName);

                ip.addPicture(this.descPic.getAbsolutePath(), ipPath, this.descPic.getName());

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

                ip.writeMarker(coordLat.getText()+","+ coordLong.getText());

                FileManager.getInstance().getVillageWorkspace().getV().get(FileManager.getInstance()
                        .getVillageWorkspace().getV().indexOf(GUIControllerVillage.getInstance().getSelectedVisit())).addInterestPoint(ip);

                GUIControllerVillage.getInstance().iPListV.add(ip);

            }
            else {

                Visit selectedVisit = GUIControllerVillage.getInstance().getSelectedVisit();
                InterestPoint selectedPoint = GUIControllerVillage.getInstance().getSelectedPoint();

                int visitIndex = FileManager.getInstance().getVillageWorkspace().getV().indexOf(selectedVisit);
                int ipIndex = FileManager.getInstance().getVillageWorkspace().getV().get(visitIndex).getIP().indexOf(selectedPoint);

                String ipNewName = this.ipName.getText();
                String originalName = selectedPoint.getName();

                String ipPath = FileManager.WORKSPACE + "/" + FileManager.VILLAGE + "/" +
                        GUIControllerVillage.getInstance().getSelectedVisit().getName() + "/" + ipName;

                // TODO: 07/05/2016  rempir auto les coord

                if (!Objects.equals(ipNewName, originalName)) {
                    ipPath = FileManager.WORKSPACE + "/" + FileManager.VILLAGE + "/" +
                            GUIControllerVillage.getInstance().getSelectedVisit().getName();

                    renameIP(selectedPoint, ipPath, ipNewName, false);

                } else {

                    FileManager.getInstance().getVillageWorkspace().getV().get(visitIndex).getIP().get(ipIndex).writePresentation_FR(ipPresTextFR.getText());
                    FileManager.getInstance().getVillageWorkspace().getV().get(visitIndex).getIP().get(ipIndex).writePresentation_EN(ipPresTextEN.getText());

                    if (descPic != null) {
                        if (selectedPoint.getPicture() != null) {
                            FileManager.getInstance().getVillageWorkspace().getV().get(visitIndex).getIP().get(ipIndex).removePicture(selectedPoint.getPicture().getPath(), selectedPoint.getPicture());
                        }
                        FileManager.getInstance().getVillageWorkspace().getV().get(visitIndex).getIP().get(ipIndex).addPicture(this.descPic.getAbsolutePath(), ipPath, this.descPic.getName());
                    }

                    for (int i = 0; i < FileManager.getInstance().getVillageWorkspace().getV().get(visitIndex).getIP().get(ipIndex).getPhotos().size(); i++) {
                        if (!(photos.contains(FileManager.getInstance().getVillageWorkspace().getV().get(visitIndex).getIP().get(ipIndex).getPhotos().get(i)))) {
                            File imageToDel = FileManager.getInstance().getVillageWorkspace().getV().get(visitIndex).getIP().get(ipIndex).getPhotos().get(i);
                            System.out.println("stuff to be del" + imageToDel.getName());
                            FileManager.getInstance().getVillageWorkspace().getV().get(visitIndex).getIP().get(ipIndex).removePhotos(imageToDel.getPath(), imageToDel);
                        }
                    }

                    for (int i = 0; i < photos.size(); i++) {
                        if (!FileManager.getInstance().getVillageWorkspace().getV().get(visitIndex).getIP().get(ipIndex).getPhotos().contains(photos.get(i))) {
                            System.out.println("stuff to be add" + photos.get(i).getName());
                            FileManager.getInstance().getVillageWorkspace().getV().get(visitIndex).getIP().get(ipIndex).addPhotos(photos.get(i).getAbsolutePath(), ipPath, photos.get(i).getName());

                        }
                    }

                    for (int i = 0; i < FileManager.getInstance().getVillageWorkspace().getV().get(visitIndex).getIP().get(ipIndex).getInterieur().size(); i++) {
                        if (!(interieur.contains(FileManager.getInstance().getVillageWorkspace().getV().get(visitIndex).getIP().get(ipIndex).getInterieur().get(i)))) {
                            File imageToDel = FileManager.getInstance().getVillageWorkspace().getV().get(visitIndex).getIP().get(ipIndex).getInterieur().get(i);
                            System.out.println("stuff to be del" + imageToDel.getName());
                            FileManager.getInstance().getVillageWorkspace().getV().get(visitIndex).getIP().get(ipIndex).removeInterieur(imageToDel.getPath(), imageToDel);
                        }
                    }

                    for (int i = 0; i < interieur.size(); i++) {
                        if (!FileManager.getInstance().getVillageWorkspace().getV().get(visitIndex).getIP().get(ipIndex).getInterieur().contains(interieur.get(i))) {
                            System.out.println("stuff to be add" + interieur.get(i).getName());
                            FileManager.getInstance().getVillageWorkspace().getV().get(visitIndex).getIP().get(ipIndex).addInterieur(interieur.get(i).getAbsolutePath(), ipPath, interieur.get(i).getName());

                        }
                    }

                    for (int i = 0; i < FileManager.getInstance().getVillageWorkspace().getV().get(visitIndex).getIP().get(ipIndex).get_360().size(); i++) {
                        if (!(_360.contains(FileManager.getInstance().getVillageWorkspace().getV().get(visitIndex).getIP().get(ipIndex).get_360().get(i)))) {
                            File imageToDel = FileManager.getInstance().getVillageWorkspace().getV().get(visitIndex).getIP().get(ipIndex).get_360().get(i);
                            System.out.println("stuff to be del" + imageToDel.getName());
                            FileManager.getInstance().getVillageWorkspace().getV().get(visitIndex).getIP().get(ipIndex).remove360(imageToDel.getPath(), imageToDel);
                        }
                    }

                    for (int i = 0; i < _360.size(); i++) {
                        if (!FileManager.getInstance().getVillageWorkspace().getV().get(visitIndex).getIP().get(ipIndex).get_360().contains(_360.get(i))) {
                            System.out.println("stuff to be add" + _360.get(i).getName());
                            FileManager.getInstance().getVillageWorkspace().getV().get(visitIndex).getIP().get(ipIndex).add360(_360.get(i).getAbsolutePath(), ipPath, _360.get(i).getName());

                        }
                    }

                    for (int i = 0; i < FileManager.getInstance().getVillageWorkspace().getV().get(visitIndex).getIP().get(ipIndex).getVideos().size(); i++) {
                        if (!(videos.contains(FileManager.getInstance().getVillageWorkspace().getV().get(visitIndex).getIP().get(ipIndex).getVideos().get(i)))) {
                            File imageToDel = FileManager.getInstance().getVillageWorkspace().getV().get(visitIndex).getIP().get(ipIndex).getVideos().get(i);
                            System.out.println("stuff to be del" + imageToDel.getName());
                            FileManager.getInstance().getVillageWorkspace().getV().get(visitIndex).getIP().get(ipIndex).removeVideo(imageToDel.getPath(), imageToDel);
                        }
                    }

                    for (int i = 0; i < videos.size(); i++) {
                        if (!FileManager.getInstance().getVillageWorkspace().getV().get(visitIndex).getIP().get(ipIndex).getVideos().contains(videos.get(i))) {
                            System.out.println("stuff to be add" + videos.get(i).getName());
                            FileManager.getInstance().getVillageWorkspace().getV().get(visitIndex).getIP().get(ipIndex).addVideo(videos.get(i).getAbsolutePath(), ipPath, videos.get(i).getName());
                        }
                    }

                    FileManager.getInstance().getVillageWorkspace().getV().get(visitIndex).getIP().get(ipIndex).writeMarker(coordLat.getText()+","+ coordLong.getText());

                }
            }

            wipeData();
            GUIFormsController.getInstance().closeForm();
            stage.close();
        }
        else {
            GUIFormsController.getInstance().displayErrorAlert("Un ou plusieurs champs sont vides",
                    "Les champs manquants sont:",errorList).showAndWait();
            errorList = "";
        }
    }

    private void wipeData() {
        descPic = null;
        photos.clear();
        videos.clear();
        interieur.clear();
        _360.clear();
        GUIControllerPhotoForm.getInstance().wipePictures();
    }

    public InterestPoint renameIP(InterestPoint oldIP, String newPath, String newName, boolean isFromVisit) {

        String oldPath = FileManager.WORKSPACE + "/" + FileManager.VILLAGE + "/" +
                GUIControllerVillage.getInstance().getSelectedVisit().getName() + "/" + oldIP.getName();

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

            ip.writePresentation_FR(oldIP.readPresentation_FR());
            ip.writePresentation_EN(oldIP.readPresentation_EN());
            ip.writeMarker(coordLat.getText()+","+ coordLong.getText());
        }
        else {

            ip.addPicture(this.descPic.getAbsolutePath(), ipPath, this.descPic.getName());

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
                    .getVillageWorkspace().getV().indexOf(GUIControllerVillage.getInstance().getSelectedVisit())).deleteInterestPoint(oldIP,oldPath);

            FileManager.getInstance().getVillageWorkspace().getV().get(FileManager.getInstance()
                    .getVillageWorkspace().getV().indexOf(GUIControllerVillage.getInstance().getSelectedVisit())).addInterestPoint(ip);

            GUIControllerVillage.getInstance().iPListV.add(ip);
            GUIControllerVillage.getInstance().iPListV.remove(oldIP);
        }

        return ip;
    }

    private boolean validForm() {
        boolean isValid = true;

        if (this.coordLat.getText().equals("")) {
            errorList += "• Le case de latitude est vide\n";
            coordLat.getStyleClass().add("errorStyle");
            isValid = false;
        } else {
            double lat;

            try
            {
                lat = Double.parseDouble(this.coordLat.getText());

                if (lat  >= -90.0 && lat <= 90.0) {
                    coordLat.getStyleClass().clear();
                    coordLat.getStyleClass().addAll("text-field", "text-input");
                }
                else {
                    errorList += "• Le case de latitude contient une valeur incorrecte: " +
                            "elle doit être comprise entre -90 et 90\n";
                    coordLat.getStyleClass().add("errorStyle");
                }
            }
            catch(NumberFormatException e)
            {
                errorList += "• Le case de latitude contient une valeur incorrecte: " +
                        "celle-ci doit être numérique (par exemple 42 ou 42.4242)\n\n";
                coordLat.getStyleClass().add("errorStyle");
            }
        }


        if (this.coordLong.getText().equals("")) {
            errorList += "• Le case de longitude est vide\n";
            coordLong.getStyleClass().add("errorStyle");
            isValid = false;
        } else {
            double lon;
            try
            {
                lon = Double.parseDouble(this.coordLong.getText());

                if (lon  >= -180.0 && lon <= 180.0) {
                    coordLong.getStyleClass().clear();
                    coordLong.getStyleClass().addAll("text-field", "text-input");
                }
                else {
                    errorList += "• Le case de longitude contient une valeur incorrecte: " +
                            "elle doit être comprise entre -180 et 180\n";
                    coordLong.getStyleClass().add("errorStyle");
                }
            }
            catch(NumberFormatException e)
            {
                errorList += "• Le case de longitude contient une valeur incorrecte: " +
                        "celle-ci doit être numérique (par exemple 42 ou 42.4242)\n\n";
                coordLong.getStyleClass().add("errorStyle");
            }
        }

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

        return isValid;
    }

    public void displayGmap() {


        preFillButton.setDisable(false);
        GMap map = new GMap();
        map.setC(coords);

        try {
            map.display();
        } catch (Exception e) {
            e.printStackTrace();
            GUIFormsController.getInstance().displayExceptionAlert(e,"Erreur lors de l'affichage de la fenêtre Google Map").showAndWait();

        }


    }

    public void preFillPos() {

        coordLat.setText(String.valueOf(coords.getLat()));
        coordLong.setText(String.valueOf(coords.getLon()));

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
        imageLabel.setText((photos.size() <= 1) ? photos.size() + " image sélectionnée" : photos.size() + " images sélectionnées");
        this.photos = photos;
    }

    public ArrayList<File> getInterieur() {
        return interieur;
    }

    public void setInterieur(ArrayList<File> interieur) {
        indoorLabel.setText((interieur.size() <= 1) ? interieur.size() + " image sélectionnée" : interieur.size() + " images sélectionnées");
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

}
