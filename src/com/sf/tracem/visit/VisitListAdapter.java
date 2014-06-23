/**
 * 
 */
package com.sf.tracem.visit;

import java.util.List;

import com.sf.tracem.connection.Visit;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/**
 * @author José Guadalupe Mandujano Serrano
 * 
 */
public class VisitListAdapter extends ArrayAdapter<Visit> {

	/**
	 * 
	 * @param context
	 *            Context
	 * @param objects
	 *            List of {@link Visit}
	 */
	public VisitListAdapter(Context context, List<Visit> objects) {
		super(context, android.R.layout.simple_list_item_activated_2,
				android.R.id.text1, objects);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = super.getView(position, convertView, parent);

		Visit item = getItem(position);

		TextView text1 = (TextView) view.findViewById(android.R.id.text1);
		TextView text2 = (TextView) view.findViewById(android.R.id.text2);

		text1.setText("" + item.getID_VISIT());
		text2.setText(item.getCOMENTARIO());

		return view;
	}

}
