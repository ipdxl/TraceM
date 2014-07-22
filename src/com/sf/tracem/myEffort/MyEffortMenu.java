package com.sf.tracem.myEffort;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

import com.sf.tracem.R;
import com.sf.tracem.connection.ZEMYEFFORT;
import com.sf.tracem.db.DBManager;

public class MyEffortMenu extends FragmentActivity {
	
	DBManager dbManager = new DBManager(this);
	List<ZEMYEFFORT> effort = new ArrayList<ZEMYEFFORT>();
	Context context = this;
	String id_program;
	LinearLayout pieGraph;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.my_effort_menu);
		
		//Obtiene los programas que hay en la tabla EFFORT de la BD		
		effort = dbManager.getEffort();	
		
		//Arreglo para mostrarlo en la lista
		String[] schedule = new String[effort.size()];
		
		//Dar los valores que mostrará la lista
		if(effort != null){
			
			int i = 0;
			for (ZEMYEFFORT effort1 : effort) {	
				schedule[i] = effort1.getID_SCHEDULE();
				i++;
			}
			
			//Generar la lista y mostrarla
			setContentView(android.R.layout.list_content);
			
			ListAdapter adapter = new ArrayAdapter<String>(this,
					android.R.layout.simple_list_item_1, android.R.id.text1, schedule);

			ListView listView = (ListView) findViewById(android.R.id.list);
			listView.setAdapter(adapter);
			listView.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					
					//Guardar lo que tiene la posición a la que se le dio click
					ZEMYEFFORT eff = effort.get(position);
  				    
					//Pasar a la pantalla que mostrará las graficas
					Intent intentEffort = new Intent(context, MyEffort.class);	
					
					//Pasarle los parametros que se necesitan para generar la grafica
					intentEffort.putExtra("total", eff.getTOTAL());
					intentEffort.putExtra("conf", eff.getCONFIRMADAS());
					intentEffort.putExtra("t_estim", eff.getT_ESTIMADOS());
					intentEffort.putExtra("t_conf", eff.getT_CONFIRMADOS());
					
					startActivity(intentEffort);
				    
				}
				
			});
		}
	}
}



