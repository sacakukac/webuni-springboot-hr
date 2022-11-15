package hu.webuni.hr.saca.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import hu.webuni.hr.saca.Service.EmployeeService;
import hu.webuni.hr.saca.Service.SmartEmployeeService;

@Configuration
@Profile("smart")
public class SmartEmployeeConfiguration {

	@Bean
	EmployeeService employeeService() {
		return new SmartEmployeeService();
	}

}
