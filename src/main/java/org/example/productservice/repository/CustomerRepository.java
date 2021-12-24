package org.example.productservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import org.example.modelproject.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

}
