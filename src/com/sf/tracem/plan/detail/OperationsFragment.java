package com.sf.tracem.plan.detail;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

import com.sf.tracem.R;
import com.sf.tracem.connection.ZEOPERATION_ORDER;

public class OperationsFragment extends Fragment {

	private static final String OPERATIONS = "OPERATIONS";
	private ListView operationList;
	private ZEOPERATION_ORDER[] operations;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		setRetainInstance(true);
		onRestoreSavedonstanceState(savedInstanceState);
		super.onCreate(savedInstanceState);
	}

	private void onRestoreSavedonstanceState(Bundle savedInstanceState) {
		if (savedInstanceState != null) {
			if (savedInstanceState.getSerializable(OPERATIONS) != null) {
				operations = (ZEOPERATION_ORDER[]) savedInstanceState
						.getSerializable(OPERATIONS);
			}
		}
		if (operations == null) {
			operations = (ZEOPERATION_ORDER[]) getArguments().getSerializable(
					"operations");
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		onRestoreSavedonstanceState(savedInstanceState);

		// View view = inflater.inflate(R.layout.order_detail, container,
		// false);
		View view = inflater.inflate(android.R.layout.list_content, container,
				false);
		// operationList = (ListView) view.findViewById(R.id.operationsList);
		operationList = (ListView) view.findViewById(android.R.id.list);

		operationList
				.setAdapter(new opsArrayAdapter(getActivity(), operations));

		return view;
	}

	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
	}

	private class opsArrayAdapter extends ArrayAdapter<ZEOPERATION_ORDER> {

		private TextView activity, description, time;
		private CheckBox opStatus;

		public opsArrayAdapter(Context context, ZEOPERATION_ORDER[] objects) {
			super(context, R.layout.operation_detail, R.id.eqktx, objects);
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			LayoutInflater inflater = LayoutInflater.from(getContext());
			View view = inflater.inflate(R.layout.operation_detail, parent,
					false);

			activity = (TextView) view.findViewById(R.id.activity);
			description = (TextView) view.findViewById(R.id.eqktx);
			time = (TextView) view.findViewById(R.id.time);
			opStatus = (CheckBox) view.findViewById(R.id.operarion_status);

			ZEOPERATION_ORDER operation = getItem(position);

			activity.setText(operation.getACTIVITY());
			description.setText(operation.getDESCRIPTION());
			time.setText(operation.getDURATION_NORMAL() + " "
					+ operation.getDURATION_NORMAL_UNIT());

			opStatus.setChecked(operation.getCOMPLETE() == 1);
			opStatus.setActivated(false);

			return view;
		}
	}
}
