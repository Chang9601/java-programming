package reflection.data;

public class Clothing extends Item {
	private Size size;
	private String color;
	
	public Size getSize() {
		return size;
	}

	public String getColor() {
		return color;
	}
	
	public void setSize(Size size) {
		this.size = size;
	}

	public void setColor(String color) {
		this.color = color;
	}	
}