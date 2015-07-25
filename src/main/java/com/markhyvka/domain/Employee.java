package com.markhyvka.domain;

import java.util.List;

public class Employee extends User {

	private String employeeCardNum;

	private List<String> departments;

	public int workedHours;

	public String getEmployeeCardNum() {
		return employeeCardNum;
	}

	public void setEmployeeCardNum(String employeeCardNum) {
		this.employeeCardNum = employeeCardNum;
	}

	public List<String> getDepartments() {
		return departments;
	}

	public void setDepartments(List<String> departments) {
		this.departments = departments;
	}
}
