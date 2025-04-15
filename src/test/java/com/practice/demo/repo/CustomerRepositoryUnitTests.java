package com.practice.demo.repo;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;

import com.practice.demo.entity.Customer;

@DataJpaTest
public class CustomerRepositoryUnitTests {

    @Autowired
    private CustomerRepository repo;
    
    @Autowired
    private TestEntityManager entityManager; // Allows direct entity persistence in tests

    private Customer customer1;
    private Customer customer2;

    @BeforeEach
    void setUp() {
        // Creating and persisting test data before each test
        customer1 = new Customer();
        customer1.setFirstname("John Doe");
        customer1.setLastname("donald");
        
        customer2 = new Customer();
        customer2.setFirstname("Jane Doe");
        customer2.setLastname("donald");

        entityManager.persist(customer1);
        entityManager.persist(customer2);
        entityManager.flush(); // Forces data to be written to DB
    }

  

    @Test
    public void getCustomerTest() {
  

        // Action
        Customer fetchedCustomer = repo.findById(customer1.getCustomerid())
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        // Verify
        System.out.println(fetchedCustomer);
        Assertions.assertThat(fetchedCustomer.getCustomerid()).isEqualTo(customer1.getCustomerid());
        Assertions.assertThat(fetchedCustomer.getFirstname()).isEqualTo("John Doe");
    }
    
    @Test
    public void getAllCustomersTest()
    {
    	assertEquals(repo.findAll().size(),2);
    }
    
    @Test
    public void updateCustomerTest() {
    	Customer customer = repo.findById(customer1.getCustomerid()).get();
    	customer.setFirstname("Trump");
    	Customer updatedCustomer=repo.save(customer);
    	assertEquals(customer1.getCustomerid(),updatedCustomer.getCustomerid());
    	assertEquals(updatedCustomer.getFirstname(),"Trump");
    	

        System.out.println(updatedCustomer);
    	
    }
    
    @Test
    public void deleteCustomerByIdTEst()
    {
    	int id=customer1.getCustomerid();
    	repo.deleteById(customer1.getCustomerid());
    	assertThat(Optional.of(repo.findById(id)).isEmpty());
    }
}
