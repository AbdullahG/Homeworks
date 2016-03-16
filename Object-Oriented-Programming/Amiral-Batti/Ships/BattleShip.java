package Ships;

public class BattleShip extends Ship{

	public BattleShip() {
		this.parts=new String[4];
		this.coordinates=new int[4][2];
		fillThePartsArray();
	}
	
	public String toString()
	{
		return "Battle Ship";
	}
}
