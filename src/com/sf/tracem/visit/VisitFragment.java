/**
 * 
 */
package com.sf.tracem.visit;

import java.io.IOException;
import java.util.List;

import org.ksoap2.transport.HttpResponseException;
import org.xmlpull.v1.XmlPullParserException;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.DataSetObserver;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Looper;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;

import com.sf.tracem.R;
import com.sf.tracem.connection.Connection;
import com.sf.tracem.connection.Visit;
import com.sf.tracem.db.DBManager;
import com.sf.tracem.login.CurrentConfig;
import com.sf.tracem.plan.MyJobNavigation;

/**
 * @author José Guadalupe Mandujano Serrano
 * 
 */
public class VisitFragment extends Fragment {

	public static final String TAG = "VISIT_FRAGMENT";
	private ListView list;
	private List<Visit> visistList;
	private SharedPreferences loginPreferences;
	private VisitListAdapter visitAdapter;
	protected String activeID;
	protected int year;
	protected int week;
	@SuppressWarnings("unused")
	private MyJobNavigation navigation;

	@Override
	public void onAttach(Activity activity) {
		navigation = (MyJobNavigation) activity;
		super.onAttach(activity);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setHasOptionsMenu(true);
		loginPreferences = getActivity().getSharedPreferences(
				CurrentConfig.LOGIN_PREFERENCES, Context.MODE_PRIVATE);

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = inflater.inflate(android.R.layout.list_content, container,
				false);

		list = (ListView) view.findViewById(android.R.id.list);

		// emptyText = (TextView) view.findViewById(android.R.id.empty);
		return view;

	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		getVisitsList();
	}

	private String getActiveSchedule() {

		String id = null;

		DBManager dbManager = new DBManager(getActivity());

		id = dbManager.getActiveSchedule();

		return id;
	}

	private void getVisitsList() {

		AsyncTask<String, Integer, List<Visit>> visitTask = new AsyncTask<String, Integer, List<Visit>>() {

			@Override
			protected void onPreExecute() {
				activeID = getActiveSchedule();
				if (activeID != null) {
					year = Integer.parseInt(activeID.substring(0, 4));
					week = Integer.parseInt(activeID.substring(4));
				} else {
					year = 0;
					week = 0;
				}
			}

			@Override
			protected List<Visit> doInBackground(String... params) {

				List<Visit> vl = null;
				try {
					Looper.prepare();
				} catch (Exception e) {
					e.printStackTrace();
				}

				Connection conn = new Connection(getActivity());
				try {
					vl = conn.getVisitList(loginPreferences.getString(
							CurrentConfig.USERNAME, null), year, week);
				} catch (HttpResponseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (XmlPullParserException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				return vl;
			}

			@Override
			protected void onPostExecute(List<Visit> result) {
				populateView(result);

			}
		};

		visitTask.execute();

	}

	protected void populateView(List<Visit> result) {
		visistList = result;

		// if (visistList.size() == 0) {
		// emptyText.setText("No data");
		// } else {
		visitAdapter = new VisitListAdapter(getActivity(), visistList);
		list.setAdapter(visitAdapter);
		// }
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		inflater.inflate(R.menu.visit_list_menu, menu);
		super.onCreateOptionsMenu(menu, inflater);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.create_visist:
			createVisit();
			// navigation.onCreateVisit();
			break;

		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	private void createVisit() {
		LayoutInflater inflater = LayoutInflater.from(getActivity());
		View view = inflater.inflate(R.layout.create_visit, null);

		Spinner fini = (Spinner) view.findViewById(R.id.fini);

		fini.setAdapter(new ArrayAdapter<String>(getActivity(),
				android.R.layout.simple_list_item_1, android.R.id.text1,
				new String[] { "2014-07-04" }));

		final OnDateSetListener finiDateSt = new OnDateSetListener() {

			@Override
			public void onDateSet(DatePicker view, int year, int monthOfYear,
					int dayOfMonth) {

			}
		};

		fini.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				DatePickerDialog dpd = new DatePickerDialog(getActivity(),
						finiDateSt, 2014, 7, 4);
				dpd.show();
				return true;
			}
		});
		//
		// @Override
		// public void onClick(View v) {
		// DatePickerDialog dpd = new DatePickerDialog(getActivity(),
		// null, 2014, 7, 4);
		// dpd.show();
		// }
		// });

		Spinner hini = (Spinner) view.findViewById(R.id.hini);

		hini.setAdapter(new ArrayAdapter<String>(getActivity(),
				android.R.layout.simple_list_item_1, android.R.id.text1,
				new String[] { "10:00:00 AM" }));

		AlertDialog cvd = new AlertDialog.Builder(getActivity()).setView(view)
				.setTitle(R.string.create_visit)
				.setIcon(android.R.drawable.ic_dialog_info)
				.setPositiveButton(android.R.string.ok, null)
				.setNegativeButton(android.R.string.cancel, null).create();
		cvd.show();

	}
}