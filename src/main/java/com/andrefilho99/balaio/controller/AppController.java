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

import com.andrefilho99.balaio.exception.BalaioException;
import com.andrefilho99.balaio.exception.TokenException;
import com.andrefilho99.balaio.exception.UserNotFoundException;
import com.andrefilho99.balaio.model.Balaio;
import com.andrefilho99.balaio.model.User;
import com.andrefilho99.balaio.service.BalaioService;
import com.andrefilho99.balaio.service.UserService;

@RestController
@RequestMapping(value = "/users")
public class AppController {

	@Autowired
	private UserService userService;
	
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
	
	@PostMapping(value = "/login")
	public User login(@RequestHeader("number") String number) {
		try {
			return userService.login(number);
		} catch(TokenException ex) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
		} catch(UserNotFoundException ex) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage());
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
	
	//Balaio
	
	@PostMapping(value = "/{userId}/balaios")
	public void createBalaio(@PathVariable("userId")Integer userId, @RequestHeader("message") String message, @RequestHeader("to") String number, @RequestHeader("latitude") Double latitude, @RequestHeader("longitude") Double longitude){
		
		try {
			balaioService.create(message, userService.get(userId), userService.getByNumber(number), latitude, longitude);
		} catch(UserNotFoundException ex) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage());
		}
	}
	
	@GetMapping(value = "/{userId}/balaios/search", produces = "application/json")
	public Balaio searchBalaio(@PathVariable("userId")Integer userId, @RequestHeader("latitude") Double latitude, @RequestHeader("longitude") Double longitude) {
		try {
			return balaioService.getByDistance(userId, latitude, longitude);
		} catch(BalaioException ex) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage());
		}
	}
	
	@GetMapping(value = "/{userId}/balaios", produces = "application/json")
	public List<Balaio> getBalaio(@PathVariable("userId")Integer userId) {
		try {
			return balaioService.getBalaio(userId);
		} catch(BalaioException ex) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage());
		}
	}
	
	@GetMapping(value = "/{userId}/balaios/found", produces = "application/json")
	public List<Balaio> getBalaioFound(@PathVariable("userId")Integer userId) {
		try {
			return balaioService.getBalaiosFound(userId);
		} catch(BalaioException ex) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage());
		}
	}
	
	@GetMapping(value = "/{userId}/balaios/sent", produces = "application/json")
	public List<Balaio> getBalaioSent(@PathVariable("userId")Integer userId) {
		try {
			return balaioService.getBalaiosSent(userId);
		} catch(BalaioException ex) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage());
		}
	}
}
