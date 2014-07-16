package com.sf.tracem.schedule;

import java.util.concurrent.ExecutionException;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.DialogInterface.OnClickListener;
import android.content.DialogInterface.OnDismissListener;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.sf.tracem.R;

public class WarningScheduleDialog {

	private FragmentActivity activity;
	private boolean result;

	public WarningScheduleDialog(FragmentActivity activity) {
		this.activity = activity;
	}

	@SuppressLint("InflateParams")
	public boolean show() throws InterruptedException, ExecutionException {

		activity.runOnUiThread(new Runnable() {

			private TextView text1;

			@Override
			public void run() {
				LayoutInflater inflater = LayoutInflater.from(activity);
				View view = inflater.inflate(
						android.R.layout.simple_list_item_1, null);

				text1 = (TextView) view.findViewById(android.R.id.text1);
				text1.setText(R.string.save_schedule_alert);

				android.content.DialogInterface.OnClickListener okListener = new OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						result = true;
						activity.notify();
					}
				};

				AlertDialog.Builder builder = new AlertDialog.Builder(activity)
						.setTitle(android.R.string.dialog_alert_title)
						.setView(view)
						.setPositiveButton(android.R.string.ok, okListener);

				Dialog dialog = builder.create();
				dialog.setOnCancelListener(new OnCancelListener() {

					@Override
					public void onCancel(DialogInterface dialog) {
						result = false;
						activity.notify();
					}
				});
				dialog.setOnDismissListener(new OnDismissListener() {

					@Override
					public void onDismiss(DialogInterface dialog) {
						result = false;
						activity.notify();
					}
				});
				dialog.show();
			}
		});

		activity.wait();

		return result;
	}
}
