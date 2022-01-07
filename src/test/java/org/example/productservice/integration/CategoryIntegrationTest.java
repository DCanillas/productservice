package org.example.productservice.integration;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.example.modelproject.dto.CategoryDTO;
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
public class CategoryIntegrationTest {
    @LocalServerPort
    private int port;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private TestRestTemplate testRestTemplate;

    private CategoryDTO category;

    @BeforeEach
    public void setUp() throws Exception {
        category = new ObjectMapper().readValue(
                new File("src/test/resource/Category.json"),
                new TypeReference<CategoryDTO>() {
                });
    }

    @Test
    public void getAllCategoriesTest(){
        log.info("Test - getAllCategoriesTest");
        String url = "http://localhost:"+port+"/api/v1/category";

        HttpEntity<List<CategoryDTO>> requestEntity = new HttpEntity<>(null,null);
        ResponseEntity<List> response = testRestTemplate.exchange(url,
                HttpMethod.GET, requestEntity, List.class);

        log.info("Response: "+response.getBody());
        assertThat(HttpStatus.OK).isEqualTo(response.getStatusCode());
    }

    @Test
    public void createCategoryTest(){
        log.info("Test - createCategoryTest");
        String url = "http://localhost:"+port+"/api/v1/category";

        HttpEntity<CategoryDTO> requestEntity = new HttpEntity<>(category,null);
        ResponseEntity<CategoryDTO> response = testRestTemplate.exchange(url,
                HttpMethod.POST, requestEntity, CategoryDTO.class);

        log.info("Response: "+response.getBody());
        assertThat(HttpStatus.OK).isEqualTo(response.getStatusCode());
    }

    @Test
    public void getCategoryByIdTest(){
        log.info("Test - getCategoryByIdTest");
        String url = "http://localhost:"+port+"/api/v1/category/1";

        HttpEntity<CategoryDTO> requestEntity = new HttpEntity<>(category,null);
        ResponseEntity<CategoryDTO> response = testRestTemplate.exchange(url,
                HttpMethod.GET, requestEntity, CategoryDTO.class);

        log.info("Response: "+response.getBody());
        assertThat(HttpStatus.OK).isEqualTo(response.getStatusCode());
    }

    @Test
    public void updateCategoryTest(){
        log.info("Test - updateCategoryTest");
        String url = "http://localhost:"+port+"/api/v1/category/1";

        HttpEntity<CategoryDTO> requestEntity = new HttpEntity<>(category,null);
        ResponseEntity<CategoryDTO> response = testRestTemplate.exchange(url,
                HttpMethod.PUT, requestEntity, CategoryDTO.class);

        log.info("Response: "+response.getBody());
        assertThat(HttpStatus.OK).isEqualTo(response.getStatusCode());
    }

    @Test
    public void deleteCategoryTest() throws JsonProcessingException {
        log.info("Test - deleteCategoryTest");

        String urlGet = "http://localhost:"+port+"/api/v1/category";

        HttpEntity<List<CategoryDTO>> requestEntityGet = new HttpEntity<>(null,null);
        ResponseEntity<String> responseGet = testRestTemplate.exchange(urlGet,
                HttpMethod.GET, requestEntityGet, String.class);

        CategoryDTO[] categories = objectMapper.readValue(responseGet.getBody(),
                CategoryDTO[].class);
        List<CategoryDTO> categoryList = Arrays.asList(categories);

        Long categoryListMaxId = categoryList.stream().max((x,y) -> (int) (x.getId() - y.getId())).get().getId();

        String url = "http://localhost:"+port+"/api/v1/category/"+categoryListMaxId;
        HttpEntity<CategoryDTO> requestEntity = new HttpEntity<>(category,null);

        ResponseEntity<CategoryDTO> response = testRestTemplate.exchange(url,
                HttpMethod.DELETE, requestEntity, CategoryDTO.class);
        log.info("Response: "+response.getBody());

        assertThat(HttpStatus.OK).isEqualTo(response.getStatusCode());
    }
}
