package reflection.data;

public class UserInterfaceConfiguration {
	private String titleColor;
	private String titleFontSize;
	private String titleText;
	private String footerFontSize;
	
	public String getTitleColor() {
		return titleColor;
	}
	public String getTitleFontSize() {
		return titleFontSize;
	}
	public String getTitleText() {
		return titleText;
	}
	public String getFooterFontSize() {
		return footerFontSize;
	}
	
	@Override
	public String toString() {
		return "UserInterfaceConfiguration [titleColor=" + titleColor + ", titleFontSize=" + titleFontSize
				+ ", titleText=" + titleText + ", footerFontSize=" + footerFontSize + "]";
	}
}