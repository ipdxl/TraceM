/**
 * 
 */
package com.sf.tracem.schedule;

import java.util.ArrayList;
import java.util.List;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.sf.tracem.R;
import com.sf.tracem.connection.Schedule;
import com.sf.tracem.db.DBManager;
import com.sf.tracem.plan.MyJobNavigation;

/**
 * @author José Guadalupe Mandujano Serrano
 * 
 */
public class SchedulesFragment extends Fragment {
	private static final String SCHEDULE = "SCHEDULE";
	public static final String TAG = "SCHEDULES_FRAGMENT";
	private ScheduleAdapter scheduleAdapter;
	private List<Schedule> schedules;

	private MyJobNavigation navigation;
	private ListView scheduleListView;

	@SuppressWarnings("unchecked")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
		navigation = (MyJobNavigation) getActivity();
		if (savedInstanceState != null) {
			schedules = (ArrayList<Schedule>) savedInstanceState
					.getSerializable(SCHEDULE);
		} else {

		}
	}

	private void getSchedules() {
		AsyncTask<String, Integer, List<Schedule>> scheduleTask = new AsyncTask<String, Integer, List<Schedule>>() {
			ProgressDialog progress;

			@Override
			protected void onPreExecute() {
				progress = new ProgressDialog(getActivity());
				progress.setMessage(getResources().getString(R.string.loading));
				progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
				progress.setIndeterminate(true);
				progress.show();
			}

			@Override
			protected List<Schedule> doInBackground(String... params) {
				try {
					Looper.prepare();
				} catch (Exception e) {
					e.printStackTrace();
				}

				try {
					DBManager dbManager = new DBManager(getActivity());
					schedules = dbManager.getSchedules();
				} catch (Exception e) {
					e.printStackTrace();
				}

				return schedules;
			}

			@Override
			protected void onPostExecute(List<Schedule> result) {
				scheduleAdapter = new ScheduleAdapter(getActivity(), schedules);
				scheduleListView.setAdapter(scheduleAdapter);
				progress.cancel();
			}
		};

		scheduleTask.execute();

	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		outState.putSerializable(SCHEDULE, (ArrayList<Schedule>) schedules);
		super.onSaveInstanceState(outState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(android.R.layout.list_content, container,
				false);

		scheduleListView = (ListView) view.findViewById(android.R.id.list);
		scheduleListView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
		scheduleListView
				.setOnItemClickListener(new ScheduleItemClickListener());

		return view;
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		getSchedules();
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		// getActivity().getActionBar().setDisplayOptions(
		// ActionBar.DISPLAY_SHOW_HOME | ActionBar.DISPLAY_HOME_AS_UP);
		// getActivity().getActionBar().setDisplayShowCustomEnabled(false);
		// getActivity().getActionBar().setHomeButtonEnabled(true);
		// getActivity().getActionBar().setDisplayHomeAsUpEnabled(true);
		menu.clear();
		inflater.inflate(R.menu.my_plan_menu, menu);
		super.onCreateOptionsMenu(menu, inflater);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == R.id.createProgram) {
			navigation.onCreateSchedule(null);
		}
		return super.onOptionsItemSelected(item);
	}

	class ScheduleItemClickListener implements OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			Schedule schedule = schedules.get(position);
			String idProgram = schedule.getID_PROGRAM();
			navigation.onCreateSchedule(idProgram);
		}

	}

	class ScheduleAdapter extends ArrayAdapter<Schedule> {
		private TextView text1;
		private TextView text2;

		public ScheduleAdapter(Context context, List<Schedule> objects) {
			super(context, android.R.layout.simple_list_item_activated_2,
					android.R.id.text1, objects);
		}

		// private RadioButton activateRadio;

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			Schedule item = getItem(position);

			View view = super.getView(position, convertView, parent);

			text1 = (TextView) view.findViewById(android.R.id.text1);
			text1.setText(item.getID_PROGRAM());

			text2 = (TextView) view.findViewById(android.R.id.text2);
			switch (item.getSTATUS()) {
			case 1:
				text2.setText(R.string.created);
				text2.setTextColor(getResources().getColor(
						android.R.color.holo_blue_dark));
				break;
			case 2:
				text2.setText(R.string.active);
				text2.setTextColor(getResources().getColor(
						android.R.color.holo_green_dark));
				break;
			case 3:
				text2.setText(R.string.closed);
				text2.setTextColor(getResources().getColor(
						android.R.color.holo_red_dark));
				break;
			}

			return view;
		}
	}
}
