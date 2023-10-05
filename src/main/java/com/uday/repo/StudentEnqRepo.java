package com.uday.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.uday.entity.StudentEnqEntity;

public interface StudentEnqRepo extends JpaRepository<StudentEnqEntity, Integer> {

}
