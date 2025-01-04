package reflection.data;

public class Film {
	private final String name;
	private final float rating;
	private final String[] categories;
	private final Cast[] casts;
	
	public Film(String name, float rating, String[] categories, Cast[] casts) {
		this.name = name;
		this.rating = rating;
		this.categories = categories;
		this.casts = casts;
	}	
}