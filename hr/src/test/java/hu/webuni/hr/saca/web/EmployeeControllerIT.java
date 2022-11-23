package hu.webuni.hr.saca.web;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.web.reactive.server.WebTestClient;

import hu.webuni.hr.saca.dto.EmployeeDto;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient(timeout = "PT600S")
public class EmployeeControllerIT {

	private static final String BASE_URI = "/api/employees";
	
	@Autowired
	WebTestClient webTestClient;

	/**
	 * PUT test: successful
	 */
	@Test
	void testThatEmployeeIsUpdated() {
		EmployeeDto employeeDto =new EmployeeDto(Long.valueOf(1L), "ujBela", "ujboss", 999, LocalDateTime.of(1999,11,11,12,00,00).minusMonths(50));
		modifyEmployeeTest(employeeDto);
	}
	
	/**
	 * PUT test: unsuccessful
	 */
	@Test
	void testThatEmployeeNotFound() {
		EmployeeDto employeeDto =new EmployeeDto(Long.valueOf(5L), "XBela", "xboss", 888, LocalDateTime.of(2010,11,11,12,00,00).minusMonths(50));
		modifyEmployeeTest(employeeDto);
	}

	void modifyEmployeeTest(EmployeeDto employeeDto) {
		putEmployee(employeeDto);
		EmployeeDto employeeDtoAfter = getEmployee(employeeDto.getId());
		
		assertThat(employeeDto)
			.usingRecursiveComparison()
			.isEqualTo(employeeDtoAfter);
	}
	
	private void putEmployee(EmployeeDto employeeDto) {
		webTestClient
			.put()
			.uri(BASE_URI + "/" + employeeDto.getId())
			.bodyValue(employeeDto)
			.exchange()
			.expectStatus()
			.isOk();
	}
	
	private EmployeeDto getEmployee(Long id) {
		return webTestClient
				.get()
				.uri(BASE_URI + "/" + id)
				.exchange()
				.expectStatus().isOk()
				.expectBody(EmployeeDto.class)
				.returnResult().getResponseBody();
	}
	
	/**
	 * POST test: successful
	 */
	@Test
	void testThatCreatedEmployeeIsListed() throws Exception {
		EmployeeDto newEmployeeDto = new EmployeeDto(Long.valueOf(3L), "KisPista", "lótifuti", 10, LocalDateTime.of(2012,11,11,12,00,00));				
		PostTestThatCreatedEmployeeIsListed(newEmployeeDto);
	}

	/**
	 * POST test: unsuccessful, updating Id is exist
	 */
	@Test
	void testForDuplicatedEmployee() throws Exception {
		EmployeeDto newEmployeeDto = new EmployeeDto(Long.valueOf(1L), "KisPista", "lótifuti", 10, LocalDateTime.of(2012,11,11,12,00,00));				
		PostTestThatCreatedEmployeeIsListed(newEmployeeDto);
	}
	
	void PostTestThatCreatedEmployeeIsListed(EmployeeDto newEmployeeDto) throws Exception {
		List<EmployeeDto> employeesBefore = getAllEmployee();
//		EmployeeDto newEmployeeDto = new EmployeeDto(Long.valueOf(1L), "KisPista", "lótifuti", 10, LocalDateTime.of(2012,11,11,12,00,00));				
		createEmployee(newEmployeeDto);
		List<EmployeeDto> employeesAfter = getAllEmployee();
	
		assertThat(employeesAfter.subList(0, employeesBefore.size()))
			.usingRecursiveFieldByFieldElementComparator()
			.containsExactlyElementsOf(employeesBefore);
		
		assertThat(employeesAfter.get(employeesAfter.size()-1))
			.usingRecursiveComparison()
			.isEqualTo(newEmployeeDto);
	}
	
	private List<EmployeeDto> getAllEmployee() {
		List<EmployeeDto> responseEmployees = webTestClient
			.get()
			.uri(BASE_URI)
			.exchange()
			.expectStatus().isOk()
			.expectBodyList(EmployeeDto.class)
			.returnResult()
			.getResponseBody();
		
		Collections.sort(responseEmployees, (a1, a2) -> Long.compare(a1.getId(), a2.getId()) );
		return responseEmployees;
	}
	
	private void createEmployee(EmployeeDto employeeDto) {
		webTestClient
			.post()
			.uri(BASE_URI)
			.bodyValue(employeeDto)
			.exchange()
			.expectStatus()
			.isOk();
	}
}
