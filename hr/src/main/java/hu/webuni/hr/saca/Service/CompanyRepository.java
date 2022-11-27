package hu.webuni.hr.saca.Service;

import org.springframework.data.jpa.repository.JpaRepository;

import hu.webuni.hr.saca.model.Company;

public interface CompanyRepository extends JpaRepository<Company, Long>{

}
