package org.example.productservice.integration;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.example.modelproject.dto.OrderDTO;
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
import org.springframework.test.context.ActiveProfiles;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class OrderIntegrationTest {
    @LocalServerPort
    private int port;

    @Autowired
    private ObjectMapper objectMapper;

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
    public void ordersIntegrationTest(){
        log.info("Test - createOrderTest");
        String urlCreate = "http://localhost:"+port+"/api/v1/order";
        HttpEntity<OrderDTO> requestEntityCreate = new HttpEntity<>(new OrderDTO(),null);
        ResponseEntity<OrderDTO> responseCreate = testRestTemplate.exchange(urlCreate,
                HttpMethod.POST, requestEntityCreate, OrderDTO.class);
        log.info("Response createOrderTest: "+responseCreate.getBody());
        assertThat(HttpStatus.OK).isEqualTo(responseCreate.getStatusCode());

        log.info("Test - getOrderByIdTest");
        String urlGet = "http://localhost:"+port+"/api/v1/order/1";
        HttpEntity<OrderDTO> requestEntityGet = new HttpEntity<>(null,null);
        ResponseEntity<OrderDTO> responseGet = testRestTemplate.exchange(urlGet,
                HttpMethod.GET, requestEntityGet, OrderDTO.class);
        log.info("Response getOrderByIdTest: "+responseGet.getBody());
        assertThat(HttpStatus.OK).isEqualTo(responseGet.getStatusCode());

        log.info("Test - getAllOrdersTest");
        String urlGetAll = "http://localhost:"+port+"/api/v1/order";
        HttpEntity<List<OrderDTO>> requestEntityGetAll = new HttpEntity<>(null,null);
        ResponseEntity<List> responseGetAll = testRestTemplate.exchange(urlGetAll,
                HttpMethod.GET, requestEntityGetAll, List.class);
        log.info("Response getAllOrdersTest: "+responseGetAll.getBody());
        assertThat(HttpStatus.OK).isEqualTo(responseGetAll.getStatusCode());

        log.info("Test - deleteOrderTest");
        String urlDel = "http://localhost:"+port+"/api/v1/order/1";
        HttpEntity<OrderDTO> requestEntityDel = new HttpEntity<>(order,null);
        ResponseEntity<OrderDTO> responseDel = testRestTemplate.exchange(urlDel,
                HttpMethod.DELETE, requestEntityDel, OrderDTO.class);
        log.info("Response deleteOrderTest: "+responseDel.getBody());
        assertThat(HttpStatus.OK).isEqualTo(responseDel.getStatusCode());
    }
}