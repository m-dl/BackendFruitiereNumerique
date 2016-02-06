package files;

import java.io.*;

import static java.nio.file.StandardCopyOption.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;


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
		Path file = Paths.get(path);
		try {
			Files.walkFileTree(file, new SimpleFileVisitor<Path>() {
			   @Override
			   public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
			       Files.delete(file);
			       return FileVisitResult.CONTINUE;
			   }

			   @Override
			   public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
			       Files.delete(dir);
			       return FileVisitResult.CONTINUE;
			   }
			});
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	// Copy a file / directory
	public static void Copy(String pathFrom, String pathTo) {
		Path fileFrom = Paths.get(pathFrom);
		Path fileTo = Paths.get(pathTo);
		try {
			Files.walkFileTree(fileFrom, new SimpleFileVisitor<Path>() {
			    @Override
			    public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
			        Path targetPath = fileTo.resolve(fileFrom.relativize(dir));
			        if(!Files.exists(targetPath)){
			        	if(!fileFrom.equals(fileTo)) {
			        		Files.createDirectory(targetPath);
			        	}
			        }
			        return FileVisitResult.CONTINUE;
			    }

			    @Override
			    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
			        Files.copy(file, fileTo.resolve(fileFrom.relativize(file)), REPLACE_EXISTING);
			        return FileVisitResult.CONTINUE;
			    }
			});
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
}
