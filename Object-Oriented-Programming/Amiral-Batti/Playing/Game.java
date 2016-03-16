package Playing;
import java.util.ArrayList;
import java.util.Scanner;

import Equipments.Equipment;
import Equipments.GunShot;
import Equipments.HandBomb;
import Equipments.Rocket;
import Level.*;
import Playing.User;


public class Game {
	int level;
	Level gameMode=null;
	User user=null;
	Scanner scanKeyboard=new Scanner(System.in);
	ArrayList<Rocket> rockets=new ArrayList<>();
	ArrayList<HandBomb> handBombs=new ArrayList<>();
	ArrayList<GunShot> gunShots=new ArrayList<>();
	
	public Game(User user)
	{
		this.user=user;
		this.level=user.level;
		
		if(level>=0 && level <2)
			gameMode=new Easy();
		
		else if(level>=2 && level<5)
			gameMode=new Normal();
		
		else if(level>=5)
			gameMode=new Hard();
		
		setEquipments();
	}
	public void play()
	{
		int selection=0;
		int[][] coordinates=new int[1][2];
		while(true)
		{
			gameMode.printTheGrid();
			selection=selectEquipment();
			coordinates=determinePosition(selection);
			shoot(coordinates[0][0], coordinates[0][1] ,selection);
			if(!isThereAnyEquipment() && isThereAnyShip())
			{
				gameMode.printTheGrid();
				System.out.println("All the equipments are used. Game Over.");
				break;
			}
			else if(!isThereAnyShip())
			{
				gameMode.printTheGrid();
				user.level++;
				user.updateUserInfo();
				System.out.println("Congratulations! You have completed the level-"+user.getLevelAsString());
				break;
			}
		}
	}
	
	public int selectEquipment()
	{
		int selection=0;
		
		while(true)
		{
			System.out.println("Equipments:");
		    System.out.println("1.Rocket("+rockets.size()+")");
			System.out.println("2.Hand Bomb("+handBombs.size()+")");
			System.out.println("3.Gun Shot("+gunShots.size()+")");
			try {
				selection=scanKeyboard.nextInt();
			} catch (Exception e) {
				System.out.println("Please enter 1,2 or 3 to select equipment");
				scanKeyboard.nextLine();
			}
			if(selection==1 && rockets.size()>0)
			return selection;
			else if(selection==2 && handBombs.size()>0)
				return selection;
			else if(selection==3 && gunShots.size()>0)
				return selection;
			else if(selection==1 || selection==2 || selection==3)
				System.out.println("You don't have this equipment. Choose another one.");
			else
				System.out.println("You made a wrong choice. Please try again.");
		}
	}
	
	public int[][] determinePosition(int selection)
	{
		int[][] tempCoordinate=new int[1][2];
		
		while(true)
		{
			try {
				System.out.println("Enter Position(x,y): ");
				int x=tempCoordinate[0][0]=scanKeyboard.nextInt();
				int y=tempCoordinate[0][1]=scanKeyboard.nextInt();
				
				if(x<gameMode.grid.length && y<gameMode.grid[0].length)
					switch(selection)
					{
					case 1:
						if((x>0 && x+1<gameMode.grid.length) && (y>0 && y+1<gameMode.grid[0].length))
							return tempCoordinate;
						else
							System.out.println("The position is not valid for rocket.");
						break;
					case 2:
						if(y>0 && y+1<gameMode.grid[0].length)
							return tempCoordinate;
						else
							System.out.println("The position is not valid for hand bomb");
						break;
					case 3:
						return tempCoordinate;
					}
				else
					System.out.println("The position is invalid. Please try again.");
			} catch (Exception e) {
				System.out.println("Please enter the coordinate as integer. For example: 3[enter] 5[enter]");
				scanKeyboard.nextLine();
			}
		}
	}
	
	public void setEquipments()
	{
		for (int i = 0; i < gameMode.equipments.size(); i++) {
			if(gameMode.equipments.get(i) instanceof Rocket)
				rockets.add((Rocket) gameMode.equipments.get(i));
			else if(gameMode.equipments.get(i) instanceof HandBomb)
				handBombs.add((HandBomb) gameMode.equipments.get(i));
			else
				gunShots.add((GunShot) gameMode.equipments.get(i));
		}
	}
	
	
	public void shoot(int x, int y, int equipment)
	{
		switch (equipment) {
		case 1:
			gameMode.equipments.remove(0);
			rockets.remove(0);
			
			shotCoordinate(x, y);
			shotCoordinate(x+1, y);
			shotCoordinate(x-1, y);
			shotCoordinate(x, y+1);
			shotCoordinate(x, y-1);
			break;
		case 2:
			gameMode.equipments.remove(0);
			handBombs.remove(0);
			
			shotCoordinate(x, y);
			shotCoordinate(x, y+1);
			shotCoordinate(x, y-1);
			break;
		case 3:
			gameMode.equipments.remove(0);
			gunShots.remove(0);
			
			shotCoordinate(x, y);
			break;
		}
	}
	
	public boolean isThereAnyEquipment()
	{
		if(gameMode.equipments.size()>0)
			return true;
		else
			return false;
	}
	
	public boolean isThereAnyShip()
	{
		for (int i = 0; i < gameMode.ships.size(); i++) {
			if(!gameMode.ships.get(i).isSunk)
				return true;
		}
		return false;
	}
	
	public void shotCoordinate(int x, int y)
	{
		if(gameMode.secretGrid[x][y].equals("*"))
		{
			gameMode.secretGrid[x][y]="S";
			gameMode.grid[x][y]="S";
		}
		else if(gameMode.secretGrid[x][y].equals("."))
		{
			gameMode.secretGrid[x][y]="-";
			gameMode.grid[x][y]="-";
		}
		
		for (int i = 0; i < gameMode.ships.size(); i++) {
			for (int j = 0; j < gameMode.ships.get(i).coordinates.length; j++) {
				if(gameMode.ships.get(i).coordinates[j][0]==x && gameMode.ships.get(i).coordinates[j][1]==y)
					if(gameMode.ships.get(i).parts[j].equals("*"))
						gameMode.ships.get(i).parts[j]="S";
			}
		}
		
		for (int i = 0; i < gameMode.ships.size(); i++) {
			for (int j = 0; j < gameMode.ships.get(i).parts.length; j++) {
				if(gameMode.ships.get(i).parts[j].equals("*"))
					break;
				if(j==(gameMode.ships.get(i).parts.length-1) && gameMode.ships.get(i).isSunk==false)
				{
					for (int j2 = 0; j2 < gameMode.ships.get(i).parts.length; j2++) {
						gameMode.ships.get(i).parts[j2]="x";
					}
					gameMode.ships.get(i).isSunk=true;
					System.out.println("You just sank a "+gameMode.ships.get(i));
				}
			}
		}
		
		for (int i = 0; i < gameMode.ships.size(); i++) {
			if(gameMode.ships.get(i).isSunk)
			{
				for (int j = 0; j < gameMode.ships.get(i).coordinates.length; j++) {
					gameMode.grid[gameMode.ships.get(i).coordinates[j][0]][gameMode.ships.get(i).coordinates[j][1]]="x";
					gameMode.secretGrid[gameMode.ships.get(i).coordinates[j][0]][gameMode.ships.get(i).coordinates[j][1]]="x";
				}
			}
		}
	}
}
