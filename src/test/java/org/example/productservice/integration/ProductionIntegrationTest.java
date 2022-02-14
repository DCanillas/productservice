package org.example.productservice.integration;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.example.modelproject.dto.ProductDTO;
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
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ProductionIntegrationTest {
    @LocalServerPort
    private int port;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private TestRestTemplate testRestTemplate;

    private ProductDTO product;

    @BeforeEach
    public void setUp() throws Exception {
        product = new ObjectMapper().readValue(
                new File("src/test/resource/Product.json"),
                new TypeReference<ProductDTO>() {
                });
    }

    @Test
    @Transactional
    public void productsIntegrationTest(){
        log.info("Test - createProductTest");
        String urlCreate = "http://localhost:"+port+"/api/v1/product";
        HttpEntity<ProductDTO> requestEntityCreate = new HttpEntity<>(product,null);
        ResponseEntity<ProductDTO> responseCreate = testRestTemplate.exchange(urlCreate,
                HttpMethod.POST, requestEntityCreate, ProductDTO.class);
        log.info("Response: "+responseCreate.getBody());
        assertThat(HttpStatus.OK).isEqualTo(responseCreate.getStatusCode());

        log.info("Test - getProductByIdTest");
        String urlGet = "http://localhost:"+port+"/api/v1/product/1";
        HttpEntity<ProductDTO> requestEntityGet = new HttpEntity<>(product,null);
        ResponseEntity<ProductDTO> responseGet = testRestTemplate.exchange(urlGet,
                HttpMethod.GET, requestEntityGet, ProductDTO.class);
        log.info("Response: "+responseGet.getBody());
        assertThat(HttpStatus.OK).isEqualTo(responseGet.getStatusCode());

        log.info("Test - updateProductTest");
        String urlUpdate = "http://localhost:"+port+"/api/v1/product/1";
        HttpEntity<ProductDTO> requestEntityUpdate = new HttpEntity<>(product,null);
        ResponseEntity<ProductDTO> responseUpdate = testRestTemplate.exchange(urlUpdate,
                HttpMethod.PUT, requestEntityUpdate, ProductDTO.class);
        log.info("Response: "+responseUpdate.getBody());
        assertThat(HttpStatus.OK).isEqualTo(responseUpdate.getStatusCode());

        log.info("Test - getAllProductsTest");
        String urlGetAll = "http://localhost:"+port+"/api/v1/product";
        HttpEntity<List<ProductDTO>> requestEntityGetAll = new HttpEntity<>(null,null);
        ResponseEntity<List> responseGetAll = testRestTemplate.exchange(urlGetAll,
                HttpMethod.GET, requestEntityGetAll, List.class);
        log.info("Response: "+responseGetAll.getBody());
        assertThat(HttpStatus.OK).isEqualTo(responseGetAll.getStatusCode());

        log.info("Test - deleteProductTest");
        String urlDel = "http://localhost:"+port+"/api/v1/product/1";
        HttpEntity<ProductDTO> requestEntityDel = new HttpEntity<>(product,null);
        ResponseEntity<ProductDTO> responseDel = testRestTemplate.exchange(urlDel,
                HttpMethod.DELETE, requestEntityDel, ProductDTO.class);
        log.info("Response: "+responseDel.getBody());
        assertThat(HttpStatus.OK).isEqualTo(responseDel.getStatusCode());
    }
}
