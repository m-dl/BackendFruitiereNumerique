package entities;

import files.FileManager;
import files.FileTools;

import java.io.File;
import java.util.ArrayList;

/**
 * @author Maxime
 *
 */
public class Info { 
	private File content_FR, content_EN, picture;
	private ArrayList<File> photos;
	
	/**
	 * @param pathFrom
	 */
	public Info(String pathFrom) {
		this.content_FR = new File(pathFrom + "/" + FileManager.PRESENTATION_FR);
		this.content_EN = new File(pathFrom + "/" + FileManager.PRESENTATION_EN);
		ArrayList<File> tmpPicture = FileTools.ListFolderPictures(pathFrom);
		if(!tmpPicture.isEmpty())
			this.picture = tmpPicture.get(0);
		this.photos = FileTools.ListFolderPictures(pathFrom + "/" + FileManager.PHOTOS);
	}
	
	public File getContent_FR() {
		return content_FR;
	}
	public void setContent_FR(File content_FR) {
		this.content_FR = content_FR;
	}
	public File getContent_EN() {
		return content_EN;
	}
	public void setContent_EN(File content_EN) {
		this.content_EN = content_EN;
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

}
