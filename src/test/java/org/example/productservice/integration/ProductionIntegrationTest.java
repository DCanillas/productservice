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

import java.io.File;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
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
    public void getAllProductsTest(){
        log.info("Test - getAllProductsTest");
        String url = "http://localhost:"+port+"/api/v1/product";

        HttpEntity<List<ProductDTO>> requestEntity = new HttpEntity<>(null,null);
        ResponseEntity<List> response = testRestTemplate.exchange(url,
                HttpMethod.GET, requestEntity, List.class);

        log.info("Response: "+response.getBody());

        assertThat(HttpStatus.OK).isEqualTo(response.getStatusCode());
    }

    @Test
    public void createProductTest(){
        log.info("Test - createProductTest");
        String url = "http://localhost:"+port+"/api/v1/product";

        HttpEntity<ProductDTO> requestEntity = new HttpEntity<>(product,null);
        ResponseEntity<ProductDTO> response = testRestTemplate.exchange(url,
                HttpMethod.POST, requestEntity, ProductDTO.class);

        log.info("Response: "+response.getBody());
        assertThat(HttpStatus.OK).isEqualTo(response.getStatusCode());
    }

    @Test
    public void getProductByIdTest(){
        log.info("Test - getProductByIdTest");
        String url = "http://localhost:"+port+"/api/v1/product/1";

        HttpEntity<ProductDTO> requestEntity = new HttpEntity<>(product,null);
        ResponseEntity<ProductDTO> response = testRestTemplate.exchange(url,
                HttpMethod.GET, requestEntity, ProductDTO.class);

        log.info("Response: "+response.getBody());
        assertThat(HttpStatus.OK).isEqualTo(response.getStatusCode());
    }

    @Test
    public void updateProductTest(){
        log.info("Test - updateProductTest");
        String url = "http://localhost:"+port+"/api/v1/product/1";

        HttpEntity<ProductDTO> requestEntity = new HttpEntity<>(product,null);
        ResponseEntity<ProductDTO> response = testRestTemplate.exchange(url,
                HttpMethod.PUT, requestEntity, ProductDTO.class);

        log.info("Response: "+response.getBody());
        assertThat(HttpStatus.OK).isEqualTo(response.getStatusCode());
    }

    @Test
    public void deleteProductTest() throws JsonProcessingException {
        log.info("Test - deleteProductTest");

        String urlGet = "http://localhost:"+port+"/api/v1/product";

        HttpEntity<List<ProductDTO>> requestEntityGet = new HttpEntity<>(null,null);
        ResponseEntity<String> responseGet = testRestTemplate.exchange(urlGet,
                HttpMethod.GET, requestEntityGet, String.class);

        ProductDTO[] products = objectMapper.readValue(responseGet.getBody(),
                ProductDTO[].class);
        List<ProductDTO> productsList = Arrays.asList(products);

        Long productsListMaxId = productsList.stream().max((x,y) -> (int) (x.getId() - y.getId())).get().getId();

        String url = "http://localhost:"+port+"/api/v1/product/"+productsListMaxId;
        HttpEntity<ProductDTO> requestEntity = new HttpEntity<>(product,null);

        ResponseEntity<ProductDTO> response = testRestTemplate.exchange(url,
                HttpMethod.DELETE, requestEntity, ProductDTO.class);
        log.info("Response: "+response.getBody());

        assertThat(HttpStatus.OK).isEqualTo(response.getStatusCode());
    }
}
