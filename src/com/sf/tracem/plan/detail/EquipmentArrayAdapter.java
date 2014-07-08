package com.sf.tracem.plan.detail;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.sf.tracem.R;
import com.sf.tracem.connection.Equipment;
import com.sf.tracem.connection.MeasurementPoint;

public class EquipmentArrayAdapter extends BaseExpandableListAdapter {

	private TextView eqktx, equnr;
	private CheckBox complete;
	private List<Equipment> equipmets;
	private Context context;

	public EquipmentArrayAdapter(Context context, List<Equipment> equipments) {
		this.context = context;
		this.equipmets = equipments;
	}

	@Override
	public Object getChild(int groupPosition, int childPosition) {
		return equipmets.get(groupPosition).getMeasures().get(childPosition);
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		return Long.parseLong(equipmets.get(groupPosition).getMeasures()
				.get(childPosition).getAufnr());
	}

	@Override
	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {

		LayoutInflater inflater = LayoutInflater.from(context);

		View view = inflater.inflate(R.layout.measurement_item, parent, false);

		TextView point = (TextView) view.findViewById(R.id.point);
		TextView description = (TextView) view.findViewById(R.id.comment);
		TextView read = (TextView) view.findViewById(R.id.read);
		TextView unit = (TextView) view.findViewById(R.id.unit);
		TextView notes = (TextView) view.findViewById(R.id.notes);

		MeasurementPoint mp = (MeasurementPoint) getChild(groupPosition,
				childPosition);

		point.setText(mp.getPoint());
		description.setText(mp.getDescription());
		read.setText("" + mp.getRead());
		unit.setText(mp.getUnit());
		notes.setText(mp.getNotes());

		return view;
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		return equipmets.get(groupPosition).getMeasures().size();
	}

	@Override
	public Object getGroup(int groupPosition) {
		return equipmets.get(groupPosition);
	}

	@Override
	public int getGroupCount() {
		return equipmets.size();
	}

	@Override
	public long getGroupId(int groupPosition) {
		return groupPosition;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {

		LayoutInflater inflater = LayoutInflater.from(context);

		View view = inflater.inflate(R.layout.equipment_item, parent, false);

		eqktx = (TextView) view.findViewById(R.id.eqktx);
		equnr = (TextView) view.findViewById(R.id.equnr);
		complete = (CheckBox) view.findViewById(R.id.complete);

		Equipment equipment = (Equipment) getGroup(groupPosition);

		equnr.setText(equipment.getEQUNR());
		eqktx.setText(equipment.getEQKTX());

		complete.setChecked(equipment.getComplete() == 1);
		complete.setEnabled(false);

		return view;
	}

	@Override
	public boolean hasStableIds() {
		return false;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return true;
	}

}