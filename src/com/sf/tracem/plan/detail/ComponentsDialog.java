/**
 * 
 */
package com.sf.tracem.plan.detail;

import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;

import com.sf.tracem.R;
import com.sf.tracem.connection.Component;

/**
 * @author USER-7
 * 
 */
public class ComponentsDialog extends DialogFragment {

	public static final String COMPONENTS = "COMPONENTS";
	private View view;
	private ListView list;
	private List<Component> components;
	private Builder builder;

	/**
	 * 
	 */
	public ComponentsDialog() {
	}

	@Override
	public Dialog getDialog() {

		builder = new AlertDialog.Builder(getActivity())
				.setPositiveButton(android.R.string.ok, null)
				.setNegativeButton(android.R.string.cancel, null)

				.setTitle(getResources().getString(R.string.components))
				.setIcon(android.R.drawable.ic_dialog_info);

		AlertDialog dialog = builder.create();
		return dialog;
	}

	@SuppressWarnings("unchecked")
	@Override
	public View getView() {
		LayoutInflater inflater = LayoutInflater.from(getActivity());

		components = (ArrayList<Component>) getArguments().getSerializable(
				COMPONENTS);

		view = inflater.inflate(android.R.layout.list_content, null, false);
		list = (ListView) view.findViewById(android.R.id.list);

		ComponentsAdapter ca = new ComponentsAdapter(getActivity(), components);

		list = (ListView) view.findViewById(android.R.id.list);
		list.setAdapter(ca);

		return view;
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
	}
}
