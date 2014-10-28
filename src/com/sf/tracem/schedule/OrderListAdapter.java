/**
 * 
 */
package com.sf.tracem.schedule;

import java.util.List;
import java.util.Locale;

import android.content.Context;
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
	private TextView status;

	public OrderListAdapter(Context context, List<Order> orders) {
		super(context, R.layout.order_item, R.id.aufnr, orders);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = super.getView(position, convertView, parent);
		Order order = getItem(position);

		aufnr = (TextView) view.findViewById(R.id.aufnr);
		auftx = (TextView) view.findViewById(R.id.auftext);
		co_gstrpPlan = (TextView) view.findViewById(R.id.co_gstrp);
		name = (TextView) view.findViewById(R.id.name);
		hour = (TextView) view.findViewById(R.id.zhour);
		status = (TextView) view.findViewById(R.id.order_status);

		aufnr.setText(order.getAufnr());
		auftx.setText(order.getAuftext());
		co_gstrpPlan.setText(order.getCoGstrp());
		name.setText(order.getPartner());
		hour.setText(String.format(Locale.US, "%.1f", order.getZHhours()));

		switch (order.getOrderStatus()) {
		case 0:
			status.setText("");
			break;

		case 1:
			status.setText(getContext().getResources().getString(
					R.string.complete));
			status.setTextColor(getContext().getResources().getColor(
					android.R.color.holo_green_dark));
			break;
		case 2:
			status.setText(getContext().getResources().getString(
					R.string.in_process));
			status.setTextColor(getContext().getResources().getColor(
					android.R.color.holo_blue_dark));
			break;
		}

		return view;
	}
}