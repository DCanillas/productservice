package org.example.productservice.integration;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.example.modelproject.dto.CustomerDTO;
import org.example.productservice.security.TestSecurityConfig;
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

import javax.transaction.Transactional;
import java.io.File;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = TestSecurityConfig.class)
public class CustomerIntegrationTest {
    @LocalServerPort
    private int port;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private TestRestTemplate testRestTemplate;

    private CustomerDTO customer;

    @BeforeEach
    public void setUp() throws Exception {
        customer = new ObjectMapper().readValue(
                new File("src/test/resource/Customer.json"),
                new TypeReference<CustomerDTO>() {
                });
    }

    //@Test
    @Transactional
    public void customersIntegrationTest(){
        log.info("Test - createCustomerTest");
        String urlCreate = "http://localhost:"+port+"/api/v1/customer";
        HttpEntity<CustomerDTO> requestEntityCreate = new HttpEntity<>(customer,null);
        ResponseEntity<CustomerDTO> responseCreate = testRestTemplate.exchange(urlCreate,
                HttpMethod.POST, requestEntityCreate, CustomerDTO.class);
        log.info("Response createCustomerTest: "+responseCreate.getBody());
        assertThat(HttpStatus.OK).isEqualTo(responseCreate.getStatusCode());

        log.info("Test - getCustomerByIdTest");
        String urlGet = "http://localhost:"+port+"/api/v1/customer/1";
        HttpEntity<CustomerDTO> requestEntityGet = new HttpEntity<>(customer,null);
        ResponseEntity<CustomerDTO> responseGet = testRestTemplate.exchange(urlGet,
                HttpMethod.GET, requestEntityGet, CustomerDTO.class);
        log.info("Response getCustomerByIdTest: "+responseGet.getBody());
        assertThat(HttpStatus.OK).isEqualTo(responseGet.getStatusCode());

        log.info("Test - updateCustomerTest");
        String urlUpdate = "http://localhost:"+port+"/api/v1/customer/1";
        HttpEntity<CustomerDTO> requestEntityUpdate = new HttpEntity<>(customer,null);
        ResponseEntity<CustomerDTO> responseUpdate = testRestTemplate.exchange(urlUpdate,
                HttpMethod.PUT, requestEntityUpdate, CustomerDTO.class);
        log.info("Response updateCustomerTest: "+responseUpdate.getBody());
        assertThat(HttpStatus.OK).isEqualTo(responseUpdate.getStatusCode());

        log.info("Test - getAllCustomersTest");
        String urlGetAll = "http://localhost:"+port+"/api/v1/customer";
        HttpEntity<List<CustomerDTO>> requestEntityGetAll = new HttpEntity<>(null,null);
        ResponseEntity<List> responseGetAll = testRestTemplate.exchange(urlGetAll,
                HttpMethod.GET, requestEntityGetAll, List.class);
        log.info("Response getAllCustomersTest: "+responseGetAll.getBody());
        assertThat(HttpStatus.OK).isEqualTo(responseGetAll.getStatusCode());

        log.info("Test - deleteCustomerTest");
        String urlDel = "http://localhost:"+port+"/api/v1/customer/1";
        HttpEntity<CustomerDTO> requestEntityDel = new HttpEntity<>(customer,null);
        ResponseEntity<CustomerDTO> responseDel = testRestTemplate.exchange(urlDel,
                HttpMethod.DELETE, requestEntityDel, CustomerDTO.class);
        log.info("Response deleteCustomerTest: "+responseDel.getBody());
        assertThat(HttpStatus.OK).isEqualTo(responseDel.getStatusCode());
    }
}
