package Level;

import Equipments.GunShot;
import Equipments.HandBomb;
import Equipments.Rocket;
import Ships.*;

public class Easy extends Level {
	
	public Easy() {
		this.grid=new String[10][10];
		this.secretGrid=new String[10][10];
		fillTheGrids();
		setEquipments();
		placeShips();
	}

	public void setEquipments() {
		equipments.add(new Rocket());
		equipments.add(new HandBomb());
		equipments.add(new HandBomb());
		
		for (int i = 0; i < 7; i++) {
			equipments.add(new GunShot());
		}
	}

	public void placeShips() {
		placeShip(boat1);
		placeShip(submarine);
		placeShip(destroyer);
	}
	

	
}
