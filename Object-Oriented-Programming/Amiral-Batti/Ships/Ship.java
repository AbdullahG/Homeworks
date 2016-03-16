package Ships;

public class Ship {
	public String[] parts=null;
	public int[][] coordinates=null;
	public boolean isSunk=false;
	public void fillThePartsArray()
	{
		for (int i = 0; i < parts.length; i++) {
			parts[i]="*";
		}
	}
	
}
