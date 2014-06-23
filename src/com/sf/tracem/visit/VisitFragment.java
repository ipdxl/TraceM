/**
 * 
 */
package com.sf.tracem.visit;

import java.io.IOException;
import java.util.List;

import org.ksoap2.transport.HttpResponseException;
import org.xmlpull.v1.XmlPullParserException;

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
import android.widget.ListView;

import com.sf.tracem.connection.Connection;
import com.sf.tracem.connection.Visit;
import com.sf.tracem.db.DBManager;
import com.sf.tracem.login.CurrentConfig;

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
		// TODO Auto-generated method stub
		super.onCreateOptionsMenu(menu, inflater);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		return super.onOptionsItemSelected(item);
	}

}
