package com.user.entity;

import java.util.ArrayList;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonIgnore; // Uncomment if you use Jackson
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Document("SecurityUser")
//@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class User {
    public ObjectId getId() {
		return id;
	}

	public void setId(ObjectId id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
  @JsonIgnore
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<Post> getPosts() {
		return posts;
	}

	public void setPosts(List<Post> posts) {
		this.posts = posts;
	}

	public List<String> getRole() {
		return role;
	}

	public void setRole(List<String> role) {
		this.role = role;
	}

	@Id
    private ObjectId id;

    @NonNull
    private String username;

    @NonNull
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
   // Uncomment if you use Jackson and want to hide the password in JSON
    private String password;

    @DBRef
    private List<Post> posts = new ArrayList<>();

    private List<String> role = new ArrayList<>();

	public User(ObjectId id, @NonNull String username, List<Post> posts, List<String> role) {
		super();
		this.id = id;
		this.username = username;
		this.posts = posts;
		this.role = role;
	}
    
   
}
