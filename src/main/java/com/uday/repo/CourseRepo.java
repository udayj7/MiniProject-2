package com.uday.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.uday.entity.CourseEntity;

public interface CourseRepo extends JpaRepository<CourseEntity, Integer> {

}
