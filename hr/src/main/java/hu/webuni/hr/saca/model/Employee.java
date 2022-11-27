package hu.webuni.hr.saca.model;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;

@Entity
@NamedQuery(name = "Employee.getAll",query = "SELECT a FROM Employee a ")
@NamedQuery(name = "Employee.getJob",query = "SELECT e FROM Employee e WHERE job = :job")
@NamedQuery(name = "Employee.getName",query = "SELECT e FROM Employee e WHERE UPPER(name) LIKE (UPPER(:name)||'%')")
@NamedQuery(name = "Employee.getJobEnterDate",query = "SELECT e FROM Employee e WHERE e.jobStartDate BETWEEN :enterdate1 AND :enterdate2")
@NamedQuery(name = "Employee.deleteAll",query = "DELETE FROM Employee")
public class Employee {

	@Id
	//@GeneratedValue(strategy = GenerationType.IDENTITY)
	@GeneratedValue
	private Long id;
	private String name;
	private String job;
	private int salary;
	private LocalDateTime jobStartDate;
	
	@ManyToOne
	@JoinColumn(name = "company_id")  //company id oszlopnév 
	private Company company;
	
	public Employee(Long id, String name, String job, int salary, LocalDateTime jobStartDate, Company company) {
		super();
		this.id = id;
		this.name = name;
		this.job = job;
		this.salary = salary;
		this.jobStartDate = jobStartDate;
		this.company = company;
	}

	public Employee() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getJob() {
		return job;
	}

	public void setJob(String job) {
		this.job = job;
	}

	public int getSalary() {
		return salary;
	}

	public void setSalary(int salary) {
		this.salary = salary;
	}

	public LocalDateTime getJobStartDate() {
		return jobStartDate;
	}

	public void setJobStartDate(LocalDateTime jobStartDate) {
		this.jobStartDate = jobStartDate;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

}
