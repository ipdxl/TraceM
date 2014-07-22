/**
 * 
 */
package com.sf.tracem.plan.detail;

import java.util.List;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
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
@SuppressLint("InflateParams")
public class ComponentsDialog extends DialogFragment {

	public static final String COMPONENTS = "COMPONENTS";
	private View view;
	private ListView list;
	private List<Component> components;
	private Builder builder;

	/**
	 * 
	 */
	public ComponentsDialog(List<Component> cmponents) {
		this.components = cmponents;
	}

	@Override
	public Dialog getDialog() {
		LayoutInflater inflater = LayoutInflater.from(getActivity());

		view = inflater.inflate(android.R.layout.list_content, null, false);
		list = (ListView) view.findViewById(android.R.id.list);

		ComponentsAdapter ca = new ComponentsAdapter(getActivity(), components);

		list = (ListView) view.findViewById(android.R.id.list);
		list.setAdapter(ca);

		builder = new AlertDialog.Builder(getActivity())
				.setPositiveButton(android.R.string.ok, null)
				.setTitle(R.string.components)
				.setIcon(android.R.drawable.ic_dialog_info).setView(view);

		AlertDialog dialog = builder.create();
		return dialog;
	}

	@Override
	public View getView() {
		LayoutInflater inflater = LayoutInflater.from(getActivity());
//
//		components = (ArrayList<Component>) getArguments().getSerializable(
//				COMPONENTS);

		view = inflater.inflate(android.R.layout.list_content, null, false);
		list = (ListView) view.findViewById(android.R.id.list);

		ComponentsAdapter ca = new ComponentsAdapter(getActivity(), components);

		list = (ListView) view.findViewById(android.R.id.list);
		list.setAdapter(ca);

		return view;
	}
}
