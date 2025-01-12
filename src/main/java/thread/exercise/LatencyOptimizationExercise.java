package thread.exercise;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

public class LatencyOptimizationExercise {
	public static final String SOURCE_FILE = "resources/game-properties.cfg";
	public static final String DESTINATION_FILE = "output/game-properties.cfg";	
	
	public static void Main(String[] args) throws IOException {
		BufferedImage originalImage = ImageIO.read(new File(SOURCE_FILE));
		BufferedImage resultImage = new BufferedImage(originalImage.getWidth(), originalImage.getHeight(), BufferedImage.TYPE_INT_RGB);
	
		long startTime = System.currentTimeMillis();
		//recolorSingleThreaded(originalImage, resultImage);
		
		int threadNum = 4;
		recolorMultiThreaded(originalImage, resultImage, threadNum);
		long finishTime = System.currentTimeMillis();
		
		long duration = finishTime - startTime;
			
		File outputFile = new File(DESTINATION_FILE);
		ImageIO.write(resultImage, "jpg", outputFile);
		
		System.out.println(String.valueOf(duration));
	}
	
	public static void recolorSingleThreaded(BufferedImage originalImage, BufferedImage resultImage) {
		recolorImage(originalImage, resultImage, 0, 0, originalImage.getWidth(), originalImage.getHeight());
	}
	
	public static void recolorMultiThreaded(BufferedImage originalImage, BufferedImage resultImage, int threadNum) {
		List<Thread> threads = new ArrayList<Thread>();
		int width = originalImage.getWidth();
		int height = originalImage.getHeight();
		
		for (int i = 0; i < threadNum; i++) {
			final int threadMultiplier = i;
			
			Thread thread = new Thread(() -> {
				int leftCorner = 0;
				int topCorner = height * threadMultiplier;
				
				recolorImage(originalImage, resultImage, leftCorner, topCorner, width, height);
			});
			
			threads.add(thread);
		}
		
		for (Thread thread: threads) {
			thread.start();
		}
		
		for (Thread thread: threads) {
			try {
				thread.join();
			} catch (InterruptedException exception) {
			}
		}
	}
	
	public static void recolorImage(BufferedImage originalImage, BufferedImage resultImage, int leftCorner, int topCorner, int width, int height) {
		for (int x = leftCorner; x < leftCorner + width && x < originalImage.getWidth(); x++) {
			for (int y = topCorner; y < topCorner + height && y < originalImage.getHeight(); y++) {
				recolorPixel(originalImage, resultImage, x, y);
			}
		}
	}
	
	public static void recolorPixel(BufferedImage originalImage, BufferedImage resultImage, int x, int y) {
		int rgb = originalImage.getRGB(x, y);
		
		int red = getRed(rgb);
		int green = getGreen(rgb);
		int blue = getBlue(rgb);
		
		int recoloredRed;
		int recoloredGreen;
		int recoloredBlue;
		
		if (isGreyShade(red, green, blue)) {
			recoloredRed = Math.min(255,  red + 10);
			recoloredGreen = Math.min(0,  green - 80);
			recoloredBlue = Math.min(0,  blue - 20);
		} else {
			recoloredRed = red;
			recoloredGreen = green;
			recoloredBlue = blue;
		}
		
		int recoloedRgb = createRgb(recoloredRed, recoloredGreen, recoloredBlue);
		setRgb(resultImage, x, y, recoloedRgb);
	}
	
	public static void setRgb(BufferedImage image, int x, int y, int rgb) {
		image.getRaster().setDataElements(x, y, image.getColorModel().getDataElements(rgb, null));
	}
	
	public static boolean isGreyShade(int red, int green, int blue) {
		return Math.abs(red - green) < 30 && Math.abs(red - blue) < 30 && Math.abs(green - blue) < 30;
	}
	
	public static int createRgb(int red, int green, int blue) {
		int rgb = 0;
		
		rgb |= blue;
		rgb |= green << 8;
		rgb |= red << 16;
		
		rgb |= 0xFF000000;
		
		return rgb;
	}
	
	public static int getRed(int rgb) {
		return (rgb & 0x00FF0000) >> 16;
	}
	
	public static int getGreen(int rgb) {
		return (rgb & 0x0000FF00) >> 8;
	}
	
	public static int getBlue(int rgb) {
		return rgb & 0x000000FF;
	}
}