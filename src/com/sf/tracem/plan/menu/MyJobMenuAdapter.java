/**
 * 
 */
package com.sf.tracem.plan.menu;

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
public class MyJobMenuAdapter extends ArrayAdapter<Menu> {
	/**
	 * 
	 * @param context
	 * @param objects
	 *            Menus list
	 */
	public MyJobMenuAdapter(Context context, List<Menu> objects) {
		super(context, android.R.layout.simple_list_item_activated_2,
				android.R.id.text1, objects);

		context.getResources().getStringArray(R.array.main_menu_titles);

	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = LayoutInflater.from(getContext());

		View view = inflater.inflate(
				android.R.layout.simple_list_item_activated_2, parent, false);

		TextView textId = (TextView) view.findViewById(android.R.id.text2);
		TextView title = (TextView) view.findViewById(android.R.id.text1);

		Menu menu = getItem(position);

		int id = menu.getIdMenu();
		textId.setText(String.valueOf(id));
		textId.setVisibility(View.INVISIBLE);

		switch (id) {
		case 6:
			title.setText(R.string.my_plans);
			break;
		case 7:
			title.setText(R.string.my_schedule);
			break;
		case 8:
			title.setText(R.string.my_path);
			break;
		case 9:
			title.setText(R.string.my_visit);
			break;
		default:
			break;
		}

		return view;
	}

}
