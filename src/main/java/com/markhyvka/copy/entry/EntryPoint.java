package com.markhyvka.copy.entry;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.log4j.Logger;

import com.markhyvka.copy.domain.Employee;
import com.markhyvka.copy.util.DeepCopyUtil;

public class EntryPoint {

	final static Logger LOG = Logger.getLogger(EntryPoint.class);

	private static final long FAVOURITE_USER_16 = 16L;
	private static final long FAVOURITE_USER_14 = 14L;
	private static final String ALCOHOL_DEPARTMENT = "ALCOHOL";
	private static final String BEVERAGES_DEPARTMENT = "BEVERAGES";
	private static final int WORKED_HOURS = 1000;
	private static final String EMPLOYEE_CARD_NUM = "100001";
	private static final byte[] ICON = { 3, 5 };
	private static final long USER_ID = 15L;
	private static final String PASSWORD = "password";
	private static final String USERNAME = "username";
	private static final List<Long> FAVOURITE_USERS = new ArrayList<Long>();
	private static final List<String> DEPARTMENTS = new ArrayList<String>();

	static {
		FAVOURITE_USERS.add(FAVOURITE_USER_14);
		FAVOURITE_USERS.add(FAVOURITE_USER_16);

		DEPARTMENTS.add(BEVERAGES_DEPARTMENT);
		DEPARTMENTS.add(ALCOHOL_DEPARTMENT);
	}

	public static void main(String[] args) throws InstantiationException,
			IllegalAccessException {
		Employee source = new EntryPoint().populateEmployee();
		Employee target = (Employee) new DeepCopyUtil().deepCopy(source);

		LOG.debug("Source " + source + " has been copied to " + target);
	}

	private Employee populateEmployee() {
		Employee employee = new Employee();

		employee.setUsername(USERNAME);
		employee.setPassword(PASSWORD);
		employee.isActive = Boolean.TRUE;
		employee.setLastUsed(Calendar.getInstance());

		employee.setUserId(USER_ID);
		employee.setFavouriteUsers(FAVOURITE_USERS);
		employee.icon = ICON;

		employee.setEmployeeCardNum(EMPLOYEE_CARD_NUM);
		employee.setDepartments(DEPARTMENTS);
		employee.workedHours = WORKED_HOURS;

		return employee;
	}
}
