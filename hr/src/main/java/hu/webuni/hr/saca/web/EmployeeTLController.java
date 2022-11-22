package hu.webuni.hr.saca.web;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import hu.webuni.hr.saca.model.Employee;

@Controller
public class EmployeeTLController {

	private List<Employee> allEmployees = new ArrayList<>();
	
	{
		allEmployees.add(new Employee(1L, "Bela", "bigboss", 100, LocalDateTime.of(2022,11,11,12,00,00).minusMonths(50)));
		allEmployees.add(new Employee(2L, "jozsi", "littleboss", 50, LocalDateTime.of(2022,11,11,12,00,00).minusMonths(30)));
	}
	
	@GetMapping("/")
	public String home() {
		return "index";
	}
	
	@GetMapping("/employees")
	public String listEmployees(Map<String, Object> model) {
		model.put("employees", allEmployees);
		model.put("newEmployee", new Employee());
		return "employees";
	}
	
	@PostMapping("/employees")
	public String addEmployee(Employee employee) {
		allEmployees.add(employee);
		return "redirect:employees";    //TODO: miért? Ez pedig működik
		
	}
	
	@GetMapping("/employees/delete/{id}")
	public String DeleteEmployee(@PathVariable Long id) {
		//1. megoldás, a klasszik
//		Integer index = 0;
//		for (int i = 0; i < allEmployees.size(); i++) {
//			if(allEmployees.get(i).getId() == id) {
//				index = i;
//				break;
//			}
//		}
//		if (index != null) {
//			allEmployees.remove(index.intValue());    //TODO: miért? a allEmployees.remove(index) nem működik, nincs remove, de hibaüzenet sincs  
//		}
		//2. megoldás
//		allEmployees.remove((allEmployees.stream().filter(employee -> employee.getId() == id).findFirst().get()) );
		//3. megoldás
		allEmployees.removeIf(employee -> employee.getId() == id);
		
		//return "redirect:employees";  //TODO: miért? "/employees/delete/employees"-t fog generálni és
			//Failed to convert value of type 'java.lang.String' to required type 'java.lang.Long'; nested exception is java.lang.NumberFormatException: 
			//	For input string: "employees"]
		return "redirect:/employees";  // "/employees"-t fog generálni
	}
	
	@GetMapping("/employees/edit/{id}")
	public String editEmployee(@PathVariable Long id, Map<String, Object> model) {
		model.put("editEmployee", allEmployees.stream().filter(emp -> emp.getId() == id).findFirst().get());
		return "editemployee";
	}
	
	@PostMapping("/employees/edit")
	public String updateEmployee(Employee editEmployee) {
		int index = allEmployees.indexOf(allEmployees.stream().filter(emp -> emp.getId() == editEmployee.getId()).findFirst().get());
		allEmployees.set(index, editEmployee);
		return "redirect:/employees";
	}
}
