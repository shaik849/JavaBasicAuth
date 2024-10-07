package com.user.reposiratory;



import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.user.entity.Post;
import com.user.entity.User;

@Repository
public interface UserReposiratoty extends MongoRepository<User, ObjectId> {

	public Optional<User> findByUsername(String username);
//	 List<Post> findAllIdIn(ObjectId userId);
}
