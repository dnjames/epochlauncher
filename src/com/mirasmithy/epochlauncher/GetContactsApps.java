package com.mirasmithy.epochlauncher;

import android.content.*;
import android.content.pm.*;
import android.provider.*;
import java.util.*;

public class GetContactsApps implements Runnable {
	public void run() {
		GetContactsAppsCommunicator gCAC = new GetContactsAppsCommunicator();
		PackageManager packageManager = gCAC.getPackageManager();
		ArrayList<AppInfo> contactsApps = new ArrayList<AppInfo>();
		Intent getContactsAppsIntent = new Intent(Intent.ACTION_VIEW, ContactsContract.Contacts.CONTENT_URI);
		List<ResolveInfo> getContactsAppsList = packageManager.queryIntentActivities(getContactsAppsIntent, PackageManager.MATCH_DEFAULT_ONLY);
		for (ResolveInfo getContactsAppsInfo : getContactsAppsList) {
			contactsApps.add(new AppInfo());
			contactsApps.get(contactsApps.size() - 1).setAppInfo(getContactsAppsInfo.activityInfo.loadLabel(packageManager).toString(), getContactsAppsInfo.activityInfo.applicationInfo.packageName.toString());
		}
		if (contactsApps.size() == 0) {
			contactsApps.add(new AppInfo());
			contactsApps.get(0).setAppInfo("No Contacts Applications Installed", null);
		}
		HashSet<AppInfo> a = new HashSet<AppInfo>(contactsApps);
		contactsApps = new ArrayList<AppInfo>(a);
		Collections.sort(contactsApps, new AppInfoComparator());
		gCAC.setContactsApps(contactsApps);
		gCAC.setHasFinished(true);
	}
}
