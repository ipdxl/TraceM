/**
 * 
 */
package com.sf.tracem.plan.detail;

import java.util.List;

import com.sf.tracem.R;
import com.sf.tracem.connection.Component;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/**
 * @author USER-7
 * 
 */
public class ComponentsAdapter extends ArrayAdapter<Component> {

	private TextView matnr;
	private TextView activity;
	private TextView maktx;
	private TextView menge;
	private TextView meins;

	public ComponentsAdapter(Context context, List<Component> components) {
		super(context, R.layout.component_list_item, R.id.matnr, components);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = super.getView(position, convertView, parent);

		matnr = (TextView) view.findViewById(R.id.matnr);
		activity = (TextView) view.findViewById(R.id.activity);
		maktx = (TextView) view.findViewById(R.id.maktx);
		menge = (TextView) view.findViewById(R.id.menge);
		meins = (TextView) view.findViewById(R.id.meins);

		Component component = getItem(position);

		matnr.setText(component.getMATERIAL());
		activity.setText(component.getACTIVITY());
		maktx.setText(component.getMATL_DESC());
		menge.setText(component.getREQUIREMENT_QUANTITY());
		meins.setText(component.getREQUIREMENT_QUANTITY_UNIT());

		return view;
	}

}
