/**
 * 
 */
package com.microservices.customerservice.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.microservices.customerservice.model.Customer;

/**
 * @author 474984
 *
 */

@Component
public class AMQPPublisher {
	
	Logger log = LoggerFactory.getLogger(AMQPPublisher.class);
	
	@Autowired
	private AmqpTemplate amqpTemplate;

	@Value("${rabbitmq.exchangeName}")
	private String exchange;

	@Value("${rabbitmq.routingKey}")
	private String routingKey;

	/**
	 * @param customer
	 */
	public void produceMsg(Customer customer){
		log.info("Inside produceMsg ");
		amqpTemplate.convertSendAndReceive(exchange, routingKey, customer);
		log.info(">>>>>> Message published sucessfully - " + customer);
	}
}
