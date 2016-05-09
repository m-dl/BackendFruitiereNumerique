package entities.chateau;

import java.util.ArrayList;

/**
 * @author Maxime
 *
 */
public class Location {
    public static final int FLOOR_ONE = 1;
    public static final int FLOOR_TWO = 2;
    public static final int FLOOR_THREE = 3;
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
    
    // Delete a visit to a location
    public void deleteVisit(Visit v, String pathFrom) {
        this.V.remove(v);
        v.delete(pathFrom);
    }     
}
