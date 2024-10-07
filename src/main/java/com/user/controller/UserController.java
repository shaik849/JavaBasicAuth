package com.user.controller;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
//import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.user.entity.User;
import com.user.service.UserService;

//import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api")
public class UserController {
	
	@Autowired
	private UserService userService;

	@GetMapping("/user")
	public ResponseEntity<?> findAllUsers(){
		return userService.findAllUsers();
	}
	
	@GetMapping("/user/{id}")
	public ResponseEntity<?> findUserById(@PathVariable ObjectId id){
		return userService.findSingleUser(id);
	}
	
	@PostMapping("/user")
	public ResponseEntity<?> createEntry(@RequestBody User user) {
//		System.out.println(user);
		return userService.saveUser(user);
	}
	
	@PutMapping("/user/{id}")
	public ResponseEntity<?> updateUser(@PathVariable ObjectId id, @RequestBody User user) {
		//TODO: process PUT request
		return userService.updateUser(id, user);
	}
	
	@DeleteMapping("/user/{id}")
	public ResponseEntity<?> deleteUser(@PathVariable ObjectId id){
		return userService.deleteUser(id);
	}
	
	@PreAuthorize("hasRole('ADMIN')")  //to work this add @EnableMethodSecurity in SecurityConfig
	@GetMapping("/admin")
	public String getAdmins() {
		return "hello admin";
	}
	
//	@GetMapping("/csrf")
//	public CsrfToken getCsrf(HttpServletRequest request) {
//		return (CsrfToken) request.getAttribute("_csrf");
//	}

}
