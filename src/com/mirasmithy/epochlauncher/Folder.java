package com.mirasmithy.epochlauncher;

import java.io.*;
import java.util.*;

public class Folder implements Serializable {
	private String folderName;
	private ArrayList<AppInfo> folderContents = new ArrayList<AppInfo>();

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
