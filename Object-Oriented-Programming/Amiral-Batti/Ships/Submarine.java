package Ships;

public class Submarine extends Ship{

	public Submarine() {
		this.parts=new String[2];
		this.coordinates=new int[2][2];
		fillThePartsArray();
	}
	
	public String toString()
	{
		return "Submarine";
	}
}
