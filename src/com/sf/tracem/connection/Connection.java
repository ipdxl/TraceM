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

import com.sf.tracem.db.DBManager;
import com.sf.tracem.db.TraceMOpenHelper;
import com.tracem.connection.MeasurementPoint;

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
	private static final String Z_PM_AP_CONFIRMATION = "Z_PM_ORDER_CONFIRMATION";
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
	private static final String P_ORDER_NUMBER = "P_ORDER_NUMBER";
	private static final String ITEM = "item";
	private static final String Z_PM_AP_GET_MEASURE_POINT = "Z_PM_AP_GET_MEASURE_POINT";
	private static final String P_EQUIPMENT = "P_EQUIPMENT";
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
		login.setPlant(((SoapPrimitive) response.get(3)).toString());
		login.setRol(((SoapPrimitive) response.get(4)).toString());

		return login;

	}

	/**
	 * 
	 * @param userName
	 * @return A Message List
	 * @throws HttpResponseException
	 * @throws IOException
	 * @throws XmlPullParserException
	 */
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
					.getPropertyAsString(Menu.ID_MENU)));
			menu.setIdFather((byte) parseNumericResult(item
					.getPropertyAsString(Menu.ID_FATHER)));

			menuList.add(menu);
		}

		return menuList;
	}

	/**
	 * 
	 * @param user
	 * @return
	 * @throws Exception
	 */
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
	private List<Order> getListOrders(SoapObject listSoap) {
		int count = listSoap.getPropertyCount();
		List<Order> orders = new ArrayList<Order>();

		Order order;
		for (int i = 0; i < count; i++) {
			SoapObject item = (SoapObject) listSoap.getProperty(i);
			order = new Order();
			order.setAUFART(parseResult(item.getProperty(Order.AUFART)
					.toString()));
			order.setAUFNR(parseResult(item.getProperty(Order.AUFNR).toString()));
			order.setAUFTEXT(parseResult(item.getProperty(Order.AUFTEXT)
					.toString()));
			order.setCO_GSTRP(parseResult(item.getProperty(Order.CO_GSTRP)
					.toString()));
			order.setPARTNER(parseResult(item.getProperty(Order.PARTNER)
					.toString()));
			order.setADDRESS(parseResult(item.getProperty(Partner.ADDRESS)
					.toString()));

			order.setORDER_STATUS((int) parseNumericResult(item.getProperty(
					Order.ORDER_STATUS).toString()));
			order.setEXP_DAYS(parseResult(item.getProperty(Order.EXP_DAYS)
					.toString()));
			order.setEXP_STATUS(parseResult(item.getProperty(Order.EXP_STATUS)
					.toString()));
			order.setZHOURS((float) parseNumericResult(item.getProperty(
					Order.ZHOURS).toString()));
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
	public List<Order> getPlan(String UserName) throws Exception {
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
	public List<Order> getPlan(String[] users) throws HttpResponseException,
			IOException, XmlPullParserException {

		// Create request
		SoapObject request = new SoapObject(NAMESPACE, Z_PM_AP_GET_PLAN);

		SoapObject userslist = new SoapObject();

		for (String user : users) {
			SoapObject item = new SoapObject();
			item.addProperty("ZUSER", user);
			userslist.addProperty(ITEM, item);
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
		List<Order> zOrders = null;
		// Involve web service

		httpTrandport.call(SOAP_ACTION, envelope);
		response = (Vector<SoapObject>) envelope.getResponse();
		SoapObject soapOrder = (SoapObject) response.get(0);
		zOrders = getListOrders(soapOrder);

		// Clear database
		new TraceMOpenHelper(context).clearData();

		dbManager.insertOrders(zOrders);

		for (Order order : zOrders) {
			getOrderDetails(order.getAUFNR());
		}

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
			List<Order> orders) throws HttpResponseException, IOException,
			XmlPullParserException {

		// Create request
		SoapObject request = new SoapObject(NAMESPACE, Z_PM_AP_CREATE_SCHEDULE);

		@SuppressWarnings("unchecked")
		Vector<SoapObject> response = (Vector<SoapObject>) prepareSchedule(
				userName, year, week, orders, request);

		Schedule schedule = getSingleSchedule(response.get(1));

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
			List<Order> schedule) throws HttpResponseException, IOException,
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
			List<Order> orders, SoapObject request)
			throws HttpResponseException, IOException, XmlPullParserException {

		String idProgram = "" + year + (week < 10 ? "0" : "") + week;

		SoapObject itOrders = new SoapObject();

		for (Order order : orders) {
			SoapObject item = new SoapObject();
			item.addProperty(Order.AUFNR, order.getAUFNR());
			itOrders.addProperty(ITEM, item);
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
	public List<Order> getScheduleDetail(String userName, String idProgram)
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

		List<Order> orders = new ArrayList<Order>();
		// Involve web service
		httpTransport.call(SOAP_ACTION, envelope);
		// Get the response
		@SuppressWarnings("unchecked")
		Vector<SoapObject> response = (Vector<SoapObject>) envelope
				.getResponse();

		SoapObject ordersSoap = response.get(0);

		orders = (getOrderNumbers(ordersSoap));

		dbManager.insertScheduleDetail(idProgram, orders);

		return orders;

	}

	/**
	 * 
	 * @param ordersSoap
	 * @return An {@link Order} {@link List}
	 */
	private List<Order> getOrderNumbers(SoapObject ordersSoap) {
		List<Order> orders = new ArrayList<Order>();

		int count = ordersSoap.getPropertyCount();

		for (int i = 0; i < count; i++) {
			SoapObject item = (SoapObject) ordersSoap.getProperty(i);
			Order order = new Order();

			order.setAUFNR(parseResult(item.getPropertyAsString(Order.AUFNR)));

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

		// Este es el c�digo anterior con la funci�n Z_PM_CALENDAR
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

	/**
	 * 
	 * @param item
	 * @return A single Schedule instance
	 */
	private Schedule getSingleSchedule(SoapObject item) {
		Schedule schedule = null;

		if (item.getPropertyCount() > 0
				&& parseResult(item.getPropertyAsString(Schedule.STATUS)) != null) {
			schedule = new Schedule();
			schedule.setSTATUS(parseResult(item
					.getPropertyAsString(Schedule.STATUS)));
			schedule.setID_PROGRAM(parseResult(item
					.getPropertyAsString(Schedule.ID_PROGRAM)));
			schedule.setCREATE_DATE(parseResult(item
					.getPropertyAsString(Schedule.CREATE_DATE)));
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
	public OrderDetails getOrderDetails(String order)
			throws HttpResponseException, IOException, XmlPullParserException {

		// Create request
		SoapObject request = new SoapObject(NAMESPACE, Z_PM_AP_GET_ORDER_DETAIL);

		request.addProperty(P_ORDER_NUMBER, order);
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

		OrderDetails zOrder = new OrderDetails();
		// Involve web service
		httpTransport.call(SOAP_ACTION, envelope);
		// Get the response
		@SuppressWarnings("unchecked")
		Vector<SoapObject> response = (Vector<SoapObject>) envelope
				.getResponse();

		SoapObject errorSoap = response.get(5);
		List<Message> errors = getMessageList(errorSoap);

		zOrder.setMessages(errors);

		if (errors.size() > 0) {
			return zOrder;
		}

		SoapObject soapComponents = (SoapObject) response.get(0);

		zOrder.setComponents(getComponents(soapComponents));

		SoapObject headerSoap = response.get(1);

		zOrder.setHeader(getHeader(order, headerSoap));

		SoapObject equipmentSoap = response.get(2);

		zOrder.setEquipments(getEquipments(equipmentSoap));

		SoapObject soapOperations = response.get(3);

		zOrder.setOperations(getOperations(soapOperations));

		SoapObject parnersSoap = response.get(4);
		zOrder.setPartners(getPartners(parnersSoap));

		dbManager.insertHeader(zOrder.getHeader());
		dbManager.insertEquipment(order, zOrder.getEquipments());
		try {
			for (Equipment eq : zOrder.getEquipments()) {
				getMeasurePoints(eq.getEQUNR());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		dbManager.insertOperations(order, zOrder.getOperations());
		dbManager.insertComponents(order, zOrder.getComponents());

		return zOrder;

	}

	/**
	 * 
	 * @param parnersSoap
	 *            SoapObject containig partners information
	 * @return A {@link List} {@link Partner}
	 */
	private List<Partner> getPartners(SoapObject parnersSoap) {
		int count = parnersSoap.getPropertyCount();
		List<Partner> partners = new ArrayList<Partner>();

		for (int i = 0; i < count; i++) {
			SoapObject item = (SoapObject) parnersSoap.getProperty(i);
			Partner partner = new Partner();
			partner.setPARTN_ROLE(parseResult(item.getProperty(
					Partner.PARTN_ROLE).toString()));
			partner.setROL_TEXT(parseResult(item.getProperty(Partner.ROL_TEXT)
					.toString()));
			partner.setPARTNER(parseResult(item.getProperty(Order.PARTNER)
					.toString()));
			partner.setNAME(parseResult(item.getProperty(Partner.NAME)
					.toString()));
			partner.setADDRESS(parseResult(item.getProperty(Partner.ADDRESS)
					.toString()));

			partners.add(partner);
		}

		return partners;
	}

	/**
	 * 
	 * @param soapOperations
	 * @return
	 */
	private List<Operation> getOperations(SoapObject soapOperations) {
		int count = soapOperations.getPropertyCount();
		List<Operation> operations = new ArrayList<Operation>();

		for (int i = 0; i < count; i++) {
			SoapObject item = (SoapObject) soapOperations.getProperty(i);

			Operation operation = new Operation();
			operation.setACTIVITY(parseResult(item.getProperty(
					Operation.ACTIVITY).toString()));
			operation.setDESCRIPTION(parseResult(item.getProperty(
					Operation.DESCRIPTION).toString()));
			operation.setDURATION_NORMAL(parseResult(item.getProperty(
					Operation.DURATION_NORMAL).toString()));
			operation.setDURATION_NORMAL_UNIT(parseResult(item.getProperty(
					Operation.DURATION_NORMAL_UNIT).toString()));
			operation.setWORK_CNTR(parseResult(item.getProperty(
					Operation.WORK_CNTR).toString()));
			operation.setCOMPLETE(parseBitResult(item.getProperty(
					Operation.COMPLETE).toString()));

			operations.add(operation);
		}
		return operations;

	}

	/**
	 * 
	 * @param equipmentSoap
	 * @return
	 */
	private List<Equipment> getEquipments(SoapObject equipmentSoap) {
		int count = equipmentSoap.getPropertyCount();
		List<Equipment> equipments = new ArrayList<Equipment>();

		for (int i = 0; i < count; i++) {
			SoapObject item = (SoapObject) equipmentSoap.getProperty(i);
			Equipment equipment = new Equipment();
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
	private List<HeaderOrder> getHeader(String aufnr, SoapObject headerSoap) {
		int count = headerSoap.getPropertyCount();
		List<HeaderOrder> headers = new ArrayList<HeaderOrder>();
		for (int i = 0; i < count; i++) {
			SoapObject item = (SoapObject) headerSoap.getProperty(i);

			HeaderOrder header = new HeaderOrder();

			header.setAUFNR(aufnr);
			header.setEQUIPMENT(parseResult(item.getProperty(
					HeaderOrder.EQUIPMENT).toString()));
			header.setMN_WKCTR_ID(parseResult(item.getProperty(
					HeaderOrder.MN_WKCTR_ID).toString()));
			header.setPLANGROUP(parseResult(item.getProperty(
					HeaderOrder.PLANGROUP).toString()));
			header.setNOTIF_NO(parseResult(item.getProperty(
					HeaderOrder.NOTIF_NO).toString()));

			headers.add(header);
		}
		return headers;
	}

	/**
	 * 
	 * @param soapComponents
	 * @return
	 */
	private List<Component> getComponents(SoapObject soapComponents) {

		List<Component> components = new ArrayList<Component>();

		// get components
		for (int i = 0; i < soapComponents.getPropertyCount(); i++) {
			SoapObject item = (SoapObject) soapComponents.getProperty(i);
			Component component = new Component();

			component.setACTIVITY(parseResult(item.getProperty(
					Operation.ACTIVITY).toString()));
			component.setMATERIAL(parseResult(item.getProperty(
					Component.MATERIAL).toString()));
			component.setMATL_DESC(parseResult(item.getProperty(
					Component.MATL_DESC).toString()));
			component.setREQUIREMENT_QUANTITY(item.getProperty(
					Component.REQUIREMENT_QUANTITY).toString());
			component.setREQUIREMENT_QUANTITY_UNIT(parseResult(item
					.getProperty(Component.REQUIREMENT_QUANTITY_UNIT)
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
					.getPropertyAsString(Schedule.ID_PROGRAM)));
			visit.setZUSER(parseResult(item.getPropertyAsString("ZUSER")));
			visit.setFINI(parseResult(item.getPropertyAsString("FINI")));
			visit.setHINI(parseResult(item.getPropertyAsString("HINI")));
			visit.setTINI(parseBitResult(item.getPropertyAsString("TINI")));
			visit.setFFIN(parseResult(item.getPropertyAsString("FFIN")));
			visit.setHFIN(parseResult(item.getPropertyAsString("HFIN")));
			visit.setTFIN(parseBitResult(item.getPropertyAsString("TFIN")));
			visit.setSTATUS(parseBitResult(item
					.getPropertyAsString(Schedule.STATUS)));
			visit.setCOMENTARIO(parseResult(item
					.getPropertyAsString("COMENTARIO")));
			visit.setID_JUSTIFICATION(parseResult(item
					.getPropertyAsString("ID_JUSTIFICATION")));

			visits.add(visit);
		}

		return visits;
	}

	/**
	 * 
	 * @param visit
	 * @return
	 * @throws HttpResponseException
	 * @throws IOException
	 * @throws XmlPullParserException
	 */
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

	/**
	 * 
	 * @param year
	 * @param week
	 * @return a {@link String} formatted as yyyymm
	 */
	private String getIDProgram(int year, int week) {
		return "" + year + (week < 10 ? "0" : "") + week;
	}

	public List<Message> Confirmation(List<Confirmation> confirmations,
			String aufnr) throws Exception {

		SoapObject itConfirmation = new SoapObject();

		for (Confirmation item : confirmations) {
			SoapObject confObject = new SoapObject();

			confObject.addProperty(Confirmation.ACTIVITY, item.getACTIVITY());
			confObject.addProperty(Confirmation.ACTUAL_DUR,
					item.getACTUAL_DUR());
			confObject.addProperty(Confirmation.UN_ACT_DUR,
					item.getUN_ACT_DUR());
			confObject.addProperty(Confirmation.EXEC_START_DATE,
					item.getEXEC_START_DATE());
			confObject.addProperty(Confirmation.EXEC_FIN_DATE,
					item.getEXEC_FIN_DATE());
			confObject.addProperty(Confirmation.EXEC_START_TIME,
					item.getEXEC_START_TIME());
			confObject.addProperty(Confirmation.EXEC_FIN_TIME,
					item.getEXEC_FIN_TIME());
			confObject.addProperty(Confirmation.CONF_TEXT, item.getCONF_TEXT());
			confObject.addProperty(Confirmation.COMPLETE,
					TraceMFormater.getBool(item.getCOMPLETE()));

			itConfirmation.addProperty(ITEM, confObject);
		}

		// Create request
		SoapObject request = new SoapObject(NAMESPACE, Z_PM_AP_CONFIRMATION);
		request.addProperty("IT_CONFIRMATION", itConfirmation);
		request.addProperty(P_ORDER_NUMBER, aufnr);

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

		SoapObject response = (SoapObject) envelope.getResponse();
		List<Message> errors = getMessageList(response);

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

	/**
	 * 
	 * @param equnr
	 *            {@link Equipment#EQUNR}
	 * @return A List with Measurement points
	 * @throws HttpResponseException
	 * @throws IOException
	 * @throws XmlPullParserException
	 */
	public List<MeasurementPoint> getMeasurePoints(String equnr)
			throws HttpResponseException, IOException, XmlPullParserException {

		SoapObject request = new SoapObject(NAMESPACE,
				Z_PM_AP_GET_MEASURE_POINT);

		request.addProperty(P_EQUIPMENT, equnr);

		HttpTransportBasicAuth transport = new HttpTransportBasicAuth(URL2,
				SAP_USER, SAP_PASSWORD);

		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapSerializationEnvelope.VER11);

		envelope.setOutputSoapObject(request);

		transport.call(SOAP_ACTION, envelope);

		@SuppressWarnings("unchecked")
		Vector<SoapObject> response = (Vector<SoapObject>) envelope
				.getResponse();

		SoapObject soapPoints = response.get(0);

		List<MeasurementPoint> points = new ArrayList<MeasurementPoint>();

		int count = soapPoints.getPropertyCount();
		for (int i = 0; i < count; i++) {
			SoapObject item = (SoapObject) soapPoints.getProperty(i);

			MeasurementPoint point = new MeasurementPoint();

			point.setEqunr(equnr);
			point.setDescription(parseResult(item
					.getPropertyAsString(MeasurementPoint.DESCRIPTION)));
			point.setUnit(parseResult(item
					.getPropertyAsString(MeasurementPoint.UNIT)));
			point.setPoint(parseResult(item
					.getPropertyAsString(MeasurementPoint.POINT)));

			points.add(point);
		}

		dbManager.insertMeasurementPoints(equnr, points);

		return points;
	}
}
