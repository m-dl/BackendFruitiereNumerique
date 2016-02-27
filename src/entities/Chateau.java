package entities;

import java.util.ArrayList;

/**
 * @author Maxime
 *
 */
public class Chateau {
    private ArrayList<Visit> V;
    
    public Chateau() {
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
}
