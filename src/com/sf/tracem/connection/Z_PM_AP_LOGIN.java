/**
 * 
 */
package com.sf.tracem.connection;

import java.io.Serializable;
import java.util.List;

/**
 * @author José Guadalupe Mandujano Serrano
 * 
 */
@SuppressWarnings("serial")
public class Z_PM_AP_LOGIN implements Serializable {

	// , KvmSerializable
	private String rol;
	private String planta;
	private String location;

	private List<Message> messageList;
	private List<Menu> menuList;

	/**
	 * @return the rol
	 */
	public String getRol() {
		return rol;
	}

	/**
	 * @param rol
	 *            the rol to set
	 */
	public void setRol(String rol) {
		this.rol = rol;
	}

	/**
	 * @return the planta
	 */
	public String getPlanta() {
		return planta;
	}

	/**
	 * @param planta
	 *            the planta to set
	 */
	public void setPlanta(String planta) {
		this.planta = planta;
	}

	/**
	 * @return the location
	 */
	public String getLocation() {
		return location;
	}

	/**
	 * @param location
	 *            the location to set
	 */
	public void setLocation(String location) {
		this.location = location;
	}

	/**
	 * @return the messageList
	 */
	public List<Message> getMessageList() {
		return messageList;
	}

	/**
	 * @param messageList
	 *            the messageList to set
	 */
	public void setMessageList(List<Message> messageList) {
		this.messageList = messageList;
	}

	/**
	 * @return the menuList
	 */
	public List<Menu> getMenuList() {
		return menuList;
	}

	/**
	 * @param menuList
	 *            the menuList to set
	 */
	public void setMenuList(List<Menu> menuList) {
		this.menuList = menuList;
	}

	// @Override
	// public Object getProperty(int arg0) {
	// switch (arg0) {
	// case 0:
	// return getRol();
	// case 1:
	// return getPlanta();
	// case 2:
	// return getLocation();
	// case 3:
	// return getMessageList();
	// case 4:
	// return getMenuList();
	// default:
	// return null;
	// }
	// }
	//
	// @Override
	// public int getPropertyCount() {
	// return 5;
	// }
	//
	// @Override
	// public void getPropertyInfo(int arg0, Hashtable arg1, PropertyInfo arg2)
	// {
	// switch (arg0) {
	// case 0:
	// arg2.name = "ZROL";
	// arg2.setType(PropertyInfo.STRING_CLASS);
	// case 1:
	// arg2.name = "ZPLANTA";
	// arg2.setType(PropertyInfo.STRING_CLASS);
	// case 2:
	// arg2.name = "ZLOCATION";
	// arg2.setType(PropertyInfo.STRING_CLASS);
	// case 3:
	// arg2.name = "IT_RETURN";
	// arg2.setType(PropertyInfo.VECTOR_CLASS);
	// case 4:
	// arg2.name = "IT_MENU";
	// arg2.setType(PropertyInfo.VECTOR_CLASS);
	// }
	// }
	//
	// @Override
	// public void setProperty(int arg0, Object arg1) {
	// switch (arg0) {
	// case 0:
	// setRol((String) arg1);
	// case 1:
	// setPlanta((String) arg1);
	// case 2:
	// setLocation((String) arg1);
	// case 3:
	// setMessageList((List<Message>) arg1);
	// case 4:
	// setMenuList((List<Menu>) arg1);
	// }
	// }

}
