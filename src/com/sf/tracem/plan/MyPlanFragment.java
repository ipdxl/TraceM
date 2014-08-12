/**
 *
 */
package com.sf.tracem.plan;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.app.Activity;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Looper;
import android.preference.PreferenceManager;
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
import com.sf.tracem.login.PreferenceKeys;

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
		loginPreferences = PreferenceManager
				.getDefaultSharedPreferences(activity);
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
		list.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

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
		menu.clear();
		inflater.inflate(R.menu.my_job_ab, menu);
		super.onCreateOptionsMenu(menu, inflater);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.refresh_schedule:
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
				progress.setCancelable(false);
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
							PreferenceKeys.USERNAME, null));
					if (orders != null) {
						return orders;
					}

				} catch (Exception e) {
					e.printStackTrace();
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
