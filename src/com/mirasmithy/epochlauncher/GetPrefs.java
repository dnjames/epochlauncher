package com.mirasmithy.epochlauncher;

import android.content.*;
import android.graphics.*;
import java.io.*;
import java.util.*;

public class GetPrefs implements Runnable {
	FileInputStream getPrefsFis;
	ObjectInputStream getPrefsOis;
	FileOutputStream getPrefsFos;
	ObjectOutputStream getPrefsOos;
	public void run() {
		GetPrefsCommunicator gPC = new GetPrefsCommunicator();
		Context context = gPC.getContext();
		try {
			getPrefsFis = context.openFileInput("dispPrefsOrientation");
			getPrefsOis = new ObjectInputStream(getPrefsFis);
			gPC.setDispPrefsOrientation(((Integer) getPrefsOis.readObject()).intValue());
			getPrefsOis.close();
			getPrefsFis.close();
		}
		catch (Exception e) {
			try {
				getPrefsFos = context.getApplicationContext().openFileOutput("dispPrefsOrientation", context.MODE_PRIVATE);
				getPrefsOos = new ObjectOutputStream(getPrefsFos);
				getPrefsOos.writeObject(0);
				getPrefsOos.close();
				getPrefsFos.close();
				gPC.setDispPrefsOrientation(0);
			}
			catch (Exception f) {
				gPC.setHasCrashed(true);
			}
		}

		try {
			getPrefsFis = context.openFileInput("appTheme");
			getPrefsOis = new ObjectInputStream(getPrefsFis);
			gPC.setAppTheme(((Integer) getPrefsOis.readObject()).intValue());
			getPrefsOis.close();
			getPrefsFis.close();
		}
		catch (Exception e) {
			try {
				getPrefsFos = context.getApplicationContext().openFileOutput("appTheme", context.MODE_PRIVATE);
				getPrefsOos = new ObjectOutputStream(getPrefsFos);
				getPrefsOos.writeObject(1);
				getPrefsOos.close();
				getPrefsFos.close();
				gPC.setAppTheme(1);
			}
			catch (Exception f) {
				gPC.setHasCrashed(true);
			}
		}

		try {
			getPrefsFis = context.openFileInput("themes");
			getPrefsOis = new ObjectInputStream(getPrefsFis);
			gPC.setThemes((ArrayList<Theme>) getPrefsOis.readObject());
			getPrefsOis.close();
			getPrefsFis.close();
		}
		catch (Exception e) {
			try {
				ArrayList<Theme> themes = new ArrayList<Theme>();
				themes.add(new Theme());
				themes.get(themes.size() - 1).setThemeName("Dark Theme");
				themes.get(themes.size() - 1).getColors(0).add(Color.argb(102, 255, 255, 255));//menuLeftPlateColor
				themes.get(themes.size() - 1).getColors(0).add(Color.argb(255, 0, 0, 0));//menuLeftSpaghettiColor
				themes.get(themes.size() - 1).getColors(0).add(Color.argb(255, 255, 255, 255));//menuLeftSauceColor
				themes.get(themes.size() - 1).getColors(0).add(Color.argb(0, 255, 255, 255));//pointerMenuLeftColor
				themes.get(themes.size() - 1).getColors(0).add(Color.argb(0, 255, 255, 255));//separatorMenuLeftRight
				themes.get(themes.size() - 1).getColors(0).add(Color.argb(230, 0, 0, 0));//menuRightPlateColor
				themes.get(themes.size() - 1).getColors(0).add(Color.argb(255, 255, 255, 255));//menuRightSpaghettiColor
				themes.get(themes.size() - 1).getColors(0).add(Color.argb(255, 0, 0, 0));//menuRightSauceColor
				themes.get(themes.size() - 1).getColors(0).add(Color.argb(255, 255, 255, 255));//menuRightTextColor
				themes.get(themes.size() - 1).getColors(0).add(Color.argb(0, 255, 255, 255));//PointerUpDownColor

				themes.get(themes.size() - 1).getColors(1).add(Color.argb(102, 255, 255, 255));//menuLeftPlateColor
				themes.get(themes.size() - 1).getColors(1).add(Color.argb(255, 255, 145, 0));//menuLeftSpaghettiColor
				themes.get(themes.size() - 1).getColors(1).add(Color.argb(255, 255, 255, 255));//menuLeftSauceColor
				themes.get(themes.size() - 1).getColors(1).add(Color.argb(230, 0, 0, 0));//pointerMenuLeftColor
				themes.get(themes.size() - 1).getColors(1).add(Color.argb(230, 0, 0, 0));//separatorMenuLeftRight
				themes.get(themes.size() - 1).getColors(1).add(Color.argb(230, 255, 145, 0));//menuRightPlateColor
				themes.get(themes.size() - 1).getColors(1).add(Color.argb(255, 255, 255, 255));//menuRightSpaghettiColor
				themes.get(themes.size() - 1).getColors(1).add(Color.argb(255, 255, 145, 0));//menuRightSauceColor
				themes.get(themes.size() - 1).getColors(1).add(Color.argb(255, 255, 255, 255));//menuRightTextColor
				themes.get(themes.size() - 1).getColors(1).add(Color.argb(230, 0, 0, 0));//PointerUpDownColor

				themes.add(new Theme());
				themes.get(themes.size() - 1).setThemeName("Light Theme");
				themes.get(themes.size() - 1).getColors(0).add(Color.argb(102, 255, 255, 255));//menuLeftPlateColor
				themes.get(themes.size() - 1).getColors(0).add(Color.argb(255, 255, 255, 255));//menuLeftSpaghettiColor
				themes.get(themes.size() - 1).getColors(0).add(Color.argb(255, 0, 0, 0));//menuLeftSauceColor
				themes.get(themes.size() - 1).getColors(0).add(Color.argb(0, 255, 255, 255));//pointerMenuLeftColor
				themes.get(themes.size() - 1).getColors(0).add(Color.argb(0, 255, 255, 255));//separatorMenuLeftRight
				themes.get(themes.size() - 1).getColors(0).add(Color.argb(230, 255, 255, 255));//menuRightPlateColor
				themes.get(themes.size() - 1).getColors(0).add(Color.argb(255, 0, 0, 0));//menuRightSpaghettiColor
				themes.get(themes.size() - 1).getColors(0).add(Color.argb(255, 255, 255, 255));//menuRightSauceColor
				themes.get(themes.size() - 1).getColors(0).add(Color.argb(255, 0, 0, 0));//menuRightTextColor
				themes.get(themes.size() - 1).getColors(0).add(Color.argb(0, 255, 255, 255));//PointerUpDownColor

				themes.get(themes.size() - 1).getColors(1).add(Color.argb(102, 255, 255, 255));//menuLeftPlateColor
				themes.get(themes.size() - 1).getColors(1).add(Color.argb(255, 255, 145, 0));//menuLeftSpaghettiColor
				themes.get(themes.size() - 1).getColors(1).add(Color.argb(255, 255, 255, 255));//menuLeftSauceColor
				themes.get(themes.size() - 1).getColors(1).add(Color.argb(230, 255, 255, 255));//pointerMenuLeftColor
				themes.get(themes.size() - 1).getColors(1).add(Color.argb(230, 255, 255, 255));//separatorMenuLeftRight
				themes.get(themes.size() - 1).getColors(1).add(Color.argb(230, 255, 145, 0));//menuRightPlateColor
				themes.get(themes.size() - 1).getColors(1).add(Color.argb(255, 255, 255, 255));//menuRightSpaghettiColor
				themes.get(themes.size() - 1).getColors(1).add(Color.argb(255, 255, 145, 0));//menuRightSauceColor
				themes.get(themes.size() - 1).getColors(1).add(Color.argb(255, 255, 255, 255));//menuRightTextColor
				themes.get(themes.size() - 1).getColors(1).add(Color.argb(230, 255, 255, 255));//PointerUpDownColor

				Collections.sort(themes, new ThemeComparator());

				getPrefsFos = context.getApplicationContext().openFileOutput("themes", context.MODE_PRIVATE);
				getPrefsOos = new ObjectOutputStream(getPrefsFos);
				getPrefsOos.writeObject(themes);
				getPrefsOos.close();
				getPrefsFos.close();
				gPC.setThemes(themes);
			}
			catch (Exception f) {
				gPC.setHasCrashed(true);
			}
		}

		try {
			getPrefsFis = context.openFileInput("useCustomFont");
			getPrefsOis = new ObjectInputStream(getPrefsFis);
			gPC.setUseCustomFont(((Boolean) getPrefsOis.readObject()).booleanValue());
			getPrefsOis.close();
			getPrefsFis.close();
		}
		catch (Exception e) {
			try {
				getPrefsFos = context.getApplicationContext().openFileOutput("useCustomFont", context.MODE_PRIVATE);
				getPrefsOos = new ObjectOutputStream(getPrefsFos);
				getPrefsOos.writeObject(false);
				getPrefsOos.close();
				getPrefsFos.close();
				gPC.setUseCustomFont(false);
			}
			catch (Exception f) {
				gPC.setHasCrashed(true);
			}
		}

		try {
			getPrefsFis = context.openFileInput("customFont");
			getPrefsOis = new ObjectInputStream(getPrefsFis);
			gPC.setCustomFont((String) getPrefsOis.readObject());
			getPrefsOis.close();
			getPrefsFis.close();
		}
		catch (Exception e) {
			try {
				getPrefsFos = context.getApplicationContext().openFileOutput("customFont", context.MODE_PRIVATE);
				getPrefsOos = new ObjectOutputStream(getPrefsFos);
				getPrefsOos.writeObject("Font Path");
				getPrefsOos.close();
				getPrefsFos.close();
				gPC.setCustomFont("Font Path");
			}
			catch (Exception f) {
				gPC.setHasCrashed(true);
			}
		}
		gPC.setHasFinished(true);
	}
}
