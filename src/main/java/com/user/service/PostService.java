package com.user.service;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Component;
import com.user.entity.Post;
import com.user.entity.User;
import com.user.reposiratory.PostReposiratory;
import com.user.reposiratory.UserReposiratoty;

@Component
public class PostService implements PostServiceInfo {
		
		@Autowired
		private UserReposiratoty userReposiratoty;
		
		@Autowired
		private PostReposiratory postReposiratory;

		@Override
		@Transactional
		public ResponseEntity<?> savePost(ObjectId userId, Post post) {
			// TODO Auto-generated method stub
		try {
			User user = userReposiratoty.findById(userId).orElse(null);
			if(user == null) {
				return new ResponseEntity<>("User Not found", HttpStatus.NOT_FOUND);
			}
//			System.out.println(post);
			if(post.getPostTitle() == null) {
				return new ResponseEntity<>("Post title is Not Null", HttpStatus.NOT_FOUND);
			}
			Post posts =postReposiratory.save(post);
			user.getPosts().add(post);
			userReposiratoty.save(user);
			
			return new ResponseEntity<>(posts, HttpStatus.OK);
		}
		catch (Exception e) {
			// TODO: handle exception
			 String errorMessage = "An error occurred while saving the post: " + e.getMessage();
		        return new ResponseEntity<>(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		}

		@Override
		public ResponseEntity<?> findAllPosts() {
			// TODO Auto-generated method stub
			try {
				List<Post> post = postReposiratory.findAll();
				if(post.isEmpty()) return new ResponseEntity<>("No posts available", HttpStatus.NOT_FOUND);
				return new ResponseEntity<>(post, HttpStatus.OK);
			}
			catch (Exception e) {
				// TODO: handle exception
				 String errorMessage = "An error occurred while getting the posts: " + e.getMessage();
			        return new ResponseEntity<>(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
				
			}
		}

		@Override
		public ResponseEntity<?> findSinglePost(ObjectId PostId) {
			// TODO Auto-generated method stub
			try {
				Post post = postReposiratory.findById(PostId).orElse(null);
				if(post == null) return new ResponseEntity<>("Post not available", HttpStatus.NOT_FOUND);
				return new ResponseEntity<>(post, HttpStatus.OK);
			}
			catch (Exception e) {
				// TODO: handle exception
				 String errorMessage = "An error occurred while getting the posts: " + e.getMessage();
			        return new ResponseEntity<>(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
				
			}
		}

		@Override
		public ResponseEntity<?> updatePost(ObjectId postId, Post post) {
			// TODO Auto-generated method stub
			
			try {
				Post isPostFound = postReposiratory.findById(postId).orElse(null);
				if(isPostFound == null) return new ResponseEntity<>("Post not available", HttpStatus.NOT_FOUND);
				if(post.getPostTitle() != null){
					isPostFound.setPostTitle(post.getPostTitle());
				}
				if(post.getPostInfo() != null){
					isPostFound.setPostInfo(post.getPostInfo());
				}
				
				Post updatedPost = postReposiratory.save(isPostFound);
				return new ResponseEntity<>(updatedPost, HttpStatus.OK);
			}
			catch (Exception e) {
				// TODO: handle exception
				 String errorMessage = "An error occurred while upating the post: " + e.getMessage();
			        return new ResponseEntity<>(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}

		@Override
		public ResponseEntity<?> deletePost(ObjectId PostId) {
			// TODO Auto-generated method stub
			try {
				Post post = postReposiratory.findById(PostId).orElse(null);
				if(post == null) return new ResponseEntity<>("Post not available", HttpStatus.NOT_FOUND);
				postReposiratory.delete(post);
				return new ResponseEntity<>("Post successfully deleted", HttpStatus.OK);
			}
			catch (Exception e) {
				// TODO: handle exception
				 String errorMessage = "An error occurred while deleting the post: " + e.getMessage();
			        return new ResponseEntity<>(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}

		@Override
		public ResponseEntity<?> findAllPostsByUser(ObjectId userId) {
			// TODO Auto-generated method stub
			try {
			User user = userReposiratoty.findById(userId).orElse(null);
			if(user == null) {
				return new ResponseEntity<>("User Not found", HttpStatus.NOT_FOUND);
			}		
			List<Post> posts = user.getPosts();
			return new ResponseEntity<>(posts, HttpStatus.OK);
			}
			catch (Exception e) {
				// TODO: handle exception
				 String errorMessage = "An error occurred while getting the posts: " + e.getMessage();
			        return new ResponseEntity<>(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
				
			}
			}
	

	}

