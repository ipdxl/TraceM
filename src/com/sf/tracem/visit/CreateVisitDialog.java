/**
 * 
 */
package com.sf.tracem.visit;

import com.sf.tracem.R;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * @author USER-7
 * 
 */
public class CreateVisitDialog extends DialogFragment {

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {

		Builder builder = new AlertDialog.Builder(getActivity())
				.setTitle(getResources().getString(R.string.create_visit))
				.setPositiveButton(android.R.string.ok, new OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {

					}
				})
				.setNegativeButton(android.R.string.no, new OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {

					}
				});

		AlertDialog dialog = builder.create();

		return dialog;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return super.onCreateView(inflater, container, savedInstanceState);
	}

}
