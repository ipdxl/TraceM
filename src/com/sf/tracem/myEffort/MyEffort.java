package com.sf.tracem.myEffort;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.FrameLayout;

import com.sf.tracem.R;

public class MyEffort extends FragmentActivity {

	MyEffortMenu mem = new MyEffortMenu();
	Context context = this;
	String id_program;
	View EstadisticaIntent;
	View TiemposIntent;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.my_effort);

		// Generar el frame para mostrar la grafica de pie
		FrameLayout pieG = (FrameLayout) findViewById(R.id.PieGraph);
		// Generar el frame para mostrar la grafica de barra
		FrameLayout barG = (FrameLayout) findViewById(R.id.BarGraph);

		// Clase para obtener las vistas de las graficas
		Graphs g = new Graphs();

		// Para obtener los parametros que se le pasaron al llamarla
		Intent parameters = getIntent();

		// Inicializar los datos que se enviaran a los metodos de las graficas
		int total = Integer.parseInt((String) parameters
				.getSerializableExtra("total"));
		int conf = Integer.parseInt((String) parameters
				.getSerializableExtra("conf"));
		double t_estim = Double.parseDouble((String) parameters
				.getSerializableExtra("t_estim"));
		double t_conf = Double.parseDouble((String) parameters
				.getSerializableExtra("t_conf"));
		int pendientes = total - conf;
		int[] values = { conf, pendientes };

		// Obtener las vistas
		EstadisticaIntent = g.getPieGraph(context, values);
		TiemposIntent = g.getBarGraph(context, t_estim, t_conf);

		// Añadir las vistas a los frames
		pieG.addView(EstadisticaIntent);
		barG.addView(TiemposIntent);
	}
}
