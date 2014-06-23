/**
 *
 */
package com.sf.tracem.plan;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Looper;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.sf.tracem.R;
import com.sf.tracem.connection.Connection;
import com.sf.tracem.connection.ZEORDER;
import com.sf.tracem.db.DBManager;
import com.sf.tracem.login.CurrentConfig;

/**
 * @author Jos� Guadalupe Mandujano Serrano
 * 
 */
public class MyPlanFragment extends Fragment {

	public static final String TAG = "MY_PLAN_FRAGMENT";

	private TableLayout contentTable;

	private TableRow headerRow, contentRow;
	private TextView aufnrHdr, aufartHdr, auftextHdr, co_gstrpHdr, nameHdr,
			addressHdr;

	private TextView aufnr, aufart, auftext, co_gstrp, name, address;

	private List<ZEORDER> orders = new ArrayList<ZEORDER>();

	private MyJobNavigation navigation;

	private SharedPreferences loginPreferences;

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		navigation = (MyJobNavigation) activity;
		loginPreferences = activity.getSharedPreferences(
				CurrentConfig.LOGIN_PREFERENCES, Context.MODE_PRIVATE);
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
		view = inflater.inflate(R.layout.my_job_layout, container, false);

		// headerTable = (TableLayout) view.findViewById(R.id.headerTable);
		contentTable = (TableLayout) view.findViewById(R.id.contentTable);

		// headerRow = (TableRow) inflater.inflate(R.layout.my_job_table_row,
		// headerTable, false);
		return view;
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		if (savedInstanceState != null) {

			ZEORDER[] ordersArray;
			ordersArray = (ZEORDER[]) savedInstanceState
					.getSerializable("orders");
			orders = new ArrayList<ZEORDER>(Arrays.asList(ordersArray));
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
		ZEORDER[] ordersArray = new ZEORDER[orders.size()];
		orders.toArray(ordersArray);

		outState.putSerializable("orders", ordersArray);

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
		// final String dl = dateLow.replace("/", ".");
		// final String dh = dateHigh.replace("/", ".");

		AsyncTask<String, Void, List<ZEORDER>> task = new AsyncTask<String, Void, List<ZEORDER>>() {

			@Override
			protected List<ZEORDER> doInBackground(String... params) {
				try {
					Looper.prepare();
				} catch (Exception e) {
					e.printStackTrace();
				}
				try {

					Connection connection = new Connection(getActivity());
					orders = connection.getPlan(loginPreferences.getString(
							CurrentConfig.USERNAME, null));
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
			protected void onPostExecute(List<ZEORDER> result) {
				fillContentTable();
			}
		};

		task.execute();
	}

	private synchronized void fillContentTable() {

		contentTable.removeAllViews();

		LayoutInflater inflater = LayoutInflater.from(getActivity());

		headerRow = (TableRow) inflater.inflate(R.layout.my_job_table_row,
				contentTable, false);
		aufnrHdr = (TextView) headerRow.findViewById(R.id.aufnrVplan);
		aufnrHdr.setText(getResources().getString(R.string.aufnr));

		aufartHdr = (TextView) headerRow.findViewById(R.id.aufart);
		aufartHdr.setText(getResources().getString(R.string.aufart));

		auftextHdr = (TextView) headerRow.findViewById(R.id.auftext);
		auftextHdr.setText(getResources().getString(R.string.auftext));

		co_gstrpHdr = (TextView) headerRow.findViewById(R.id.co_gstrp);
		co_gstrpHdr.setText(getResources().getString(R.string.co_gstrp));

		nameHdr = (TextView) headerRow.findViewById(R.id.name);
		nameHdr.setText(getResources().getString(R.string.name));

		addressHdr = (TextView) headerRow.findViewById(R.id.address);
		addressHdr.setText(getResources().getString(R.string.address));

		headerRow.setBackgroundColor(Color.GRAY);
		contentTable.addView(headerRow);

		RowClickListener rcl = new RowClickListener();

		boolean flag = false;
		for (ZEORDER item : orders) {

			// for (int i = 0; i < 30; i++) {
			contentRow = (TableRow) inflater.inflate(R.layout.my_job_table_row,
					contentTable, false);

			aufnr = (TextView) contentRow.findViewById(R.id.aufnrVplan);
			aufnr.setText(item.getAUFNR());

			aufart = (TextView) contentRow.findViewById(R.id.aufart);
			aufart.setText(item.getAUFART());

			auftext = (TextView) contentRow.findViewById(R.id.auftext);
			auftext.setText(item.getAUFTEXT());

			co_gstrp = (TextView) contentRow.findViewById(R.id.co_gstrp);
			co_gstrp.setText(item.getCO_GSTRP());

			name = (TextView) contentRow.findViewById(R.id.name);
			name.setText(item.getPARTNER());

			address = (TextView) contentRow.findViewById(R.id.address);
			address.setText(item.getADDRESS());

			contentRow.setOnClickListener(rcl);

			if (flag) {
				contentRow.setBackgroundResource(R.color.blue_sf);
			} else {
				contentRow.setBackgroundResource(R.color.light_gray);
			}

			flag = !flag;
			contentTable.addView(contentRow);

			// }
		}
		contentTable.setStretchAllColumns(true);
		contentTable.setShrinkAllColumns(true);
	}

	private class RowClickListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			TextView aufnr = (TextView) v.findViewById(R.id.aufnrVplan);

			TextView name = (TextView) v.findViewById(R.id.name);
			Toast.makeText(v.getContext(), aufnr.getText().toString(),
					Toast.LENGTH_SHORT).show();
			navigation.onShowOrderDetail(aufnr.getText().toString(), name
					.getText().toString());
		}
	}
}
