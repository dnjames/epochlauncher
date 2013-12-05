package com.mirasmithy.epochlauncher;

import java.util.*;

public class FolderComparator implements Comparator {
	public int compare(Object a, Object b) {
		String aFolderName = ((Folder) a).getFolderName().toUpperCase();
		String bFolderName = ((Folder) b).getFolderName().toUpperCase();
		return aFolderName.compareTo(bFolderName);
	}
}
