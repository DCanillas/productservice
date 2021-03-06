package org.example.productservice.integration;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.example.modelproject.dto.CategoryDTO;
import org.example.modelproject.model.Category;
import org.example.productservice.security.TestSecurityConfig;
import org.junit.jupiter.api.BeforeEach;
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
public class CategoryIntegrationTest {
    @LocalServerPort
    private int port;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private TestRestTemplate testRestTemplate;

    private List<Category> listCategories;
    private CategoryDTO category;

    @BeforeEach
    public void setUp() throws Exception {
        listCategories = new ObjectMapper().readValue(
                new File("src/test/resource/ListCategories.json"),
                new TypeReference<List<Category>>() {
                });
        category = new ObjectMapper().readValue(
                new File("src/test/resource/Category.json"),
                new TypeReference<CategoryDTO>() {
                });
    }

    //@Test
    @Transactional
    public void categoryIntegrationTest(){
        log.info("Test - createCategoryTest");
        String urlCreate = "http://localhost:"+port+"/api/v1/category";
        HttpEntity<CategoryDTO> requestEntityCreate = new HttpEntity<>(category,null);
        ResponseEntity<CategoryDTO> responseCreate = testRestTemplate.exchange(urlCreate,
                HttpMethod.POST, requestEntityCreate, CategoryDTO.class);
        log.info("Response createCategoryTest: "+responseCreate.getBody());
        assertThat(HttpStatus.OK).isEqualTo(responseCreate.getStatusCode());

        log.info("Test - getCategoryByIdTest");
        String urlGet = "http://localhost:"+port+"/api/v1/category/1";
        HttpEntity<CategoryDTO> requestEntityGet = new HttpEntity<>(category,null);
        ResponseEntity<CategoryDTO> responseGet = testRestTemplate.exchange(urlGet,
                HttpMethod.GET, requestEntityGet, CategoryDTO.class);
        log.info("Response getCategoryByIdTest: "+responseGet.getBody());
        assertThat(HttpStatus.OK).isEqualTo(responseGet.getStatusCode());

        log.info("Test - updateCategoryTest");
        String urlUpdate = "http://localhost:"+port+"/api/v1/category/1";
        HttpEntity<CategoryDTO> requestEntityUpdate = new HttpEntity<>(category,null);
        ResponseEntity<CategoryDTO> responseUpdate = testRestTemplate.exchange(urlUpdate,
                HttpMethod.PUT, requestEntityUpdate, CategoryDTO.class);
        log.info("Response updateCategoryTest: "+responseUpdate.getBody());
        assertThat(HttpStatus.OK).isEqualTo(responseUpdate.getStatusCode());

        log.info("Test - getAllCategoriesTest");
        String urlGetAll = "http://localhost:"+port+"/api/v1/category";
        HttpEntity<List<CategoryDTO>> requestEntityGetAll = new HttpEntity<>(null,null);
        ResponseEntity<List> responseGetAll = testRestTemplate.exchange(urlGetAll,
                HttpMethod.GET, requestEntityGetAll, List.class);
        log.info("Response getAllCategoriesTest: "+responseGetAll.getBody());
        assertThat(HttpStatus.OK).isEqualTo(responseGetAll.getStatusCode());

        log.info("Test - deleteCategoryTest");
        String urlDel = "http://localhost:"+port+"/api/v1/category/1";
        HttpEntity<CategoryDTO> requestEntityDel = new HttpEntity<>(category,null);
        ResponseEntity<CategoryDTO> responseDel = testRestTemplate.exchange(urlDel,
                HttpMethod.DELETE, requestEntityDel, CategoryDTO.class);
        log.info("Response deleteCategoryTest: "+responseDel.getBody());
        assertThat(HttpStatus.OK).isEqualTo(responseDel.getStatusCode());
    }

}
