/**
 * 
 */
package com.sf.tracem.plan.detail;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ListView;

import com.sf.tracem.connection.Equipment;

/**
 * @author José Guadalupe Mandujano Serrano
 * 
 */
public class EquipmentMenu extends Fragment {

	public final static String EQUIPMENTS = "EQUIPMENTS";
	private List<Equipment> equipments;

	private ExpandableListView equipmentsList;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		setRetainInstance(true);
		onRestoreSavedInstanceState(savedInstanceState);
		super.onCreate(savedInstanceState);
	}

	@SuppressWarnings("unchecked")
	private void onRestoreSavedInstanceState(Bundle savedInstanceState) {
		if (savedInstanceState != null) {
			if (savedInstanceState.getSerializable(EQUIPMENTS) != null) {
				equipments = (ArrayList<Equipment>) savedInstanceState
						.getSerializable(EQUIPMENTS);
			}
		}
		if (equipments == null) {
			equipments = (ArrayList<Equipment>) getArguments().getSerializable(
					EQUIPMENTS);
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		onRestoreSavedInstanceState(savedInstanceState);
		//
		// View view = inflater.inflate(R.layout.equipment_layout, container,
		// false);
		View view = inflater.inflate(android.R.layout.expandable_list_content,
				container, false);

		// equipmentsList = (ListView) view.findViewById(R.id.operationsList);
		equipmentsList = (ExpandableListView) view
				.findViewById(android.R.id.list);

		equipmentsList.setAdapter(new EquipmentArrayAdapter(getActivity(),
				equipments));

		return view;
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		outState.putSerializable(EQUIPMENTS, (ArrayList<Equipment>) equipments);
		super.onSaveInstanceState(outState);
	}

}
