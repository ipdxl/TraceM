/**
 * 
 */
package com.sf.tracem.visit;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.ksoap2.transport.HttpResponseException;
import org.xmlpull.v1.XmlPullParserException;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
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
import android.widget.CheckedTextView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.sf.tracem.R;
import com.sf.tracem.connection.Connection;
import com.sf.tracem.connection.Visit;
import com.sf.tracem.connection.VisitLog;
import com.sf.tracem.db.DBManager;
import com.sf.tracem.login.PreferenceKeys;
import com.sf.tracem.plan.MyJobNavigation;

/**
 * @author José Guadalupe Mandujano Serrano
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
	private ListView visitLogList;
	private VisitLogAdapter logAdapter;
	private List<VisitLog> logList = new ArrayList<VisitLog>();;

	@Override
	public void onAttach(Activity activity) {
		navigation = (MyJobNavigation) activity;
		loginPreferences = PreferenceManager
				.getDefaultSharedPreferences(activity);
		super.onAttach(activity);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setHasOptionsMenu(true);

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.visit_list, container, false);

		list = (ListView) view.findViewById(R.id.visit_list);
		list.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
		list.setOnItemClickListener(new OnVisitClickListener());

		visitLogList = (ListView) view.findViewById(R.id.visit_log_list);

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
		logAdapter = new VisitLogAdapter(getActivity(), logList);
		visitLogList.setAdapter(logAdapter);
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
		if (activeIDProgram != null) {
			showCreateVisitDialog();
		} else {
			showNonActiveScheduleAlert();
		}

	}

	@SuppressLint("InflateParams")
	private void showNonActiveScheduleAlert() {

		View view = getActivity().getLayoutInflater().inflate(
				android.R.layout.simple_list_item_2, null, false);
		TextView text1 = (TextView) view.findViewById(android.R.id.text1);
		text1.setText(R.string.non_active_schedule);

		new AlertDialog.Builder(getActivity())
				.setTitle(android.R.string.dialog_alert_title)
				.setIcon(android.R.drawable.ic_dialog_alert)
				.setPositiveButton(android.R.string.ok, null).setView(view)
				.create().show();

	}

	private void showCreateVisitDialog() {

		visit = new Visit();
		visit.setID_PROGRAM(activeIDProgram);

		final VisitDialogView vdv = new VisitDialogView(getActivity());
		View view = vdv.getView();

		android.content.DialogInterface.OnClickListener createVisitConfirmation = new android.content.DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {

				visit.setFINI(vdv.getFini().getText().toString());
				visit.setHINI(vdv.getHini().getText().toString());
				visit.setTINI((byte) (vdv.getTini().isChecked() ? 1 : 0));
				visit.setUSER(loginPreferences.getString(
						PreferenceKeys.USERNAME, null));

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
											+ " " + visit.getID_VISIT(),
									Toast.LENGTH_LONG).show();

							getVisitsList();

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

		private CheckedTextView status;

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			status = (CheckedTextView) view.findViewById(R.id.status);
			if (status.isChecked()) {
				navigation.onVisitDetail();
			} else {
				DBManager dbManager = new DBManager(getActivity());
				logList.clear();
				logList.addAll(dbManager.getVisitLog(visistList.get(position)));
				logAdapter.notifyDataSetChanged();
			}
		}
	}
}