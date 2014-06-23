/**
 * 
 */
package com.sf.tracem.plan;

import android.app.ActionBar;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;

import com.sf.tracem.R;

/**
 * @author José Guadalupe Mandujano Serrano
 * 
 */
public class MyPlanCustomActionBar {

	// protected static final String DATE_PICKER_DIALOG = "DATE_PICKER_DIALOG";
	private ActionBar actionBar;
	// private MyJobNavigation navigation;
	private Activity activity;
	private Button dateLow;
	private Button dateHigh;
	private String dateLowText;
	private String DateHighText;

	public MyPlanCustomActionBar(ActionBar actionBar, Activity navigation) {
		this.actionBar = actionBar;
		// this.navigation = (MyJobNavigation) navigation;
		this.activity = navigation;
	}

	public void initBar(String dateL, String dateH) {
		dateLowText = dateL;
		DateHighText = dateH;

		actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM
				| ActionBar.DISPLAY_SHOW_HOME | ActionBar.DISPLAY_HOME_AS_UP);

		actionBar.setCustomView(R.layout.my_job_action_bar);

		View view = actionBar.getCustomView();

		dateLow = (Button) view.findViewById(R.id.dateLow);
		dateLow.setText(dateLowText);
		dateHigh = (Button) view.findViewById(R.id.dateHigh);
		dateHigh.setText(DateHighText);

		// ImageButton plan = (ImageButton) view.findViewById(R.id.createPlan);
		// plan.setOnClickListener(new OnClickListener() {
		//
		// @Override
		// public void onClick(View arg0) {
		// navigation.onCreatePlan();
		// }
		// });

		dateLow.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				String date = dateLow.getText().toString();

				int day = Integer.parseInt(date.substring(0, 2));
				int month = Integer.parseInt(date.substring(3, 5));
				int year = Integer.parseInt(date.substring(6));
				DatePickerDialog dpd = new DatePickerDialog(activity,
						new OnDateSet(dateLow), year, month, day);
				dpd.show();

			}
		});

		dateHigh.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				String date = dateHigh.getText().toString();

				int day = Integer.parseInt(date.substring(0, 2));
				int month = Integer.parseInt(date.substring(3, 5));
				int year = Integer.parseInt(date.substring(6));
				DatePickerDialog dpd = new DatePickerDialog(activity,
						new OnDateSet(dateHigh), year, month, day);
				dpd.show();

			}
		});
	}

	private class OnDateSet implements OnDateSetListener {

		private Button date;

		public OnDateSet(Button date) {
			this.date = date;
		}

		@Override
		public void onDateSet(DatePicker view, int year, int monthOfYear,
				int dayOfMonth) {
			String day = "" + dayOfMonth;
			if (day.length() == 1) {
				day = "0" + day;
			}

			String month = "" + monthOfYear;
			if (month.length() == 1) {
				month = "0" + month;
			}

			date.setText(String.format("%s/%s/%s", day, month, year));
		}
	}
}
