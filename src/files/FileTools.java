package files;

import org.apache.commons.io.FileUtils;

import entities.InterestPoint;
import entities.Info;
import entities.Overview;
import entities.Visit;
import entities.Location;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

/**
 * @author Maxime
 */
public class FileTools {
    static final String[] IMAGES_EXTENSIONS = new String[]{
        "gif", "png", "bmp", "jpeg", "jpg", "GIF", "PNG", "BMP", "JPEG", "JPG"
    };
    
    static final FilenameFilter IMAGE_FILTER = new FilenameFilter() {
        @Override
        public boolean accept(final File dir, final String name) {
            for (final String ext : IMAGES_EXTENSIONS) {
                if (name.endsWith("." + ext)) {
                    return (true);
                }
            }
            return (false);
        }
    };
    
    static final String[] VIDEOS_EXTENSIONS = new String[]{
        "mp4", "avi", "mkv", "webm", "mov", "MP4", "AVI", "MKV", "WEBM", "MOV"
    };
    
    static final FilenameFilter VIDEO_FILTER = new FilenameFilter() {
        @Override
        public boolean accept(final File dir, final String name) {
            for (final String ext : VIDEOS_EXTENSIONS) {
                if (name.endsWith("." + ext)) {
                    return (true);
                }
            }
            return (false);
        }
    };
    
	// Create a file
	public static void CreateFile(String path) {
		try {
		      File file = new File(path);
		      if(!file.createNewFile()){ 
		    	  System.out.println("Erreur : Le fichier " + path + " existe déjà !");
		      }
	    	} catch(IOException e) {
		      e.printStackTrace();
		}
	}
	
	// Create a directory
	public static void CreateDirectory(String path) {
		File dir = new File(path);
		// if the directory does not exist, create it
		if(Exist(dir)) {
		    System.out.println("Erreur : Le dossier " + path + " existe déjà !");     
		}
		else {
			try {
		        dir.mkdir();
		    } 
		    catch(SecurityException se){
		    	 se.printStackTrace();
		    }    
		}
	}
	
	// Read from a file
	public static String Read(File file) {
		String output = null;
	    try {
	        FileReader reader = new FileReader(file);
	        char[] chars = new char[(int) file.length()];
	        reader.read(chars);
	        output = new String(chars);
	        reader.close();
	    } catch(IOException e) {
	        e.printStackTrace();
	    }
		return output;
	}
	
	// Write in a file
	public static void Write(File file, String input) {
		  Writer writer = null;
		  try {
		      writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), "utf-8"));
		      writer.write(input);
		  } catch(IOException ex) {
			  ex.printStackTrace();
		  } finally {
		     try {writer.close();} catch (Exception ex) {}
		  }
	}
	
	// Move a file / directory
	public static void Move(String pathFrom, String pathTo) {
		try {
			Path fileFrom = Paths.get(pathFrom);
			Path fileTo = Paths.get(pathTo);
			Files.move(fileFrom, fileTo, REPLACE_EXISTING);
		} catch(IOException ioe) {
			ioe.printStackTrace();
		}
	}
	
	// Delete a file / directory
	public static void Delete(String path) {
		File file = new File(path);
		try {
			FileUtils.forceDelete(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	// Copy a directory
	public static void CopyDirectory(String pathFrom, String pathTo) {
		File fileFrom = new File(pathFrom);
		File fileTo = new File(pathTo);
		try {
			FileUtils.copyDirectory(fileFrom, fileTo);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	// Copy a file
	public static void CopyFile(String pathFrom, String pathTo) {
		File fileFrom = new File(pathFrom);
		File fileTo = new File(pathTo);
		try {
			FileUtils.copyFile(fileFrom, fileTo);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	// Rename a file / directory
	public static void Rename(String pathFrom, String pathTo) {
		Path fileFrom = Paths.get(pathFrom);
		Path fileTo = fileFrom.resolveSibling(pathTo);
		try {
			Files.move(fileFrom, fileTo);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	// Check if folder or file exists
	public static boolean Exist(File f) {
		if(f.exists())
			return true;
		return false;
	}
	
	// List all pictures from a folder
	public static ArrayList<File> ListFolderPictures(String p) {
		ArrayList<File> picturesList = new ArrayList<File>();
		File pathFrom = new File(p);
		if(!Exist(pathFrom)) 
			CreateDirectory(p);
		File[] list = pathFrom.listFiles(IMAGE_FILTER);
		for(final File f : list) {
        	// add picture to arraylist
			picturesList.add(f);
        }
		return picturesList;
	}
	
	// List all videos from a folder
	public static ArrayList<File> ListFolderVideos(String p) {
		ArrayList<File> videosList = new ArrayList<File>();
		File pathFrom = new File(p);
		if(!Exist(pathFrom)) 
			CreateDirectory(p);
		File[] list = pathFrom.listFiles(VIDEO_FILTER);
		for(final File f : list) {
        	// add video to arraylist
			videosList.add(f);
        }
		return videosList;
	}

	// List all visits from a location
	public static void ListVisit(String pathFrom, Location l) {
		File fileFrom = new File(pathFrom);
        File[] list = fileFrom.listFiles();
        if(!Exist(fileFrom)) 
        	CreateDirectory(pathFrom);
        for(File file : list){
            if(file.isDirectory()){ // Visits' folder
                ListVisitContent(pathFrom + "/" + file.getName(), file.getName(), l);
            }
        }
	}
	
	// List a visit folder content
    public static void ListVisitContent(String pathFrom, String visitName, Location l) {
        File fileFrom = new File(pathFrom);
        File[] list = fileFrom.listFiles();
        
        if(!Exist(fileFrom)) 
        	CreateDirectory(pathFrom);
        
        Visit tmpVisit = new Visit(pathFrom, visitName);
        for(File file : list) {
            if(file.isDirectory()){ // Overview or Info or IP folder
                if(!file.getName().equals(FileManager.OVERVIEW_FOLDER) && !file.getName().equals(FileManager.INFO_FOLDER)) {
                	InterestPoint tmpIP = new InterestPoint(pathFrom + "/" + file.getName(), file.getName());
                	tmpVisit.addInterestPoint(tmpIP);
                }
                System.out.println("Dossier: " + file.getName());
            }
        }
        l.addVisit(tmpVisit);
    }
}
