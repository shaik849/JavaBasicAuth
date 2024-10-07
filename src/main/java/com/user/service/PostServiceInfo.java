package com.user.service;

import org.bson.types.ObjectId;
import org.springframework.http.ResponseEntity;
import com.user.entity.Post;

public interface PostServiceInfo {
	public ResponseEntity<?> savePost(ObjectId userId, Post Post);
	public ResponseEntity<?> findAllPosts();
	public ResponseEntity<?> findSinglePost(ObjectId PostId);
	public ResponseEntity<?> updatePost(ObjectId PostId, Post Post);
	public ResponseEntity<?> deletePost(ObjectId PostId);
	public ResponseEntity<?> findAllPostsByUser(ObjectId userId);
}
