package com.andrefilho99.balaio.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.andrefilho99.balaio.exception.TokenException;
import com.andrefilho99.balaio.exception.UserNotFoundException;
import com.andrefilho99.balaio.model.User;
import com.andrefilho99.balaio.service.BalaioService;
import com.andrefilho99.balaio.service.ContactService;
import com.andrefilho99.balaio.service.UserService;

@RestController
@RequestMapping(value = "/users")
public class AppController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private ContactService contactService;
	
	@Autowired
	private BalaioService balaioService;
	
	//User
	
	@PostMapping(produces = "application/json")
	public User createUser(@RequestHeader("nickname") String nickname, @RequestHeader("number") String number) {
		return userService.create(nickname, number);
	}
	
	@PostMapping(value = "/{userId}/validate")
	public void validateUser(@PathVariable("userId")Integer userId, @RequestHeader("token") String token) {
		try {
			userService.validate(userId, token);
		} catch(TokenException ex) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
		}
	}
	
	@GetMapping(value = "/{userId}", produces = "application/json")
	public User getUser(@PathVariable("userId")Integer userId) {
		return userService.get(userId);
	}
	
	@GetMapping(produces = "application/json")
	public List<User> getAllUsers() {
		return userService.getAll();
	}
	
	//Contact
	
	@PostMapping(value = "/{userId}/contacts")
	public void addContact(@PathVariable("userId")Integer userId, @RequestHeader("nickname") String nickname){
		try{
			contactService.create(userService.get(userId), userService.getByNickname(nickname));
		} catch(UserNotFoundException ex) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage());
		}
	}
	
	//Balaio
	
	@PostMapping(value = "/{userId}/balaios")
	public void createBalaio(@PathVariable("userId")Integer userId, @RequestHeader("message") String message, @RequestHeader("to") String nickname, @RequestHeader("latitude") Double latitude, @RequestHeader("longitude") Double longitude){
		balaioService.create(message, userService.get(userId), userService.getByNickname(nickname), latitude, longitude);
	}
}
