package com.sf.tracem.path;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.LatLngBounds.Builder;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.sf.tracem.R;
import com.sf.tracem.connection.Path;
import com.sf.tracem.connection.Order;
import com.sf.tracem.connection.Partner;
import com.sf.tracem.db.DBManager;
import com.sf.tracem.login.CurrentConfig;

public class MyPathFragment extends Fragment implements PathNavigation {

	private static final String PATH_LIST = "PATS_LIST";
	public static final String TAG = "MY_PATH_FRAGMENT";
	private ExpandableListView expPathList;
	private ArrayList<Path> pathList;
	private GoogleMap map;
	private ExpandableListAdapter adapter;
	private LatLngBounds bounds;
	private Builder builder;
	private View view;
	private Marker marker;
	private UiSettings mapSettings;
	private SharedPreferences loginPreferences;

	@Override
	public void onAttach(Activity activity) {
		loginPreferences = activity.getSharedPreferences(
				CurrentConfig.LOGIN_PREFERENCES, Context.MODE_PRIVATE);
		super.onAttach(activity);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
		setRetainInstance(true);
		restoreSavedInstanceState(savedInstanceState);
	}

	@SuppressWarnings("unchecked")
	private void restoreSavedInstanceState(Bundle savedInstanceState) {
		if (savedInstanceState != null) {
			pathList = (ArrayList<Path>) savedInstanceState
					.getSerializable(PATH_LIST);
		} else {
			pathList = getPath(
					loginPreferences.getString(CurrentConfig.USERNAME, null),
					"28.04.2014");
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		view = getView();
		if (view != null) {
			((ViewGroup) view.getParent()).removeView(view);
			map = ((SupportMapFragment) getFragmentManager().findFragmentById(
					R.id.map)).getMap();
			return view;
		}
		view = inflater.inflate(R.layout.path_layout, container, false);

		map = ((SupportMapFragment) getFragmentManager().findFragmentById(
				R.id.map)).getMap();

		map.setMyLocationEnabled(true);
		map.setIndoorEnabled(true);

		mapSettings = map.getUiSettings();
		mapSettings.setMyLocationButtonEnabled(true);
		mapSettings.setCompassEnabled(true);

		expPathList = (ExpandableListView) view
				.findViewById(R.id.expandablePahtList);
		adapter = new ExpandableListAdapter(getActivity(), this, pathList);
		expPathList.setAdapter(adapter);
		expPathList.setChoiceMode(ExpandableListView.CHOICE_MODE_SINGLE);
		return view;
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		outState.putSerializable(PATH_LIST, pathList);
		// super.onSaveInstanceState(outState);
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		getActivity().getActionBar().setDisplayShowCustomEnabled(false);
		getActivity().getActionBar().setHomeButtonEnabled(true);
		getActivity().getActionBar().setDisplayHomeAsUpEnabled(true);
		inflater.inflate(R.menu.my_path_menu, menu);
		super.onCreateOptionsMenu(menu, inflater);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.pathLocations:
			map.clear();
			showPathLocations();
			break;
		case R.id.normal_map_item:
			map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
			break;
		case R.id.satelite_map_item:
			map.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
			break;
		case R.id.terrain_map_item:
			map.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
			break;
		case R.id.hybrid_map_item:
			map.setMapType(GoogleMap.MAP_TYPE_HYBRID);
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	private void showPathLocations() {
		map.clear();
		Geocoder coder = new Geocoder(getActivity());
		builder = new Builder();
		for (int i = 0; i < pathList.size(); i++) {
			List<Address> positions = new ArrayList<Address>();
			try {
				positions = coder.getFromLocationName(pathList.get(i)
						.getPartner().getADDRESS(), 1);
			} catch (IOException e) {
				e.printStackTrace();
			}
			LatLng position;
			position = new LatLng(positions.get(0).getLatitude(), positions
					.get(0).getLongitude());
			builder.include(position);
			MarkerOptions options = new MarkerOptions()
					.title(pathList.get(i).getPartner().getPARTNER())
					.position(position)
					.snippet(pathList.get(i).getPartner().getADDRESS());
			map.addMarker(options);

			bounds = builder.build();

			map.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, 10),
					2000, null);
		}
	}

	private ArrayList<Path> getPath(String user, String date) {

		DummyTask task = new DummyTask();
		task.execute(user, date);

		ArrayList<Path> list = new ArrayList<Path>();
		try {
			list = task.get();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}

		return list;
	}

	private class DummyTask extends AsyncTask<String, Void, ArrayList<Path>> {

		@Override
		protected ArrayList<Path> doInBackground(String... params) {

			ArrayList<Path> listPath = new ArrayList<Path>();
			try {
				DBManager dbManager = new DBManager(getActivity());

				List<Order> orders = dbManager.getOrders();

				List<Partner> partnerList = new ArrayList<Partner>();
				ArrayList<String> names = new ArrayList<String>();

				for (Order item : orders) {
					if (names.contains(item.getPARTNER())) {
						continue;
					}
					names.add(item.getPARTNER());

					Partner partner = new Partner();
					partner.setPARTNER(item.getPARTNER());
					partner.setADDRESS(item.getADDRESS());
					partnerList.add(partner);
				}

				for (int i = 0; i < partnerList.size(); i++) {
					Path path = new Path();
					path.setPartner(partnerList.get(i));

					List<Order> ordersList = new ArrayList<Order>();
					for (Order item : orders) {
						if (item.getPARTNER() == path.getPartner().getPARTNER()) {
							ordersList.add(item);
						}
					}

					path.setOrders((Order[]) ordersList
							.toArray(new Order[] {}));

					listPath.add(path);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

			return listPath;
		}
	}

	@Override
	public void locatePartner(Partner partner) {
		map.clear();
		builder = null;
		addLocation(partner);
	}

	@Override
	public void addLocation(Partner partner) {
		Geocoder coder = new Geocoder(getActivity());
		List<Address> positions = new ArrayList<Address>();
		String address = partner.getADDRESS();
		String name = partner.getPARTNER();
		try {
			positions = coder.getFromLocationName(address, 1);
		} catch (IOException e) {
			e.printStackTrace();
		}
		LatLng position;
		position = new LatLng(positions.get(0).getLatitude(), positions.get(0)
				.getLongitude());
		MarkerOptions options = new MarkerOptions().title(name)
				.position(position).snippet(address);
		marker = map.addMarker(options);

		marker.showInfoWindow();

		try {
			if (builder == null) {
				builder = new Builder();
				map.animateCamera(CameraUpdateFactory.newLatLngZoom(position,
						13));
				builder.include(position);
			} else {
				builder.include(position);
				bounds = builder.build();
				map.animateCamera(
						CameraUpdateFactory.newLatLngBounds(bounds, 25), 2000,
						null);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
