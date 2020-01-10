/**
 * 
 */
package com.microservices.customerservice.service;

import org.springframework.data.jpa.repository.JpaRepository;

import com.microservices.customerservice.model.Customer;

/**
 * @author 474984
 *
 */
public interface CustomerRespository extends JpaRepository<Customer, Long>{

}
