package com.sf.tracem.myEffort;

import org.achartengine.ChartFactory;
import org.achartengine.chart.BarChart.Type;
import org.achartengine.model.CategorySeries;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.renderer.DefaultRenderer;
import org.achartengine.renderer.SimpleSeriesRenderer;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import com.sf.tracem.R;

import android.app.Fragment;
import android.content.Context;
import android.graphics.Color;
import android.view.View;

public class Graphs extends Fragment {

	private String unatended;
	private String confirmed;
	private String scheduleOrders;
	private String estimatedTime;
	private String confirmedTime;

	public View getPieGraph(Context context, int[] values) {
		unatended = context.getResources().getString(R.string.unatended);
		confirmed = context.getResources().getString(R.string.confirmed);
		scheduleOrders = context.getResources().getString(
				R.string.schedule_orders);
		estimatedTime = context.getResources().getString(
				R.string.estimated_time);
		confirmedTime = context.getResources().getString(
				R.string.confirmed_time);

		// values tiene la estructura {pendientes, confirmadas}
		// Para guardar los valores que mostrará la grafica
		CategorySeries series = new CategorySeries("Pie Graph");
		int k = 0;
		for (int value : values) {
			if (k == 0) {
				series.add(unatended + " (" + value + ")", value);
			}
			if (k == 1) {
				series.add(confirmed + "(" + value + ")", value);
			}
			k++;
		}

		// Colores de los pedacitos de la grafica
		int[] colors = new int[] {
				context.getResources().getColor(android.R.color.holo_blue_dark),
				context.getResources()
						.getColor(android.R.color.holo_green_dark) };

		// Añade los colores a la grafica
		DefaultRenderer renderer = new DefaultRenderer();
		for (int color : colors) {
			SimpleSeriesRenderer r = new SimpleSeriesRenderer();
			r.setColor(color);
			renderer.addSeriesRenderer(r);
		}
		// Personalizar las propiedades de la grafica
		renderer.setChartTitle(scheduleOrders); // Título
		renderer.setChartTitleTextSize(30); // Tamaño de letra del título
		renderer.setLabelsTextSize(15); // Tamaño de letra de las etiquetas
		renderer.setLabelsColor(Color.BLACK); // Color de las letras
		renderer.setShowLabels(true);
		renderer.setShowLegend(true);
		renderer.setBackgroundColor(context.getResources().getColor(
				R.color.light_gray));
		renderer.setApplyBackgroundColor(true);

		// Genera una vista de la grafica y la regresa
		return ChartFactory.getPieChartView(context, series, renderer);
	}

	public View getBarGraph(Context context, double t_estimados, double t_conf) {

		// Bar 1 para barra de estimados
		double[] y = { 0, t_estimados, 0 };
		CategorySeries series = new CategorySeries(estimatedTime + ": "
				+ t_estimados + "  ");
		for (int i = 0; i < y.length; i++) {
			series.add(estimatedTime + " " + (y[i]), y[i]);
		}

		// Bar 2 para confirmados
		double[] y2 = { 0, t_conf, 0 };
		CategorySeries series2 = new CategorySeries(confirmedTime + ": "
				+ t_conf + "  ");
		for (int i = 0; i < y.length; i++) {
			series2.add("Tiempos confirmados " + y[i], y2[i]);
		}

		// Añadir las barras al set de datos
		XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
		dataset.addSeries(series.toXYSeries());
		dataset.addSeries(series2.toXYSeries());

		// Personalizar la grafica en general
		XYMultipleSeriesRenderer mRenderer = new XYMultipleSeriesRenderer();
		mRenderer.setChartTitle(estimatedTime + " / " + confirmedTime); // Título
																		// de
																		// la
																		// grafica
		mRenderer.setLabelsColor(context.getResources().getColor(
				android.R.color.primary_text_light)); // Color de todas las
														// letras
		mRenderer.setApplyBackgroundColor(true); // Para que aplique el color de
													// fondo
		mRenderer.setBackgroundColor(context.getResources().getColor(
				R.color.light_gray)); // Color de fondo
		mRenderer.setMarginsColor(context.getResources().getColor(
				R.color.light_gray));

		mRenderer.setShowAxes(true);

		// Customize bar 1
		XYSeriesRenderer renderer = new XYSeriesRenderer();
		renderer.setColor(context.getResources().getColor(
				android.R.color.holo_blue_dark));
		renderer.setDisplayChartValues(true);
		renderer.setChartValuesSpacing((float) 0.5);
		renderer.setChartValuesSpacing((float) 0.5);
		renderer.setChartValuesTextSize(15);

		// Customize bar 2
		XYSeriesRenderer renderer2 = new XYSeriesRenderer();
		renderer2.setColor(context.getResources().getColor(
				android.R.color.holo_red_dark));
		renderer2.setChartValuesSpacing((float) 0.5);
		renderer2.setChartValuesSpacing((float) 0.5);
		renderer2.setDisplayChartValues(true);
		renderer2.setChartValuesTextSize(15);

		// Añadir las propiedades de las graficas a mRender
		mRenderer.addSeriesRenderer(renderer);
		mRenderer.addSeriesRenderer(renderer2);
		mRenderer.setChartTitleTextSize(30);
		mRenderer.setLabelsTextSize(15);
		mRenderer.setLegendTextSize(15);
		mRenderer.setShowAxes(false);

		// Genera una vista de la grafica y la regresa
		return ChartFactory.getBarChartView(context, dataset, mRenderer,
				Type.DEFAULT);

	}
}
