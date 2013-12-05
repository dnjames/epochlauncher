package com.mirasmithy.epochlauncher;

import android.content.pm.*;
import java.util.*;

public class GetBrowsersCommunicator {
	private static PackageManager packageManager = null;
	private static boolean hasFinished = false;
	private static ArrayList<AppInfo> browsers = new ArrayList<AppInfo>();

	public synchronized void setPackageManager(PackageManager b) {
		this.packageManager = b;
	}
	public synchronized void setHasFinished(boolean c) {
		this.hasFinished = c;
	}
	public synchronized void setBrowsers(ArrayList<AppInfo> d) {
		this.browsers = d;
	}

	public PackageManager getPackageManager() {
		return this.packageManager;
	}
	public boolean getHasFinished() {
		return this.hasFinished;
	}
	public ArrayList<AppInfo> getBrowsers() {
		return this.browsers;
	}
}
