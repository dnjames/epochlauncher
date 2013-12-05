package com.mirasmithy.epochlauncher;

import java.io.*;
import java.util.*;

public class Theme implements Serializable {
	private String themeName;
	private ArrayList<Integer> passiveColors = new ArrayList<Integer>();
	private ArrayList<Integer> activeColors = new ArrayList<Integer>();

	public synchronized void setThemeName(String a) {
		this.themeName = a;
	}
	public synchronized void setColors(ArrayList<Integer> b, int mode) {
		if (mode == 0) {
			this.passiveColors = b;
		}
		else {
			this.activeColors = b;
		}
	}

	public String getThemeName() {
		return this.themeName;
	}
	public ArrayList<Integer> getColors(int mode) {
		if (mode == 0) {
			return this.passiveColors;
		}
		else {
			return this.activeColors;
		}
	}
}
