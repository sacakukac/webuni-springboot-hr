package hu.webuni.hr.saca.Service;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import hu.webuni.hr.saca.model.LogEntry;

@Service
public class LogEntryService {

	@Autowired
	LogEntryRepository logEntryRepository;
	
	public void createLog(String description) {
//		callBackendSystem();
		logEntryRepository.save(new LogEntry(description, SecurityContextHolder.getContext().getAuthentication().getName()));
	}

	private void callBackendSystem() {
		if(new Random().nextInt(4) == 1)
			throw new RuntimeException();
	}
}
