package entities.village;

import files.FileManager;
import files.FileTools;

import java.io.File;
import java.util.ArrayList;

/**
 * Classe définissant une Visite
 * Contient :
 * 1 liste de points
 * Un élément Overview
 * Un élément Info
 * Un File pour le nom en anglais
 */
public class Visit {

    private ArrayList<InterestPoint> IP;
    private Overview overview;
    private Info info;
    private String name;

    /**
     * Constructeur pour la classe Visit
     * Permet de créer les fichiers et de les initialiser à vide.
     *
     * @param pathFrom est la chemin dans lequel la visite sera créée.
     * @param name est le nom qui sera attribué à la visite
     */
    public Visit(String pathFrom, String name) {
    	if(!FileTools.Exist(new File(pathFrom)))
            FileTools.CreateDirectory(pathFrom);
    	
        initVisit(pathFrom);

        this.setIP(new ArrayList<>());
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
     */
    public void deleteInterestPoint(InterestPoint IP, String pathFrom) {
        this.IP.remove(IP);
        IP.delete(pathFrom);
    }

    /**
     * Ajout d'un point dans la visite
     * @param IP le point à ajouter
     */
    public void addInterestPoint(InterestPoint IP) {
        this.IP.add(IP);
    }


    /*
        Getters et setters
     */

    public ArrayList<InterestPoint> getIP() {
        return IP;
    }

    public void setIP(ArrayList<InterestPoint> IP) {
        this.IP = IP;
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
