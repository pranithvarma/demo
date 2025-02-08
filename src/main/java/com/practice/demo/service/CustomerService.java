package com.practice.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.practice.demo.entity.Customer;
import com.practice.demo.repo.CustomerRepository;

@Service
public class CustomerService {

	@Autowired
	private CustomerRepository repo;
	public Page<Customer> getcustomers(Pageable pageable) {
		// TODO Auto-generated method stub
		return repo.findAll(pageable);
	}
	public Customer createCustomer(Customer customer) {
	
		return repo.save(customer);
	}
	public Optional<Customer> getCustomer(int id) {
		// TODO Auto-generated method stub
		return repo.findById(id);
	}
	public void deletecustomer(int id) {
		// TODO Auto-generated method stub
		 repo.deleteById(id);
	}
	public Customer updateCustomer(Customer customer) {
		// TODO Auto-generated method stub
		return repo.save(customer);
	}
	public int getCustomersCount() {
		// TODO Auto-generated method stub
		return repo.getCustomerCount();
	}

}
