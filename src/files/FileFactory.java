package files;

import java.io.*;

import static java.nio.file.StandardCopyOption.*;
import java.nio.file.DirectoryNotEmptyException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


public class FileFactory {
	
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
		        System.out.println("ok!");
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
	
	// Move a file
	public static void Move(String pathFrom, String pathTo) {
		try {
			Path fileFrom = Paths.get(pathFrom);
			Path fileTo = Paths.get(pathTo);
			Files.move(fileFrom, fileTo, REPLACE_EXISTING);
		} catch(IOException ioe) {
			ioe.printStackTrace();
		}
	}
	
	// Delete a file
	public static void Delete(String path) {
		Path file = Paths.get(path);
		try {
		  Files.deleteIfExists(file);
		} catch(DirectoryNotEmptyException dnee) {
		  System.err.println("Le repertoire " + file + " n'est pas vide");
		} catch(IOException ioe) {
		  System.err.println("Impossible de supprimer " + file + " : " + ioe);
		}
	}
}
