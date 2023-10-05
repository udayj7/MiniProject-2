package com.uday.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.uday.entity.UserDtlsEntity;

public interface UserDtlsRepo extends JpaRepository<UserDtlsEntity, Integer> {

	public UserDtlsEntity findByEmail(String email);

	public UserDtlsEntity findByEmailAndPwd(String email, String pwd);


}
