package com.mirasmithy.epochlauncher;

import android.content.*;
import java.io.*;

public class GetPrefs implements Runnable {
	public void run() {
		GetPrefsCommunicator getPrefsCommunicator = new GetPrefsCommunicator();
		Context context = getPrefsCommunicator.getContext();
		try {
			FileInputStream getPrefsFis = context.openFileInput("prefs");
			ObjectInputStream getPrefsOis = new ObjectInputStream(getPrefsFis);
			getPrefsCommunicator.setPrefs((Prefs) getPrefsOis.readObject());
			getPrefsOis.close();
			getPrefsFis.close();
		}
		catch (Exception a) {
			Prefs prefs = new Prefs();
			try {
				FileOutputStream getPrefsFos = context.getApplicationContext().openFileOutput("prefs", context.MODE_PRIVATE);
				ObjectOutputStream getPrefsOos = new ObjectOutputStream(getPrefsFos);
				getPrefsOos.writeObject(prefs);
				getPrefsOos.close();
				getPrefsFos.close();
				getPrefsCommunicator.setPrefs(prefs);
			}
			catch (Exception b) {
				getPrefsCommunicator.setHasCrashed(true);
			}
		}
		getPrefsCommunicator.setHasFinished(true);
	}
}