/**
 * 
 */
package com.sf.tracem.schedule;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.ksoap2.transport.HttpResponseException;
import org.xmlpull.v1.XmlPullParserException;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Looper;
import android.support.v4.app.Fragment;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.sf.tracem.R;
import com.sf.tracem.connection.Connection;
import com.sf.tracem.connection.Message;
import com.sf.tracem.connection.Order;
import com.sf.tracem.connection.Schedule;
import com.sf.tracem.db.DBManager;
import com.sf.tracem.login.CurrentConfig;

/**
 * @author José Guadalupe Mandujano Serrano
 * 
 */
public class ScheduleDetailFragment extends Fragment {

	private static final String PLANS = "PLANS";

	private int processingType;
	/**
	 * Processing type insert
	 * 
	 * @see #PROCESSING_TYPE
	 * @see #UPDATE
	 */
	public static final int INSERT = 0;
	/**
	 * Processing type update
	 * 
	 * @see #PROCESSING_TYPE
	 * @see #INSERT
	 */
	public static final int UPDATE = 1;
	/**
	 * Processing type argument
	 * 
	 * @see #INSERT
	 * @see #UPDATE
	 */
	public static final String PROCESSING_TYPE = "PROCESSING_TYPE";
	/**
	 * ID Program argument. Use it with {@link #PROCESSING_TYPE} =
	 * {@link #UPDATE}
	 */
	public static final String ID_PROGRAM = "ID_PROGRAM";
	/**
	 * Orders argument. Use it with {@link #PROCESSING_TYPE} = {@link #UPDATE}
	 */
	public static final String ORDERS = "ORDERS";

	public final static String SCHEDULE_DETAIL = "SCHEDULE_DETAIL";

	private ListView ordersList;
	private ListView scheduleList;
	private Spinner yearSpinner;
	private Spinner weekSpinner;
	private ImageButton toSchedulenButton;
	private OrderListAdapter ordersAdapter;
	private OrderListAdapter scheduleAdapter;
	private ImageButton toOrdersButton;
	private List<Order> orders = new ArrayList<Order>();
	private List<Order> schedule = new ArrayList<Order>();
	private SharedPreferences loginPreferences;
	private int year;
	private int week;

	private MenuItem deletemenu;

	protected List<Order> oldSchedule;

	private String id;

	protected Schedule newSchedule;

	private float planHours;
	private TextView planHoursText;
	private float scheduleHours;
	private TextView scheduleHoursText;

	@Override
	public void onAttach(Activity activity) {
		loginPreferences = activity.getSharedPreferences(
				CurrentConfig.LOGIN_PREFERENCES, Context.MODE_PRIVATE);
		super.onAttach(activity);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
		if (savedInstanceState != null) {
			orders = (ArrayList<Order>) savedInstanceState
					.getSerializable(ORDERS);
			schedule = (ArrayList<Order>) savedInstanceState
					.getSerializable(PLANS);
		} else {
		}
	}

	/**
	 * Initialize {@link #processingType} from {@link #getArguments()}
	 * 
	 * @param arguments
	 *            Arguments
	 * @see #PROCESSING_TYPE
	 * @see #INSERT
	 * @see #UPDATE
	 * @see #ORDERS
	 * @see #ID_PROGRAM
	 */
	private void initProcessingType(Bundle arguments) {
		if (arguments != null) {
			processingType = arguments.getInt(PROCESSING_TYPE, INSERT);

			if (processingType == UPDATE) {
				id = arguments.getString(ID_PROGRAM);
				year = Integer.parseInt(id.substring(0, 4));
				week = Integer.parseInt(id.substring(4));
			}
		}
		getOrdersFromDB();

	}

	/**
	 * get next available schedule for creating
	 * 
	 * @throws XmlPullParserException
	 * @throws IOException
	 * @throws HttpResponseException
	 */
	private void getNextSchedule() throws HttpResponseException, IOException,
			XmlPullParserException {
		Connection connection = new Connection(getActivity());

		id = connection.getNetxSchedule(loginPreferences.getString(
				CurrentConfig.USERNAME, null));

		if (id != null) {
			year = Integer.parseInt(id.substring(0, 4));
			week = Integer.parseInt(id.substring(4));
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.create_schedule,
				container, false);

		ordersList = (ListView) view.findViewById(R.id.orderList);
		ordersList.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

		planHoursText = (TextView) view.findViewById(R.id.hours_plan);
		scheduleHoursText = (TextView) view.findViewById(R.id.hours_schedule);

		// ordersList.setOnItemLongClickListener(new OnItemLongClickListener() {
		//
		// @Override
		// public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
		// int position, long arg3) {
		// Order value = orders.get(position);
		// orders.remove(position);
		// schedule.add(value);
		// scheduleAdapter.notifyDataSetChanged();
		// ordersAdapter.notifyDataSetChanged();
		// return false;
		// }
		// });

		ordersAdapter = new OrderListAdapter(getActivity(),
				android.R.layout.simple_list_item_1, android.R.id.text1, orders);

		ordersList.setAdapter(ordersAdapter);
		ordersList.setSelector(R.drawable.list_view_keep_selection);

		toSchedulenButton = (ImageButton) view.findViewById(R.id.toPlanButton);
		toSchedulenButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View view) {

				// for (int i = 0; i < planAdapter.getCount(); i++) {

				SparseBooleanArray checkeditems = ordersList
						.getCheckedItemPositions();
				List<Order> temp = new ArrayList<Order>();
				for (int i = 0; i < checkeditems.size(); i++) {
					int index = checkeditems.keyAt(i);

					if (checkeditems.get(index)) {
						ordersList.setItemChecked(index, false);
						Order selectedItem = ordersAdapter.getItem(index);
						temp.add(selectedItem);
					}

				}

				for (Order order : temp) {
					ordersAdapter.remove(order);
					scheduleAdapter.add(order);

					scheduleHours = addHours(order, scheduleHours,
							scheduleHoursText);
					planHours = substractHours(order, planHours, planHoursText);
				}

				ordersAdapter.notifyDataSetChanged();
				scheduleAdapter.notifyDataSetChanged();
			}
		});

		toOrdersButton = (ImageButton) view.findViewById(R.id.toOrdersButton);
		toOrdersButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				// for (int i = 0; i < planAdapter.getCount(); i++) {

				SparseBooleanArray checkeditems = scheduleList
						.getCheckedItemPositions();

				List<Order> temp = new ArrayList<Order>();

				for (int i = 0; i < checkeditems.size(); i++) {
					int index = checkeditems.keyAt(i);

					if (checkeditems.get(index)) {
						scheduleList.setItemChecked(index, false);
						Order selectedItem = scheduleAdapter.getItem(index);
						temp.add(selectedItem);
					}
				}

				for (Order order : temp) {
					scheduleAdapter.remove(order);
					ordersAdapter.add(order);

					planHours = addHours(order, planHours, planHoursText);
					scheduleHours = substractHours(order, scheduleHours,
							scheduleHoursText);
				}

				ordersAdapter.notifyDataSetChanged();
				scheduleAdapter.notifyDataSetChanged();
			}
		});

		scheduleList = (ListView) view.findViewById(R.id.planList);
		scheduleList.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

		scheduleList.setSelector(R.drawable.list_view_keep_selection);

		// scheduleList.setOnItemLongClickListener(new OnItemLongClickListener()
		// {
		//
		// @Override
		// public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
		// int position, long arg3) {
		// Order value = schedule.get(position);
		// schedule.remove(position);
		// orders.add(value);
		// scheduleAdapter.notifyDataSetChanged();
		// ordersAdapter.notifyDataSetChanged();
		// scheduleAdapter.notifyDataSetChanged();
		// ordersAdapter.notifyDataSetChanged();
		// return false;
		// }
		// });
		yearSpinner = (Spinner) view.findViewById(R.id.yearSpinner);
		weekSpinner = (Spinner) view.findViewById(R.id.weekNumberSpinner);
		return view;
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		initProcessingType(getArguments());
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		outState.putSerializable(ORDERS, orders.toArray());
		outState.putSerializable(PLANS, (ArrayList<Order>) schedule);
		super.onSaveInstanceState(outState);
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		// getActivity().getActionBar().setNavigationMode(
		// ActionBar.DISPLAY_SHOW_HOME);
		// getActivity().getActionBar().setNavigationMode(
		// ActionBar.DISPLAY_HOME_AS_UP);
		getActivity().getActionBar().setDisplayShowCustomEnabled(false);
		getActivity().getActionBar().setHomeButtonEnabled(false);
		getActivity().getActionBar().setDisplayHomeAsUpEnabled(false);
		inflater.inflate(R.menu.schedule_detail_menu, menu);

		deletemenu = menu.findItem(R.id.delete_plan);
		if (processingType == INSERT)
			deletemenu.setVisible(false);

		super.onCreateOptionsMenu(menu, inflater);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.savePlan:
			saveSchedule();
			break;
		case R.id.delete_plan:
			deleteSchedule();
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	private void deleteSchedule() {
		new AsyncTask<String, Integer, List<Message>>() {

			@Override
			protected List<Message> doInBackground(String... params) {
				try {
					Looper.prepare();
				} catch (Exception e) {
					e.printStackTrace();
				}

				Connection connection = new Connection(getActivity());
				List<Message> message = null;
				try {
					message = connection.deleteSchedule(loginPreferences
							.getString(CurrentConfig.USERNAME, null), year,
							week);
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

				return message;
			}

			@Override
			protected void onPostExecute(List<Message> result) {
				if (result.size() > 0 && result.get(0).getType() == 'S') {
					Toast.makeText(
							getActivity(),
							getResources().getString(
									R.string.delete_schedule_success),
							Toast.LENGTH_LONG).show();
					lockActionBar();
				} else {
					Toast.makeText(
							getActivity(),
							getResources().getString(
									R.string.delete_schedule_error),
							Toast.LENGTH_LONG).show();
				}
			}

		}.execute();
	}

	private void lockActionBar() {
		setMenuVisibility(false);
	}

	private void saveSchedule() {

		AsyncTask<String, Integer, List<Message>> modifySchedule = new AsyncTask<String, Integer, List<Message>>() {

			@Override
			protected List<Message> doInBackground(String... params) {
				try {
					Looper.prepare();
				} catch (Exception e) {
					e.printStackTrace();
				}

				List<Message> messages = null;
				try {
					Connection connection = new Connection(getActivity());

					switch (processingType) {
					case INSERT:

						newSchedule = connection.createSchedule(
								loginPreferences.getString(
										CurrentConfig.USERNAME, null), year,
								week, schedule);
						break;
					case UPDATE:
						messages = connection.updateSchedule(loginPreferences
								.getString(CurrentConfig.USERNAME, null), year,
								week, schedule);
						break;
					default:
					}

				} catch (Exception e) {
					e.printStackTrace();
					messages = new ArrayList<Message>();
					newSchedule = null;
				}
				return messages;
			}

			@Override
			protected void onPostExecute(List<Message> result) {
				String messageText = null;

				if (processingType == INSERT) {
					messageText = getResources().getString(
							R.string.creation_program_error);
				} else {
					messageText = getResources().getString(
							R.string.update_program_error);
				}

				switch (processingType) {
				case INSERT:
					if (newSchedule != null) {
						messageText = getResources().getString(
								R.string.create_program_success);
						lockActionBar();
						DBManager dbManager = new DBManager(getActivity());
						dbManager.insertSchedule(newSchedule);
						dbManager.insertScheduleDetail(id, schedule);
					}
					break;
				case UPDATE:
					if (result.size() > 0 && result.get(0).getType() == 'S') {
						messageText = getResources().getString(
								R.string.update_program_success);
						lockActionBar();
					}
				default:
				}

				Toast.makeText(getActivity(), messageText, Toast.LENGTH_LONG)
						.show();

			}
		};

		if (schedule.size() > 0 && year != 0) {
			modifySchedule.execute();
		} else {
			Toast.makeText(getActivity(),
					getResources().getString(R.string.creation_program_error),
					Toast.LENGTH_LONG).show();
		}

	}

	private void getOrdersFromDB() {
		AsyncTask<String, Integer, List<Order>> scheduleDetailTask = new AsyncTask<String, Integer, List<Order>>() {

			@Override
			protected List<Order> doInBackground(String... params) {
				try {
					Looper.prepare();
				} catch (Exception e) {
					e.printStackTrace();
				}
				try {
					DBManager dbManager = new DBManager(getActivity());
					switch (processingType) {
					case INSERT:
						orders = dbManager.getUnassignedOrders();
						getNextSchedule();
						break;
					case UPDATE:
						orders = dbManager.getUnassignedOrders();
						oldSchedule = dbManager.getScheduleDetail(year, week);
						schedule = new ArrayList<Order>();
						schedule.addAll(oldSchedule);
						break;
					default:
						break;
					}
				} catch (Exception e) {
					e.printStackTrace();
					orders = new ArrayList<Order>();
				}
				return orders;
			}

			@Override
			protected void onPostExecute(List<Order> result) {
				PopulateView();
			};

		};

		scheduleDetailTask.execute();

	}

	private void PopulateView() {

		scheduleAdapter = new OrderListAdapter(getActivity(),
				android.R.layout.simple_list_item_1, android.R.id.text1,
				schedule);

		scheduleList.setAdapter(scheduleAdapter);

		ordersAdapter.addAll(orders);
		// scheduleAdapter.addAll(schedule);

		ordersAdapter.notifyDataSetChanged();
		scheduleAdapter.notifyDataSetChanged();

		yearSpinner.setAdapter(new ArrayAdapter<String>(getActivity(),
				android.R.layout.simple_spinner_item, android.R.id.text1,
				new String[] { "" + year }));

		weekSpinner.setAdapter(new ArrayAdapter<String>(getActivity(),
				android.R.layout.simple_spinner_item, android.R.id.text1,
				new String[] { "" + week }));

		for (Order order : schedule) {
			scheduleHours = addHours(order, scheduleHours, scheduleHoursText);
		}

		for (Order order : orders) {
			planHours = addHours(order, planHours, planHoursText);
		}

	}

	private float addHours(Order order, float initialHours, TextView textView) {
		float hour = order.getZHOURS();
		initialHours += hour;
		textView.setText(String.format(Locale.US, "%.1f", initialHours));
		return initialHours;
	}

	private float substractHours(Order order, float initialHours,
			TextView textView) {
		float hour = order.getZHOURS();
		initialHours -= hour;
		textView.setText(String.format(Locale.US, "%.1f", initialHours));
		return initialHours;
	}
	// @Override
	// public void onDetach() {
	// getActivity().getSupportFragmentManager().popBackStack(SCHEDULE_DETAIL,
	// FragmentManager.POP_BACK_STACK_INCLUSIVE);
	// }

}
