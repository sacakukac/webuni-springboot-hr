package hu.webuni.hr.saca.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import hu.webuni.hr.saca.dto.EmployeeDto;
import hu.webuni.hr.saca.model.Employee;

@Mapper(componentModel="spring")
public interface EmployeeMapper {

	List<EmployeeDto> employeesToDtos(List<Employee> employees);

	List<Employee> employeesDtosToEmpl(List<EmployeeDto> employeesDto);
	
	EmployeeDto employeeToDto(Employee employee);
	
	Employee dtoToEmployee(EmployeeDto employeeDto);

	
}
