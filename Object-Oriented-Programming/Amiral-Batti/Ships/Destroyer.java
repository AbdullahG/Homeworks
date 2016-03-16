package Ships;

public class Destroyer extends Ship{

	public Destroyer() {
		this.parts=new String[3];
		this.coordinates=new int[3][2];
		fillThePartsArray();
	}
	
	public String toString()
	{
		return "Destroyer";
	}
}
