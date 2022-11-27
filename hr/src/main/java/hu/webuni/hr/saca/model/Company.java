package hu.webuni.hr.saca.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

@Entity
@NamedQuery(name = "Company.deleteAll",query = "DELETE FROM Company")
@NamedQuery(name = "Company.getCompaniesBySalaryLimit",query = "SELECT DISTINCT c FROM Company c JOIN c.employees e WHERE e.salary > :salaryLimit GROUP BY c")
@NamedQuery(name = "Company.getCompaniesByNumberOfEmployee",query = "SELECT new hu.webuni.hr.saca.model.CountOfCompany(count(e) as cnt, c.name as name) FROM Company c JOIN c.employees e GROUP BY c HAVING count(e) > :employeeCount ")
@NamedQuery(name = "Company.getAvgOfSalaryByJob",query = "SELECT new hu.webuni.hr.saca.model.AvgOfSalary(AVG(e.salary) as avg, e.job as jobname) FROM Company c JOIN c.employees e WHERE c.id = :companyId GROUP BY e.job ORDER BY AVG(e.salary) DESC ")
public class Company {

	public enum CompanyType {Bt, Kft, ZRt, NyRt}  
	
	@Id
	//@GeneratedValue(strategy = GenerationType.IDENTITY)
	@GeneratedValue
	@Column(name = "company_id")
	private Long id;
	private String name;
	private String address;
	private String regNumber;
	private CompanyType companyType;
	
	@OneToMany(mappedBy = "company",fetch=FetchType.EAGER)  //az employee-ban a company property
	private List<Employee> employees;
	
	public Company() {
	}

	public Company(Long id, String name, String address, String regNumber, CompanyType companyType,
			List<Employee> employees) {
		super();
		this.id = id;
		this.name = name;
		this.address = address;
		this.regNumber = regNumber;
		this.companyType = companyType;
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

	public List<Employee> getEmployees() {
		return employees;
	}

	public void setEmployees(List<Employee> employees) {
		this.employees = employees;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public CompanyType getCompanyType() {
		return companyType;
	}

	public void setCompanyType(CompanyType companyType) {
		this.companyType = companyType;
	}


	
}
