package com.sf.tracem.db;

import java.util.List;

import com.sf.tracem.connection.MeasurementPoint;
import com.sf.tracem.connection.Operation;

public class UncommitedChanges {

	private List<Operation> operations;
	private List<MeasurementPoint> measures;

	public void setOperatios(List<Operation> ops) {

		setOperations(ops);
	}

	public void setMeasures(List<MeasurementPoint> mps) {
		measures = mps;
	}

	public List<MeasurementPoint> getMeasures() {
		return measures;
	}

	/**
	 * @return the operations
	 */
	public List<Operation> getOperations() {
		return operations;
	}

	/**
	 * @param operations
	 *            the operations to set
	 */
	public void setOperations(List<Operation> operations) {
		this.operations = operations;
	}

}
