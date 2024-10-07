package com.user.controller;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.user.entity.Post;
import com.user.entity.User;
import com.user.service.PostService;

@RestController
@RequestMapping("/api")
public class PostController {

	@Autowired
	private PostService postService;
	
	@PostMapping("/post/{userId}")
	public ResponseEntity<?> savePost(@RequestBody Post post, @PathVariable ObjectId userId){
		return postService.savePost(userId, post);
	}
	
	@GetMapping("/post/{userId}")
	public ResponseEntity<?> getPostsByUserId(@PathVariable ObjectId userId){
		return postService.findAllPostsByUser(userId);
	}
}
