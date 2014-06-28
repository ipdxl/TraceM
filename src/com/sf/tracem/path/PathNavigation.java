/**
 * 
 */
package com.sf.tracem.path;

import com.sf.tracem.connection.Partner;

/**
 * @author José Guadalupe Mandujano Serrano
 * 
 */
public interface PathNavigation {

	void locatePartner(Partner partner);

	void addLocation(Partner partner);

}
