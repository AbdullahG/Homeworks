import java.util.Scanner;
import java.util.StringTokenizer;

import Playing.Game;
import Playing.User;
import Playing.FileProcess;


public class Main {
	static Scanner scanKeyboard=new Scanner(System.in);
	static User user;
	public static int getChoice()
	{
		int choice=0;
		System.out.println("GAME MENU");
		
		while(true)
		{
			System.out.println("1.Login");
			System.out.println("2.Create new user");
			choice=scanKeyboard.nextInt();
			if(choice==1 || choice==2)
				return choice;
			else
				System.out.println("You made a wrong choice. Please try again..");
		}
		
	}
	
	public static void processChoice(int choice)
	{
		String userName;
		if(choice==1)
		{
			System.out.println("Enter username: ");
			userName=scanKeyboard.next();
			if(user.isExist(userName))
			{
				user=new User(userName);
				user.loginUserAndSetLevel();
			}
			else
			{
				System.out.println("The username is could not found. Please try again.");
				processChoice(getChoice());
			}
		}
		else
		{
			System.out.println("Enter a username: ");
			userName=scanKeyboard.next();
			if(user.isExist(userName))
			{
				System.out.println("The username is already exists. Please try again.");
				processChoice(getChoice());
			}
			else
				user=new User(userName);
		}
	}
	
	public static void main(String[] args)
	{
		processChoice(getChoice());
		
		Game game=new Game(user);
		game.play();
		
		String select="";
		while(true)
		{
			user.level++; //to detect next level.
			System.out.println("Do you want to continue in "+user.getLevelAsString()+" level y/n?");
			user.level--; // to decrease to correct completed level.
			select=scanKeyboard.next();
			
			if(select.equals("y") || select.equals("yes"))
			{
				game=new Game(user);
				game.play();
			}
			
			else if(select.equals("n") || select.equals("no"))
			{
				break;
			}
			
			else
				System.out.println("Wrong choice. Please type (y)es or (n)o");
		}
		user.fProcess.updateFile();
		System.out.println("END OF GAME");
	}
}
