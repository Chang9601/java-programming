package reflection.exercise;

import java.lang.reflect.Field;

public class FieldExercise {
	public static void main(String[] args) throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException {
		//printDeclaredInfo(Film.class);
		//printDeclaredInfo(Film.FileStatistics.class);
		//printDeclaredInfo(Category.class);
		
		Film film = new Film("살인의 추억", 2003,  true, Category.ACTION, 14.99);
		
		printDeclaredInfo(film.getClass(), film);
		
		Field minimumPriceField = Film.class.getDeclaredField("MINIMUM_PRICE");
		
		System.out.println(String.format("정적 MINIMUM_PRICE 값: %f", minimumPriceField.get(null)));
	}
	
	public static <T> void printDeclaredInfo(Class<? extends T> clazz, T instance) throws IllegalArgumentException, IllegalAccessException {
		for (Field field: clazz.getDeclaredFields()) {
			System.out.println(String.format("필드명: %s 타입: %s", field.getName(), field.getType().getName()));
			System.out.println(String.format("인공 필드? %s", field.isSynthetic()));
			System.out.println(String.format("필드값: %s", field.get(instance)));
			System.out.println();
		}
	}
	
	public static class Film extends Item {
		public static final double MINIMUM_PRICE = 12.99;

		private boolean isReleased;
		private Category category;
		private double actualPrice;
		
		public Film(String name, int year, boolean isReleased, Category category, double price) {
			super(name, year);
			this.isReleased = isReleased;
			this.category = category;
			this.actualPrice = Math.max(price, MINIMUM_PRICE);
		}
		
		public class FileStatistics {
			private double timesWatched;

			public FileStatistics(double timesWatched) {
				this.timesWatched = timesWatched;
			}
			
			public double getRevenue() {
				return timesWatched * actualPrice;
			}
		}
	}

	public static class Item {
		protected String name;
		protected int year;
		protected double actualPrice;
		
		public Item(String name, int year) {
			this.name = name;
			this.year = year;
		}
	}
	
	public enum Category {
		ADVENTURE,
		ACTION,
		COMEDY
	}
}