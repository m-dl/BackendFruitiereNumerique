package entities;

import java.util.ArrayList;

/**
 * @author Maxime
 *
 */
public class Location {
    private ArrayList<Visit> V;
    
    public Location() {
      this.setV(new ArrayList<Visit>());
    }

    /**
     * @return the V
     */
    public ArrayList<Visit> getV() {
      return V;
    }

    /**
     * @param V the V to set
     */
    public void setV(ArrayList<Visit> V) {
      this.V = V;
    }
    
    // Add a visit to a location
    public void addVisit(Visit v) {
        this.V.add(v);
    }
    
    /*
     * Debug : affichage de la lib de location
    public void display() {
      for(Visit v : V) {
        System.out.println(v.getName());
        for(InterestPoint ip : v.getIP()) {
          System.out.println("---"+ip.getName());
        }
      }
    }
    */      
}
