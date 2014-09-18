/**
 * 
 */
package com.sf.tracem.visit;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckedTextView;
import android.widget.TextView;

import com.sf.tracem.R;
import com.sf.tracem.connection.Visit;

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
		super(context, R.layout.visit_item, R.id.id_schedule, objects);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = super.getView(position, convertView, parent);

		Visit item = getItem(position);

		TextView idProgram = (TextView) view.findViewById(R.id.id_schedule);
		TextView idVisit = (TextView) view.findViewById(R.id.id_visit);
		TextView comment = (TextView) view.findViewById(R.id.comment);
		TextView fini = (TextView) view.findViewById(R.id.fini);
		TextView hini = (TextView) view.findViewById(R.id.hini);
		TextView tini = (TextView) view.findViewById(R.id.tini);
		TextView ffin = (TextView) view.findViewById(R.id.ffin);
		TextView hfin = (TextView) view.findViewById(R.id.hfin);
		TextView tfin = (TextView) view.findViewById(R.id.tfin);
		CheckedTextView status = (CheckedTextView) view
				.findViewById(R.id.status);

		idProgram.setText("" + item.getID_PROGRAM());
		idVisit.setText("" + item.getID_VISIT());
		comment.setText(item.getCOMENTARIO());
		fini.setText(item.getFINI());
		hini.setText(item.getHINI());

		tini.setText(item.getTINI() == 1 ? R.string.automatic : R.string.manual);
		tini.setTextColor(item.getTINI() == 1 ? getContext().getResources()
				.getColor(android.R.color.holo_red_dark) : getContext()
				.getResources().getColor(android.R.color.holo_blue_dark));

		ffin.setText(item.getFFIN());
		hfin.setText(item.getHFIN());

		tfin.setText(item.getTFIN() == 1 ? R.string.automatic : R.string.manual);
		tfin.setTextColor(item.getTFIN() == 1 ? getContext().getResources()
				.getColor(android.R.color.holo_red_dark) : getContext()
				.getResources().getColor(android.R.color.holo_blue_dark));

		status.setChecked(item.getSTATUS() == 1 ? true : false);

		return view;
	}

}
