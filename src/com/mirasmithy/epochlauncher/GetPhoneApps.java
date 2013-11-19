package com.mirasmithy.epochlauncher;

import android.content.*;
import android.content.pm.*;
import java.util.*;

public class GetPhoneApps implements Runnable {
	public void run() {
		GetPhoneAppsCommunicator getPhoneAppsCommunicator = new GetPhoneAppsCommunicator();
		PackageManager packageManager = getPhoneAppsCommunicator.getPackageManager();
		ArrayList<AppInfo> phoneApps = new ArrayList<AppInfo>();
		Intent getPhoneAppsIntent = new Intent(Intent.ACTION_DIAL, null);
		List<ResolveInfo> getPhoneAppsList = packageManager.queryIntentActivities(getPhoneAppsIntent, PackageManager.MATCH_DEFAULT_ONLY);
		for (ResolveInfo getPhoneAppsInfo : getPhoneAppsList) {
			phoneApps.add(new AppInfo());
			phoneApps.get(phoneApps.size() - 1).setAppInfo(getPhoneAppsInfo.activityInfo.loadLabel(packageManager).toString(), getPhoneAppsInfo.activityInfo.applicationInfo.packageName.toString());
		}
		if (phoneApps.size() == 0) {
			phoneApps.add(new AppInfo());
			phoneApps.get(0).setAppInfo("No Phone Applications Installed", "");
		}
		HashSet<AppInfo> a = new HashSet<AppInfo>(phoneApps);
		phoneApps = new ArrayList<AppInfo>(a);
		Collections.sort(phoneApps, new AppInfoComparator());
		getPhoneAppsCommunicator.setPhoneApps(phoneApps);
		getPhoneAppsCommunicator.setHasFinished(true);
	}
}