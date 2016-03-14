package entities;

import files.FileManager;
import files.FileTools;

import java.io.File;
import java.util.ArrayList;

/**
 * @author Maxime
 */
public class InterestPoint {    
	private File presentation_FR, presentation_EN, marker, road, picture;
	private ArrayList<File> photos, interieur, _360, video;
	String name;
	
	/**
	 * @param pathFrom
	 */
	public InterestPoint(String pathFrom, String name) {
		this.presentation_FR = new File(pathFrom + "/" + FileManager.PRESENTATION_FR);
		this.presentation_EN = new File(pathFrom + "/" + FileManager.PRESENTATION_EN);
		this.marker = new File(pathFrom + "/" + FileManager.MARKER);
		this.road = new File(pathFrom + "/" + FileManager.ROAD);

		initInterestPoint(pathFrom);

		this.photos = FileTools.ListFolderPictures(pathFrom + "/" + FileManager.PHOTOS);
		this._360 = FileTools.ListFolderPictures(pathFrom + "/" + FileManager._360);
		this.interieur = FileTools.ListFolderPictures(pathFrom + "/" + FileManager.INTERIEUR);
		this.video = FileTools.ListFolderVideos(pathFrom + "/" + FileManager.VIDEO);
		ArrayList<File> tmpPicture = FileTools.ListFolderPictures(pathFrom);
		if(!tmpPicture.isEmpty())
			this.picture = tmpPicture.get(0);
		this.name = name;		
	}
	
	private void initInterestPoint(String pathFrom) {
		if(!FileTools.Exist(this.presentation_FR))
			FileTools.CreateFile(pathFrom + "/" + FileManager.PRESENTATION_FR);
		if(!FileTools.Exist(this.presentation_EN))
			FileTools.CreateFile(pathFrom + "/" + FileManager.PRESENTATION_EN);
		if(!FileTools.Exist(this.marker))
			FileTools.CreateFile(pathFrom + "/" + FileManager.MARKER);
		if(!FileTools.Exist(this.road))
			FileTools.CreateFile(pathFrom + "/" + FileManager.ROAD);	
	}

	public File getPresentation_FR() {
		return presentation_FR;
	}

	public void setPresentation_FR(File presentation_FR) {
		this.presentation_FR = presentation_FR;
	}

	public File getPresentation_EN() {
		return presentation_EN;
	}

	public void setPresentation_EN(File presentation_EN) {
		this.presentation_EN = presentation_EN;
	}

	public File getMarker() {
		return marker;
	}

	public void setMarker(File marker) {
		this.marker = marker;
	}

	public File getPicture() {
		return picture;
	}

	public void setPicture(File picture) {
		this.picture = picture;
	}

	public ArrayList<File> getPhotos() {
		return photos;
	}

	public void setPhotos(ArrayList<File> photos) {
		this.photos = photos;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public File getRoad() {
		return road;
	}

	public void setRoad(File road) {
		this.road = road;
	}

	public ArrayList<File> getInterieur() {
		return interieur;
	}

	public void setInterieur(ArrayList<File> interieur) {
		this.interieur = interieur;
	}

	public ArrayList<File> getVideo() {
		return video;
	}

	public void setVideo(ArrayList<File> video) {
		this.video = video;
	}

	public ArrayList<File> get_360() {
		return _360;
	}

	public void set_360(ArrayList<File> _360) {
		this._360 = _360;
	}
}
