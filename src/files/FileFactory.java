package files;

import java.util.*;
import java.io.*;

public class FileFactory {
	
	// LE GPS NEGRO
	public static void CreateFile(String path) {
		try {
		      File file = new File(path);
		      if(!file.createNewFile()){ 
		    	  System.out.println("Erreur : Le fichier " + path + " existe déjà !");
		      }
	    	} catch (IOException e) {
		      e.printStackTrace();
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
	    } catch (IOException e) {
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
		  } catch (IOException ex) {
			  ex.printStackTrace();
		  } finally {
		     try {writer.close();} catch (Exception ex) {}
		  }
	}
}
