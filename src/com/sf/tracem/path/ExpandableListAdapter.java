/**
 * 
 */
package com.sf.tracem.path;

import java.util.Arrays;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.sf.tracem.R;
import com.sf.tracem.connection.Path;
import com.sf.tracem.schedule.OrderListAdapter;

/**
 * @author José Guadalupe Mandujano Serrano
 * 
 */
public class ExpandableListAdapter extends BaseExpandableListAdapter {

	private Context context;
	private List<Path> pathList;
	private int locationPressed;
	private int addLocationPressed;
	public PathNavigation pathNavigation;
	private OnLocationClickListener locationclickListener;
	private OrderListAdapter[] childAdapter;

	public ExpandableListAdapter(Context context,
			PathNavigation pathNavigation, List<Path> pathList) {

		childAdapter = new OrderListAdapter[pathList.size()];
		this.context = context;
		this.pathList = pathList;
		this.pathNavigation = pathNavigation;
		locationclickListener = new OnLocationClickListener();
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return true;
	}

	@Override
	public boolean hasStableIds() {
		return false;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {
		LayoutInflater inflater = LayoutInflater.from(context);

		View group = inflater.inflate(R.layout.partner_header, parent, false);

		TextView partnerName = (TextView) group.findViewById(R.id.partnerName);
		partnerName.setText(pathList.get(groupPosition).getPartner()
				.getPARTNER());

		TextView partnerAddress = (TextView) group
				.findViewById(R.id.partnerAddress);

		ImageButton locationButton = (ImageButton) group
				.findViewById(R.id.locationButton);
		locationButton.setTag(groupPosition);
		ImageButton addLocationButton = (ImageButton) group
				.findViewById(R.id.addLocationButton);
		addLocationButton.setTag(groupPosition);

		locationButton.setOnClickListener(locationclickListener);
		addLocationButton.setOnClickListener(locationclickListener);

		partnerAddress.setText(pathList.get(groupPosition).getPartner()
				.getADDRESS());

		group.setBackgroundResource(R.color.blue_sf);

		return group;
	}

	@Override
	public long getGroupId(int groupPosition) {
		return groupPosition;
	}

	@Override
	public int getGroupCount() {
		return pathList.size();
	}

	@Override
	public Object getGroup(int groupPosition) {
		return pathList.get(groupPosition);
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		return pathList.get(groupPosition).getOrders().length;
	}

	@Override
	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {

		if (childAdapter[groupPosition] == null) {
			childAdapter[groupPosition] = new OrderListAdapter(context,
					R.layout.order_item, R.id.aufnr, Arrays.asList(pathList
							.get(groupPosition).getOrders()));
		}

		return childAdapter[groupPosition].getView(childPosition, convertView,
				parent);
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		return Long
				.parseLong(pathList.get(groupPosition).getOrders()[childPosition]
						.getAUFNR());
	}

	@Override
	public Object getChild(int groupPosition, int childPosition) {
		return pathList.get(groupPosition).getOrders()[childPosition];
	}

	private class OnLocationClickListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			if (v.getId() == R.id.locationButton) {
				locationPressed = (Integer) v.getTag();
				pathNavigation.locatePartner(pathList.get(locationPressed)
						.getPartner());
			} else if (v.getId() == R.id.addLocationButton) {
				addLocationPressed = (Integer) v.getTag();
				pathNavigation.addLocation(pathList.get(addLocationPressed)
						.getPartner());
			}

		}

	}
}