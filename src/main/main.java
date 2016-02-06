package main;

import files.FileFactory;

public class main {

	public static void main(String[] args) {
			
		String dir1 = "totot";
		String file1 = dir1 + "/" + "totott.txt";
		String dir2 = "toto-rasse.txt";
		
		FileFactory.CreateDirectory(dir1);
		FileFactory.CreateFile(file1);
		FileFactory.Write(file1, "salut rasse");
		String output = FileFactory.Read(file1);
		FileFactory.Move(file1, dir2);
		System.out.println(output);
	}

}
