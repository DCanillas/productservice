package org.example.productservice.integration;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.example.productservice.dto.OrderDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.File;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class OrderIntegrationTest {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate testRestTemplate;

    private OrderDTO order;

    @BeforeEach
    public void setUp() throws Exception {
        order = new ObjectMapper().readValue(
                new File("src/test/resource/Order.json"),
                new TypeReference<OrderDTO>() {
                });
    }

    @Test
    public void getAllOrdersTest(){
        log.info("Test - getAllOrdersTest");
        String url = "http://localhost:"+port+"/api/v1/order";

        HttpEntity<List<OrderDTO>> requestEntity = new HttpEntity<>(null,null);
        ResponseEntity<List> response = testRestTemplate.exchange(url,
                HttpMethod.GET, requestEntity, List.class);

        log.info("Response: "+response.getBody());
        assertThat(HttpStatus.OK).isEqualTo(response.getStatusCode());
    }

    @Test
    public void createOrderTest(){
        log.info("Test - createOrderTest");
        String url = "http://localhost:"+port+"/api/v1/order";

        HttpEntity<OrderDTO> requestEntity = new HttpEntity<>(null,null);
        ResponseEntity<OrderDTO> response = testRestTemplate.exchange(url,
                HttpMethod.POST, requestEntity, OrderDTO.class);

        log.info("Response: "+response.getBody());
        assertThat(HttpStatus.OK).isEqualTo(response.getStatusCode());
    }

    @Test
    public void getOrderByIdTest(){
        log.info("Test - getOrderByIdTest");
        String url = "http://localhost:"+port+"/api/v1/order/1";

        HttpEntity<OrderDTO> requestEntity = new HttpEntity<>(null,null);
        ResponseEntity<OrderDTO> response = testRestTemplate.exchange(url,
                HttpMethod.GET, requestEntity, OrderDTO.class);

        log.info("Response: "+response.getBody());
        assertThat(HttpStatus.OK).isEqualTo(response.getStatusCode());
    }

    @Test
    public void updateOrderTest(){
        log.info("Test - updateOrderTest");
        String url = "http://localhost:"+port+"/api/v1/order/9/customer/1";

        HttpEntity<OrderDTO> requestEntity = new HttpEntity<>(null,null);
        ResponseEntity<OrderDTO> response = testRestTemplate.exchange(url,
                HttpMethod.PUT, requestEntity, OrderDTO.class);

        log.info("Response: "+response.getBody());
        assertThat(HttpStatus.OK).isEqualTo(response.getStatusCode());
    }

    @Test
    public void assignProductToOrderTest(){
        log.info("Test - assignProductToOrderTest");
        String url = "http://localhost:"+port+"/api/v1/order/1/product/3";

        HttpEntity<OrderDTO> requestEntity = new HttpEntity<>(null,null);
        ResponseEntity<OrderDTO> response = testRestTemplate.exchange(url,
                HttpMethod.PUT, requestEntity, OrderDTO.class);

        log.info("Response: "+response.getBody());
        assertThat(HttpStatus.OK).isEqualTo(response.getStatusCode());
    }

    @Test
    public void deleteOrderTest(){
        log.info("Test - deleteOrderTest");
        String url = "http://localhost:"+port+"/api/v1/order/11";
        HttpEntity<OrderDTO> requestEntity = new HttpEntity<>(order,null);

        ResponseEntity<OrderDTO> response = testRestTemplate.exchange(url,
                HttpMethod.DELETE, requestEntity, OrderDTO.class);
        log.info("Response: "+response.getBody());

        assertThat(HttpStatus.OK).isEqualTo(response.getStatusCode());
    }
}