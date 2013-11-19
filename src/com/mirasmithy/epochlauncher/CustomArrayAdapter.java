package com.mirasmithy.epochlauncher;

import android.app.*;
import android.bluetooth.*;
import android.content.*;
import android.content.pm.*;
import android.graphics.*;
import android.location.*;
import android.net.wifi.*;
import android.view.*;
import android.view.View.*;
import android.widget.*;
import java.util.*;

public class CustomArrayAdapter extends ArrayAdapter {
	Context context;
	int layout;
	ArrayList data;
	int appMode;
	Prefs prefs;
	PackageManager packageManager;
	boolean hwSupportsWifi;
	WifiManager wifiManager;
	boolean hwSupportsBt;
	BluetoothAdapter btAdapter;
	boolean hwSupportsGps;
	LocationManager locationManager;
	OnTouchListener menuRightItemOtl;
	Typeface akashi;
	Typeface roboto;
	public CustomArrayAdapter(Context context, int layout, ArrayList data,
							  int appMode, Prefs prefs, PackageManager packageManager,
							  boolean hwSupportsWifi, WifiManager wifiManager, boolean hwSupportsBt,
							  BluetoothAdapter btAdapter, boolean hwSupportsGps, LocationManager locationManager,
							  OnTouchListener menuRightItemOtl) {
		super(context, layout, data);
		this.context = context;
		this.layout = layout;
		this.data = data;
		this.appMode = appMode;
		this.prefs = prefs;
		this.packageManager = packageManager;
		this.hwSupportsWifi = hwSupportsWifi;
		this.wifiManager = wifiManager;
		this.hwSupportsBt = hwSupportsBt;
		this.btAdapter = btAdapter;
		this.hwSupportsGps = hwSupportsGps;
		this.locationManager = locationManager;
		this.menuRightItemOtl = menuRightItemOtl;
		akashi = Typeface.createFromAsset(context.getAssets(), "fonts/akashi/Akashi.ttf");
		roboto = Typeface.createFromAsset(context.getAssets(), "fonts/roboto/Roboto-Regular.ttf");
	}

	@Override
	public View getView(int position, View view, ViewGroup parent) {
		LayoutInflater layoutInflater = ((Activity) context).getLayoutInflater();
		switch (appMode) {
			case 1:
				view = layoutInflater.inflate(R.layout.menu_right_item, null);
				((ImageView) ((RelativeLayout) view).getChildAt(1)).setImageResource(R.drawable.menu_right_7_location_web_site);
				((TextView) ((RelativeLayout) view).getChildAt(2)).setText(((AppInfo) data.get(position)).getAppName());
				((TextView) ((RelativeLayout) view).getChildAt(2)).setTypeface(akashi);
				setMenuRightItemColor(view, 0);
				break;
			case 11:
				switch (position) {
					case 0:
						view = layoutInflater.inflate(R.layout.menu_right_item, null);
						((ImageView) ((RelativeLayout) view).getChildAt(1)).setImageResource(R.drawable.menu_right_7_location_web_site);
						((TextView) ((RelativeLayout) view).getChildAt(2)).setText((String) data.get(position));
						((TextView) ((RelativeLayout) view).getChildAt(2)).setTypeface(akashi);
						setMenuRightItemColor(view, 0);
						break;
					case 1:
						view = layoutInflater.inflate(R.layout.menu_right_item, null);
						((ImageView) ((RelativeLayout) view).getChildAt(1)).setImageResource(R.drawable.menu_right_5_content_discard);
						((TextView) ((RelativeLayout) view).getChildAt(2)).setText((String) data.get(position));
						((TextView) ((RelativeLayout) view).getChildAt(2)).setTypeface(akashi);
						setMenuRightItemColor(view, 0);
						break;
				}
				break;
			case 2:
				view = layoutInflater.inflate(R.layout.menu_right_item, null);
				((ImageView) ((RelativeLayout) view).getChildAt(1)).setImageResource(R.drawable.menu_right_6_social_group);
				((TextView) ((RelativeLayout) view).getChildAt(2)).setText(((AppInfo) data.get(position)).getAppName());
				((TextView) ((RelativeLayout) view).getChildAt(2)).setTypeface(akashi);
				setMenuRightItemColor(view, 0);
				break;
			case 21:
				switch (position) {
					case 0:
						view = layoutInflater.inflate(R.layout.menu_right_item, null);
						((ImageView) ((RelativeLayout) view).getChildAt(1)).setImageResource(R.drawable.menu_right_6_social_group);
						((TextView) ((RelativeLayout) view).getChildAt(2)).setText((String) data.get(position));
						((TextView) ((RelativeLayout) view).getChildAt(2)).setTypeface(akashi);
						setMenuRightItemColor(view, 0);
						break;
					case 1:
						view = layoutInflater.inflate(R.layout.menu_right_item, null);
						((ImageView) ((RelativeLayout) view).getChildAt(1)).setImageResource(R.drawable.menu_right_5_content_discard);
						((TextView) ((RelativeLayout) view).getChildAt(2)).setText((String) data.get(position));
						((TextView) ((RelativeLayout) view).getChildAt(2)).setTypeface(akashi);
						setMenuRightItemColor(view, 0);
						break;
				}
				break;
			case 3:
				view = layoutInflater.inflate(R.layout.menu_right_item, null);
				((ImageView) ((RelativeLayout) view).getChildAt(1)).setImageResource(R.drawable.menu_right_10_device_access_call);
				((TextView) ((RelativeLayout) view).getChildAt(2)).setText(((AppInfo) data.get(position)).getAppName());
				((TextView) ((RelativeLayout) view).getChildAt(2)).setTypeface(akashi);
				setMenuRightItemColor(view, 0);
				break;
			case 31:
				switch (position) {
					case 0:
						view = layoutInflater.inflate(R.layout.menu_right_item, null);
						((ImageView) ((RelativeLayout) view).getChildAt(1)).setImageResource(R.drawable.menu_right_10_device_access_call);
						((TextView) ((RelativeLayout) view).getChildAt(2)).setText((String) data.get(position));
						((TextView) ((RelativeLayout) view).getChildAt(2)).setTypeface(akashi);
						setMenuRightItemColor(view, 0);
						break;
					case 1:
						view = layoutInflater.inflate(R.layout.menu_right_item, null);
						((ImageView) ((RelativeLayout) view).getChildAt(1)).setImageResource(R.drawable.menu_right_5_content_discard);
						((TextView) ((RelativeLayout) view).getChildAt(2)).setText((String) data.get(position));
						((TextView) ((RelativeLayout) view).getChildAt(2)).setTypeface(akashi);
						setMenuRightItemColor(view, 0);
						break;
				}
				break;
			case 4:
				view = layoutInflater.inflate(R.layout.menu_right_item, null);
				((ImageView) ((RelativeLayout) view).getChildAt(1)).setImageResource(R.drawable.menu_right_6_social_chat);
				((TextView) ((RelativeLayout) view).getChildAt(2)).setText(((AppInfo) data.get(position)).getAppName());
				((TextView) ((RelativeLayout) view).getChildAt(2)).setTypeface(akashi);
				setMenuRightItemColor(view, 0);
				break;
			case 41:
				switch (position) {
					case 0:
						view = layoutInflater.inflate(R.layout.menu_right_item, null);
						((ImageView) ((RelativeLayout) view).getChildAt(1)).setImageResource(R.drawable.menu_right_6_social_chat);
						((TextView) ((RelativeLayout) view).getChildAt(2)).setText((String) data.get(position));
						((TextView) ((RelativeLayout) view).getChildAt(2)).setTypeface(akashi);
						setMenuRightItemColor(view, 0);
						break;
					case 1:
						view = layoutInflater.inflate(R.layout.menu_right_item, null);
						((ImageView) ((RelativeLayout) view).getChildAt(1)).setImageResource(R.drawable.menu_right_5_content_discard);
						((TextView) ((RelativeLayout) view).getChildAt(2)).setText((String) data.get(position));
						((TextView) ((RelativeLayout) view).getChildAt(2)).setTypeface(akashi);
						setMenuRightItemColor(view, 0);
						break;
				}
				break;
			case 5:
				view = layoutInflater.inflate(R.layout.menu_right_item, null);
				((ImageView) ((RelativeLayout) view).getChildAt(1)).setImageResource(R.drawable.menu_right_10_device_access_storage);
				((TextView) ((RelativeLayout) view).getChildAt(2)).setText(((AppInfo) data.get(position)).getAppName());
				((TextView) ((RelativeLayout) view).getChildAt(2)).setTypeface(akashi);
				setMenuRightItemColor(view, 0);
				break;
			case 51:
				switch (position) {
					case 0:
						view = layoutInflater.inflate(R.layout.menu_right_item, null);
						((ImageView) ((RelativeLayout) view).getChildAt(1)).setImageResource(R.drawable.menu_right_10_device_access_storage);
						((TextView) ((RelativeLayout) view).getChildAt(2)).setText((String) data.get(position));
						((TextView) ((RelativeLayout) view).getChildAt(2)).setTypeface(akashi);
						setMenuRightItemColor(view, 0);
						break;
					case 1:
						view = layoutInflater.inflate(R.layout.menu_right_item, null);
						((ImageView) ((RelativeLayout) view).getChildAt(1)).setImageResource(R.drawable.menu_right_5_content_discard);
						((TextView) ((RelativeLayout) view).getChildAt(2)).setText((String) data.get(position));
						((TextView) ((RelativeLayout) view).getChildAt(2)).setTypeface(akashi);
						setMenuRightItemColor(view, 0);
						break;
				}
				break;
			case 6:
				if (position != 0) {
					view = layoutInflater.inflate(R.layout.menu_right_item, null);
					((TextView) ((RelativeLayout) view).getChildAt(2)).setText((String) data.get(position));
					((TextView) ((RelativeLayout) view).getChildAt(2)).setTypeface(akashi);
				}
				switch (position) {
					case 0:
						view = layoutInflater.inflate(R.layout.menu_right_item_ii, null);
						((ImageView) ((RelativeLayout) view).getChildAt(1)).setImageResource(R.drawable.menu_right_2_action_about);
						((TextView) ((RelativeLayout) view).getChildAt(2)).setText((String) data.get(position));
						((TextView) ((RelativeLayout) view).getChildAt(2)).setTypeface(akashi);
						((TextView) ((RelativeLayout) view).getChildAt(3)).setText("Copyright \u00a9 2013 Miras Absar");
						((TextView) ((RelativeLayout) view).getChildAt(3)).setTypeface(akashi);
						setMenuRightItemColor(view, 0);
						break;
					case 1:
						((ImageView) ((RelativeLayout) view).getChildAt(1)).setImageResource(R.drawable.menu_right_2_action_about);
						setMenuRightItemColor(view, 0);
						break;
					case 2:
						((ImageView) ((RelativeLayout) view).getChildAt(1)).setImageResource(R.drawable.menu_right_10_device_access_network_wifi);
						if (hwSupportsWifi) {
							if (wifiManager.isWifiEnabled()) {
								setMenuRightItemColor(view, 1);
							}
							else {
								setMenuRightItemColor(view, 0);
							}
						}
						else {
							setMenuRightItemColor(view, 0);
						}
						break;
					case 3:
						((ImageView) ((RelativeLayout) view).getChildAt(1)).setImageResource(R.drawable.menu_right_10_device_access_bluetooth);
						if (hwSupportsBt) {
							if (btAdapter.isEnabled()) {
								setMenuRightItemColor(view, 1);
							}
							else {
								setMenuRightItemColor(view, 0);
							}
						}
						else {
							setMenuRightItemColor(view, 0);
						}
						break;
					case 4:
						if (hwSupportsGps) {
							if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
								((ImageView) ((RelativeLayout) view).getChildAt(1)).setImageResource(R.drawable.menu_right_10_device_access_location_found);
								setMenuRightItemColor(view, 1);
							}
							else {
								((ImageView) ((RelativeLayout) view).getChildAt(1)).setImageResource(R.drawable.menu_right_10_device_access_location_off);
								setMenuRightItemColor(view, 0);
							}
						}
						else {
							((ImageView) ((RelativeLayout) view).getChildAt(1)).setImageResource(R.drawable.menu_right_10_device_access_location_off);
							setMenuRightItemColor(view, 0);
						}
						break;
					case 5:
						if (data.get(position) == "Orientation Unlocked") {
							((ImageView) ((RelativeLayout) view).getChildAt(1)).setImageResource(R.drawable.menu_right_10_device_access_screen_rotation);
						}
						if (data.get(position) == "Orientation Locked to Portrait") {
							((ImageView) ((RelativeLayout) view).getChildAt(1)).setImageResource(R.drawable.menu_right_10_device_access_screen_locked_to_portrait);
						}
						if (data.get(position) == "Orientation Locked to Landscape") {
							((ImageView) ((RelativeLayout) view).getChildAt(1)).setImageResource(R.drawable.menu_right_10_device_access_screen_locked_to_landscape);
						}
						if (data.get(position) == "Orientation Lock Unsupported") {
							((ImageView) ((RelativeLayout) view).getChildAt(1)).setImageResource(R.drawable.menu_right_10_device_access_screen_locked_to_portrait);
						}
						setMenuRightItemColor(view, 0);
						break;
					case 6:
						((ImageView) ((RelativeLayout) view).getChildAt(1)).setImageResource(R.drawable.menu_right_2_action_settings);
						setMenuRightItemColor(view, 0);
						break;
					case 7:
						((ImageView) ((RelativeLayout) view).getChildAt(1)).setImageResource(R.drawable.menu_right_1_navigation_refresh);
						setMenuRightItemColor(view, 0);
						break;
					case 8:
						((ImageView) ((RelativeLayout) view).getChildAt(1)).setImageResource(R.drawable.menu_right_11_alerts_and_states_warning);
						setMenuRightItemColor(view, 0);
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
				setMenuRightItemColor(view, 0);
				break;
			case 62:
				view = layoutInflater.inflate(R.layout.menu_right_item, null);
				((TextView) ((RelativeLayout) view).getChildAt(2)).setText((String) data.get(position));
				((TextView) ((RelativeLayout) view).getChildAt(2)).setTypeface(akashi);
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
				setMenuRightItemColor(view, 0);
				break;
			case 63:
				view = layoutInflater.inflate(R.layout.menu_right_item, null);
				((TextView) ((RelativeLayout) view).getChildAt(2)).setText((String) data.get(position));
				((TextView) ((RelativeLayout) view).getChildAt(2)).setTypeface(akashi);
				((ImageView) ((RelativeLayout) view).getChildAt(1)).setImageResource(R.drawable.menu_right_2_action_settings);
				setMenuRightItemColor(view, 0);
				break;
		}
		view.setTag(new Integer(position));
		view.setOnTouchListener(menuRightItemOtl);
		return view;
	}

	public void setMenuRightItemColor(View a, int b) {
		if (b == 0) {
			a.setBackgroundColor(prefs.getMenuRightPlateColorPassive());
			((ImageView) ((RelativeLayout) a).getChildAt(0)).setColorFilter(prefs.getMenuRightSpaghettiColorPassive());
			((ImageView) ((RelativeLayout) a).getChildAt(1)).setColorFilter(prefs.getMenuRightSauceColorPassive());
			((TextView) ((RelativeLayout) a).getChildAt(2)).setTextColor(prefs.getMenuRightTextColorPassive());
			if (((RelativeLayout) a).getChildCount() == 4) {
				((TextView) ((RelativeLayout) a).getChildAt(3)).setTextColor(prefs.getMenuRightTextColorPassive());
			}
		}
		else {
			a.setBackgroundColor(prefs.getMenuRightPlateColorActive());
			((ImageView) ((RelativeLayout) a).getChildAt(0)).setColorFilter(prefs.getMenuRightSpaghettiColorActive());
			((ImageView) ((RelativeLayout) a).getChildAt(1)).setColorFilter(prefs.getMenuRightSauceColorActive());
			((TextView) ((RelativeLayout) a).getChildAt(2)).setTextColor(prefs.getMenuRightTextColorActive());
			if (((RelativeLayout) a).getChildCount() == 4) {
				((TextView) ((RelativeLayout) a).getChildAt(3)).setTextColor(prefs.getMenuRightTextColorPassive());
			}
		}
	}
}