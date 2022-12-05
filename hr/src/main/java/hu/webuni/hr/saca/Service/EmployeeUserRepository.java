package hu.webuni.hr.saca.Service;

import org.springframework.data.jpa.repository.JpaRepository;

import hu.webuni.hr.saca.model.EmployeeUser;

public interface EmployeeUserRepository extends JpaRepository<EmployeeUser, String> {

	
}
