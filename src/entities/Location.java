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
    public void addVisit(String s) {
        this.V.add(new Visit(s));
    }
}
