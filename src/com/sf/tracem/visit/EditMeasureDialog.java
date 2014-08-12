/**
 * 
 */
package com.sf.tracem.visit;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.sf.tracem.R;
import com.sf.tracem.connection.MeasurementPoint;

/**
 * @author USER-7
 * 
 */
@SuppressLint("InflateParams")
public class EditMeasureDialog extends DialogFragment {

	public static final String TAG = "EDIT_MEASURE_DIALOG";
	private MeasurementPoint mp;

	public MeasurementPoint getMp() {
		return mp;
	}

	private OnClickListener okListener;
	private TextView read;
	private TextView notes;
	private boolean isShown = true;
	protected boolean canceled;

	public EditMeasureDialog(MeasurementPoint mp) {
		super();
		this.mp = mp;
	}

	@Override
	public void onCancel(DialogInterface dialog) {
		isShown = false;
		canceled = true;
		super.onCancel(dialog);
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		isShown = true;

		okListener = new OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				try {
					mp.setRead(Double.parseDouble(read.getText().toString()));
				} catch (NumberFormatException e) {
					mp.setRead(0.0);
				}
				mp.setNotes(notes.getText().toString());
				isShown = false;
			}
		};

		LayoutInflater inflater = LayoutInflater.from(getActivity());

		View view = inflater.inflate(R.layout.edit_measure, null, false);

		read = (TextView) view.findViewById(R.id.read);
		notes = (TextView) view.findViewById(R.id.notes);

		read.setText("" + mp.getRead());
		notes.setText(mp.getNotes());

		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
				.setIcon(android.R.drawable.ic_dialog_dialer)
				.setTitle(R.string.edit)
				.setPositiveButton(android.R.string.ok, okListener)
				.setNegativeButton(android.R.string.cancel, null).setView(view);

		Dialog dialog = builder.create();

		return dialog;
	}

	public boolean isCanceled() {
		return canceled;
	}

	public boolean isShow() {
		return isShown;
	}

}
