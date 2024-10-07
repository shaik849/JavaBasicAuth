package com.user.service;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.http.ResponseEntity;

import com.user.entity.User;

public interface UserServiceInfo {

	public ResponseEntity<?> saveUser(User user);
	public ResponseEntity<?> findAllUsers();
	public ResponseEntity<?> findSingleUser(ObjectId userId);
	public ResponseEntity<?> updateUser(ObjectId userId, User user);
	public ResponseEntity<?> deleteUser(ObjectId userId);
}
