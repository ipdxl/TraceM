/**
 * 
 */
package com.sf.tracem.schedule;

import java.util.List;
import java.util.Locale;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.sf.tracem.R;
import com.sf.tracem.connection.Order;

/**
 * @author José Guadalupe Mandujano Serrano
 * 
 */
public class OrderListAdapter extends ArrayAdapter<Order> {

	private TextView aufnr;
	private TextView co_gstrpPlan;
	private TextView name;
	private TextView auftx;
	private TextView hour;

	public OrderListAdapter(Context context, int resource,
			int textViewResourceId, List<Order> orders) {
		super(context, resource, textViewResourceId, orders);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = LayoutInflater.from(getContext());
		View view = inflater.inflate(R.layout.order_item, parent, false);
		Order order = getItem(position);

		aufnr = (TextView) view.findViewById(R.id.aufnrVplan);
		auftx = (TextView) view.findViewById(R.id.auftextPlan);
		co_gstrpPlan = (TextView) view.findViewById(R.id.co_gstrpPlan);
		name = (TextView) view.findViewById(R.id.namePlan);
		hour = (TextView) view.findViewById(R.id.zhour);

		aufnr.setText(order.getAUFNR());
		auftx.setText(order.getAUFTEXT());
		co_gstrpPlan.setText(order.getCO_GSTRP());
		name.setText(order.getPARTNER());
		hour.setText(String.format(Locale.US, "%.1f", order.getZHOURS()));

		return view;
	}
}