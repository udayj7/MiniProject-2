package com.uday.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.uday.entity.EnqStatusEntity;

public interface EnqStatusRepo extends JpaRepository<EnqStatusEntity, Integer> {

}
