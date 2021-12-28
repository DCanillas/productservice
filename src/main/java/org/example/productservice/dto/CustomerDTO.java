package org.example.productservice.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@ToString(exclude = "orders", callSuper=true, includeFieldNames=true)
@EqualsAndHashCode(exclude="orders")
public class CustomerDTO {

    private long id;
    private String name;
    private String email;
    private List<OrderDTO> orders = new ArrayList<>();

}
