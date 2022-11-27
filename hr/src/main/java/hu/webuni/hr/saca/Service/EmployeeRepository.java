package hu.webuni.hr.saca.Service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import hu.webuni.hr.saca.model.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

//	@Profile("!smart") TODO:
	
	List<Employee> getJob(String job);
	
	List<Employee> getName(String name);
	
	List<Employee> getJobEnterDate(LocalDateTime enterdate1, LocalDateTime enterdate2);

	
	
}
