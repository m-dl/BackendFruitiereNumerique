package entities.village;

import java.util.ArrayList;

/**
 * Classe contenant la liste de toutes les visites
 */
public class Location {
    private ArrayList<Visit> V;

    /**
     * Constructeur de la classe
     * Initialise la liste des visites
     */
    public Location() {
      this.setV(new ArrayList<>());
    }

    /**
     * Getteur de la liste des visites
     *
     * @return la liste des visites
     */
    public ArrayList<Visit> getV() {
      return V;
    }

    /**
     * Setteur de la liste des visites
     *
     * @param V la liste qui sera enregistée
     */
    public void setV(ArrayList<Visit> V) {
      this.V = V;
    }

    /**
     * Fonction pour l'ajout d'une visite dans la liste
     *
     * @param v la visite qui sera ajoutée
     */
    public void addVisit(Visit v) {
        this.V.add(v);
    }

    /**
     * Fonction pour la suppression d'une visite de la liste
     *
     * @param v        la visite à supprimer
     * @param pathFrom le chemin de la visite à supprimer
     */
    public void deleteVisit(Visit v, String pathFrom) {
        this.V.remove(v);
        v.delete(pathFrom);
    }     
}
