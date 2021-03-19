package com.andrefilho99.balaio.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.andrefilho99.balaio.exception.UserNotFoundException;
import com.andrefilho99.balaio.model.User;
import com.andrefilho99.balaio.repository.UserRepository;
import com.andrefilho99.balaio.utils.PasswordUtils;

@Service
public class UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PasswordUtils passwordUtils;
	//CRUD
	
	public void create(String nickname, String email, String password) {
		
		User user = new User();
		
		user.setNickname(nickname);
		user.setEmail(email);
		user.setPassword(passwordUtils.encodePassword(password));
		
		userRepository.save(user);
	}
	
	public User get(Integer id) {
		
		Optional<User> user = userRepository.findById(id);
		
		if(!user.isPresent()) {
			throw new UserNotFoundException("There is no user with the given id.");
		}
		
		return user.get();
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
