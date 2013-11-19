package com.mirasmithy.epochlauncher;

import android.graphics.*;
import java.io.*;

public class Prefs implements Serializable {
	private int dispPrefsOrientation = 0;
	private int appTheme = 0;
	private int menuLeftPlateColor = Color.argb(102, 255, 255, 255);
	private int menuLeftSpaghettiColorLightPassive = Color.argb(255, 255, 255, 255);
	private int menuLeftSpaghettiColorDarkPassive = Color.argb(255, 0, 0, 0);
	private int menuLeftSpaghettiColorActive = Color.argb(255, 255, 145, 0);
	private int menuLeftSauceColorLightPassive = Color.argb(255, 0, 0, 0);
	private int menuLeftSauceColorDarkPassive = Color.argb(255, 255, 255, 255);
	private int menuLeftSauceColorActive = Color.argb(255, 255, 255, 255);
	private int pointerMenuLeftColorLight = Color.argb(230, 255, 255, 255);
	private int pointerMenuLeftColorDark = Color.argb(230, 0, 0, 0);
	private int separatorMenuLeftRightColorLight = Color.argb(230, 255, 255, 255);
	private int separatorMenuLeftRightColorDark = Color.argb(230, 0, 0, 0);
	private int menuRightPlateColorLightPassive = Color.argb(230, 255, 255, 255);
	private int menuRightPlateColorDarkPassive = Color.argb(230, 0, 0, 0);
	private int menuRightPlateColorActive = Color.argb(230, 255, 145, 0);
	private int menuRightSpaghettiColorLightPassive = Color.argb(255, 0, 0, 0);
	private int menuRightSpaghettiColorDarkPassive = Color.argb(255, 255, 255, 255);
	private int menuRightSpaghettiColorActive = Color.argb(255, 255, 255, 255);
	private int menuRightSauceColorLightPassive = Color.argb(255, 255, 255, 255);
	private int menuRightSauceColorDarkPassive = Color.argb(255, 0, 0, 0);
	private int menuRightSauceColorActive = Color.argb(255, 255, 145, 0);
	private int menuRightTextColorLightPassive = Color.argb(255, 0, 0, 0);
	private int menuRightTextColorDarkPassive = Color.argb(255, 255, 255, 255);
	private int menuRightTextColorActive = Color.argb(255, 255, 255, 255);
	private int pointerUpDownColorLight = Color.argb(230, 255, 255, 255);
	private int pointerUpDownColorDark = Color.argb(230, 0, 0, 0);

	public synchronized void setDispPrefsOrientation(int a) {
		this.dispPrefsOrientation = a;
	}
	public synchronized void setAppTheme(int b) {
		this.appTheme = b;
	}

	public int getDispPrefsOrientation() {
		return this.dispPrefsOrientation;
	}
	public int getAppTheme() {
		return this.appTheme;
	}
	public int getMenuLeftPlateColor() {
		return this.menuLeftPlateColor;
	}
	public int getMenuLeftSpaghettiColorPassive() {
		if (appTheme == 0) {
			return this.menuLeftSpaghettiColorLightPassive;
		}
		else {
			return this.menuLeftSpaghettiColorDarkPassive;
		}
	}
	public int getMenuLeftSpaghettiColorActive() {
		return this.menuLeftSpaghettiColorActive;
	}
	public int getMenuLeftSauceColorPassive() {
		if (appTheme == 0) {
			return this.menuLeftSauceColorLightPassive;
		}
		else {
			return this.menuLeftSauceColorDarkPassive;
		}
	}
	public int getMenuLeftSauceColorActive() {
		return this.menuLeftSauceColorActive;
	}
	public int getPointerMenuLeftColor() {
		if (appTheme == 0) {
			return this.pointerMenuLeftColorLight;
		}
		else {
			return this.pointerMenuLeftColorDark;
		}
	}
	public int getSeparatorMenuLeftRightColor() {
		if (appTheme == 0) {
			return this.separatorMenuLeftRightColorLight;
		}
		else {
			return this.separatorMenuLeftRightColorDark;
		}
	}
	public int getMenuRightPlateColorPassive() {
		if (appTheme == 0) {
			return this.menuRightPlateColorLightPassive;
		}
		else {
			return this.menuRightPlateColorDarkPassive;
		}
	}
	public int getMenuRightPlateColorActive() {
		return this.menuRightPlateColorActive;
	}
	public int getMenuRightSpaghettiColorPassive() {
		if (appTheme == 0) {
			return this.menuRightSpaghettiColorLightPassive;
		}
		else {
			return this.menuRightSpaghettiColorDarkPassive;
		}
	}
	public int getMenuRightSpaghettiColorActive() {
		return this.menuRightSpaghettiColorActive;
	}
	public int getMenuRightSauceColorPassive() {
		if (appTheme == 0) {
			return this.menuRightSauceColorLightPassive;
		}
		else {
			return this.menuRightSauceColorDarkPassive;
		}
	}
	public int getMenuRightSauceColorActive() {
		return this.menuRightSauceColorActive;
	}
	public int getMenuRightTextColorPassive() {
		if (appTheme == 0) {
			return this.menuRightTextColorLightPassive;
		}
		else {
			return this.menuRightTextColorDarkPassive;
		}
	}
	public int getMenuRightTextColorActive() {
		return this.menuRightTextColorActive;
	}
	public int getPointerUpDownColor() {
		if (appTheme == 0) {
			return this.pointerUpDownColorLight;
		}
		else {
			return this.pointerUpDownColorDark;
		}
	}
}