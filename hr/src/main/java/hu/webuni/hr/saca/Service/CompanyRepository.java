package hu.webuni.hr.saca.Service;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import hu.webuni.hr.saca.model.AvgOfSalary;
import hu.webuni.hr.saca.model.Company;
import hu.webuni.hr.saca.model.CountOfCompany;

public interface CompanyRepository extends JpaRepository<Company, Long>{

	public List<Company> getCompaniesBySalaryLimit(int salaryLimit);

	public List<CountOfCompany> getCompaniesByNumberOfEmployee(Long employeeCount);

	public List<AvgOfSalary> getAvgOfSalaryByJob(Long companyId);
	
}
