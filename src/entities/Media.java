package entities;

/**
 * Created by adriansalas on 08/02/2016.
 */
public class Media {
  private String name;
  
  public Media(String name) {
    this.setName(name);
  }

  /**
   * @return the name
   */
  public String getName() {
    return name;
  }

  /**
   * @param name the name to set
   */
  public void setName(String name) {
    this.name = name;
  }
}
