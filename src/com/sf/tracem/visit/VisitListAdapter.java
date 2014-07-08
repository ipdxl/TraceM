/**
 * 
 */
package com.sf.tracem.visit;

import java.util.List;

import com.sf.tracem.R;
import com.sf.tracem.connection.Visit;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.RadioButton;
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
		super(context, R.layout.visit_item, R.id.id_program, objects);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = super.getView(position, convertView, parent);

		Visit item = getItem(position);

		TextView idProgram = (TextView) view.findViewById(R.id.id_program);
		TextView idVisit = (TextView) view.findViewById(R.id.id_visit);
		TextView comment = (TextView) view.findViewById(R.id.comment);
		TextView fini = (TextView) view.findViewById(R.id.fini);
		TextView hini = (TextView) view.findViewById(R.id.hini);
		CheckBox tini = (CheckBox) view.findViewById(R.id.tini);
		TextView ffin = (TextView) view.findViewById(R.id.ffin);
		TextView hfin = (TextView) view.findViewById(R.id.hfin);
		CheckBox tfin = (CheckBox) view.findViewById(R.id.tfin);
		RadioButton status = (RadioButton) view.findViewById(R.id.status);

		idProgram.setText("" + item.getID_PROGRAM());
		idVisit.setText("" + item.getID_VISIT());
		comment.setText(item.getCOMENTARIO());
		fini.setText(item.getFINI());
		hini.setText(item.getHINI());
		tini.setChecked(item.getTINI() == 1 ? true : false);
		ffin.setText(item.getFFIN());
		hfin.setText(item.getHFIN());
		tfin.setChecked(item.getTFIN() == 1 ? true : false);
		status.setChecked(item.getSTATUS() == 1 ? true : false);

		return view;
	}

}
