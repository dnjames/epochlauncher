package com.mirasmithy.epochlauncher;

public class AppInfo {
	private String appName;
	private String packageName;

	public synchronized void setAppInfo(String a, String b) {
		this.appName = a;
		this.packageName = b;
	}

	public String getAppName() {
		return this.appName;
	}
	public String getPackageName() {
		return this.packageName;
	}
}