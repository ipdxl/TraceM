/**
 * 
 */
package com.sf.tracem.visit;

import java.util.List;

import com.sf.tracem.R;
import com.sf.tracem.connection.VisitLog;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/**
 * @author USER-7
 * 
 */
public class VisitLogAdapter extends ArrayAdapter<VisitLog> {

	public VisitLogAdapter(Context context, List<VisitLog> objects) {
		super(context, R.layout.visit_log_item, R.id.text_event, objects);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = super.getView(position, convertView, parent);

		TextView textEvent = (TextView) view.findViewById(R.id.text_event);
		TextView date = (TextView) view.findViewById(R.id.date);
		TextView hour = (TextView) view.findViewById(R.id.hour);

		VisitLog log = getItem(position);

		textEvent.setText(log.getText_event());
		date.setText(log.getDate());
		hour.setText(log.getHour());

		return view;
	}

}
