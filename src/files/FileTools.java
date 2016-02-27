package files;

import org.apache.commons.io.FileUtils;

import entities.Location;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

public class FileTools {
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
		if(dir.exists()) {
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
	public static String Read(String path) {
		String output = null;
        File file = new File(path);
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
	public static void Write(String path, String input) {
		  Writer writer = null;
		  try {
		      writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(path), "utf-8"));
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
	
	// List files and directories from a directory
	public static void ListVisitContent(String pathFrom, Location l) {
		File fileFrom = new File(pathFrom);
        File[] list = fileFrom.listFiles();
        for(File file : list){
            if(file.isDirectory()){
                l.addVisit(file.getName());
                System.out.println("Dossier: " + file.getName());
            }
            if(file.isFile()){
                System.out.println("Fichier: " + file.getName());
            }
        }
	}
	// Gros bordel ici ... enregistrer le fichier le plus profond puis remonter et add la visite contenant deja tout le contenu...
	// List files and directories from a directory
    public static void ListInterestPointContent(String pathFrom, Location l) {
        File fileFrom = new File(pathFrom);
        File[] list = fileFrom.listFiles();
        for(File file : list){
            if(file.isDirectory()){
                //l.getV().addInterestPoint(file.getName());
                System.out.println("Dossier: " + file.getName());
            }
            if(file.isFile()){
                System.out.println("Fichier: " + file.getName());
            }
        }
    }
}
