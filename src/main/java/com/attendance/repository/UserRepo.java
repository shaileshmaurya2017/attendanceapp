package com.attendance.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.attendance.entity.User;

@Repository
public interface UserRepo extends JpaRepository<User, Integer> {

	User getByUsername(String username);
	
	@Query("SELECT u FROM User u WHERE u.username = :username")
    public User getUserByUsername(@Param("username") String username);

	@Query("SELECT u FROM User u WHERE u.role != 'admin'")
	List<User>  getByUsersWhereRoleisNotAdmin();
}
