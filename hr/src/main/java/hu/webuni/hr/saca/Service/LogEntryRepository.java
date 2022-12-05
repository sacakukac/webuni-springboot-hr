package hu.webuni.hr.saca.Service;

import org.springframework.data.jpa.repository.JpaRepository;

import hu.webuni.hr.saca.model.LogEntry;

public interface LogEntryRepository extends JpaRepository<LogEntry, Long>{

}
