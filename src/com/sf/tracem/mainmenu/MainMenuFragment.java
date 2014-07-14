package com.sf.tracem.mainmenu;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Looper;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.sf.tracem.R;
import com.sf.tracem.connection.Connection;
import com.sf.tracem.connection.Menu;
import com.sf.tracem.connection.Message;
import com.sf.tracem.db.TraceMOpenHelper;
import com.sf.tracem.login.LoginSharedPreferences;
import com.sf.tracem.plan.MyJobActivity;

public class MainMenuFragment extends Fragment {

	private GridView menuGrid;
	private List<Menu> menus;
	private SQLiteDatabase tracemrdb;
	private Cursor cursorMenu;
	private List<Message> errorsList;
	private TraceMOpenHelper toh;
	private SharedPreferences loginPreferences;

	/**
	 * @param savedInstanceState
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		setRetainInstance(true);
		setHasOptionsMenu(true);
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.mainmenu_options, container,
				false);

		getActivity().getSharedPreferences(LoginSharedPreferences.LOGIN_PREFERENCES,
				Context.MODE_PRIVATE);

		TraceMOpenHelper toh = new TraceMOpenHelper(getActivity());
		tracemrdb = toh.getReadableDatabase();

		cursorMenu = tracemrdb.query(Menu.TABLE_NAME,
				new String[] { Menu.ID_MENU }, Menu.ID_FATHER
						+ " = ?", new String[] { "0" }, null, null,
				Menu.ID_MENU);

		menus = new ArrayList<Menu>();
		if (cursorMenu.moveToFirst())
			do {
				menus.add(new Menu((byte) cursorMenu.getShort(0), (byte) 0));
			} while (cursorMenu.moveToNext());

		menuGrid = (GridView) view.findViewById(R.id.menu_grid);
		menuGrid.setAdapter(new MainMenuAdapter(getActivity(), menus));

		menuGrid.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				TextView title = (TextView) view.findViewById(R.id.menu_title);

				String titleString = title.getText().toString();
				String[] options = getResources().getStringArray(
						R.array.main_menu_titles);

				for (int i = 0; i < options.length; i++) {
					if (titleString.equalsIgnoreCase(options[i])) {
						goToMenu(i);
						break;
					}
				}
			}
		});

		// myJobButton = (ImageButton) view.findViewById(R.id.my_job_item);
		// myJobButton.setOnClickListener(new OnClickListener() {
		//
		// @Override
		// public void onClick(View v) {
		// Intent intent = new Intent(getActivity(),
		// MyJobActivityMain.class);
		// getActivity().startActivity(intent);
		// }
		// });
		return view;
	}

	@Override
	public void onCreateOptionsMenu(android.view.Menu menu,
			MenuInflater inflater) {
		inflater.inflate(R.menu.user_menu, menu);
		super.onCreateOptionsMenu(menu, inflater);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == R.id.logout) {
			logout();
		}
		return super.onOptionsItemSelected(item);
	}

	private void logout() {

		AsyncTask<String, Integer, List<Message>> logoutTask = new AsyncTask<String, Integer, List<Message>>() {

			@Override
			protected List<Message> doInBackground(String... params) {

				try {
					Looper.prepare();
				} catch (Exception e) {
					e.printStackTrace();
				}

				try {
					Connection connection = new Connection(getActivity());
					errorsList = connection.logout(getActivity()
							.getSharedPreferences(
									LoginSharedPreferences.LOGIN_PREFERENCES,
									Context.MODE_PRIVATE).getString(
									LoginSharedPreferences.USERNAME, null));
				} catch (Exception e) {
					Toast.makeText(
							getActivity(),
							getActivity().getResources().getString(
									R.string.logout_error), Toast.LENGTH_LONG)
							.show();
					e.printStackTrace();
					return null;
				}
				return errorsList;
			}

			@Override
			protected void onPostExecute(List<Message> result) {

				// if (returnTable != null && returnTable.length != 0) {
				// clear database
				toh = new TraceMOpenHelper(getActivity());
				toh.clear();
				loginPreferences = getActivity().getSharedPreferences(
						LoginSharedPreferences.LOGIN_PREFERENCES, Context.MODE_PRIVATE);

				// clear LOGIN_PREFERENCES
				loginPreferences.edit().clear().commit();

				getActivity().recreate();
				// }
			}
		};

		logoutTask.execute();
	}

	protected void goToMenu(int i) {
		switch (i) {
		case 0:
			Intent intent = new Intent(getActivity(), MyJobActivity.class);
			getActivity().startActivity(intent);
			break;
		default:
		}
	}
}
