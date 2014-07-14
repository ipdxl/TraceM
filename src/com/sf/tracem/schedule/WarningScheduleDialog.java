package com.sf.tracem.schedule;

import java.util.concurrent.ExecutionException;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.DialogInterface.OnClickListener;
import android.content.DialogInterface.OnDismissListener;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;

public class WarningScheduleDialog {

	private Context context;
	private boolean result;

	public WarningScheduleDialog(Context context) {
		this.context = context;
	}

	public boolean show() throws InterruptedException, ExecutionException {

		LayoutInflater inflater = LayoutInflater.from(context);
		View view = inflater.inflate(android.R.layout.simple_list_item_1, null);

		android.content.DialogInterface.OnClickListener okListener = new OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				result = true;
			}
		};

		AlertDialog.Builder builder = new AlertDialog.Builder(context)
				.setTitle(android.R.string.dialog_alert_title).setView(view)
				.setPositiveButton(android.R.string.ok, okListener);

		Dialog dialog = builder.create();
		dialog.setOnCancelListener(new OnCancelListener() {

			@Override
			public void onCancel(DialogInterface dialog) {
				result = false;
			}
		});
		dialog.setOnDismissListener(new OnDismissListener() {

			@Override
			public void onDismiss(DialogInterface dialog) {
				result = false;
			}
		});
		dialog.show();

		return result;
	}
}
