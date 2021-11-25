package com.fatou.rest.webservice.restfulwebservices5.controller;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.fatou.rest.webservice.restfulwebservices5.exception.UserNotFoundException;

import com.fatou.rest.webservice.restfulwebservices5.tima.User;
import com.fatou.rest.webservice.restfulwebservices5.tima.UserDaoService;

@RestController
public class UserController {
	
	@Autowired 
	private UserDaoService service;
	
	@GetMapping("/users")
	public List<User> retrieveAllUsers(){
		return service.findAll();
	}
	
	@GetMapping("/users/{id}")
	public User retrieveUser(@PathVariable int id) {
		User user = service.findOne(id);
		if(user == null) {
			throw new UserNotFoundException("id-" +id);
		}
		return user;
	}
	
	@PostMapping("/users")
	public ResponseEntity<Object> createUser(@RequestBody User user){
		User saveUser = service.saveUser(user);
		saveUser.getId();
		URI location = ServletUriComponentsBuilder
				.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(saveUser.getId()).toUri();
		
		return ResponseEntity.created(location).build();
	}
	
	@DeleteMapping("/users/{id}")
	public void deleteUser(@PathVariable int id) {
		User user = service.deleteById(id);
		
		if(user==null) {
			throw new UserNotFoundException("id-" +id);
		}
	}
	
	

}
