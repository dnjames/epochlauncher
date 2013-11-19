package com.mirasmithy.epochlauncher;

import android.content.*;
import android.content.pm.*;
import java.util.*;

public class GetApps implements Runnable {
	public void run() {
		GetAppsCommunicator getAppsCommunicator = new GetAppsCommunicator();
		PackageManager packageManager = getAppsCommunicator.getPackageManager();
		ArrayList<AppInfo> apps = new ArrayList<AppInfo>();
		Intent getAppsIntent = new Intent(Intent.ACTION_MAIN, null);
		getAppsIntent.addCategory(Intent.CATEGORY_LAUNCHER);
		List<ResolveInfo> getAppsList = packageManager.queryIntentActivities(getAppsIntent, 0);
		for (ResolveInfo getAppsInfo : getAppsList) {
			apps.add(new AppInfo());
			apps.get(apps.size() - 1).setAppInfo(getAppsInfo.activityInfo.loadLabel(packageManager).toString(), getAppsInfo.activityInfo.applicationInfo.packageName.toString());
		}
		HashSet<AppInfo> a = new HashSet<AppInfo>(apps);
		apps = new ArrayList<AppInfo>(a);
		Collections.sort(apps, new AppInfoComparator());
		getAppsCommunicator.setApps(apps);
		getAppsCommunicator.setHasFinished(true);
	}
}