/**
 * 
 */
package com.sf.tracem.plan;

import java.util.List;

import com.sf.tracem.R;
import com.sf.tracem.connection.Order;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

/**
 * @author USER-7
 * 
 */
public class OrderPlanListAdapter extends
		com.sf.tracem.schedule.OrderListAdapter {

	/**
	 * 
	 */
	public OrderPlanListAdapter(Context context, int resource,
			int textViewResourceId, List<Order> orders) {
		super(context, resource, textViewResourceId, orders);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = super.getView(position, convertView, parent);

		if (position % 2 == 0) {
			view.setBackgroundResource(R.color.blue_sf);
		} else {
			view.setBackgroundResource(R.color.light_gray);
		}

		return view;
	}
}
