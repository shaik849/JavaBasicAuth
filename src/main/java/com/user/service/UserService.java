package com.user.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.user.entity.Post;
import com.user.entity.User;
import com.user.reposiratory.PostReposiratory;
import com.user.reposiratory.UserReposiratoty;

@Component
//@Validated
public class UserService implements UserServiceInfo {
	
	@Autowired
	private UserReposiratoty userReposiratory;
	
	@Autowired
	private PostReposiratory postReposiratory;
	
    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

	@Override
	@Transactional
	public ResponseEntity<?> saveUser(User user) {
		try {
			
            if (user == null) {
                return new ResponseEntity<>("User cannot be null", HttpStatus.BAD_REQUEST);
            }

            // Additional validation if necessary
//            System.out.println(user);
            if (user.getUsername() == null || user.getUsername().trim().isEmpty()) {
                return new ResponseEntity<>("Username cannot be empty", HttpStatus.BAD_REQUEST);
            }
            if (user.getPassword() == null || user.getPassword().trim().isEmpty()) {
                return new ResponseEntity<>("Password cannot be empty", HttpStatus.BAD_REQUEST);
            }
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            User savedUser = userReposiratory.save(user);
            return new ResponseEntity<>(savedUser, HttpStatus.OK);
        } catch (Exception e) {
            // Log the exception for debugging
            // For example: logger.error("Error saving user", e);
            
            String errorMessage = "An error occurred while saving the user: " + e.getMessage();
            return new ResponseEntity<>(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
        }
			}
	

	@Override
	public ResponseEntity<?> findAllUsers() {
		// TODO Auto-generated method stub
		try{
			List<User> user = userReposiratory.findAll();
			if(user.size() == 0){
				return new ResponseEntity<>("Users Not Found", HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<>(user, HttpStatus.OK) ;
		}
		catch (Exception e) {
			// TODO: handle exception
			 String errorMessage = "An error occurred while getting the users: " + e.getMessage();
		     return new ResponseEntity<>(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Override
	public ResponseEntity<?> findSingleUser(ObjectId userId) {
		// TODO Auto-generated method stub
		try {
			User user = userReposiratory.findById(userId).orElse(null);
			if(user == null) {
				return new ResponseEntity<>("User not found", HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<>(user, HttpStatus.OK);
		}
		catch (Exception e) {
			// TODO: handle exception
			 String errorMessage = "An error occurred while getting the user: " + e.getMessage();
		        return new ResponseEntity<>(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Override
	public ResponseEntity<?> updateUser(ObjectId userId, User user) {
		// TODO Auto-generated method stub
		try {
			User isUserFound = userReposiratory.findById(userId).orElse(null);
			if(isUserFound == null) {
				return new ResponseEntity<>("User Not Found", HttpStatus.NOT_FOUND);
			}
			if(user.getUsername() != null) {
	    		isUserFound.setUsername(user.getUsername());
	    	}
	    	
			User updatedUser = userReposiratory.save(isUserFound);
			return new ResponseEntity<>(updatedUser, HttpStatus.OK);
		}
		catch (Exception e) {
			// TODO: handle exception
			 String errorMessage = "An error occurred while updating the user: " + e.getMessage();
		        return new ResponseEntity<>(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Override
	@Transactional
	public ResponseEntity<?> deleteUser(ObjectId userId) {
		// TODO Auto-generated method stub
		try {
			User isUserFound = userReposiratory.findById(userId).orElse(null);
			if(isUserFound == null) {
				return new ResponseEntity<>("User Not Found", HttpStatus.NO_CONTENT);
			}
	                // Get the list of post IDs associated with the user
	                List<ObjectId> postIds = isUserFound.getPosts().stream()
	                                            .map(Post:: getPostId)
	                                            .collect(Collectors.toList());

	                // Delete posts by their IDs
	                if (!postIds.isEmpty()) {
	                    postReposiratory.deleteByPostIdIn(postIds);
	                }
			userReposiratory.deleteById(userId);
			return new ResponseEntity<>("User Deleted Successfully", HttpStatus.OK);
		}
		catch (Exception e) {
			// TODO: handle exception
			 String errorMessage = "An error occurred while deleting the user: " + e.getMessage();
		        return new ResponseEntity<>(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	
}
