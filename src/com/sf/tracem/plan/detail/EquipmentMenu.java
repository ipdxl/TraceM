/**
 * 
 */
package com.sf.tracem.plan.detail;

import java.util.ArrayList;
import java.util.List;

import android.app.PendingIntent.CanceledException;
import android.database.DataSetObserver;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;

import com.sf.tracem.connection.Equipment;
import com.sf.tracem.connection.MeasurementPoint;
import com.sf.tracem.db.DBManager;
import com.sf.tracem.visit.EditMeasureDialog;

/**
 * @author José Guadalupe Mandujano Serrano
 * 
 */
public class EquipmentMenu extends Fragment {

	public final static String EQUIPMENTS = "EQUIPMENTS";
	private List<Equipment> equipments;

	private ExpandableListView list;
	private String mode;
	private EquipmentExpandableListAdapter adapter;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		setRetainInstance(true);
		onRestoreSavedInstanceState(savedInstanceState);
		mode = getArguments().getString(OrderDetailFragment.MODE,
				OrderDetailFragment.VIEW_MODE);
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
		list = (ExpandableListView) view.findViewById(android.R.id.list);

		return view;
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		adapter = new EquipmentExpandableListAdapter(getActivity(), equipments);
		list.setAdapter(adapter);

		if (OrderDetailFragment.EDIT_MODE.equals(mode)) {
			list.setOnChildClickListener(new OnChildClickListener() {

				@Override
				public boolean onChildClick(ExpandableListView parent, View v,
						int groupPosition, int childPosition, long id) {

					final MeasurementPoint mp = equipments.get(groupPosition)
							.getMeasures().get(childPosition);

					final EditMeasureDialog emd = new EditMeasureDialog(mp);

					AsyncTask<String, Integer, MeasurementPoint> editMeasureTask = new AsyncTask<String, Integer, MeasurementPoint>() {

						@Override
						protected void onPreExecute() {
							emd.show(getFragmentManager(),
									EditMeasureDialog.TAG);
						}

						@Override
						protected MeasurementPoint doInBackground(
								String... params) {
							while (emd.isShow()) {
								try {
									Thread.sleep(500);
								} catch (InterruptedException e) {
									e.printStackTrace();
								}
							}

							if (emd.isCanceled()) {
								return null;
							} else {
								return emd.getMp();
							}
						}

						@Override
						protected void onPostExecute(MeasurementPoint result) {
							if (result != null) {
								DBManager dbManager = new DBManager(
										getActivity());
								dbManager.updateMeasurementPoint(result);
								adapter.notifyDataSetChanged();
							}
						}
					};

					editMeasureTask.execute();

					return false;
				}
			});
		}
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		outState.putSerializable(EQUIPMENTS, (ArrayList<Equipment>) equipments);
		super.onSaveInstanceState(outState);
	}

}
