package com.mirasmithy.epochlauncher;

import android.content.*;
import java.util.*;

public class GetPrefsCommunicator {
	private static Context context = null;
	private static boolean hasFinished = false;
	private static boolean hasCrashed = false;
	private static int dispPrefsOrientation;
	private static int appTheme;
	private static ArrayList<Theme> themes = new ArrayList<Theme>();
	private static boolean useCustomFont;
	private static String customFont;

	public synchronized void setContext(Context a) {
		this.context = a;
	}
	public synchronized void setHasFinished(boolean b) {
		this.hasFinished = b;
	}
	public synchronized void setHasCrashed(boolean c) {
		this.hasCrashed = c;
	}
	public synchronized void setDispPrefsOrientation(int d) {
		this.dispPrefsOrientation = d;
	}
	public synchronized void setAppTheme(int e) {
		this.appTheme = e;
	}
	public synchronized void setThemes(ArrayList<Theme> f) {
		this.themes = f;
	}
	public synchronized void setUseCustomFont(boolean g) {
		this.useCustomFont = g;
	}
	public synchronized void setCustomFont(String h) {
		this.customFont = h;
	}

	public Context getContext() {
		return this.context;
	}
	public boolean getHasFinished() {
		return this.hasFinished;
	}
	public boolean getHasCrashed() {
		return this.hasCrashed;
	}
	public int getDispPrefsOrientation() {
		return this.dispPrefsOrientation;
	}
	public int getAppTheme() {
		return this.appTheme;
	}
	public ArrayList<Theme> getThemes() {
		return this.themes;
	}
	public boolean getUseCustomFont() {
		return this.useCustomFont;
	}
	public String getCustomFont() {
		return this.customFont;
	}
}
