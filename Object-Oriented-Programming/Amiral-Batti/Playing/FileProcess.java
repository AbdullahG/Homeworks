package Playing;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.StringTokenizer;


public class FileProcess {
	File file=new File("AmiralBattiInfo.txt");
	Scanner scanFile=null;
	ArrayList<String[]> fileBuffer=new ArrayList<>();
	PrintWriter pWriter=null;
	public FileProcess()
	{
		readFileToBuffer();
	}
	public String searchUser(String userName)
	{
		for (int i = 0; i < fileBuffer.size(); i++) {
			if(fileBuffer.get(i)[0].equals(userName))
			{
				return fileBuffer.get(i)[1];
			}
		}
		return "doesNotExist";
	}
	
	public int getUserLevel(String levelStr)
	{
		String temp =levelStr;
		String temp2;
		String temp3;
		int level;
		StringTokenizer st=new StringTokenizer(temp,"(");
		temp2=st.nextToken();
		
		switch (temp2) {
		case "easy":
			temp3=st.nextToken();
			level=Integer.parseInt(temp3.replaceAll("[\\D]", ""));
			return level;
		case "normal": 
			temp3=st.nextToken();
			level=Integer.parseInt(temp3.replaceAll("[\\D]", ""));
			return 2+level;
		case "hard":
			temp3=st.nextToken();
			level=Integer.parseInt(temp3.replaceAll("[\\D]", ""));
			return 5+level;
		default:
			break;
		}
		return -1;
	}
	public Boolean openFile()
	{
		while(true)
		{
			try {
				this.scanFile=new Scanner(file);
				return true;
				} catch (Exception e) {
					try {
						pWriter=new PrintWriter(file);
						System.out.println("File could not found.So, new AmiralBattiInfo.txt has been created on project workspace.");
						pWriter.close();
					} catch (FileNotFoundException e1) {
						System.out.println("File could not found and could not created.");
						System.out.println("The program will exit.");
						System.exit(0);
						return false;
						}
				}
		}
	}
	public void readFileToBuffer()
	{
		this.openFile();
		String[] tempStr;
		while(scanFile.hasNext())
		{
			tempStr=new String[2];
			tempStr[0]=scanFile.next();
			tempStr[1]=scanFile.next();
			fileBuffer.add(tempStr);
		}
	}
	
	public void writeBufferToFile()
	{
		openFile();
		try {
			pWriter=new PrintWriter(file);
		} catch (Exception e) {
			System.out.println("File could open as PrintWriter..");
		}
		pWriter.write("");
		for (int i = 0; i < fileBuffer.size(); i++) {
			pWriter.println(fileBuffer.get(i).clone()[0]+" "+fileBuffer.get(i).clone()[1]);
		}
		pWriter.close();
	}
	
	public void updateFile()
	{
		this.writeBufferToFile();
	}
	
}
