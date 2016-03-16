package Level;

import java.util.ArrayList;
import java.util.Random;

import Equipments.Equipment;
import Ships.*;

public abstract class Level {
	
	public String[][] grid=null;
	public String[][] secretGrid=null;
	
	int x=0;
	int y=0;
	Random random=new Random();
	public ArrayList<Equipment> equipments=new ArrayList<>();
	public ArrayList<Ship> ships=new ArrayList<>();
	Boat boat1=new Boat();
	Boat boat2=new Boat();
	Submarine submarine=new Submarine();
	Destroyer destroyer=new Destroyer();
	BattleShip battleShip=new BattleShip();
	
	abstract public void setEquipments();
	abstract public void placeShips();
	
	public void printTheGrid()
	{
		System.out.print("  ");
		for (int i = 0; i < grid.length; i++) {
			System.out.printf("%3d",i);
		}
		System.out.println();
		for (int i = 0; i < grid.length; i++) {
			System.out.printf("%2d", i);
			for (int j = 0; j < grid[i].length ; j++) {
				System.out.printf("%3s",grid[i][j]);
			}
			System.out.println();
		}
		
	}
	
	public void fillTheGrids()
	{
		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[i].length ; j++) {
				grid[i][j]=".";
			}
		}
		
		for (int i = 0; i < secretGrid.length; i++) {
			for (int j = 0; j < secretGrid[i].length ; j++) {
				secretGrid[i][j]=".";
			}
		}
	}
	
	public void placeShip(Ship ship)
	{
		ships.add(ship);
		int decision=random.nextInt(2);
		if(decision==0)
			placeAsHorizontal(ship);
		else
			placeAsVertical(ship);
	}
	
	public void placeAsHorizontal(Ship ship)
	{
		while(true)
		{
			x=random.nextInt(grid.length);
			y=random.nextInt(grid.length);
			
			if(secretGrid[x][y].equals("."))
			{
				if(secretGrid[x].length>(y+ship.parts.length)) // if the outOfBoundary Exception will not occur
				for (int i = 0; i < ship.parts.length; i++) { 
					if(!secretGrid[x][y+i].equals("."))  // control whether the coordinates are suitable(empty) or not.
						break;     // If a coordinate is not suitable, then break and change the reference coordinate.
					
					if(i==(ship.parts.length-1))   // place the ship if the coordinate is suitable
						for (int j = 0; j < ship.parts.length; j++) {
							secretGrid[x][y+j]="*";
							ship.coordinates[j][0]=x;
							ship.coordinates[j][1]=y+j;
						}
				}
			}
			if(secretGrid[x][y].equals("*")) // if the ship is placed
				break;
		}
		
	}
	
	public void placeAsVertical(Ship ship)
	{
		while(true)
		{
			x=random.nextInt(grid.length);
			y=random.nextInt(grid.length);
			
			if(secretGrid[x][y].equals("."))
			{
				if(secretGrid.length>(x+ship.parts.length)) // if the outOfBoundary Exception will not occur
				for (int i = 0; i < ship.parts.length; i++) { 
					if(!secretGrid[x+i][y].equals("."))  // control whether the coordinates are suitable or not.
						break;     // If a coordinate is not suitable, then break and change the reference coordinate.
					
					if(i==(ship.parts.length-1))   // place the ship if the coordinate is suitable
						for (int j = 0; j < ship.parts.length; j++) {
							secretGrid[x+j][y]="*";
							ship.coordinates[j][0]=x+j;
							ship.coordinates[j][1]=y;
						}
				}
				if(secretGrid[x][y].equals("*")) // if the ship is placed
					break;
			}
			
		}
	}
}
