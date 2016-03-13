package entities;

import java.util.ArrayList;

/**
 * @author Maxime
 */
public class Visit {
	
  private ArrayList<InterestPoint> IP;
  private Overview overview;
  private Info info;
  private String name;
  
  public Visit(String name) {
      this.setIP(new ArrayList<InterestPoint>());
      this.setOverview(null);
      this.setInfo(null);
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
  
  // Add an interest point to a visit
  public void addInterestPoint(InterestPoint IP) {
      this.IP.add(IP);
  }
}
