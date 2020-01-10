/**
 * 
 */
package com.microservices.customerservice.controller;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.microservices.customerservice.model.Customer;
import com.microservices.customerservice.service.CustomerRespository;
import com.microservices.customerservice.util.AMQPPublisher;

/**
 * @author 474984
 *
 */
@RestController
@RequestMapping("/customerservice")
public class CustomerController {
	
	Logger log = LoggerFactory.getLogger(CustomerController.class);
	
	@Autowired
	CustomerRespository customerRespository;

	@Autowired
	AMQPPublisher amqpPublisher;
	
	/**
	 * @param custmerId
	 * @return
	 */
	@GetMapping("/customers/{custmerId}")
	public Customer getCustomerDetails(@PathVariable Long custmerId){
		log.info("Inside getCustomerDetails method ");
		Optional<Customer> result = customerRespository.findById(custmerId);
		if(result.isPresent()) {
			return result.get();
		} else  {
			return new Customer();
		}
	}

	/**
	 * @return
	 */
	@GetMapping("/customers")
	public List<Customer> getAllCustomerDetails(){
		log.info("Inside getAllCustomerDetails method ");
		return customerRespository.findAll();
	}

	/**
	 * @param customer
	 * @return
	 */
	@PostMapping("/customers/create")
	public Customer createCustomer(@RequestBody Customer customer){
		log.info("Inside createCustomer method ");
		Customer cust = customerRespository.save(customer);
		amqpPublisher.produceMsg(customer);
		return cust;
	}

	/**
	 * @param custmerId
	 * @return
	 */
	@GetMapping("/customers/{custmerId}/delete")
	public String deleteCustomerDetails(@PathVariable Long custmerId){
		log.info("Inside deleteCustomerDetails method ");
		customerRespository.deleteById(custmerId);
		return "Deleted Sucessfully";
	}

}
