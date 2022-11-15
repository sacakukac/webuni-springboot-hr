package hu.webuni.hr.saca.web;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import hu.webuni.hr.saca.dto.EmployeeDto;

@RestController
@RequestMapping("/api/employees")
public class EmployeeContorller {

	private Map<Long, EmployeeDto> employees = new HashMap<>();

	{
		employees.put(1L, new EmployeeDto(1L, "Bela", "boss", 100, LocalDateTime.of(2022,11,11,12,00,00).minusMonths(50)));
		employees.put(2L, new EmployeeDto(2L, "jozsi", "littleboss", 50, LocalDateTime.of(2022,11,11,12,00,00).minusMonths(30)));
	}

	//1. megoldás param nélküli hívás esetén
//	@GetMapping
//	public List<EmployeeDto> getAll() {
//		return new ArrayList<>(employees.values());
//	}

	//1. megoldás szuresre param-mal történő hívás esetén
//	@GetMapping(params = "salarylimit")
//	public ResponseEntity<List<EmployeeDto>> getEmployeeBySalary(@RequestParam Integer salarylimit ) {
//		List<EmployeeDto> overLimitEmployees = new ArrayList<>();		
//		for (Map.Entry<Long, EmployeeDto> entry : employees.entrySet()) {
//			if( entry.getValue().getSalary() > salarylimit)	{
//				overLimitEmployees.add(entry.getValue());
//			}
//		}
//		if (overLimitEmployees.size() > 0 ) {
//			return ResponseEntity.ok(overLimitEmployees);
//		} else {
//			return ResponseEntity.notFound().build();
//		}
//	}	

	//2. megoldás egy rutinban lekezelve
	@GetMapping
	public ResponseEntity<List<EmployeeDto>> getAll(@RequestParam(required = false) Integer salarylimit) {
		if (salarylimit == null) {
			return ResponseEntity.ok(new ArrayList<>(employees.values()));	
		} else {
			List<EmployeeDto> overLimitEmployees = employees.values()
			.stream()
			.filter(emp -> emp.getSalary() > salarylimit)
			.collect(Collectors.toList());
//			List<EmployeeDto> overLimitEmployees = new ArrayList<>();		
//			for (Map.Entry<Long, EmployeeDto> entry : employees.entrySet()) {
//				if( entry.getValue().getSalary() > salarylimit)	{
//					overLimitEmployees.add(entry.getValue());
//				}
//			}
			if (overLimitEmployees != null ) {
				return ResponseEntity.ok(overLimitEmployees);
			} else {
				return ResponseEntity.notFound().build();
			}
		}
	}
	

	
	@GetMapping("/{id}")
	public ResponseEntity<EmployeeDto> getById(@PathVariable long id) {
		EmployeeDto employeeDto = employees.get(id); 
		if (employeeDto != null) {
			return ResponseEntity.ok(employeeDto);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@PostMapping
	public EmployeeDto createEmployee(@RequestBody EmployeeDto employeeDto) {
		employees.put(employeeDto.getId(), employeeDto);
		return employeeDto;
	}
	
	@PutMapping("/{id}")  //modify
	public ResponseEntity<EmployeeDto> modifyEmployee(@PathVariable long id, @RequestBody EmployeeDto employeeDto){
		if (!employees.containsKey(id)) {
			return ResponseEntity.notFound().build();
		} else {
			employeeDto.setId(id);
			employees.put(id, employeeDto);
			return ResponseEntity.ok(employeeDto);
		}
	}

	@DeleteMapping("/{id}")
	public void deleteEmployee(@PathVariable Long id) {
		employees.remove(id);
	}
	
	//sajat megoldas
	@GetMapping("/salarylimit")
	public ResponseEntity<List<EmployeeDto>> getEmployeeBySalary2(@RequestParam("salarylimit") Integer salaryLimit ) {
		List<EmployeeDto> overLimitEmployees = new ArrayList<>();		
		for (Map.Entry<Long, EmployeeDto> entry : employees.entrySet()) {
			if( entry.getValue().getSalary() > salaryLimit)	{
				overLimitEmployees.add(entry.getValue());
			}
		}
		if (overLimitEmployees.size() > 0 ) {
			return ResponseEntity.ok(overLimitEmployees);
		} else {
			return ResponseEntity.notFound().build();
		}
	}
}
