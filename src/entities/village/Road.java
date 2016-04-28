package entities.village;

import java.util.ArrayList;

/**
 * @author Maxime
 *
 */
public class Road {
	final public static String ROAD_WIDTH = "width=";
	final public static String ROAD_COLOR = "color=";

	private String width, color;
	private ArrayList<String> coord;
	
	/**
	 * @param coord
	 * @param width
	 * @param color
	 */
	public Road(ArrayList<String> coord, String width, String color) {
		this.coord = coord;
		this.width = width;
		this.color = color;
	}

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
