package hu.webuni.hr.saca.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import hu.webuni.hr.saca.Service.DefaultEmployeeService;
import hu.webuni.hr.saca.Service.EmployeeService;

@Configuration
@Profile("!smart")
public class DefaultEmployeeConfiguration {

	@Bean
	EmployeeService employeeService() {
		return new DefaultEmployeeService();
	}
}
