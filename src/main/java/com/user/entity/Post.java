package com.user.entity;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Document("post")
@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class Post {

	@Id
	private ObjectId postId;
	
	@NonNull
	private String postTitle;
	
	private String postInfo;
}
