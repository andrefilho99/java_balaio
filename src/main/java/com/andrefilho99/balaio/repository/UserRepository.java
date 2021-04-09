package com.andrefilho99.balaio.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.andrefilho99.balaio.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer>{
	
	@Query(value = "select * from USER where NICKNAME = ?1", nativeQuery = true)
	public User findByNickname(String nickname);
	
	@Query(value = "select * from USER where NUMBER = ?1", nativeQuery = true)
	public User findByNumber(String number);
}
