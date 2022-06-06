package com.nttdata.consultmanagement.Repository;

import com.nttdata.consultmanagement.Model.Customer;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface customerRepository  extends ReactiveMongoRepository <Customer, String>{
    
}
