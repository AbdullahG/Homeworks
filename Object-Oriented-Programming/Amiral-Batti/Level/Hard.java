package Level;

import Equipments.GunShot;
import Equipments.HandBomb;
import Equipments.Rocket;

public class Hard extends Level {

	public Hard() {
		this.grid=new String[20][20];
		this.secretGrid=new String[20][20];
		fillTheGrids();
		setEquipments();
		placeShips();
	}

	public void setEquipments() {
		equipments.add(new Rocket());
		
		for (int i = 0; i < 4; i++) {  // add 4 handbomb
			equipments.add(new HandBomb());
		}
		
		for (int i = 0; i < 12; i++) {   // add 12 gunshot
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
