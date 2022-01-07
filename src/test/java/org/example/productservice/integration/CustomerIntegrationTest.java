package org.example.productservice.integration;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.example.modelproject.dto.CustomerDTO;
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
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
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

    @Test
    public void getAllCustomersTest(){
        log.info("Test - getAllCustomersTest");
        String url = "http://localhost:"+port+"/api/v1/customer";

        HttpEntity<List<CustomerDTO>> requestEntity = new HttpEntity<>(null,null);
        ResponseEntity<List> response = testRestTemplate.exchange(url,
                HttpMethod.GET, requestEntity, List.class);

        log.info("Response: "+response.getBody());
        assertThat(HttpStatus.OK).isEqualTo(response.getStatusCode());
    }

    @Test
    public void createCustomerTest(){
        log.info("Test - createCustomerTest");
        String url = "http://localhost:"+port+"/api/v1/customer";

        HttpEntity<CustomerDTO> requestEntity = new HttpEntity<>(customer,null);
        ResponseEntity<CustomerDTO> response = testRestTemplate.exchange(url,
                HttpMethod.POST, requestEntity, CustomerDTO.class);

        log.info("Response: "+response.getBody());
        assertThat(HttpStatus.OK).isEqualTo(response.getStatusCode());
    }

    @Test
    public void getCustomerByIdTest(){
        log.info("Test - getCustomerByIdTest");
        String url = "http://localhost:"+port+"/api/v1/customer/1";

        HttpEntity<CustomerDTO> requestEntity = new HttpEntity<>(customer,null);
        ResponseEntity<CustomerDTO> response = testRestTemplate.exchange(url,
                HttpMethod.GET, requestEntity, CustomerDTO.class);

        log.info("Response: "+response.getBody());
        assertThat(HttpStatus.OK).isEqualTo(response.getStatusCode());
    }

    @Test
    public void updateCustomerTest(){
        log.info("Test - updateCustomerTest");
        String url = "http://localhost:"+port+"/api/v1/customer/1";

        HttpEntity<CustomerDTO> requestEntity = new HttpEntity<>(customer,null);
        ResponseEntity<CustomerDTO> response = testRestTemplate.exchange(url,
                HttpMethod.PUT, requestEntity, CustomerDTO.class);

        log.info("Response: "+response.getBody());
        assertThat(HttpStatus.OK).isEqualTo(response.getStatusCode());
    }

    @Test
    public void deleteCustomerTest() throws JsonProcessingException {
        log.info("Test - deleteCustomerTest");

        String urlGet = "http://localhost:"+port+"/api/v1/customer";

        HttpEntity<List<CustomerDTO>> requestEntityGet = new HttpEntity<>(null,null);
        ResponseEntity<String> responseGet = testRestTemplate.exchange(urlGet,
                HttpMethod.GET, requestEntityGet, String.class);

        CustomerDTO[] customers = objectMapper.readValue(responseGet.getBody(),
                CustomerDTO[].class);
        List<CustomerDTO> customerList = Arrays.asList(customers);

        Long customerListMaxId = customerList.stream().max((x,y) -> (int) (x.getId() - y.getId())).get().getId();

        String url = "http://localhost:"+port+"/api/v1/customer/"+customerListMaxId;
        HttpEntity<CustomerDTO> requestEntity = new HttpEntity<>(customer,null);

        ResponseEntity<CustomerDTO> response = testRestTemplate.exchange(url,
                HttpMethod.DELETE, requestEntity, CustomerDTO.class);
        log.info("Response: "+response.getBody());

        assertThat(HttpStatus.OK).isEqualTo(response.getStatusCode());
    }
}
