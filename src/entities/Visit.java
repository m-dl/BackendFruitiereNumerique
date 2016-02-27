package entities;

import java.util.ArrayList;

/**
 * Created by adriansalas on 08/02/2016.
 */
public class Visit {
  private ArrayList<InterestPoint> IP;
  private String name;
  
  public Visit(String name) {
    this.setIP(new ArrayList<InterestPoint>());
    this.name = name;
  }

  /**
   * @return the iP
   */
  public ArrayList<InterestPoint> getIP() {
    return IP;
  }

  /**
   * @param iP the iP to set
   */
  public void setIP(ArrayList<InterestPoint> IP) {
    this.IP = IP;
  }
  
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
}
