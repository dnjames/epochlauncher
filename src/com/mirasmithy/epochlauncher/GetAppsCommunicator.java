package com.mirasmithy.epochlauncher;

import android.content.pm.*;
import java.util.*;

public class GetAppsCommunicator {
	private static PackageManager packageManager = null;
	private static boolean hasFinished = false;
	private static ArrayList<AppInfo> apps = new ArrayList<AppInfo>();

	public synchronized void setPackageManager(PackageManager b) {
		this.packageManager = b;
	}
	public synchronized void setHasFinished(boolean c) {
		this.hasFinished = c;
	}
	public synchronized void setApps(ArrayList<AppInfo> d) {
		this.apps = d;
	}

	public PackageManager getPackageManager() {
		return this.packageManager;
	}
	public boolean getHasFinished() {
		return this.hasFinished;
	}
	public ArrayList<AppInfo> getApps() {
		return this.apps;
	}
}
