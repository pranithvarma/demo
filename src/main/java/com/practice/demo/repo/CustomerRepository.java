package com.practice.demo.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.practice.demo.entity.Customer;
import com.practice.demo.entity.UserAddress;

@Repository
public interface CustomerRepository extends JpaRepository<Customer,Integer>{

	@Query(value = "SELECT count(*) from customer", nativeQuery = true)
    int getCustomerCount();
	
	@Query(value="select u from UserAddress u")
	List<UserAddress> findByAddress();
	

	List<Optional<Customer>> findByfirstname(String firstname);
	
	

}
