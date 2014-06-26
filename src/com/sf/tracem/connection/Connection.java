package com.sf.tracem.connection;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpResponseException;
import org.ksoap2.transport.HttpTransportBasicAuth;
import org.xmlpull.v1.XmlPullParserException;

import android.app.Activity;
import android.content.Context;

import com.sf.tracem.db.ComponentTable;
import com.sf.tracem.db.DBManager;
import com.sf.tracem.db.HeaderOrderTable;
import com.sf.tracem.db.MenuTable;
import com.sf.tracem.db.OperationTable;
import com.sf.tracem.db.OrdersTable;
import com.sf.tracem.db.PartnerTable;
import com.sf.tracem.db.ScheduleTable;

public class Connection extends Activity {

	private final static String NAMESPACE = "urn:sap-com:document:sap:rfc:functions";
	// private final static String URL =
	// "http://192.168.100.1:8004/sap/bc/srt/rfc/sap/ZWS_PM_ORDERS_2?sap-client=800";
	private final static String URL = "http://25.111.9.178:8004/sap/bc/srt/rfc/sap/ZWS_PM_ORDERS_2?sap-client=800";
	private final static String URL2 = "http://25.111.9.178:8004/sap/bc/srt/rfc/sap/ZPM_AP_WS?sap-client=800";
	private final static String SAP_USER = "sapuser";
	private final static String SAP_PASSWORD = "password3";
	private final static String SOAP_ACTION = "";
	private final static String MY_EFFORT_WM = "Z_PM_EFFORT";
	private final static String Z_PM_AP_GET_PLAN = "Z_PM_AP_GET_PLAN";
	private final static String Z_PM_AP_GET_SCHEDULE_DETAIL = "Z_PM_AP_GET_SCHEDULE_DETAIL";
	private final static String Z_PM_AP_GET_ORDER_DETAIL = "Z_PM_AP_GET_ORDER_DETAIL";
	// private final static String Z_PM_LOG = "Z_PM_LOG";
	private static final String CONFIRMATION = "Z_PM_ORDER_CONFIRMATION";
	/*
	 * Web Service 2
	 */
	private final static String Z_PM_AP_LOGIN = "Z_PM_AP_LOGIN";
	private static final String Z_PM_AP_LOGOUT = "Z_PM_AP_LOGOUT";
	private static final String GET_NEXT_SCHEDULE = "Z_PM_AP_GET_NEXT_SCHEDULE";
	private static final String Z_PM_AP_CREATE_SCHEDULE = "Z_PM_AP_CREATE_SCHEDULE";
	private static final String Z_PM_AP_UPDATE_SCHEDULE = "Z_PM_AP_UPDATE_SCHEDULE";
	private static final String Z_PM_AP_GET_SCHEDULE_LIST = "Z_PM_AP_GET_SCHEDULE_LIST";
	private static final String anyType = "anyType{}";
	private static final String Z_PM_AP_DELETE_SCHEDULE = "Z_PM_AP_DELETE_SCHEDULE";
	private static final String Z_PM_AP_GET_VISIT_LIST = "Z_PM_AP_GET_VISIT_LIST";
	private static final String P_USER = "P_USER";
	private static final String FINI = "FINI";
	private static final String HINI = "HINI";
	private static final String TINI = "TINI";
	private static final String P_PROGRAM = "P_PROGRAM";
	private static final String P_PASSWORD = "P_PASSWORD";
	@SuppressWarnings("unused")
	private Context context;
	private DBManager dbManager;

	/**
	 * 
	 * @param context
	 */
	public Connection(Context context) {
		this.context = context;
		dbManager = new DBManager(context);
	}

	/**
	 * @param userName
	 * @param password
	 * @return Z_PM_AP_LOGIN object containing user information
	 * @throws HttpResponseException
	 * @throws IOException
	 * @throws XmlPullParserException
	 */
	public Z_PM_AP_LOGIN login(String userName, String password)
			throws HttpResponseException, IOException, XmlPullParserException {

		SoapObject request = new SoapObject(NAMESPACE, Z_PM_AP_LOGIN);

		request.addProperty(P_PASSWORD, password);
		request.addProperty(P_USER, userName);

		// Create envelope
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);

		// envelope.dotNet = true;
		// Set output SOAP object
		envelope.setOutputSoapObject(request);

		// Create HTTP call object
		HttpTransportBasicAuth httpTrandport = new HttpTransportBasicAuth(URL2,
				SAP_USER, SAP_PASSWORD);

		httpTrandport.debug = true;

		// Involve web service
		httpTrandport.call(SOAP_ACTION, envelope);

		// Get the response
		@SuppressWarnings("unchecked")
		Vector<Object> response = (Vector<Object>) envelope.getResponse();

		Z_PM_AP_LOGIN login = new Z_PM_AP_LOGIN();

		login.setMenuList(getMenuList((SoapObject) response.get(0)));
		login.setMessageList(getMessageList((SoapObject) response.get(1)));
		login.setLocation(((SoapPrimitive) response.get(2)).toString());
		login.setPlanta(((SoapPrimitive) response.get(3)).toString());
		login.setRol(((SoapPrimitive) response.get(4)).toString());

		return login;

	}

	public List<Message> logout(String userName) throws HttpResponseException,
			IOException, XmlPullParserException {

		SoapObject request = new SoapObject(NAMESPACE, Z_PM_AP_LOGOUT);

		request.addProperty(P_USER, userName);

		// Create envelope
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);

		// envelope.dotNet = true;
		// Set output SOAP object
		envelope.setOutputSoapObject(request);

		// Create HTTP call object
		HttpTransportBasicAuth httpTrandport = new HttpTransportBasicAuth(URL2,
				SAP_USER, SAP_PASSWORD);

		httpTrandport.debug = true;

		// Involve web service
		httpTrandport.call(SOAP_ACTION, envelope);

		// Get the response
		SoapObject response = (SoapObject) envelope.getResponse();

		return getMessageList(response);

	}

	/**
	 * 
	 * @param object
	 *            {@link SoapObject} containing a Message List
	 * @return
	 */
	private List<Message> getMessageList(SoapObject object) {
		int count = object.getPropertyCount();

		ArrayList<Message> messageList = new ArrayList<Message>();

		for (int i = 0; i < count; i++) {
			SoapObject item = (SoapObject) object.getProperty(i);
			Message message = new Message();
			message.setMessage(parseResult(item.getProperty("MESSAGE")
					.toString()));
			message.setNumber((int) parseNumericResult(item.getProperty(
					"NUMBER").toString()));
			message.setType((item.getProperty("TYPE").toString().charAt(0)));
			messageList.add(message);
		}

		return messageList;
	}

	/**
	 * 
	 * @param userName
	 *            User name
	 * @return Next program id available
	 * @throws HttpResponseException
	 * @throws IOException
	 * @throws XmlPullParserException
	 */
	@SuppressWarnings("unchecked")
	public String getNetxSchedule(String userName)
			throws HttpResponseException, IOException, XmlPullParserException {

		SoapObject request = new SoapObject(NAMESPACE, GET_NEXT_SCHEDULE);

		request.addProperty(P_USER, userName);

		HttpTransportBasicAuth transport = new HttpTransportBasicAuth(URL2,
				SAP_USER, SAP_PASSWORD);

		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);

		envelope.setOutputSoapObject(request);

		transport.call(SOAP_ACTION, envelope);

		Vector<Object> response = (Vector<Object>) envelope.getResponse();

		// List<Message> messages = getMessageList((SoapObject)
		// response.get(1));
		String idProgram = parseResult(response.get(0).toString());

		return idProgram;
	}

	/**
	 * 
	 * @param object
	 *            {@link SoapObject} with menu list
	 * @return {@link List}<{@link Menu}>
	 * @throws NumberFormatException
	 */
	private List<Menu> getMenuList(SoapObject object)
			throws NumberFormatException {
		int count = object.getPropertyCount();

		ArrayList<Menu> menuList = new ArrayList<Menu>();

		for (int i = 0; i < count; i++) {
			Menu menu = new Menu();
			SoapObject item = (SoapObject) object.getProperty(i);

			menu.setIdMenu((byte) parseNumericResult(item
					.getPropertyAsString(MenuTable.ID_MENU)));
			menu.setIdFather((byte) parseNumericResult(item
					.getPropertyAsString(MenuTable.ID_FATHER)));

			menuList.add(menu);
		}

		return menuList;
	}

	public ZEMYEFFORT getMyEffort(String user) throws Exception {

		// Create request
		SoapObject request = new SoapObject(NAMESPACE, MY_EFFORT_WM);

		request.addProperty(P_USER, user);
		// Create envelope

		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		// envelope.dotNet = true;
		// Set output SOAP object
		envelope.setOutputSoapObject(request);
		// Create HTTP call object
		HttpTransportBasicAuth httpTransport = new HttpTransportBasicAuth(URL,
				SAP_USER, SAP_PASSWORD);

		httpTransport.debug = true;

		ZEMYEFFORT zEffort = new ZEMYEFFORT();
		// Involve web service

		httpTransport.call(SOAP_ACTION, envelope);
		// Get the response
		@SuppressWarnings("unchecked")
		Vector<SoapObject> response2 = (Vector<SoapObject>) envelope
				.getResponse();
		@SuppressWarnings("unchecked")
		Vector<Object> response3 = (Vector<Object>) envelope.getResponse();

		SoapObject errorSoap = response2.get(0);
		List<Message> errors = getMessageList(errorSoap);

		zEffort.setErrors(errors);

		if (errors.size() > 0) {
			return zEffort;
		}

		zEffort.setCONFIRMADAS(parseResult(response3.get(1).toString()));
		zEffort.setPCONFIRMADAS(parseResult(response3.get(2).toString()));
		zEffort.setPENDIENTES(parseResult(response3.get(3).toString()));
		zEffort.setTOTAL(parseResult(response3.get(4).toString()));

		return zEffort;
	}

	/**
	 * 
	 * @param listSoap
	 *            {@link SoapObject} containig a list of orders
	 * @return
	 */
	private List<ZEORDER> getListOrders(SoapObject listSoap) {
		int count = listSoap.getPropertyCount();
		List<ZEORDER> orders = new ArrayList<ZEORDER>();

		ZEORDER order;
		for (int i = 0; i < count; i++) {
			SoapObject item = (SoapObject) listSoap.getProperty(i);
			order = new ZEORDER();
			order.setAUFART(parseResult(item.getProperty(OrdersTable.AUFART)
					.toString()));
			order.setAUFNR(parseResult(item.getProperty(OrdersTable.AUFNR)
					.toString()));
			order.setAUFTEXT(parseResult(item.getProperty(OrdersTable.AUFTEXT)
					.toString()));
			order.setCO_GSTRP(parseResult(item
					.getProperty(OrdersTable.CO_GSTRP).toString()));
			order.setPARTNER(parseResult(item.getProperty(OrdersTable.PARTNER)
					.toString()));
			order.setADDRESS(parseResult(item.getProperty(PartnerTable.ADDRESS)
					.toString()));

			order.setORDER_STATUS((int) parseNumericResult(item.getProperty(
					OrdersTable.ORDER_STATUS).toString()));
			order.setEXP_DAYS(parseResult(item
					.getProperty(OrdersTable.EXP_DAYS).toString()));
			order.setEXP_STATUS(parseResult(item.getProperty(
					OrdersTable.EXP_STATUS).toString()));
			order.setZHOURS(parseResult(item.getProperty(OrdersTable.ZHOURS)
					.toString()));
			// order.setASSIGNED_STATUS(parseBitResult(item.getProperty(
			// "ASSIGNED_STATUS").toString()));

			orders.add(order);
		}
		return orders;
	}

	/**
	 * 
	 * @param bool
	 *            {@link String} with "X" or Empty {@link String}
	 * @return 1 or 0
	 */
	private byte parseBitResult(String bool) {
		if (anyType.equalsIgnoreCase(bool)) {
			return 0;
		} else {
			return 1;
		}
	}

	/**
	 * 
	 * @param UserName
	 *            User name
	 * @return Single user's plan
	 * @throws Exception
	 */
	public List<ZEORDER> getPlan(String UserName) throws Exception {
		return getPlan(new String[] { UserName });
	}

	/**
	 * 
	 * @param users
	 *            User's names
	 * @return User's plan list
	 * @throws XmlPullParserException
	 * @throws IOException
	 * @throws HttpResponseException
	 */
	@SuppressWarnings("unchecked")
	public List<ZEORDER> getPlan(String[] users) throws HttpResponseException,
			IOException, XmlPullParserException {

		// Create request
		SoapObject request = new SoapObject(NAMESPACE, Z_PM_AP_GET_PLAN);

		SoapObject userslist = new SoapObject();

		for (String user : users) {
			SoapObject item = new SoapObject();
			item.addProperty("ZUSER", user);
			userslist.addProperty("item", item);
		}
		request.addProperty("IT_USERS", userslist);

		// Create envelope
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		// envelope.dotNet = true;
		// Set output SOAP object
		envelope.setOutputSoapObject(request);
		// Create HTTP call object
		HttpTransportBasicAuth httpTrandport = new HttpTransportBasicAuth(URL2,
				SAP_USER, SAP_PASSWORD);

		// httpTrandport.debug = true;
		Vector<SoapObject> response = null;
		List<ZEORDER> zOrders = null;
		// Involve web service

		httpTrandport.call(SOAP_ACTION, envelope);
		response = (Vector<SoapObject>) envelope.getResponse();
		SoapObject soapOrder = (SoapObject) response.get(0);
		zOrders = getListOrders(soapOrder);
		dbManager.insertOrders(zOrders);
		List<Schedule> schedules = getScheduleList(users[0]);
		for (Schedule schedule : schedules) {
			getScheduleDetail(users[0], schedule.getID_PROGRAM());
		}

		return zOrders;
	}

	/**
	 * 
	 * @param userName
	 *            User name
	 * @param year
	 *            Schedule year
	 * @param week
	 *            Schedule week
	 * @param orders
	 *            List of orders
	 * @return A list containing return messages
	 * @throws XmlPullParserException
	 * @throws IOException
	 * @throws HttpResponseException
	 * 
	 * @see #updateSchedule(String, int, int, List)
	 */
	public Schedule createSchedule(String userName, int year, int week,
			List<ZEORDER> orders) throws HttpResponseException, IOException,
			XmlPullParserException {

		// Create request
		SoapObject request = new SoapObject(NAMESPACE, Z_PM_AP_CREATE_SCHEDULE);

		@SuppressWarnings("unchecked")
		Vector<SoapObject> response = (Vector<SoapObject>) prepareSchedule(
				userName, year, week, orders, request);

		Schedule schedule = getSingleSchedule(response.get(0));

		if (schedule != null) {
			dbManager.insertScheduleDetail(schedule.getID_PROGRAM(), orders);
		}

		return schedule;

	}

	/**
	 * 
	 * @param userName
	 *            User name
	 * @param year
	 *            Schedule year
	 * @param week
	 *            Schedule week
	 * @param schedule
	 *            List of orders
	 * @param oldSchedule
	 * @return A list containing return messages
	 * @throws XmlPullParserException
	 * @throws IOException
	 * @throws HttpResponseException
	 * 
	 * @see #createSchedule(String, int, int, List)
	 */
	public List<Message> updateSchedule(String userName, int year, int week,
			List<ZEORDER> schedule) throws HttpResponseException, IOException,
			XmlPullParserException {

		// Create request
		SoapObject request = new SoapObject(NAMESPACE, Z_PM_AP_UPDATE_SCHEDULE);

		SoapObject response = (SoapObject) prepareSchedule(userName, year,
				week, schedule, request);

		List<Message> messagelist = null;
		messagelist = getMessageList(response);

		dbManager.updateSchedule(schedule, year, week);

		return messagelist;
	}

	/**
	 * 
	 * @param userName
	 *            User name
	 * @param year
	 *            Year
	 * @param week
	 *            Week
	 * @param orders
	 *            List of orders
	 * @param request
	 *            SoapObject output request
	 * @return A list containing the return messages
	 * @throws HttpResponseException
	 * @throws IOException
	 * @throws XmlPullParserException
	 */

	private Object prepareSchedule(String userName, int year, int week,
			List<ZEORDER> orders, SoapObject request)
			throws HttpResponseException, IOException, XmlPullParserException {

		String idProgram = "" + year + (week < 10 ? "0" : "") + week;

		SoapObject itOrders = new SoapObject();

		for (ZEORDER order : orders) {
			SoapObject item = new SoapObject();
			item.addProperty(OrdersTable.AUFNR, order.getAUFNR());
			itOrders.addProperty("item", item);
		}

		request.addProperty("IT_ORDERS", itOrders);
		request.addProperty(P_PROGRAM, idProgram);
		request.addProperty(P_USER, userName);

		// Create envelope
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		// envelope.dotNet = true;
		// Set output SOAP object
		envelope.setOutputSoapObject(request);
		// Create HTTP call object
		HttpTransportBasicAuth httpTrandport = new HttpTransportBasicAuth(URL2,
				SAP_USER, SAP_PASSWORD);

		// httpTrandport.debug = true;
		// Involve web service

		httpTrandport.call(SOAP_ACTION, envelope);
		Object response = envelope.getResponse();
		return response;
	}

	/**
	 * 
	 * @param userName
	 *            User name
	 * @param idProgram
	 *            Program ID
	 * @return Schedule details and a message list
	 * @throws HttpResponseException
	 * @throws IOException
	 * @throws XmlPullParserException
	 */
	public ZEMYPROGRAM getScheduleDetail(String userName, String idProgram)
			throws HttpResponseException, IOException, XmlPullParserException {
		// int year, int week
		// String idProgram = "" + year + (week < 10 ? "0" : "") + week;

		// Create request
		SoapObject request = new SoapObject(NAMESPACE,
				Z_PM_AP_GET_SCHEDULE_DETAIL);

		request.addProperty(P_PROGRAM, idProgram);
		request.addProperty(P_USER, userName);

		// Create envelope
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		// envelope.dotNet = true;
		// Set output SOAP object
		envelope.setOutputSoapObject(request);
		// Create HTTP call object
		HttpTransportBasicAuth httpTransport = new HttpTransportBasicAuth(URL2,
				SAP_USER, SAP_PASSWORD);
		httpTransport.debug = true;

		ZEMYPROGRAM zProgram = new ZEMYPROGRAM();
		// Involve web service
		httpTransport.call(SOAP_ACTION, envelope);
		// Get the response
		@SuppressWarnings("unchecked")
		Vector<SoapObject> response = (Vector<SoapObject>) envelope
				.getResponse();

		SoapObject ordersSoap = response.get(0);

		zProgram.setORDERS(getOrderNumbers(ordersSoap));

		dbManager.insertScheduleDetail(idProgram, zProgram.getORDERS());

		return zProgram;

	}

	private List<ZEORDER> getOrderNumbers(SoapObject ordersSoap) {
		List<ZEORDER> orders = new ArrayList<ZEORDER>();

		int count = ordersSoap.getPropertyCount();

		for (int i = 0; i < count; i++) {
			SoapObject item = (SoapObject) ordersSoap.getProperty(i);
			ZEORDER order = new ZEORDER();

			order.setAUFNR(parseResult(item
					.getPropertyAsString(OrdersTable.AUFNR)));

			orders.add(order);
		}

		return orders;
	}

	/**
	 * 
	 * @param userName
	 *            User name
	 * @return A list containing schedules
	 * @throws HttpResponseException
	 * @throws IOException
	 * @throws XmlPullParserException
	 */
	public List<Schedule> getScheduleList(String userName)
			throws HttpResponseException, IOException, XmlPullParserException {
		// int year, int week
		// String idProgram = "" + year + (week < 10 ? "0" : "") + week;

		// Create request
		SoapObject request = new SoapObject(NAMESPACE,
				Z_PM_AP_GET_SCHEDULE_LIST);

		request.addProperty(P_USER, userName);

		// Create envelope
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		// envelope.dotNet = true;
		// Set output SOAP object
		envelope.setOutputSoapObject(request);
		// Create HTTP call object
		HttpTransportBasicAuth httpTransport = new HttpTransportBasicAuth(URL2,
				SAP_USER, SAP_PASSWORD);
		httpTransport.debug = true;

		List<Schedule> scheduleList = new ArrayList<Schedule>();

		// Involve web service

		httpTransport.call(SOAP_ACTION, envelope);

		// Get the response
		@SuppressWarnings("unchecked")
		Vector<SoapObject> response = (Vector<SoapObject>) envelope
				.getResponse();

		// SoapObject errorSoap = response.get(1);
		// List<Message> errors = getMessageList(errorSoap);

		// Este es el código anterior con la función Z_PM_CALENDAR
		SoapObject scheduleSoap = response.get(1);

		int count = scheduleSoap.getPropertyCount();

		for (int i = 0; i < count; i++) {
			SoapObject item = (SoapObject) scheduleSoap.getProperty(i);
			Schedule schedule = getSingleSchedule(item);
			dbManager.insertSchedule(schedule);
			scheduleList.add(schedule);
		}

		return scheduleList;

	}

	private Schedule getSingleSchedule(SoapObject item) {
		Schedule schedule = null;

		if (item.getPropertyCount() > 0
				&& parseResult(item.getPropertyAsString(ScheduleTable.STATUS)) != null) {
			schedule = new Schedule();
			schedule.setSTATUS(parseResult(item
					.getPropertyAsString(ScheduleTable.STATUS)));
			schedule.setID_PROGRAM(parseResult(item
					.getPropertyAsString(ScheduleTable.ID_PROGRAM)));
			schedule.setCREATE_DATE(parseResult(item
					.getPropertyAsString(ScheduleTable.CREATE_DATE)));
		}
		return schedule;
	}

	/**
	 * 
	 * @param order
	 *            Order number
	 * @return Order details
	 * @throws XmlPullParserException
	 * @throw IOException
	 * @throw HttpResponseException
	 */
	public ZOrderDetails getOrderDetails(String order)
			throws HttpResponseException, IOException, XmlPullParserException {

		// Create request
		SoapObject request = new SoapObject(NAMESPACE, Z_PM_AP_GET_ORDER_DETAIL);

		request.addProperty("P_ORDER_NUMBER", order);
		// Create envelope

		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		// envelope.dotNet = true;
		// Set output SOAP object
		envelope.setOutputSoapObject(request);
		// Create HTTP call object
		HttpTransportBasicAuth httpTransport = new HttpTransportBasicAuth(URL2,
				SAP_USER, SAP_PASSWORD);

		httpTransport.debug = true;

		ZOrderDetails zOrder = new ZOrderDetails();
		// Involve web service
		httpTransport.call(SOAP_ACTION, envelope);
		// Get the response
		@SuppressWarnings("unchecked")
		Vector<SoapObject> response = (Vector<SoapObject>) envelope
				.getResponse();

		SoapObject errorSoap = response.get(5);
		List<Message> errors = getMessageList(errorSoap);

		zOrder.setErrors(errors);

		if (errors.size() > 0) {
			return zOrder;
		}

		SoapObject soapComponents = (SoapObject) response.get(0);

		zOrder.setComponents(getComponents(soapComponents));

		SoapObject headerSoap = response.get(1);

		zOrder.setHeader(getHeader(headerSoap));

		SoapObject equipmentSoap = response.get(2);

		zOrder.setEquipments(getEquipments(equipmentSoap));

		SoapObject soapOperations = response.get(3);

		zOrder.setOperations(getOperations(soapOperations));

		SoapObject parnersSoap = response.get(4);
		zOrder.setPartners(getPartners(parnersSoap));

		return zOrder;

	}

	/**
	 * 
	 * @param parnersSoap
	 *            SoapObject containig partners information
	 * @return A {@link List} {@link ZEPARTNER}
	 */
	private List<ZEPARTNER> getPartners(SoapObject parnersSoap) {
		int count = parnersSoap.getPropertyCount();
		List<ZEPARTNER> partners = new ArrayList<ZEPARTNER>();

		for (int i = 0; i < count; i++) {
			SoapObject item = (SoapObject) parnersSoap.getProperty(i);
			ZEPARTNER partner = new ZEPARTNER();
			partner.setPARTN_ROLE(parseResult(item.getProperty(
					PartnerTable.PARTN_ROLE).toString()));
			partner.setROL_TEXT(parseResult(item.getProperty(
					PartnerTable.ROL_TEXT).toString()));
			partner.setPARTNER(parseResult(item
					.getProperty(OrdersTable.PARTNER).toString()));
			partner.setNAME(parseResult(item.getProperty(PartnerTable.NAME)
					.toString()));
			partner.setADDRESS(parseResult(item.getProperty(
					PartnerTable.ADDRESS).toString()));

			partners.add(partner);
		}

		return partners;
	}

	/**
	 * 
	 * @param soapOperations
	 * @return
	 */
	private List<ZEOPERATION_ORDER> getOperations(SoapObject soapOperations) {
		int count = soapOperations.getPropertyCount();
		List<ZEOPERATION_ORDER> operations = new ArrayList<ZEOPERATION_ORDER>();

		for (int i = 0; i < count; i++) {
			SoapObject item = (SoapObject) soapOperations.getProperty(i);
			ZEOPERATION_ORDER operation = new ZEOPERATION_ORDER();
			operation.setACTIVITY(parseResult(item.getProperty(
					OperationTable.ACTIVITY).toString()));
			operation.setDESCRIPTION(parseResult(item.getProperty(
					OperationTable.DESCRIPTION).toString()));
			operation.setDURATION_NORMAL(parseResult(item.getProperty(
					OperationTable.DURATION_NORMAL).toString()));
			operation.setDURATION_NORMAL_UNIT(parseResult(item.getProperty(
					OperationTable.DURATION_NORMAL_UNIT).toString()));
			operation.setWORK_CNTR(parseResult(item.getProperty(
					OperationTable.WORK_CNTR).toString()));

			operations.add(operation);
		}
		return operations;

	}

	/**
	 * 
	 * @param equipmentSoap
	 * @return
	 */
	private List<ZEOBJECT_ORDER> getEquipments(SoapObject equipmentSoap) {
		int count = equipmentSoap.getPropertyCount();
		List<ZEOBJECT_ORDER> equipments = new ArrayList<ZEOBJECT_ORDER>();

		for (int i = 0; i < count; i++) {
			SoapObject item = (SoapObject) equipmentSoap.getProperty(i);
			ZEOBJECT_ORDER equipment = new ZEOBJECT_ORDER();
			equipment
					.setEQKTX(parseResult(item.getProperty("EQKTX").toString()));
			equipment
					.setEQUNR(parseResult(item.getProperty("EQUNR").toString()));
			equipments.add(equipment);
		}
		return equipments;
	}

	/**
	 * 
	 * @param headerSoap
	 * @return
	 */
	private List<ZEHEADER_ORDER> getHeader(SoapObject headerSoap) {
		int count = headerSoap.getPropertyCount();
		List<ZEHEADER_ORDER> headers = new ArrayList<ZEHEADER_ORDER>();
		for (int i = 0; i < count; i++) {
			SoapObject item = (SoapObject) headerSoap.getProperty(i);
			ZEHEADER_ORDER header = new ZEHEADER_ORDER();
			header.setEQUIPMENT(parseResult(item.getProperty(
					HeaderOrderTable.EQUIPMENT).toString()));
			header.setMN_WKCTR_ID(parseResult(item.getProperty(
					HeaderOrderTable.MN_WKCTR_ID).toString()));
			header.setPLANGROUP(parseResult(item.getProperty(
					HeaderOrderTable.PLANGROUP).toString()));
			header.setNOTIF_NO(parseResult(item.getProperty(
					HeaderOrderTable.NOTIF_NO).toString()));

			headers.add(header);
		}
		return headers;
	}

	/**
	 * 
	 * @param soapComponents
	 * @return
	 */
	private List<ZECOMPONENTS_ORDER> getComponents(SoapObject soapComponents) {

		List<ZECOMPONENTS_ORDER> components = new ArrayList<ZECOMPONENTS_ORDER>();

		// get components
		for (int i = 0; i < soapComponents.getPropertyCount(); i++) {
			SoapObject item = (SoapObject) soapComponents.getProperty(i);
			ZECOMPONENTS_ORDER component = new ZECOMPONENTS_ORDER();

			component.setACTIVITY(parseResult(item.getProperty(
					OperationTable.ACTIVITY).toString()));
			component.setMATERIAL(parseResult(item.getProperty(
					ComponentTable.MATERIAL).toString()));
			component.setMATL_DESC(parseResult(item.getProperty(
					ComponentTable.MATL_DESC).toString()));
			component.setREQUIREMENT_QUANTITY(item.getProperty(
					ComponentTable.REQUIREMENT_QUANTITY).toString());
			component.setREQUIREMENT_QUANTITY_UNIT(parseResult(item
					.getProperty(ComponentTable.REQUIREMENT_QUANTITY_UNIT)
					.toString()));

			components.add(component);
		}
		return components;
	}

	/**
	 * 
	 * @param UserName
	 *            User name
	 * @param year
	 *            Year Program
	 * @param week
	 *            Week program
	 * @return A list of Visists for the passed program
	 * @throws XmlPullParserException
	 * @throws IOException
	 * @throws HttpResponseException
	 */
	@SuppressWarnings("unchecked")
	public List<Visit> getVisitList(String UserName, int year, int week)
			throws HttpResponseException, IOException, XmlPullParserException {
		String id = getIDProgram(year, week);
		SoapObject request = new SoapObject(NAMESPACE, Z_PM_AP_GET_VISIT_LIST);

		request.addProperty(P_PROGRAM, id);
		request.addProperty(P_USER, UserName);

		HttpTransportBasicAuth transport = new HttpTransportBasicAuth(URL2,
				SAP_USER, SAP_PASSWORD);

		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);

		envelope.setOutputSoapObject(request);

		transport.call(SOAP_ACTION, envelope);

		Vector<SoapObject> response = (Vector<SoapObject>) envelope
				.getResponse();

		List<Visit> visitList = getVisits(response.get(1));

		return visitList;
	}

	/**
	 * 
	 * @param soapObject
	 *            {@link SoapObject} with Visists inside
	 * @return A list of Vistis
	 */
	private List<Visit> getVisits(SoapObject soapObject) {
		List<Visit> visits = new ArrayList<Visit>();
		int count = soapObject.getPropertyCount();

		for (int i = 0; i < count; i++) {
			SoapObject item = (SoapObject) soapObject.getProperty(i);
			Visit visit = new Visit();
			visit.setID_VISIT((long) parseNumericResult(item
					.getPropertyAsString("ID_VISIT")));
			visit.setID_PROGRAM(parseResult(item
					.getPropertyAsString(ScheduleTable.ID_PROGRAM)));
			visit.setZUSER(parseResult(item.getPropertyAsString("ZUSER")));
			visit.setFINI(parseResult(item.getPropertyAsString("FINI")));
			visit.setHINI(parseResult(item.getPropertyAsString("HINI")));
			visit.setTINI(parseBitResult(item.getPropertyAsString("TINI")));
			visit.setFFIN(parseResult(item.getPropertyAsString("FFIN")));
			visit.setHFIN(parseResult(item.getPropertyAsString("HFIN")));
			visit.setTFIN(parseBitResult(item.getPropertyAsString("TFIN")));
			visit.setSTATUS(parseBitResult(item
					.getPropertyAsString(ScheduleTable.STATUS)));
			visit.setCOMENTARIO(parseResult(item
					.getPropertyAsString("COMENTARIO")));
			visit.setID_JUSTIFICATION(parseResult(item
					.getPropertyAsString("ID_JUSTIFICATION")));

			visits.add(visit);
		}

		return visits;
	}

	public String createVisit(Visit visit) throws HttpResponseException,
			IOException, XmlPullParserException {

		SoapObject request = new SoapObject(NAMESPACE, Z_PM_AP_GET_VISIT_LIST);

		request.addProperty(FINI, visit.getFINI());
		request.addProperty(HINI, visit.getHINI());
		request.addProperty(TINI, visit.getTINI() == 1 ? "X" : "");
		request.addProperty(P_PROGRAM, visit.getID_PROGRAM());
		request.addProperty(P_USER, visit.getZUSER());

		HttpTransportBasicAuth transport = new HttpTransportBasicAuth(URL2,
				SAP_USER, SAP_PASSWORD);

		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);

		envelope.setOutputSoapObject(request);

		transport.call(SOAP_ACTION, envelope);

		@SuppressWarnings("unchecked")
		Vector<Object> response = (Vector<Object>) envelope.getResponse();

		@SuppressWarnings("unused")
		List<Message> messageList = getMessageList((SoapObject) response.get(0));

		String idVisit = parseResult(response.get(1).toString());

		return idVisit;
	}

	private String getIDProgram(int year, int week) {
		return "" + year + (week < 10 ? "0" : "") + week;
	}

	public List<Message> Confirmation(
			ArrayList<IT_CONFIRMATION> listCONFIRMATION,
			ArrayList<IT_OPERATIONS> listOPERATIONS, String N_Order)
			throws Exception {

		int mayor = 0;

		if (listCONFIRMATION.size() > listOPERATIONS.size()) {
			mayor = listCONFIRMATION.size();
		} else {
			mayor = listOPERATIONS.size();
		}

		SoapObject tab = new SoapObject();
		SoapObject tab2 = new SoapObject();

		for (int i = 0; i < mayor; i++) {
			SoapObject item1 = new SoapObject();
			SoapObject item2 = new SoapObject();

			item1.addProperty(OperationTable.ACTIVITY, listCONFIRMATION.get(i)
					.getACTIVITY());
			item1.addProperty("ACTUAL_DUR", listCONFIRMATION.get(i)
					.getACTUAL_DUR());
			item1.addProperty("UN_ACT_DUR", listCONFIRMATION.get(i)
					.getUN_ACT_DUR());
			item1.addProperty("UN_ACT_DUR_ISO", listCONFIRMATION.get(i)
					.getUN_ACT_DUR_ISO());
			item1.addProperty("EXEC_START_DATE", listCONFIRMATION.get(i)
					.getEXEC_START_DATE());
			item1.addProperty("EXEC_FIN_DATE", listCONFIRMATION.get(i)
					.getEXEC_FIN_DATE());
			item1.addProperty("EXEC_START_TIME", listCONFIRMATION.get(i)
					.getEXEC_START_TIME());
			item1.addProperty("EXEC_FIN_TIME", listCONFIRMATION.get(i)
					.getEXEC_FIN_TIME());
			item1.addProperty("CONF_TEXT", listCONFIRMATION.get(i)
					.getCONF_TEXT());
			item1.addProperty(OperationTable.COMPLETE, listCONFIRMATION.get(i)
					.getCOMPLETE());
			// Operations
			item2.addProperty(OperationTable.ACTIVITY, listOPERATIONS.get(i)
					.getACTIVITY());
			item2.addProperty(OperationTable.WORK_CNTR, listOPERATIONS.get(i)
					.getWORK_CNTR());
			item2.addProperty(OperationTable.DESCRIPTION, listOPERATIONS.get(i)
					.getDESCRIPTION());
			item2.addProperty(OperationTable.CONF_NO, listOPERATIONS.get(i)
					.getCONF_NO());
			item2.addProperty(OperationTable.PLANT, listOPERATIONS.get(i)
					.getPLANT());
			item2.addProperty(OperationTable.DURATION_NORMAL, listOPERATIONS
					.get(i).getDURATION_NORMAL());
			item2.addProperty(OperationTable.DURATION_NORMAL_UNIT,
					listOPERATIONS.get(i).getDURATION_NORMAL_UNIT());
			item2.addProperty(OperationTable.DURATION_NORMAL_UNIT_ISO,
					listOPERATIONS.get(i).getDURATION_NORMAL_UNIT_ISO());
			item2.addProperty(OperationTable.COMPLETE, listOPERATIONS.get(i)
					.getCOMPLETE());

			tab.addProperty("item", item1);
			tab2.addProperty("item", item2);
		}

		// Create request
		SoapObject request = new SoapObject(NAMESPACE, CONFIRMATION);
		request.addProperty("IT_CONFIRMATION", tab);
		request.addProperty("IT_OPERATIONS", tab2);
		request.addProperty("P_NO_ORDER", N_Order);

		// Create envelope
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		// envelope.dotNet = true;
		// Set output SOAP object
		envelope.setOutputSoapObject(request);

		// Create HTTP call object
		HttpTransportBasicAuth httpTransport = new HttpTransportBasicAuth(URL,
				SAP_USER, SAP_PASSWORD);

		httpTransport.debug = true;

		httpTransport.call(SOAP_ACTION, envelope);
		// Get the response

		SoapObject response3 = (SoapObject) envelope.getResponse();
		List<Message> errors = getMessageList(response3);

		return errors;

	}

	private String parseResult(String result) {
		if (anyType.equalsIgnoreCase(result)) {
			return null;
		} else {
			return result;
		}
	}

	private double parseNumericResult(String result) {
		if (anyType.equalsIgnoreCase(result)) {
			return 0;
		} else {
			return Double.valueOf(result);
		}
	}

	public List<Message> deleteSchedule(String userName, int year, int week)
			throws HttpResponseException, IOException, XmlPullParserException {

		String idProgram = "" + year + (week < 10 ? "0" : "") + week;
		SoapObject request = new SoapObject(NAMESPACE, Z_PM_AP_DELETE_SCHEDULE);

		request.addProperty(P_PROGRAM, idProgram);
		request.addProperty(P_USER, userName);

		HttpTransportBasicAuth transport = new HttpTransportBasicAuth(URL2,
				SAP_USER, SAP_PASSWORD);

		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapSerializationEnvelope.VER11);

		envelope.setOutputSoapObject(request);
		List<Message> messages = null;

		transport.call(SOAP_ACTION, envelope);
		SoapObject response = (SoapObject) envelope.getResponse();

		messages = getMessageList(response);

		if (messages.size() > 0 && messages.get(0).getType() == 'S') {
			dbManager.deleteSchedule(year, week);
		}

		return messages;

	}
}
