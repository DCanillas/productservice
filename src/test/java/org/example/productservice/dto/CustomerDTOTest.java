package org.example.productservice.dto;

import lombok.extern.slf4j.Slf4j;
import org.example.modelproject.dto.CategoryDTO;
import org.example.modelproject.dto.CustomerDTO;
import org.example.productservice.security.TestSecurityConfig;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Slf4j
@SpringBootTest(classes = TestSecurityConfig.class)
public class CustomerDTOTest {

    @Test
    public void testConstructor(){
        log.info("Test - testConstructor");
        CustomerDTO customer = new CustomerDTO();
        assertThat(customer).isNotNull();
    }

    @Test
    public void testMethodsData(){
        log.info("Test - testMethodsData");
        CustomerDTO customer = new CustomerDTO();
        customer.setId(1);
        customer.setName("Batman");
        customer.setEmail("batman@epam.es");
        customer.setOrders(new ArrayList<>());
        log.info("Customer: "+customer.toString());
        assertThat(customer.hashCode()).isNotEqualTo(new CategoryDTO().hashCode());

        CustomerDTO customer2 = new CustomerDTO();
        customer2.setId(customer.getId());
        customer2.setName(customer.getName());
        customer2.setEmail(customer.getEmail());
        customer2.setOrders(customer.getOrders());
        assertTrue(customer.equals(customer2));
    }

}
