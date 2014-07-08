/**
 * 
 */
package com.sf.tracem.plan;

/**
 * @author José Guadalupe Mandujano Serrano
 * 
 */
public interface MyJobNavigation {

	void onShowOrderDetail(String orderNumber, String name);

	void onCreateSchedule(String id);

	void onViewPlans();

	void onViewMyJob();

	void onViewMyPath();

	void onViewMyVisit();

	void onVisitDetail();

}
