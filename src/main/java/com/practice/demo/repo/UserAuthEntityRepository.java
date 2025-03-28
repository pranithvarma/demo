package com.practice.demo.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import com.practice.demo.entity.UserAuthEntity;

@Repository
public interface UserAuthEntityRepository extends JpaRepository<UserAuthEntity,Long>{
	UserDetails findByUsername(String username);

}
