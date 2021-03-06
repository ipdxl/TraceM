/**
 * 
 */
package com.sf.tracem.plan;

/**
 * @author Jos� Guadalupe Mandujano Serrano
 * 
 */
public interface MyJobNavigation {

	void onShowOrderDetail(String orderNumber, String name);

	void onCreateSchedule(String id);

	void onViewSchedules();

	void onViewMyJob();

	void onViewMyPath();

	void onViewMyVisit();

	void onVisitDetail();

	void OnVisitOrderSelected(String aufnr);

}
