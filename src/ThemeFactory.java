import java.awt.Color;

public class ThemeFactory {

	private int rDark;
	private int rLight;
	private int rDefault;

	private int gDark;
	private int gLight;
	private int gDefault;

	private int bDark;
	private int bLight;
	private int bDefault;

	public int getRDark() {
		return rDark;
	}

	public void setRDark(int rValue) {
		this.rDark = rValue;
	}

	public int getRLight() {
		return rLight;
	}

	public void setRLight(int rValue) {
		this.rLight = rValue;
	}

	public int getRDefault() {
		return rDefault;
	}

	public void setRDefault(int rValue) {
		this.rDefault = rValue;
	}

	public int getGDark() {
		return gDark;
	}

	public void setGDark(int gValue) {
		this.gDark = gValue;
	}

	public int getGLight() {
		return gLight;
	}

	public void setGLight(int gValue) {
		this.gLight = gValue;
	}

	public int getGDefault() {
		return gDefault;
	}

	public void setGDefault(int gValue) {
		this.gDefault = gValue;
	}

	public int getBDark() {
		return bDark;
	}

	public void setBDark(int bValue) {
		this.bDark = bValue;
	}

	public int getBLight() {
		return bLight;
	}

	public void setBLight(int bValue) {
		this.bLight = bValue;
	}

	public int getBDefault() {
		return bDefault;
	}

	public void setBDefault(int bValue) {
		this.bDefault = bValue;
	}

	public ThemeFactory(Themes theme) {

		setTheme(theme);

	}

	public void setTheme(Themes theme) {

		if (theme == Themes.Black) {
			setRDark(0);
			setGDark(0);
			setBDark(0);
			setRLight(64);
			setGLight(64);
			setBLight(64);
			setRDefault(32);
			setGDefault(32);
			setBDefault(32);
		} else if (theme == Themes.Blue) {
			setRDark(0);
			setGDark(0);
			setBDark(102);
			setRLight(0);
			setGLight(0);
			setBLight(204);
			setRDefault(0);
			setGDefault(0);
			setBDefault(153);
		} else if (theme == Themes.Green) {
			setRDark(0);
			setGDark(51);
			setBDark(0);
			setRLight(76);
			setGLight(153);
			setBLight(0);
			setRDefault(51);
			setGDefault(102);
			setBDefault(0);
		} else if (theme == Themes.Red) {
			setRDark(153);
			setGDark(0);
			setBDark(0);
			setRLight(255);
			setGLight(0);
			setBLight(0);
			setRDefault(204);
			setGDefault(0);
			setBDefault(0);
		} else if (theme == null) {
			setRDark(Color.DARK_GRAY.getRed());
			setGDark(Color.DARK_GRAY.getGreen());
			setBDark(Color.DARK_GRAY.getBlue());
			setRLight(Color.LIGHT_GRAY.getRed());
			setGLight(Color.LIGHT_GRAY.getGreen());
			setBLight(Color.LIGHT_GRAY.getBlue());
			setRDefault(Color.GRAY.getRed());
			setGDefault(Color.GRAY.getGreen());
			setBDefault(Color.GRAY.getBlue());
		} else if (theme == Themes.Purple) {
			setRDark(51);
			setGDark(0);
			setBDark(25);
			setRLight(153);
			setGLight(0);
			setBLight(76);
			setRDefault(102);
			setGDefault(0);
			setBDefault(51);
		} else if (theme == Themes.LightBlue) {
			setRDark(0);
			setGDark(51);
			setBDark(102);
			setRLight(0);
			setGLight(102);
			setBLight(204);
			setRDefault(0);
			setGDefault(76);
			setBDefault(153);
		} else if (theme == Themes.Orange) {
			setRDark(204);
			setGDark(102);
			setBDark(0);
			setRLight(255);
			setGLight(153);
			setBLight(51);
			setRDefault(255);
			setGDefault(128);
			setBDefault(0);
		}

	}

}
