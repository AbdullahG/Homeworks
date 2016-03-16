package Level;

import Equipments.GunShot;
import Equipments.HandBomb;
import Equipments.Rocket;

public class Normal extends Level {
	
	public Normal() {
		this.grid=new String[15][15];
		this.secretGrid=new String[15][15];
		fillTheGrids();
		setEquipments();
		placeShips();
	}

	public void setEquipments() {
		equipments.add(new Rocket());
		equipments.add(new HandBomb());
		equipments.add(new HandBomb());
		equipments.add(new HandBomb());
		
		for (int i = 0; i < 10; i++) {
			equipments.add(new GunShot());
		}
	}

	public void placeShips() {
		placeShip(boat1);
		placeShip(boat2);
		placeShip(submarine);
		placeShip(destroyer);
		placeShip(battleShip);
	}

	

}
