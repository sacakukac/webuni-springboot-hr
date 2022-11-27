package hu.webuni.hr.saca.Service;

import java.util.List;
import java.util.NoSuchElementException;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.transaction.annotation.Transactional;

import hu.webuni.hr.saca.model.Employee;

public abstract class AbstractEmployeeService implements EmployeeService{

//	private Map<Long, Employee> employees = new HashMap<>();
//
//	{
//		employees.put(Long.valueOf(1L), new Employee(Long.valueOf(1L), "Bela", "boss", 100, LocalDateTime.of(2022,11,11,12,00,00).minusMonths(50)));
//		employees.put(Long.valueOf(2L), new Employee(Long.valueOf(2L), "jozsi", "littleboss", 50, LocalDateTime.of(2022,11,11,12,00,00).minusMonths(30)));
//	}
	
	@PersistenceContext
	EntityManager em;
	
	public List<Employee> findAll() {
		//return em.createQuery("SELECT e FROM employee e", Employee.class).getResultList();
		return em.createNamedQuery("Employee.getAll", Employee.class).getResultList();
		//return new ArrayList<Employee>(employees.values());
	}
	
	public Employee findById(long id) {
		return em.find(Employee.class, id);
		//return employees.get(id);
	} 
	
	@Transactional
	public Employee save(Employee employee) {
		//employees.put(employee.getId(), employee);
		em.persist(employee);
		return employee;
	}
	
	@Transactional
	public void delete(long id) {
		em.remove(findById(id));

		//employees.remove(id);
	}

	@Transactional
	public Employee update(Employee employee) {
		if (findById(employee.getId()) != null) {
			em.merge(employee);
			//employees.put(company.getId(), company);			
			return employee;
		} else {
			throw new NoSuchElementException();
		}
	}
		
	
	
}
