package com.practice.demo.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.practice.demo.entity.Customer;
import com.practice.demo.repo.CustomerRepository;
import com.practice.demo.service.CustomerService;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@RestController
@Tag(name="user controller")
@RequestMapping("api/customers")

public class Controller {

	private final CustomerService service ;
	private  final CustomerRepository repo;
	
	public Controller(CustomerService service,CustomerRepository repo) {
		this.repo=repo;
		this.service=service;
	}
	@GetMapping("/hello")
	public String helloworld(HttpServletRequest httpservletrequest) {
		return "hello world"+httpservletrequest.getSession().getId();
	}
	@GetMapping
	public List<Customer> getcustomers(){
		return service.getcustomers();
		
	}
	@GetMapping("/{id}")
	public Customer getCustomer(@PathVariable int id) {
		return service.getCustomer(id).orElseThrow(()->new UserNotFoundException("user with id:"+id+"is not present"));
	}
	@PostMapping
	public ResponseEntity<Customer> createCustomer(@Valid @RequestBody Customer customer){

		Customer createdcustomer = service.createCustomer(customer);
		return ResponseEntity.ok(createdcustomer);
	}
	@PutMapping("/{id}")
	public ResponseEntity<Customer> updateCustomer(@RequestBody Customer customer,@PathVariable int id){
		Customer updatedCustomer= service.updateCustomer(customer);
		return ResponseEntity.ok(updatedCustomer);
	}
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteCustomer(@PathVariable int id) {
		if(repo.existsById(id)) {
			service.deletecustomer(id);
			return  ResponseEntity.ok(" deleted successfully ");
			
		}
		else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("customer not found");
		}
		
	}
}
