/**
 * 
 */
package com.sf.tracem.plan.detail;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.sf.tracem.connection.Equipment;

/**
 * @author José Guadalupe Mandujano Serrano
 * 
 */
public class EquipmentMenu extends Fragment {

	private static final String EQUIPMENTS = "EQUIPMENTS";
	private Equipment[] equipments;

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
				equipments = (Equipment[]) savedInstanceState
						.getSerializable(EQUIPMENTS);
			}
		}
		if (equipments == null) {
			equipments = (Equipment[]) getArguments().getSerializable(
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
				.setAdapter(new EquipmentArrayAdapter(getActivity(), equipments));

		return view;
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		outState.putSerializable(EQUIPMENTS, equipments);
		super.onSaveInstanceState(outState);
	}

}
