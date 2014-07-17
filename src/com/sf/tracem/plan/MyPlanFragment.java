/**
 *
 */
package com.sf.tracem.plan;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Looper;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
//import android.widget.TableLayout;
//import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.sf.tracem.R;
import com.sf.tracem.connection.Connection;
import com.sf.tracem.connection.Order;
import com.sf.tracem.db.DBManager;
import com.sf.tracem.login.LoginSharedPreferences;

/**
 * @author José Guadalupe Mandujano Serrano
 * 
 */
public class MyPlanFragment extends Fragment {

	public static final String TAG = "MY_PLAN_FRAGMENT";

	public static final String ORDERS = "ORDERS";

	// private TableLayout contentTable;
	//
	// private TableRow headerRow, contentRow;
	// private TextView aufnrHdr, aufartHdr, auftextHdr, co_gstrpHdr, nameHdr,
	// addressHdr;
	//
	// private TextView aufnr, aufart, auftext, co_gstrp, name, address;

	private List<Order> orders = new ArrayList<Order>();

	private MyJobNavigation navigation;

	private SharedPreferences loginPreferences;

	protected ProgressDialog progress;

	private ListView list;

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		navigation = (MyJobNavigation) activity;
		loginPreferences = activity.getSharedPreferences(
				LoginSharedPreferences.LOGIN_PREFERENCES, Context.MODE_PRIVATE);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setRetainInstance(true);
		navigation = (MyJobNavigation) getActivity();

		setHasOptionsMenu(true);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = getView();
		if (view != null) {
			container.removeView(view);
			return view;
		}

		// getOrders();
		// view = inflater.inflate(R.layout.job_layout, container, false);
		view = inflater
				.inflate(android.R.layout.list_content, container, false);
		list = (ListView) view.findViewById(android.R.id.list);

		// headerTable = (TableLayout) view.findViewById(R.id.headerTable);
		// contentTable = (TableLayout) view.findViewById(R.id.contentTable);

		// headerRow = (TableRow) inflater.inflate(R.layout.my_job_table_row,
		// headerTable, false);
		return view;
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		if (savedInstanceState != null) {

			Order[] ordersArray;
			ordersArray = (Order[]) savedInstanceState.getSerializable(ORDERS);
			orders = new ArrayList<Order>(Arrays.asList(ordersArray));
		} else {
			getOrdersFromDB();
		}
	}

	private void getOrdersFromDB() {
		getActivity().runOnUiThread(new Runnable() {

			@Override
			public void run() {
				DBManager db = new DBManager(getActivity());
				orders = db.getOrders();
				if (orders.size() > 0) {
					fillContentTable();
				} else {
					getOrders();
				}
			}
		});
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		Order[] ordersArray = new Order[orders.size()];
		orders.toArray(ordersArray);

		outState.putSerializable(ORDERS, ordersArray);

		super.onSaveInstanceState(outState);
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		// Inflate the menu items for use in the action bar
		// MyJobCustomActionBar mjcab = new MyJobCustomActionBar(getActivity()
		// .getActionBar(), getActivity());

		inflater.inflate(R.menu.my_job_ab, menu);
		super.onCreateOptionsMenu(menu, inflater);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.createPlan:
			navigation.onCreateSchedule(null);
			break;
		case R.id.refresh_program:
			getOrders();
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	private void getOrders() {

		AsyncTask<String, Void, List<Order>> task = new AsyncTask<String, Void, List<Order>>() {

			@Override
			protected void onPreExecute() {
				progress = new ProgressDialog(getActivity());
				progress.setMessage(getResources().getString(R.string.loading));
				progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
				progress.setIndeterminate(true);
				progress.show();
			}

			@Override
			protected List<Order> doInBackground(String... params) {
				try {
					Looper.prepare();
				} catch (Exception e) {
					e.printStackTrace();
				}
				try {

					Connection connection = new Connection(getActivity());
					orders = connection.getPlan(loginPreferences.getString(
							LoginSharedPreferences.USERNAME, null));
					if (orders != null) {
						return orders;
					}

				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					Looper.myLooper().quit();
				}
				return orders;
			}

			@Override
			protected void onPostExecute(List<Order> result) {
				fillContentTable();
				progress.hide();
			}
		};

		task.execute();
	}

	private synchronized void fillContentTable() {

		OrderPlanListAdapter adapter = new OrderPlanListAdapter(getActivity(),
				R.layout.order_item, R.id.aufnr, orders);

		list.setAdapter(adapter);

		list.setOnItemClickListener(new RowClickListener());

		//
		// contentTable.removeAllViews();
		//
		// LayoutInflater inflater = LayoutInflater.from(getActivity());
		//
		// headerRow = (TableRow) inflater.inflate(R.layout.job_table_row,
		// contentTable, false);
		// aufnrHdr = (TextView) headerRow.findViewById(R.id.aufnr);
		// aufnrHdr.setText(getResources().getString(R.string.aufnr));
		//
		// aufartHdr = (TextView) headerRow.findViewById(R.id.aufart);
		// aufartHdr.setText(getResources().getString(R.string.aufart));
		//
		// auftextHdr = (TextView) headerRow.findViewById(R.id.auftext);
		// auftextHdr.setText(getResources().getString(R.string.auftext));
		//
		// co_gstrpHdr = (TextView) headerRow.findViewById(R.id.co_gstrp);
		// co_gstrpHdr.setText(getResources().getString(R.string.co_gstrp));
		//
		// nameHdr = (TextView) headerRow.findViewById(R.id.name);
		// nameHdr.setText(getResources().getString(R.string.name));
		//
		// addressHdr = (TextView) headerRow.findViewById(R.id.address);
		// addressHdr.setText(getResources().getString(R.string.address));
		//
		// headerRow.setBackgroundColor(Color.GRAY);
		// contentTable.addView(headerRow);
		//
		// RowClickListener rcl = new RowClickListener();
		//
		// boolean flag = false;
		// for (Order item : orders) {
		//
		// // for (int i = 0; i < 30; i++) {
		// contentRow = (TableRow) inflater.inflate(R.layout.job_table_row,
		// contentTable, false);
		//
		// aufnr = (TextView) contentRow.findViewById(R.id.aufnr);
		// aufnr.setText(item.getAUFNR());
		//
		// aufart = (TextView) contentRow.findViewById(R.id.aufart);
		// aufart.setText(item.getAUFART());
		//
		// auftext = (TextView) contentRow.findViewById(R.id.auftext);
		// auftext.setText(item.getAUFTEXT());
		//
		// co_gstrp = (TextView) contentRow.findViewById(R.id.co_gstrp);
		// co_gstrp.setText(item.getCO_GSTRP());
		//
		// name = (TextView) contentRow.findViewById(R.id.name);
		// name.setText(item.getPARTNER());
		//
		// address = (TextView) contentRow.findViewById(R.id.address);
		// address.setText(item.getADDRESS());
		//
		// contentRow.setOnClickListener(rcl);
		//
		// if (flag) {
		// contentRow.setBackgroundResource(R.color.blue_sf);
		// } else {
		// contentRow.setBackgroundResource(R.color.light_gray);
		// }
		//
		// flag = !flag;
		// contentTable.addView(contentRow);
		//
		// // }
		// }
		// contentTable.setStretchAllColumns(true);
		// contentTable.setShrinkAllColumns(true);
	}

	private class RowClickListener implements OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			TextView aufnr = (TextView) view.findViewById(R.id.aufnr);

			TextView name = (TextView) view.findViewById(R.id.name);
			Toast.makeText(view.getContext(), aufnr.getText().toString(),
					Toast.LENGTH_SHORT).show();
			navigation.onShowOrderDetail(aufnr.getText().toString(), name
					.getText().toString());

		}
	}
}
