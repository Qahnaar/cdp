package com.markhyvka.copy.entry;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.markhyvka.copy.domain.Employee;
import com.markhyvka.copy.util.DeepCopyUtil;

public class EntryPoint {

	private static final int WORKED_HOURS = 1000;
	private static final String EMPLOYEE_CARD_NUM = "100001";
	private static final byte[] ICON = { 3, 5 };
	private static final long USER_ID = 15L;
	private static final String PASSWORD = "password";
	private static final String USERNAME = "username";
	private static final List<Long> FAVOURITE_USERS = new ArrayList<Long>();
	private static final List<String> DEPARTMENTS = new ArrayList<String>();

	static {
		FAVOURITE_USERS.add(14L);
		FAVOURITE_USERS.add(16L);

		DEPARTMENTS.add("BEVERAGES");
		DEPARTMENTS.add("ALCOHOL");
	}

	public static void main(String[] args) throws InstantiationException,
			IllegalAccessException {
		Employee source = new EntryPoint().populateEmployee();
		Employee target = (Employee) new DeepCopyUtil().deepCopy(source);

		System.out.println(target);
	}

	private Employee populateEmployee() {
		Employee emp = new Employee();

		emp.setUsername(USERNAME);
		emp.setPassword(PASSWORD);
		emp.isActive = Boolean.TRUE;
		emp.setLastUsed(Calendar.getInstance());

		emp.setUserId(USER_ID);
		emp.setFavouriteUsers(FAVOURITE_USERS);
		emp.icon = ICON;

		emp.setEmployeeCardNum(EMPLOYEE_CARD_NUM);
		emp.setDepartments(DEPARTMENTS);
		emp.workedHours = WORKED_HOURS;

		return emp;
	}
}
