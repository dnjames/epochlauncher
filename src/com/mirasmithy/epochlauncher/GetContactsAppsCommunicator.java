package com.mirasmithy.epochlauncher;

import android.content.pm.*;
import java.util.*;

public class GetContactsAppsCommunicator {
	private static PackageManager packageManager = null;
	private static boolean hasFinished = false;
	private static ArrayList<AppInfo> contactsApps = new ArrayList<AppInfo>();

	public synchronized void setPackageManager(PackageManager b) {
		this.packageManager = b;
	}
	public synchronized void setHasFinished(boolean c) {
		this.hasFinished = c;
	}
	public synchronized void setContactsApps(ArrayList<AppInfo> d) {
		this.contactsApps = d;
	}

	public PackageManager getPackageManager() {
		return this.packageManager;
	}
	public boolean getHasFinished() {
		return this.hasFinished;
	}
	public ArrayList<AppInfo> getContactsApps() {
		return this.contactsApps;
	}
}
