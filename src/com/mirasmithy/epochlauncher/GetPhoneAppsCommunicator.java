package com.mirasmithy.epochlauncher;

import android.content.pm.*;
import java.util.*;

public class GetPhoneAppsCommunicator {
	private static PackageManager packageManager = null;
	private static boolean hasFinished = false;
	private static ArrayList<AppInfo> phoneApps = new ArrayList<AppInfo>();

	public synchronized void setPackageManager(PackageManager b) {
		this.packageManager = b;
	}
	public synchronized void setHasFinished(boolean c) {
		this.hasFinished = c;
	}
	public synchronized void setPhoneApps(ArrayList<AppInfo> d) {
		this.phoneApps = d;
	}

	public PackageManager getPackageManager() {
		return this.packageManager;
	}
	public boolean getHasFinished() {
		return this.hasFinished;
	}
	public ArrayList<AppInfo> getPhoneApps() {
		return this.phoneApps;
	}
}
