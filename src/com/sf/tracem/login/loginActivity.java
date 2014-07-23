/**
 * 
 */
package com.sf.tracem.login;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.sf.tracem.R;
import com.sf.tracem.mainmenu.MainMenuFragment;
import com.sf.tracem.preferences.SettingsActivity;

/**
 * @author José Guadalupe Mandujano Serrano
 * 
 */
public class loginActivity extends FragmentActivity {

	LoginFragment loginFragment;
	MainMenuFragment mmf;
	private SharedPreferences sharedPreferences;
	private String loginName;
	final static String TAG_TASK_FRAGMENT = "LoginFragment";
	final static String MAIN_MENU_FRAGMENT = "MainMenuFragment";

	/**
	 * 
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.mainmenu);
		loginFragment = new LoginFragment();
		FragmentManager fragmentManager = getSupportFragmentManager();

		loginFragment = (LoginFragment) fragmentManager
				.findFragmentByTag(TAG_TASK_FRAGMENT);

		mmf = (MainMenuFragment) fragmentManager
				.findFragmentByTag(MAIN_MENU_FRAGMENT);

		FragmentTransaction fragmentTransaction = fragmentManager
				.beginTransaction();

		sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

		loginName = sharedPreferences.getString(
				PreferenceKeys.USERNAME, null);

		if (loginName == null) {
			mmf = null;
		} else {
			if (mmf == null) {
				mmf = new MainMenuFragment();
			}
		}

		if (mmf == null) {
			if (loginFragment == null) {
				loginFragment = new LoginFragment();
			}
			fragmentTransaction.replace(R.id.mainFrameLayout, loginFragment,
					TAG_TASK_FRAGMENT);
		} else {
			fragmentTransaction.replace(R.id.mainFrameLayout, mmf,
					MAIN_MENU_FRAGMENT);
		}
		fragmentTransaction.commit();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		MenuInflater inflater = new MenuInflater(this);
		inflater.inflate(R.menu.main_menu, menu);

		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {
		case R.id.settings:
			Intent intent = new Intent(this, SettingsActivity.class);
			startActivity(intent);
			break;
		}

		return super.onOptionsItemSelected(item);
	}
}
