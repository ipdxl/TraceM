package com.sf.tracem.visit;

import java.util.List;

import com.sf.tracem.R;
import com.sf.tracem.connection.Visit;
import com.sf.tracem.connection.VisitLog;
import com.sf.tracem.db.DBManager;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class VisitLogFragment extends Fragment {

	public static final String VISIT = "VISIT";
	public static final String TAG = VisitLogFragment.class.getName();
	private ListView list;
	private VisitLogAdapter logAdapter;
	private List<VisitLog> logList;
	private Visit visit;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setRetainInstance(true);
		visit = (Visit) getArguments().getSerializable(VISIT);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.visit_log, container, false);

		list = (ListView) view.findViewById(R.id.visit_log_list);

		return view;
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);

		DBManager dbManager = new DBManager(getActivity());
		logList = dbManager.getVisitLog(visit);
		logAdapter = new VisitLogAdapter(getActivity(), logList);
		list.setAdapter(logAdapter);
		logAdapter.notifyDataSetChanged();
	}

}
