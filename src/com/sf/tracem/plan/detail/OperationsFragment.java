package com.sf.tracem.plan.detail;

import java.util.ArrayList;
import java.util.List;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.CheckedTextView;
import android.widget.ListView;

import com.sf.tracem.R;
import com.sf.tracem.connection.Operation;
import com.sf.tracem.db.DBManager;

public class OperationsFragment extends Fragment {

	public static final String OPERATIONS = "OPERATIONS";
	private ListView List;
	private List<Operation> operations;
	String mode;
	private DBManager dbManager;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		setRetainInstance(true);
		onRestoreSavedonstanceState(savedInstanceState);
		mode = getArguments().getString(OrderDetailFragment.MODE,
				OrderDetailFragment.VIEW_MODE);
		dbManager = new DBManager(getActivity());
		super.onCreate(savedInstanceState);
	}

	@SuppressWarnings("unchecked")
	private void onRestoreSavedonstanceState(Bundle savedInstanceState) {
		if (savedInstanceState != null) {
			if (savedInstanceState.getSerializable(OPERATIONS) != null) {
				operations = (ArrayList<Operation>) savedInstanceState
						.getSerializable(OPERATIONS);
			}
		}
		if (operations == null) {
			operations = (ArrayList<Operation>) getArguments().getSerializable(
					OPERATIONS);
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
		List = (ListView) view.findViewById(android.R.id.list);

		List.setAdapter(new OpsArrayAdapter(this, getActivity(), operations));

		return view;
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);

		if (OrderDetailFragment.EDIT_MODE.equals(mode)) {
			List.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {

					CheckedTextView opStatus = (CheckedTextView) view
							.findViewById(R.id.operarion_status);
					if (opStatus.isEnabled()) {
						opStatus.setChecked(!opStatus.isChecked());

						Operation op = operations.get(position);
						op.setComplete(op.getComplete() == 1 ? 0 : 1);
						dbManager.updateOperation(op);
					}
				}
			});
		}
	}

	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
	}
}
