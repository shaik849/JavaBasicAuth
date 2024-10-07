package com.user.reposiratory;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.user.entity.Post;

public interface PostReposiratory extends MongoRepository<Post, ObjectId> {
	void deleteByPostIdIn(List<ObjectId> ids);}
