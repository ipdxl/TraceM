package com.sf.tracem.visit;

import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.DatePicker;
import android.widget.TimePicker;

import com.sf.tracem.R;
import com.sf.tracem.connection.TraceMFormater;

public class VisitDialogView {

	private Button fini;
	private Button hini;
	private CheckBox tini;
	private Context context;

	public VisitDialogView(Context context) {
		setContext(context);
	}

	public Button getFini() {
		return fini;
	}

	public Button getHini() {
		return hini;
	}

	public CheckBox getTini() {
		return tini;
	}

	public View getView() {
		View view = View.inflate(getContext(), R.layout.create_visit, null);

		fini = (Button) view.findViewById(R.id.fini);

		fini.setText(TraceMFormater.getDate());

		final OnDateSetListener finiDateSet = new OnDateSetListener() {

			@Override
			public void onDateSet(DatePicker view, int year, int monthOfYear,
					int dayOfMonth) {
				String sDate = TraceMFormater.getDate(year, monthOfYear,
						dayOfMonth);
				fini.setText(sDate);
			}
		};

		fini.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				String date = fini.getText().toString();
				DatePickerDialog dpd = new DatePickerDialog(getContext(),
						finiDateSet, TraceMFormater.getYear(date),
						TraceMFormater.getMonth(date), TraceMFormater
								.getDay(date));
				dpd.show();
			}
		});

		hini = (Button) view.findViewById(R.id.hini);

		hini.setText(TraceMFormater.getTime());

		final OnTimeSetListener onTimeSetListener = new OnTimeSetListener() {

			@Override
			public void onTimeSet(TimePicker view, int hour, int minute) {
				hini.setText(TraceMFormater.getTime(hour, minute, 0));
			}
		};

		hini.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				String time = hini.getText().toString();
				TimePickerDialog tpd = new TimePickerDialog(getContext(),
						onTimeSetListener, TraceMFormater.getHour(time),
						TraceMFormater.getMinute(time), true);
				tpd.show();

			}
		});

		tini = (CheckBox) view.findViewById(R.id.tini);
		tini.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton button, boolean active) {
				fini.setEnabled(!active);
				hini.setEnabled(!active);
				if (active) {
					fini.setText(TraceMFormater.getDate());
					hini.setText(TraceMFormater.getTime());
				}
			}
		});

		return view;
	}

	/**
	 * @return the context
	 */
	public Context getContext() {
		return context;
	}

	/**
	 * @param context
	 *            the context to set
	 */
	public void setContext(Context context) {
		this.context = context;
	}
}
