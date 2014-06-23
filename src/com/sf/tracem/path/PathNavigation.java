/**
 * 
 */
package com.sf.tracem.path;

import com.sf.tracem.connection.ZEPARTNER;

/**
 * @author José Guadalupe Mandujano Serrano
 * 
 */
public interface PathNavigation {

	void locatePartner(ZEPARTNER partner);

	void addLocation(ZEPARTNER partner);

}
