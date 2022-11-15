package hu.webuni.hr.saca.dto;

import java.util.List;

import hu.webuni.hr.saca.model.Employee;

public class CompanyDto {

	private long id;
	private String name;
	private String address;
	private String regNumber;
	private List<EmployeeDto> employees;
	
	public CompanyDto() {
	}

		public CompanyDto(long id, String name, String address, String regNumber, List<EmployeeDto> employees) {
		this.id = id;
		this.name = name;
		this.address = address;
		this.regNumber = regNumber;
		this.employees = employees;
	}



	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getRegNumber() {
		return regNumber;
	}

	public void setRegNumber(String regNumber) {
		this.regNumber = regNumber;
	}

	public List<EmployeeDto> getEmployees() {
		return employees;
	}

	public void setEmployees(List<EmployeeDto> employees) {
		this.employees = employees;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
	
}
