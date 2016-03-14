package entities;

import files.FileTools;
import files.FileManager;

import java.io.File;
import java.util.ArrayList;

/**
 * @author Maxime
 *
 */
public class Overview {    
	private File presentation_FR, presentation_EN, length_FR, length_EN;
	private ArrayList<File> imagesContent, photos;
	
	/**
	 * @param pathFrom
	 */
	public Overview(String pathFrom) {
		this.presentation_FR = new File(pathFrom + "/" + FileManager.PRESENTATION_FR);
		this.presentation_EN = new File(pathFrom + "/" + FileManager.PRESENTATION_EN);
		this.length_FR = new File(pathFrom + "/" + FileManager.LENGTH_FR);
		this.length_EN = new File(pathFrom + "/" + FileManager.LENGTH_EN);
		
		initOverview(pathFrom);
		
		this.photos = FileTools.ListFolderPictures(pathFrom + "/" + FileManager.PHOTOS);
		this.imagesContent = FileTools.ListFolderPictures(pathFrom);
	}
	
	private void initOverview(String pathFrom) {
		if(!FileTools.Exist(this.presentation_FR))
			FileTools.CreateFile(pathFrom + "/" + FileManager.PRESENTATION_FR);
		if(!FileTools.Exist(this.presentation_EN))
			FileTools.CreateFile(pathFrom + "/" + FileManager.PRESENTATION_EN);
		if(!FileTools.Exist(this.length_FR))
			FileTools.CreateFile(pathFrom + "/" + FileManager.LENGTH_FR);
		if(!FileTools.Exist(this.length_EN))
			FileTools.CreateFile(pathFrom + "/" + FileManager.LENGTH_EN);	
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
	
	public String readLength_FR() {
		return FileTools.Read(this.length_FR);
	}
	
	public void writeLength_FR(String input) {
		FileTools.Write(this.length_FR, input);
	}
	
	public String readLength_EN() {
		return FileTools.Read(this.length_EN);
	}
	
	public void writeLength_EN(String input) {
		FileTools.Write(this.length_EN, input);
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
	
	public void addImagesContent(String pathFrom, String f) {
		String newPath = pathFrom + "/" + f;
		FileTools.CopyFile(f, newPath);
		File file = new File(newPath);
		this.imagesContent.add(file);
	}
	
	public void removeImagesContent(String pathFrom, String f) {
		String path = pathFrom + "/" + f;
		FileTools.Delete(path);
		for(int i = 0; i < this.imagesContent.size(); i++) {
    		if(this.imagesContent.get(i).getName().equals(f))
    			this.imagesContent.get(i).delete();
		}
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

	public File getLength_FR() {
		return length_FR;
	}

	public void setLength_FR(File length_FR) {
		this.length_FR = length_FR;
	}

	public File getLength_EN() {
		return length_EN;
	}

	public void setLength_EN(File length_EN) {
		this.length_EN = length_EN;
	}

	public ArrayList<File> getImagesContent() {
		return imagesContent;
	}

	public void setImagesContent(ArrayList<File> imagesContent) {
		this.imagesContent = imagesContent;
	}

	public ArrayList<File> getPhotos() {
		return photos;
	}

	public void setPhotos(ArrayList<File> photos) {
		this.photos = photos;
	}
}
