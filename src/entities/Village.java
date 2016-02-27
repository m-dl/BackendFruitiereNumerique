package entities;

import java.util.ArrayList;

/**
 * @author Maxime
 *
 */
public class Village {
    private ArrayList<Visit> V;
    
    public Village() {
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
