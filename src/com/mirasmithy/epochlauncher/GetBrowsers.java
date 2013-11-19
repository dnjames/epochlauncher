package com.mirasmithy.epochlauncher;

import android.content.*;
import android.content.pm.*;
import android.net.*;
import java.util.*;

public class GetBrowsers implements Runnable {
	public void run() {
		GetBrowsersCommunicator getBrowsersCommunicator = new GetBrowsersCommunicator();
		PackageManager packageManager = getBrowsersCommunicator.getPackageManager();
		ArrayList<AppInfo> browsers = new ArrayList<AppInfo>();
		Intent getBrowsersIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.google.com/"));
		List<ResolveInfo> getBrowsersList = packageManager.queryIntentActivities(getBrowsersIntent, PackageManager.MATCH_DEFAULT_ONLY);
		for (ResolveInfo getBrowsersInfo : getBrowsersList) {
			browsers.add(new AppInfo());
			browsers.get(browsers.size() - 1).setAppInfo(getBrowsersInfo.activityInfo.loadLabel(packageManager).toString(), getBrowsersInfo.activityInfo.applicationInfo.packageName.toString());
		}
		if (browsers.size() == 0) {
			browsers.add(new AppInfo());
			browsers.get(0).setAppInfo("No Browsers Installed", "");
		}
		HashSet<AppInfo> a = new HashSet<AppInfo>(browsers);
		browsers = new ArrayList<AppInfo>(a);
		Collections.sort(browsers, new AppInfoComparator());
		getBrowsersCommunicator.setBrowsers(browsers);
		getBrowsersCommunicator.setHasFinished(true);
	}
}