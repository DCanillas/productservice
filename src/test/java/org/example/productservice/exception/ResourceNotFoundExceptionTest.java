package org.example.productservice.exception;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@SpringBootTest
public class ResourceNotFoundExceptionTest {
    @Test
    public void testConstructor(){
        log.info("Test - testConstructor");
        ResourceNotFoundException resourceNotFoundException = new ResourceNotFoundException("ERROR");
        assertThat(resourceNotFoundException).isNotNull();
    }
}
