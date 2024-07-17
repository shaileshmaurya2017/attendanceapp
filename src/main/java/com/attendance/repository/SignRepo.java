package com.attendance.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.attendance.entity.SignDetail;

@Repository
public interface SignRepo extends JpaRepository<SignDetail, Integer> {

	List<SignDetail> getByUserIdOrderBySignindatetimeDesc(int id);

	SignDetail findFirstByUserIdOrderBySignindatetimeDesc(int i);

	
}
