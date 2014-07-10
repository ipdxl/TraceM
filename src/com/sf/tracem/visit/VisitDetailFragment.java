/**
 * 
 */
package com.sf.tracem.visit;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Looper;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.sf.tracem.R;
import com.sf.tracem.connection.Order;
import com.sf.tracem.connection.OrderDetails;
import com.sf.tracem.connection.Visit;
import com.sf.tracem.db.DBManager;
import com.sf.tracem.plan.MyJobNavigation;

/**
 * @author USER-7
 * 
 */
public class VisitDetailFragment extends Fragment {

	DBManager dbManager;

	private MyJobNavigation navigation;
	private Visit visit;
	private List<Order> orders;

	private FragmentManager fm;

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		navigation = (MyJobNavigation) activity;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
		setRetainInstance(true);

		fm = ((FragmentActivity) getActivity()).getSupportFragmentManager();

		dbManager = new DBManager(getActivity());
		visit = dbManager.getActiveVisit();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.visit, container, false);

		return view;
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		orders = getUnCompleteOrders();

		FragmentTransaction ft = fm.beginTransaction();

		Bundle args = new Bundle();
		args.putSerializable(VisitOrdersFragment.ORDERS,
				(ArrayList<Order>) orders);
		VisitOrdersFragment vof = new VisitOrdersFragment();
		vof.setArguments(args);

		ft.replace(R.id.orders_frame, vof);
		ft.commit();
	}

	private List<Order> getUnCompleteOrders() {
		AsyncTask<String, Integer, List<Order>> visitOrderTask = new AsyncTask<String, Integer, List<Order>>() {

			private ProgressDialog progress;

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
				List<Order> orders = null;
				try {
					Looper.prepare();
				} catch (Exception e) {
				}

				DBManager dbManager = new DBManager(getActivity());
				orders = dbManager.getUncompleteOrders();

				return orders;
			}

			@Override
			protected void onPostExecute(List<Order> result) {
				progress.hide();
			}

		};
		visitOrderTask.execute();

		List<Order> orders = null;
		try {
			orders = visitOrderTask.get();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return orders;
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		inflater.inflate(R.menu.visit_detail_menu, menu);
		super.onCreateOptionsMenu(menu, inflater);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.close_visit:
			closeVisit();
			break;
		}

		return super.onOptionsItemSelected(item);
	}

	private void closeVisit() {
		AsyncTask<String, Integer, Visit> closeVisitTask = new AsyncTask<String, Integer, Visit>() {

			private ProgressDialog progress;

			@Override
			protected void onPreExecute() {
				progress = new ProgressDialog(getActivity());
				progress.setMessage(getResources().getString(R.string.loading));
				progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
				progress.setIndeterminate(true);
				progress.show();
			}

			@Override
			protected Visit doInBackground(String... params) {
				
				return null;
			}

			@Override
			protected void onPostExecute(Visit result) {
				progress.hide();
			}

		};

		closeVisitTask.execute();
	}

}
