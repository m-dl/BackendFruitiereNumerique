package main;

public class main {

	public static void main(String[] args) {
			
		String file1 = "toto.txt";
		
		FileFactory.CreateFile(file1);
		FileFactory.Write(file1, "salut rasse");
		String output = FileFactory.Read(file1);
		System.out.println(output);


		//ça fait un merge conflict izi

	}

}
