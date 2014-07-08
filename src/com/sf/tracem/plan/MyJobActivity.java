/**
 *
 */
package com.sf.tracem.plan;

import java.util.Calendar;

import android.app.ActionBar;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.view.MenuItem;
import android.view.View;

import com.sf.tracem.R;
import com.sf.tracem.login.CurrentConfig;
import com.sf.tracem.path.MyPathFragment;
import com.sf.tracem.plan.detail.OrderDetailFragment;
import com.sf.tracem.plan.menu.MyJobMenuFragment;
import com.sf.tracem.schedule.MySchedulesFragment;
import com.sf.tracem.schedule.ScheduleDetailFragment;
import com.sf.tracem.visit.VisitListFragment;

/**
 * @author José Guadalupe Mandujano Serrano
 * 
 */
public class MyJobActivity extends FragmentActivity implements MyJobNavigation {

	private DrawerLayout mDrawerLayout;
	private ActionBarDrawerToggle mDrawerToggle;
	private MyJobMenuFragment mjmf;
	private MyPlanFragment mpf;
	private OrderDetailFragment od;
	private FragmentManager fm;
	private FragmentTransaction ft;
	private ActionBar actionBar;
	private SharedPreferences sharedPreferences;
	private String loginName;
	private SharedPreferences loginPreferences;

	// private String currentFragment;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		loginPreferences = getSharedPreferences(
				CurrentConfig.LOGIN_PREFERENCES, MODE_PRIVATE);

		fm = getSupportFragmentManager();
		ft = fm.beginTransaction();

		sharedPreferences = getSharedPreferences(
				CurrentConfig.LOGIN_PREFERENCES, MODE_PRIVATE);

		loginName = sharedPreferences.getString(CurrentConfig.USERNAME, null);

		if (loginName != null) {
			mjmf = new MyJobMenuFragment();
		} else {
			mjmf = (MyJobMenuFragment) fm
					.findFragmentByTag(MyJobMenuFragment.TAG);
		}
		if (mjmf == null) {
			mjmf = new MyJobMenuFragment();
		}

		ft.replace(R.id.menu_drawer, mjmf, MyJobMenuFragment.TAG);
		ft.commit();

		initDrawer();
	}

	private void initDrawer() {

		actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setHomeButtonEnabled(true);

		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mDrawerToggle = new ActionBarDrawerToggle(this, /*
														 * host Activity
														 */
		mDrawerLayout, /*
						 * DrawerLayout object
						 */
		R.drawable.ic_navigation_drawer, /*
										 * nav drawer icon to replace 'Up' caret
										 */
		R.string.drawer_open, /*
							 * "open drawer" description
							 */
		R.string.drawer_close /*
							 * "close drawer" description
							 */
		) {

			/**
			 * Called when a drawer has settled in a completely closed state.
			 */
			public void onDrawerClosed(View view) {
				super.onDrawerClosed(view);
				// getActionBar().setTitle(mTitle);
			}

			/**
			 * Called when a drawer has settled in a completely open state.
			 */
			public void onDrawerOpened(View drawerView) {
				super.onDrawerOpened(drawerView);
				// getActionBar().setTitle(mDrawerTitle);
			}
		};

		// Set the drawer toggle as the DrawerListener
		mDrawerLayout.setDrawerListener(mDrawerToggle);

	}

	//
	// @Override
	// public boolean onCreateOptionsMenu(Menu menu) {
	// // Inflate the menu items for use in the action bar
	// MenuInflater inflater = getMenuInflater();
	// inflater.inflate(R.menu.my_job_ab, menu);
	//
	// return super.onCreateOptionsMenu(menu);
	// }
	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		// Sync the toggle state after onRestoreInstanceState has occurred.
		mDrawerToggle.syncState();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		mDrawerToggle.onConfigurationChanged(newConfig);
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Pass the event to ActionBarDrawerToggle, if it returns
		// true, then it has handled the app icon touch event
		if (mDrawerToggle.onOptionsItemSelected(item)) {
			return true;
		}
		// Handle your other action bar items...

		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onShowOrderDetail(String orderNumber, String name) {
		// OrderDetail od = (OrderDetail) fm.findFragmentByTag(ORDER_DETAIL);
		// if (od != null) {
		// ft.remove(od);
		// fm.popBackStack(ORDER_DETAIL,
		// FragmentManager.POP_BACK_STACK_INCLUSIVE);
		// }
		// if (od == null) {
		od = new OrderDetailFragment();
		Bundle args = new Bundle();
		args.putString("aufnr", orderNumber);
		args.putString("name", name);
		od.setArguments(args);
		// } else {
		// od.getArguments().putString("aufnr", orderNumber);
		// }
		ft = fm.beginTransaction();
		ft.replace(R.id.content_frame, od, OrderDetailFragment.TAG).addToBackStack(
				OrderDetailFragment.TAG);
		ft.commit();
	}

	@Override
	public void onCreateSchedule(String id) {
		ft = fm.beginTransaction();
		ScheduleDetailFragment cpf = new ScheduleDetailFragment();

		if (id != null) {
			Bundle args = new Bundle();
			args.putInt(ScheduleDetailFragment.PROCESSING_TYPE,
					ScheduleDetailFragment.UPDATE);
			args.putString(ScheduleDetailFragment.ID_PROGRAM, id);
			cpf.setArguments(args);
		}

		ft.replace(R.id.content_frame, cpf,
				ScheduleDetailFragment.SCHEDULE_DETAIL)
				.addToBackStack(ScheduleDetailFragment.SCHEDULE_DETAIL)
				.commit();
	}

	@Override
	public void onViewPlans() {
		ft = fm.beginTransaction();
		MySchedulesFragment mpf = new MySchedulesFragment();
		ft.replace(R.id.content_frame, mpf).addToBackStack(
				MySchedulesFragment.TAG);

		Bundle args = new Bundle();
		args.putString("USER",
				loginPreferences.getString(CurrentConfig.USERNAME, null));
		mpf.setArguments(args);

		ft.commit();

		mDrawerLayout.closeDrawers();
	}

	@Override
	public void onViewMyJob() {

		mpf = (MyPlanFragment) fm.findFragmentByTag(MyPlanFragment.TAG);
		if (mpf == null) {
			mpf = new MyPlanFragment();
			Bundle args = new Bundle();
			Calendar calendar = Calendar.getInstance();
			String day = "" + calendar.get(Calendar.DAY_OF_MONTH);
			if (day.length() == 1) {
				day = "0" + day;
			}
			String month = "" + (calendar.get(Calendar.MONTH) + 1);
			if (month.length() == 1) {
				month = "0" + month;
			}

			String year = "" + calendar.get(Calendar.YEAR);
			args.putString("DATE_HIGH",
					String.format("%s/%s/%s", day, month, year));
			args.putString(
					"DATE_LOW",
					String.format("%s/%s/%s", day, month,
							calendar.get(Calendar.YEAR) - 7));

			mpf.setArguments(args);

		}

		ft = fm.beginTransaction();
		ft.replace(R.id.content_frame, mpf, MyPlanFragment.TAG);
		ft.commit();
		try {
			mDrawerLayout.closeDrawers();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onViewMyPath() {
		MyPathFragment mpf = new MyPathFragment();

		ft = fm.beginTransaction();
		ft.replace(R.id.content_frame, mpf, MyPathFragment.TAG);

		ft.commit();

		mDrawerLayout.closeDrawers();
	}

	@Override
	public void onViewMyVisit() {
		ft = fm.beginTransaction();
		VisitListFragment visitFragment = (VisitListFragment) fm
				.findFragmentByTag(VisitListFragment.TAG);

		if (visitFragment == null) {
			visitFragment = new VisitListFragment();
		}

		ft.replace(R.id.content_frame, visitFragment, VisitListFragment.TAG);
		ft.commit();

	}

	@Override
	public void onVisitDetail() {
		// TODO Auto-generated method stub

	}
}
