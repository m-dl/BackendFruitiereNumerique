package files;

import gui.Controller.GUIFormsController;
import org.apache.commons.io.FileUtils;

import entities.village.Road;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

/**
 * @author Maxime
 */
public class FileTools {
	public static final Pattern WINDOWS_FILES_FORMATS = Pattern.compile(
	        "# Match a valid Windows filename (unspecified file system).          \n" +
	        "^                                # Anchor to start of string.        \n" +
	        "(?!                              # Assert filename is not: CON, PRN, \n" +
	        "  (?:                            # AUX, NUL, COM1, COM2, COM3, COM4, \n" +
	        "    CON|PRN|AUX|NUL|             # COM5, COM6, COM7, COM8, COM9,     \n" +
	        "    COM[1-9]|LPT[1-9]            # LPT1, LPT2, LPT3, LPT4, LPT5,     \n" +
	        "  )                              # LPT6, LPT7, LPT8, and LPT9...     \n" +
	        "  (?:\\.[^.]*)?                  # followed by optional extension    \n" +
	        "  $                              # and end of string                 \n" +
	        ")                                # End negative lookahead assertion. \n" +
	        "[^<>:\"/\\\\|?*\\x00-\\x1F]*     # Zero or more valid filename chars.\n" +
	        "[^<>:\"/\\\\|?*\\x00-\\x1F\\ .]  # Last char is not a space or dot.  \n" +
	        "$                                # Anchor to end of string.            ", 
	        Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE | Pattern.COMMENTS);
	
	public static final Pattern GPS = Pattern.compile(
			"([+-]?\\d+\\.?\\d+)\\s*,\\s*([+-]?\\d+\\.?\\d+)", 
	        Pattern.CASE_INSENSITIVE);
	
    public static final String[] IMAGES_EXTENSIONS = new String[]{
        "gif", "png", "bmp", "jpeg", "jpg", "GIF", "PNG", "BMP", "JPEG", "JPG"
    };
    
    public static final FileChooser.ExtensionFilter IMAGES_FILE_FILTER = new FileChooser.ExtensionFilter("(Fichiers images)", "*.gif", "*.png", "*.bmp", "*.jpeg", "*.jpg", "*.GIF", "*.PNG", "*.BMP", "*.JPEG", "*.JPG");

    public static final FilenameFilter IMAGE_FILTER = new FilenameFilter() {
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
    
    public static final String[] VIDEOS_EXTENSIONS = new String[]{
        "mp4", "avi", "mkv", "webm", "mov", "MP4", "AVI", "MKV", "WEBM", "MOV"
    };
    
    public static final FileChooser.ExtensionFilter VIDEOS_FILE_FILTER = new FileChooser.ExtensionFilter("(Fichiers vidéos)", "*.mp4", "*.avi", "*.mkv", "*.webm", "*.mov", "*.MP4", "*.AVI", "*.MKV", "*.WEBM", "*.MOV");
    
    public static final FilenameFilter VIDEO_FILTER = new FilenameFilter() {
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
    
    public static final FileChooser.ExtensionFilter ALL_FILE_FILTER = new FileChooser.ExtensionFilter("*", "*.*");
    
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
		     try {writer.close();} catch (Exception ex) {
				 GUIFormsController.getInstance().displayExceptionAlert(ex,"Erreur d'écriture de fichier").showAndWait();

			 }
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
			GUIFormsController.getInstance().displayExceptionAlert(e,"Erreur de suppression de fichier").showAndWait();

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
			GUIFormsController.getInstance().displayExceptionAlert(e,"Erreur de copie de répertoire").showAndWait();

		}
	}
	
	// Copy a file
	public static void CopyFile(String pathFrom, String pathTo) {
		File fileFrom = new File(pathFrom);
		File fileTo = new File(pathTo);
		try {
			FileUtils.copyFile(fileFrom, fileTo, false);
		} catch (IOException e) {
			e.printStackTrace();
			GUIFormsController.getInstance().displayExceptionAlert(e,"Erreur de copie de fichier").showAndWait();

		}
	}
	
    // Rename a file / directory
    public static boolean Rename(String pathFrom, String pathTo) {
        Path fileFrom = Paths.get(pathFrom);
        Path fileTo = Paths.get(pathTo);
        File tmpf = new File(pathFrom);
        if(tmpf.exists()) {
	        try {
	            Files.move(fileFrom, fileTo);
	            return true; // success
	        } catch (IOException e) {
	            e.printStackTrace();
				GUIFormsController.getInstance().displayExceptionAlert(e,"erreur de rennomage").showAndWait();

			}
        }
        return false; // not renamed
    }
	
	// Check if folder or file exists
	public static boolean Exist(File f) {
		if(f.exists())
			return true;
		return false;
	}
	
    // Directory chooser
    public static File DirectoryChooser(){
    	DirectoryChooser directoryChooser = new DirectoryChooser();
    	directoryChooser.setTitle("Sélectionner un dossier");
    	return directoryChooser.showDialog(null);
    }
    
    // File chooser
    public static File FileChooser(FileChooser.ExtensionFilter extFilter){
    	if(extFilter == null)
    		extFilter = FileTools.ALL_FILE_FILTER;
    	FileChooser fileChooser = new FileChooser();
    	fileChooser.getExtensionFilters().add(extFilter);
    	fileChooser.setTitle("Sélectionner un fichier " + extFilter.getDescription());
    	return fileChooser.showOpenDialog(null);
    }
    
    // Multiple File chooser
    public static ArrayList<File> MultipleFileChooser(FileChooser.ExtensionFilter extFilter){
    	if(extFilter == null)
    		extFilter = FileTools.ALL_FILE_FILTER;
    	FileChooser fileChooser = new FileChooser();
    	fileChooser.getExtensionFilters().add(extFilter);
    	fileChooser.setTitle("Sélectionner des fichiers " + extFilter.getDescription());
    	return new ArrayList<File>(fileChooser.showOpenMultipleDialog(null));
    }
    
    // Remove file extension
    public static String RemoveExtension(String s) {
    	int pos = s.lastIndexOf(".");
    	if (pos > 0) {
    	    s = s.substring(0, pos);
    	}
    	return s;
    }
    
    // Get file extension
    public static String GetExtension(String s) {
    	int i = s.lastIndexOf('.');
    	if (i > 0) {
    	    return s.substring(i);
    	}
    	return "";
    }
    
    // Get file extension without '.'
    public static String GetShortExtension(String s) {
    	int i = s.lastIndexOf('.');
    	if (i > 0) {
    	    return s.substring(i+1);
    	}
    	return "";
    }
	
    // String To lower case
    public static String StringToLower(String input) {
    	return input.toLowerCase(); // to lower case
    }
    
    // Clear string double spaces / line jump
    public static String StringClearSpaces(String input) {
    	return input.replaceAll("\\s+"," "); // removes spaces / line jump
    }
    
    // Clear string all spaces / line jump
    public static String StringClearAllSpaces(String input) {
    	return input.replaceAll("\\s+",""); // removes spaces / line jump
    }
    
	// List all pictures from a folder
	public static ArrayList<File> ListFolderPictures(String p) {
		ArrayList<File> picturesList = new ArrayList<File>();
		File pathFrom = new File(p);
		if(!Exist(pathFrom)) 
			CreateDirectory(p);
		File[] list = pathFrom.listFiles(IMAGE_FILTER);
		for(File f : list) {
        	// add picture to arraylist
			picturesList.add(f);
        }
		return picturesList;
	}
	
	// List all 360 from a folder
	public static ArrayList<File> ListFolder360(String p) {
		ArrayList<File> _360List = new ArrayList<File>();
		File pathFrom = new File(p);
		if(!Exist(pathFrom)) 
			CreateDirectory(p);
		File[] list = pathFrom.listFiles(IMAGE_FILTER);
		for(File f : list) {
			if(f.getName().contains(FileManager._360_)) {
	        	// add 360 to arraylist
				_360List.add(f);
			}
        }
		return _360List;
	}
	
	// List all videos from a folder
	public static ArrayList<File> ListFolderVideos(String p) {
		ArrayList<File> videosList = new ArrayList<File>();
		File pathFrom = new File(p);
		if(!Exist(pathFrom)) 
			CreateDirectory(p);
		File[] list = pathFrom.listFiles(VIDEO_FILTER);
		for(File f : list) {
        	// add video to arraylist
			videosList.add(f);
        }
		return videosList;
	}

	// List all Chateau visits from a location
	public static void ListVisitChateau(String pathFrom, entities.chateau.Location l) {
		File fileFrom = new File(pathFrom);
        File[] list = fileFrom.listFiles();
        if(!Exist(fileFrom)) 
        	CreateDirectory(pathFrom);
        for(File file : list){
            if(file.isDirectory()){ // Visits' folder
            	ListVisitContentChateau(pathFrom + "/" + file.getName(), file.getName(), l);
            }
        }
	}
	
	// List a Chateau visit folder content
    public static void ListVisitContentChateau(String pathFrom, String visitName, entities.chateau.Location l) {
        File fileFrom = new File(pathFrom);
        File[] list = fileFrom.listFiles();
        
        if(!Exist(fileFrom)) 
        	CreateDirectory(pathFrom);
        
        entities.chateau.Visit tmpVisit = new entities.chateau.Visit(pathFrom, visitName);
        for(File file : list) {
            if(file.isDirectory()){ // Overview or Info or IP folder
                if(!file.getName().equals(FileManager.OVERVIEW_FOLDER) && !file.getName().equals(FileManager.INFO_FOLDER)) {
                	entities.chateau.InterestPoint tmpIP = new entities.chateau.InterestPoint(pathFrom + "/" + file.getName(), file.getName());
					if(tmpIP.getFloor() == entities.chateau.Location.FLOOR_ONE)
                		tmpVisit.addInterestPoint(tmpIP, tmpVisit.getIP1());
					else if(tmpIP.getFloor() == entities.chateau.Location.FLOOR_TWO)
						tmpVisit.addInterestPoint(tmpIP, tmpVisit.getIP2());
					else if(tmpIP.getFloor() == entities.chateau.Location.FLOOR_THREE)
						tmpVisit.addInterestPoint(tmpIP, tmpVisit.getIP3());
                }
            }
        }
        l.addVisit(tmpVisit);
    }
    
	// List all Village visits from a location
	public static void ListVisitVillage(String pathFrom, entities.village.Location l) {
		File fileFrom = new File(pathFrom);
        File[] list = fileFrom.listFiles();
        if(!Exist(fileFrom)) 
        	CreateDirectory(pathFrom);
        for(File file : list){
            if(file.isDirectory()){ // Visits' folder
            	ListVisitContentVillage(pathFrom + "/" + file.getName(), file.getName(), l);
            }
        }
	}
	
	// List a Village visit folder content
    public static void ListVisitContentVillage(String pathFrom, String visitName, entities.village.Location l) {
        File fileFrom = new File(pathFrom);
        File[] list = fileFrom.listFiles();
        
        if(!Exist(fileFrom)) 
        	CreateDirectory(pathFrom);
        
        entities.village.Visit tmpVisit = new entities.village.Visit(pathFrom, visitName);
        for(File file : list) {
            if(file.isDirectory()){ // Overview or Info or IP folder
                if(!file.getName().equals(FileManager.OVERVIEW_FOLDER) && !file.getName().equals(FileManager.INFO_FOLDER)) {
                	entities.village.InterestPoint tmpIP = new entities.village.InterestPoint(pathFrom + "/" + file.getName(), file.getName());
                	tmpVisit.addInterestPoint(tmpIP);
                }
            }
        }
        l.addVisit(tmpVisit);
    }
    
    // Parse file name
    public static String ParseFileName(String input) {
    	input = StringClearSpaces(input);
    	input = StringToLower(input);
    	Matcher matcher = WINDOWS_FILES_FORMATS.matcher(input);
    	boolean isMatch = matcher.matches();
    
    	if(!isMatch) {
    		input = "";
    		System.out.println("Nom de fichier saisi invalide !");
    	}
        return input;
    }
    
    // Parse GPS coordinates
    public static String ParseCoordinates(String input) {
    	input = StringClearAllSpaces(input);
    	Matcher matcher = GPS.matcher(input);
    	boolean isMatch = matcher.matches();
    
    	if(!isMatch) {
    		input = "";
    		System.out.println("Coordonnées saisies invalides !");
    	}
        return input;
    }
    
    // Read road file
    public static Road ReadRoad(File road) {
    	String width = "", color = "";
		ArrayList<String> coord = new ArrayList<String>();
		Scanner scanner = new Scanner(Read(road));
		while (scanner.hasNextLine()) {
		  String line = scanner.nextLine();
		  if(!ParseCoordinates(line).equals(""))
			  coord.add(line);
		  else if(line.contains(Road.ROAD_WIDTH))
			  width = line.substring(Road.ROAD_WIDTH.length());
		  else if(line.contains(Road.ROAD_COLOR))
			  color = line.substring(Road.ROAD_COLOR.length());
		}
		scanner.close();
		return new Road(coord, width, color);
    }
    
    // Read village marker file
    public static String ReadMarkerVillage(File marker) {
		String coord = "";
		Scanner scanner = new Scanner(Read(marker));
		while (scanner.hasNextLine()) {
		  String line = scanner.nextLine();
		  if(!ParseCoordinates(line).equals("")) {
			  coord = line;
			  break;
		  }
		}
		scanner.close();
		return coord;
    }
    
    // Parse floor and coordinates
    public static void ParseCoordinates(entities.chateau.InterestPoint IP) {
		String input = Read(IP.getMarker());
		if(input != null) {
			String[] lines = input.split(System.getProperty("line.separator"));
			if(lines.length > 1) {
				IP.setFloor(Integer.parseInt(lines[0]));
				String[] coord = lines[1].split(",");
				if(coord.length > 1) {
					IP.setCoordX(Double.parseDouble(coord[0]));
					IP.setCoordY(Double.parseDouble(coord[1]));
				}
			}
		}
    }
}
