package com.mirasmithy.epochlauncher;

import java.util.*;

public class ThemeComparator implements Comparator {
	public int compare(Object a, Object b) {
		String aThemeName = ((Theme) a).getThemeName().toUpperCase();
		String bThemeName = ((Theme) b).getThemeName().toUpperCase();
		return aThemeName.compareTo(bThemeName);
	}
}
