/**
 * 
 */
package com.sf.tracem.plan.detail;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.ksoap2.transport.HttpResponseException;
import org.xmlpull.v1.XmlPullParserException;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.sf.tracem.R;
import com.sf.tracem.connection.Connection;
import com.sf.tracem.connection.Equipment;
import com.sf.tracem.connection.Message;
import com.sf.tracem.connection.Operation;
import com.sf.tracem.connection.OrderDetails;
import com.sf.tracem.db.DBManager;

/**
 * @author José Guadalupe Mandujano Serrano
 * 
 */
public class OrderDetailFragment extends Fragment {

	private static final String EQUIPMENT_MENU = "EQUIPMENT_MENU";
	private static final String ORDER_DETAILS = "ORDER_DETAILS";
	private static final String OPERATIONS_FRAGMENT = "OPERATIONS_FRAGMENT";
	public static final String TAG = "ORDER_DETAIL";
	public static final String AUFNR = "AUFNR";

	public static final String EDIT_MODE = "EDIT_MODE";
	public static final String VIEW_MODE = "VIEW_MODE";
	public static final String MODE = "MODE";
	private static final int PICK_IMAGE = 4;

	private String mode;

	private String aufnr;
	private OrderDetails orderDetails;
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

		mode = getArguments().getString(MODE, VIEW_MODE);

		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		onRestoreSavedInstanceState(savedInstanceState);

		View view = inflater.inflate(R.layout.order_detail, container, false);

		// of = (OperationsFragment) fm.findFragmentByTag(OPERATIONS_FRAGMENT);
		// em = (EquipmentMenu) fm.findFragmentByTag(EQUIPMENT_MENU);
		//
		// if (em == null) {
		em = new EquipmentMenu();
		Bundle eqArgs = new Bundle();
		List<Equipment> equipments = orderDetails.getEquipments();
		eqArgs.putSerializable(EquipmentMenu.EQUIPMENTS,
				(ArrayList<Equipment>) equipments);
		eqArgs.putString(OrderDetailFragment.MODE, mode);
		em.setArguments(eqArgs);
		// } else {
		// em.getArguments().putString(AUFNR,
		// getArguments().getString(AUFNR));
		// }

		// if (of == null) {
		of = new OperationsFragment();
		Bundle ofArgs = new Bundle();

		ofArgs.putSerializable(OperationsFragment.OPERATIONS,
				(ArrayList<Operation>) orderDetails.getOperations());
		ofArgs.putString(OrderDetailFragment.MODE, mode);

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
			orderDetails = (OrderDetails) savedInstanceState
					.getSerializable(ORDER_DETAILS);
			aufnr = savedInstanceState.getString(AUFNR);
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

			aufnr = getArguments().getString(AUFNR);
			if (orderDetails == null) {
				OrderDetailsTask task = new OrderDetailsTask();
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
		outState.putString(AUFNR, aufnr);
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		super.onCreateOptionsMenu(menu, inflater);
		// ActionBar actionBar = getActivity().getActionBar();
		// actionBar.setDisplayHomeAsUpEnabled(false);

		inflater.inflate(R.menu.order_details_menu, menu);

		// MenuItem orderDescription = menu.findItem(R.id.orderDescription);
		MenuItem savePicture = menu.findItem(R.id.save_picture);
		if (mode != EDIT_MODE) {
			savePicture.setVisible(false);
		}

		// View view = orderDescription.getActionView();
		//
		// TextView descriptionText = (TextView) view
		// .findViewById(android.R.id.text1);
		// descriptionText.setText(String.format("%s : %s", getArguments()
		// .getString(AUFNR), getArguments().getString(NAME)));

	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (!isVisible()) {
			return super.onOptionsItemSelected(item);
		}

		switch (item.getItemId()) {
		case R.id.components:
			showComponents();
			break;
		case android.R.id.home:
			// getActivity().onBackPressed();
			break;
		case R.id.save_picture:
			Intent pickImageintent = new Intent();
			pickImageintent.setType("image/*");
			pickImageintent.setAction(Intent.ACTION_GET_CONTENT);
			startActivityForResult(Intent.createChooser(pickImageintent,
					getResources().getString(R.string.select_picture)),
					PICK_IMAGE);
			// startActivityForResult(pickImageintent, PICK_IMAGE);
			break;

		default:
			break;
		}

		return super.onOptionsItemSelected(item);
	}

	@SuppressLint("InflateParams")
	private void showComponents() {

		LayoutInflater inflater = LayoutInflater.from(getActivity());

		View view = inflater
				.inflate(android.R.layout.list_content, null, false);
		ListView list = (ListView) view.findViewById(android.R.id.list);

		ComponentsAdapter ca = new ComponentsAdapter(getActivity(),
				orderDetails.getComponents());

		list = (ListView) view.findViewById(android.R.id.list);
		list.setAdapter(ca);

		new AlertDialog.Builder(getActivity())
				.setPositiveButton(android.R.string.ok, null)
				.setTitle(R.string.components)
				.setIcon(android.R.drawable.ic_dialog_info).setView(view)
				.create().show();
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if (requestCode == PICK_IMAGE && data != null && data.getData() != null) {

			Uri uri = data.getData();

			Cursor cursor = getActivity()
					.getContentResolver()
					.query(uri,
							new String[] { android.provider.MediaStore.Images.ImageColumns.DATA },
							null, null, null);

			if (cursor.moveToFirst()) {
				List<String> files = new ArrayList<String>();
				do {
					files.add(cursor.getString(0));
					String path = cursor.getString(0);
					savePicture(path);

				} while (cursor.moveToNext());
			}
		}

	}

	private void savePicture(String path) {
		AsyncTask<String, Integer, List<Message>> savePictureTask;
		savePictureTask = new AsyncTask<String, Integer, List<Message>>() {

			@Override
			protected List<Message> doInBackground(String... params) {
				List<Message> messages = null;

				try {
					Looper.prepare();
				} catch (Exception e) {
				}

				Connection conn = new Connection(getActivity());
				try {
					messages = conn.savePicture(params[0], 'X', getArguments()
							.getString(AUFNR));
				} catch (HttpResponseException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				} catch (XmlPullParserException e) {
					e.printStackTrace();
				}
				return messages;
			}

		};
		savePictureTask.execute(path);
	}

	private class OrderDetailsTask extends
			AsyncTask<String, Integer, OrderDetails> {

		@Override
		protected OrderDetails doInBackground(String... params) {
			try {
				Looper.prepare();
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				DBManager dbManager = new DBManager(getActivity());
				// Connection connection = new Connection(getActivity());
				// orderDetails = connection.getOrderDetails(aufnr);
				orderDetails = dbManager.getOrderDetails(aufnr);
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				Looper.myLooper().quit();
			}
			return orderDetails;
		}

		@Override
		protected void onPostExecute(OrderDetails result) {
			if (result == null) {
				result = new OrderDetails();
			}
			super.onPostExecute(result);
		}
	}
}
