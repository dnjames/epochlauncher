package com.mirasmithy.epochlauncher;

import android.content.*;
import java.util.*;

public class GetFoldersCommunicator {
	private static Context context = null;
	private static boolean hasFinished = false;
	private static boolean hasCrashed = false;
	private static ArrayList<Folder> folders = new ArrayList<Folder>();

	public synchronized void setContext(Context a) {
		this.context = a;
	}
	public synchronized void setHasFinished(boolean b) {
		this.hasFinished = b;
	}
	public synchronized void setHasCrashed(boolean c) {
		this.hasCrashed = c;
	}
	public synchronized void setFolders(ArrayList<Folder> d) {
		this.folders = d;
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
	public ArrayList<Folder> getFolders() {
		return this.folders;
	}
}
