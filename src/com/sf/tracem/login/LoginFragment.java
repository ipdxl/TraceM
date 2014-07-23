/**
 *
 */
package com.sf.tracem.login;

import java.util.List;

import android.content.ContentValues;
import android.content.SharedPreferences.Editor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Looper;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.sf.tracem.R;
import com.sf.tracem.connection.Connection;
import com.sf.tracem.connection.Menu;
import com.sf.tracem.connection.Z_PM_AP_LOGIN;
import com.sf.tracem.db.TraceMOpenHelper;
import com.sf.tracem.mainmenu.MainMenuFragment;

/**
 * @author José Guadalupe Mandujano Serrano
 * 
 */
public class LoginFragment extends Fragment {

	public Fragment mmfa;
	public final String MENU_TAG = "MainMenuFragment";
	TextView user;
	TextView pass;
	Z_PM_AP_LOGIN response;
	private Button loginButton;
	public boolean isSigning;
	private Editor editor;
	private SQLiteDatabase tracemdb;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// Retain this fragment across configuration changes.
		setRetainInstance(true);

		// Create and execute the background task.
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.login, container, false);
		try {

			loginButton = (Button) view.findViewById(R.id.loginbutton);
			loginButton.setOnClickListener(new OnClickHandler());
			user = (TextView) view.findViewById(R.id.username);
			pass = (TextView) view.findViewById(R.id.password);

			pass.setOnKeyListener(new OnKeyListener() {

				@Override
				public boolean onKey(View v, int keyCode, KeyEvent event) {
					if (event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
						loginButton.callOnClick();
					}
					return false;
				}
			});

			FragmentManager fragmentManager = getFragmentManager();
			mmfa = (MainMenuFragment) fragmentManager
					.findFragmentByTag(MENU_TAG);

			if (mmfa != null) {
				addMainMenu();
			}

			return view;
		} catch (Exception e) {
			e.printStackTrace();
			return view;
		}
	}

	private void addMainMenu() {

		if (response.getRol() == null || response.getRol().isEmpty()) {
			Toast.makeText(getActivity(),
					getResources().getString(R.string.incorrect_login),
					Toast.LENGTH_SHORT).show();
			return;
		}

		editor = PreferenceManager.getDefaultSharedPreferences(getActivity())
				.edit();

		editor.putString(PreferenceKeys.USERNAME, user.getText()
				.toString());
		editor.putString(PreferenceKeys.PASSWORD, pass.getText()
				.toString());

		List<Menu> menuList = response.getMenuList();

		TraceMOpenHelper toh = new TraceMOpenHelper(getActivity());
		toh.clear();
		tracemdb = toh.getWritableDatabase();

		ContentValues values = new ContentValues();
		for (Menu item : menuList) {
			values.put(Menu.ID_MENU, item.getIdMenu());
			values.put(Menu.ID_FATHER, item.getIdFather());
			tracemdb.insert(Menu.TABLE_NAME, null, values);
		}

		editor.commit();

		FragmentManager fragmentManager = getFragmentManager();
		mmfa = (MainMenuFragment) fragmentManager.findFragmentByTag(MENU_TAG);

		if (mmfa == null) {
			mmfa = new MainMenuFragment();
		}

		FragmentTransaction fragmentTransaction = fragmentManager
				.beginTransaction();
		// replace content in container
		fragmentTransaction.replace(R.id.mainFrameLayout, mmfa, MENU_TAG);
		fragmentTransaction.commit();
	}

	private class OnClickHandler implements OnClickListener {

		@Override
		public void onClick(View v) {

			final String userText = user.getText().toString();
			final String passText = pass.getText().toString();

			if (userText.isEmpty() || passText.isEmpty() || isSigning) {
				return;
			}
			isSigning = true;

			new AsyncTask<String, Void, Void>() {

				@Override
				protected Void doInBackground(String... params) {

					try {
						Looper.prepare();
					} catch (Exception e) {
						e.printStackTrace();
					}

					setControlsEnabled(false);
					try {
						response = null;
						Connection connection = new Connection(
								LoginFragment.this.getActivity());
						response = connection.login(userText, passText);
					} catch (Exception e) {
						e.printStackTrace();
						response = new Z_PM_AP_LOGIN();
					} finally {
						setControlsEnabled(true);
					}
					return null;
				}

				@Override
				protected void onPostExecute(Void result) {
					addMainMenu();
				}

			}.execute();
		}
	}

	private void setControlsEnabled(final boolean enabled) {
		isSigning = !enabled;
		getActivity().runOnUiThread(new Runnable() {

			@Override
			public void run() {
				loginButton.setEnabled(enabled);
				user.setEnabled(enabled);
				pass.setEnabled(enabled);
			}
		});
	}
}
