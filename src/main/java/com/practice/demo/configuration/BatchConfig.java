package com.practice.demo.configuration;
import org.springframework.batch.core.*;
import org.springframework.batch.core.configuration.annotation.*;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.support.JobRepositoryFactoryBean;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.support.transaction.ResourcelessTransactionManager;
import org.springframework.context.annotation.*;
import org.springframework.core.io.ClassPathResource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import com.practice.demo.DTO.CustomerDTO;
import com.practice.demo.entity.Customer;
import com.practice.demo.repo.CustomerRepository;

import jakarta.activation.DataSource;
import jakarta.validation.Valid;

@Configuration
public class BatchConfig {

    private final JobRepository jobRepository;
    private final CustomerRepository customerRepository;
    private final JpaTransactionManager transactionManager;

    public BatchConfig(JobRepository jobRepository, 
                       CustomerRepository customerRepository, 
                       JpaTransactionManager transactionManager) {
        this.jobRepository = jobRepository;
        this.customerRepository = customerRepository;
        this.transactionManager = transactionManager;
    }
   

    @Bean
    public FlatFileItemReader<CustomerDTO> reader() {
        FlatFileItemReader<CustomerDTO> reader = new FlatFileItemReader<>();
        reader.setResource(new ClassPathResource("customers.csv"));
        reader.setLinesToSkip(1); // Skip header

        reader.setLineMapper((line, lineNumber) -> {
            String[] fields = line.split(",");
            CustomerDTO customerDTO = new CustomerDTO();
            customerDTO.setFirstname(fields[0]);
            customerDTO.setLastname(fields[1]);
            return customerDTO;
        });

        return reader;
    }

    @Bean
    public ItemProcessor<@Valid CustomerDTO, Customer> processor() {
        return customerDTO -> {
            Customer customer = new Customer();
            customer.setFirstname(customerDTO.getFirstname());
            customer.setLastname(customerDTO.getLastname());
            return customer;
        };
    }

    @Bean
    public ItemWriter<Customer> writer() {
        return customers -> customerRepository.saveAll(customers);
    }

    @Bean
    public Step step1() {
        return new StepBuilder("step1", jobRepository)
                .<CustomerDTO, Customer>chunk(10, transactionManager)
                .reader(reader())
                .processor(processor())
                .writer(writer())
                .build();
    }

    @Bean
    public Job importCustomerJob() {
        return new JobBuilder("importCustomerJob", jobRepository)
                .start(step1())
                .build();
    }
}
