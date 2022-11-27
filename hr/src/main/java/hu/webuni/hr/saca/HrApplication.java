package hu.webuni.hr.saca;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import hu.webuni.hr.saca.Service.SalaryService;
import hu.webuni.hr.saca.model.Employee;

@SpringBootApplication
public class HrApplication implements CommandLineRunner{

	@Autowired
	SalaryService salaryService; 
	
	public static void main(String[] args) {
		SpringApplication.run(HrApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		LocalDateTime now = LocalDateTime.now();
		Employee emp1 = new Employee(1L, "Bela", "boss", 100, now.minusMonths(91), null);
		System.out.println(emp1.getName() + " raised salary: " + salaryService.getEmployeeSalary(emp1));
		
	}

}
