package files;

import gui.Controller.GUIFormsController;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

/**
 * Classe pour les méthodes de création d'archive en ue d'envoi sur GDrive
 */
public class ZipManager {

    /**
     * Fonction pour créer un zip d'un dossier
     *
     * @param srcFolder   le chemin du dossier à zipper
     * @param destZipFile le chemin du zip créé
     * @throws Exception en cas de problème lors de la création du zip
     */
    public static void zipFolder(String srcFolder, String destZipFile) throws Exception {
		ZipOutputStream zip = null;
		FileOutputStream fileWriter = null;

		fileWriter = new FileOutputStream(destZipFile);
		zip = new ZipOutputStream(fileWriter);

		addFolderToZip("", srcFolder, zip);
		zip.flush();
        zip.close();
    }

    /**
     * Ajouter un fichier dans une archive zip
     * @param path le chemin du zip dans lequel ajouter le fichier
     * @param srcFile le chemin du fichier à ajouter
     * @param zip le zip dans lequel ajouter le fichier
     * @throws Exception exception en cas de problème d'ajout
     */
    private static void addFileToZip(String path, String srcFile, ZipOutputStream zip) throws Exception {
		File folder = new File(srcFile);
		if(folder.isDirectory()) {
			if(folder.list().length != 0) {
				addFolderToZip(path, srcFile, zip);
			} else {
				zip.putNextEntry(new ZipEntry(path + "/" + folder.getName() + "/"));
			}
		} else {
			byte[] buf = new byte[1024];
			int len;
			FileInputStream in = new FileInputStream(srcFile);
			zip.putNextEntry(new ZipEntry(path + "/" + folder.getName()));
			while((len = in.read(buf)) > 0) {
				zip.write(buf, 0, len);
			}
            in.close();
        }
    }

    /**
     *  Ajouter un dossier dans une archive zip
     * @param path le chemin du zip dans lequel ajouter le dossier
     * @param srcFolder le chemin du dossier à ajouter
     * @param zip le zip dans lequel ajouter le dossier
     * @throws Exception exception en cas de problème d'ajout
     */
    private static void addFolderToZip(String path, String srcFolder, ZipOutputStream zip) throws Exception {
		File folder = new File(srcFolder);
		for(String fileName : folder.list()) {
			if (path.equals("")) {
				addFileToZip(folder.getName(), srcFolder + "/" + fileName, zip);
			} else {
				addFileToZip(path + "/" + folder.getName(), srcFolder + "/" + fileName, zip);
            }
        }
    }

    /**
     * Fonction pour dezipper le zip
     *
     * @param zipFile le chemin du zip a décompresser
     * @param outputFolder la destionation de la décompression
     */
	public static void unZip(String zipFile, String outputFolder) {
		byte[] buffer = new byte[1024];
		try {
			// create output directory is not exists
			File folder = new File(outputFolder);
			if(!folder.exists()) {
				folder.mkdir();
			}
			// get the zip file content
			FileInputStream fis = new FileInputStream(zipFile);
			ZipInputStream zis = new ZipInputStream(fis);
			// get the zipped file list entry
			ZipEntry ze = zis.getNextEntry();
			while(ze != null) {
				String fileName = ze.getName();
				File newFile = new File(outputFolder + File.separator + fileName);
				if(ze.isDirectory()) {
					new File(newFile.getParent()).mkdirs();
					newFile.mkdir();
				}
				else {
					// create all non exists folders
					// else you will hit FileNotFoundException for compressed folder
					new File(newFile.getParent()).mkdirs();
					FileOutputStream fos = new FileOutputStream(newFile);
					int len;
					while((len = zis.read(buffer)) > 0) {
						fos.write(buffer, 0, len);
					}
					fos.close();
				}
				ze = zis.getNextEntry();
			}
			fis.close();
			zis.closeEntry();
			zis.close();
		} catch (IOException ex) {
			ex.printStackTrace();
			GUIFormsController.getInstance().displayExceptionAlert(ex,"Erreur lors de la décompression du l'archive Zip").showAndWait();

		}
	}
}