/**
 * 
 */
package com.sf.tracem.plan;

import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.sf.tracem.connection.Order;

/**
 * @author USER-7
 * 
 */
@SuppressLint("ViewConstructor")
public class OrderPlanListAdapter extends
		com.sf.tracem.schedule.OrderListAdapter {

	/**
	 * 
	 */
	public OrderPlanListAdapter(Context context, int resource,
			int textViewResourceId, List<Order> orders) {
		super(context, orders);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = super.getView(position, convertView, parent);

		// if (position % 2 == 0) {
		// view.setBackgroundResource(R.color.blue_sf);
		// } else {
		// view.setBackgroundResource(R.color.light_gray);
		// }

		return view;
	}
}
