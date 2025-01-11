package reflection.data;

import java.util.Arrays;

public class UserInterfaceConfiguration {
	private String titleColor;
	private String[] titleFonts;
	private String titleFontSize;
	private String titleText;
	private short[] titleTextSizes;
	private String footerFontSize;
	
	public String getTitleColor() {
		return titleColor;
	}
	
	public String[] getTitleFonts() {
		return titleFonts;
	}

	public String getTitleFontSize() {
		return titleFontSize;
	}
	
	public String getTitleText() {
		return titleText;
	}

	public short[] getTitleTextSizes() {
		return titleTextSizes;
	}

	public String getFooterFontSize() {
		return footerFontSize;
	}

	@Override
	public String toString() {
		return "UserInterfaceConfiguration [titleColor=" + titleColor + ", titleFonts=" + Arrays.toString(titleFonts)
				+ ", titleFontSize=" + titleFontSize + ", titleText=" + titleText + ", titleTextSizes="
				+ Arrays.toString(titleTextSizes) + ", footerFontSize=" + footerFontSize + "]";
	}
}