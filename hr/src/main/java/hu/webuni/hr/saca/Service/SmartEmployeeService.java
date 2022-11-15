package hu.webuni.hr.saca.Service;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hu.webuni.hr.saca.config.HrConfigProperties;
import hu.webuni.hr.saca.model.Employee;

@Service 
public class SmartEmployeeService implements EmployeeService {

	@Autowired
	HrConfigProperties config;

	@Override
	public int getPayRaisePercent(Employee employee) {
	
		Integer percent = null;
		
		//eredeti config szerinti feldolgozás, 3 fix értékpár 		
//		if (employee.getJobStartDate().isBefore(LocalDateTime.now().minusDays((long)config.getSalary().getSmart().getLimit3()*365))  ) {
//			percent = config.getSalary().getSmart().getPercent3();
//		} else if (employee.getJobStartDate().isBefore(LocalDateTime.now().minusDays((long)config.getSalary().getSmart().getLimit2()*365))) {
//			percent = config.getSalary().getSmart().getPercent2();
//		} else if (employee.getJobStartDate().isBefore(LocalDateTime.now().minusDays((long)(config.getSalary().getSmart().getLimit1()*365)))) {
//			percent = config.getSalary().getSmart().getPercent1();
//		} else {
//			percent = 0;
//		}
		
		
		TreeMap<Double, Integer> limits = config.getSalary().getSmart().getLimits();
		for (Map.Entry<Double, Integer> entry : limits.entrySet()) {
			Double limit = entry.getKey();
			if (employee.getJobStartDate().isBefore(LocalDateTime.now().minusDays((int)(limit*365)))) {
				percent = entry.getValue();				
			} else { 
				break;
			}
		}
		
		return percent == null ? 0 : percent;
	}
}
