package entities.chateau;

import files.FileManager;
import files.FileTools;

import java.io.File;
import java.util.ArrayList;

/**
 * Classe définissant Info contenue dans une Visite
 * Contient 3 File pour les descriptions texte et une image
 * Ainsi qu'une collection d'objets contenant des images associées à Info
 */
public class Info {
    private File content_FR, content_EN, picture;
    private ArrayList<File> photos;


    /**
     * Constructeur pour la classe Info
     * Permet de créer les fichiers contenus dans Info et de les initialiser à vide.
     *
     * @param pathFrom est la chemin dans lequel Info sera créé.
     */
    public Info(String pathFrom) {
        if (!FileTools.Exist(new File(pathFrom)))
            FileTools.CreateDirectory(pathFrom);

        this.content_FR = new File(pathFrom + "/" + FileManager.PRESENTATION_FR);
        this.content_EN = new File(pathFrom + "/" + FileManager.PRESENTATION_EN);

        initInfo(pathFrom);

        ArrayList<File> tmpPicture = FileTools.ListFolderPictures(pathFrom);
        if (!tmpPicture.isEmpty())
            this.picture = tmpPicture.get(0);
        this.photos = FileTools.ListFolderPictures(pathFrom + "/" + FileManager.PHOTOS);
    }

    /**
     * Initialisation des fichiers pour la description contenue dans Info.
     *
     * @param pathFrom le chemin dans lequel seront initialisés les fichiers.
     */
    private void initInfo(String pathFrom) {
        if (!FileTools.Exist(this.content_FR))
            FileTools.CreateFile(pathFrom + "/" + FileManager.PRESENTATION_FR);
        if (!FileTools.Exist(this.content_EN))
            FileTools.CreateFile(pathFrom + "/" + FileManager.PRESENTATION_EN);
    }

    /**
     * Lecture du fichier de la présentation française d'Info
     *
     * @return la chaîne du fichier lu
     */
    public String readContent_FR() {
        return FileTools.Read(this.content_FR);
    }

    /**
     * Écriture de la chaîne enregistée pour la description française dans le fichier.
     *
     * @param input la chaîne qui sera écrite dans le fichier
     */
    public void writeContent_FR(String input) {
        FileTools.Write(this.content_FR, input);

    }

    /**
     * Lecture du fichier de la présentation anglaise d'Info
     *
     * @return la chaîne du fichier lu
     */
    public String readContent_EN() {
        return FileTools.Read(this.content_EN);
    }

    /**
     * Écriture de la chaîne enregistée pour la description anglaise dans le fichier.
     *
     * @param input la chaîne qui sera écrite dans le fichier
     */
    public void writeContent_EN(String input) {
        FileTools.Write(this.content_EN, input);
    }


    /**
     * Fonction permettant l'ajout de photos dans la liste, contenue dans Info
     * @param pathFrom chemin de l'image originale sélectionnée
     * @param pathTo chemin dans lequel sera enregistrée la copie de l'image originelle.
     * @param f l'image en elle même
     */
    public void addPhotos(String pathFrom, String pathTo, String f) {
        f = FileTools.StringToLower(f);
        String newPath = pathTo + "/" + FileManager.PHOTOS + "/" + f;
        FileTools.CopyFile(pathFrom, newPath);
        File file = new File(newPath);
        this.photos.add(file);
    }


    /**
     * Fonction permettant la suppression d'images de Info
     * @param pathFrom chemin de l'image à supprimer
     * @param f l'image en elle même
     */
    public void removePhotos(String pathFrom, File f) {
        FileTools.Delete(pathFrom);
        for (int i = 0; i < this.photos.size(); i++) {
            if (this.photos.get(i).getName().equals(f))
                this.photos.get(i).delete();
        }
    }

    /**
     * Fonction permettant l'ajout de l'image descriptive d'Info
     * @param pathFrom chemin de l'image originale sélectionnée
     * @param pathTo chemin dans lequel sera enregistrée la copie de l'image originelle.
     * @param f l'image en elle même
     */
    public void addPicture(String pathFrom, String pathTo, String f) {
        f = FileTools.StringToLower(f);
        String newPath = pathTo + "/" + f;
        FileTools.CopyFile(pathFrom, newPath);
        File file = new File(newPath);
        this.picture = file;
    }

    /**
     * Fonction permettant la suppression de l'image descriptive d'Info
     * @param pathFrom chemin de l'image à supprimer
     * @param f l'image en elle même
     */
    public void removePicture(String pathFrom, File f) {
        FileTools.Delete(pathFrom);
        this.picture = null;
    }

     /*
        Getters et setters
     */


    /**
     * Method getContent_FR returns the content_FR of this Info object.
     *
     * @return the content_FR (type File) of this Info object.
     */
    public File getContent_FR() {
        return content_FR;
    }

    /**
     * Method setContent_FR sets the content_FR of this Info object.
     *
     *
     *
     * @param content_FR the content_FR of this Info object.
     *
     */
    public void setContent_FR(File content_FR) {
        this.content_FR = content_FR;
    }

    /**
     * Method getContent_EN returns the content_EN of this Info object.
     *
     *
     *
     * @return the content_EN (type File) of this Info object.
     */
    public File getContent_EN() {
        return content_EN;
    }

    /**
     * Method setContent_EN sets the content_EN of this Info object.
     *
     *
     *
     * @param content_EN the content_EN of this Info object.
     *
     */
    public void setContent_EN(File content_EN) {
        this.content_EN = content_EN;
    }

    /**
     * Method getPicture returns the picture of this Info object.
     *
     *
     *
     * @return the picture (type File) of this Info object.
     */
    public File getPicture() {
        return picture;
    }

    /**
     * Method setPicture sets the picture of this Info object.
     *
     *
     *
     * @param picture the picture of this Info object.
     *
     */
    public void setPicture(File picture) {
        this.picture = picture;
    }

    /**
     * Method getPhotos returns the photos of this Info object.
     *
     *
     *
     * @return the photos (type ArrayList<File>) of this Info object.
     */
    public ArrayList<File> getPhotos() {
        return photos;
    }

    /**
     * Method setPhotos sets the photos of this Info object.
     *
     *
     *
     * @param photos the photos of this Info object.
     *
     */
    public void setPhotos(ArrayList<File> photos) {
        this.photos = photos;
    }

}
