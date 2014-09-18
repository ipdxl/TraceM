/**
 * 
 */
package com.sf.tracem.path;

import java.io.IOException;

import com.sf.tracem.connection.Partner;

/**
 * @author José Guadalupe Mandujano Serrano
 * 
 */
public interface PathNavigation {

	void locatePartner(Partner partner) throws IOException;

	void addLocation(Partner partner) throws IOException;

}
