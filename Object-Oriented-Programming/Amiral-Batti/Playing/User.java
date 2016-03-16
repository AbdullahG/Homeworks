package Playing;

import java.io.FilePermission;

public class User {
	String name;
	public int level;
	public static FileProcess fProcess=new FileProcess();
	public User(String name)
	{
		this.name=name;
		if(isExist(name))
			this.level=fProcess.getUserLevel(name);
		else
			createUser(name);
	}
	
	public void updateUserInfo()
	{
		for (int i = 0; i < fProcess.fileBuffer.size(); i++) {
			if(fProcess.fileBuffer.get(i)[0].equals(name))
				fProcess.fileBuffer.get(i)[1]=getLevelAsString();
		}
	}
	public void loginUserAndSetLevel()
	{
		fProcess.openFile();
		level=fProcess.getUserLevel(fProcess.searchUser(this.name));
		
	}
	
	public void createUser(String name)
	{
		if(isExist(name))
			name=name+"(2)";
		
		fProcess.openFile();
		String[] tempStr=new String[2];
		tempStr[0]=name;
		tempStr[1]="easy(0)";
		fProcess.fileBuffer.add(tempStr);
		fProcess.updateFile();
	}
	
	public static boolean isExist(String name)
	{
		if(fProcess.searchUser(name).equals("doesNotExist"))
			return false;
		else 
			return true;
	}
	
	public String getLevelAsString()
	{
		if(level>=0 && level<=2)
			return "easy("+level+")";
		else if(level>2 && level<=5)
			return "normal("+(level-2)+")";
		else
			return "hard("+(level-5)+")";
	}
}
