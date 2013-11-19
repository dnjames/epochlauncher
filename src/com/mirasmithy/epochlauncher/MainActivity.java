package com.mirasmithy.epochlauncher;

import android.app.*;
import android.bluetooth.*;
import android.content.*;
import android.content.pm.*;
import android.content.res.*;
import android.graphics.*;
import android.location.*;
import android.net.*;
import android.net.wifi.*;
import android.os.*;
import android.provider.*;
import android.util.*;
import android.view.*;
import android.view.View.*;
import android.view.ViewTreeObserver.*;
import android.view.animation.*;
import android.widget.*;
import android.widget.AbsListView.*;
import java.io.*;
import java.util.*;

import android.view.View.OnLongClickListener;
import android.view.View.OnTouchListener;

public class MainActivity extends Activity {
	Context context;

	PackageManager packageManager;
	boolean hwSupportsWifi;
	WifiManager wifiManager;
	boolean hwSupportsBt;
	BluetoothAdapter btAdapter;
	boolean hwSupportsGps;
	LocationManager locationManager;

	DisplayMetrics dispMetrics;
	int dispResX;
	int dispResY;
	double dispDensity;

	RelativeLayout parent;
	LinearLayout menuLeft;
	RelativeLayout menuLeftIconInternet;
	RelativeLayout menuLeftIconContacts;
	RelativeLayout menuLeftIconPhone;
	RelativeLayout menuLeftIconMessaging;
	RelativeLayout menuLeftIconApps;
	RelativeLayout menuLeftIconPrefs;
	ImageView pointerMenuLeft;
	ImageView separatorMenuLeftRight;
	ImageView separatorMenuLeftRightII;
	ListView menuRight;
	ImageView pointerUp;
	ImageView pointerDown;

	Thread getPrefs;
	Prefs prefs;

	int menuLeftHeight;
	int menuLeftIconHeight;
	int pointerMenuLeftHeight;
	boolean dispSupportsPrefsOrientation;

	int appMode;
	GetBrowsersCommunicator getBrowsersCommunicator;
	Thread getBrowsers;
	ArrayList<AppInfo> browsers;
	GetContactsAppsCommunicator getContactsAppsCommunicator;
	Thread getContactsApps;
	ArrayList<AppInfo> contactsApps;
	GetPhoneAppsCommunicator getPhoneAppsCommunicator;
	Thread getPhoneApps;
	ArrayList<AppInfo> phoneApps;
	GetAppsCommunicator getAppsCommunicator;
	Thread getApps;
	ArrayList<AppInfo> Apps;
	Animation fadeIn;

	int lastClickPosition;
	ArrayList<String> secondLevelList;
	OnTouchListener menuRightItemOtl;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mainactivity);

		context = getApplicationContext();

		if (Runtime.getRuntime().availableProcessors() == 1) {
			Toast.makeText(context, "WARNING! Epoch Launcher is meant to run on multi-core devices. Do not expect a buttery experience on single-core devices.", Toast.LENGTH_SHORT).show();
			Toast.makeText(context, "WARNING! Epoch Launcher is meant to run on multi-core devices. Do not expect a buttery experience on single-core devices.", Toast.LENGTH_SHORT).show();
			Toast.makeText(context, "WARNING! Epoch Launcher is meant to run on multi-core devices. Do not expect a buttery experience on single-core devices.", Toast.LENGTH_SHORT).show();
			Toast.makeText(context, "WARNING! Epoch Launcher is meant to run on multi-core devices. Do not expect a buttery experience on single-core devices.", Toast.LENGTH_SHORT).show();
			Toast.makeText(context, "WARNING! Epoch Launcher is meant to run on multi-core devices. Do not expect a buttery experience on single-core devices.", Toast.LENGTH_SHORT).show();
		}
		packageManager = getPackageManager();
		hwSupportsWifi = packageManager.hasSystemFeature(PackageManager.FEATURE_WIFI);
		wifiManager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
		hwSupportsBt = packageManager.hasSystemFeature(PackageManager.FEATURE_BLUETOOTH);
		btAdapter = BluetoothAdapter.getDefaultAdapter();
		hwSupportsGps = packageManager.hasSystemFeature(PackageManager.FEATURE_LOCATION_GPS);
		locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

		getDispMetrics();
		setWallpaperParams();

		parent = (RelativeLayout) findViewById(R.id.parent);
		menuLeft = (LinearLayout) findViewById(R.id.menuLeft);
		menuLeftIconInternet = (RelativeLayout) findViewById(R.id.menuLeftIconInternet);
		menuLeftIconInternet.setTag(new Integer(1));
		menuLeftIconContacts = (RelativeLayout) findViewById(R.id.menuLeftIconContacts);
		menuLeftIconContacts.setTag(new Integer(2));
		menuLeftIconPhone = (RelativeLayout) findViewById(R.id.menuLeftIconPhone);
		menuLeftIconPhone.setTag(new Integer(3));
		menuLeftIconMessaging = (RelativeLayout) findViewById(R.id.menuLeftIconMessaging);
		menuLeftIconMessaging.setTag(new Integer(4));
		menuLeftIconApps = (RelativeLayout) findViewById(R.id.menuLeftIconApps);
		menuLeftIconApps.setTag(new Integer(5));
		menuLeftIconPrefs = (RelativeLayout) findViewById(R.id.menuLeftIconPrefs);
		menuLeftIconPrefs.setTag(new Integer(6));
		pointerMenuLeft = (ImageView) findViewById(R.id.pointerMenuLeft);
		separatorMenuLeftRight = (ImageView) findViewById(R.id.separatorMenuLeftRight);
		separatorMenuLeftRightII = (ImageView) findViewById(R.id.separatorMenuLeftRightII);
		menuRight = (ListView) findViewById(R.id.menuRight);
		pointerUp = (ImageView) findViewById(R.id.pointerUp);
		pointerDown = (ImageView) findViewById(R.id.pointerDown);

		GetPrefsCommunicator getPrefsCommunicator = new GetPrefsCommunicator();
		getPrefsCommunicator.setContext(context);
		getPrefs = new Thread(new GetPrefs());
		getPrefs.start();
		while (!getPrefsCommunicator.getHasFinished()) {
		}
		if (getPrefsCommunicator.getHasCrashed()) {
			Toast.makeText(context, "ERROR! Epoch Launcher has crashed. This can be caused by a lack of internal storage space. Try to free up some space and relaunch Epoch Launcher. If the problem persists, contact the developer at miras.absar@gmail.com", Toast.LENGTH_SHORT).show();
			Toast.makeText(context, "ERROR! Epoch Launcher has crashed. This can be caused by a lack of internal storage space. Try to free up some space and relaunch Epoch Launcher. If the problem persists, contact the developer at miras.absar@gmail.com", Toast.LENGTH_SHORT).show();
			Toast.makeText(context, "ERROR! Epoch Launcher has crashed. This can be caused by a lack of internal storage space. Try to free up some space and relaunch Epoch Launcher. If the problem persists, contact the developer at miras.absar@gmail.com", Toast.LENGTH_SHORT).show();
			Toast.makeText(context, "ERROR! Epoch Launcher has crashed. This can be caused by a lack of internal storage space. Try to free up some space and relaunch Epoch Launcher. If the problem persists, contact the developer at miras.absar@gmail.com", Toast.LENGTH_SHORT).show();
			Toast.makeText(context, "ERROR! Epoch Launcher has crashed. This can be caused by a lack of internal storage space. Try to free up some space and relaunch Epoch Launcher. If the problem persists, contact the developer at miras.absar@gmail.com", Toast.LENGTH_SHORT).show();
			Toast.makeText(context, "ERROR! Epoch Launcher has crashed. This can be caused by a lack of internal storage space. Try to free up some space and relaunch Epoch Launcher. If the problem persists, contact the developer at miras.absar@gmail.com", Toast.LENGTH_SHORT).show();
			Toast.makeText(context, "ERROR! Epoch Launcher has crashed. This can be caused by a lack of internal storage space. Try to free up some space and relaunch Epoch Launcher. If the problem persists, contact the developer at miras.absar@gmail.com", Toast.LENGTH_SHORT).show();
			Toast.makeText(context, "ERROR! Epoch Launcher has crashed. This can be caused by a lack of internal storage space. Try to free up some space and relaunch Epoch Launcher. If the problem persists, contact the developer at miras.absar@gmail.com", Toast.LENGTH_SHORT).show();
			Toast.makeText(context, "ERROR! Epoch Launcher has crashed. This can be caused by a lack of internal storage space. Try to free up some space and relaunch Epoch Launcher. If the problem persists, contact the developer at miras.absar@gmail.com", Toast.LENGTH_SHORT).show();
			Toast.makeText(context, "ERROR! Epoch Launcher has crashed. This can be caused by a lack of internal storage space. Try to free up some space and relaunch Epoch Launcher. If the problem persists, contact the developer at miras.absar@gmail.com", Toast.LENGTH_SHORT).show();
			clearLauncherDefaults();
			finish();
		}
		prefs = getPrefsCommunicator.getPrefs();

		ViewTreeObserver parentVto = parent.getViewTreeObserver();
		parentVto.addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
				@Override
				public void onGlobalLayout() {
					menuLeftHeight = menuLeft.getHeight();
					menuLeftIconHeight = menuLeftIconInternet.getHeight();
					pointerMenuLeftHeight = pointerMenuLeft.getHeight();
					if (menuLeftHeight > dispResX || menuLeftHeight > dispResY) {
						dispSupportsPrefsOrientation = false;
						setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
					}
					else {
						dispSupportsPrefsOrientation = true;
						switch (prefs.getDispPrefsOrientation()) {
							case 0:
								setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
								break;
							case 1:
								setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
								break;
							case 2:
								setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
								break;
						}
					}
					parent.getViewTreeObserver().removeGlobalOnLayoutListener(this);
				}
			});

		fadeIn = AnimationUtils.loadAnimation(context, R.anim.fade_in);
		setAppMode(0);

		parent.setOnLongClickListener(new OnLongClickListener() {
				@Override
				public boolean onLongClick(View view) {
					startActivity(Intent.createChooser(new Intent(Intent.ACTION_SET_WALLPAPER), "Select Wallpaper"));
					return true;
				}
			});

		OnTouchListener menuLeftIconOtl = new OnTouchListener() {
			@Override
			public boolean onTouch(View view, MotionEvent motionEvent) {
				switch (((Integer) view.getTag()).intValue()) {
					case 1:
						if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
							setMenuLeftIconColor(view, 1);
						}
						if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
							if (appMode == 1) {
								setAppMode(0);
							}
							else {
								if ((motionEvent.getEventTime() - motionEvent.getDownTime()) <= 300) {
									setMenuLeftIconColor(view, 0);
									try {
										startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.google.com/")));
									}
									catch (Exception e) {
										Toast.makeText(context, "No Browsers Installed", Toast.LENGTH_SHORT).show();
									}
								}
								else {
									setAppMode(1);
								}
							}
						}
						break;
					case 2:
						if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
							setMenuLeftIconColor(view, 1);
						}
						if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
							if (appMode == 2) {
								setAppMode(0);
							}
							else {
								if ((motionEvent.getEventTime() - motionEvent.getDownTime()) <= 300) {
									setMenuLeftIconColor(view, 0);
									try {
										startActivity(new Intent(Intent.ACTION_VIEW, ContactsContract.Contacts.CONTENT_URI));
									}
									catch (Exception e) {
										Toast.makeText(context, "No Contacts Applications Installed", Toast.LENGTH_SHORT).show();
									}
								}
								else {
									setAppMode(2);
								}
							}
						}
						break;
					case 3:
						if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
							setMenuLeftIconColor(view, 1);
						}
						if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
							if (appMode == 3) {
								setAppMode(0);
							}
							else {
								if ((motionEvent.getEventTime() - motionEvent.getDownTime()) <= 300) {
									setMenuLeftIconColor(view, 0);
									try {
										startActivity(new Intent(Intent.ACTION_DIAL, null));
									}
									catch (Exception e) {
										Toast.makeText(context, "No Phone Applications Installed", Toast.LENGTH_SHORT).show();
									}
								}
								else {
									setAppMode(3);
								}
							}
						}
						break;
					case 4:
						if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
							setMenuLeftIconColor(view, 1);
						}
						if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
							setMenuLeftIconColor(view, 0);
							if ((motionEvent.getEventTime() - motionEvent.getDownTime()) <= 300) {
								launch("com.android.mms");
							}
							else {
								launch("com.google.android.talk");
							}
						}
						break;
					case 5:
						if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
							setMenuLeftIconColor(view, 1);
						}
						if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
							if (appMode == 5) {
								setAppMode(0);
							}
							else {
								if ((motionEvent.getEventTime() - motionEvent.getDownTime()) <= 300) {
									setAppMode(5);
								}
								else {
									setMenuLeftIconColor(view, 0);
									launch("com.android.vending");
								}
							}
						}
						break;
					case 6:
						if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
							setMenuLeftIconColor(view, 1);
						}
						if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
							if (appMode == 6) {
								setAppMode(0);
							}
							else {
								if ((motionEvent.getEventTime() - motionEvent.getDownTime()) <= 300) {
									setAppMode(6);
								}
								else {
									launch("com.android.settings");
								}
							}
						}
						break;
				}
				return true;
			}
		};
		menuLeftIconInternet.setOnTouchListener(menuLeftIconOtl);
		menuLeftIconContacts.setOnTouchListener(menuLeftIconOtl);
		menuLeftIconPhone.setOnTouchListener(menuLeftIconOtl);
		menuLeftIconMessaging.setOnTouchListener(menuLeftIconOtl);
		menuLeftIconApps.setOnTouchListener(menuLeftIconOtl);
		menuLeftIconPrefs.setOnTouchListener(menuLeftIconOtl);

		menuRight.setOnScrollListener(new OnScrollListener()
			{
				public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
					if (firstVisibleItem == 0 && firstVisibleItem + visibleItemCount != totalItemCount) {
						pointerUp.setColorFilter(Color.argb(0, 255, 255, 255), PorterDuff.Mode.MULTIPLY);
						pointerDown.setColorFilter(prefs.getPointerUpDownColor(), PorterDuff.Mode.MULTIPLY);
					}
					if (firstVisibleItem != 0 && firstVisibleItem + visibleItemCount != totalItemCount) {
						pointerUp.setColorFilter(prefs.getPointerUpDownColor(), PorterDuff.Mode.MULTIPLY);
						pointerDown.setColorFilter(prefs.getPointerUpDownColor(), PorterDuff.Mode.MULTIPLY);
					}
					if (firstVisibleItem != 0 && firstVisibleItem + visibleItemCount == totalItemCount) {
						pointerUp.setColorFilter(prefs.getPointerUpDownColor(), PorterDuff.Mode.MULTIPLY);
						pointerDown.setColorFilter(Color.argb(0, 255, 255, 255), PorterDuff.Mode.MULTIPLY);
					}
					if (firstVisibleItem == 0 && firstVisibleItem + visibleItemCount == totalItemCount) {
						pointerUp.setColorFilter(Color.argb(0, 255, 255, 255), PorterDuff.Mode.MULTIPLY);
						pointerDown.setColorFilter(Color.argb(0, 255, 255, 255), PorterDuff.Mode.MULTIPLY);
					}
				}
				public void onScrollStateChanged(AbsListView view, int scrollState) {
				}
			});

		menuRightItemOtl = new OnTouchListener() {
			@Override
			public boolean onTouch(View view, MotionEvent motionEvent) {
				if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
					setMenuRightItemColor(view, 1);
				}
				if (motionEvent.getAction() == MotionEvent.ACTION_CANCEL) {
					if (appMode == 6 && (((Integer) view.getTag()).intValue() == 2 || ((Integer) view.getTag()).intValue() == 3 || ((Integer) view.getTag()).intValue() == 4)) {
					}
					else {
						setMenuRightItemColor(view, 0);
					}
				}
				if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
					switch (appMode) {
						case 1:
							if (getBrowsersCommunicator.getBrowsers().get(((Integer) view.getTag()).intValue()).getPackageName() == "") {
								setAppMode(0);
							}
							else {
								if ((motionEvent.getEventTime() - motionEvent.getDownTime()) <= 300) {
									launch(getBrowsersCommunicator.getBrowsers().get(((Integer) view.getTag()).intValue()).getPackageName());
								}
								else {
									lastClickPosition = ((Integer) view.getTag()).intValue();
									setAppMode(11);
								}
							}
							break;
						case 11:
							if (((Integer) view.getTag()).intValue() == 0) {
								launch(getBrowsersCommunicator.getBrowsers().get(lastClickPosition).getPackageName());
							}
							else {
								uninstall(getBrowsersCommunicator.getBrowsers().get(lastClickPosition).getPackageName());
							}
							break;
						case 2:
							if (getContactsAppsCommunicator.getContactsApps().get(((Integer) view.getTag()).intValue()).getPackageName() == "") {
								setAppMode(0);
							}
							else {
								if ((motionEvent.getEventTime() - motionEvent.getDownTime()) <= 300) {
									launch(getContactsAppsCommunicator.getContactsApps().get(((Integer) view.getTag()).intValue()).getPackageName());
								}
								else {
									lastClickPosition = ((Integer) view.getTag()).intValue();
									setAppMode(21);
								}
							}
							break;
						case 21:
							if (((Integer) view.getTag()).intValue() == 0) {
								launch(getContactsAppsCommunicator.getContactsApps().get(lastClickPosition).getPackageName());
							}
							else {
								uninstall(getContactsAppsCommunicator.getContactsApps().get(lastClickPosition).getPackageName());
							}
							break;
						case 3:
							if (getPhoneAppsCommunicator.getPhoneApps().get(((Integer) view.getTag()).intValue()).getPackageName() == "") {
								setAppMode(0);
							}
							else {
								if ((motionEvent.getEventTime() - motionEvent.getDownTime()) <= 300) {
									launch(getPhoneAppsCommunicator.getPhoneApps().get(((Integer) view.getTag()).intValue()).getPackageName());
								}
								else {
									lastClickPosition = ((Integer) view.getTag()).intValue();
									setAppMode(31);
								}
							}
							break;
						case 31:
							if (((Integer) view.getTag()).intValue() == 0) {
								launch(getPhoneAppsCommunicator.getPhoneApps().get(lastClickPosition).getPackageName());
							}
							else {
								uninstall(getPhoneAppsCommunicator.getPhoneApps().get(lastClickPosition).getPackageName());
							}
							break;
						case 5:
							if ((motionEvent.getEventTime() - motionEvent.getDownTime()) <= 300) {
								launch(getAppsCommunicator.getApps().get(((Integer) view.getTag()).intValue()).getPackageName());
							}
							else {
								lastClickPosition = ((Integer) view.getTag()).intValue();
								setAppMode(51);
							}
							break;
						case 51:
							switch (((Integer) view.getTag()).intValue()) {
								case 0:
									launch(getAppsCommunicator.getApps().get(lastClickPosition).getPackageName());
									break;
								case 1:
									uninstall(getAppsCommunicator.getApps().get(lastClickPosition).getPackageName());
									break;
							}
							break;
						case 6:
							switch (((Integer) view.getTag()).intValue()) {
								case 0:
									setMenuRightItemColor(view, 0);
									break;
								case 1:
									setAppMode(61);
									break;
								case 2:
									if (hwSupportsWifi) {
										if (wifiManager.isWifiEnabled()) {
											wifiManager.setWifiEnabled(false);
											setMenuRightItemColor(view, 0);
										}
										else {
											wifiManager.setWifiEnabled(true);
											setMenuRightItemColor(view, 1);
										}
									}
									break;
								case 3:
									if (hwSupportsBt) {
										if (btAdapter.isEnabled()) {
											btAdapter.disable();
											setMenuRightItemColor(view, 0);
										}
										else {
											btAdapter.enable();
											setMenuRightItemColor(view, 1);
										}
									}
									break;
								case 4:
									if (hwSupportsGps) {
										startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
									}
									break;
								case 5:
									if (dispSupportsPrefsOrientation) {
										setAppMode(62);
									}
									else {
										setMenuRightItemColor(view, 0);
									}
									break;
								case 6:
									setAppMode(63);
									break;
								case 7:
									restartApp();
									break;
								case 8:
									clearLauncherDefaults();
									break;
							}
							break;
						case 61:
							switch (((Integer) view.getTag()).intValue()) {
								case 0:
									startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://github.com/mirasmithy/epochlauncher")));
									startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.gnu.org/licenses/gpl-3.0.html")));
									break;
								case 1:
									startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://developer.android.com/design/downloads/index.html")));
									startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://creativecommons.org/licenses/by/2.5/legalcode")));
									break;
								case 2:
									startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://tenbytwenty.com/?xxxx_posts=akashi")));
									startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://scripts.sil.org/cms/scripts/page.php?item_id=OFL_web")));
									break;
								case 3:
									startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://developer.android.com/design/downloads/index.html")));
									startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.apache.org/licenses/LICENSE-2.0")));
									break;
								case 4:
									startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://developer.android.com/tools/support-library/index.html")));
									startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.apache.org/licenses/LICENSE-2.0")));
									break;
							}
							break;
						case 62:
							prefs.setDispPrefsOrientation(((Integer) view.getTag()).intValue());
							setPrefs();
							switch (prefs.getDispPrefsOrientation()) {
								case 0:
									setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
									break;
								case 1:
									setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
									break;
								case 2:
									setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
									break;
							}
							setAppMode(6);
							break;
						case 63:
							prefs.setAppTheme(((Integer) view.getTag()).intValue());
							setPrefs();
							setAppMode(6);
							break;
					}
				}
				return true;
			}
		};
	}

	public void animateMainElements() {
		pointerMenuLeft.startAnimation(fadeIn);
		separatorMenuLeftRight.startAnimation(fadeIn);
		separatorMenuLeftRightII.startAnimation(fadeIn);
		menuRight.startAnimation(fadeIn);
	}

	public void clearLauncherDefaults() {
		ComponentName clearLauncherDefaultsComponent = new ComponentName(context, FakeLauncher.class);
		packageManager.setComponentEnabledSetting(clearLauncherDefaultsComponent, PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP);
		Intent clearLauncherDefaultsIntent = new Intent(Intent.ACTION_MAIN);
		clearLauncherDefaultsIntent.addCategory(Intent.CATEGORY_HOME);
		startActivity(clearLauncherDefaultsIntent);
		packageManager.setComponentEnabledSetting(clearLauncherDefaultsComponent, PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);
	}

	public void getDispMetrics() {
		dispMetrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dispMetrics);
		dispResX = dispMetrics.widthPixels;
		dispResY = dispMetrics.heightPixels;
		dispDensity = dispMetrics.density;
	}

	public void inflateMenuRight(int layout, ArrayList data) {
		menuRight.setAdapter(new CustomArrayAdapter(this, layout, data,
													appMode, prefs, packageManager,
													hwSupportsWifi, wifiManager, hwSupportsBt,
													btAdapter, hwSupportsGps, locationManager,
													menuRightItemOtl));
	}

	public void launch(String packageName) {
		try {
			startActivity(getPackageManager().getLaunchIntentForPackage(packageName));
		}
		catch (Exception e) {
			if (packageName == "com.android.mms") {
				Toast.makeText(context, "Messaging Not Installed", Toast.LENGTH_SHORT).show();
			}
			if (packageName == "com.google.android.talk") {
				Toast.makeText(context, "Hangouts Not Installed", Toast.LENGTH_SHORT).show();
			}
			if (packageName == "com.android.vending") {
				Toast.makeText(context, "Play Store Not Installed", Toast.LENGTH_SHORT).show();
			}
		}
	}

	public void restartApp() {
		finish();
		launch("com.mirasmithy.epochlauncher");
	}

	public void setPrefs() {
		try {
			FileOutputStream setPrefsFos = context.getApplicationContext().openFileOutput("prefs", context.MODE_PRIVATE);
			ObjectOutputStream setPrefsOos = new ObjectOutputStream(setPrefsFos);
			setPrefsOos.writeObject(prefs);
			setPrefsOos.close();
			setPrefsFos.close();
		}
		catch (Exception e) {
		}
	}

	public void setAppMode(int a) {
		appMode = a;
		setMenuLeftPointerMenuLeftLayoutParams();
		setPointerMenuLeftLayoutParams();
		switch (appMode) {
			case 0:
				setMenuLeftIconColor(menuLeftIconInternet, 0);
				setMenuLeftIconColor(menuLeftIconContacts, 0);
				setMenuLeftIconColor(menuLeftIconPhone, 0);
				setMenuLeftIconColor(menuLeftIconMessaging, 0);
				setMenuLeftIconColor(menuLeftIconApps, 0);
				setMenuLeftIconColor(menuLeftIconPrefs, 0);
				pointerMenuLeft.setColorFilter(Color.argb(0, 255, 255, 255), PorterDuff.Mode.MULTIPLY);
				separatorMenuLeftRight.setBackgroundColor(Color.argb(0, 255, 255, 255));
				separatorMenuLeftRightII.setBackgroundColor(Color.argb(0, 255, 255, 255));
				inflateMenuRight(R.layout.menu_right_item, new ArrayList<AppInfo>());
				break;
			case 1:
				getBrowsersCommunicator = new GetBrowsersCommunicator();
				getBrowsersCommunicator.setPackageManager(packageManager);
				getBrowsers = new Thread(new GetBrowsers());
				getBrowsers.start();
				setMenuLeftIconColor(menuLeftIconInternet, 1);
				setMenuLeftIconColor(menuLeftIconContacts, 0);
				setMenuLeftIconColor(menuLeftIconPhone, 0);
				setMenuLeftIconColor(menuLeftIconMessaging, 0);
				setMenuLeftIconColor(menuLeftIconApps, 0);
				setMenuLeftIconColor(menuLeftIconPrefs, 0);
				pointerMenuLeft.setColorFilter(prefs.getPointerMenuLeftColor(), PorterDuff.Mode.MULTIPLY);
				separatorMenuLeftRight.setBackgroundColor(prefs.getSeparatorMenuLeftRightColor());
				separatorMenuLeftRightII.setBackgroundColor(prefs.getSeparatorMenuLeftRightColor());
				while (!getBrowsersCommunicator.getHasFinished()) {
				}
				inflateMenuRight(R.layout.menu_right_item, getBrowsersCommunicator.getBrowsers());
				animateMainElements();
				break;
			case 11:
				secondLevelList = new ArrayList<String>();
				secondLevelList.add("Launch " + getBrowsersCommunicator.getBrowsers().get(lastClickPosition).getAppName());
				secondLevelList.add("Uninstall " + getBrowsersCommunicator.getBrowsers().get(lastClickPosition).getAppName());
				inflateMenuRight(R.layout.menu_right_item, secondLevelList);
				animateMainElements();
				break;
			case 2:
				getContactsAppsCommunicator = new GetContactsAppsCommunicator();
				getContactsAppsCommunicator.setPackageManager(packageManager);
				getContactsApps = new Thread(new GetContactsApps());
				getContactsApps.start();
				setMenuLeftIconColor(menuLeftIconInternet, 0);
				setMenuLeftIconColor(menuLeftIconContacts, 1);
				setMenuLeftIconColor(menuLeftIconPhone, 0);
				setMenuLeftIconColor(menuLeftIconMessaging, 0);
				setMenuLeftIconColor(menuLeftIconApps, 0);
				setMenuLeftIconColor(menuLeftIconPrefs, 0);
				pointerMenuLeft.setColorFilter(prefs.getPointerMenuLeftColor(), PorterDuff.Mode.MULTIPLY);
				separatorMenuLeftRight.setBackgroundColor(prefs.getSeparatorMenuLeftRightColor());
				separatorMenuLeftRightII.setBackgroundColor(prefs.getSeparatorMenuLeftRightColor());
				while (!getContactsAppsCommunicator.getHasFinished()) {
				}
				inflateMenuRight(R.layout.menu_right_item, getContactsAppsCommunicator.getContactsApps());
				animateMainElements();
				break;
			case 21:
				secondLevelList = new ArrayList<String>();
				secondLevelList.add("Launch " + getContactsAppsCommunicator.getContactsApps().get(lastClickPosition).getAppName());
				secondLevelList.add("Uninstall " + getContactsAppsCommunicator.getContactsApps().get(lastClickPosition).getAppName());
				inflateMenuRight(R.layout.menu_right_item, secondLevelList);
				animateMainElements();
				break;
			case 3:
				getPhoneAppsCommunicator = new GetPhoneAppsCommunicator();
				getPhoneAppsCommunicator.setPackageManager(packageManager);
				getPhoneApps = new Thread(new GetPhoneApps());
				getPhoneApps.start();
				setMenuLeftIconColor(menuLeftIconInternet, 0);
				setMenuLeftIconColor(menuLeftIconContacts, 0);
				setMenuLeftIconColor(menuLeftIconPhone, 1);
				setMenuLeftIconColor(menuLeftIconMessaging, 0);
				setMenuLeftIconColor(menuLeftIconApps, 0);
				setMenuLeftIconColor(menuLeftIconPrefs, 0);
				pointerMenuLeft.setColorFilter(prefs.getPointerMenuLeftColor(), PorterDuff.Mode.MULTIPLY);
				separatorMenuLeftRight.setBackgroundColor(prefs.getSeparatorMenuLeftRightColor());
				separatorMenuLeftRightII.setBackgroundColor(prefs.getSeparatorMenuLeftRightColor());
				while (!getPhoneAppsCommunicator.getHasFinished()) {
				}
				inflateMenuRight(R.layout.menu_right_item, getPhoneAppsCommunicator.getPhoneApps());
				animateMainElements();
				break;
			case 31:
				secondLevelList = new ArrayList<String>();
				secondLevelList.add("Launch " + getPhoneAppsCommunicator.getPhoneApps().get(lastClickPosition).getAppName());
				secondLevelList.add("Uninstall " + getPhoneAppsCommunicator.getPhoneApps().get(lastClickPosition).getAppName());
				inflateMenuRight(R.layout.menu_right_item, secondLevelList);
				animateMainElements();
				break;
			case 5:
				getAppsCommunicator = new GetAppsCommunicator();
				getAppsCommunicator.setPackageManager(packageManager);
				getApps = new Thread(new GetApps());
				getApps.start();
				setMenuLeftIconColor(menuLeftIconInternet, 0);
				setMenuLeftIconColor(menuLeftIconContacts, 0);
				setMenuLeftIconColor(menuLeftIconPhone, 0);
				setMenuLeftIconColor(menuLeftIconMessaging, 0);
				setMenuLeftIconColor(menuLeftIconApps, 1);
				setMenuLeftIconColor(menuLeftIconPrefs, 0);
				pointerMenuLeft.setColorFilter(prefs.getPointerMenuLeftColor(), PorterDuff.Mode.MULTIPLY);
				separatorMenuLeftRight.setBackgroundColor(prefs.getSeparatorMenuLeftRightColor());
				separatorMenuLeftRightII.setBackgroundColor(prefs.getSeparatorMenuLeftRightColor());
				while (!getAppsCommunicator.getHasFinished()) {
				}
				inflateMenuRight(R.layout.menu_right_item, getAppsCommunicator.getApps());
				animateMainElements();
				break;
			case 51:
				secondLevelList = new ArrayList<String>();
				secondLevelList.add("Launch " + getAppsCommunicator.getApps().get(lastClickPosition).getAppName());
				secondLevelList.add("Uninstall " + getAppsCommunicator.getApps().get(lastClickPosition).getAppName());
				inflateMenuRight(R.layout.menu_right_item, secondLevelList);
				animateMainElements();
				break;
			case 6:
				setMenuLeftIconColor(menuLeftIconInternet, 0);
				setMenuLeftIconColor(menuLeftIconContacts, 0);
				setMenuLeftIconColor(menuLeftIconPhone, 0);
				setMenuLeftIconColor(menuLeftIconMessaging, 0);
				setMenuLeftIconColor(menuLeftIconApps, 0);
				setMenuLeftIconColor(menuLeftIconPrefs, 1);
				pointerMenuLeft.setColorFilter(prefs.getPointerMenuLeftColor(), PorterDuff.Mode.MULTIPLY);
				separatorMenuLeftRight.setBackgroundColor(prefs.getSeparatorMenuLeftRightColor());
				separatorMenuLeftRightII.setBackgroundColor(prefs.getSeparatorMenuLeftRightColor());
				ArrayList<String> prefsList = new ArrayList<String>();
				prefsList.add("Epoch Launcher 1.0");
				prefsList.add("Licensing");
				if (hwSupportsWifi) {
					prefsList.add("Wi-Fi");
				}
				else {
					prefsList.add("Wi-Fi Unsupported");
				}
				if (hwSupportsBt) {
					prefsList.add("Bluetooth");
				}
				else {
					prefsList.add("Bluetooth Unsupported");
				}
				if (hwSupportsGps) {
					prefsList.add("GPS");
				}
				else {
					prefsList.add("GPS Unsupported");
				}
				if (dispSupportsPrefsOrientation) {
					switch (prefs.getDispPrefsOrientation()) {
						case 0:
							prefsList.add("Orientation Unlocked");
							break;
						case 1:
							prefsList.add("Orientation Locked to Portrait");
							break;
						case 2:
							prefsList.add("Orientation Locked to Landscape");
							break;
					}
				}
				else {
					prefsList.add("Orientation Lock Unsupported");
				}
				prefsList.add("Change Theme");
				prefsList.add("Restart Epoch Launcher");
				prefsList.add("Clear Launcher Defaults");
				inflateMenuRight(R.layout.menu_right_item, prefsList);
				animateMainElements();
				break;
			case 61:
				secondLevelList = new ArrayList<String>();
				secondLevelList.add("Epoch Launcher");
				secondLevelList.add("Android Action Bar Icon Pack");
				secondLevelList.add("Akashi");
				secondLevelList.add("Roboto");
				secondLevelList.add("android-support-v4.jar");
				inflateMenuRight(R.layout.menu_right_item, secondLevelList);
				animateMainElements();
				break;
			case 62:
				secondLevelList = new ArrayList<String>();
				secondLevelList.add("Unlock Orientation");
				secondLevelList.add("Lock Orientation to Portrait");
				secondLevelList.add("Lock Orientation to Landscape");
				inflateMenuRight(R.layout.menu_right_item, secondLevelList);
				animateMainElements();
				break;
			case 63:
				secondLevelList = new ArrayList<String>();
				secondLevelList.add("Light Theme");
				secondLevelList.add("Dark Theme");
				inflateMenuRight(R.layout.menu_right_item, secondLevelList);
				animateMainElements();
				break;
		}
	}

	public void setMenuLeftIconColor(View b, int mode) {
		((ImageView) ((RelativeLayout) b).getChildAt(0)).setColorFilter(prefs.getMenuLeftPlateColor(), PorterDuff.Mode.MULTIPLY);
		if (mode == 0) {
			((ImageView) ((RelativeLayout) b).getChildAt(1)).setColorFilter(prefs.getMenuLeftSpaghettiColorPassive(), PorterDuff.Mode.MULTIPLY);
			((ImageView) ((RelativeLayout) b).getChildAt(2)).setColorFilter(prefs.getMenuLeftSauceColorPassive(), PorterDuff.Mode.MULTIPLY);
		}
		else {
			((ImageView) ((RelativeLayout) b).getChildAt(1)).setColorFilter(prefs.getMenuLeftSpaghettiColorActive(), PorterDuff.Mode.MULTIPLY);
			((ImageView) ((RelativeLayout) b).getChildAt(2)).setColorFilter(prefs.getMenuLeftSauceColorActive(), PorterDuff.Mode.MULTIPLY);
		}
	}

	public void setMenuRightItemColor(View c, int mode) {
		if (mode == 0) {
			c.setBackgroundColor(prefs.getMenuRightPlateColorPassive());
			((ImageView) ((RelativeLayout) c).getChildAt(0)).setColorFilter(prefs.getMenuRightSpaghettiColorPassive());
			((ImageView) ((RelativeLayout) c).getChildAt(1)).setColorFilter(prefs.getMenuRightSauceColorPassive());
			((TextView) ((RelativeLayout) c).getChildAt(2)).setTextColor(prefs.getMenuRightTextColorPassive());
			if (((RelativeLayout) c).getChildCount() == 4) {
				((TextView) ((RelativeLayout) c).getChildAt(3)).setTextColor(prefs.getMenuRightTextColorPassive());
			}
		}
		else {
			c.setBackgroundColor(prefs.getMenuRightPlateColorActive());
			((ImageView) ((RelativeLayout) c).getChildAt(0)).setColorFilter(prefs.getMenuRightSpaghettiColorActive());
			((ImageView) ((RelativeLayout) c).getChildAt(1)).setColorFilter(prefs.getMenuRightSauceColorActive());
			((TextView) ((RelativeLayout) c).getChildAt(2)).setTextColor(prefs.getMenuRightTextColorActive());
			if (((RelativeLayout) c).getChildCount() == 4) {
				((TextView) ((RelativeLayout) c).getChildAt(3)).setTextColor(prefs.getMenuRightTextColorActive());
			}
		}
	}

	public void setMenuLeftPointerMenuLeftLayoutParams() {
		if (!dispSupportsPrefsOrientation) {
			RelativeLayout.LayoutParams menuLeftLayoutParams = (RelativeLayout.LayoutParams) menuLeft.getLayoutParams();
			RelativeLayout.LayoutParams pointerMenuLeftLayoutParams = (RelativeLayout.LayoutParams) pointerMenuLeft.getLayoutParams();
			if (appMode == 0) {
				menuLeftLayoutParams.leftMargin = ((int) (20 * dispDensity));
				pointerMenuLeftLayoutParams.leftMargin = 0;
			}
			else {
				menuLeftLayoutParams.leftMargin = -(menuLeft.getWidth() + pointerMenuLeft.getWidth());
				pointerMenuLeftLayoutParams.leftMargin = -pointerMenuLeft.getWidth();
			}
			menuLeft.setLayoutParams(menuLeftLayoutParams);
			pointerMenuLeft.setLayoutParams(pointerMenuLeftLayoutParams);
		}
	}

	public void setPointerMenuLeftLayoutParams() {
		RelativeLayout.LayoutParams pointerMenuLeftLayoutParams = (RelativeLayout.LayoutParams) pointerMenuLeft.getLayoutParams();
		pointerMenuLeftLayoutParams.topMargin = (int) ((menuLeftIconHeight + (20 * dispDensity)) * Integer.parseInt(String.valueOf(appMode).substring(0, 1)) - (pointerMenuLeftHeight + ((menuLeftIconHeight - pointerMenuLeftHeight) / 2)));
		pointerMenuLeft.setLayoutParams(pointerMenuLeftLayoutParams);
	}

	public void setWallpaperParams() {
		WallpaperManager wallpaperManager = WallpaperManager.getInstance(context);
		wallpaperManager.suggestDesiredDimensions(dispResX, dispResY);
	}

	public void uninstall(String packageName) {
		try {
			startActivity(new Intent(Intent.ACTION_DELETE, Uri.parse("package:" + packageName)));
		}
		catch (Exception e) {
		}
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		getDispMetrics();
		setWallpaperParams();
	}

	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		if (hasFocus) {
			setAppMode(0);
		}
	}

	@Override  
	public void onBackPressed() {
		switch (String.valueOf(appMode).length()) {
			case 1:
				setAppMode(0);
				break;
			case 2:
				setAppMode(Integer.parseInt(String.valueOf(appMode).substring(0, 1)));
				break;
		}
	}
}