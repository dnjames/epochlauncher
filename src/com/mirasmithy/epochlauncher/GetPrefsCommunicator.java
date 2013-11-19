package com.mirasmithy.epochlauncher;

import android.content.*;

public class GetPrefsCommunicator {
	private static Context context = null;
	private static boolean hasFinished = false;
	private static boolean hasCrashed = false;
	private static Prefs prefs = new Prefs();

	public synchronized void setContext(Context a) {
		this.context = a;
	}
	public synchronized void setHasFinished(boolean b) {
		this.hasFinished = b;
	}
	public synchronized void setHasCrashed(boolean c) {
		this.hasCrashed = c;
	}
	public synchronized void setPrefs(Prefs d) {
		this.prefs = d;
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
	public Prefs getPrefs() {
		return this.prefs;
	}
}