package hu.webuni.hr.saca.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hu.webuni.hr.saca.config.HrConfigProperties;
import hu.webuni.hr.saca.model.Employee;

@Service
public class DefaultEmployeeService implements EmployeeService {

	@Autowired
	HrConfigProperties config;
	
	@Override
	public int getPayRaisePercent(Employee employee) {
		return config.getSalary().getDef().getPercent();
	}

}
