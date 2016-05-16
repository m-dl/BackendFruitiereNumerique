package entities.village;

import files.FileManager;
import files.FileTools;

import java.io.File;
import java.util.ArrayList;

/**
 * Classe définissant Overview contenue dans une Visite
 * Contient 4 File pour les descriptions texte
 * Ainsi qu'une collection d'objets contenant des images associées à Overview
 */
public class Overview {
    private File presentation_FR, presentation_EN, length_FR, length_EN;
    private ArrayList<File> imagesContent;

    /**
     * Constructeur pour la classe Overview
     * Permet de créer les fichiers contenus dans Info et de les initialiser à vide.
     *
     * @param pathFrom est la chemin dans lequel Overview sera créé.
     */
    public Overview(String pathFrom) {
        if (!FileTools.Exist(new File(pathFrom)))
            FileTools.CreateDirectory(pathFrom);

        this.presentation_FR = new File(pathFrom + "/" + FileManager.PRESENTATION_FR);
        this.presentation_EN = new File(pathFrom + "/" + FileManager.PRESENTATION_EN);
        this.length_FR = new File(pathFrom + "/" + FileManager.LENGTH_FR);
        this.length_EN = new File(pathFrom + "/" + FileManager.LENGTH_EN);

        initOverview(pathFrom);

        this.imagesContent = FileTools.ListFolderPictures(pathFrom);
    }

    /**
     * Initialisation des fichiers pour les descriptions contenus dans Overview.
     *
     * @param pathFrom le chemin dans lequel seront initialisés les fichiers.
     */
    private void initOverview(String pathFrom) {
        if (!FileTools.Exist(this.presentation_FR))
            FileTools.CreateFile(pathFrom + "/" + FileManager.PRESENTATION_FR);
        if (!FileTools.Exist(this.presentation_EN))
            FileTools.CreateFile(pathFrom + "/" + FileManager.PRESENTATION_EN);
        if (!FileTools.Exist(this.length_FR))
            FileTools.CreateFile(pathFrom + "/" + FileManager.LENGTH_FR);
        if (!FileTools.Exist(this.length_EN))
            FileTools.CreateFile(pathFrom + "/" + FileManager.LENGTH_EN);
    }

    /*
     * Fonctions pour l'écriture et la lecture des fichiers de description
     */

    public String readPresentation_FR() {
        return FileTools.Read(this.presentation_FR);
    }

    public void writePresentation_FR(String input) {
        FileTools.Write(this.presentation_FR, input);
    }

    public String readPresentation_EN() {
        return FileTools.Read(this.presentation_EN);
    }

    public void writePresentation_EN(String input) {
        FileTools.Write(this.presentation_EN, input);
    }

    public String readLength_FR() {
        return FileTools.Read(this.length_FR);
    }

    public void writeLength_FR(String input) {
        FileTools.Write(this.length_FR, input);
    }

    public String readLength_EN() {
        return FileTools.Read(this.length_EN);
    }

    public void writeLength_EN(String input) {
        FileTools.Write(this.length_EN, input);
    }

    /**
     * Fonction permettant l'ajout de photos dans la liste, contenue dans Overview
     *
     * @param pathFrom chemin de l'image originale sélectionnée
     * @param pathTo   chemin dans lequel sera enregistrée la copie de l'image originelle.
     * @param f        l'image en elle même
     */
    public void addImagesContent(String pathFrom, String pathTo, String f) {
        f = FileTools.StringToLower(f);
        String newPath = pathTo + "/" + f;
        FileTools.CopyFile(pathFrom, newPath);
        File file = new File(newPath);
        this.imagesContent.add(file);
    }

    /**
     * Fonction permettant la suppression d'images d'Overview
     *
     * @param pathFrom chemin de l'image à supprimer
     * @param f        l'image en elle même
     */
    public void removeImagesContent(String pathFrom, File f) {
        FileTools.Delete(pathFrom);
        this.imagesContent.remove(this.imagesContent.indexOf(f));
    }

    /**
     * Fonction permettant la suppression des toutes les images
     */
    public void removeAll() {
        for (int i = 0; i < this.imagesContent.size(); i++) {

            removeImagesContent(this.imagesContent.get(i).getPath(), this.imagesContent.get(i));
        }
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

    public File getLength_FR() {
        return length_FR;
    }

    public void setLength_FR(File length_FR) {
        this.length_FR = length_FR;
    }

    public File getLength_EN() {
        return length_EN;
    }

    public void setLength_EN(File length_EN) {
        this.length_EN = length_EN;
    }

    public ArrayList<File> getImagesContent() {
        return imagesContent;
    }

    public void setImagesContent(ArrayList<File> imagesContent, String pathTo) {

        for (int i = 0; i < imagesContent.size(); i++) {

            System.out.println(imagesContent.get(i).getPath());

            String name = FileTools.StringToLower(imagesContent.get(i).getName());
            String newPath = pathTo + "/" + name;
            if (!imagesContent.get(i).getPath().equals(newPath))
                FileTools.CopyFile(imagesContent.get(i).getPath(), newPath);
        }

        this.imagesContent = imagesContent;
    }
}
