package model;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class CustomFile {
	
	private File file;
	
	public CustomFile (String filename) {
		file = new File(filename);
		createNewFile();
	}
	
	private void createNewFile() {
		try {
			file.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void write(String s) {
		try {
			FileWriter writer = new FileWriter(file, true);
			System.out.println("TESTING" + s + " : " + file.getPath() );
			writer.write(s);
			writer.flush();
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void writeNewLine() {
		try {
			FileWriter writer = new FileWriter(file, true);
			writer.append("\n");
			writer.flush();
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
