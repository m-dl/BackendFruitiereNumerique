package files;

import java.util.*;
import java.io.*;

public class FileFactory {
	
	// LE GPS NEGRO
	public static String Files(String path, String map) {
		try {
		      File file = new File(path);
	 
		      if (file.createNewFile()){ // écrire dans le fichier la map par défaut si le fichier n'existe pas
		    	  Writer writer = null;

		    	  try {
		    	      writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(path), "utf-8"));
		    	      writer.write(map);
		    	  } catch (IOException ex) {
		    	    // erreur
		    	  } finally {
		    	     try {writer.close();} catch (Exception ex) {}
		    	  }
		      }else{ // lire dans le fichier la dernière map chargée
		    	   try {
		    	       FileReader reader = new FileReader(file);
		    	       char[] chars = new char[(int) file.length()];
		    	       reader.read(chars);
		    	       map = new String(chars);
		    	       reader.close();
		    	   } catch (IOException e) {
		    	       e.printStackTrace();
		    	   }
		      }
	    	} catch (IOException e) {
		      e.printStackTrace();
		}
		return map;
	}
}
