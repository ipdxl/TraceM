/**
 * 
 */
package com.sf.tracem.plan.detail;

import java.util.concurrent.ExecutionException;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Looper;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sf.tracem.R;
import com.sf.tracem.connection.Connection;
import com.sf.tracem.connection.ZEOBJECT_ORDER;
import com.sf.tracem.connection.ZEOPERATION_ORDER;
import com.sf.tracem.connection.ZOrderDetails;

/**
 * @author Jos� Guadalupe Mandujano Serrano
 * 
 */
public class OrderDetail extends Fragment {

	private static final String EQUIPMENT_MENU = "EQUIPMENT_MENU";
	private static final String ORDER_DETAILS = "ORDER_DETAILS";
	private static final String OPERATIONS_FRAGMENT = "OPERATIONS_FRAGMENT";
	public static final String TAG = "ORDER_DETAIL";
	private String aufnr;
	private ZOrderDetails orderDetails;
	private EquipmentMenu em;
	private OperationsFragment of;
	private FragmentManager fm;
	private FragmentTransaction ft;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		setRetainInstance(true);
		onRestoreSavedInstanceState(savedInstanceState);
		setHasOptionsMenu(true);
		fm = getFragmentManager();
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		onRestoreSavedInstanceState(savedInstanceState);

		while (orderDetails == null)
			;

		View view = inflater.inflate(R.layout.order_detail_lauyout, container,
				false);

		// of = (OperationsFragment) fm.findFragmentByTag(OPERATIONS_FRAGMENT);
		// em = (EquipmentMenu) fm.findFragmentByTag(EQUIPMENT_MENU);
		//
		// if (em == null) {
		em = new EquipmentMenu();
		Bundle eqArgs = new Bundle();
		ZEOBJECT_ORDER[] equipmentsArray = new ZEOBJECT_ORDER[orderDetails
				.getEquipments().size()];
		orderDetails.getEquipments().toArray(equipmentsArray);
		eqArgs.putSerializable("equipments", equipmentsArray);
		em.setArguments(eqArgs);
		// } else {
		// em.getArguments().putString("aufnr",
		// getArguments().getString("aufnr"));
		// }

		// if (of == null) {
		of = new OperationsFragment();
		Bundle ofArgs = new Bundle();

		ZEOPERATION_ORDER[] operationsArray = new ZEOPERATION_ORDER[orderDetails
				.getOperations().size()];
		orderDetails.getOperations().toArray(operationsArray);
		ofArgs.putSerializable("operations", operationsArray);

		of.setArguments(ofArgs);
		// } else {
		// of.getArguments().putSerializable("operations",
		// orderDetails.getOperations());
		// }

		ft = fm.beginTransaction();
		ft.replace(R.id.equipmentFrame, em, EQUIPMENT_MENU);
		// .addToBackStack(EQUIPMENT_MENU);
		ft.replace(R.id.detailsFrame, of, OPERATIONS_FRAGMENT);
		// .addToBackStack(OPERATIONS_FRAGMENT);
		ft.commit();
		return view;
	}

	private void onRestoreSavedInstanceState(Bundle savedInstanceState) {
		if (savedInstanceState != null) {
			orderDetails = (ZOrderDetails) savedInstanceState
					.getSerializable(ORDER_DETAILS);
			aufnr = savedInstanceState.getString("aufnr");
		} else {
			FragmentManager fm = getFragmentManager();
			FragmentTransaction ft = fm.beginTransaction();
			Fragment f;
			if ((f = fm.findFragmentByTag(EQUIPMENT_MENU)) != null) {
				ft.remove(f);
				// fm.popBackStackImmediate(EQUIPMENT_MENU,
				// FragmentManager.POP_BACK_STACK_INCLUSIVE);
				f = null;
			}

			if ((f = fm.findFragmentByTag(OPERATIONS_FRAGMENT)) != null) {
				ft.remove(f);
				// fm.popBackStackImmediate(OPERATIONS_FRAGMENT,
				// FragmentManager.POP_BACK_STACK_INCLUSIVE);
			}
			ft.commit();

			aufnr = getArguments().getString("aufnr");
			if (orderDetails == null) {
				DummyTask task = new DummyTask();
				task.execute();
				try {
					orderDetails = task.get();
				} catch (InterruptedException e) {
					e.printStackTrace();
				} catch (ExecutionException e) {
					e.printStackTrace();
				}
			}
		}

	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		outState.putSerializable(ORDER_DETAILS, orderDetails);
		outState.putString("aufnr", aufnr);
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		getActivity().getActionBar().setDisplayShowCustomEnabled(false);
		getActivity().getActionBar().setDisplayHomeAsUpEnabled(true);
		getActivity().getActionBar().setHomeButtonEnabled(true);

		inflater.inflate(R.menu.order_details_menu, menu);

		MenuItem orderDescription = menu.findItem(R.id.orderDescription);
		View view = orderDescription.getActionView();

		TextView descriptionText = (TextView) view
				.findViewById(android.R.id.text1);
		descriptionText.setText(String.format("%s : %s", getArguments()
				.getString("aufnr"), getArguments().getString("name")));

		super.onCreateOptionsMenu(menu, inflater);
	}

	private class DummyTask extends AsyncTask<String, Integer, ZOrderDetails> {

		@Override
		protected ZOrderDetails doInBackground(String... params) {
			try {
				Looper.prepare();
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				Connection connection = new Connection(getActivity());
				orderDetails = connection.getOrderDetails(aufnr);
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				Looper.myLooper().quit();
			}
			return orderDetails;
		}

		@Override
		protected void onPostExecute(ZOrderDetails result) {
			if (result == null) {
				result = new ZOrderDetails();
			}
			super.onPostExecute(result);
		}
	}
}
