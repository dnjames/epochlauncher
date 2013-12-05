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

	GetPrefsCommunicator gPC;
	Thread getPrefs;
	Thread getFolders;

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

	ArrayList<Folder> folders;

	int menuLeftIconHeight;
	int pointerMenuLeftHeight;
	boolean dispSupportsPrefsOrientation;

	ArrayList firstLevelList;
	int lastClickPosition;
	int lastClickPositionII;
	ArrayList secondLevelList;
	Animation fadeIn;
	int appMode;
	GetBrowsersCommunicator gBC;
	Thread getBrowsers;
	ArrayList<AppInfo> browsers;
	GetContactsAppsCommunicator gCAC;
	Thread getContactsApps;
	ArrayList<AppInfo> contactsApps;
	GetPhoneAppsCommunicator gPAC;
	Thread getPhoneApps;
	ArrayList<AppInfo> phoneApps;
	GetAppsCommunicator gAC;
	Thread getApps;
	ArrayList<AppInfo> Apps;

	OnTouchListener menuRightItemOtl;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		if (android.os.Build.VERSION.SDK_INT >= 19) {
			setTheme(R.style.Theme_Holo_Light_NoActionBar_Wallpaper_TranslucentDecor);
		}
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mainactivity);

		context = getApplicationContext();

		gPC = new GetPrefsCommunicator();
		gPC.setContext(context);
		getPrefs = new Thread(new GetPrefs());
		getPrefs.start();
		GetFoldersCommunicator getFC = new GetFoldersCommunicator();
		getFC.setContext(context);
		getFolders = new Thread(new GetFolders());
		getFolders.start();

		if (Runtime.getRuntime().availableProcessors() == 1) {
			for (int i = 0; i < 5; i++) {
				Toast.makeText(context, "WARNING! Epoch Launcher is meant to run on multi-core devices. Do not expect a buttery experience on single-core devices.", Toast.LENGTH_SHORT).show();
			}
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

		while (!gPC.getHasFinished() || !getFC.getHasFinished()) {
		}
		if (gPC.getHasCrashed() || getFC.getHasCrashed()) {
			onCrash();
		}
		folders = getFC.getFolders();

		ViewTreeObserver parentVto = parent.getViewTreeObserver();
		parentVto.addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
				@Override
				public void onGlobalLayout() {
					menuLeftIconHeight = menuLeftIconInternet.getHeight();
					pointerMenuLeftHeight = pointerMenuLeft.getHeight();
					if (((menuLeftIconHeight + (20 * dispDensity)) * 6) > dispResX || ((menuLeftIconHeight + (20 * dispDensity)) * 6) > dispResY) {
						dispSupportsPrefsOrientation = false;
						setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
					}
					else {
						dispSupportsPrefsOrientation = true;
						switch (gPC.getDispPrefsOrientation()) {
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
				int position = ((Integer) view.getTag()).intValue();
				switch (position) {
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
										Toast.makeText(context, "No Contacts Application Installed", Toast.LENGTH_SHORT).show();
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
										Toast.makeText(context, "No Phone Application Installed", Toast.LENGTH_SHORT).show();
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
							if (android.os.Build.VERSION.SDK_INT >= 19) {
								launch("com.google.android.talk");
							}
							else {
								launch("com.android.mms");
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
									setMenuLeftIconColor(view, 0);
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
					if (firstVisibleItem == 0 && (firstVisibleItem + visibleItemCount) != totalItemCount) {
						pointerUp.setColorFilter(gPC.getThemes().get(gPC.getAppTheme()).getColors(0).get(TAL("pointerUpDownColor")), PorterDuff.Mode.MULTIPLY);
						pointerDown.setColorFilter(gPC.getThemes().get(gPC.getAppTheme()).getColors(1).get(TAL("pointerUpDownColor")), PorterDuff.Mode.MULTIPLY);
					}
					if (firstVisibleItem != 0 && (firstVisibleItem + visibleItemCount) != totalItemCount) {
						pointerUp.setColorFilter(gPC.getThemes().get(gPC.getAppTheme()).getColors(1).get(TAL("pointerUpDownColor")), PorterDuff.Mode.MULTIPLY);
						pointerDown.setColorFilter(gPC.getThemes().get(gPC.getAppTheme()).getColors(1).get(TAL("pointerUpDownColor")), PorterDuff.Mode.MULTIPLY);
					}
					if (firstVisibleItem != 0 && (firstVisibleItem + visibleItemCount) == totalItemCount) {
						pointerUp.setColorFilter(gPC.getThemes().get(gPC.getAppTheme()).getColors(1).get(TAL("pointerUpDownColor")), PorterDuff.Mode.MULTIPLY);
						pointerDown.setColorFilter(gPC.getThemes().get(gPC.getAppTheme()).getColors(0).get(TAL("pointerUpDownColor")), PorterDuff.Mode.MULTIPLY);
					}
					if (firstVisibleItem == 0 && (firstVisibleItem + visibleItemCount) == totalItemCount) {
						pointerUp.setColorFilter(gPC.getThemes().get(gPC.getAppTheme()).getColors(0).get(TAL("pointerUpDownColor")), PorterDuff.Mode.MULTIPLY);
						pointerDown.setColorFilter(gPC.getThemes().get(gPC.getAppTheme()).getColors(0).get(TAL("pointerUpDownColor")), PorterDuff.Mode.MULTIPLY);
					}
				}
				public void onScrollStateChanged(AbsListView view, int scrollState) {
				}
			});
		menuRightItemOtl = new OnTouchListener() {
			@Override
			public boolean onTouch(View view, MotionEvent motionEvent) {
				int position = ((Integer) view.getTag()).intValue();
				if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
					switch (appMode) {
						case 1:
						case 2:
						case 3:
							if ((appMode == 1 && gBC.getBrowsers().get(position).getPackageName() == null) ||
								(appMode == 2 && gCAC.getContactsApps().get(position).getPackageName() == null) ||
								(appMode == 3 && gPAC.getPhoneApps().get(position).getPackageName() == null)) {
								setMenuRightItemColor(view, 1, false);
							}
							else {
								setMenuRightItemColor(view, 1, true);
							}
							break;
						case 5:
							setMenuRightItemColor(view, 1, firstLevelList.get(position).getClass() == AppInfo.class);
							break;
						case 52:
							setMenuRightItemColor(view, 1, true);
							break;
						case 6:
							if (position != 1 && position != 2 && position != 3) {
								setMenuRightItemColor(view, 1, false);
							}
							break;
						case 64:
							if (position != 0) {
								setMenuRightItemColor(view, 1, false);
							}
							break;
						default:
							setMenuRightItemColor(view, 1, false);
							break;
					}
				}
				if (motionEvent.getAction() == MotionEvent.ACTION_CANCEL) {
					switch (appMode) {
						case 1:
						case 2:
						case 3:
							if ((appMode == 1 && gBC.getBrowsers().get(position).getPackageName() == null) ||
								(appMode == 2 && gCAC.getContactsApps().get(position).getPackageName() == null) ||
								(appMode == 3 && gPAC.getPhoneApps().get(position).getPackageName() == null)) {
								setMenuRightItemColor(view, 0, false);
							}
							else {
								setMenuRightItemColor(view, 0, true);
							}
							break;
						case 5:
							setMenuRightItemColor(view, 0, firstLevelList.get(position).getClass() == AppInfo.class);
							break;
						case 52:
							setMenuRightItemColor(view, 0, true);
							break;
						case 6:
							if (position != 1 && position != 2 && position != 3) {
								setMenuRightItemColor(view, 0, false);
							}
							break;
						case 64:
							if (position != 0) {
								setMenuRightItemColor(view, 0, false);
							}
							break;
						default:
							setMenuRightItemColor(view, 0, false);
							break;
					}
				}
				if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
					switch (appMode) {
						case 1:
							if (gBC.getBrowsers().get(position).getPackageName() == null) {
								setMenuRightItemColor(view, 0, false);
								setAppMode(0);
							}
							else {
								if ((motionEvent.getEventTime() - motionEvent.getDownTime()) <= 300) {
									setMenuRightItemColor(view, 0, true);
									launch(gBC.getBrowsers().get(position).getPackageName());
								}
								else {
									lastClickPosition = position;
									setAppMode(11);
								}
							}
							break;
						case 11:
							if (position == 0) {
								setMenuRightItemColor(view, 0, false);
								launch(gBC.getBrowsers().get(lastClickPosition).getPackageName());
							}
							else {
								setMenuRightItemColor(view, 0, false);
								uninstall(gBC.getBrowsers().get(lastClickPosition).getPackageName());
							}
							break;
						case 2:
							if (gCAC.getContactsApps().get(position).getPackageName() == null) {
								setMenuRightItemColor(view, 0, false);
								setAppMode(0);
							}
							else {
								if ((motionEvent.getEventTime() - motionEvent.getDownTime()) <= 300) {
									setMenuRightItemColor(view, 0, true);
									launch(gCAC.getContactsApps().get(position).getPackageName());
								}
								else {
									lastClickPosition = position;
									setAppMode(21);
								}
							}
							break;
						case 21:
							if (position == 0) {
								setMenuRightItemColor(view, 0, false);
								launch(gCAC.getContactsApps().get(lastClickPosition).getPackageName());
							}
							else {
								setMenuRightItemColor(view, 0, false);
								uninstall(gCAC.getContactsApps().get(lastClickPosition).getPackageName());
							}
							break;
						case 3:
							if (gPAC.getPhoneApps().get(position).getPackageName() == null) {
								setMenuRightItemColor(view, 0, false);
								setAppMode(0);
							}
							else {
								if ((motionEvent.getEventTime() - motionEvent.getDownTime()) <= 300) {
									setMenuRightItemColor(view, 0, true);
									launch(gPAC.getPhoneApps().get(position).getPackageName());
								}
								else {
									lastClickPosition = position;
									setAppMode(31);
								}
							}
							break;
						case 31:
							if (position == 0) {
								setMenuRightItemColor(view, 0, false);
								launch(gPAC.getPhoneApps().get(lastClickPosition).getPackageName());
							}
							else {
								setMenuRightItemColor(view, 0, false);
								uninstall(gPAC.getPhoneApps().get(lastClickPosition).getPackageName());
							}
							break;
						case 5:
							if ((motionEvent.getEventTime() - motionEvent.getDownTime()) <= 300) {
								if (firstLevelList.get(position).getClass() == AppInfo.class) {
									setMenuRightItemColor(view, 0, true);
									launch(((AppInfo) firstLevelList.get(position)).getPackageName());
								}
								else {
									lastClickPosition = position;
									setAppMode(52);
								}
							}
							else {
								lastClickPosition = position;
								if (firstLevelList.get(position).getClass() == AppInfo.class) {
									setAppMode(51);
								}
								else {
									setAppMode(53);
								}
							}
							break;
						case 51:
							switch (position) {
								case 0:
									setMenuRightItemColor(view, 0, false);
									launch(((AppInfo) firstLevelList.get(lastClickPosition)).getPackageName());
									break;
								case 1:
									setMenuRightItemColor(view, 0, false);
									uninstall(((AppInfo) firstLevelList.get(lastClickPosition)).getPackageName());
									break;
								case 2:
									setAppMode(511);
									break;
								default:
									folders.get(position - 3).getFolderContents().add(((AppInfo) firstLevelList.get(lastClickPosition)));
									Collections.sort(folders.get(position - 3).getFolderContents(), new AppInfoComparator());
									saveFolders();
									setAppMode(5);
									break;
							}
							break;
						case 511:
							switch (position) {
								case 0:
									setMenuRightItemColor(view, 0, false);
									break;
								case 1:
									folders.add(new Folder());
									folders.get(folders.size() - 1).setFolderName(((EditText) ((RelativeLayout) menuRight.getChildAt(0)).getChildAt(2)).getText().toString());
									folders.get(folders.size() - 1).getFolderContents().add(((AppInfo) firstLevelList.get(lastClickPosition)));
									saveFolders();
									setAppMode(5);
									break;
								case 2:
									setAppMode(51);
									break;
							}
							break;
						case 52:
							if ((motionEvent.getEventTime() - motionEvent.getDownTime()) <= 300) {
								setMenuRightItemColor(view, 0, true);
								launch(folders.get(lastClickPosition).getFolderContents().get(position).getPackageName());
							}
							else {
								lastClickPositionII = position;
								setAppMode(521);
							}
							break;
						case 521:
							switch (position) {
								case 0:
									setMenuRightItemColor(view, 0, false);
									launch(folders.get(lastClickPosition).getFolderContents().get(lastClickPositionII).getPackageName());
									break;
								case 1:
									setMenuRightItemColor(view, 0, false);
									uninstall(folders.get(lastClickPosition).getFolderContents().get(lastClickPositionII).getPackageName());
									break;
								case 2:
									folders.get(lastClickPosition).getFolderContents().remove(lastClickPositionII);
									if (folders.get(lastClickPosition).getFolderContents().isEmpty()) {
										folders.remove(lastClickPosition);
										saveFolders();
										setAppMode(5);
									}
									else {
										saveFolders();
										setAppMode(52);
									}
									break;
							}
							break;
						case 53:
							switch (position) {
								case 0:
									setAppMode(531);
									break;
								case 1:
									folders.remove(lastClickPosition);
									saveFolders();
									setAppMode(5);
									break;
							}
							break;
						case 531:
							switch (position) {
								case 0:
									setMenuRightItemColor(view, 0, false);
									break;
								case 1:
									folders.get(lastClickPosition).setFolderName(((EditText) ((RelativeLayout) menuRight.getChildAt(0)).getChildAt(2)).getText().toString());
									saveFolders();
									setAppMode(5);
									break;
								case 2:
									setAppMode(53);
									break;
							}
							break;
						case 6:
							switch (position) {
								case 0:
									setAppMode(61);
									break;
								case 1:
									if (hwSupportsWifi) {
										setMenuRightItemColor(view, boolean2int(!wifiManager.isWifiEnabled()), false);
										wifiManager.setWifiEnabled(!wifiManager.isWifiEnabled());
									}
									break;
								case 2:
									if (hwSupportsBt) {
										setMenuRightItemColor(view, boolean2int(!btAdapter.isEnabled()), false);
										if (btAdapter.isEnabled()) {
											btAdapter.disable();
										}
										else {
											btAdapter.enable();
										}
									}
									break;
								case 3:
									if (hwSupportsGps) {
										startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
									}
									break;
								case 4:
									if (dispSupportsPrefsOrientation) {
										setAppMode(62);
									}
									else {
										setMenuRightItemColor(view, 0, false);
									}
									break;
								case 5:
									setAppMode(63);
									break;
								case 6:
									setAppMode(64);
									break;
								case 7:
									restartApp();
									break;
								case 8:
									setMenuRightItemColor(view, 0, false);
									clearLauncherDefaults();
									break;
							}
							break;
						case 61:
							switch (position) {
								case 0:
									setMenuRightItemColor(view, 0, false);
									try {
										startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://github.com/mirasmithy/epochlauncher")));
										startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.gnu.org/licenses/gpl-3.0.html")));
									}
									catch (Exception e) {
										Toast.makeText(context, "No Browsers Installed", Toast.LENGTH_SHORT).show();
									}
									break;
								case 1:
									setMenuRightItemColor(view, 0, false);
									try {
										startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://developer.android.com/design/downloads/index.html")));
										startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://creativecommons.org/licenses/by/2.5/legalcode")));
									}
									catch (Exception e) {
										Toast.makeText(context, "No Browsers Installed", Toast.LENGTH_SHORT).show();
									}
									break;
								case 2:
									setMenuRightItemColor(view, 0, false);
									try {
										startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://tenbytwenty.com/?xxxx_posts=akashi")));
										startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://scripts.sil.org/cms/scripts/page.php?item_id=OFL_web")));
									}
									catch (Exception e) {
										Toast.makeText(context, "No Browsers Installed", Toast.LENGTH_SHORT).show();
									}
									break;
								case 3:
									setMenuRightItemColor(view, 0, false);
									try {
										startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://developer.android.com/design/downloads/index.html")));
										startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.apache.org/licenses/LICENSE-2.0")));
									}
									catch (Exception e) {
										Toast.makeText(context, "No Browsers Installed", Toast.LENGTH_SHORT).show();
									}
									break;
								case 4:
									setMenuRightItemColor(view, 0, false);
									try {
										startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://developer.android.com/tools/support-library/index.html")));
										startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.apache.org/licenses/LICENSE-2.0")));
									}
									catch (Exception e) {
										Toast.makeText(context, "No Browsers Installed", Toast.LENGTH_SHORT).show();
									}
									break;
							}
							break;
						case 62:
							gPC.setDispPrefsOrientation(position);
							savePrefs();
							switch (gPC.getDispPrefsOrientation()) {
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
							gPC.setAppTheme(position);
							savePrefs();
							setAppMode(6);
							break;
						case 64:
							switch (position) {
								case 0:
									gPC.setUseCustomFont(!gPC.getUseCustomFont());
									savePrefs();
									setMenuRightItemColor(view, boolean2int(gPC.getUseCustomFont()), false);
									break;
								case 1:
									setMenuRightItemColor(view, 0, false);
									break;
								case 2:
									gPC.setCustomFont(((EditText) ((RelativeLayout) menuRight.getChildAt(1)).getChildAt(2)).getText().toString());
									savePrefs();
									setAppMode(6);
									break;
								case 3:
									setAppMode(6);
									break;
							}
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

	public int boolean2int(boolean input) {
		if (input) {
			return 1;
		}
		else {
			return 0;
		}
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

	public void inflateMenuRight(ArrayList data) {
		menuRight.setAdapter(new CustomArrayAdapter(this, android.R.layout.simple_list_item_1, data,
													packageManager,
													hwSupportsWifi, wifiManager,
													hwSupportsBt, btAdapter,
													hwSupportsGps, locationManager,
													appMode,
													menuRightItemOtl));
	}

	public boolean int2boolean(int input) {
		if (input == 0) {
			return false;
		}
		else {
			return true;
		}
	}

	public void launch(String packageName) {
		try {
			startActivity(getPackageManager().getLaunchIntentForPackage(packageName));
		}
		catch (Exception e) {
			switch (packageName) {
				case "com.android.mms":
					Toast.makeText(context, "Messaging Not Installed", Toast.LENGTH_SHORT).show();
					break;
				case "com.google.android.talk":
					Toast.makeText(context, "Hangouts Not Installed", Toast.LENGTH_SHORT).show();
					break;
				case "com.android.vending":
					Toast.makeText(context, "Play Store Not Installed", Toast.LENGTH_SHORT).show();
					break;
				case "com.android.setting":
					Toast.makeText(context, "Settings Not Installed", Toast.LENGTH_SHORT).show();
					break;
				default:
					Toast.makeText(context, "App Not Installed", Toast.LENGTH_SHORT).show();
					break;
			}
		}
	}

	public void onCrash() {
		for (int i = 0; i < 10; i++) {
			Toast.makeText(context, "ERROR! Epoch Launcher has crashed. This can be caused by a lack of internal storage space. Try to free up some space and relaunch Epoch Launcher. If the problem persists, contact the developer at mirasmithy@maskr.me", Toast.LENGTH_SHORT).show();
		}
		clearLauncherDefaults();
		finish();
	}

	public void restartApp() {
		finish();
		launch("com.mirasmithy.epochlauncher");
	}

	public void saveFolders() {
		Collections.sort(folders, new FolderComparator());
		try {
			FileOutputStream getFoldersFos = context.getApplicationContext().openFileOutput("folders", context.MODE_PRIVATE);
			ObjectOutputStream getFoldersOos = new ObjectOutputStream(getFoldersFos);
			getFoldersOos.writeObject(folders);
			getFoldersOos.close();
			getFoldersFos.close();
		}
		catch (Exception e) {
			onCrash();
		}
	}

	public void setAppMode(int a) {
		appMode = a;
		setMenuLeftPointerMenuLeftLayoutParams();
		switch (appMode) {
			case 0:
				setMenuLeftIconColor(menuLeftIconInternet, 0);
				setMenuLeftIconColor(menuLeftIconContacts, 0);
				setMenuLeftIconColor(menuLeftIconPhone, 0);
				setMenuLeftIconColor(menuLeftIconMessaging, 0);
				setMenuLeftIconColor(menuLeftIconApps, 0);
				setMenuLeftIconColor(menuLeftIconPrefs, 0);
				pointerMenuLeft.setColorFilter(gPC.getThemes().get(gPC.getAppTheme()).getColors(0).get(TAL("pointerMenuLeftColor")), PorterDuff.Mode.MULTIPLY);
				separatorMenuLeftRight.setBackgroundColor(gPC.getThemes().get(gPC.getAppTheme()).getColors(0).get(TAL("separatorMenuLeftRightColor")));
				separatorMenuLeftRightII.setBackgroundColor(gPC.getThemes().get(gPC.getAppTheme()).getColors(0).get(TAL("separatorMenuLeftRightColor")));
				inflateMenuRight(new ArrayList());
				break;
			case 1:
				gBC = new GetBrowsersCommunicator();
				gBC.setPackageManager(packageManager);
				getBrowsers = new Thread(new GetBrowsers());
				getBrowsers.start();
				setMenuLeftIconColor(menuLeftIconInternet, 1);
				setMenuLeftIconColor(menuLeftIconContacts, 0);
				setMenuLeftIconColor(menuLeftIconPhone, 0);
				setMenuLeftIconColor(menuLeftIconMessaging, 0);
				setMenuLeftIconColor(menuLeftIconApps, 0);
				setMenuLeftIconColor(menuLeftIconPrefs, 0);
				pointerMenuLeft.setColorFilter(gPC.getThemes().get(gPC.getAppTheme()).getColors(1).get(TAL("pointerMenuLeftColor")), PorterDuff.Mode.MULTIPLY);
				separatorMenuLeftRight.setBackgroundColor(gPC.getThemes().get(gPC.getAppTheme()).getColors(1).get(TAL("separatorMenuLeftRightColor")));
				separatorMenuLeftRightII.setBackgroundColor(gPC.getThemes().get(gPC.getAppTheme()).getColors(1).get(TAL("separatorMenuLeftRightColor")));
				while (!gBC.getHasFinished()) {
				}
				inflateMenuRight(gBC.getBrowsers());
				animateMainElements();
				break;
			case 11:
				secondLevelList = new ArrayList<String>();
				secondLevelList.add("Launch " + gBC.getBrowsers().get(lastClickPosition).getAppName());
				secondLevelList.add("Uninstall " + gBC.getBrowsers().get(lastClickPosition).getAppName());
				inflateMenuRight(secondLevelList);
				animateMainElements();
				break;
			case 2:
				gCAC = new GetContactsAppsCommunicator();
				gCAC.setPackageManager(packageManager);
				getContactsApps = new Thread(new GetContactsApps());
				getContactsApps.start();
				setMenuLeftIconColor(menuLeftIconInternet, 0);
				setMenuLeftIconColor(menuLeftIconContacts, 1);
				setMenuLeftIconColor(menuLeftIconPhone, 0);
				setMenuLeftIconColor(menuLeftIconMessaging, 0);
				setMenuLeftIconColor(menuLeftIconApps, 0);
				setMenuLeftIconColor(menuLeftIconPrefs, 0);
				pointerMenuLeft.setColorFilter(gPC.getThemes().get(gPC.getAppTheme()).getColors(1).get(TAL("pointerMenuLeftColor")), PorterDuff.Mode.MULTIPLY);
				separatorMenuLeftRight.setBackgroundColor(gPC.getThemes().get(gPC.getAppTheme()).getColors(1).get(TAL("separatorMenuLeftRightColor")));
				separatorMenuLeftRightII.setBackgroundColor(gPC.getThemes().get(gPC.getAppTheme()).getColors(1).get(TAL("separatorMenuLeftRightColor")));
				while (!gCAC.getHasFinished()) {
				}
				inflateMenuRight(gCAC.getContactsApps());
				animateMainElements();
				break;
			case 21:
				secondLevelList = new ArrayList<String>();
				secondLevelList.add("Launch " + gCAC.getContactsApps().get(lastClickPosition).getAppName());
				secondLevelList.add("Uninstall " + gCAC.getContactsApps().get(lastClickPosition).getAppName());
				inflateMenuRight(secondLevelList);
				animateMainElements();
				break;
			case 3:
				gPAC = new GetPhoneAppsCommunicator();
				gPAC.setPackageManager(packageManager);
				getPhoneApps = new Thread(new GetPhoneApps());
				getPhoneApps.start();
				setMenuLeftIconColor(menuLeftIconInternet, 0);
				setMenuLeftIconColor(menuLeftIconContacts, 0);
				setMenuLeftIconColor(menuLeftIconPhone, 1);
				setMenuLeftIconColor(menuLeftIconMessaging, 0);
				setMenuLeftIconColor(menuLeftIconApps, 0);
				setMenuLeftIconColor(menuLeftIconPrefs, 0);
				pointerMenuLeft.setColorFilter(gPC.getThemes().get(gPC.getAppTheme()).getColors(1).get(TAL("pointerMenuLeftColor")), PorterDuff.Mode.MULTIPLY);
				separatorMenuLeftRight.setBackgroundColor(gPC.getThemes().get(gPC.getAppTheme()).getColors(1).get(TAL("separatorMenuLeftRightColor")));
				separatorMenuLeftRightII.setBackgroundColor(gPC.getThemes().get(gPC.getAppTheme()).getColors(1).get(TAL("separatorMenuLeftRightColor")));
				while (!gPAC.getHasFinished()) {
				}
				inflateMenuRight(gPAC.getPhoneApps());
				animateMainElements();
				break;
			case 31:
				secondLevelList = new ArrayList<String>();
				secondLevelList.add("Launch " + gPAC.getPhoneApps().get(lastClickPosition).getAppName());
				secondLevelList.add("Uninstall " + gPAC.getPhoneApps().get(lastClickPosition).getAppName());
				inflateMenuRight(secondLevelList);
				animateMainElements();
				break;
			case 5:
				gAC = new GetAppsCommunicator();
				gAC.setPackageManager(packageManager);
				getApps = new Thread(new GetApps());
				getApps.start();
				setMenuLeftIconColor(menuLeftIconInternet, 0);
				setMenuLeftIconColor(menuLeftIconContacts, 0);
				setMenuLeftIconColor(menuLeftIconPhone, 0);
				setMenuLeftIconColor(menuLeftIconMessaging, 0);
				setMenuLeftIconColor(menuLeftIconApps, 1);
				setMenuLeftIconColor(menuLeftIconPrefs, 0);
				pointerMenuLeft.setColorFilter(gPC.getThemes().get(gPC.getAppTheme()).getColors(1).get(TAL("pointerMenuLeftColor")), PorterDuff.Mode.MULTIPLY);
				separatorMenuLeftRight.setBackgroundColor(gPC.getThemes().get(gPC.getAppTheme()).getColors(1).get(TAL("separatorMenuLeftRightColor")));
				separatorMenuLeftRightII.setBackgroundColor(gPC.getThemes().get(gPC.getAppTheme()).getColors(1).get(TAL("separatorMenuLeftRightColor")));
				while (!gAC.getHasFinished()) {
				}
				firstLevelList = new ArrayList();
				firstLevelList.addAll(folders);
				firstLevelList.addAll(gAC.getApps());
				inflateMenuRight(firstLevelList);
				animateMainElements();
				break;
			case 51:
				secondLevelList = new ArrayList<String>();
				secondLevelList.add("Launch " + ((AppInfo) firstLevelList.get(lastClickPosition)).getAppName());
				secondLevelList.add("Uninstall " + ((AppInfo) firstLevelList.get(lastClickPosition)).getAppName());
				secondLevelList.add("Add " + ((AppInfo) firstLevelList.get(lastClickPosition)).getAppName() + " to New Folder");
				for (int i = 0; i < folders.size(); i++) {
					secondLevelList.add("Add " + ((AppInfo) firstLevelList.get(lastClickPosition)).getAppName() + " to " + folders.get(i).getFolderName());
				}
				inflateMenuRight(secondLevelList);
				animateMainElements();
				break;
			case 511:
				secondLevelList = new ArrayList<String>();
				secondLevelList.add("New Folder");
				secondLevelList.add("Create New Folder");
				secondLevelList.add("Nevermind");
				inflateMenuRight(secondLevelList);
				animateMainElements();
				break;
			case 52:
				for (int i = 0; i < folders.get(lastClickPosition).getFolderContents().size(); i++) {
					try {
						packageManager.getPackageInfo(folders.get(lastClickPosition).getFolderContents().get(i).getPackageName(), 0);
					}
					catch (Exception e) {
						folders.get(lastClickPosition).getFolderContents().remove(i);
					}
				}
				saveFolders();
				inflateMenuRight(folders.get(lastClickPosition).getFolderContents());
				animateMainElements();
				break;
			case 521:
				secondLevelList = new ArrayList<String>();
				secondLevelList.add("Launch " + folders.get(lastClickPosition).getFolderContents().get(lastClickPositionII).getAppName());
				secondLevelList.add("Uninstall " + folders.get(lastClickPosition).getFolderContents().get(lastClickPositionII).getAppName());
				secondLevelList.add("Remove " + folders.get(lastClickPosition).getFolderContents().get(lastClickPositionII).getAppName());
				inflateMenuRight(secondLevelList);
				animateMainElements();
				break;
			case 53:
				secondLevelList = new ArrayList<String>();
				secondLevelList.add("Rename " + folders.get(lastClickPosition).getFolderName());
				secondLevelList.add("Delete " + folders.get(lastClickPosition).getFolderName());
				inflateMenuRight(secondLevelList);
				animateMainElements();
				break;
			case 531:
				secondLevelList = new ArrayList<String>();
				secondLevelList.add(folders.get(lastClickPosition).getFolderName());
				secondLevelList.add("Rename " + folders.get(lastClickPosition).getFolderName());
				secondLevelList.add("Nevermind");
				inflateMenuRight(secondLevelList);
				animateMainElements();
				break;
			case 6:
				setMenuLeftIconColor(menuLeftIconInternet, 0);
				setMenuLeftIconColor(menuLeftIconContacts, 0);
				setMenuLeftIconColor(menuLeftIconPhone, 0);
				setMenuLeftIconColor(menuLeftIconMessaging, 0);
				setMenuLeftIconColor(menuLeftIconApps, 0);
				setMenuLeftIconColor(menuLeftIconPrefs, 1);
				pointerMenuLeft.setColorFilter(gPC.getThemes().get(gPC.getAppTheme()).getColors(1).get(TAL("pointerMenuLeftColor")), PorterDuff.Mode.MULTIPLY);
				separatorMenuLeftRight.setBackgroundColor(gPC.getThemes().get(gPC.getAppTheme()).getColors(1).get(TAL("separatorMenuLeftRightColor")));
				separatorMenuLeftRightII.setBackgroundColor(gPC.getThemes().get(gPC.getAppTheme()).getColors(1).get(TAL("separatorMenuLeftRightColor")));
				firstLevelList = new ArrayList<String>();
				try {
					firstLevelList.add("Epoch Launcher " + packageManager.getPackageInfo("com.mirasmithy.epochlauncher", 0).versionName);
				}
				catch (Exception e) {
				}
				if (hwSupportsWifi) {
					firstLevelList.add("Wi-Fi");
				}
				else {
					firstLevelList.add("Wi-Fi Unsupported");
				}
				if (hwSupportsBt) {
					firstLevelList.add("Bluetooth");
				}
				else {
					firstLevelList.add("Bluetooth Unsupported");
				}
				if (hwSupportsGps) {
					firstLevelList.add("GPS");
				}
				else {
					firstLevelList.add("GPS Unsupported");
				}
				if (dispSupportsPrefsOrientation) {
					switch (gPC.getDispPrefsOrientation()) {
						case 0:
							firstLevelList.add("Orientation Unlocked");
							break;
						case 1:
							firstLevelList.add("Orientation Locked to Portrait");
							break;
						case 2:
							firstLevelList.add("Orientation Locked to Landscape");
							break;
					}
				}
				else {
					firstLevelList.add("Orientation Lock Unsupported");
				}
				firstLevelList.add("Theme Settings");
				firstLevelList.add("Font Settings");
				firstLevelList.add("Restart Epoch Launcher");
				firstLevelList.add("Clear Launcher Defaults");
				inflateMenuRight(firstLevelList);
				animateMainElements();
				break;
			case 61:
				secondLevelList = new ArrayList<String>();
				secondLevelList.add("Epoch Launcher");
				secondLevelList.add("Android Action Bar Icon Pack");
				secondLevelList.add("Akashi");
				secondLevelList.add("Roboto");
				secondLevelList.add("android-support-v4.jar");
				inflateMenuRight(secondLevelList);
				animateMainElements();
				break;
			case 62:
				secondLevelList = new ArrayList<String>();
				secondLevelList.add("Unlock Orientation");
				secondLevelList.add("Lock Orientation to Portrait");
				secondLevelList.add("Lock Orientation to Landscape");
				inflateMenuRight(secondLevelList);
				animateMainElements();
				break;
			case 63:
				inflateMenuRight(gPC.getThemes());
				animateMainElements();
				break;
			case 64:
				secondLevelList = new ArrayList<String>();
				secondLevelList.add("Use Custom Font");
				secondLevelList.add(gPC.getCustomFont());
				secondLevelList.add("Save Font Settings");
				secondLevelList.add("Nevermind");
				inflateMenuRight(secondLevelList);
				animateMainElements();
				break;
		}
	}

	public void setMenuLeftIconColor(View view, int mode) {
		((ImageView) ((RelativeLayout) view).getChildAt(mode)).setColorFilter(gPC.getThemes().get(gPC.getAppTheme()).getColors(mode).get(TAL("menuLeftPlateColor")), PorterDuff.Mode.MULTIPLY);
		((ImageView) ((RelativeLayout) view).getChildAt(1)).setColorFilter(gPC.getThemes().get(gPC.getAppTheme()).getColors(mode).get(TAL("menuLeftSpaghettiColor")), PorterDuff.Mode.MULTIPLY);
		((ImageView) ((RelativeLayout) view).getChildAt(2)).setColorFilter(gPC.getThemes().get(gPC.getAppTheme()).getColors(mode).get(TAL("menuLeftSauceColor")), PorterDuff.Mode.MULTIPLY);
	}

	public void setMenuRightItemColor(View view, int mode, boolean usingCustomIcon) {
		view.setBackgroundColor(gPC.getThemes().get(gPC.getAppTheme()).getColors(mode).get(TAL("menuRightPlateColor")));
		if (usingCustomIcon) {
			((ImageView) ((RelativeLayout) view).getChildAt(0)).setColorFilter(Color.argb(0, 255, 255, 255), PorterDuff.Mode.MULTIPLY);
		}
		else {
			((ImageView) ((RelativeLayout) view).getChildAt(0)).setColorFilter(gPC.getThemes().get(gPC.getAppTheme()).getColors(mode).get(TAL("menuRightSpaghettiColor")), PorterDuff.Mode.MULTIPLY);
			((ImageView) ((RelativeLayout) view).getChildAt(1)).setColorFilter(gPC.getThemes().get(gPC.getAppTheme()).getColors(mode).get(TAL("menuRightSauceColor")), PorterDuff.Mode.MULTIPLY);
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

	public void setMenuLeftPointerMenuLeftLayoutParams() {
		if (!dispSupportsPrefsOrientation) {
			RelativeLayout.LayoutParams menuLeftLayoutParams = (RelativeLayout.LayoutParams) menuLeft.getLayoutParams();
			if (appMode == 0) {
				menuLeftLayoutParams.leftMargin = ((int) (20 * dispDensity));
			}
			else {
				menuLeftLayoutParams.leftMargin = -(menuLeft.getWidth() + pointerMenuLeft.getWidth());
			}
			menuLeft.setLayoutParams(menuLeftLayoutParams);
		}
		RelativeLayout.LayoutParams pointerMenuLeftLayoutParams = (RelativeLayout.LayoutParams) pointerMenuLeft.getLayoutParams();
		if (appMode == 0) {
			pointerMenuLeftLayoutParams.leftMargin = 0;
		}
		else {
			pointerMenuLeftLayoutParams.topMargin = ((int) ((menuLeftIconHeight + (20 * dispDensity)) * Integer.parseInt(String.valueOf(appMode).substring(0, 1)) - (pointerMenuLeftHeight + ((menuLeftIconHeight - pointerMenuLeftHeight) / 2))));
			if (!dispSupportsPrefsOrientation) {
				pointerMenuLeftLayoutParams.leftMargin = -pointerMenuLeft.getWidth();
			}
		}
		pointerMenuLeft.setLayoutParams(pointerMenuLeftLayoutParams);
	}

	public void savePrefs() {
		FileOutputStream savePrefsFos;
		ObjectOutputStream savePrefsOos;
		try {
			savePrefsFos = context.getApplicationContext().openFileOutput("dispPrefsOrientation", context.MODE_PRIVATE);
			savePrefsOos = new ObjectOutputStream(savePrefsFos);
			savePrefsOos.writeObject(gPC.getDispPrefsOrientation());
			savePrefsOos.close();
			savePrefsFos.close();
		}
		catch (Exception e) {
			onCrash();
		}
		try {
			savePrefsFos = context.getApplicationContext().openFileOutput("appTheme", context.MODE_PRIVATE);
			savePrefsOos = new ObjectOutputStream(savePrefsFos);
			savePrefsOos.writeObject(gPC.getAppTheme());
			savePrefsOos.close();
			savePrefsFos.close();
		}
		catch (Exception e) {
			onCrash();
		}
		try {
			Collections.sort(gPC.getThemes(), new ThemeComparator());
			savePrefsFos = context.getApplicationContext().openFileOutput("themes", context.MODE_PRIVATE);
			savePrefsOos = new ObjectOutputStream(savePrefsFos);
			savePrefsOos.writeObject(gPC.getThemes());
			savePrefsOos.close();
			savePrefsFos.close();
		}
		catch (Exception e) {
			onCrash();
		}
		try {
			savePrefsFos = context.getApplicationContext().openFileOutput("useCustomFont", context.MODE_PRIVATE);
			savePrefsOos = new ObjectOutputStream(savePrefsFos);
			savePrefsOos.writeObject(gPC.getUseCustomFont());
			savePrefsOos.close();
			savePrefsFos.close();
		}
		catch (Exception e) {
			onCrash();
		}
		try {
			savePrefsFos = context.getApplicationContext().openFileOutput("customFont", context.MODE_PRIVATE);
			savePrefsOos = new ObjectOutputStream(savePrefsFos);
			savePrefsOos.writeObject(gPC.getCustomFont());
			savePrefsOos.close();
			savePrefsFos.close();
		}
		catch (Exception e) {
			onCrash();
		}
	}

	public void setWallpaperParams() {
		WallpaperManager wallpaperManager = WallpaperManager.getInstance(context);
		wallpaperManager.suggestDesiredDimensions(dispResX, dispResY);
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

	public void uninstall(String packageName) {
		try {
			startActivity(new Intent(Intent.ACTION_DELETE, Uri.parse("package:" + packageName)));
		}
		catch (Exception e) {
		}
	}

	@Override  
	public void onBackPressed() {
		setAppMode(appMode / 10);
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
}
