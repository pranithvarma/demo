package com.practice.demo.controller;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.logging.Log;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.practice.demo.entity.Customer;
import com.practice.demo.entity.UserAddress;
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
@Slf4j

public class Controller {

	private final CustomerService service ;
	private  final CustomerRepository repo;
	
	 private static final Logger logger = Logger.getLogger(Controller.class.getName());

	
	public Controller(CustomerService service,CustomerRepository repo) {
		this.repo=repo;
		this.service=service;
	}
	@GetMapping("/hello")
	public String helloworld(HttpServletRequest httpservletrequest) {
		return "hello world"+httpservletrequest.getSession().getId();
	}
	@GetMapping
	public Page<Customer> getcustomers(
			@RequestParam(defaultValue="0") int page,
			@RequestParam(defaultValue="10")int size,
			@RequestParam(defaultValue="customerid") String sortBy,
			@RequestParam(defaultValue="true") Boolean ascending
			
			){
        Sort sort = ascending ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);
		
		return service.getcustomers(pageable);
		
	}
	
	
	@GetMapping("/address")
	public List<UserAddress> getAddresses(){
		return repo.findByAddress();
	}
	
	
	@GetMapping("/count")
	public int getCustomersCount() {
		return service.getCustomersCount();
	}
	
	
	@GetMapping("find/{firstname}")
	public List<Optional<Customer>> getCustomer(@PathVariable String firstname) {
		return repo.findByfirstname(firstname);
	}
	
	
	@Cacheable(value="datacache",key="#id")
	@GetMapping("/{id}")
	public ResponseEntity<Customer> getCustomer(@PathVariable int id) {
		  try {
	            Thread.sleep(2000);
	        } catch (InterruptedException e) {
	            Thread.currentThread().interrupt();
	        }
		return ResponseEntity.ok(service.getCustomer(id).orElseThrow(()->new UserNotFoundException("user with id:"+id+"is not present")));
	}
	

	@PostMapping
	public ResponseEntity<?> createCustomer(@Valid @RequestBody Customer customer,BindingResult bindingresult){

		logger.info("customer data request"+customer);
		logger.info("validation erros"+bindingresult);
		  if (bindingresult.hasErrors()) {
	            return ResponseEntity.badRequest().body(bindingresult.getFieldErrors());
	        }
		Customer createdcustomer = service.createCustomer(customer);
		return ResponseEntity.ok(createdcustomer);
	}
	
	@CachePut(value="datacache",key="#id")
	@PutMapping("/{id}")
	public ResponseEntity<Customer> updateCustomer(@RequestBody Customer customer,@PathVariable int id){
		Customer updatedCustomer= service.updateCustomer(customer);
		return ResponseEntity.ok(updatedCustomer);
	}
	

	@CacheEvict(value="datacache",key="#id")
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
