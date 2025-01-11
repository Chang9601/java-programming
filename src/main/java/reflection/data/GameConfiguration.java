package reflection.data;

import java.util.Arrays;

public class GameConfiguration {
	private int release;
	private String name;
	private double price;
	private String[] characters;
	
	public int getRelease() {
		return release;
	}

	public String getName() {
		return name;
	}

	public double getPrice() {
		return price;
	}
	
	public String[] getCharacters() {
		return characters;
	}

	@Override
	public String toString() {
		return "GameConfiguration [release=" + release + ", name=" + name + ", price=" + price + ", characters="
				+ Arrays.toString(characters) + "]";
	}
}