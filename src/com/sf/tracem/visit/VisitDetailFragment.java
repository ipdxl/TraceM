/**
 * 
 */
package com.sf.tracem.visit;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.ksoap2.transport.HttpResponseException;
import org.xmlpull.v1.XmlPullParserException;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Looper;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.sf.tracem.R;
import com.sf.tracem.connection.Connection;
import com.sf.tracem.connection.Order;
import com.sf.tracem.connection.Visit;
import com.sf.tracem.db.DBManager;
import com.sf.tracem.login.LoginSharedPreferences;
import com.sf.tracem.plan.MyJobNavigation;

/**
 * @author USER-7
 * 
 */
public class VisitDetailFragment extends Fragment {

	public static final String TAG = "VISIT_DETAIL_FRAGMENT";

	DBManager dbManager;

	private MyJobNavigation navigation;
	private Visit visit;
	private List<Order> orders;

	private FragmentManager fm;

	protected SharedPreferences loginPreferences;

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
		visit.setID_PROGRAM(visit.getID_PROGRAM());
		final VisitDialogView vdv = new VisitDialogView(getActivity());
		View view = vdv.getView();

		android.content.DialogInterface.OnClickListener closeVisitConfirmation = new android.content.DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {

				visit.setFFIN(vdv.getFini().getText().toString());
				visit.setHFIN(vdv.getHini().getText().toString());
				visit.setTFIN((byte) (vdv.getTini().isChecked() ? 1 : 0));
				visit.setUSER(loginPreferences.getString(
						LoginSharedPreferences.USERNAME, null));

				AsyncTask<String, Integer, Boolean> createVisitTask = new AsyncTask<String, Integer, Boolean>() {

					@Override
					protected Boolean doInBackground(String... params) {
						try {
							Looper.prepare();
						} catch (Exception e) {

						}
						Connection connection = new Connection(getActivity());
						boolean closed = false;
						try {
							closed = connection.closeVisit(visit);
						} catch (HttpResponseException e) {
							e.printStackTrace();
						} catch (IOException e) {
							e.printStackTrace();
						} catch (XmlPullParserException e) {
							e.printStackTrace();
						}
						return closed;
					}

					@Override
					protected void onPostExecute(Boolean result) {
						if (result) {
							Toast.makeText(
									getActivity(),
									getResources().getString(
											R.string.visit_closed)
											+ visit.getID_VISIT(),
									Toast.LENGTH_LONG).show();

							getActivity().dispatchKeyEvent(
									new KeyEvent(KeyEvent.ACTION_DOWN,
											KeyEvent.KEYCODE_BACK));
						} else {

							Toast.makeText(
									getActivity(),
									getResources().getString(
											R.string.visit_closed_error),
									Toast.LENGTH_LONG).show();
						}
					}
				};

				createVisitTask.execute();
			}
		};

		AlertDialog cvd = new AlertDialog.Builder(getActivity()).setView(view)
				.setTitle(R.string.close_visit)
				.setIcon(android.R.drawable.ic_dialog_alert)
				.setPositiveButton(android.R.string.ok, closeVisitConfirmation)
				.setNegativeButton(android.R.string.cancel, null).create();
		cvd.show();

	}

}
