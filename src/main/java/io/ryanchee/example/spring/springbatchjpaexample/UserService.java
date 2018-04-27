package io.ryanchee.example.spring.springbatchjpaexample;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
	
	@Autowired
	UserRepository userRepository;	
	
	public UserBean getUserByName(String employeeName) {
		return userRepository.findByName(employeeName);
	}
}
