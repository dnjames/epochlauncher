package com.mirasmithy.epochlauncher;

import java.util.*;

public class AppInfoComparator implements Comparator {
	public int compare(Object a, Object b) {
		String aPackageName = ((AppInfo) a).getAppName().toUpperCase();
		String bPackageName = ((AppInfo) b).getAppName().toUpperCase();
		return aPackageName.compareTo(bPackageName);
	}
}
