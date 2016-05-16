package entities.village;

import java.util.ArrayList;

/**
 * Classe définissant une Road
 * Un chemin sur lequel est un point avec la couleur du chemin et l'épaisseur du trait.
 */
public class Road {
    final public static String ROAD_WIDTH = "width=";
    final public static String ROAD_COLOR = "color=";

    private String width, color;
    private ArrayList<String> coord;

    /**
     * Constructeur pour la classe Visit
     * Permet de créer les fichiers et de les initialiser à vide.
     * @param coord la liste des coordonnées des points du chemin
     * @param width l'épaisseur du trait du chemin à afficher
     * @param color la couleur du trait à afficher
     */
    public Road(ArrayList<String> coord, String width, String color) {
        this.coord = coord;
        this.width = width;
        this.color = color;
    }

     /*
        Getters et setters
     */

    public ArrayList<String> getCoord() {
        return coord;
    }

    public void setCoord(ArrayList<String> coord) {
        this.coord = coord;
    }

    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
