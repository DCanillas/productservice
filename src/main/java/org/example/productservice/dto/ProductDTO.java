package org.example.productservice.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@ToString(exclude = "orders", includeFieldNames=true)
@EqualsAndHashCode(exclude="orders")
public class ProductDTO {

    private long id;
    private String name;
    private String description;
    private double price;
    private List<CategoryDTO> categories = new ArrayList<>();

}
