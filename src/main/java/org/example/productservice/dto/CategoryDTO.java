package org.example.productservice.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@ToString(includeFieldNames=true)
public class CategoryDTO {

    private long id;
    private String name;
    private String description;

}
