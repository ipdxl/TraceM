package com.sf.tracem.plan.detail;

import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckedTextView;
import android.widget.TextView;

import com.sf.tracem.R;
import com.sf.tracem.connection.Operation;

/**
 * 
 * @author JGMS
 *
 */
class OpsArrayAdapter extends ArrayAdapter<Operation> {

	private OperationsFragment operationsFragment;
	private TextView activity, description, time;
	private CheckedTextView opStatus;

	public OpsArrayAdapter(OperationsFragment operationsFragment,
			Context context, List<Operation> operations) {
		super(context, R.layout.operation_item, R.id.eqktx, operations);
		this.operationsFragment = operationsFragment;
	}

	@SuppressLint("ViewHolder")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		LayoutInflater inflater = LayoutInflater.from(getContext());
		View view = inflater.inflate(R.layout.operation_item, parent, false);

		activity = (TextView) view.findViewById(R.id.activity);
		description = (TextView) view.findViewById(R.id.eqktx);
		time = (TextView) view.findViewById(R.id.time);
		opStatus = (CheckedTextView) view.findViewById(R.id.operarion_status);

		Operation operation = getItem(position);

		activity.setText(operation.getActivity());
		description.setText(operation.getDescription());
		time.setText(operation.getDurationNormal() + " " + operation.getDNU());

		opStatus.setChecked(operation.getComplete() == 1);
		if (OrderDetailFragment.VIEW_MODE.equals(operationsFragment.mode)) {
			opStatus.setEnabled(false);
		} else if (operation.getCommited() == 1) {
			opStatus.setEnabled(false);
			// opStatus.setVisibility(operation.getCommited() == 0 ?
			// View.VISIBLE
			// : View.INVISIBLE);
			opStatus.setCheckMarkDrawable(android.R.drawable.button_onoff_indicator_on);
		}
		return view;
	}
}