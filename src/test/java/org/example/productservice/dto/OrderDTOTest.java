package org.example.productservice.dto;

import lombok.extern.slf4j.Slf4j;
import org.example.modelproject.dto.OrderDTO;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Slf4j
@SpringBootTest(classes = OrderDTO.class)
public class OrderDTOTest {

    @Test
    public void testConstructor(){
        log.info("Test - testConstructor");
        OrderDTO order = new OrderDTO();
        assertThat(order).isNotNull();
    }

    @Test
    public void testMethodsData(){
        log.info("Test - testMethodsData");
        OrderDTO order = new OrderDTO();
        order.setId(1);
        order.setCustomerId(1);
        order.setProducts(new ArrayList<>());
        log.info("Order: "+order);
        assertThat(order.hashCode()).isNotEqualTo(new OrderDTO().hashCode());

        OrderDTO order2 = new OrderDTO();
        order2.setId(order.getId());
        order2.setCustomerId(order.getCustomerId());
        order2.setProducts(order.getProducts());
        assertTrue(order.equals(order2));
    }

}
