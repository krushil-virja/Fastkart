package com.FastKart.Repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.FastKart.entities.User;

public interface UserRepository extends CrudRepository<User, Integer> {

	@Query("select u from User u where u.email= :email")
	public User getUserByUserName(@Param("email") String email);
	
	
	 boolean existsByEmail(String email);
}
