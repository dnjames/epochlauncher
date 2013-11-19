package com.mirasmithy.epochlauncher;

import java.util.*;

public class Folder {
	private String folderName;
	private ArrayList<AppInfo> folderContents;

	public synchronized void setFolderName(String a) {
		this.folderName = a;
	}
	public synchronized void setFolderContents(ArrayList<AppInfo> b) {
		this.folderContents = b;
	}

	public String getFolderName() {
		return this.folderName;
	}
	public ArrayList<AppInfo> getFolderContents() {
		return this.folderContents;
	}
}