package Ships;

public class Boat extends Ship{
	public Boat() {
		this.parts=new String[1];
		this.coordinates=new int[1][2];
		fillThePartsArray();
	}
	
	public String toString()
	{
		return "Boat";
	}
}
