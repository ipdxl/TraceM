package com.sf.tracem.connection;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
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
import com.sf.tracem.db.UncommitedChanges;

public class Connection extends Activity {

	private final static String NAMESPACE = "urn:sap-com:document:sap:rfc:functions";
	private final static String URL2 = "http://25.111.9.178:8004/sap/bc/srt/rfc/sap/ZPM_AP_WS?sap-client=800";
	private final static String SAP_USER = "sapuser";
	private final static String SAP_PASSWORD = "password3";
	private final static String SOAP_ACTION = "";
	private final static String MY_EFFORT_WM = "Z_PM_EFFORT";
	private final static String Z_PM_AP_GET_PLAN = "Z_PM_AP_GET_PLAN";
	private final static String Z_PM_AP_GET_SCHEDULE_DETAIL = "Z_PM_AP_GET_SCHEDULE_DETAIL";
	private final static String Z_PM_AP_GET_ORDER_DETAIL = "Z_PM_AP_GET_ORDER_DETAIL";
	// private final static String Z_PM_LOG = "Z_PM_LOG";
	private static final String Z_PM_AP_CONFIRMATION = "Z_PM_AP_CONFIRMATION";
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
	private static final String DESCRIPTION = "DESCRIPTION";
	private static final String POINT = "POINT";
	private static final String ZREAD = "ZREAD";
	private static final String IT_READ_POINTS = "IT_READ_POINTS";
	private static final String Z_PM_AP_CREATE_VISIT = "Z_PM_AP_CREATE_VISIT";
	private static final String Z_PM_AP_CLOSE_VISIT = "Z_PM_AP_CLOSE_VISIT";
	private static final String P_VISIT = "P_VISIT";
	private static final String IT_CONFIRMATION = "IT_CONFIRMATION";
	private static final String Z_PM_AP_SAVE_MEASURE_DOCUEMNT = "Z_PM_AP_SAVE_MEASURE_DOCUEMNT";
	private static final String Z_PM_AP_GET_VISIT_DETAIL = "Z_PM_AP_GET_VISIT_DETAIL";
	private static final String Z_PM_AP_CLOSE_SCHEDULE = "Z_PM_AP_CLOSE_SCHEDULE";
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

		SoapSerializationEnvelope envelope = call(request);
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

		SoapSerializationEnvelope envelope = call(request);

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

		SoapSerializationEnvelope envelope = call(request);

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

		ZEMYEFFORT zEffort = new ZEMYEFFORT();
		// Involve web service

		SoapSerializationEnvelope envelope = call(request);
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

		SoapSerializationEnvelope envelope = call(request);

		// httpTrandport.debug = true;
		Vector<SoapObject> response = null;
		List<Order> zOrders = null;
		// Involve web service

		response = (Vector<SoapObject>) envelope.getResponse();
		SoapObject soapOrder = (SoapObject) response.get(0);
		zOrders = getListOrders(soapOrder);

		// Clear database
		new TraceMOpenHelper(context).clearData();

		dbManager.insertOrders(zOrders);

		for (Order order : zOrders) {
			getOrderDetails(order.getAUFNR());
		}

		zOrders = dbManager.getOrders();

		List<Schedule> schedules = getScheduleList(users[0]);
		for (Schedule schedule : schedules) {
			String id = schedule.getID_PROGRAM();
			getScheduleDetail(users[0], id);
			getVisitList(users[0], id);
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

		SoapSerializationEnvelope envelope = call(request);

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

		SoapSerializationEnvelope envelope = call(request);

		List<Order> orders = new ArrayList<Order>();
		// Involve web service
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

		SoapSerializationEnvelope envelope = call(request);

		List<Schedule> scheduleList = new ArrayList<Schedule>();

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
			schedule.setSTATUS((int) parseNumericResult(item
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
	 * @param aufnr
	 *            Order number
	 * @return Order details
	 * @throws XmlPullParserException
	 * @throw IOException
	 * @throw HttpResponseException
	 */
	public OrderDetails getOrderDetails(String aufnr)
			throws HttpResponseException, IOException, XmlPullParserException {

		// Create request
		SoapObject request = new SoapObject(NAMESPACE, Z_PM_AP_GET_ORDER_DETAIL);

		request.addProperty(P_ORDER_NUMBER, aufnr);

		SoapSerializationEnvelope envelope = call(request);

		OrderDetails zOrder = new OrderDetails();

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

		zOrder.setHeader(getHeader(aufnr, headerSoap));

		SoapObject equipmentSoap = response.get(2);

		zOrder.setEquipments(getEquipments(aufnr, equipmentSoap));

		SoapObject soapOperations = response.get(3);

		zOrder.setOperations(getOperations(aufnr, soapOperations));

		SoapObject parnersSoap = response.get(4);
		zOrder.setPartners(getPartners(parnersSoap));

		dbManager.insertHeader(zOrder.getHeader());
		dbManager.insertEquipment(aufnr, zOrder.getEquipments());
		try {
			for (Equipment eq : zOrder.getEquipments()) {
				getMeasurePoints(aufnr, eq.getEQUNR());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		dbManager.insertOperations(zOrder.getOperations());
		dbManager.insertComponents(aufnr, zOrder.getComponents());

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
	 * @param aufnr
	 * @param soapOperations
	 * @return
	 */
	private List<Operation> getOperations(String aufnr,
			SoapObject soapOperations) {
		int count = soapOperations.getPropertyCount();
		List<Operation> operations = new ArrayList<Operation>();

		for (int i = 0; i < count; i++) {
			SoapObject item = (SoapObject) soapOperations.getProperty(i);

			Operation operation = new Operation();
			operation.setACTIVITY(parseResult(item.getProperty(
					Operation.ACTIVITY).toString()));
			operation.setDESCRIPTION(parseResult(item.getProperty(
					Operation.DESCRIPTION).toString()));
			operation.setDURATION_NORMAL(parseNumericResult(item.getProperty(
					Operation.DURATION_NORMAL).toString()));
			operation.setDURATION_NORMAL_UNIT(parseResult(item.getProperty(
					Operation.DURATION_NORMAL_UNIT).toString()));
			operation.setWORK_CNTR(parseResult(item.getProperty(
					Operation.WORK_CNTR).toString()));
			operation.setCOMPLETE(parseBitResult(item.getProperty(
					Operation.COMPLETE).toString()));

			operation.setAufnr(aufnr);

			operations.add(operation);
		}
		return operations;

	}

	/**
	 * @param aufnr
	 * @param equipmentSoap
	 * @return
	 */
	private List<Equipment> getEquipments(String aufnr, SoapObject equipmentSoap) {
		int count = equipmentSoap.getPropertyCount();
		List<Equipment> equipments = new ArrayList<Equipment>();

		for (int i = 0; i < count; i++) {
			SoapObject item = (SoapObject) equipmentSoap.getProperty(i);
			Equipment equipment = new Equipment();

			equipment.setAufnr(aufnr);
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

			component.setACTIVITY((int) parseNumericResult(item.getProperty(
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
	 * @param userName
	 *            User name
	 * @param year
	 *            Year Program
	 * @param week
	 *            Week program
	 * @return A list of Visits for the passed program
	 * @throws XmlPullParserException
	 * @throws IOException
	 * @throws HttpResponseException
	 */
	@SuppressWarnings("unchecked")
	public List<Visit> getVisitList(String userName, String id)
			throws HttpResponseException, IOException, XmlPullParserException {
		SoapObject request = new SoapObject(NAMESPACE, Z_PM_AP_GET_VISIT_LIST);

		request.addProperty(P_PROGRAM, id);
		request.addProperty(P_USER, userName);

		SoapSerializationEnvelope envelope = call(request);

		Vector<SoapObject> response = (Vector<SoapObject>) envelope
				.getResponse();

		List<Visit> visitList = getVisits(response.get(1));

		dbManager.insertVisits(visitList);

		for (Visit visit : visitList) {
			visit.setUSER(userName);
			getVisitDetail(visit);
		}

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
					.getPropertyAsString(Visit.ID_VISIT)));
			visit.setID_PROGRAM(parseResult(item
					.getPropertyAsString(Visit.ID_PROGRAM)));
			visit.setUSER(parseResult(item.getPropertyAsString("ZUSER")));
			visit.setFINI(parseResult(item.getPropertyAsString(Visit.FINI)));
			visit.setHINI(parseResult(item.getPropertyAsString(Visit.HINI)));
			visit.setTINI(parseBitResult(item.getPropertyAsString(Visit.TINI)));
			visit.setFFIN(parseResult(item.getPropertyAsString(Visit.FFIN)));
			visit.setHFIN(parseResult(item.getPropertyAsString(Visit.HFIN)));
			visit.setTFIN(parseBitResult(item.getPropertyAsString(Visit.TFIN)));
			visit.setSTATUS(parseBitResult(item
					.getPropertyAsString(Visit.STATUS)));
			visit.setCOMENTARIO(parseResult(item
					.getPropertyAsString(Visit.COMENTARIO)));
			visit.setID_JUSTIFICATION(parseResult(item
					.getPropertyAsString(Visit.ID_JUSTIFICATION)));

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
	public int createVisit(Visit visit) throws HttpResponseException,
			IOException, XmlPullParserException {

		SoapObject request = new SoapObject(NAMESPACE, Z_PM_AP_CREATE_VISIT);

		request.addProperty(FINI, visit.getFINI());
		request.addProperty(HINI, visit.getHINI());
		request.addProperty(P_PROGRAM, visit.getID_PROGRAM());
		request.addProperty(P_USER, visit.getUSER());
		request.addProperty(TINI, visit.getTINI() == 1 ? "X" : "");

		SoapSerializationEnvelope envelope = call(request);

		@SuppressWarnings("unchecked")
		Vector<Object> response = (Vector<Object>) envelope.getResponse();

		@SuppressWarnings("unused")
		List<Message> messageList = getMessageList((SoapObject) response.get(1));

		int idVisit = (int) parseNumericResult(response.get(0).toString());

		if (idVisit != 0) {
			visit.setID_VISIT(idVisit);
			visit.setSTATUS((byte) 1);
			dbManager.insertVisit(visit);
		}

		return idVisit;
	}

	public boolean closeVisit(Visit visit) throws HttpResponseException,
			IOException, XmlPullParserException {
		List<Message> messageList = new ArrayList<Message>();

		UncommitedChanges uc = dbManager.getUncommitedChanges();
		messageList.addAll(Confirmation(visit, uc.getOperations()));
		for (Message m : messageList) {
			if ("E".equalsIgnoreCase("" + m.getType())) {
				return false;
			}
		}
		messageList.addAll(saveMeasureDoc(visit.getUSER(), uc.getMeasures()));

		for (Message m : messageList) {
			if ("E".equalsIgnoreCase("" + m.getType())) {
				return false;
			}
		}

		SoapObject request = new SoapObject(NAMESPACE, Z_PM_AP_CLOSE_VISIT);

		request.addProperty(Visit.FFIN, visit.getFFIN());
		request.addProperty(Visit.HFIN, visit.getHFIN());
		request.addProperty(P_PROGRAM, visit.getID_PROGRAM());
		request.addProperty(P_USER, visit.getUSER());
		request.addProperty(P_VISIT, visit.getID_VISIT());
		request.addProperty(Visit.TFIN, visit.getTFIN() == 1 ? "X" : "");

		SoapSerializationEnvelope envelope = call(request);

		SoapObject response = (SoapObject) envelope.getResponse();

		messageList.addAll(getMessageList(response));

		for (Message m : messageList) {
			if ("E".equalsIgnoreCase("" + m.getType())) {
				return false;
			}
		}
		visit.setSTATUS((byte) 0);
		dbManager.updateViisit(visit);

		return true;
	}

	public List<Message> Confirmation(Visit visit, List<Operation> ops)
			throws HttpResponseException, IOException, XmlPullParserException {
		List<String> orders = new ArrayList<String>();

		List<Message> messages = new ArrayList<Message>();

		for (Operation op : ops) {
			if (!orders.contains(op.getAufnr())) {
				orders.add(op.getAufnr());
			}
		}

		for (String order : orders) {
			List<Confirmation> confirmations = new ArrayList<Confirmation>();

			for (Operation op : ops) {
				if (order.equals(op.getAufnr())) {
					Confirmation conf = new Confirmation();

					conf.setACTIVITY(op.getACTIVITY());
					conf.setCOMPLETE(op.getCOMPLETE());
					conf.setACTUAL_DUR(op.getDURATION_NORMAL());
					conf.setCONF_TEXT("");
					conf.setEXEC_FIN_DATE(visit.getFFIN());
					conf.setEXEC_FIN_TIME(visit.getHFIN());
					conf.setEXEC_START_DATE(visit.getFINI());
					conf.setEXEC_START_TIME(visit.getHINI());
					conf.setUN_ACT_DUR(op.getDURATION_NORMAL_UNIT());
					confirmations.add(conf);
				}
			}
			messages.addAll(Confirmation(order, confirmations));
		}

		for (Message m : messages) {
			if (m.getType() == 'E') {
				return messages;
			}
		}

		for (Operation op : ops) {
			op.setCommited(1);
		}

		dbManager.updateOperations(ops);

		return messages;
	}

	public List<Message> Confirmation(String aufnr,
			List<Confirmation> confirmations) throws HttpResponseException,
			IOException, XmlPullParserException {

		SoapObject itConfirmation = new SoapObject();

		for (Confirmation item : confirmations) {
			SoapObject confObject = new SoapObject();

			confObject.addProperty(Confirmation.ACTIVITY, item.getACTIVITY());
			confObject.addProperty(Confirmation.ACTUAL_DUR,
					String.format(Locale.US, "%.1f", item.getACTUAL_DUR()));
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
			confObject
					.addProperty(
							Confirmation.CONF_TEXT,
							item.getCONF_TEXT() == null
									|| "".equalsIgnoreCase(item.getCONF_TEXT()) ? "COMPLETE"
									: item.getCONF_TEXT());
			confObject.addProperty(Confirmation.COMPLETE,
					TraceMFormater.getBool(item.getCOMPLETE()));

			itConfirmation.addProperty(ITEM, confObject);
		}

		// Create request
		SoapObject request = new SoapObject(NAMESPACE, Z_PM_AP_CONFIRMATION);
		request.addProperty(IT_CONFIRMATION, itConfirmation);
		request.addProperty(P_ORDER_NUMBER, aufnr);

		SoapSerializationEnvelope envelope = call(request);

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

		SoapSerializationEnvelope envelope = call(request);

		List<Message> messages = null;

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
	 * @param aufnr
	 * @return A List with Measurement points
	 * @throws HttpResponseException
	 * @throws IOException
	 * @throws XmlPullParserException
	 */
	public List<MeasurementPoint> getMeasurePoints(String aufnr, String equnr)
			throws HttpResponseException, IOException, XmlPullParserException {

		SoapObject request = new SoapObject(NAMESPACE,
				Z_PM_AP_GET_MEASURE_POINT);

		request.addProperty(P_EQUIPMENT, equnr);

		SoapSerializationEnvelope envelope = call(request);

		@SuppressWarnings("unchecked")
		Vector<SoapObject> response = (Vector<SoapObject>) envelope
				.getResponse();

		SoapObject soapPoints = response.get(0);

		List<MeasurementPoint> points = new ArrayList<MeasurementPoint>();

		int count = soapPoints.getPropertyCount();
		for (int i = 0; i < count; i++) {
			SoapObject item = (SoapObject) soapPoints.getProperty(i);

			MeasurementPoint point = new MeasurementPoint();

			point.setAufnr(aufnr);
			point.setEqunr(equnr);
			point.setDescription(parseResult(item
					.getPropertyAsString(MeasurementPoint.DESCRIPTION)));
			point.setUnit(parseResult(item
					.getPropertyAsString(MeasurementPoint.UNIT)));
			point.setPoint(parseResult(item
					.getPropertyAsString(MeasurementPoint.POINT)));

			points.add(point);
		}

		dbManager.insertMeasurementPoints(points);

		return points;
	}

	public List<Message> saveMeasureDoc(String userName,
			List<MeasurementPoint> points) throws HttpResponseException,
			IOException, XmlPullParserException {

		if (points == null || points.size() == 0) {
			return new ArrayList<Message>();
		}

		SoapObject request = new SoapObject(NAMESPACE,
				Z_PM_AP_SAVE_MEASURE_DOCUEMNT);

		SoapObject soapPoints = new SoapObject();

		for (MeasurementPoint point : points) {
			SoapObject item = new SoapObject();

			item.addProperty(DESCRIPTION,
					point.getNotes() == null ? "" : point.getNotes());
			item.addProperty(POINT, point.getPoint());
			item.addProperty(ZREAD,
					String.format(Locale.US, "%.1f", point.getRead()));

			soapPoints.addProperty(ITEM, item);
		}

		request.addProperty(IT_READ_POINTS, soapPoints);
		request.addProperty(P_USER, userName);

		SoapSerializationEnvelope envelope = call(request);

		SoapObject response = (SoapObject) envelope.getResponse();
		List<Message> messages = getMessageList(response);

		for (Message m : messages) {
			if (m.getType() == 'E') {
				return messages;
			}
		}

		for (MeasurementPoint point : points) {
			point.setCommited(1);
		}
		dbManager.updateMeasurementPoints(points);

		return messages;
	}

	private SoapSerializationEnvelope call(SoapObject request)
			throws HttpResponseException, IOException, XmlPullParserException {

		// Create envelope
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		envelope.setOutputSoapObject(request);
		HttpTransportBasicAuth transport = new HttpTransportBasicAuth(URL2,
				SAP_USER, SAP_PASSWORD);

		transport.call(SOAP_ACTION, envelope);

		return envelope;
	}

	public List<VisitLog> getVisitDetail(Visit visit)
			throws HttpResponseException, IOException, XmlPullParserException {

		SoapObject request = new SoapObject(NAMESPACE, Z_PM_AP_GET_VISIT_DETAIL);

		request.addProperty(P_PROGRAM, visit.getID_PROGRAM());
		request.addProperty(P_USER, visit.getUSER());
		request.addProperty(P_VISIT, visit.getID_VISIT());

		SoapSerializationEnvelope envelope = call(request);

		List<VisitLog> visitLog = new ArrayList<VisitLog>();

		@SuppressWarnings("unchecked")
		Vector<SoapObject> response = (Vector<SoapObject>) envelope
				.getResponse();

		SoapObject soapVisitLog = response.get(0);

		int count = soapVisitLog.getPropertyCount();

		for (int i = 0; i < count; i++) {
			SoapObject item = (SoapObject) soapVisitLog.getProperty(i);

			VisitLog vl = new VisitLog();

			vl.setDate(parseResult(item.getPropertyAsString(VisitLog.DATE)));
			vl.setHour(parseResult(item.getPropertyAsString(VisitLog.HOUR)));
			vl.setId_event((long) parseNumericResult(item
					.getPropertyAsString(VisitLog.ID_EVENT)));
			vl.setId_visit(visit.getID_VISIT());
			vl.setText_event(parseResult(item
					.getPropertyAsString(VisitLog.TEXT_EVENT)));

			vl.setId_program(visit.getID_PROGRAM());

			visitLog.add(vl);
		}

		dbManager.insertVisitLog(visitLog);

		return visitLog;
	}

	public List<Message> closeSchedule(String user, String id)
			throws HttpResponseException, IOException, XmlPullParserException {

		SoapObject request = new SoapObject(NAMESPACE, Z_PM_AP_CLOSE_SCHEDULE);

		request.addProperty(P_PROGRAM, id);
		request.addProperty(P_USER, user);

		SoapSerializationEnvelope envelope = call(request);

		SoapObject response = (SoapObject) envelope.getResponse();

		List<Message> messages = getMessageList(response);

		for (Message m : messages) {
			if (m.getType() == 'E') {
				return messages;
			}
		}

		dbManager.updateScheduleStatus(id, 3);

		return messages;
	}
}
