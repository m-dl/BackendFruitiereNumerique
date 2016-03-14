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
	private ArrayList<File> photos, interieur, _360, videos;
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
		this.videos = FileTools.ListFolderVideos(pathFrom + "/" + FileManager.VIDEOS);
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
	
	public String readPresentation_FR() {
		return FileTools.Read(this.presentation_FR);
	}
	
	public void writePresentation_FR(String input) {
		FileTools.Write(this.presentation_FR, input);
	}
	
	public String readPresentation_EN() {
		return FileTools.Read(this.presentation_EN);
	}
	
	public void writePresentation_EN(String input) {
		FileTools.Write(this.presentation_EN, input);
	}
	
	public String readMarker() {
		return FileTools.Read(this.marker);
	}
	
	public void writeMarker(String input) {
		FileTools.Write(this.marker, input);
	}
	
	public String readRoad() {
		return FileTools.Read(this.road);
	}
	
	public void writeRoad(String input) {
		FileTools.Write(this.road, input);
	}
	
	public void addPhotos(String pathFrom, String f) {
		String newPath = pathFrom + "/" + FileManager.PHOTOS + "/" + f;
		FileTools.CopyFile(f, newPath);
		File file = new File(newPath);
		this.photos.add(file);
	}
	
	public void removePhotos(String pathFrom, String f) {
		String path = pathFrom + "/" + FileManager.PHOTOS + "/" + f;
		FileTools.Delete(path);
		for(int i = 0; i < this.photos.size(); i++) {
    		if(this.photos.get(i).getName().equals(f))
    			this.photos.get(i).delete();
		}
	}
	
	public void add360(String pathFrom, String f) {
		String newPath = pathFrom + "/" + FileManager._360 + "/" + f;
		FileTools.CopyFile(f, newPath);
		File file = new File(newPath);
		this._360.add(file);
	}
	
	public void remove360(String pathFrom, String f) {
		String path = pathFrom + "/" + FileManager._360 + "/" + f;
		FileTools.Delete(path);
		for(int i = 0; i < this._360.size(); i++) {
    		if(this._360.get(i).getName().equals(f))
    			this._360.get(i).delete();
		}
	}
	
	public void addVideo(String pathFrom, String f) {
		String newPath = pathFrom + "/" + FileManager.VIDEOS + "/" + f;
		FileTools.CopyFile(f, newPath);
		File file = new File(newPath);
		this.videos.add(file);
	}
	
	public void removeVideo(String pathFrom, String f) {
		String path = pathFrom + "/" + FileManager.VIDEOS + "/" + f;
		FileTools.Delete(path);
		for(int i = 0; i < this.videos.size(); i++) {
    		if(this.videos.get(i).getName().equals(f))
    			this.videos.get(i).delete();
		}
	}
	
	public void addInterieur(String pathFrom, String f) {
		String newPath = pathFrom + "/" + FileManager.INTERIEUR + "/" + f;
		FileTools.CopyFile(f, newPath);
		File file = new File(newPath);
		this.interieur.add(file);
	}
	
	public void removeInterieur(String pathFrom, String f) {
		String path = pathFrom + "/" + FileManager.INTERIEUR + "/" + f;
		FileTools.Delete(path);
		for(int i = 0; i < this.interieur.size(); i++) {
    		if(this.interieur.get(i).getName().equals(f))
    			this.interieur.get(i).delete();
		}
	}
	
	public void addPicture(String pathFrom, String f) {
		String newPath = pathFrom + "/" + f;
		FileTools.CopyFile(f, newPath);
		File file = new File(newPath);
		this.picture = file;
	}
	
	public void removePicture(String pathFrom, String f) {
		String path = pathFrom + "/" + f;
		FileTools.Delete(path);
		this.picture = null;
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

	public ArrayList<File> getVideos() {
		return videos;
	}

	public void setVideos(ArrayList<File> videos) {
		this.videos = videos;
	}

	public ArrayList<File> get_360() {
		return _360;
	}

	public void set_360(ArrayList<File> _360) {
		this._360 = _360;
	}
}
