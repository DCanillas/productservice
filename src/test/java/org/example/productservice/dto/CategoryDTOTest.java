package org.example.productservice.dto;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Slf4j
@SpringBootTest
public class CategoryDTOTest {
    @Test
    public void testConstructor(){
        log.info("Test - testConstructor");
        CategoryDTO category = new CategoryDTO();
        assertThat(category).isNotNull();
    }

    @Test
    public void testMethodsData(){
        log.info("Test - testMethodsData");
        CategoryDTO category = new CategoryDTO();
        category.setId(1);
        category.setName("Board Games");
        category.setDescription("Best board games");
        log.info("Category: "+category.toString());
        assertThat(category.hashCode()).isNotEqualTo(new CategoryDTO().hashCode());

        CategoryDTO category2 = new CategoryDTO();
        category2.setId(category.getId());
        category2.setName(category.getName());
        category2.setDescription(category.getDescription());
        assertTrue(category.equals(category2));
        assertTrue(category.canEqual(category2));
    }

}
