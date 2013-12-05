package com.mirasmithy.epochlauncher;

import android.content.*;
import java.io.*;
import java.util.*;

public class GetFolders implements Runnable {
	public void run() {
		GetFoldersCommunicator gFC = new GetFoldersCommunicator();
		Context context = gFC.getContext();
		try {
			FileInputStream getFoldersFis = context.openFileInput("folders");
			ObjectInputStream getFoldersOis = new ObjectInputStream(getFoldersFis);
			gFC.setFolders((ArrayList<Folder>) getFoldersOis.readObject());
			getFoldersOis.close();
			getFoldersFis.close();
		}
		catch (Exception a) {
			ArrayList<Folder> folders = new ArrayList<Folder>();
			try {
				FileOutputStream getFoldersFos = context.getApplicationContext().openFileOutput("folders", context.MODE_PRIVATE);
				ObjectOutputStream getFoldersOos = new ObjectOutputStream(getFoldersFos);
				getFoldersOos.writeObject(folders);
				getFoldersOos.close();
				getFoldersFos.close();
				gFC.setFolders(folders);
			}
			catch (Exception b) {
				gFC.setHasCrashed(true);
			}
		}
		gFC.setHasFinished(true);
	}
}
