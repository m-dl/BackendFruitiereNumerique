package entities;

import java.util.ArrayList;

/**
 * Created by adriansalas on 08/02/2016.
 */
public class InterestPoint {
  private ArrayList<Media> M;
  private String name;
  
  public InterestPoint(String name) {
    this.setM(new ArrayList<Media>());
    this.name = name;
  }

  /**
   * @return the M
   */
  public ArrayList<Media> getM() {
    return M;
  }

  /**
   * @param M the M to set
   */
  public void setM(ArrayList<Media> M) {
    this.M = M;
  }
  
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
}
