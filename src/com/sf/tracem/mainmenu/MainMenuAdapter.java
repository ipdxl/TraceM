/**
 * 
 */
package com.sf.tracem.mainmenu;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.sf.tracem.R;
import com.sf.tracem.connection.Menu;

/**
 * @author José Guadalupe Mandujano Serrano
 * 
 */
public class MainMenuAdapter extends ArrayAdapter<Menu> {
	/**
	 * Menu titles array
	 */
	private String[] titles;

	/**
	 * 
	 * @param context
	 * @param objects
	 *            Menus list
	 */
	public MainMenuAdapter(Context context, List<Menu> objects) {
		super(context, R.layout.main_menu_item, R.id.menu_title, objects);

		titles = context.getResources()
				.getStringArray(R.array.main_menu_titles);

	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = LayoutInflater.from(getContext());

		View view = inflater.inflate(R.layout.main_menu_item, parent, false);

		TextView textId = (TextView) view.findViewById(R.id.id_main_menu);
		TextView title = (TextView) view.findViewById(R.id.menu_title);

		Menu menu = getItem(position);

		int id = menu.getIdMenu();
		textId.setText(String.valueOf(id));
		title.setText(titles[position]);

		switch (id) {
		case 1:
			title.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.my_job,
					0, 0);
			break;
		case 2:
			title.setCompoundDrawablesWithIntrinsicBounds(0,
					R.drawable.my_stock, 0, 0);
			break;
		case 3:
			title.setCompoundDrawablesWithIntrinsicBounds(0,
					R.drawable.my_purchases, 0, 0);
			break;
		case 4:
			title.setCompoundDrawablesWithIntrinsicBounds(0,
					R.drawable.my_effort, 0, 0);
			break;
		case 5:
			title.setCompoundDrawablesWithIntrinsicBounds(0,
					R.drawable.my_notifications, 0, 0);
			break;

		default:
			break;
		}

		return view;
	}

}
