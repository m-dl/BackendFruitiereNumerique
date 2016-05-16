package entities.chateau;

import files.FileManager;
import files.FileTools;

import java.io.File;
import java.util.ArrayList;

/**
 * Classe définissant les points d'intérêt contenus dans une Visite
 * Contient 5 File pour les descriptions texte et une image descriptive
 * Ainsi que 4 collection d'objets contenant des images, les images d'interieur, les images panoramique et les vidéos,
 * associées à Info
 */
public class InterestPoint {
    private File presentation_FR, presentation_EN, marker, picture, name_EN;
    private ArrayList<File> photos, interieur, _360, videos;
    private String name;
    private int floor;
    private double coordX, coordY;


    /**
     * Constructeur pour la classe InterestPoint
     * Permet de créer les fichiers contenus dans un point et de les initialiser à vide.
     * @param pathFrom est la chemin dans lequel le point sera créé.
     * @param name le nom du point
     */
    public InterestPoint(String pathFrom, String name) {
        if (!FileTools.Exist(new File(pathFrom)))
            FileTools.CreateDirectory(pathFrom);

        this.presentation_FR = new File(pathFrom + "/" + FileManager.PRESENTATION_FR);
        this.presentation_EN = new File(pathFrom + "/" + FileManager.PRESENTATION_EN);
        this.name_EN = new File(pathFrom + "/" + FileManager.NAME_EN);
        this.marker = new File(pathFrom + "/" + FileManager.MARKER);

        initInterestPoint(pathFrom);

        this.photos = FileTools.ListFolderPictures(pathFrom + "/" + FileManager.PHOTOS);
        this._360 = FileTools.ListFolderPictures(pathFrom + "/" + FileManager._360);
        this.interieur = FileTools.ListFolderPictures(pathFrom + "/" + FileManager.INTERIEUR);
        this.videos = FileTools.ListFolderVideos(pathFrom + "/" + FileManager.VIDEOS);
        ArrayList<File> tmpPicture = FileTools.ListFolderPictures(pathFrom);
        if (!tmpPicture.isEmpty())
            this.picture = tmpPicture.get(0);
        this.name = name;
        this.coordX = 0;
        this.coordY = 0;
        this.floor = 0;
        readMarker();
    }

    /**
     * Initialisation des fichiers pour la description contenue dans un point.
     *
     * @param pathFrom le chemin dans lequel seront initialisés les fichiers.
     */
    private void initInterestPoint(String pathFrom) {
        if (!FileTools.Exist(this.presentation_FR))
            FileTools.CreateFile(pathFrom + "/" + FileManager.PRESENTATION_FR);
        if (!FileTools.Exist(this.presentation_EN))
            FileTools.CreateFile(pathFrom + "/" + FileManager.PRESENTATION_EN);
        if (!FileTools.Exist(this.name_EN))
            FileTools.CreateFile(pathFrom + "/" + FileManager.NAME_EN);
        if (!FileTools.Exist(this.marker))
            FileTools.CreateFile(pathFrom + "/" + FileManager.MARKER);
    }

    /**
     * Lecture du fichier de la présentation française du point
     *
     * @return la chaîne du fichier lu
     */
    public String readPresentation_FR() {
        return FileTools.Read(this.presentation_FR);
    }

    /**
     * Écriture de la chaîne enregistée pour la description française dans le fichier.
     *
     * @param input la chaîne qui sera écrite dans le fichier
     */
    public void writePresentation_FR(String input) {
        FileTools.Write(this.presentation_FR, input);
    }

    /**
     * Lecture du fichier de la présentation anglaise du point
     *
     * @return la chaîne du fichier lu
     */
    public String readPresentation_EN() {
        return FileTools.Read(this.presentation_EN);
    }

    /**
     * Écriture de la chaîne enregistée pour la description anglaise dans le fichier.
     *
     * @param input la chaîne qui sera écrite dans le fichier
     */
    public void writePresentation_EN(String input) {
        FileTools.Write(this.presentation_EN, input);
    }

    /**
     * Lecture du fichier du nom du point en français
     *
     * @return la chaîne du fichier lu
     */
    public String readName_EN() {
        return FileTools.Read(this.name_EN);
    }

    /**
     * Écriture du nom du point en anglais
     *
     * @param input la chaîne qui sera écrite dans le fichier
     */
    public void writeName_EN(String input) {
        FileTools.Write(this.name_EN, input);
    }

    /**
     * Lecture du fichier des coordonnées du point
     */
    public void readMarker() {
        FileTools.ParseCoordinates(this);
    }

    /**
     * Écriture du fichier des coordonnées du point
     */
    public void writeMarker() {
        String input = this.floor + "\n" + this.coordX + "," + this.coordY;
        FileTools.Write(this.marker, input);
    }


    /**
     * Fonction permettant l'ajout de photos dans la liste Photos
     *
     * @param pathFrom chemin de l'image originale sélectionnée
     * @param pathTo   chemin dans lequel sera enregistrée la copie de l'image originelle.
     * @param f        l'image en elle même
     */
    public void addPhotos(String pathFrom, String pathTo, String f) {
        f = FileTools.StringToLower(f);
        String newPath = pathTo + "/" + FileManager.PHOTOS + "/" + f;
        FileTools.CopyFile(pathFrom, newPath);
        File file = new File(newPath);
        this.photos.add(file);
    }

    /**
     * Fonction permettant la suppression d'images de la liste Photos
     *
     * @param pathFrom chemin de l'image à supprimer
     * @param f        l'image en elle même
     */
    public void removePhotos(String pathFrom, File f) {
        FileTools.Delete(pathFrom);
        for (int i = 0; i < this.photos.size(); i++) {
            if (this.photos.get(i).getName().equals(f))
                this.photos.get(i).delete();
        }
    }

    /**
     * Fonction permettant l'ajout de photos dans la liste 360
     *
     * @param pathFrom chemin de l'image originale sélectionnée
     * @param pathTo   chemin dans lequel sera enregistrée la copie de l'image originelle.
     * @param f        l'image en elle même
     */
    public void add360(String pathFrom, String pathTo, String f) {
        f = FileTools.StringToLower(f);
        String newPath = pathTo + "/" + FileManager._360 + "/" + f;
        FileTools.CopyFile(pathFrom, newPath);
        File file = new File(newPath);
        this._360.add(file);
    }

    /**
     * Fonction permettant la suppression d'images de la liste 360
     *
     * @param pathFrom chemin de l'image à supprimer
     * @param f        l'image en elle même
     */
    public void remove360(String pathFrom, File f) {
        FileTools.Delete(pathFrom);
        for (int i = 0; i < this._360.size(); i++) {
            if (this._360.get(i).getName().equals(f))
                this._360.get(i).delete();
        }
    }

    /**
     * Fonction permettant l'ajout de photos dans la liste Video
     *
     * @param pathFrom chemin de l'image originale sélectionnée
     * @param pathTo   chemin dans lequel sera enregistrée la copie de l'image originelle.
     * @param f        l'image en elle même
     */
    public void addVideo(String pathFrom, String pathTo, String f) {
        f = FileTools.StringToLower(f);
        String newPath = pathTo + "/" + FileManager.VIDEOS + "/" + f;
        FileTools.CopyFile(pathFrom, newPath);
        File file = new File(newPath);
        this.videos.add(file);
    }

    /**
     * Fonction permettant la suppression d'images de la liste Video
     *
     * @param pathFrom chemin de l'image à supprimer
     * @param f        l'image en elle même
     */
    public void removeVideo(String pathFrom, File f) {
        FileTools.Delete(pathFrom);
        for (int i = 0; i < this.videos.size(); i++) {
            if (this.videos.get(i).getName().equals(f))
                this.videos.get(i).delete();
        }
    }

    /**
     * Fonction permettant l'ajout de photos dans la liste Interieur
     *
     * @param pathFrom chemin de l'image originale sélectionnée
     * @param pathTo   chemin dans lequel sera enregistrée la copie de l'image originelle.
     * @param f        l'image en elle même
     */
    public void addInterieur(String pathFrom, String pathTo, String f) {
        f = FileTools.StringToLower(f);
        String newPath = pathTo + "/" + FileManager.INTERIEUR + "/" + f;
        FileTools.CopyFile(pathFrom, newPath);
        File file = new File(newPath);
        this.interieur.add(file);
    }

    /**
     * Fonction permettant la suppression d'images de la liste Interieur
     *
     * @param pathFrom chemin de l'image à supprimer
     * @param f        l'image en elle même
     */
    public void removeInterieur(String pathFrom, File f) {
        FileTools.Delete(pathFrom);
        for (int i = 0; i < this.interieur.size(); i++) {
            if (this.interieur.get(i).getName().equals(f))
                this.interieur.get(i).delete();
        }
    }

    /**
     * Fonction permettant l'ajout de l'image descriptive
     *
     * @param pathFrom chemin de l'image originale sélectionnée
     * @param pathTo   chemin dans lequel sera enregistrée la copie de l'image originelle.
     * @param f        l'image en elle même
     */
    public void addPicture(String pathFrom, String pathTo, String f) {
        f = FileTools.StringToLower(f);
        String newPath = pathTo + "/" + f;
        FileTools.CopyFile(pathFrom, newPath);
        File file = new File(newPath);
        this.picture = file;
    }

    /**
     * Fonction permettant la suppression de l'image descriptive
     *
     * @param pathFrom chemin de l'image à supprimer
     * @param f        l'image en elle même
     */
    public void removePicture(String pathFrom, File f) {
        FileTools.Delete(pathFrom);
        this.picture = null;
    }

    /**
     * Fonction permettant la suppression complète du point
     *
     * @param pathFrom chemin du point à supprimer
     */
    public void delete(String pathFrom) {
        FileTools.Delete(pathFrom);
    }


     /*
        Getters et setters
     */

    public File getPresentation_FR() {
        return presentation_FR;
    }

    public void setPresentation_FR(File presentation_FR) {
        this.presentation_FR = presentation_FR;
    }

    public File getPresentation_EN() {
        return presentation_EN;
    }

    public void setPresentation_EN(File presentation_EN) {
        this.presentation_EN = presentation_EN;
    }

    public File getMarker() {
        return marker;
    }

    public void setMarker(File marker) {
        this.marker = marker;
    }

    public File getPicture() {
        return picture;
    }

    public void setPicture(File picture) {
        this.picture = picture;
    }

    public ArrayList<File> getPhotos() {
        return photos;
    }

    public void setPhotos(ArrayList<File> photos) {
        this.photos = photos;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<File> getInterieur() {
        return interieur;
    }

    public void setInterieur(ArrayList<File> interieur) {
        this.interieur = interieur;
    }

    public ArrayList<File> getVideos() {
        return videos;
    }

    public void setVideos(ArrayList<File> videos) {
        this.videos = videos;
    }

    public ArrayList<File> get_360() {
        return _360;
    }

    public void set_360(ArrayList<File> _360) {
        this._360 = _360;
    }

    public File getName_EN() {
        return name_EN;
    }

    public void setName_EN(File name_EN) {
        this.name_EN = name_EN;
    }

    public double getCoordX() {
        return coordX;
    }

    public void setCoordX(double coordX) {
        this.coordX = coordX;
    }

    public double getCoordY() {
        return coordY;
    }

    public void setCoordY(double coordY) {
        this.coordY = coordY;
    }

    public int getFloor() {
        return floor;
    }

    public void setFloor(int floor) {
        this.floor = floor;
    }
}
