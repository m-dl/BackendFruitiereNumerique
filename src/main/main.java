package main;

import files.FileFactory;

public class main {

	public static void main(String[] args) {
			// bug de mort boucle infinie copie
		String dir1 = "totot";
		String dir = dir1 + "/totot/";
		String file1 = dir1 + "/" + "totott.txt";
		String file2 = dir + "tt.txt";
		String dir2 = "totorasse.txt";
		
		FileFactory.CreateDirectory(dir1);
		FileFactory.CopyFile(dir2, "tt.txt");
		/*
		FileFactory.CreateDirectory(dir);
		FileFactory.CreateFile(file1);
		FileFactory.CreateFile(file2);
		FileFactory.Write(file1, "salut rasse");
		String output = FileFactory.Read(file1);
		FileFactory.Move(file1, dir2);
		FileFactory.Delete(dir1);
		System.out.println(output);
		*/
	}

}
