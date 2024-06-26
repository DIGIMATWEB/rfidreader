package com.vanch.vhxdemo;

import lab.sodino.language.util.Strings;

import android.Manifest;
import android.app.AlertDialog;
import android.bluetooth.BluetoothDevice;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTabHost;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.TabWidget;
import android.widget.TextView;
import android.widget.Toast;

import de.greenrobot.event.EventBus;

public class MainActivity extends FragmentActivity {
	private static final String TAG = "main";
	public static BluetoothDevice selectedDevice;
	public static String lang;
	public static String lang_key = "lang";
	private static final int REQUEST_ENABLE_BLUETOOTH_CONNECT = 2;
//	TabHost mTabHost;
	FragmentTabHost mTabHost;
	//AndroidApkUpdater updater;
//	ViewPager mViewPager;
//	TabsAdapter mTabsAdapter;

//	@Override
//	protected void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//
//		// get lang
//		lang = readLangConfig();
//
//		setContentView(R.layout.activity_main);
//		// mTabHost = (FragmentTabHost)findViewById(android.R.id.tabhost);
//
//		mTabHost = (TabHost) findViewById(android.R.id.tabhost);
//		mTabHost.setup();
//		mViewPager = (ViewPager) findViewById(R.id.pager);
//		mTabsAdapter = new TabsAdapter(this, mTabHost, mViewPager);
//
//		mTabsAdapter.addTab(mTabHost.newTabSpec("Link").setIndicator("Link"),
//				LinkUi.class, null);
//		mTabsAdapter.addTab(
//				mTabHost.newTabSpec("Inventory").setIndicator("Inventory"),
//				InventoryUI.class, null);
//		mTabsAdapter.addTab(mTabHost.newTabSpec("Access").setIndicator("Access"),
//				AccessUI.class, null);
//		mTabsAdapter.addTab(mTabHost.newTabSpec("Config").setIndicator("Config"),
//				ConfigUI.class, null);
//
//		initTabsAppearance(mTabHost.getTabWidget());
//		updateLang();
//	}

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		lang = readLangConfig();
    setContentView(R.layout.activity_main);
    mTabHost = (FragmentTabHost)findViewById(android.R.id.tabhost);
    mTabHost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);

    mTabHost.addTab(mTabHost.newTabSpec("link").setIndicator("Link"),
            LinkUi.class, null);
    mTabHost.addTab(mTabHost.newTabSpec("inventory").setIndicator("Inventory"),
        InventoryUI.class, null);
/*    mTabHost.addTab(mTabHost.newTabSpec("access").setIndicator("Access"),
        AccessUI.class, null);
    mTabHost.addTab(mTabHost.newTabSpec("ocnfig").setIndicator("Config"),
        ConfigUI.class, null);*/
	initTabsAppearance(mTabHost.getTabWidget());
	updateLang();

		/** 改变标题栏  */
		this.setTitle(getString(R.string.app_name) + " V" + getVerStr());
		//this.setTitle(getString(R.string.app_name)+"V1.0.19");

	/** updater = new AndroidApkUpdater(this) {
		String jsonUrl = getResources().getString(R.string.update_url);
		@Override
		protected Version getServerVersion() throws Exception {
			JSONObject jsonObject = Utility.get(jsonUrl);
			if (jsonObject == null)
				return null;
			int verCode = jsonObject.getInt("verCode");
			String verName = jsonObject.getString("verName");
			String apkUrl = jsonObject.getString("url");
			AndroidApkUpdater.Version version = 
					new AndroidApkUpdater.Version(verName, verCode, apkUrl);
			Log.i(TAG, version.toString());
			return version;
		}

		@Override
		protected String getPackageName() {
			return "com.vanch.vhxdemo";
		}
	};
	if (ConfigUI.getConfigCheckUpdate(this)) {
		updater.update();
	}*/
		if (ContextCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_CONNECT)
				!= PackageManager.PERMISSION_GRANTED) {
			// Request BLUETOOTH_CONNECT permission
			ActivityCompat.requestPermissions(this,
					new String[]{Manifest.permission.BLUETOOTH_CONNECT},
					REQUEST_ENABLE_BLUETOOTH_CONNECT);
		} else {
			// BLUETOOTH_CONNECT permission is already granted, proceed with your logic
			// ...
		}
	}
	@Override
	public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
		super.onRequestPermissionsResult(requestCode, permissions, grantResults);
		if (requestCode == REQUEST_ENABLE_BLUETOOTH_CONNECT) {
			if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED &&
					grantResults[1] == PackageManager.PERMISSION_GRANTED) {
				// Bluetooth permissions granted, proceed with your logic
				// ...
			} else {
				// Bluetooth permissions denied, show a message or handle accordingly
				Toast.makeText(this, "Bluetooth permissions are required for this app to function properly.", Toast.LENGTH_SHORT).show();
			}
		}
	}

	private String getPackageNamex() {
		return "com.vanch.vhxdemo";
	}

	private String getVerStr() {
		String verName = "1.x.x";
		try {
			verName = this.getPackageManager().getPackageInfo(
					getPackageNamex(), 0).versionName;
		} catch (NameNotFoundException e) {
		}
		return verName;
	}

	private void initTabsAppearance(TabWidget tabWidget) {
		/** change background */
		tabWidget.setBackgroundResource(R.drawable.bg_tab_normal);
		for (int i = 0; i < tabWidget.getChildCount(); i++) {
			tabWidget.getChildAt(i).setBackgroundResource(R.drawable.tab_bg);
			TextView tv = (TextView) tabWidget.getChildAt(i).findViewById(
					android.R.id.title);
			tv.setTextColor(Color.parseColor("#ffffff"));
		}
		tabWidget.setBackgroundResource(R.drawable.bg_tab_normal);
	}
	

	@Override
	protected void onResume() {
		super.onResume();
		Log.i(TAG, "onResume");
		EventBus.getDefault().registerSticky(this);
	}

	public void onEventMainThread(AccessUI.EpcSelectedEvent e) {
		Log.i(TAG, "epc select " + e);
		mTabHost.setCurrentTab(2);
	}

	public void onEventMainThread(ConfigUI.LangChanged e) {
		updateLang();
	}

	private void updateLang() {
		setTabTitle(0, Strings.getString(R.string.link_tab));
		setTabTitle(1, Strings.getString(R.string.inventory_tab));
		//setTabTitle(2, Strings.getString(R.string.access_tab));
		//setTabTitle(3, Strings.getString(R.string.config_tab));
	}

	private void setTabTitle(int idx, String title) {
		TabWidget widget = mTabHost.getTabWidget();
		TextView textView = (TextView) widget.getChildAt(idx).findViewById(
				android.R.id.title);
		textView.setText(title);
	}

//	@Override
//	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//		super.onActivityResult(requestCode, resultCode, data);
//	}

	@Override
	protected void onPause() {
		Log.i(TAG, "onPause");
		EventBus.getDefault().unregister(this);
		super.onPause();
	}

	@Override
	protected void onStop() {
		Log.i(TAG, "onStop");
		EventBus.getDefault().unregister(this);
		super.onStop();
	}

	@Override
	protected void onDestroy() {
		Log.i(TAG, "onDestroy");
		EventBus.getDefault().unregister(this);
		super.onDestroy();
	}

	private String readLangConfig() {
		SharedPreferences defaultPreferences = PreferenceManager
				.getDefaultSharedPreferences(this);
		String lang = defaultPreferences.getString(lang_key,
				Strings.LANGUAGE_ENGLISH);
		Log.i(TAG, "readLangConfig Lang " + lang);
		Strings.setLanguage(this, lang);
		return lang;
	}

//	public static class TabsAdapter extends FragmentPagerAdapter implements
//			TabHost.OnTabChangeListener, ViewPager.OnPageChangeListener {
//		private final Context mContext;
//		private final TabHost mTabHost;
//		private final ViewPager mViewPager;
//		private final ArrayList<TabInfo> mTabs = new ArrayList<TabInfo>();
//
//		static final class TabInfo {
//			private final String tag;
//			private final Class<?> clss;
//			private final Bundle args;
//
//			TabInfo(String _tag, Class<?> _class, Bundle _args) {
//				tag = _tag;
//				clss = _class;
//				args = _args;
//			}
//		}
//
//		static class DummyTabFactory implements TabHost.TabContentFactory {
//			private final Context mContext;
//
//			public DummyTabFactory(Context context) {
//				mContext = context;
//			}
//
//			@Override
//			public View createTabContent(String tag) {
//				View v = new View(mContext);
//				v.setMinimumWidth(0);
//				v.setMinimumHeight(0);
//				return v;
//			}
//		}
//
//		public TabsAdapter(FragmentActivity activity, TabHost tabHost,
//				ViewPager pager) {
//			super(activity.getSupportFragmentManager());
//			mContext = activity;
//			mTabHost = tabHost;
//			mViewPager = pager;
//			mTabHost.setOnTabChangedListener(this);
//			mViewPager.setAdapter(this);
//			mViewPager.setOnPageChangeListener(this);
//		}
//
//		public void addTab(TabHost.TabSpec tabSpec, Class<?> clss, Bundle args) {
//			tabSpec.setContent(new DummyTabFactory(mContext));
//			String tag = tabSpec.getTag();
//
//			TabInfo info = new TabInfo(tag, clss, args);
//			mTabs.add(info);
//			mTabHost.addTab(tabSpec);
//			notifyDataSetChanged();
//		}
//
//		@Override
//		public int getCount() {
//			return mTabs.size();
//		}
//
//		@Override
//		public Fragment getItem(int position) {
//			TabInfo info = mTabs.get(position);
//			return Fragment.instantiate(mContext, info.clss.getName(), info.args);
//		}
//
//		@Override
//		public void onTabChanged(String tabId) {
//			int position = mTabHost.getCurrentTab();
//			mViewPager.setCurrentItem(position);
//			Log.i(TAG, "onTabChanged " + tabId);
//		}
//
//		@Override
//		public void onPageScrolled(int position, float positionOffset,
//				int positionOffsetPixels) {
//		}
//
//		@Override
//		public void onPageSelected(int position) {
//			// Unfortunately when TabHost changes the current tab, it kindly
//			// also takes care of putting focus on it when not in touch mode.
//			// The jerk.
//			// This hack tries to prevent this from pulling focus out of our
//			// ViewPager.
//			TabWidget widget = mTabHost.getTabWidget();
//			int oldFocusability = widget.getDescendantFocusability();
//			widget.setDescendantFocusability(ViewGroup.FOCUS_BLOCK_DESCENDANTS);
//			mTabHost.setCurrentTab(position);
//			widget.setDescendantFocusability(oldFocusability);
//			//Log.i(TAG, "onPageSelected " + position);
//
//			//TabInfo info = mTabs.get(position);
//			//Fragment.instantiate(mContext, info.clss.getName(), info.args).onResume();
//			
////			switch (position) {
////			case 0:
////				LinkUi.me.onResume();
////				break;
////			case 1:
////				InventoryUI.me.onResume();
////				break;
////			case 2:
////				AccessUI.me.onResume();
////				break;
////			case 3:
////				ConfigUI.me.onResume();
////				break;
////			}
//		}
//
//		@Override
//		public void onPageScrollStateChanged(int state) {
//		}
//	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {

		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			AlertDialog.Builder localBuilder = new AlertDialog.Builder(this);
			localBuilder
					.setIcon(R.drawable.ic_on)
					.setTitle(Strings.getString(R.string.msg_Exitbox_Title))
					// 友情提示...
					.setMessage(Strings.getString(R.string.msg_Exitbox_Context)); // 你确定要退出吗？
			localBuilder.setPositiveButton(
					Strings.getString(R.string.msg_Exitbox_BtnOK), // 确定
					new DialogInterface.OnClickListener() {
						public void onClick(
								DialogInterface paramDialogInterface,
								int paramInt) {
							MainActivity.this.finish();
						}
					});
			localBuilder.setNegativeButton(
					Strings.getString(R.string.msg_Exitbox_BtnCancal), // "取消",
					new DialogInterface.OnClickListener() {
						public void onClick(
								DialogInterface paramDialogInterface,
								int paramInt) {
							paramDialogInterface.cancel();
						}
					}).create();
			localBuilder.show();

		} else if (keyCode == KeyEvent.KEYCODE_MENU) {
			return false;
		}
		return true;
	}

}
