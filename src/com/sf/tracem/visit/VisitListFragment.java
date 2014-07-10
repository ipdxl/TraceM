/**
 * 
 */
package com.sf.tracem.visit;

import java.io.IOException;
import java.util.Currency;
import java.util.List;

import org.ksoap2.transport.HttpResponseException;
import org.xmlpull.v1.XmlPullParserException;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import android.widget.RadioButton;
import android.widget.Toast;

import com.sf.tracem.R;
import com.sf.tracem.connection.Connection;
import com.sf.tracem.connection.Visit;
import com.sf.tracem.db.DBManager;
import com.sf.tracem.login.CurrentConfig;
import com.sf.tracem.plan.MyJobNavigation;

/**
 * @author Jos� Guadalupe Mandujano Serrano
 * 
 */
public class VisitListFragment extends Fragment {

	public static final String TAG = "VISIT_FRAGMENT";
	private ListView list;
	private List<Visit> visistList;
	private SharedPreferences loginPreferences;
	private VisitListAdapter visitAdapter;
	protected String activeIDProgram;
	protected int year;
	protected int week;
	private MyJobNavigation navigation;
	private Visit visit;

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

		list.setOnItemClickListener(new OnVisitClickListener());

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
				activeIDProgram = getActiveSchedule();
				if (activeIDProgram != null) {
					year = Integer.parseInt(activeIDProgram.substring(0, 4));
					week = Integer.parseInt(activeIDProgram.substring(4));
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

				DBManager dbManager = new DBManager(getActivity());
				vl = dbManager.getVisits();

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
		visitAdapter = new VisitListAdapter(getActivity(), visistList);
		list.setAdapter(visitAdapter);
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

	@SuppressLint("InflateParams")
	private void createVisit() {
		visit = new Visit();
		visit.setID_PROGRAM(activeIDProgram);

		final VisitDialogView view = new VisitDialogView(getActivity());

		android.content.DialogInterface.OnClickListener createVisitConfirmation = new android.content.DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {

				visit.setFINI(view.getFini().getText().toString());
				visit.setHINI(view.getHini().getText().toString());
				visit.setTINI((byte) (view.getTini().isChecked() ? 1 : 0));
				visit.setUSER(loginPreferences.getString(
						CurrentConfig.USERNAME, null));

				AsyncTask<String, Integer, Integer> createVisitTask = new AsyncTask<String, Integer, Integer>() {

					@Override
					protected Integer doInBackground(String... params) {
						try {
							Looper.prepare();
						} catch (Exception e) {

						}
						Connection connection = new Connection(getActivity());
						int visitID = 0;
						try {
							visitID = connection.createVisit(visit);
						} catch (HttpResponseException e) {
							e.printStackTrace();
						} catch (IOException e) {
							e.printStackTrace();
						} catch (XmlPullParserException e) {
							e.printStackTrace();
						}
						return visitID;
					}

					@Override
					protected void onPostExecute(Integer result) {
						if (result != 0) {
							visit.setID_VISIT(result);
							visit.setSTATUS((byte) 1);

							Toast.makeText(
									getActivity(),
									getResources().getString(
											R.string.visit_created)
											+ visit, Toast.LENGTH_LONG).show();
						} else {

							Toast.makeText(
									getActivity(),
									getResources().getString(
											R.string.create_visit_error),
									Toast.LENGTH_LONG).show();
						}
					}
				};

				createVisitTask.execute();
			}
		};

		AlertDialog cvd = new AlertDialog.Builder(getActivity())
				.setView(view)
				.setTitle(R.string.create_visit)
				.setIcon(android.R.drawable.ic_dialog_info)
				.setPositiveButton(android.R.string.ok, createVisitConfirmation)
				.setNegativeButton(android.R.string.cancel, null).create();
		cvd.show();

	}

	private class OnVisitClickListener implements OnItemClickListener {

		private RadioButton status;

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			status = (RadioButton) view.findViewById(R.id.status);
			if (status.isChecked()) {
				navigation.onVisitDetail();
			}
		}
	}
}