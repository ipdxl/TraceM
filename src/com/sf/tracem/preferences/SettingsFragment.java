package com.sf.tracem.preferences;

import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Bundle;
import android.preference.PreferenceFragment;

import com.sf.tracem.R;

public class SettingsFragment extends PreferenceFragment implements
		OnSharedPreferenceChangeListener {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.preferences);
	}

	// ((EditTextPreference) findPreference(PreferenceKeys.IP_ADDRESS))
	// .setDefaultValue(PreferenceKeys.DEFAULT_IP_ADDRESS);
	//
	// ((EditTextPreference) findPreference(PreferenceKeys.SAP_USER))
	// .setDefaultValue(PreferenceKeys.DEFAULT_SAP_USER);
	//
	// ((EditTextPreference) findPreference(PreferenceKeys.SAP_PASSWORD))
	// .setDefaultValue(PreferenceKeys.DEFAULT_SAP_PASSWORD);

	@Override
	public void onSharedPreferenceChanged(SharedPreferences sharedPreferences,
			String key) {
	}

	@Override
	public void onResume() {
		super.onResume();
		getPreferenceScreen().getSharedPreferences()
				.registerOnSharedPreferenceChangeListener(this);
	}

	@Override
	public void onPause() {
		super.onPause();
		getPreferenceScreen().getSharedPreferences()
				.unregisterOnSharedPreferenceChangeListener(this);
	}

}
