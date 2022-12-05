package hu.webuni.hr.saca.security;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import hu.webuni.hr.saca.Service.EmployeeUserRepository;
import hu.webuni.hr.saca.model.EmployeeUser;

@Service
public class EmployeeUserDetailsService implements UserDetailsService{

	@Autowired
	EmployeeUserRepository employeeUserRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		 EmployeeUser employeeUser = employeeUserRepository.findById(username)
		 	.orElseThrow(() -> new UsernameNotFoundException(username));
		
		 
		 return new User(username, employeeUser.getPassword(), 
				 employeeUser.getRoles().stream().map(SimpleGrantedAuthority::new)
				 .collect(Collectors.toList()));
	}

}
