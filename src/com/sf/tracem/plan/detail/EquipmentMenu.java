/**
 * 
 */
package com.sf.tracem.plan.detail;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.sf.tracem.R;
import com.sf.tracem.connection.ZEOBJECT_ORDER;

/**
 * @author José Guadalupe Mandujano Serrano
 * 
 */
public class EquipmentMenu extends Fragment {

	private static final String EQUIPMENTS = "EQUIPMENTS";
	private ZEOBJECT_ORDER[] equipments;

	private ListView equipmentsList;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		setRetainInstance(true);
		onRestoreSavedInstanceState(savedInstanceState);
		super.onCreate(savedInstanceState);
	}

	private void onRestoreSavedInstanceState(Bundle savedInstanceState) {
		if (savedInstanceState != null) {
			if (savedInstanceState.getSerializable(EQUIPMENTS) != null) {
				equipments = (ZEOBJECT_ORDER[]) savedInstanceState
						.getSerializable(EQUIPMENTS);
			}
		}
		if (equipments == null) {
			equipments = (ZEOBJECT_ORDER[]) getArguments().getSerializable(
					"equipments");
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		onRestoreSavedInstanceState(savedInstanceState);
		//
		// View view = inflater.inflate(R.layout.equipment_layout, container,
		// false);
		View view = inflater.inflate(android.R.layout.list_content, container,
				false);

		// equipmentsList = (ListView) view.findViewById(R.id.operationsList);
		equipmentsList = (ListView) view.findViewById(android.R.id.list);

		equipmentsList
				.setAdapter(new eqArrayAdapter(getActivity(), equipments));

		return view;
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		outState.putSerializable(EQUIPMENTS, equipments);
		super.onSaveInstanceState(outState);
	}

	private class eqArrayAdapter extends ArrayAdapter<ZEOBJECT_ORDER> {

		private TextView eqktx, equnr;

		public eqArrayAdapter(Context context, ZEOBJECT_ORDER[] objects) {
			super(context, R.layout.equipment_item, R.id.eqktx, objects);
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			LayoutInflater inflater = LayoutInflater.from(getContext());
			// View view = inflater
			// .inflate(R.layout.equipment_item, parent, false);

			View view = inflater.inflate(android.R.layout.simple_list_item_2,
					parent, false);

			// eqktx = (TextView) view.findViewById(R.id.eqktx);
			// equnr = (TextView) view.findViewById(R.id.equnr);

			eqktx = (TextView) view.findViewById(android.R.id.text1);
			equnr = (TextView) view.findViewById(android.R.id.text2);

			ZEOBJECT_ORDER equipment = getItem(position);

			equnr.setText(equipment.getEQUNR());
			eqktx.setText(equipment.getEQKTX());

			return view;
		}

	}
}
