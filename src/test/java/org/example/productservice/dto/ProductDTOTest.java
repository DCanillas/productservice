package org.example.productservice.dto;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Slf4j
@SpringBootTest
public class ProductDTOTest {

    @Test
    public void testConstructor(){
        log.info("Test - testConstructor");
        ProductDTO product = new ProductDTO();
        assertThat(product).isNotNull();
    }

    @Test
    public void testMethodsData(){
        log.info("Test - testMethodsData");
        ProductDTO product = new ProductDTO();
        product.setId(1);
        product.setName("Sagrada");
        product.setDescription("Build your own church");
        product.setPrice(Double.valueOf(35.95));
        log.info("Product: "+product.toString());
        assertThat(product.hashCode()).isNotEqualTo(new ProductDTO().hashCode());

        ProductDTO product2 = new ProductDTO();
        product2.setId(product.getId());
        product2.setName(product.getName());
        product2.setDescription(product.getDescription());
        product2.setPrice(product.getPrice());
        assertTrue(product.equals(product2));
        assertTrue(product.canEqual(product2));
    }

}
