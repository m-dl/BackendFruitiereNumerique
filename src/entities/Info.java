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
		if(!FileTools.Exist(new File(pathFrom)))
            FileTools.CreateDirectory(pathFrom);
		
		this.content_FR = new File(pathFrom + "/" + FileManager.PRESENTATION_FR);
		this.content_EN = new File(pathFrom + "/" + FileManager.PRESENTATION_EN);
		
		initInfo(pathFrom);

		ArrayList<File> tmpPicture = FileTools.ListFolderPictures(pathFrom);
		if(!tmpPicture.isEmpty())
			this.picture = tmpPicture.get(0);
		this.photos = FileTools.ListFolderPictures(pathFrom + "/" + FileManager.PHOTOS);		
	}
	
	private void initInfo(String pathFrom) {
		if(!FileTools.Exist(this.content_FR))
			FileTools.CreateFile(pathFrom + "/" + FileManager.PRESENTATION_FR);
		if(!FileTools.Exist(this.content_EN))
			FileTools.CreateFile(pathFrom + "/" + FileManager.PRESENTATION_EN);	
	}
	
	public String readContent_FR() {
		return FileTools.Read(this.content_FR);
	}
	
	public void writeContent_FR(String input) {
		FileTools.Write(this.content_FR, input);
	}
	
	public String readContent_EN() {
		return FileTools.Read(this.content_EN);
	}
	
	public void writeContent_EN(String input) {
		FileTools.Write(this.content_EN, input);
	}
	
	public void addPhotos(String pathFrom, String pathTo, String f) {
		f = FileTools.StringToLower(f);
		String newPath = pathTo + "/" + FileManager.PHOTOS + "/" + f;
		FileTools.CopyFile(pathFrom, newPath);
		File file = new File(newPath);
		this.photos.add(file);
	}
	
	public void removePhotos(String pathFrom, String f) {
		FileTools.Delete(pathFrom);
		for(int i = 0; i < this.photos.size(); i++) {
    		if(this.photos.get(i).getName().equals(f))
    			this.photos.get(i).delete();
		}
	}
	
	public void addPicture(String pathFrom, String pathTo, String f) {
		f = FileTools.StringToLower(f);
		String newPath = pathTo + "/" + f;
		FileTools.CopyFile(pathFrom, newPath);
		File file = new File(newPath);
		this.picture = file;
	}
	
	public void removePicture(String pathFrom, String f) {
		String path = pathFrom + "/" + f;
		FileTools.Delete(path);
		this.picture = null;
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
