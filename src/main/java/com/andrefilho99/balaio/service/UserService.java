package com.andrefilho99.balaio.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.andrefilho99.balaio.exception.TokenException;
import com.andrefilho99.balaio.exception.UserNotFoundException;
import com.andrefilho99.balaio.model.User;
import com.andrefilho99.balaio.repository.UserRepository;

@Service
public class UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private SmsService smsService;
	
	//CRUD
	
	public User create(String nickname, String number) {
		
		User user = new User();
		
		user.setNickname(nickname);
		user.setNumber(number);
		
		userRepository.save(user);
		smsService.notifyNewUser(number, number.substring(number.length()-5));
		
		return user;
	}
	
	public void validate(Integer id, String token) {
		
		User user = userRepository.findById(id).get();
		String userNumber = user.getNumber();
		
		System.out.println(userNumber.substring(userNumber.length()-5));
		
		if(!userNumber.substring(userNumber.length()-5).equals(token)) {
			throw new TokenException("The given token is not valid.");
		}
		
		user.setValidated(true);
		userRepository.save(user);
	}
	
	public User get(Integer id) {
		
		Optional<User> user = userRepository.findById(id);
		
		if(!user.isPresent()) {
			throw new UserNotFoundException("There is no user with the given id.");
		}
		
		return user.get();
	}
	
	public User getByNumber(String number) {
		
		User user = userRepository.findByNumber(number);
		
		if(user == null) {
			smsService.notifyNumber(number);
			throw new UserNotFoundException("There is no user with the given number.");
		}
		
		return user;
	}
	
	public void update(Integer id, String nickname) {
		
		User user = userRepository.getOne(id);
		
		user.setNickname(nickname);
		
		userRepository.save(user);
	}
	
	public void delete(Integer id) {
		userRepository.deleteById(id);
	}
	
	//Other Methods
	
	public List<User> getAll() {
		return userRepository.findAll();
	}
	
	public User getByNickname(String nickname) throws UserNotFoundException {
		
		User user = userRepository.findByNickname(nickname);
		
		if(user == null) {
			throw new UserNotFoundException("There is no user with the given nickname");
		}
		
		return user;
	}
}