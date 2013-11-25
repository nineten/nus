
public class Grid {
	
	private int color = -1;
	public boolean boundary = false;
	public boolean breeze = false;
	public boolean stench = false;
	public boolean glitter = false;
		
	public String previous = "";
	
	public Grid() {
		color = -1;
	}

	public Grid(int value) {
		color = value;
	}	
	
	public int getColor() {
		return color;
	}
	
	public void setColor(int value) {
		color = value;
	}
}
