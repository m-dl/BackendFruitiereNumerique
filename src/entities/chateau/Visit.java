package entities.chateau;

import files.FileManager;
import files.FileTools;

import java.io.File;
import java.util.ArrayList;

/**
 * Classe définissant une Visite
 * Contient :
 * 3 listes de points, une pour chaque étage du château
 * Un élément Overview
 * Un élément Info
 * Un File pour le nom en anglais
 * Une chaîne pour le nom français
 */
public class Visit {

    private ArrayList<InterestPoint> IP1, IP2, IP3;
    private Overview overview;
    private Info info;
    private File name_EN;
    private String name;

    /**
     * Constructeur pour la classe Visit
     * Permet de créer les fichiers et de les initialiser à vide.
     *
     * @param pathFrom est la chemin dans lequel la visite sera créée.
     * @param name est le nom qui sera attribué à la visite
     */
    public Visit(String pathFrom, String name) {
        if (!FileTools.Exist(new File(pathFrom)))
            FileTools.CreateDirectory(pathFrom);
        this.name_EN = new File(pathFrom + "/" + FileManager.NAME_EN);

        initVisit(pathFrom);

        this.setIP1(new ArrayList<>());
        this.setIP2(new ArrayList<>());
        this.setIP3(new ArrayList<>());
        this.setOverview(new Overview(pathFrom + "/" + FileManager.OVERVIEW_FOLDER));
        this.setInfo(new Info(pathFrom + "/" + FileManager.INFO_FOLDER));
        this.name = name;
    }

    /**
     * Initialisation des fichiers pour la description contenue dans une visite.
     *
     * @param pathFrom le chemin dans lequel seront initialisés les fichiers.
     */
    private void initVisit(String pathFrom) {
        if (!FileTools.Exist(new File(pathFrom + "/" + FileManager.OVERVIEW_FOLDER)))
            FileTools.CreateDirectory(pathFrom + "/" + FileManager.OVERVIEW_FOLDER);
        if (!FileTools.Exist(new File(pathFrom + "/" + FileManager.INFO_FOLDER)))
            FileTools.CreateDirectory(pathFrom + "/" + FileManager.INFO_FOLDER);
        if (!FileTools.Exist(this.name_EN))
            FileTools.CreateFile(pathFrom + "/" + FileManager.NAME_EN);
    }

    /**
     * Fonction pour la suppression d'une visite
     * @param pathFrom
     */
    public void delete(String pathFrom) {
        FileTools.Delete(pathFrom);
    }

    /**
     * Fonction pour la suppression d'un des points d'une visite
     * @param IP le point à supprimer
     * @param pathFrom le chemin du point
     * @param IPArray la liste dans laquelle le point sera supprimée, dépendant de l'étage du point
     */
    public void deleteInterestPoint(InterestPoint IP, String pathFrom, ArrayList<InterestPoint> IPArray) {
        IPArray.remove(IP);
        IP.delete(pathFrom);
    }

    /**
     * Ajout d'un point dans la visite
     * @param IP le point à ajouter
     * @param IPArray la liste ( étage ) dans laquelle ajouter le point
     */
    public void addInterestPoint(InterestPoint IP, ArrayList<InterestPoint> IPArray) {
        IPArray.add(IP);
    }


    /**
     * Lecture d nom anglais de la visite
     * @return la chaîne lue dans le fichier
     */
    public String readName_EN() {
        return FileTools.Read(this.name_EN);
    }

    /**
     * Écriture du nom en anglais dans le fichier
     * @param input le nom de la visite en anglais
     */
    public void writeName_EN(String input) {
        FileTools.Write(this.name_EN, input);
    }


    /*
        Getters et setters
     */

    public ArrayList<InterestPoint> getIP1() {
        return IP1;
    }

    public void setIP1(ArrayList<InterestPoint> IP1) {
        this.IP1 = IP1;
    }

    public ArrayList<InterestPoint> getIP2() {
        return IP2;
    }

    public void setIP2(ArrayList<InterestPoint> IP2) {
        this.IP2 = IP2;
    }

    public ArrayList<InterestPoint> getIP3() {
        return IP3;
    }

    public void setIP3(ArrayList<InterestPoint> IP3) {
        this.IP3 = IP3;
    }

    public Overview getOverview() {
        return overview;
    }

    public void setOverview(Overview overview) {
        this.overview = overview;
    }

    public Info getInfo() {
        return info;
    }

    public void setInfo(Info info) {
        this.info = info;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}
