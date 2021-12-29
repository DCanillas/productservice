package org.example.productservice.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@ToString(includeFieldNames=true)
public class OrderDTO {

    private long id;
    private long customerId;
    private List<ProductDTO> products = new ArrayList<>();

}
