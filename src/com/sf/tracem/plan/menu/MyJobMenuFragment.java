/**
 * 
 */
package com.sf.tracem.plan.menu;

import java.util.ArrayList;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.sf.tracem.R;
import com.sf.tracem.connection.Menu;
import com.sf.tracem.db.TraceMOpenHelper;
import com.sf.tracem.plan.MyJobNavigation;

/**
 * @author José Guadalupe Mandujano Serrano
 * 
 */
public class MyJobMenuFragment extends Fragment {

	public static final String TAG = "MY_JOB_MENU_FRAGMENT";
	public ListView menuList;
	private MyJobNavigation navigation;
	public int lastItemSelected;
	private SQLiteDatabase tracemrdb;
	private TraceMOpenHelper toh;
	private Cursor menus;
	private ArrayList<Menu> items;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		navigation = (MyJobNavigation) getActivity();
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.job_menu_fragment, container,
				false);

		menuList = (ListView) view.findViewById(R.id.my_job_menu_drawer_list);
		// menuList.setSelector(R.drawable.list_view_keep_selection);

		toh = new TraceMOpenHelper(getActivity());

		tracemrdb = toh.getReadableDatabase();

		menus = tracemrdb.query(Menu.TABLE_NAME, new String[] { Menu.ID_MENU },
				Menu.ID_FATHER + " = ?", new String[] { "1" }, null, null,
				Menu.ID_MENU);

		items = new ArrayList<Menu>();
		if (menus.moveToFirst()) {
			do {
				items.add(new Menu((byte) menus.getShort(0), (byte) 1));
			} while (menus.moveToNext());
		}

		menuList.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

		menuList.setAdapter(new MyJobMenuAdapter(getActivity(), items));

		menuList.setOnItemClickListener(new menuListItemClickListener());
		return view;

	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		try {
			menuList.setItemChecked(0, true);
			gotoMenuSelected(items.get(0).getIdMenu());
		} catch (Exception e) {
			e.printStackTrace();
		}
		super.onViewCreated(view, savedInstanceState);
	}

	private class menuListItemClickListener implements OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {

			TextView idMenu = (TextView) view.findViewById(android.R.id.text2);

			int menuIdSelected = Integer.parseInt(idMenu.getText().toString());

			if (menuIdSelected == lastItemSelected) {
				return;
			}

			lastItemSelected = menuList.getCheckedItemPosition();

			gotoMenuSelected(menuIdSelected);

		}
	}

	public void gotoMenuSelected(int menuIdSelected) {
		switch (menuIdSelected) {
		case 6:
			navigation.onViewMyJob();
			break;
		case 7:
			navigation.onViewPlans();
			break;
		case 8:
			navigation.onViewMyPath();
			break;
		case 9:
			navigation.onViewMyVisit();
			break;
		}
	}
}
