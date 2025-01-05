package reflection.data;

public class GameConfiguration {
	private int release;
	private String name;
	private double price;
	
	public int getRelease() {
		return release;
	}

	public String getName() {
		return name;
	}

	public double getPrice() {
		return price;
	}

	@Override
	public String toString() {
		return "GameConfiguration [release=" + release + ", name=" + name + ", price=" + price + "]";
	}
}