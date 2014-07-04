package com.sf.tracem.plan.detail;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.sf.tracem.R;
import com.sf.tracem.connection.Equipment;

public class EquipmentArrayAdapter extends ArrayAdapter<Equipment> {

	private TextView eqktx, equnr;
	private CheckBox complete;

	public EquipmentArrayAdapter(Context context, Equipment[] objects) {
		super(context, R.layout.equipment_item, R.id.eqktx, objects);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = super.getView(position, convertView, parent);

		eqktx = (TextView) view.findViewById(R.id.eqktx);
		equnr = (TextView) view.findViewById(R.id.equnr);
		complete = (CheckBox) view.findViewById(R.id.complete);

		Equipment equipment = getItem(position);

		equnr.setText(equipment.getEQUNR());
		eqktx.setText(equipment.getEQKTX());

		complete.setChecked(equipment.getComplete() == 1);
		complete.setEnabled(false);

		return view;
	}

}