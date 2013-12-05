package com.mirasmithy.epochlauncher;

import android.app.*;
import android.bluetooth.*;
import android.content.*;
import android.content.pm.*;
import android.graphics.*;
import android.location.*;
import android.net.wifi.*;
import android.os.*;
import android.view.*;
import android.view.View.*;
import android.widget.*;
import java.io.*;
import java.util.*;

public class CustomArrayAdapter extends ArrayAdapter {
	Context context;
	int layout;
	ArrayList data;
	PackageManager packageManager;
	boolean hwSupportsWifi;
	WifiManager wifiManager;
	boolean hwSupportsBt;
	BluetoothAdapter btAdapter;
	boolean hwSupportsGps;
	LocationManager locationManager;
	GetPrefsCommunicator gPC;
	int appMode;
	OnTouchListener menuRightItemOtl;
	Typeface akashi;
	Typeface roboto;
	Typeface customFont;
	public CustomArrayAdapter(Context context, int layout, ArrayList data,
							  PackageManager packageManager,
							  boolean hwSupportsWifi, WifiManager wifiManager,
							  boolean hwSupportsBt, BluetoothAdapter btAdapter,
							  boolean hwSupportsGps, LocationManager locationManager,
							  int appMode,
							  OnTouchListener menuRightItemOtl) {
		super(context, layout, data);
		this.context = context;
		this.layout = layout;
		this.data = data;
		this.packageManager = packageManager;
		this.hwSupportsWifi = hwSupportsWifi;
		this.wifiManager = wifiManager;
		this.hwSupportsBt = hwSupportsBt;
		this.btAdapter = btAdapter;
		this.hwSupportsGps = hwSupportsGps;
		this.locationManager = locationManager;
		gPC = new GetPrefsCommunicator();
		this.appMode = appMode;
		this.menuRightItemOtl = menuRightItemOtl;
		akashi = Typeface.createFromAsset(context.getAssets(), "fonts/akashi/Akashi.ttf");
		roboto = Typeface.createFromAsset(context.getAssets(), "fonts/roboto/Roboto-Regular.ttf");
		File file = new File(Environment.getExternalStorageDirectory(), gPC.getCustomFont());
		if (file.exists() && gPC.getUseCustomFont()) {
			try {
				customFont = Typeface.createFromFile(file);
			}
			catch (Exception e) {
				customFont = akashi;
			}
		}
		else {
			customFont = akashi;
		}
	}

	@Override
	public View getView(int position, View view, ViewGroup parent) {
		LayoutInflater layoutInflater = ((Activity) context).getLayoutInflater();
		switch (appMode) {
			case 1:
				view = layoutInflater.inflate(R.layout.menu_right_item, null);
				try {
					((ImageView) ((RelativeLayout) view).getChildAt(1)).setImageDrawable(packageManager.getApplicationIcon(((AppInfo) data.get(position)).getPackageName()));
				}
				catch (Exception e) {
					((ImageView) ((RelativeLayout) view).getChildAt(1)).setImageResource(R.drawable.menu_right_7_location_web_site);
				}
				((TextView) ((RelativeLayout) view).getChildAt(2)).setText(((AppInfo) data.get(position)).getAppName());
				((TextView) ((RelativeLayout) view).getChildAt(2)).setTypeface(customFont);
				if (((AppInfo) data.get(position)).getPackageName() == null) {
					setMenuRightItemColor(view, 0, false);
				}
				else {
					setMenuRightItemColor(view, 0, true);
				}
				break;
			case 11:
				view = layoutInflater.inflate(R.layout.menu_right_item, null);
				if (position == 0) {
					((ImageView) ((RelativeLayout) view).getChildAt(1)).setImageResource(R.drawable.menu_right_7_location_web_site);
				}
				else {
					((ImageView) ((RelativeLayout) view).getChildAt(1)).setImageResource(R.drawable.menu_right_5_content_discard);
				}
				((TextView) ((RelativeLayout) view).getChildAt(2)).setText((String) data.get(position));
				((TextView) ((RelativeLayout) view).getChildAt(2)).setTypeface(customFont);
				setMenuRightItemColor(view, 0, false);
				break;
			case 2:
				view = layoutInflater.inflate(R.layout.menu_right_item, null);
				try {
					((ImageView) ((RelativeLayout) view).getChildAt(1)).setImageDrawable(packageManager.getApplicationIcon(((AppInfo) data.get(position)).getPackageName()));
				}
				catch (Exception e) {
					((ImageView) ((RelativeLayout) view).getChildAt(1)).setImageResource(R.drawable.menu_right_6_social_group);
				}
				((TextView) ((RelativeLayout) view).getChildAt(2)).setText(((AppInfo) data.get(position)).getAppName());
				((TextView) ((RelativeLayout) view).getChildAt(2)).setTypeface(customFont);
				if (((AppInfo) data.get(position)).getPackageName() == null) {
					setMenuRightItemColor(view, 0, false);
				}
				else {
					setMenuRightItemColor(view, 0, true);
				}
				break;
			case 21:
				view = layoutInflater.inflate(R.layout.menu_right_item, null);
				if (position == 0) {
					((ImageView) ((RelativeLayout) view).getChildAt(1)).setImageResource(R.drawable.menu_right_6_social_group);
				}
				else {
					((ImageView) ((RelativeLayout) view).getChildAt(1)).setImageResource(R.drawable.menu_right_5_content_discard);
				}
				((TextView) ((RelativeLayout) view).getChildAt(2)).setText((String) data.get(position));
				((TextView) ((RelativeLayout) view).getChildAt(2)).setTypeface(customFont);
				setMenuRightItemColor(view, 0, false);
				break;
			case 3:
				view = layoutInflater.inflate(R.layout.menu_right_item, null);
				try {
					((ImageView) ((RelativeLayout) view).getChildAt(1)).setImageDrawable(packageManager.getApplicationIcon(((AppInfo) data.get(position)).getPackageName()));
				}
				catch (Exception e) {
					((ImageView) ((RelativeLayout) view).getChildAt(1)).setImageResource(R.drawable.menu_right_10_device_access_call);
				}
				((TextView) ((RelativeLayout) view).getChildAt(2)).setText(((AppInfo) data.get(position)).getAppName());
				((TextView) ((RelativeLayout) view).getChildAt(2)).setTypeface(customFont);
				if (((AppInfo) data.get(position)).getPackageName() == null) {
					setMenuRightItemColor(view, 0, false);
				}
				else {
					setMenuRightItemColor(view, 0, true);
				}
				break;
			case 31:
				view = layoutInflater.inflate(R.layout.menu_right_item, null);
				if (position == 0) {
					((ImageView) ((RelativeLayout) view).getChildAt(1)).setImageResource(R.drawable.menu_right_10_device_access_call);
				}
				else {
					((ImageView) ((RelativeLayout) view).getChildAt(1)).setImageResource(R.drawable.menu_right_5_content_discard);
				}
				((TextView) ((RelativeLayout) view).getChildAt(2)).setText((String) data.get(position));
				((TextView) ((RelativeLayout) view).getChildAt(2)).setTypeface(customFont);
				setMenuRightItemColor(view, 0, false);
				break;
			case 5:
				view = layoutInflater.inflate(R.layout.menu_right_item, null);
				if (data.get(position).getClass() == AppInfo.class) {
					try {
						((ImageView) ((RelativeLayout) view).getChildAt(1)).setImageDrawable(packageManager.getApplicationIcon(((AppInfo) data.get(position)).getPackageName()));
					}
					catch (Exception e) {
					}
					((TextView) ((RelativeLayout) view).getChildAt(2)).setText(((AppInfo) data.get(position)).getAppName());
					setMenuRightItemColor(view, 0, true);
				}
				else {
					((ImageView) ((RelativeLayout) view).getChildAt(1)).setImageResource(R.drawable.menu_right_4_collections_collection);
					((TextView) ((RelativeLayout) view).getChildAt(2)).setText(((Folder) data.get(position)).getFolderName());
					setMenuRightItemColor(view, 0, false);
				}
				((TextView) ((RelativeLayout) view).getChildAt(2)).setTypeface(customFont);
				break;
			case 51:
				view = layoutInflater.inflate(R.layout.menu_right_item, null);
				switch (position) {
					case 0:
						((ImageView) ((RelativeLayout) view).getChildAt(1)).setImageResource(R.drawable.menu_right_10_device_access_storage);
						break;
					case 1:
						((ImageView) ((RelativeLayout) view).getChildAt(1)).setImageResource(R.drawable.menu_right_5_content_discard);
						break;
					default:
						((ImageView) ((RelativeLayout) view).getChildAt(1)).setImageResource(R.drawable.menu_right_4_collections_collection);
						break;
				}
				((TextView) ((RelativeLayout) view).getChildAt(2)).setText((String) data.get(position));
				((TextView) ((RelativeLayout) view).getChildAt(2)).setTypeface(customFont);
				setMenuRightItemColor(view, 0, false);
				break;
			case 511:
				switch (position) {
					case 0:
						view = layoutInflater.inflate(R.layout.menu_right_item_edittable, null);
						((ImageView) ((RelativeLayout) view).getChildAt(1)).setImageResource(R.drawable.menu_right_5_content_edit);
						((EditText) ((RelativeLayout) view).getChildAt(2)).setText((String) data.get(position));
						((EditText) ((RelativeLayout) view).getChildAt(2)).setTypeface(customFont);
						break;
					case 1:
						view = layoutInflater.inflate(R.layout.menu_right_item, null);
						((ImageView) ((RelativeLayout) view).getChildAt(1)).setImageResource(R.drawable.menu_right_1_navigation_accept);
						((TextView) ((RelativeLayout) view).getChildAt(2)).setText((String) data.get(position));
						((TextView) ((RelativeLayout) view).getChildAt(2)).setTypeface(customFont);
						break;
					case 2:
						view = layoutInflater.inflate(R.layout.menu_right_item, null);
						((ImageView) ((RelativeLayout) view).getChildAt(1)).setImageResource(R.drawable.menu_right_1_navigation_cancel);
						((TextView) ((RelativeLayout) view).getChildAt(2)).setText((String) data.get(position));
						((TextView) ((RelativeLayout) view).getChildAt(2)).setTypeface(customFont);
						break;
				}
				setMenuRightItemColor(view, 0, false);
				break;
			case 52:
				view = layoutInflater.inflate(R.layout.menu_right_item, null);
				try {
					((ImageView) ((RelativeLayout) view).getChildAt(1)).setImageDrawable(packageManager.getApplicationIcon(((AppInfo) data.get(position)).getPackageName()));
				}
				catch (Exception e) {
				}
				((TextView) ((RelativeLayout) view).getChildAt(2)).setText(((AppInfo) data.get(position)).getAppName());
				((TextView) ((RelativeLayout) view).getChildAt(2)).setTypeface(customFont);
				setMenuRightItemColor(view, 0, true);
				break;
			case 521:
				view = layoutInflater.inflate(R.layout.menu_right_item, null);
				switch (position) {
					case 0:
						((ImageView) ((RelativeLayout) view).getChildAt(1)).setImageResource(R.drawable.menu_right_10_device_access_storage);
						break;
					case 1:
						((ImageView) ((RelativeLayout) view).getChildAt(1)).setImageResource(R.drawable.menu_right_5_content_discard);
						break;
					case 2:
						((ImageView) ((RelativeLayout) view).getChildAt(1)).setImageResource(R.drawable.menu_right_5_content_discard);
						break;
				}
				((TextView) ((RelativeLayout) view).getChildAt(2)).setText((String) data.get(position));
				((TextView) ((RelativeLayout) view).getChildAt(2)).setTypeface(customFont);
				setMenuRightItemColor(view, 0, false);
				break;
			case 53:
				view = layoutInflater.inflate(R.layout.menu_right_item, null);
				switch (position) {
					case 0:
						((ImageView) ((RelativeLayout) view).getChildAt(1)).setImageResource(R.drawable.menu_right_5_content_edit);
						break;
					case 1:
						((ImageView) ((RelativeLayout) view).getChildAt(1)).setImageResource(R.drawable.menu_right_5_content_discard);
						break;
				}
				((TextView) ((RelativeLayout) view).getChildAt(2)).setText((String) data.get(position));
				((TextView) ((RelativeLayout) view).getChildAt(2)).setTypeface(customFont);
				setMenuRightItemColor(view, 0, false);
				break;
			case 531:
				switch (position) {
					case 0:
						view = layoutInflater.inflate(R.layout.menu_right_item_edittable, null);
						((ImageView) ((RelativeLayout) view).getChildAt(1)).setImageResource(R.drawable.menu_right_5_content_edit);
						((EditText) ((RelativeLayout) view).getChildAt(2)).setText((String) data.get(position));
						((EditText) ((RelativeLayout) view).getChildAt(2)).setTypeface(customFont);
						break;
					case 1:
						view = layoutInflater.inflate(R.layout.menu_right_item, null);
						((ImageView) ((RelativeLayout) view).getChildAt(1)).setImageResource(R.drawable.menu_right_1_navigation_accept);
						((TextView) ((RelativeLayout) view).getChildAt(2)).setText((String) data.get(position));
						((TextView) ((RelativeLayout) view).getChildAt(2)).setTypeface(customFont);
						break;
					case 2:
						view = layoutInflater.inflate(R.layout.menu_right_item, null);
						((ImageView) ((RelativeLayout) view).getChildAt(1)).setImageResource(R.drawable.menu_right_1_navigation_cancel);
						((TextView) ((RelativeLayout) view).getChildAt(2)).setText((String) data.get(position));
						((TextView) ((RelativeLayout) view).getChildAt(2)).setTypeface(customFont);
						break;
				}
				setMenuRightItemColor(view, 0, false);
				break;
			case 6:
				if (position != 0) {
					view = layoutInflater.inflate(R.layout.menu_right_item, null);
					((TextView) ((RelativeLayout) view).getChildAt(2)).setText((String) data.get(position));
					((TextView) ((RelativeLayout) view).getChildAt(2)).setTypeface(customFont);
				}
				switch (position) {
					case 0:
						view = layoutInflater.inflate(R.layout.menu_right_item_ii, null);
						((ImageView) ((RelativeLayout) view).getChildAt(1)).setImageResource(R.drawable.menu_right_2_action_about);
						((TextView) ((RelativeLayout) view).getChildAt(2)).setText((String) data.get(position));
						((TextView) ((RelativeLayout) view).getChildAt(2)).setTypeface(customFont);
						((TextView) ((RelativeLayout) view).getChildAt(3)).setText("Copyright \u00a9 2013 Miras Absar");
						((TextView) ((RelativeLayout) view).getChildAt(3)).setTypeface(customFont);
						setMenuRightItemColor(view, 0, false);
						break;
					case 1:
						((ImageView) ((RelativeLayout) view).getChildAt(1)).setImageResource(R.drawable.menu_right_10_device_access_network_wifi);
						setMenuRightItemColor(view, boolean2int(hwSupportsWifi && wifiManager.isWifiEnabled()), false);
						break;
					case 2:
						((ImageView) ((RelativeLayout) view).getChildAt(1)).setImageResource(R.drawable.menu_right_10_device_access_bluetooth);
						setMenuRightItemColor(view, boolean2int(hwSupportsBt && btAdapter.isEnabled()), false);
						break;
					case 3:
						if (hwSupportsGps) {
							if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
								((ImageView) ((RelativeLayout) view).getChildAt(1)).setImageResource(R.drawable.menu_right_10_device_access_location_found);
							}
							else {
								((ImageView) ((RelativeLayout) view).getChildAt(1)).setImageResource(R.drawable.menu_right_10_device_access_location_off);
							}
						}
						else {
							((ImageView) ((RelativeLayout) view).getChildAt(1)).setImageResource(R.drawable.menu_right_10_device_access_location_off);
						}
						setMenuRightItemColor(view, boolean2int(hwSupportsGps && locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)), false);
						break;
					case 4:
						switch ((String) data.get(position)) {
							case "Orientation Unlocked":
								((ImageView) ((RelativeLayout) view).getChildAt(1)).setImageResource(R.drawable.menu_right_10_device_access_screen_rotation);
								break;
							case "Orientation Locked to Portrait":
								((ImageView) ((RelativeLayout) view).getChildAt(1)).setImageResource(R.drawable.menu_right_10_device_access_screen_locked_to_portrait);
								break;
							case "Orientation Locked to Landscape":
								((ImageView) ((RelativeLayout) view).getChildAt(1)).setImageResource(R.drawable.menu_right_10_device_access_screen_locked_to_landscape);
								break;
							case "Orientation Lock Unsupported":
								((ImageView) ((RelativeLayout) view).getChildAt(1)).setImageResource(R.drawable.menu_right_10_device_access_screen_locked_to_portrait);
								break;

						}
						setMenuRightItemColor(view, 0, false);
						break;
					case 5:
						((ImageView) ((RelativeLayout) view).getChildAt(1)).setImageResource(R.drawable.menu_right_2_action_settings);
						setMenuRightItemColor(view, 0, false);
						break;
					case 6:
						((ImageView) ((RelativeLayout) view).getChildAt(1)).setImageResource(R.drawable.menu_right_2_action_settings);
						setMenuRightItemColor(view, 0, false);
						break;
					case 7:
						((ImageView) ((RelativeLayout) view).getChildAt(1)).setImageResource(R.drawable.menu_right_1_navigation_refresh);
						setMenuRightItemColor(view, 0, false);
						break;
					case 8:
						((ImageView) ((RelativeLayout) view).getChildAt(1)).setImageResource(R.drawable.menu_right_11_alerts_and_states_warning);
						setMenuRightItemColor(view, 0, false);
						break;
				}
				break;
			case 61:
				view = layoutInflater.inflate(R.layout.menu_right_item_ii, null);
				((ImageView) ((RelativeLayout) view).getChildAt(1)).setImageResource(R.drawable.menu_right_2_action_about);
				((TextView) ((RelativeLayout) view).getChildAt(2)).setText((String) data.get(position));
				((TextView) ((RelativeLayout) view).getChildAt(2)).setTypeface(roboto);
				switch (position) {
					case 0:
						((TextView) ((RelativeLayout) view).getChildAt(3)).setText("Copyright \u00a9 2013 Miras Absar. Except as noted, Epoch Launcher is distributed under the GNU General Public License Version 3");
						break;
					case 1:
						((TextView) ((RelativeLayout) view).getChildAt(3)).setText("Copyright \u00a9 2013 Android Open Source Project. Portions of this application are modifications based on work created and shared by the Android Open Source Project and used according to terms described in the Creative Commons 2.5 Attribution License");
						break;
					case 2:
						((TextView) ((RelativeLayout) view).getChildAt(3)).setText("Copyright \u00a9 2013 Ten by Twenty. Epoch Launcher makes use of the font \"Akashi\" and uses it according to the terms described in the SIL Open Font License 1.1");
						break;
					case 3:
						((TextView) ((RelativeLayout) view).getChildAt(3)).setText("Copyright \u00a9 2013 Google. Epoch Launcher makes use of the font \"Roboto\" and uses it according to the terms described in the Apache License Version 2.0");
						break;
					case 4:
						((TextView) ((RelativeLayout) view).getChildAt(3)).setText("Copyright \u00a9 2013 Android Open Source Project. Epoch Launcher makes use of the library \"android-support-v4.jar\" and uses it according to the terms described in the Apache License Version 2.0");
						break;
				}
				((TextView) ((RelativeLayout) view).getChildAt(3)).setTypeface(roboto);
				setMenuRightItemColor(view, 0, false);
				break;
			case 62:
				view = layoutInflater.inflate(R.layout.menu_right_item, null);
				switch (position) {
					case 0:
						((ImageView) ((RelativeLayout) view).getChildAt(1)).setImageResource(R.drawable.menu_right_10_device_access_screen_rotation);
						break;
					case 1:
						((ImageView) ((RelativeLayout) view).getChildAt(1)).setImageResource(R.drawable.menu_right_10_device_access_screen_locked_to_portrait);
						break;
					case 2:
						((ImageView) ((RelativeLayout) view).getChildAt(1)).setImageResource(R.drawable.menu_right_10_device_access_screen_locked_to_landscape);
						break;
				}
				((TextView) ((RelativeLayout) view).getChildAt(2)).setText((String) data.get(position));
				((TextView) ((RelativeLayout) view).getChildAt(2)).setTypeface(customFont);
				setMenuRightItemColor(view, boolean2int(gPC.getDispPrefsOrientation() == position), false);
				break;
			case 63:
				view = layoutInflater.inflate(R.layout.menu_right_item, null);
				((TextView) ((RelativeLayout) view).getChildAt(2)).setText(((Theme) data.get(position)).getThemeName());
				((TextView) ((RelativeLayout) view).getChildAt(2)).setTypeface(customFont);
				((ImageView) ((RelativeLayout) view).getChildAt(1)).setImageResource(R.drawable.menu_right_2_action_settings);
				setMenuRightItemColor(view, boolean2int(gPC.getAppTheme() == position), false);
				break;
			case 64:
				switch (position) {
					case 0:
						view = layoutInflater.inflate(R.layout.menu_right_item, null);
						((ImageView) ((RelativeLayout) view).getChildAt(1)).setImageResource(R.drawable.menu_right_2_action_settings);
						((TextView) ((RelativeLayout) view).getChildAt(2)).setText((String) data.get(position));
						((TextView) ((RelativeLayout) view).getChildAt(2)).setTypeface(customFont);
						setMenuRightItemColor(view, boolean2int(gPC.getUseCustomFont()), false);
						break;
					case 1:
						view = layoutInflater.inflate(R.layout.menu_right_item_edittable, null);
						((ImageView) ((RelativeLayout) view).getChildAt(1)).setImageResource(R.drawable.menu_right_5_content_edit);
						((EditText) ((RelativeLayout) view).getChildAt(2)).setText((String) data.get(position));
						((EditText) ((RelativeLayout) view).getChildAt(2)).setTypeface(customFont);
						setMenuRightItemColor(view, 0, false);
						break;
					case 2:
						view = layoutInflater.inflate(R.layout.menu_right_item, null);
						((ImageView) ((RelativeLayout) view).getChildAt(1)).setImageResource(R.drawable.menu_right_1_navigation_accept);
						((TextView) ((RelativeLayout) view).getChildAt(2)).setText((String) data.get(position));
						((TextView) ((RelativeLayout) view).getChildAt(2)).setTypeface(customFont);
						setMenuRightItemColor(view, 0, false);
						break;
					case 3:
						view = layoutInflater.inflate(R.layout.menu_right_item, null);
						((ImageView) ((RelativeLayout) view).getChildAt(1)).setImageResource(R.drawable.menu_right_1_navigation_cancel);
						((TextView) ((RelativeLayout) view).getChildAt(2)).setText((String) data.get(position));
						((TextView) ((RelativeLayout) view).getChildAt(2)).setTypeface(customFont);
						setMenuRightItemColor(view, 0, false);
						break;
				}
				break;
		}
		view.setTag(new Integer(position));
		view.setOnTouchListener(menuRightItemOtl);
		return view;
	}

	public void setMenuRightItemColor(View view, int mode, boolean usingCustomIcon) {
		view.setBackgroundColor(gPC.getThemes().get(gPC.getAppTheme()).getColors(mode).get(TAL("menuRightPlateColor")));
		if (usingCustomIcon) {
			((ImageView) ((RelativeLayout) view).getChildAt(0)).setColorFilter(Color.argb(0, 255, 255, 255), PorterDuff.Mode.MULTIPLY);
		}
		else {
			((ImageView) ((RelativeLayout) view).getChildAt(0)).setColorFilter(gPC.getThemes().get(gPC.getAppTheme()).getColors(mode).get(TAL("menuRightSpaghettiColor")));
			((ImageView) ((RelativeLayout) view).getChildAt(1)).setColorFilter(gPC.getThemes().get(gPC.getAppTheme()).getColors(mode).get(TAL("menuRightSauceColor")));
		}
		if (((RelativeLayout) view).getChildAt(2).getClass() == TextView.class) {
			((TextView) ((RelativeLayout) view).getChildAt(2)).setTextColor(gPC.getThemes().get(gPC.getAppTheme()).getColors(mode).get(TAL("menuRightTextColor")));
		}
		else {
			((EditText) ((RelativeLayout) view).getChildAt(2)).setTextColor(gPC.getThemes().get(gPC.getAppTheme()).getColors(mode).get(TAL("menuRightTextColor")));
		}
		if (((RelativeLayout) view).getChildCount() == 4) {
			((TextView) ((RelativeLayout) view).getChildAt(3)).setTextColor(gPC.getThemes().get(gPC.getAppTheme()).getColors(mode).get(TAL("menuRightTextColor")));
		}
	}

	public int boolean2int(boolean input) {
		if (input) {
			return 1;
		}
		else {
			return 0;
		}
	}

	public boolean int2boolean(int input) {
		if (input == 0) {
			return false;
		}
		else {
			return true;
		}
	}

	public int TAL(String mode) { //ThemeAbstractionLayer
		int returnValue = 0;
		switch (mode) {
			case "menuLeftPlateColor":
				returnValue = 0;
				break;
			case "menuLeftSpaghettiColor":
				returnValue = 1;
				break;
			case "menuLeftSauceColor":
				returnValue = 2;
				break;
			case "pointerMenuLeftColor":
				returnValue = 3;
				break;
			case "separatorMenuLeftRightColor":
				returnValue = 4;
				break;
			case "menuRightPlateColor":
				returnValue = 5;
				break;
			case "menuRightSpaghettiColor":
				returnValue = 6;
				break;
			case "menuRightSauceColor":
				returnValue = 7;
				break;
			case "menuRightTextColor":
				returnValue = 8;
				break;
			case "pointerUpDownColor":
				returnValue = 9;
				break;
		}
		return returnValue;
	}
}
