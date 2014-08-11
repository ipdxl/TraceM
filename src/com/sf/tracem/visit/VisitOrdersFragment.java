package com.sf.tracem.visit;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.sf.tracem.R;
import com.sf.tracem.connection.Order;
import com.sf.tracem.plan.MyJobNavigation;
import com.sf.tracem.schedule.OrderListAdapter;

public class VisitOrdersFragment extends Fragment {

	public final static String ORDERS = "ORDERS";

	private List<Order> orders;
	private ListView list;
	private MyJobNavigation navigation;
	private int lastItemChecked = -1;

	private OrderListAdapter adapter;

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		navigation = (MyJobNavigation) activity;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		orders = (ArrayList<Order>) getArguments().getSerializable(ORDERS);
		setRetainInstance(true);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.visit_orders, container, false);
		list = (ListView) view.findViewById(R.id.list);
		list.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

		adapter = new OrderListAdapter(getActivity(), orders);

		return view;
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		list.setAdapter(adapter);
		list.setOnItemClickListener(new OnItemClickListener() {

			private TextView aufnr;

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				if (list.getCheckedItemPosition() != lastItemChecked) {
					lastItemChecked = list.getCheckedItemPosition();
					aufnr = (TextView) view.findViewById(R.id.aufnr);
					navigation.OnVisitOrderSelected(aufnr.getText().toString());
				}
			}
		});
	}
}
